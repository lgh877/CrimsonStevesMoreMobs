
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.MoveBackToVillageGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.GolemRandomStrollInVillageGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Difficulty;
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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.BlockPos;

import net.mcreator.crimson_steves_mobs.procedures.ChadVillagerNaturalEntitySpawningConditionProcedure;
import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.SmartBodyHelper2;
import net.mcreator.crimson_steves_mobs.SlowRotMoveControl;
import net.mcreator.crimson_steves_mobs.IThrowAbility;
import net.mcreator.crimson_steves_mobs.IProtectiveVehicle;
import net.mcreator.crimson_steves_mobs.CustomProtectVillagerGoal;
import net.mcreator.crimson_steves_mobs.CustomMathHelper;

import javax.annotation.Nullable;

import java.util.UUID;
import java.util.EnumSet;

public class RedstonePoweredIronGolemEntity extends AbstractGolem implements IThrowAbility, NeutralMob, IProtectiveVehicle {
	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(RedstonePoweredIronGolemEntity.class, EntityDataSerializers.BOOLEAN);
	private int remainingPersistentAngerTime;
	@Nullable
	private UUID persistentAngerTarget;
	private boolean wasOnGround;
	private int chargingTime;
	private int chargingActiveTime;
	private Vec3 posVector;
	private static int dist = 4;

	public void makeStuckInBlock(BlockState p_33796_, Vec3 p_33797_) {
		if (this.tickCount % 15 == 0)
			super.makeStuckInBlock(p_33796_, p_33797_);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ATTACKING, false);
	}

	public RedstonePoweredIronGolemEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.REDSTONE_POWERED_IRON_GOLEM.get(), world);
	}

	public RedstonePoweredIronGolemEntity(EntityType<RedstonePoweredIronGolemEntity> type, Level world) {
		super(type, world);
		xpReward = 25;
		this.maxUpStep = 2;
		this.moveControl = new SlowRotMoveControl(this);
		setPersistenceRequired();
	}

	@Override
	protected BodyRotationControl createBodyControl() {
		return new SmartBodyHelper2(this);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public boolean isAlliedTo(Entity entityIn) {
		if (this.getTeam() != null) {
			return super.isAlliedTo(entityIn);
		} else if (entityIn.getType() == this.getType())
			return true;
		else if (entityIn instanceof Mob mob) {
			if (!(entityIn instanceof Enemy) && mob.getTarget() != this)
				return true;
		}
		return super.isAlliedTo(entityIn);
	}

	public void setAttacking(boolean input) {
		this.entityData.set(ATTACKING, input);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new stopAndLookTargetGoal());
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) {
			protected void checkAndPerformAttack(LivingEntity p_25557_, double p_25558_) {
				double d0 = this.getAttackReachSqr(p_25557_) * 1.3;
				if (p_25558_ <= d0 && !RedstonePoweredIronGolemEntity.this.entityData.get(ATTACKING) && this.isTimeToAttack()) {
					this.resetAttackCooldown();
					RedstonePoweredIronGolemEntity.this.entityData.set(ATTACKING, true);
					/*
					this.mob.swing(InteractionHand.MAIN_HAND);
					this.mob.doHurtTarget(p_25557_);
					*/
				}
			}

			protected double getAttackReachSqr(LivingEntity entity) {
				if (!mob.swinging) {
					return CustomMathHelper.isEntityInBox(entity, mob, 2) ? Double.POSITIVE_INFINITY : super.getAttackReachSqr(entity);
				}
				return -1;
			}
		});
		this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9D, 32.0F));
		this.goalSelector.addGoal(2, new MoveBackToVillageGoal(this, 1D, false));
		this.goalSelector.addGoal(4, new GolemRandomStrollInVillageGoal(this, 0.6D));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new CustomProtectVillagerGoal(this, Villager.class));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false, (p_28879_) -> {
			return p_28879_ instanceof Enemy && !(p_28879_ instanceof Creeper);
		}));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, false));
	}

	class stopAndLookTargetGoal extends Goal {
		RedstonePoweredIronGolemEntity mob = RedstonePoweredIronGolemEntity.this;

		public stopAndLookTargetGoal() {
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
		}

		public boolean canUse() {
			return mob.isShiftKeyDown();
		}

		public boolean canContinueToUse() {
			return mob.isShiftKeyDown();
		}

		public void tick() {
			mob.getNavigation().stop();
			if (mob.getTarget() != null)
				mob.getLookControl().setLookAt(mob.getTarget(), 60.0F, 30.0F);
		}
	}

	protected int getMaxPassengers() {
		return 3;
	}

	public boolean rideableUnderWater() {
		return true;
	}

	protected boolean canAddPassenger(Entity p_38390_) {
		return this.getPassengers().size() < this.getMaxPassengers();
	}

	@Nullable
	public Entity getControllingPassenger() {
		return null;
	}

	public void positionRider(Entity entity) {
		int i = this.getPassengers().indexOf(entity);
		this.clampRotation(entity);
		this.positionRider(entity, Entity::setPos, i);
	}

	private void positionRider(Entity p_19957_, Entity.MoveFunction p_19958_, int index) {
		if (this.hasPassenger(p_19957_)) {
			float rot = this.isLeftHanded() ? -Mth.sin((((float) attackReadyAnimationAmount / 10) - this.attackAnim) * (float) Math.PI) * 60f : Mth.sin((((float) attackReadyAnimationAmount / 10) - this.attackAnim) * (float) Math.PI) * 60f;
			double d0;
			Vec3 vec32 = this.calculateViewVector(0, this.getYRot() * 0.7f + this.getYHeadRot() * 0.3f).scale((shiftDownAmount + 0.8f) * 0.6f);
			if (this.isShiftKeyDown()) {
				d0 = this.getY() - shiftDownAmount + this.getPassengersRidingOffset() + p_19957_.getMyRidingOffset();
			} else {
				d0 = this.getY() + this.getPassengersRidingOffset() + p_19957_.getMyRidingOffset();
			}
			Vec3 vec3 = this.calculateViewVector(0, this.getYRot() * 0.7f + rot + this.getYHeadRot() * 0.3f + 90)/* : this.calculateViewVector(0, this.getYRot() * 0.7f + this.getYHeadRot() * 0.3f + 90)*/;
			if (index == 0) {
				p_19958_.accept(p_19957_, this.getX() + vec32.x, d0, this.getZ() + vec32.z);
			} else if (index == 1) {
				p_19958_.accept(p_19957_, this.getX() + vec3.x + vec32.x, d0, this.getZ() + vec3.z + vec32.z);
			} else if (index == 2) {
				p_19958_.accept(p_19957_, this.getX() - vec3.x + vec32.x, d0, this.getZ() - vec3.z + vec32.z);
			}
		}
	}

	protected void clampRotation(Entity p_38322_) {
		p_38322_.setYBodyRot(this.getYRot());
		float f = Mth.wrapDegrees(p_38322_.getYRot() - this.getYRot());
		float f1 = Mth.clamp(f, -105.0F, 105.0F);
		p_38322_.yRotO += f1 - f;
		float f2 = p_38322_.getYRot() + f1 - f;
		p_38322_.setYRot(f2);
		float f3 = Mth.clamp(p_38322_.getYHeadRot(), f2 - 60.0F, f2 + 60.0F);
		p_38322_.setYHeadRot(f3);
	}

	public boolean canCollideWith(Entity entity) {
		if (!this.level.isClientSide && (entity instanceof Villager) && ((ServerLevel) this.level).isRaided(this.blockPosition())) {
			boolean flag = !(this.getControllingPassenger() instanceof Player);
			if (!entity.hasPassenger(this)) {
				if (flag && this.getPassengers().size() < this.getMaxPassengers() && !entity.isPassenger() && entity.getBbWidth() < this.getBbWidth() && entity instanceof LivingEntity && !(entity instanceof WaterAnimal)
						&& !(entity instanceof Player)) {
					entity.startRiding(this);
				} else {
					this.push(entity);
				}
			}
		}
		return super.canCollideWith(entity);
	}

	public void onPassengerTurned(Entity p_38383_) {
		this.clampRotation(p_38383_);
	}

	protected void customServerAiStep() {
		super.customServerAiStep();
		if (this.chargingActiveTime > 0) {
			this.chargingActiveTime--;
			if (this.level instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(), this.getZ(), 4, this.getBbWidth() / 2, this.getBbHeight() / 2, this.getBbWidth() / 2, 0);
			for (LivingEntity livingentity : this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2))) {
				if (((livingentity instanceof Enemy) && !this.isAlliedTo(livingentity)) || this.getTarget() == livingentity) {
					IThrowAbility.hurtAndThrowTargetHorizontallyCustom(this, livingentity, (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 2, this.getAttributeValue(Attributes.ATTACK_KNOCKBACK));
				}
			}
			if (!this.wasOnGround && this.onGround) {
				this.chargingActiveTime = 0;
			}
		}
		if (this.getTarget() != null && !this.isShiftKeyDown() && this.random.nextInt(100) == 0 && this.onGround) {
			this.setShiftKeyDown(true);
		}
		if (this.isShiftKeyDown()) {
			if (this.chargingTime < 30) {
				if (this.chargingTime % 5 == 0)
					this.playSound(SoundEvents.PISTON_EXTEND, 1, 1.5f);
				this.chargingTime++;
				if (this.level instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX(), this.getY(), this.getZ(), 4, this.getBbWidth() / 2, this.getBbHeight() / 2, this.getBbWidth() / 2, 0);
			} else {
				this.playSound(SoundEvents.GENERIC_EXPLODE, 2, 2);
				this.chargingTime = 0;
				this.chargingActiveTime = 20;
				this.setShiftKeyDown(false);
				LivingEntity entityIn = this;
				LivingEntity targetIn = this.getTarget();
				posVector = this.getLookAngle();
				if (targetIn != null) {
					posVector = new Vec3(targetIn.getX() - entityIn.getX(), targetIn.getY() - entityIn.getY(), targetIn.getZ() - entityIn.getZ());
				}
				posVector.normalize();
				//this.setDeltaMovement(posVector.x * dist * 0.07, posVector.y * dist * 0.05 + 0.5, posVector.z * dist * 0.07);
				this.setDeltaMovement(posVector.x * 0.28, posVector.y * 0.2 + 0.5, posVector.z * 0.28);
			}
		}
	}

	protected double getAttackReachSqr(LivingEntity entity) {
		return CustomMathHelper.isEntityInBox(entity, this, 1.5) ? Double.POSITIVE_INFINITY : (double) (this.getBbWidth() * 2.0F * this.getBbWidth() * 2.0F + entity.getBbWidth());
	}

	float shiftDownAmount;
	float attackReadyAnimationAmount;

	public void aiStep() {
		super.aiStep();
		this.updateSwingTime();
		//===============================
		shiftDownAmount = this.getPersistentData().getFloat("ShiftDownAmount");
		attackReadyAnimationAmount = this.getPersistentData().getFloat("attackReadyAnimationAmount");
		if (this.entityData.get(ATTACKING)) {
			attackReadyAnimationAmount++;
			if (attackReadyAnimationAmount > 9) {
				this.entityData.set(ATTACKING, false);
				this.swing(InteractionHand.MAIN_HAND);
				if (this.getTarget() != null) {
					LivingEntity target = this.getTarget();
					double d0 = this.getAttackReachSqr(target);
					if (this.distanceToSqr(target) <= d0) {
						this.doHurtTarget(target);
					} else {
						this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 0.5f);
						AABB attackrange = CustomMathHelper.makeAttackRange(this.getX() + this.getLookAngle().x, this.getY() + this.getEyeHeight() + this.getLookAngle().y, this.getZ() + this.getLookAngle().z, 0, 3, 3, 3);
						for (LivingEntity livingentity : this.level.getEntitiesOfClass(LivingEntity.class, attackrange)) {
							if (((livingentity instanceof Enemy) && !this.isAlliedTo(livingentity)) || this.getTarget() == livingentity) {
								IThrowAbility.hurtAndThrowTargetVerticallyCustom(this, livingentity, (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) / 3, this.getAttributeValue(Attributes.ATTACK_KNOCKBACK) / 2);
								livingentity.invulnerableTime = 0;
							}
						}
					}
				} else {
					this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 0.5f);
					AABB attackrange = CustomMathHelper.makeAttackRange(this.getX() + this.getLookAngle().x, this.getY() + this.getLookAngle().y, this.getZ() + this.getLookAngle().z, 0, 3, 3, 3);
					for (LivingEntity livingentity : this.level.getEntitiesOfClass(LivingEntity.class, attackrange)) {
						if (((livingentity instanceof Enemy) && !this.isAlliedTo(livingentity)) || this.getTarget() == livingentity) {
							IThrowAbility.hurtAndThrowTargetVerticallyCustom(this, livingentity, (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) / 3, this.getAttributeValue(Attributes.ATTACK_KNOCKBACK) / 2);
							livingentity.invulnerableTime = 0;
						}
					}
				}
			}
		} else {
			attackReadyAnimationAmount = 0;
		}
		if (this.isShiftKeyDown()) {
			shiftDownAmount = Mth.lerp(0.4f, shiftDownAmount, 1);
		} else if (shiftDownAmount > 0) {
			shiftDownAmount = Mth.lerp(0.2f, shiftDownAmount, 0);
		}
		if (shiftDownAmount != this.getPersistentData().getFloat("ShiftDownAmount")) {
			this.getPersistentData().putFloat("ShiftDownAmount", shiftDownAmount);
		}
		if (attackReadyAnimationAmount != this.getPersistentData().getFloat("attackReadyAnimationAmount")) {
			this.getPersistentData().putFloat("attackReadyAnimationAmount", attackReadyAnimationAmount);
		}
		//================================
		if (this.getDeltaMovement().horizontalDistanceSqr() > (double) 2.5000003E-7F && this.random.nextInt(5) == 0) {
			int i = Mth.floor(this.getX());
			int j = Mth.floor(this.getY() - (double) 0.2F);
			int k = Mth.floor(this.getZ());
			BlockPos pos = new BlockPos(i, j, k);
			BlockState blockstate = this.level.getBlockState(pos);
			if (!blockstate.isAir()) {
				this.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate).setPos(pos), this.getX() + ((double) this.random.nextFloat() - 0.5D) * (double) this.getBbWidth(), this.getY() + 0.1D,
						this.getZ() + ((double) this.random.nextFloat() - 0.5D) * (double) this.getBbWidth(), 4.0D * ((double) this.random.nextFloat() - 0.5D), 0.5D, ((double) this.random.nextFloat() - 0.5D) * 4.0D);
			}
		}
		if (this.chargingActiveTime == 0)
			if (this.getDeltaMovement().horizontalDistanceSqr() > (double) 2.5000003E-7F && this.random.nextInt(5) == 0) {
				int i = Mth.floor(this.getX());
				int j = Mth.floor(this.getY() - (double) 0.2F);
				int k = Mth.floor(this.getZ());
				BlockPos pos = new BlockPos(i, j, k);
				BlockState blockstate = this.level.getBlockState(pos);
				if (!blockstate.isAir()) {
					this.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate).setPos(pos), this.getX() + ((double) this.random.nextFloat() - 0.5D) * (double) this.getBbWidth(), this.getY() + 0.1D,
							this.getZ() + ((double) this.random.nextFloat() - 0.5D) * (double) this.getBbWidth(), 4.0D * ((double) this.random.nextFloat() - 0.5D), 0.5D, ((double) this.random.nextFloat() - 0.5D) * 4.0D);
				}
			}
		if (!this.level.isClientSide) {
			this.updatePersistentAnger((ServerLevel) this.level, true);
		}
		this.wasOnGround = this.onGround;
	}

	/*
	public class massivePounceGoal extends JumpGoal{
	public boolean canUse(){
		
	}
	}
	*/
	public void addAdditionalSaveData(CompoundTag p_28867_) {
		super.addAdditionalSaveData(p_28867_);
		this.addPersistentAngerSaveData(p_28867_);
		p_28867_.putShort("chargingTime", (short) this.chargingTime);
		p_28867_.putShort("chargingActiveTime", (short) this.chargingActiveTime);
	}

	public void readAdditionalSaveData(CompoundTag p_28857_) {
		super.readAdditionalSaveData(p_28857_);
		this.readPersistentAngerSaveData(this.level, p_28857_);
		this.chargingTime = p_28857_.getShort("chargingTime");
		this.chargingActiveTime = p_28857_.getShort("chargingActiveTime");
	}

	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	public void setRemainingPersistentAngerTime(int p_28859_) {
		this.remainingPersistentAngerTime = p_28859_;
	}

	public int getRemainingPersistentAngerTime() {
		return this.remainingPersistentAngerTime;
	}

	public void setPersistentAngerTarget(@Nullable UUID p_28855_) {
		this.persistentAngerTarget = p_28855_;
	}

	@Nullable
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

	public boolean canAttackType(EntityType<?> p_28851_) {
		return p_28851_ == EntityType.CREEPER ? false : super.canAttackType(p_28851_);
	}

	protected int decreaseAirSupply(int p_28882_) {
		return p_28882_;
	}

	protected void updateControlFlags() {
		//boolean flag1 = !(this.getVehicle() instanceof Boat);
		this.goalSelector.setControlFlag(Goal.Flag.MOVE, true);
		this.goalSelector.setControlFlag(Goal.Flag.JUMP, true);
		this.goalSelector.setControlFlag(Goal.Flag.LOOK, true);
		this.goalSelector.setControlFlag(Goal.Flag.TARGET, true);
	}

	protected void doPush(Entity p_28839_) {
		if (!this.isAlliedTo(p_28839_) && (p_28839_ instanceof Enemy) && !(p_28839_ instanceof Creeper) && this.getRandom().nextInt(20) == 0) {
			this.setTarget((LivingEntity) p_28839_);
		}
		super.doPush(p_28839_);
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

	public boolean doHurtTarget(Entity p_34491_) {
		this.playSound(SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, 1.0F, 0.5f);
		AABB attackrange = CustomMathHelper.makeAttackRange(p_34491_.getX(), p_34491_.getY(), p_34491_.getZ(), 0, 3, 3, 3);
		for (LivingEntity livingentity : this.level.getEntitiesOfClass(LivingEntity.class, attackrange)) {
			if (((livingentity instanceof Enemy) && !this.isAlliedTo(livingentity)) || this.getTarget() == livingentity) {
				IThrowAbility.hurtAndThrowTargetVerticallyCustom(this, livingentity, (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) / 3, this.getAttributeValue(Attributes.ATTACK_KNOCKBACK) / 2);
				livingentity.invulnerableTime = 0;
			}
		}
		if (!(p_34491_ instanceof LivingEntity)) {
			return false;
		} else {
			if (!this.level.isClientSide && !this.getMainHandItem().isEmpty()) {
				Item item1 = this.getMainHandItem().getItem();
				item1.hurtEnemy(this.getMainHandItem(), (LivingEntity) p_34491_, this);
			}
			if (this.random.nextInt(2) == 0)
				return IThrowAbility.hurtAndThrowTargetVertically(this, (LivingEntity) p_34491_);
			else
				return IThrowAbility.hurtAndThrowTargetHorizontally(this, (LivingEntity) p_34491_);
		}
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:redstonegolem_idle"));
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.step")), 0.3f, 0.5f);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:redstonegolem_hit"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:redstonegolem_death"));
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source == DamageSource.CACTUS)
			return false;
		return super.hurt(source, amount);
	}

	public static void init() {
		SpawnPlacements.register(CrimsonStevesMobsModEntities.REDSTONE_POWERED_IRON_GOLEM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos, random) -> {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			return ChadVillagerNaturalEntitySpawningConditionProcedure.execute(world, x, y, z);
			//
		});
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33601_, DifficultyInstance p_33602_, MobSpawnType p_33603_, @Nullable SpawnGroupData p_33604_, @Nullable CompoundTag p_33605_) {
		if (this.random.nextFloat() < (this.level.getDifficulty() == Difficulty.HARD ? 0.05F : 0.01F)) {
			int i = this.random.nextInt(3);
			if (i == 0) {
				this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
			} else {
				this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
			}
		}
		return super.finalizeSpawn(p_33601_, p_33602_, p_33603_, p_33604_, p_33605_);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 225);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		builder = builder.add(Attributes.ARMOR, 15);
		builder = builder.add(Attributes.ARMOR_TOUGHNESS, 15);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 8);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.8);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1.5);
		return builder;
	}
}
