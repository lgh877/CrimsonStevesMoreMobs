
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.FluidTags;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.IThrowAbility;
import net.mcreator.crimson_steves_mobs.CustomMathHelper;

import java.util.EnumSet;

@Mod.EventBusSubscriber
public class TRabusEntity extends Raider implements IThrowAbility {
	private int rushTicks;

	public boolean isAlliedTo(Entity p_34110_) {
		if (this.getTeam() != null) {
			return super.isAlliedTo(p_34110_);
		} else if (p_34110_ instanceof LivingEntity && ((LivingEntity) p_34110_).getMobType() == MobType.ILLAGER) {
			return this.getTeam() == null && p_34110_.getTeam() == null;
		} else {
			return false;
		}
	}

	public void makeStuckInBlock(BlockState p_33796_, Vec3 p_33797_) {
	}

	protected PathNavigation createNavigation(Level p_33348_) {
		if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(p_33348_, this))
			return new TRabusEntity.RavagerNavigation(this, p_33348_);
		else
			return new GroundPathNavigation(this, p_33348_);
	}

	public void tick() {
		super.tick();
		if (!this.level.isClientSide) {
			if (this.tickCount % 500 == 0) {
				this.navigation = createNavigation(this.level);
			}
		}
	}

	public static class RavagerNavigation extends GroundPathNavigation {
		public RavagerNavigation(Mob p_33379_, Level p_33380_) {
			super(p_33379_, p_33380_);
		}

		protected PathFinder createPathFinder(int p_33382_) {
			this.nodeEvaluator = new TRabusEntity.RavagerNodeEvaluator();
			return new PathFinder(this.nodeEvaluator, p_33382_);
		}
	}

	public static class RavagerNodeEvaluator extends WalkNodeEvaluator {
		protected BlockPathTypes evaluateBlockPathType(BlockGetter p_33387_, boolean p_33388_, boolean p_33389_, BlockPos p_33390_, BlockPathTypes p_33391_) {
			return (p_33391_ == BlockPathTypes.LEAVES || level.getBlockState(p_33390_).getMaterial() == Material.WOOD || level.getBlockState(p_33390_).getMaterial() == Material.BAMBOO || level.getBlockState(p_33390_).getMaterial() == Material.WOOL
					|| level.getBlockState(p_33390_).getMaterial() == Material.PLANT || level.getBlockState(p_33390_).getMaterial() == Material.GLASS)
							? BlockPathTypes.OPEN
							: super.evaluateBlockPathType(p_33387_, p_33388_, p_33389_, p_33390_, p_33391_);
		}
	}

	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.RED, ServerBossEvent.BossBarOverlay.PROGRESS);

	public TRabusEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.T_RABUS.get(), world);
	}

	public void applyRaidBuffs(int p_34140_, boolean p_34141_) {
	}

	public boolean canBeLeader() {
		return false;
	}

	public TRabusEntity(EntityType<TRabusEntity> type, Level world) {
		super(type, world);
		xpReward = 25;
		this.maxUpStep = this.getScale() / 2;
		this.setPathfindingMalus(BlockPathTypes.LAVA, 0.0F);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
		this.blocksBuilding = true;
		this.refreshDimensions();
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
		this.goalSelector.addGoal(1, new CustomLeapAtTargetGoal(this, 1.5f));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractGolem.class, true));
		this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				float f = TRabusEntity.this.getBbWidth() - 0.1F;
				return (double) (f * 2.0F * f * 2.0F + entity.getBbWidth());
			}
		});
		this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
		this.goalSelector.addGoal(7, new FloatGoal(this) {
			public boolean canUse() {
				return TRabusEntity.this.isInWater() && TRabusEntity.this.getFluidHeight(FluidTags.WATER) > TRabusEntity.this.getFluidJumpThreshold();
			}
		});
	}

	public class CustomLeapAtTargetGoal extends Goal {
		private final Mob mob;
		private LivingEntity target;
		private final float yd;

		public CustomLeapAtTargetGoal(Mob p_25492_, float p_25493_) {
			this.mob = p_25492_;
			this.yd = p_25493_;
			this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
		}

		public boolean canUse() {
			if (this.mob.isVehicle()) {
				return false;
			} else {
				this.target = this.mob.getTarget();
				if (this.target == null) {
					return false;
				} else {
					double d0 = this.mob.distanceToSqr(this.target);
					if (!(d0 > 169)) {
						if (!this.mob.isOnGround()) {
							return false;
						} else {
							return this.mob.getRandom().nextInt(reducedTickDelay(40)) == 0;
						}
					} else {
						return false;
					}
				}
			}
		}

		public boolean canContinueToUse() {
			return !this.mob.isOnGround();
		}

		public void start() {
			Vec3 vec3 = this.mob.getDeltaMovement();
			Vec3 vec31 = new Vec3(this.target.getX() - this.mob.getX(), 0.0D, this.target.getZ() - this.mob.getZ());
			if (vec31.lengthSqr() > 1.0E-7D) {
				vec31 = vec31.normalize().add(vec3.scale(0.4D));
			}
			this.mob.playSound(SoundEvents.GENERIC_EXPLODE, 2, 2);
			this.mob.setDeltaMovement(vec31.x, (double) this.yd, vec31.z);
		}
	}

	public boolean causeFallDamage(float distance, float p_148712_, DamageSource p_148713_) {
		if (distance > 3) {
			double modifiedDist = Math.sqrt(distance) * 2;
			AABB aabb = this.getBoundingBox().inflate(3);
			for (int j = 0; j < 30; ++j) {
				float f = this.random.nextFloat() * ((float) Math.PI * 2F);
				float f1 = this.random.nextFloat() * 0.5F + 0.5F;
				float f2 = Mth.sin(f) * 6F * f1;
				float f3 = Mth.cos(f) * 6F * f1;
				if (this.level instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.ANGRY_VILLAGER, this.getX() + (double) f2, this.getY() + 0.5, this.getZ() + (double) f3, 1, 0, 0, 0, 0);
			}
			this.playSound(SoundEvents.GENERIC_EXPLODE, 2, 2);
			for (LivingEntity livingentity : this.level.getEntitiesOfClass(LivingEntity.class, aabb)) {
				if (livingentity != this && livingentity.isOnGround()) {
					livingentity.invulnerableTime = 0;
					if (!livingentity.isAlliedTo(this) && livingentity.hurt(DamageSource.mobAttack(this), (float) Mth.clamp(modifiedDist * 3, 0, 12))) {
						this.strongKnockback(livingentity, modifiedDist * 0.3);
					}
				}
			}
		}
		return false;
	}

	private void strongKnockback(Entity p_33340_, double modifier) {
		double d0 = (p_33340_.getX() - this.getX());
		double d1 = (p_33340_.getZ() - this.getZ());
		double d2 = Math.max(d0 * d0 + d1 * d1, 0.001D) * 2;
		p_33340_.push(d0 * modifier / d2, 0.5D * modifier, d1 * modifier / d2);
	}

	public boolean doHurtTarget(Entity p_34491_) {
		if (!(p_34491_ instanceof LivingEntity)) {
			return false;
		} else {
			this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 2.0F, this.getVoicePitch());
			if (this.random.nextInt(2) == 0)
				return IThrowAbility.hurtAndThrowTargetVertically(this, (LivingEntity) p_34491_);
			else
				return IThrowAbility.hurtAndThrowTargetHorizontally(this, (LivingEntity) p_34491_);
		}
	}

	public float getVoicePitch() {
		return super.getVoicePitch() * 0.6f;
	}

	@Override
	public MobType getMobType() {
		return MobType.ILLAGER;
	}

	public SoundEvent getCelebrateSound() {
		return null;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:robot_idle"));
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_iron")), 0.3f, 0.5f);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.zombie.attack_iron_door"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.zombie.attack_iron_door"));
	}

	@Override
	public boolean canChangeDimensions() {
		return false;
	}

	public float getScale() {
		return 2;
	}

	public void aiStep() {
		super.aiStep();
		this.floatStrider();
		if (this.getDeltaMovement().y < -0.4)
			this.push(0, -0.8, 0);
		if (this.isAlive()) {
			if (this.isImmobile()) {
				this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.0D);
			} else {
				double d0 = this.getTarget() != null ? 0.35D : 0.3D;
				double d1 = this.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue();
				this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(Mth.lerp(0.1D, d1, d0));
			}
			if ((this.horizontalCollision || this.getDeltaMovement().y < -0.4) && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this)) {
				boolean flag = false;
				AABB aabb = this.getBoundingBox().inflate(0.2D);
				for (BlockPos blockpos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
					BlockState blockstate = this.level.getBlockState(blockpos);
					Block block = blockstate.getBlock();
					if (block instanceof LeavesBlock || level.getBlockState(blockpos).getMaterial() == Material.WOOD || level.getBlockState(blockpos).getMaterial() == Material.BAMBOO || level.getBlockState(blockpos).getMaterial() == Material.WOOL
							|| level.getBlockState(blockpos).getMaterial() == Material.PLANT || level.getBlockState(blockpos).getMaterial() == Material.GLASS) {
						flag = this.level.destroyBlock(blockpos, true, this) || flag;
					}
				}
				if (!flag && this.onGround) {
					this.jumpFromGround();
				}
			}
		}
	}

	private void floatStrider() {
		if (this.isInLava()) {
			CollisionContext collisioncontext = CollisionContext.of(this);
			if (collisioncontext.isAbove(LiquidBlock.STABLE_SHAPE, this.blockPosition(), true) && !this.level.getFluidState(this.blockPosition().above()).is(FluidTags.LAVA)) {
				if (this.level instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(), this.getZ(), 3, this.getBbWidth(), 0, this.getBbWidth(), 0.01);
				this.onGround = true;
			} else {
				this.setDeltaMovement(this.getDeltaMovement().scale(0.5D).add(0.0D, 0.05D, 0.0D));
			}
		}
	}

	public boolean canStandOnFluid(FluidState p_204067_) {
		return p_204067_.is(FluidTags.LAVA);
	}

	@Override
	public void startSeenByPlayer(ServerPlayer player) {
		super.startSeenByPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer player) {
		super.stopSeenByPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	public double getPassengersRidingOffset() {
		return this.getEyeHeight();
	}

	@Override
	public void customServerAiStep() {
		super.customServerAiStep();
		this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
	}

	public static void init() {
		SpawnPlacements.register(CrimsonStevesMobsModEntities.T_RABUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos, random) -> (CustomMathHelper.checkAnimalSpawnRules(entityType, world, reason, pos, random) && CustomMathHelper.isOverworld(world)));
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
		builder = builder.add(Attributes.MAX_HEALTH, 200);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		builder = builder.add(Attributes.ARMOR, 10);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 8);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.6);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 3);
		return builder;
	}
}
