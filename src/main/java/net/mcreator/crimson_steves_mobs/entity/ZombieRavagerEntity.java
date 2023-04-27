
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Difficulty;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.SmartBodyHelper2;
import net.mcreator.crimson_steves_mobs.SlowRotMoveControl;
import net.mcreator.crimson_steves_mobs.IProtectiveVehicle;
import net.mcreator.crimson_steves_mobs.EmergableZombie;
import net.mcreator.crimson_steves_mobs.CustomMathHelper;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class ZombieRavagerEntity extends EmergableZombie implements IProtectiveVehicle {
	private int roarTick;
	private int stunnedTick;

	public ZombieRavagerEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.ZOMBIE_RAVAGER.get(), world);
	}

	public void makeStuckInBlock(BlockState p_33796_, Vec3 p_33797_) {
	}

	public ZombieRavagerEntity(EntityType<ZombieRavagerEntity> type, Level world) {
		super(type, world);
		this.maxUpStep = 1.0F;
		xpReward = 20;
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

	public ResourceLocation getDefaultLootTable() {
		return EntityType.ZOMBIE.getDefaultLootTable();
	}

	protected void updateControlFlags() {
		boolean flag = !(this.getControllingPassenger() instanceof EmergableZombie);
		boolean flag1 = !(this.getVehicle() instanceof Boat);
		this.goalSelector.setControlFlag(Goal.Flag.MOVE, flag);
		this.goalSelector.setControlFlag(Goal.Flag.JUMP, flag && flag1);
		this.goalSelector.setControlFlag(Goal.Flag.LOOK, flag);
		this.goalSelector.setControlFlag(Goal.Flag.TARGET, flag);
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

	public boolean isBaby() {
		return false;
	}

	public void addAdditionalSaveData(CompoundTag p_33353_) {
		super.addAdditionalSaveData(p_33353_);
		p_33353_.putInt("StunTick", this.stunnedTick);
		p_33353_.putInt("RoarTick", this.roarTick);
	}

	public void readAdditionalSaveData(CompoundTag p_33344_) {
		super.readAdditionalSaveData(p_33344_);
		this.stunnedTick = p_33344_.getInt("StunTick");
		this.roarTick = p_33344_.getInt("RoarTick");
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
	}

	protected void addBehaviourGoals() {
		this.goalSelector.addGoal(1, new ZombieAttackGoal(this, 1.0D, false) {
			protected double getAttackReachSqr(LivingEntity entity) {
				if (!mob.swinging) {
					if (CustomMathHelper.isEntityInBox(entity, mob, 0.75)) {
						return Double.POSITIVE_INFINITY;
					} else if (ZombieRavagerEntity.this.random.nextInt(100) == 0 && CustomMathHelper.isEntityInBox(entity, mob, 3)) {
						ZombieRavagerEntity.this.playSound(SoundEvents.RAVAGER_ROAR, 1, mob.getVoicePitch());
						ZombieRavagerEntity.this.roarTick = 20;
						return -1;
					}
				}
				return -1;
			}
		});
		this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0D, true, 4, this::canBreakDoors));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(ZombifiedPiglin.class));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	/*
	public void tick() {
		if (this.level.isClientSide()) {
			if (this.isMovingOnLand()) {
				this.walkAnimationState.startIfStopped(this.tickCount);
			} else {
				this.walkAnimationState.stop();
			}
		}
		super.tick();
	}
	*/
	public void aiStep() {
		super.aiStep();
		float shiftDownAmount = this.getPersistentData().getFloat("ShiftDownAmount");
		if (this.isShiftKeyDown()) {
			shiftDownAmount = Mth.lerp(0.4f, shiftDownAmount, 1);
		} else if (shiftDownAmount > 0) {
			shiftDownAmount = Mth.lerp(0.1f, shiftDownAmount, 0);
		}
		if (shiftDownAmount != this.getPersistentData().getFloat("ShiftDownAmount")) {
			this.getPersistentData().putFloat("ShiftDownAmount", shiftDownAmount);
		}
		if (this.isAlive()) {
			if (this.stunnedTick > 0) {
				--this.stunnedTick;
				this.stunEffect();
				if (this.stunnedTick == 0) {
					this.playSound(SoundEvents.RAVAGER_ROAR, this.getSoundVolume(), this.getVoicePitch());
					this.roarTick = 20;
				}
			}
			if (!this.level.isClientSide && this.roarTick > 0) {
				--this.roarTick;
				if (this.roarTick == 10) {
					this.roar();
					this.setShiftKeyDown(true);
				}
				if (this.roarTick == 0)
					this.setShiftKeyDown(false);
			}
		}
	}

	public void playAmbientSound() {
		if (this.random.nextInt(20) == 0) {
			this.playSound(SoundEvents.RAVAGER_ROAR, this.getSoundVolume(), this.getVoicePitch());
			this.roarTick = 20;
		} else
			super.playAmbientSound();
	}

	public boolean doHurtTarget(Entity entityIn) {
		this.playSound(SoundEvents.RAVAGER_ATTACK, this.getSoundVolume(), this.getVoicePitch());
		return super.doHurtTarget(entityIn);
	}

	protected void doPush(Entity p_28839_) {
		super.doPush(p_28839_);
		if (!this.level.isClientSide && p_28839_.getType() != this.getType() && !(p_28839_.isPassenger() || p_28839_.isVehicle()) && !(this.isPassenger() || this.isVehicle()) && this.isAlliedTo(p_28839_)
				&& (this.random.nextInt(60) == 0 || p_28839_ instanceof ZombieEvokerEntity))
			p_28839_.startRiding(this);
	}

	private float getAttackDamage() {
		return (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
	}

	protected boolean isImmobile() {
		return super.isImmobile() || this.stunnedTick > 0 || this.roarTick > 0;
	}

	protected boolean convertsInWater() {
		return false;
	}

	private void roar() {
		if (this.isAlive()) {
			for (LivingEntity livingentity : this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0D))) {
				if (!this.isAlliedTo(livingentity)) {
					livingentity.hurt(DamageSource.mobAttack(this), this.getAttackDamage() / 2);
					//this.strongShrink(livingentity);
				}
			}
			Vec3 vec3 = this.getBoundingBox().getCenter();
			if (this.level instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.POOF, vec3.x, vec3.y, vec3.z, 40, 0, 0, 0, 0.2);
			/*
			for (int i = 0; i < 40; ++i) {
				double d0 = this.random.nextGaussian() * 0.2D;
				double d1 = this.random.nextGaussian() * 0.2D;
				double d2 = this.random.nextGaussian() * 0.2D;
				this.level.addParticle(ParticleTypes.POOF, vec3.x, vec3.y, vec3.z, d0, d1, d2);
			}
			*/
			this.gameEvent(GameEvent.ENTITY_ROAR);
		}
	}

	private void stunEffect() {
		if (this.random.nextInt(6) == 0) {
			double d0 = this.getX() - (double) this.getBbWidth() * Math.sin((double) (this.yBodyRot * ((float) Math.PI / 180F))) + (this.random.nextDouble() * 0.6D - 0.3D);
			double d1 = this.getY() + (double) this.getBbHeight() - 0.3D;
			double d2 = this.getZ() + (double) this.getBbWidth() * Math.cos((double) (this.yBodyRot * ((float) Math.PI / 180F))) + (this.random.nextDouble() * 0.6D - 0.3D);
			this.level.addParticle(ParticleTypes.ENTITY_EFFECT, d0, d1, d2, 0.5D, 0.5D, 0.5D);
		}
	}

	public void strongShrink(Entity p_33340_) {
		double d0 = Math.pow(-p_33340_.getX() + this.getX(), 0.33);
		double d1 = Math.pow(-p_33340_.getZ() + this.getZ(), 0.33);
		double d3 = Math.pow(-p_33340_.getY() + this.getY(), 0.33);
		//p_33340_.setDeltaMovement(p_33340_.getDeltaMovement().add(d0, d3, d1));
		p_33340_.push(d0, d3, d1);
	}

	private void strongKnockback(Entity p_33340_) {
		double d0 = p_33340_.getX() - this.getX();
		double d1 = p_33340_.getZ() - this.getZ();
		double d2 = Math.max(d0 * d0 + d1 * d1, 0.001D);
		p_33340_.push(d0 / d2 * 4.0D, 0.2D, d1 / d2 * 4.0D);
	}

	protected void blockedByShield(LivingEntity p_33361_) {
		if (this.roarTick == 0) {
			if (this.random.nextDouble() < 0.5D) {
				this.stunnedTick = 40;
				this.playSound(SoundEvents.RAVAGER_STUNNED, this.getSoundVolume(), this.getVoicePitch());
				this.level.broadcastEntityEvent(this, (byte) 39);
				p_33361_.push(this);
			} else {
				this.strongKnockback(p_33361_);
			}
			p_33361_.hurtMarked = true;
		}
	}

	public void handleEntityEvent(byte p_33335_) {
		if (p_33335_ == 39) {
			this.stunnedTick = 40;
		} else if (p_33335_ == 40) {
			this.stunnedTick = 1;
		}
		super.handleEntityEvent(p_33335_);
	}

	@Override
	public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ravager.ambient"));
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ravager.step")), 0.15f, 1);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ravager.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ravager.death"));
	}

	public static void init() {
		SpawnPlacements.register(CrimsonStevesMobsModEntities.ZOMBIE_RAVAGER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos,
				random) -> (world.getDifficulty() != Difficulty.PEACEFUL && CustomMathHelper.isOverworld(world) && Monster.isDarkEnoughToSpawn(world, pos, random) && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)));
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_34297_, DifficultyInstance p_34298_, MobSpawnType p_34299_, @Nullable SpawnGroupData p_34300_, @Nullable CompoundTag p_34301_) {
		p_34300_ = super.finalizeSpawn(p_34297_, p_34298_, p_34299_, p_34300_, p_34301_);
		this.emergeTicks = 40;
		this.moveTo(this.getX(), this.getY() - 4, this.getZ());
		return p_34300_;
	}

	protected void populateDefaultEquipmentSlots(DifficultyInstance p_34286_) {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
		builder = builder.add(Attributes.MAX_HEALTH, 100);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 12);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
		return builder;
	}
}
