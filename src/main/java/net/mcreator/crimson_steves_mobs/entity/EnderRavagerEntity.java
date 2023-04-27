
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.Difficulty;
import net.minecraft.util.Mth;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.SmartBodyHelper2;
import net.mcreator.crimson_steves_mobs.SlowRotMoveControl;
import net.mcreator.crimson_steves_mobs.IThrowAbility;
import net.mcreator.crimson_steves_mobs.CustomProtectVillagerGoal;
import net.mcreator.crimson_steves_mobs.BaseNeutralMob;

import javax.annotation.Nullable;

import java.util.function.Predicate;
import java.util.UUID;
import java.util.List;
import java.util.EnumSet;

@Mod.EventBusSubscriber
public class EnderRavagerEntity extends BaseNeutralMob implements Enemy, IThrowAbility {
	private static final EntityDataAccessor<Integer> ATTACKDELAYTIME = SynchedEntityData.defineId(EnderRavagerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> ATTACK_ACTIVE = SynchedEntityData.defineId(EnderRavagerEntity.class, EntityDataSerializers.BOOLEAN);
	private static final UUID SPEED_MODIFIER_ATTACKING_UUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
	private static final AttributeModifier SPEED_MODIFIER_ATTACKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_UUID, "Attacking speed boost", (double) 0.15F, AttributeModifier.Operation.ADDITION);
	private int targetChangeTime;
	private int stunnedTick;

	public void makeStuckInBlock(BlockState p_33796_, Vec3 p_33797_) {
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ATTACKDELAYTIME, 0);
		this.entityData.define(ATTACK_ACTIVE, false);
	}

	public int getAttackDelayTime() {
		return this.entityData.get(ATTACKDELAYTIME);
	}

	public void setAttackDelayTime(int p_32284_) {
		this.entityData.set(ATTACKDELAYTIME, p_32284_);
	}

	public boolean isAttackActive() {
		return this.entityData.get(ATTACK_ACTIVE);
	}

	public void setAttackActive(boolean p_32284_) {
		this.entityData.set(ATTACK_ACTIVE, p_32284_);
	}

	public void addAdditionalSaveData(CompoundTag p_33353_) {
		super.addAdditionalSaveData(p_33353_);
		p_33353_.putInt("StunTick", this.stunnedTick);
	}

	public void readAdditionalSaveData(CompoundTag p_33344_) {
		super.readAdditionalSaveData(p_33344_);
		this.stunnedTick = p_33344_.getInt("StunTick");
	}

	protected PathNavigation createNavigation(Level p_33348_) {
		return new EnderRavagerEntity.RavagerNavigation(this, p_33348_);
	}

	static class RavagerNavigation extends GroundPathNavigation {
		public RavagerNavigation(Mob p_33379_, Level p_33380_) {
			super(p_33379_, p_33380_);
		}

		protected PathFinder createPathFinder(int p_33382_) {
			this.nodeEvaluator = new EnderRavagerEntity.RavagerNodeEvaluator();
			return new PathFinder(this.nodeEvaluator, p_33382_);
		}
	}

	static class RavagerNodeEvaluator extends WalkNodeEvaluator {
		protected BlockPathTypes evaluateBlockPathType(BlockGetter p_33387_, boolean p_33388_, boolean p_33389_, BlockPos p_33390_, BlockPathTypes p_33391_) {
			return (p_33391_ == BlockPathTypes.LEAVES || level.getBlockState(p_33390_).is(BlockTags.LOGS) || level.getBlockState(p_33390_).is(BlockTags.WART_BLOCKS))
					? BlockPathTypes.OPEN
					: super.evaluateBlockPathType(p_33387_, p_33388_, p_33389_, p_33390_, p_33391_);
		}
	}

	protected boolean teleport() {
		if (!this.level.isClientSide() && this.isAlive()) {
			double d0 = this.getX() + (this.random.nextDouble() - 0.5D) * 64.0D;
			double d1 = this.getY() + (double) (this.random.nextInt(64) - 32);
			double d2 = this.getZ() + (this.random.nextDouble() - 0.5D) * 64.0D;
			return this.teleport(d0, d1, d2);
		} else {
			return false;
		}
	}

	boolean teleportTowards(Entity p_32501_) {
		Vec3 vec3 = new Vec3(this.getX() - p_32501_.getX(), this.getY(0.5D) - p_32501_.getEyeY(), this.getZ() - p_32501_.getZ());
		vec3 = vec3.normalize();
		double d0 = 16.0D;
		double d1 = this.getX() + (this.random.nextDouble() - 0.5D) * 8.0D - vec3.x * 16.0D;
		double d2 = this.getY() + (double) (this.random.nextInt(16) - 8) - vec3.y * 16.0D;
		double d3 = this.getZ() + (this.random.nextDouble() - 0.5D) * 8.0D - vec3.z * 16.0D;
		return this.teleport(d1, d2, d3);
	}

	private boolean teleport(double p_32544_, double p_32545_, double p_32546_) {
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(p_32544_, p_32545_, p_32546_);
		while (blockpos$mutableblockpos.getY() > this.level.getMinBuildHeight() && !this.level.getBlockState(blockpos$mutableblockpos).getMaterial().blocksMotion()) {
			blockpos$mutableblockpos.move(Direction.DOWN);
		}
		BlockState blockstate = this.level.getBlockState(blockpos$mutableblockpos);
		boolean flag = blockstate.getMaterial().blocksMotion();
		boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);
		if (flag && !flag1) {
			net.minecraftforge.event.entity.EntityTeleportEvent.EnderEntity event = net.minecraftforge.event.ForgeEventFactory.onEnderTeleport(this, p_32544_, p_32545_, p_32546_);
			if (event.isCanceled())
				return false;
			boolean flag2 = this.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
			if (flag2 && !this.isSilent()) {
				this.level.playSound((Player) null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0F, 1.0F);
				this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
			}
			return flag2;
		} else {
			return false;
		}
	}

	private boolean hurtWithCleanWater(DamageSource p_186273_, ThrownPotion p_186274_, float p_186275_) {
		ItemStack itemstack = p_186274_.getItem();
		Potion potion = PotionUtils.getPotion(itemstack);
		List<MobEffectInstance> list = PotionUtils.getMobEffects(itemstack);
		boolean flag = potion == Potions.WATER && list.isEmpty();
		return flag ? super.hurt(p_186273_, p_186275_) : false;
	}

	public boolean hurt(DamageSource p_32494_, float p_32495_) {
		if (this.isInvulnerableTo(p_32494_)) {
			return false;
		} else if (p_32494_ instanceof IndirectEntityDamageSource) {
			Entity entity = p_32494_.getDirectEntity();
			boolean flag1 = this.random.nextInt(2) == 0 ? true : false;
			if (flag1) {
				if (entity instanceof ThrownPotion) {
					flag1 = this.hurtWithCleanWater(p_32494_, (ThrownPotion) entity, p_32495_);
				} else
					flag1 = super.hurt(p_32494_, p_32495_);
			} else {
				if (!this.level.isClientSide())
					for (int i = 0; i < 64; ++i) {
						if (this.teleport()) {
							return true;
						}
					}
			}
			return flag1;
		} else {
			boolean flag = super.hurt(p_32494_, p_32495_);
			if (!this.level.isClientSide() && !(p_32494_.getEntity() instanceof LivingEntity) && this.random.nextInt(8) != 0) {
				this.teleport();
			}
			return flag;
		}
	}

	public boolean isSensitiveToWater() {
		return true;
	}

	public SoundSource getSoundSource() {
		return SoundSource.HOSTILE;
	}

	public void setTarget(@Nullable LivingEntity p_32537_) {
		AttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
		if (p_32537_ == null) {
			this.targetChangeTime = 0;
			attributeinstance.removeModifier(SPEED_MODIFIER_ATTACKING);
		} else {
			this.targetChangeTime = this.tickCount;
			if (!attributeinstance.hasModifier(SPEED_MODIFIER_ATTACKING)) {
				attributeinstance.addTransientModifier(SPEED_MODIFIER_ATTACKING);
			}
		}
		super.setTarget(p_32537_); //Forge: Moved down to allow event handlers to write data manager values.
	}

	protected boolean shouldDespawnInPeaceful() {
		return true;
	}

	public EnderRavagerEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.ENDER_RAVAGER.get(), world);
	}

	public EnderRavagerEntity(EntityType<? extends EnderRavagerEntity> type, Level world) {
		super(type, world);
		xpReward = 20;
		this.maxUpStep = this.getScale() + 1;
		this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
		this.refreshDimensions();
		this.moveControl = new SlowRotMoveControl(this);
	}

	@Override
	protected BodyRotationControl createBodyControl() {
		return new SmartBodyHelper2(this);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new EnderRavagerEntity.EndermanLookForPlayerGoal(this, this::isAngryAt));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
		this.targetSelector.addGoal(1, new CustomProtectVillagerGoal(this, EnderRavagerEntity.class));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				float f = EnderRavagerEntity.this.getBbWidth() * this.mob.getScale() - 0.1F;
				return (double) (f * 2.0F * f * 2.0F + entity.getBbWidth());
			}

			protected void checkAndPerformAttack(LivingEntity p_25557_, double p_25558_) {
				double d0 = this.getAttackReachSqr(p_25557_);
				if (p_25558_ <= d0 * 5 && this.getTicksUntilNextAttack() <= 0 && !EnderRavagerEntity.this.isAttackActive()) {
					this.mob.playSound(SoundEvents.ZOMBIE_VILLAGER_CONVERTED, 2, 0.5f);
					this.resetAttackCooldown();
					EnderRavagerEntity.this.setAttackActive(true);
					/*
					this.mob.swing(InteractionHand.MAIN_HAND);
					this.mob.doHurtTarget(p_25557_);
					EnderRavagerEntity.this.setAttackDelayTime(0);
					*/
				}
			}
		});
		this.goalSelector.addGoal(1, new EnderRavagerEntity.EndermanFreezeWhenLookedAt(this));
		//this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true, false));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(5, new FloatGoal(this));
	}

	static class EndermanLookForPlayerGoal extends NearestAttackableTargetGoal<Player> {
		private final EnderRavagerEntity enderman;
		@Nullable
		private Player pendingTarget;
		private int aggroTime;
		private int teleportTime;
		private final TargetingConditions startAggroTargetConditions;
		private final TargetingConditions continueAggroTargetConditions = TargetingConditions.forCombat().ignoreLineOfSight();

		public EndermanLookForPlayerGoal(EnderRavagerEntity p_32573_, @Nullable Predicate<LivingEntity> p_32574_) {
			super(p_32573_, Player.class, 10, false, false, p_32574_);
			this.enderman = p_32573_;
			this.startAggroTargetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector((p_32578_) -> {
				return p_32573_.isLookingAtMe((Player) p_32578_);
			});
		}

		public boolean canUse() {
			this.pendingTarget = this.enderman.level.getNearestPlayer(this.startAggroTargetConditions, this.enderman);
			return this.pendingTarget != null;
		}

		public void start() {
			this.aggroTime = this.adjustedTickDelay(5);
			this.teleportTime = 0;
		}

		public void stop() {
			this.pendingTarget = null;
			super.stop();
		}

		public boolean canContinueToUse() {
			if (this.pendingTarget != null) {
				if (!this.enderman.isLookingAtMe(this.pendingTarget)) {
					return false;
				} else {
					this.enderman.lookAt(this.pendingTarget, 10.0F, 10.0F);
					return true;
				}
			} else {
				return this.target != null && this.continueAggroTargetConditions.test(this.enderman, this.target) ? true : super.canContinueToUse();
			}
		}

		public void tick() {
			if (this.enderman.getTarget() == null) {
				super.setTarget((LivingEntity) null);
			}
			if (this.pendingTarget != null) {
				if (--this.aggroTime <= 0) {
					this.target = this.pendingTarget;
					this.pendingTarget = null;
					super.start();
				}
			} else {
				if (this.target != null && !this.enderman.isPassenger()) {
					if (this.enderman.isLookingAtMe((Player) this.target)) {
						if (this.target.distanceToSqr(this.enderman) < 16.0D) {
							this.enderman.teleport();
						}
						this.teleportTime = 0;
					} else if (this.target.distanceToSqr(this.enderman) > 256.0D && this.teleportTime++ >= this.adjustedTickDelay(30) && this.enderman.teleportTowards(this.target)) {
						this.teleportTime = 0;
					}
				}
				super.tick();
			}
		}
	}

	protected float teleportationChance() {
		return 0.15f;
	}

	public boolean doHurtTarget(Entity entityIn) {
		if (this.random.nextInt(6) == 0)
			this.teleport();
		if (entityIn instanceof LivingEntity livingentity) {
			if (this.random.nextFloat() < this.teleportationChance()) {
				double x = livingentity.getX() + (double) ((livingentity.getRandom().nextFloat() - 0.5F) * 14.0F);
				double y = livingentity.getY() + (double) livingentity.getRandom().nextFloat() * 13;
				double z = livingentity.getZ() + (double) ((livingentity.getRandom().nextFloat() - 0.5F) * 14.0F);
				entityIn.stopRiding();
				BlockPos blockpos1 = new BlockPos(x, y, z);
				int i = 0;
				while (!livingentity.getLevel().isEmptyBlock(blockpos1) && i < 10) {
					y++;
					i++;
				}
				livingentity.setPos(x, y, z);
			}
		}
		return IThrowAbility.hurtAndThrowTargetHorizontally(this, (LivingEntity) entityIn);
	}

	static class EndermanFreezeWhenLookedAt extends Goal {
		private final EnderRavagerEntity enderman;
		@Nullable
		private LivingEntity target;

		public EndermanFreezeWhenLookedAt(EnderRavagerEntity p_32550_) {
			this.enderman = p_32550_;
			this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
		}

		public boolean canUse() {
			this.target = this.enderman.getTarget();
			if (!(this.target instanceof Player)) {
				return false;
			} else {
				double d0 = this.target.distanceToSqr(this.enderman);
				return d0 > 256.0D ? false : this.enderman.isLookingAtMe((Player) this.target);
			}
		}

		public void start() {
			this.enderman.getNavigation().stop();
		}

		public void tick() {
			this.enderman.getLookControl().setLookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
		}
	}

	public float getScale() {
		return super.getScale() * 1.3f;
	}

	protected void customServerAiStep() {
		if (this.level.isDay() && this.tickCount >= this.targetChangeTime + 600) {
			float f = this.getLightLevelDependentMagicValue();
			if (f > 0.5F && this.level.canSeeSky(this.blockPosition()) && this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
				this.setTarget((LivingEntity) null);
				this.teleport();
			}
		}
		super.customServerAiStep();
	}

	protected double getAttackReachSqr(LivingEntity entity) {
		float f = this.getBbWidth() - 0.1F;
		return (double) (f * 2.0F * f * 2.0F + entity.getBbWidth());
	}

	public void aiStep() {
		super.aiStep();
		int i = this.getAttackDelayTime();
		if (!this.level.isClientSide) {
			this.updatePersistentAnger((ServerLevel) this.level, true);
		}
		if (this.isAlive()) {
			if (this.isAttackActive()) {
				if (i <= 40) {
					this.setAttackDelayTime(i + 1);
					if (this.level instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.PORTAL, this.getX(), this.getY(), this.getZ(), (int) (i * this.getScale() / 4), this.getBbWidth(), this.getBbHeight(), this.getBbWidth(), 0.01);
				} else {
					this.playSound(getAttackSound(), this.getSoundVolume(), getAttackVoicePitch());
					this.setAttackDelayTime(0);
					this.setAttackActive(false);
					this.swing(InteractionHand.MAIN_HAND);
					if (this.getTarget() != null) {
						LivingEntity livingentity = this.getTarget();
						double d0 = this.getAttackReachSqr(livingentity);
						if (this.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ()) <= d0) {
							this.doHurtTarget(livingentity);
						}
					}
				}
			}
			if (this.getTarget() != null) {
				LivingEntity livingentity = this.getTarget();
				if (this.random.nextInt(100) == 0)
					this.teleportTowards(livingentity);
			}
			if (this.isImmobile()) {
				this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.0D);
			} else {
				double d0 = this.getTarget() != null ? 0.35D : 0.3D;
				double d1 = this.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue();
				this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(Mth.lerp(0.1D, d1, d0));
			}
			if (this.horizontalCollision && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this)) {
				boolean flag = false;
				AABB aabb = this.getBoundingBox().inflate(0.2D);
				for (BlockPos blockpos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
					BlockState blockstate = this.level.getBlockState(blockpos);
					Block block = blockstate.getBlock();
					if (block instanceof LeavesBlock || level.getBlockState(blockpos).is(BlockTags.LOGS) || level.getBlockState(blockpos).is(BlockTags.WART_BLOCKS)) {
						flag = this.level.destroyBlock(blockpos, true, this) || flag;
					}
				}
			}
		}
		if (this.stunnedTick > 0) {
			--this.stunnedTick;
			if (this.stunnedTick % 4 == 0)
				this.playSound(this.getDeathSound(), 1, 2);
			this.xRotO += (this.random.nextFloat() - 0.5f) * 36;
			this.yHeadRot += (this.random.nextFloat() - 0.5f) * 36;
			this.yHeadRotO += (this.random.nextFloat() - 0.5f) * 36;
		}
	}

	protected boolean isImmobile() {
		return super.isImmobile() || this.swinging || this.stunnedTick > 0;
	}

	protected void blockedByShield(LivingEntity p_33361_) {
		if (this.random.nextDouble() < 0.5D) {
			this.teleport();
			this.stunnedTick = 60;
			this.level.broadcastEntityEvent(this, (byte) 39);
		}
	}

	public void handleEntityEvent(byte p_33335_) {
		if (p_33335_ == 39) {
			this.stunnedTick = 60;
		}
		super.handleEntityEvent(p_33335_);
	}

	boolean isLookingAtMe(LivingEntity p_32535_) {
		Vec3 vec3 = p_32535_.getViewVector(1.0F).normalize();
		Vec3 vec31 = new Vec3(this.getX() - p_32535_.getX(), this.getEyeY() - p_32535_.getEyeY(), this.getZ() - p_32535_.getZ());
		double d0 = vec31.length();
		vec31 = vec31.normalize();
		double d1 = vec3.dot(vec31);
		return d1 > 1.0D - 0.025D / d0 ? p_32535_.hasLineOfSight(this) : false;
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:endersent_idle"));
	}

	public SoundEvent getAttackSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:endersent_hurt"));
	}

	public float getAttackVoicePitch() {
		return 2;
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:endersent_step")), 0.15f, 1);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:endersent_hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:endersent_death"));
	}

	/*
	@SubscribeEvent
	public static void addLivingEntityToBiomes(BiomeLoadingEvent event) {
		event.getSpawns().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(WorkspaceModEntities.ENDER_RAVAGER.get(), 3, 1, 1));
	}
	*/
	public static boolean isOverworld(LevelAccessor world) {
		if (world instanceof Level _lvl) {
			if (_lvl.dimension() == Level.OVERWORLD || _lvl.dimension() == Level.END) {
				return true;
			}
		}
		return false;
	}

	public static void init() {
		SpawnPlacements.register(CrimsonStevesMobsModEntities.ENDER_RAVAGER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos, random) -> (world.getDifficulty() != Difficulty.PEACEFUL && isOverworld(world) && Monster.isDarkEnoughToSpawn(world, pos, random) && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)));
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
		builder = builder.add(Attributes.MAX_HEALTH, 185);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 16);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.3);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 2);
		return builder;
	}
}
