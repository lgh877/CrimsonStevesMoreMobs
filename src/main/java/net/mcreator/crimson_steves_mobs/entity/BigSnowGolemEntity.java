
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.BlockPos;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.SmartBodyHelper2;
import net.mcreator.crimson_steves_mobs.SlowRotMoveControl;
import net.mcreator.crimson_steves_mobs.CustomMathHelper;

import javax.annotation.Nullable;

import java.util.UUID;
import java.util.List;

@Mod.EventBusSubscriber
public class BigSnowGolemEntity extends TamableAnimal implements NeutralMob {
	private int remainingPersistentAngerTime;
	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	@Nullable
	private UUID persistentAngerTarget;
	/*
	private static final Set<ResourceLocation> SPAWN_BIOMES = Set.of(new ResourceLocation("windswept_hills"), new ResourceLocation("snowy_plains"),
			new ResourceLocation("snowy_slopes"), new ResourceLocation("snowy_taiga"), new ResourceLocation("snowy_beach"));
	*/
	private double[] sd = new double[5];
	private int throwState;
	private boolean isThrowing;
	private int bullets;
	private static final EntityDataAccessor<Boolean> SHOOTING = SynchedEntityData.defineId(BigSnowGolemEntity.class, EntityDataSerializers.BOOLEAN);

	public void makeStuckInBlock(BlockState p_33796_, Vec3 p_33797_) {
		if (this.tickCount % 3 == 0)
			super.makeStuckInBlock(p_33796_, p_33797_);
	}

	public boolean isShooting() {
		return this.entityData.get(SHOOTING);
	}

	public void setShooting(boolean p_29568_) {
		this.entityData.set(SHOOTING, p_29568_);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOTING, false);
	}

	public boolean isAlliedTo(Entity entityIn) {
		if (this.getTeam() != null) {
			return super.isAlliedTo(entityIn);
		} else if (entityIn instanceof Mob mob) {
			if (entityIn.getType() == this.getType())
				return true;
			else if (!(entityIn instanceof Enemy) && mob.getTarget() != this)
				return true;
		} else if (this.getOwner() == entityIn)
			return true;
		return super.isAlliedTo(entityIn);
	}

	public boolean canFreeze() {
		return false;
	}

	public BigSnowGolemEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.BIG_SNOW_GOLEM.get(), world);
	}

	public BigSnowGolemEntity(EntityType<BigSnowGolemEntity> type, Level world) {
		super(type, world);
		xpReward = 20;
		this.maxUpStep = 1.5f;
		this.moveControl = new SlowRotMoveControl(this);
		/*
		this.getPersistentData().putBoolean("shootState", true);
		this.getPersistentData().putBoolean("shootActive", true);
		*/
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
		this.goalSelector.addGoal(1, new additionalAttacksGoal());
		this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, false) {
			protected double getAttackReachSqr(LivingEntity entity) {
				if (mob.swinging || !mob.getSensing().hasLineOfSight(entity))
					return -1;
				if (CustomMathHelper.isEntityInBox(entity, mob, 1)) {
					return Double.POSITIVE_INFINITY;
				}
				float f = mob.getBbWidth() - 0.1F;
				return (double) (f * f + entity.getBbWidth() * entity.getBbWidth()) * 2;
			}
		});
		this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false, (p_28879_) -> {
			return p_28879_ instanceof Enemy && !(p_28879_ instanceof Creeper);
		}));
		this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 20.0F, 2.0F, false));
		this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(9, new FloatGoal(this));
		this.targetSelector.addGoal(9, new ResetUniversalAngerTargetGoal<>(this, false));
	}

	class additionalAttacksGoal extends Goal {
		public boolean canUse() {
			return BigSnowGolemEntity.this.getTarget() != null;
		}

		public void tick() {
			if (BigSnowGolemEntity.this.random.nextInt(50) == 0) {
				BigSnowGolemEntity.this.setShooting(!BigSnowGolemEntity.this.isShooting());
			}
			if (BigSnowGolemEntity.this.random.nextInt(40) == 0 && !BigSnowGolemEntity.this.swinging && !BigSnowGolemEntity.this.isThrowing) {
				LivingEntity target = BigSnowGolemEntity.this.getTarget();
				BigSnowGolemEntity.this.swing(InteractionHand.MAIN_HAND);
				BigSnowGolemEntity.this.throwState = 5;
				BigSnowGolemEntity.this.isThrowing = true;
				BigSnowGolemEntity.this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.5f);
				BigSnowGolemEntity.this.sd[0] = target.getEyeY() - (double) 1.1F;
				BigSnowGolemEntity.this.sd[1] = target.getX() - BigSnowGolemEntity.this.getX();
				BigSnowGolemEntity.this.sd[2] = BigSnowGolemEntity.this.sd[0] - BigSnowGolemEntity.this.getEyeY();
				BigSnowGolemEntity.this.sd[3] = target.getZ() - BigSnowGolemEntity.this.getZ();
				BigSnowGolemEntity.this.sd[4] = Math.sqrt(BigSnowGolemEntity.this.sd[1] * BigSnowGolemEntity.this.sd[1] + BigSnowGolemEntity.this.sd[3] * BigSnowGolemEntity.this.sd[3]) * (double) 0.2F;
			}
		}

		public void stop() {
			BigSnowGolemEntity.this.setShooting(false);
		}
	}

	/*
	protected void customServerAiStep() {
		super.customServerAiStep();
		if (this.tickCount % 300 > 250 && this.isAggressive()) {
			
		}
	}
	*/
	public void aiStep() {
		super.aiStep();
		this.updateSwingTime();
		if (!this.level.isClientSide) {
			int i = Mth.floor(this.getX());
			int j = Mth.floor(this.getY());
			int k = Mth.floor(this.getZ());
			BlockPos blockpos = new BlockPos(i, j, k);
			if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this)) {
				BlockState blockstate = Blocks.SNOW.defaultBlockState();
				for (int l = 0; l < 4; ++l) {
					i = Mth.floor(this.getX() + (double) ((float) (l % 2 * 2 - 1) * 0.25F));
					j = Mth.floor(this.getY());
					k = Mth.floor(this.getZ() + (double) ((float) (l / 2 % 2 * 2 - 1) * 0.25F));
					BlockPos blockpos1 = new BlockPos(i, j, k);
					if (this.level.isEmptyBlock(blockpos) && blockstate.canSurvive(this.level, blockpos)) {
						this.level.setBlockAndUpdate(blockpos1, blockstate);
					}
				}
			}
		}
		if (this.isAlive()) {
			if (this.getTarget() != null && !this.getTarget().isAlive())
				this.setTarget((LivingEntity) null);
			/*if (this.getTarget() != null && this.animStep % 10 <= 0.5f && !this.swinging && !this.isThrowing) {
				LivingEntity target = this.getTarget();
				this.swing(InteractionHand.MAIN_HAND);
				this.throwState = 3;
				this.isThrowing = true;
				sd[0] = target.getEyeY() - (double) 1.1F;
				sd[1] = target.getX() - this.getX();
				sd[2] = sd[0] - this.getEyeY();
				sd[3] = target.getZ() - this.getZ();
				sd[4] = Math.sqrt(sd[1] * sd[1] + sd[3] * sd[3]) * (double) 0.2F;
				this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.5f);
			} else*/ if (this.isThrowing && this.throwState == 0) {
				BigSnowBallEntity snowball = new BigSnowBallEntity(CrimsonStevesMobsModEntities.BIG_SNOW_BALL.get(), this, this.level);
				snowball.shoot(sd[1], sd[2] + sd[4], sd[3], 1.6F, 12.0F);
				snowball.setSilent(true);
				this.isThrowing = false;
				this.level.addFreshEntity(snowball);
			}
			/*
			if (this.getPersistentData().getBoolean("shootState")) {
				if (this.getTarget() != null)
					this.lookAt(this.getTarget(), 180, 180);
				HardenedSnowBallEntity.shoot(level, this, 0.1f);
				HardenedSnowBallEntity.shoot(level, this, 0.25f);
				HardenedSnowBallEntity.shoot(level, this, 0.4f);
				if (this.random.nextInt(90) == 0) {
					this.getPersistentData().putBoolean("shootState", false);
				}
			}
			*/
			if (this.throwState > 0)
				this.throwState -= 1;
			/*
			if (!this.isAggressive() && this.isShooting() && this.random.nextInt(50) == 0) {
				this.setShooting(false);
			}
			*/
			if (this.isShooting()) {
				if (this.getTarget() != null) {
					this.lookAt(this.getTarget(), 180, 180);
				}
				Vec3 vec32;
				if (this.isLeftHanded())
					vec32 = this.calculateViewVector(0, this.yBodyRot + 90);
				//vec32 = ;
				else
					vec32 = this.calculateViewVector(0, this.yBodyRot - 90);
				Vec3 vec31 = new Vec3(this.getLookAngle().x * 1.3 + this.getX() + vec32.x * 2, this.getY() + this.getBbHeight() / 2 + this.getLookAngle().y * 1.3, this.getLookAngle().z * 1.3 + this.getZ() + vec32.z * 2);
				//vec31.add(vec32.x * 20, 0, vec32.z * 20);
				//vec32 = calculateViewVector(0, this.yBodyRot - 90);
				if (this.isBaby())
					vec31.scale(0.5);
				HardenedSnowBallEntity.shoot(level, this, 0.4f, vec31);
				HardenedSnowBallEntity.shoot(level, this, 0.6f, vec31);
				HardenedSnowBallEntity.shoot(level, this, 0.8f, vec31);
				this.playSound(SoundEvents.POWDER_SNOW_FALL, 2, 0.5f);
			}
			/*
			if (this.tickCount % 300 > 250) {
			if (this.getTarget() != null)
				this.lookAt(this.getTarget(), 180, 180);
			HardenedSnowBallEntity.shoot(level, this, 0.1f);
			HardenedSnowBallEntity.shoot(level, this, 0.25f);
			HardenedSnowBallEntity.shoot(level, this, 0.4f);
			if (this.tickCount % 20 == 0)
				this.playSound(SoundEvents.ELYTRA_FLYING, (float) (Math.random() * 1.5) + 0.5f, 2);
			if (this.level.isClientSide)
				BigSnowGolemEntity.this.getPersistentData().putBoolean("shootState", true);
			this.bullets -= 1;
			} else if (this.level.isClientSide)
			this.getPersistentData().putBoolean("shootState", false);
			*/
		}
	}

	private ParticleOptions getParticle() {
		return (ParticleOptions) new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.ICE));
	}

	private int getCurrentSwingDuration() {
		if (MobEffectUtil.hasDigSpeed(this)) {
			return 9 - (1 + MobEffectUtil.getDigSpeedAmplification(this));
		} else {
			return this.hasEffect(MobEffects.DIG_SLOWDOWN) ? 9 + (1 + this.getEffect(MobEffects.DIG_SLOWDOWN).getAmplifier()) * 3 : 9;
		}
	}

	protected void updateSwingTime() {
		int i = this.getCurrentSwingDuration();
		if (this.swinging) {
			++this.swingTime;
			if (this.swingTime >= i) {
				this.swingTime = 0;
				this.swinging = false;
			}
		} else {
			this.swingTime = 0;
		}
		this.attackAnim = (float) this.swingTime / (float) i;
	}

	public boolean doHurtTarget(Entity entity) {
		if (!(entity instanceof LivingEntity)) {
			return false;
		} else {
			if (!this.level.isClientSide && !this.getMainHandItem().isEmpty()) {
				Item item1 = this.getMainHandItem().getItem();
				item1.hurtEnemy(this.getMainHandItem(), (LivingEntity) entity, this);
			}
		}
		boolean flag = entity.hurt(DamageSource.mobAttack(this), this.getAttackDamage() * 2f + entity.getTicksFrozen() / 140);
		if (!entity.isFullyFrozen())
			entity.setTicksFrozen(entity.getTicksFrozen() + 60);
		else {
			if (flag) {
				entity.push(this.getLookAngle().x, 1, this.getLookAngle().z);
				this.playSound(SoundEvents.GLASS_BREAK, 2, 0.5f);
				entity.setTicksFrozen(0);
				if (this.level instanceof ServerLevel _level)
					_level.sendParticles(getParticle(), entity.getX(), entity.getY() + entity.getEyeHeight(), entity.getZ(), 30, 0, 0, 0, 0.4f);
			}
			return flag;
		}
		return super.doHurtTarget(entity);
	}

	public boolean hurt(DamageSource source, float amount) {
		if (this.level instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY(), this.getZ(), 60, this.getBbWidth() / 3, this.getBbHeight() / 1.5f, this.getBbWidth() / 3, 0.3f);
		if (source.getDirectEntity() != null && this.isAlliedTo(source.getDirectEntity()))
			amount *= 0.3f;
		return super.hurt(source, amount);
	}

	private float getAttackDamage() {
		return (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
	}

	public int getTicksFrozen() {
		return 0;
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.powder_snow.step")), 1, 1);
	}

	@Override
	public SoundEvent getAmbientSound() {
		return SoundEvents.SNOW_GOLEM_AMBIENT;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource p_29929_) {
		return SoundEvents.SNOW_GOLEM_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.SNOW_GOLEM_DEATH;
	}

	@Override
	public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
		ItemStack itemstack = sourceentity.getItemInHand(hand);
		InteractionResult retval = InteractionResult.sidedSuccess(this.level.isClientSide());
		Item item = itemstack.getItem();
		if (itemstack.getItem() instanceof SpawnEggItem) {
			retval = super.mobInteract(sourceentity, hand);
		} else if (this.level.isClientSide()) {
			retval = (this.isTame() && this.isOwnedBy(sourceentity) || this.isFood(itemstack)) ? InteractionResult.sidedSuccess(this.level.isClientSide()) : InteractionResult.PASS;
		} else {
			if (this.isTame()) {
				if (this.isOwnedBy(sourceentity)) {
					if (itemstack.getItem() == Items.SNOWBALL && this.getHealth() < this.getMaxHealth()) {
						this.usePlayerItem(sourceentity, hand, itemstack);
						this.heal(3);
						retval = InteractionResult.sidedSuccess(this.level.isClientSide());
					} else {
						retval = super.mobInteract(sourceentity, hand);
					}
				}
			} else if (itemstack.getItem() == Blocks.CARVED_PUMPKIN.asItem()) {
				this.usePlayerItem(sourceentity, hand, itemstack);
				this.tame(sourceentity);
				this.level.broadcastEntityEvent(this, (byte) 7);
				this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Blocks.CARVED_PUMPKIN));
				retval = InteractionResult.sidedSuccess(this.level.isClientSide());
			} else {
				retval = super.mobInteract(sourceentity, hand);
				if (retval == InteractionResult.SUCCESS || retval == InteractionResult.CONSUME)
					this.setPersistenceRequired();
			}
		}
		return retval;
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
		BigSnowGolemEntity retval = CrimsonStevesMobsModEntities.BIG_SNOW_GOLEM.get().create(serverWorld);
		retval.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(retval.blockPosition()), MobSpawnType.BREEDING, null, null);
		return retval;
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return List.of().contains(stack.getItem());
	}

	public static void init() {
		SpawnPlacements.register(CrimsonStevesMobsModEntities.BIG_SNOW_GOLEM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos, random) -> (world.getBlockState(pos.below()).getMaterial() == Material.GRASS && world.getRawBrightness(pos, 0) > 8));
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.23);
		builder = builder.add(Attributes.MAX_HEALTH, 80);
		builder = builder.add(Attributes.FOLLOW_RANGE, 42);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		return builder;
	}

	public int getRemainingPersistentAngerTime() {
		return this.remainingPersistentAngerTime;
	}

	public void setRemainingPersistentAngerTime(int p_28859_) {
		this.remainingPersistentAngerTime = p_28859_;
	}

	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	public void setPersistentAngerTarget(@Nullable UUID p_28855_) {
		this.persistentAngerTarget = p_28855_;
	}

	@Nullable
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}
}
