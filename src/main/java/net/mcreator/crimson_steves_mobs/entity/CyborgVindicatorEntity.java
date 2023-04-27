
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Difficulty;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.SmartBodyHelper2;
import net.mcreator.crimson_steves_mobs.SlowRotMoveControl;
import net.mcreator.crimson_steves_mobs.IThrowAbility;
import net.mcreator.crimson_steves_mobs.IProtectiveVehicle;
import net.mcreator.crimson_steves_mobs.CustomMathHelper;

import javax.annotation.Nullable;

import java.util.Map;
import java.util.EnumSet;

import com.google.common.collect.Maps;

public class CyborgVindicatorEntity extends AbstractIllager implements IProtectiveVehicle {
	private static final EntityDataAccessor<Integer> ID_SIZE = SynchedEntityData.defineId(CyborgVindicatorEntity.class, EntityDataSerializers.INT);
	private boolean canRespawn;
	private static final EntityDataAccessor<Boolean> RAGE = SynchedEntityData.defineId(CyborgVindicatorEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(CyborgVindicatorEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> ISIMMORTAL = SynchedEntityData.defineId(CyborgVindicatorEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> READY_TO_RAGE = SynchedEntityData.defineId(CyborgVindicatorEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> READY_TO_RUSH = SynchedEntityData.defineId(CyborgVindicatorEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> SOUND_OCCURRED = SynchedEntityData.defineId(CyborgVindicatorEntity.class, EntityDataSerializers.BOOLEAN);
	private int respawnTicks;
	private int chargeRegenTicks;
	private boolean wasSpawned;
	private int ambientAnimTicks;

	public void makeStuckInBlock(BlockState p_33796_, Vec3 p_33797_) {
		if (this.tickCount % this.getSize() == 0)
			super.makeStuckInBlock(p_33796_, p_33797_);
	}

	public boolean isAlliedTo(Entity p_34110_) {
		if (this.getTeam() != null) {
			return super.isAlliedTo(p_34110_);
		} else if (p_34110_ instanceof LivingEntity && ((LivingEntity) p_34110_).getMobType() == MobType.ILLAGER) {
			return this.getTeam() == null && p_34110_.getTeam() == null;
		} else {
			return false;
		}
	}

	public CyborgVindicatorEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.CYBORG_VINDICATOR.get(), world);
	}

	public boolean isRaged() {
		return this.entityData.get(RAGE);
	}

	public CyborgVindicatorEntity(EntityType<CyborgVindicatorEntity> type, Level world) {
		super(type, world);
		this.refreshDimensions();
		setPersistenceRequired();
		this.moveControl = new SlowRotMoveControl(this);
		if (!this.wasSpawned) {
			this.setSize(this.getSize(), true);
			this.wasSpawned = true;
			this.canRespawn = true;
			this.respawnTicks = 100;
		}
	}

	@Override
	protected BodyRotationControl createBodyControl() {
		return new SmartBodyHelper2(this);
	}

	protected float getSoundVolume() {
		return super.getSoundVolume() + this.getSize() / 10;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ID_SIZE, 1);
		this.entityData.define(RAGE, false);
		this.entityData.define(ATTACKING, false);
		this.entityData.define(ISIMMORTAL, false);
		this.entityData.define(READY_TO_RAGE, false);
		this.entityData.define(READY_TO_RUSH, false);
		this.entityData.define(SOUND_OCCURRED, false);
	}

	public void addAdditionalSaveData(CompoundTag p_33619_) {
		super.addAdditionalSaveData(p_33619_);
		p_33619_.putInt("Size", this.getSize() - 1);
		p_33619_.putBoolean("canRespawn", this.canRespawn);
		p_33619_.putBoolean("isImmortal", this.entityData.get(ISIMMORTAL));
		p_33619_.putBoolean("wasSpawned", this.wasSpawned);
		p_33619_.putBoolean("isRaged", this.entityData.get(RAGE));
		p_33619_.putBoolean("readyRage", this.entityData.get(READY_TO_RAGE));
		p_33619_.putInt("respawnTicks", this.respawnTicks);
	}

	public void readAdditionalSaveData(CompoundTag p_33607_) {
		super.readAdditionalSaveData(p_33607_);
		this.entityData.set(RAGE, p_33607_.getBoolean("isRaged"));
		this.entityData.set(READY_TO_RAGE, p_33607_.getBoolean("readyRage"));
		this.entityData.set(ISIMMORTAL, p_33607_.getBoolean("isImmortal"));
		this.canRespawn = p_33607_.getBoolean("canRespawn");
		this.wasSpawned = p_33607_.getBoolean("wasSpawned");
		this.respawnTicks = p_33607_.getInt("respawnTicks");
		this.setSize(p_33607_.getInt("Size") + 1, !this.wasSpawned);
		if (!this.wasSpawned) {
			this.wasSpawned = true;
			this.canRespawn = true;
			this.respawnTicks = 100;
		}
	}

	public int getSize() {
		return this.entityData.get(ID_SIZE);
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new rushAttackGoal(this));
		this.goalSelector.addGoal(0, new stopAndLookTargetGoal());
		this.goalSelector.addGoal(2, new FloatGoal(this));
		this.targetSelector.addGoal(2, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
		this.goalSelector.addGoal(3, new Raider.HoldGroundAttackGoal(this, 10.0F));
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.2, false) {
			protected void checkAndPerformAttack(LivingEntity p_25557_, double p_25558_) {
				double d0 = this.getAttackReachSqr(p_25557_) * 1.3;
				if (p_25558_ <= d0 && !CyborgVindicatorEntity.this.entityData.get(ATTACKING)) {
					mob.lookAt(EntityAnchorArgument.Anchor.FEET, p_25557_.position());
					if (CyborgVindicatorEntity.this.entityData.get(RAGE)) {
						this.mob.swing(InteractionHand.MAIN_HAND);
						this.mob.doHurtTarget(p_25557_);
					} else {
						CyborgVindicatorEntity.this.entityData.set(ATTACKING, true);
					}
					/*
					this.mob.swing(InteractionHand.MAIN_HAND);
					this.mob.doHurtTarget(p_25557_);
					*/
				}
			}

			protected double getAttackReachSqr(LivingEntity entity) {
				if (!mob.swinging) {
					return CustomMathHelper.isEntityInBox(entity, mob, 1.3) ? Double.POSITIVE_INFINITY : mob.getBbWidth() * mob.getBbWidth() + entity.getBbWidth() * entity.getBbWidth();
				}
				return -1;
			}
		});
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1D));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
	}

	protected double getAttackReachSqr(LivingEntity entity) {
		return CustomMathHelper.isEntityInBox(entity, this, 1) ? Double.POSITIVE_INFINITY : (double) (this.getBbWidth() * this.getBbWidth() + entity.getBbWidth() * entity.getBbWidth());
	}

	class rushAttackGoal extends Goal {
		final CyborgVindicatorEntity mob;
		boolean wasUsed;
		int skillTicks;
		final RandomSource random = RandomSource.create();
		Vec3 vec31;
		Vec3 vec32;
		float movespeed;

		public rushAttackGoal(CyborgVindicatorEntity mob) {
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
			this.mob = mob;
		}

		public boolean canUse() {
			if (skillTicks > 10 && mob.isAlive() && !mob.isShiftKeyDown() && !mob.entityData.get(RAGE))
				return true;
			if (!mob.isAlive() || mob.isShiftKeyDown() || mob.horizontalCollision || mob.entityData.get(RAGE)) {
				return false;
			}
			if (skillTicks > 0)
				return true;
			return mob.getTarget() != null && random.nextInt(wasUsed ? 160 : 50) == 0;
		}

		public void start() {
			mob.getNavigation().stop();
			vec31 = mob.getTarget().position();
			skillTicks = 30;
			wasUsed = true;
			mob.entityData.set(READY_TO_RUSH, true);
		}

		public void tick() {
			skillTicks--;
			movespeed = (float) mob.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
			//movespeed = (float) mob.getAttributeBaseValue(Attributes.MOVEMENT_SPEED);
			if (skillTicks > 10) {
				if (mob.getTarget() != null)
					vec31 = mob.getTarget().position();
				mob.lookAt(EntityAnchorArgument.Anchor.FEET, vec31);
				vec32 = new Vec3(vec31.x - mob.getX(), 0, vec31.z - mob.getZ()).normalize();
				//vec32.scale(movespeed * 1000);
				//vec32.scale(Mth.sqrt(mob.getSize()) * 4);
			} else {
				//vec31.add(vec32);
				for (LivingEntity livingentity : mob.level.getEntitiesOfClass(LivingEntity.class, mob.getBoundingBox().inflate(0.5))) {
					if (!mob.isAlliedTo(livingentity)) {
						IThrowAbility.hurtAndThrowTargetVerticallyCustom(mob, livingentity, (float) (mob.getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.3), mob.getAttributeValue(Attributes.ATTACK_KNOCKBACK) / 2);
					}
				}
				/*
				mob.yBodyRot = (float) (Math.asin(vec32.x) + Math.acos(vec32.z));
				mob.yBodyRotO = (float) (Math.asin(vec32.x) + Math.acos(vec32.z));
				*/
				mob.setSprinting(true);
				if (!mob.level.isClientSide)
					mob.setSprinting(true);
				/*
				mob.xxa = 0;
				mob.yya = 0;
				mob.zza = movespeed * 15;
				*/
				//mob.moveRelative(0.98f, vec32.scale(movespeed * 15));
				mob.setDeltaMovement(vec32.x * movespeed * 4, -mob.getAttributeValue(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get()), vec32.z * movespeed * 4);
				if (!mob.isOnGround())
					mob.setDeltaMovement(mob.getDeltaMovement().scale(0.9));
			}
			//mob.getLookControl().setLookAt(vec31.x, vec31.y, vec31.z, 45, 45);
		}

		public void stop() {
			movespeed = (float) mob.getAttributeValue(Attributes.MOVEMENT_SPEED);
			if (skillTicks <= 10 && !mob.entityData.get(RAGE))
				if (!mob.level.isClientSide) {
					Explosion.BlockInteraction explosion$blockinteraction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(mob.level, mob) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
					for (int i = 0; i < 3; i++)
						mob.level.explode(mob, mob.getX() + mob.getLookAngle().x * i * movespeed * 10, mob.getY() + mob.getBbHeight() * 0.6 + mob.getLookAngle().y * i * movespeed * 10, mob.getZ() + mob.getLookAngle().z * i * movespeed * 10,
								movespeed * 8, explosion$blockinteraction);
				}
			mob.setSprinting(false);
			if (!mob.level.isClientSide)
				mob.setSprinting(false);
			mob.entityData.set(READY_TO_RUSH, false);
			skillTicks = 0;
		}
	}

	public void StandingWalkingAnimation(LivingEntity p_21044_) {
		p_21044_.animationSpeedOld = p_21044_.animationSpeed;
		p_21044_.animationSpeed += 1;
		p_21044_.animationPosition += p_21044_.animationSpeed;
	}

	public void remove(Entity.RemovalReason p_149847_) {
		if (!this.entityData.get(ISIMMORTAL))
			super.remove(p_149847_);
	}

	class stopAndLookTargetGoal extends Goal {
		CyborgVindicatorEntity mob = CyborgVindicatorEntity.this;

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
		}
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33601_, DifficultyInstance p_33602_, MobSpawnType p_33603_, @Nullable SpawnGroupData p_33604_, @Nullable CompoundTag p_33605_) {
		int i = this.random.nextInt(10) + 1;
		this.setSize(i, true);
		/*
		this.canRespawn = true;
		this.respawnTicks = 100;
		*/
		this.populateDefaultEquipmentSlots(p_33602_);
		this.populateDefaultEquipmentEnchantments(this.random, p_33602_);
		return super.finalizeSpawn(p_33601_, p_33602_, p_33603_, p_33604_, p_33605_);
	}

	protected void populateDefaultEquipmentSlots(DifficultyInstance p_34084_) {
		if (this.getCurrentRaid() == null) {
			this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
		}
	}

	protected PathNavigation createNavigation(Level p_33348_) {
		if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(p_33348_, this))
			return new TRabusEntity.RavagerNavigation(this, p_33348_);
		else
			return new GroundPathNavigation(this, p_33348_);
	}

	public AbstractIllager.IllagerArmPose getArmPose() {
		if (this.entityData.get(RAGE) || this.entityData.get(ISIMMORTAL)) {
			return AbstractIllager.IllagerArmPose.ATTACKING;
		} else {
			return this.isCelebrating() || this.isSprinting() ? AbstractIllager.IllagerArmPose.CELEBRATING : AbstractIllager.IllagerArmPose.NEUTRAL;
		}
	}

	protected void blockedByShield(LivingEntity p_33361_) {
		if (!this.entityData.get(READY_TO_RUSH)) {
			this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2, 1), p_33361_);
		}
	}

	protected void customServerAiStep() {
		this.chargeRegenTicks++;
		if (this.isAlive() && !this.entityData.get(RAGE)) {
			if (this.chargeRegenTicks > 70) {
				Mth.clamp(this.respawnTicks + 10, 0, 100);
				this.chargeRegenTicks = 0;
				if (this.isShiftKeyDown() && this.getHealth() > this.getMaxHealth() * 0.8f) {
					this.playSound(SoundEvents.VINDICATOR_CELEBRATE, 1, 0.5f);
					this.setShiftKeyDown(false);
					this.canRespawn = true;
				} else if (!this.isShiftKeyDown())
					this.canRespawn = true;
			}
			if (this.canRespawn && this.getHealth() < this.getMaxHealth() / 3 && this.respawnTicks > 0) {
				this.canRespawn = false;
				this.setShiftKeyDown(true);
			}
			if (!this.canRespawn && this.respawnTicks > 0) {
				this.respawnTicks--;
				if (this.level instanceof ServerLevel _level) {
					_level.sendParticles(ParticleTypes.LAVA, this.getX(), this.getY() + this.getBbHeight() / 3, this.getZ(), 2, this.getBbWidth() / 3, this.getBbHeight() / 6, this.getBbWidth() / 3, 0);
				}
				if (this.respawnTicks % 10 == 0) {
					if (this.level instanceof ServerLevel _level) {
						_level.sendParticles(ParticleTypes.HAPPY_VILLAGER, this.getX(), this.getY() + this.getBbHeight() / 3, this.getZ(), 30, this.getBbWidth() / 3, this.getBbHeight() / 3, this.getBbWidth() / 3, 0);
					}
					this.playSound(SoundEvents.BLAZE_SHOOT, 1, 0.5f);
					this.playSound(SoundEvents.BLASTFURNACE_FIRE_CRACKLE, 2, 0.5f);
					this.heal(this.getMaxHealth() / 10);
				}
				if (this.respawnTicks == 0) {
					//if (this.level.isClientSide)
					this.setShiftKeyDown(false);
					this.playSound(SoundEvents.VINDICATOR_CELEBRATE, 1, 0.5f);
				}
			}
		}
	}

	public void setCustomName(@Nullable Component p_34096_) {
		super.setCustomName(p_34096_);
		if (!this.entityData.get(ISIMMORTAL) && p_34096_ != null && p_34096_.getString().equals("IMMORTAL")) {
			this.entityData.set(ISIMMORTAL, true);
		} else if (this.entityData.get(ISIMMORTAL) && p_34096_ != null && !p_34096_.getString().equals("IMMORTAL")) {
			this.entityData.set(ISIMMORTAL, false);
		}
	}

	public void aiStep() {
		super.aiStep();
		//===============================
		float attackReadyAnimationAmount = this.getPersistentData().getFloat("attackReadyAnimationAmount");
		float shiftDownAmount = this.getPersistentData().getFloat("ShiftDownAmount");
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
					}
				} else {
					this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 0.5f);
				}
			}
		} else {
			attackReadyAnimationAmount = 0;
		}
		if (this.isShiftKeyDown()) {
			shiftDownAmount = Mth.lerp(0.2f, shiftDownAmount, 1);
		} else if (shiftDownAmount > 0) {
			shiftDownAmount = Mth.lerp(0.2f, shiftDownAmount, 0);
		}
		if (shiftDownAmount != this.getPersistentData().getFloat("ShiftDownAmount")) {
			this.getPersistentData().putFloat("ShiftDownAmount", shiftDownAmount);
		}
		if (attackReadyAnimationAmount != this.getPersistentData().getFloat("attackReadyAnimationAmount")) {
			this.getPersistentData().putFloat("attackReadyAnimationAmount", attackReadyAnimationAmount);
		}
		//===============================
		/*
		if (this.entityData.get(SOUND_OCCURRED)) {
			super.playAmbientSound();
			this.entityData.set(SOUND_OCCURRED, false);
		}
		*/
		if (this.entityData.get(READY_TO_RUSH))
			this.StandingWalkingAnimation(this);
		if (this.isAlive()) {
			this.deathTime = 0;
		}
		if (this.entityData.get(RAGE)) {
			if (this.level instanceof ServerLevel _level) {
				_level.sendParticles(ParticleTypes.LAVA, this.getX(), this.getY() + this.getBbHeight() / 3, this.getZ(), 3, this.getBbWidth() / 3, this.getBbHeight() / 6, this.getBbWidth() / 3, 0);
			}
			if (this.chargeRegenTicks % 20 == 0) {
				this.markHurt();
				this.hurtTime = this.hurtDuration;
				this.hurtDir = 0.0F;
				this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 2));
				if (this.isAlive()) {
					this.playSound(SoundEvents.FIRE_EXTINGUISH, 1, 0.5f);
					this.hurt(DamageSource.indirectMobAttack(this.getLastHurtByMob(), null).bypassArmor(), this.getMaxHealth() / 11);
					this.invulnerableTime = 0;
				}
			}
		}
		if (this.horizontalCollision && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this)) {
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

	public void tick() {
		super.tick();
		if (this.isDeadOrDying())
			this.dead = true;
	}

	public boolean canJoinRaid() {
		return !this.entityData.get(ISIMMORTAL);
	}

	protected void tickDeath() {
		this.setSprinting(false);
		this.setShiftKeyDown(false);
		if (!this.level.isClientSide()) {
			this.setShiftKeyDown(false);
			this.setSprinting(false);
		}
		if (this.entityData.get(READY_TO_RAGE)) {
			if (this.level instanceof ServerLevel _level) {
				_level.sendParticles(ParticleTypes.LAVA, this.getX(), this.getY() + this.getBbHeight() / 3, this.getZ(), 3, this.getBbWidth() / 3, this.getBbHeight() / 6, this.getBbWidth() / 3, 0);
			}
			if (--this.deathTime == 0) {
				if (!this.level.isClientSide()) {
					this.setHealth(this.getMaxHealth());
					if (this.entityData.get(ISIMMORTAL)) {
						this.canRespawn = true;
						this.respawnTicks = 100;
					}
				}
				this.entityData.set(READY_TO_RAGE, false);
				if (!this.entityData.get(ISIMMORTAL))
					this.entityData.set(RAGE, true);
			}
		} else if (this.deathTime == 18 && !this.entityData.get(RAGE)) {
			this.dead = false;
			this.entityData.set(READY_TO_RAGE, true);
		} else
			super.tickDeath();
	}

	private int getCurrentSwingDuration() {
		if (MobEffectUtil.hasDigSpeed(this)) {
			return 8 - (1 + MobEffectUtil.getDigSpeedAmplification(this));
		} else {
			return this.hasEffect(MobEffects.DIG_SLOWDOWN) ? 8 + (1 + this.getEffect(MobEffects.DIG_SLOWDOWN).getAmplifier()) * 3 : 8;
		}
	}

	protected void updateSwingTime() {
		int i = this.getCurrentSwingDuration();
		if (!this.entityData.get(RAGE))
			i *= (int) Math.pow(this.getSize(), 0.5);
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

	public boolean doHurtTarget(Entity p_33328_) {
		if (this.entityData.get(RAGE))
			p_33328_.invulnerableTime *= 0.7;
		this.playSound(SoundEvents.HOSTILE_BIG_FALL, 1.0F, 0.5F);
		if (!(p_33328_ instanceof LivingEntity)) {
			return false;
		} else {
			if (!this.level.isClientSide && !this.getMainHandItem().isEmpty()) {
				Item item1 = this.getMainHandItem().getItem();
				item1.hurtEnemy(this.getMainHandItem(), (LivingEntity) p_33328_, this);
			}
		}
		return super.doHurtTarget(p_33328_);
	}

	protected void updateControlFlags() {
		//boolean flag = this.isAlliedTo();
		boolean flag1 = !(this.getVehicle() instanceof Boat);
		this.goalSelector.setControlFlag(Goal.Flag.MOVE, true);
		this.goalSelector.setControlFlag(Goal.Flag.JUMP, flag1);
		this.goalSelector.setControlFlag(Goal.Flag.LOOK, true);
	}

	public boolean hurt(DamageSource source, float amount) {
		if (source.getEntity() instanceof Mob _lve) {
			if (_lve.getTarget() == this && this.getTarget() != null ? this.distanceTo(_lve) < this.distanceTo(this.getTarget()) : false)
				this.setTarget(_lve);
		}
		return super.hurt(source, amount);
	}

	public void setSize(int p_33594_, boolean p_33595_) {
		int i = Mth.clamp(p_33594_, 1, 127);
		this.entityData.set(ID_SIZE, i);
		this.maxUpStep = 1 + (float) (Math.pow(i, 0.5) * 0.3);
		this.reapplyPosition();
		this.refreshDimensions();
		this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(30 + i);
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double) (44 + Math.pow(i, 1.2) * 5));
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double) (0.22 + 0.03 * Math.pow(i, 0.5)));
		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(Math.pow(i, 0.65) * 0.8 + 3);
		if (p_33595_) {
			this.setHealth(this.getMaxHealth());
		}
		this.xpReward = 15 + i * 5;
	}

	public float getScale() {
		return 0.4f + (float) this.getSize() / 10;
	}

	public void playAmbientSound() {
		super.playAmbientSound();
		this.level.broadcastEntityEvent(this, (byte) 10);
	}

	public void handleEntityEvent(byte p_29814_) {
		if (p_29814_ == 10) {
			this.ambientSoundTime = -this.getAmbientSoundInterval();
		} else {
			super.handleEntityEvent(p_29814_);
		}
	}

	public void onSyncedDataUpdated(EntityDataAccessor<?> p_33609_) {
		if (ID_SIZE.equals(p_33609_)) {
			this.refreshDimensions();
			this.setYRot(this.yHeadRot);
			this.yBodyRot = this.yHeadRot;
			if (this.isInWater() && this.random.nextInt(20) == 0) {
				this.doWaterSplashEffect();
			}
		}
		super.onSyncedDataUpdated(p_33609_);
	}

	@Override
	public MobType getMobType() {
		return MobType.ILLAGER;
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	public float getVoicePitch() {
		return 0.5f;
	}

	public SoundEvent getCelebrateSound() {
		return SoundEvents.VINDICATOR_CELEBRATE;
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ravager.step")), 0.15f, 1);
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.VINDICATOR_AMBIENT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.VINDICATOR_DEATH;
	}

	protected SoundEvent getHurtSound(DamageSource p_34103_) {
		return SoundEvents.VINDICATOR_HURT;
	}

	public static void init() {
		SpawnPlacements.register(CrimsonStevesMobsModEntities.CYBORG_VINDICATOR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
	}

	public void applyRaidBuffs(int p_34079_, boolean p_34080_) {
		this.setSize(this.getSize() + p_34079_, true);
		ItemStack itemstack = new ItemStack(Items.IRON_AXE);
		Raid raid = this.getCurrentRaid();
		int i = 1;
		if (p_34079_ > raid.getNumGroups(Difficulty.NORMAL)) {
			i = 2;
		}
		boolean flag = this.random.nextFloat() <= raid.getEnchantOdds();
		if (flag) {
			Map<Enchantment, Integer> map = Maps.newHashMap();
			map.put(Enchantments.SHARPNESS, i);
			EnchantmentHelper.setEnchantments(map, itemstack);
		}
		this.setItemSlot(EquipmentSlot.MAINHAND, itemstack);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
		builder = builder.add(Attributes.MAX_HEALTH, 44);
		builder = builder.add(Attributes.FOLLOW_RANGE, 16);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.6);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 2);
		return builder;
	}
}
