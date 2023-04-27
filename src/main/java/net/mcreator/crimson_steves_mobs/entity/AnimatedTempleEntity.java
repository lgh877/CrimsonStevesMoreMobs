
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.Difficulty;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.SmartBodyHelper2;
import net.mcreator.crimson_steves_mobs.SlowRotMoveControl;
import net.mcreator.crimson_steves_mobs.IThrowAbility;
import net.mcreator.crimson_steves_mobs.ICanBeAnimated;
import net.mcreator.crimson_steves_mobs.CustomMathHelper;

import java.util.EnumSet;

public class AnimatedTempleEntity extends Monster implements ICanBeAnimated {
	public AnimationState slamAnimationState = new AnimationState();
	public AnimationState leftSlamAnimationState = new AnimationState();
	public AnimationState rightSlamAnimationState = new AnimationState();
	public AnimationState insertArmsStartAnimationState = new AnimationState();
	public AnimationState insertArmsIdleAnimationState = new AnimationState();
	public AnimationState insertArmsStopAnimationState = new AnimationState();
	public AnimationState fastShootAnimationState = new AnimationState();
	public AnimationState leapAnimationState = new AnimationState();
	public AnimationState deathAnimationState = new AnimationState();
	private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(AnimatedTempleEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> SQUASH_AMOUNT = SynchedEntityData.defineId(AnimatedTempleEntity.class, EntityDataSerializers.FLOAT);

	public void makeStuckInBlock(BlockState p_33796_, Vec3 p_33797_) {
	}

	protected void updateControlFlags() {
		//boolean flag1 = !(this.getVehicle() instanceof Boat);
		this.goalSelector.setControlFlag(Goal.Flag.MOVE, true);
		this.goalSelector.setControlFlag(Goal.Flag.JUMP, true);
		this.goalSelector.setControlFlag(Goal.Flag.LOOK, true);
		this.goalSelector.setControlFlag(Goal.Flag.TARGET, true);
	}

	public AnimatedTempleEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.ANIMATED_TEMPLE.get(), world);
	}

	public AnimatedTempleEntity(EntityType<AnimatedTempleEntity> type, Level world) {
		super(type, world);
		xpReward = 400;
		maxUpStep = 2;
		this.moveControl = new SlowRotMoveControl(this);
	}

	@Override
	protected BodyRotationControl createBodyControl() {
		return new SmartBodyHelper2(this);
	}

	public float getYRot() {
		if (this.attackAnim > 0.2f)
			return this.yHeadRot;
		return super.getYRot();
	}

	public void setXRot(float input) {
		if (this.isDeadOrDying()) {
		} else {
			super.setXRot(input);
		}
	}

	public void setYRot(float input) {
		if (this.isDeadOrDying()) {
		} else {
			super.setYRot(input);
		}
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ATTACK_STATE, 0);
		this.entityData.define(SQUASH_AMOUNT, 0f);
	}

	public void setAttackState(int input) {
		this.entityData.set(ATTACK_STATE, input);
	}

	public AnimationState getAnimationState(String input) {
		if (input == "slam")
			return slamAnimationState;
		else if (input == "leftSlam")
			return leftSlamAnimationState;
		else if (input == "rightSlam")
			return rightSlamAnimationState;
		else if (input == "insertArmsStart")
			return insertArmsStartAnimationState;
		else if (input == "insertArmsIdle")
			return insertArmsIdleAnimationState;
		else if (input == "insertArmsStop")
			return insertArmsStopAnimationState;
		else if (input == "fastShoot")
			return fastShootAnimationState;
		else if (input == "leap")
			return leapAnimationState;
		else if (input == "death_type1")
			return deathAnimationState;
		return new AnimationState();
	}

	public void onSyncedDataUpdated(EntityDataAccessor<?> p_219422_) {
		if (ATTACK_STATE.equals(p_219422_)) {
			if (this.level.isClientSide)
				switch (this.entityData.get(ATTACK_STATE)) {
					case 0 :
						break;
					case 1 :
						this.slamAnimationState.start(this.tickCount);
						break;
					case 2 :
						this.leftSlamAnimationState.start(this.tickCount);
						break;
					case 3 :
						this.rightSlamAnimationState.start(this.tickCount);
						break;
					case 4 :
						this.insertArmsStartAnimationState.start(this.tickCount);
						break;
					case 5 :
						this.insertArmsStartAnimationState.stop();
						this.insertArmsIdleAnimationState.start(this.tickCount);
						break;
					case 6 :
						this.insertArmsIdleAnimationState.stop();
						this.insertArmsStopAnimationState.start(this.tickCount);
						break;
					case 7 :
						this.fastShootAnimationState.start(this.tickCount);
						break;
					case 8 :
						this.insertArmsStartAnimationState.start(this.tickCount);
						break;
					case 9 :
						this.insertArmsStartAnimationState.stop();
						this.leapAnimationState.start(this.tickCount);
						break;
				}
		}
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public float squish;
	public float oSquish;

	public boolean causeFallDamage(float distance, float p_148712_, DamageSource p_148713_) {
		this.entityData.set(SQUASH_AMOUNT, Mth.clamp(distance, 0, 10));
		if (distance > 4) {
			this.playSound(SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR, 2f, 0.5f);
			this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:rock_smash")), 2f, 0.5f);
			for (int i = 0; i < 18; i++)
				EarthQuakeEntity.shoot(this.level, this, (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.4f, 4, (int) (Mth.square(distance) * 0.5f), 1, 20 * i);
		}
		if (distance > 16)
			return super.causeFallDamage(distance - 16, p_148712_, p_148713_);
		else
			return true;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		//this.goalSelector.addGoal(0, new lockAngle(this));
		this.goalSelector.addGoal(0, new slamAttackGoal(this));
		this.goalSelector.addGoal(0, new singleSlamAttackGoal(this));
		this.goalSelector.addGoal(0, new insertArmsGoal(this));
		this.goalSelector.addGoal(0, new massiveJumpGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, false) {
			protected void checkAndPerformAttack(LivingEntity p_25557_, double p_25558_) {
				double d0 = this.getAttackReachSqr(p_25557_);
				if (!mob.swinging && (p_25558_ <= d0 || CustomMathHelper.isEntityInBox(p_25557_, mob, 4))) {
					this.mob.swing(InteractionHand.MAIN_HAND);
					this.mob.doHurtTarget(p_25557_);
				}
			}
		});
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, LivingEntity.class, false, false));
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(6, new FloatGoal(this));
	}

	class slamAttackGoal extends LockAngleGoal {
		int attackprogress;
		Vec3 vec31;

		public slamAttackGoal(AnimatedTempleEntity mob) {
			super(mob);
		}

		public boolean canUse() {
			return super.canUse() && this.mob.entityData.get(ATTACK_STATE) == 1;
		}

		public void start() {
			super.start();
			attackprogress = 0;
		}

		public void tick() {
			if (attackprogress == 0) {
				attackprogress++;
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:redstonegolem_prepare_attack")), 2f, 0.7f);
			} else if (mob.attackAnim > 0.26f && attackprogress == 1) {
				attackprogress++;
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:rock_match")), 1f, 0.5f);
			} else if (mob.attackAnim > 0.54f && attackprogress == 2) {
				attackprogress++;
				mob.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 2f, 0.5f);
			} else if (mob.attackAnim > 0.58f && attackprogress == 3) {
				attackprogress++;
				for (int i = 0; i < 2; i++) {
					AABB attackrange = CustomMathHelper.makeAttackRange(mob.getX() + mob.getHorizontalLookAngle().x * 2 + (mob.getHorizontalLeftLookAngle().x) * (1 - i * 2) * 3.3, mob.getY(),
							mob.getZ() + mob.getHorizontalLookAngle().z * 2 + (mob.getHorizontalLeftLookAngle().z) * (1 - i * 2) * 3.3, 5, 4, 6, 4);
					for (LivingEntity livingentity : mob.level.getEntitiesOfClass(LivingEntity.class, attackrange)) {
						if (livingentity != mob) {
							if (livingentity != mob) {
								if (livingentity.hurt(DamageSource.mobAttack(mob), (float) (mob.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5)))
									livingentity.push(0, -2, 0);
							}
						}
					}
				}
			} else if (mob.attackAnim > 0.6f && attackprogress == 4) {
				attackprogress++;
				vec31 = new Vec3(mob.getX() + mob.getHorizontalLookAngle().x * 4, mob.getY(), mob.getZ() + mob.getHorizontalLookAngle().z * 4);
				EarthQuakeEntity.shoot(mob.level, mob, (float) mob.getAttributeValue(Attributes.ATTACK_DAMAGE), 8, 15, 3, mob.getYRot(), vec31);
				mob.playSound(SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR, 2f, 0.5f);
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:rock_smash")), 1f, 0.5f);
				AABB attackrange = CustomMathHelper.makeAttackRange(mob.getX() + mob.getHorizontalLookAngle().x * 2, mob.getY(), mob.getZ() + mob.getHorizontalLookAngle().z * 2, 0, 7, 5, 7);
				for (LivingEntity livingentity : mob.level.getEntitiesOfClass(LivingEntity.class, attackrange)) {
					if (livingentity != mob) {
						livingentity.invulnerableTime = 0;
						IThrowAbility.hurtAndThrowTargetVerticallyCustom(mob, livingentity, (float) (mob.getAttributeValue(Attributes.ATTACK_DAMAGE) * 2), (int) (mob.getAttributeValue(Attributes.ATTACK_KNOCKBACK) * 0.7));
						//mob.defaultAttack(livingentity);
					}
				}
			}
		}
	}

	class singleSlamAttackGoal extends LockAngleGoal {
		int attackprogress;
		Vec3 vec31;
		int as1;

		public singleSlamAttackGoal(AnimatedTempleEntity mob) {
			super(mob);
		}

		public boolean canUse() {
			int as = this.mob.entityData.get(ATTACK_STATE);
			return super.canUse() && (as == 2 || as == 3);
		}

		public void start() {
			super.start();
			attackprogress = 0;
			as1 = (this.mob.entityData.get(ATTACK_STATE) - 2) * 2;
		}

		public void tick() {
			if (attackprogress == 0) {
				attackprogress++;
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:redstonegolem_prepare_attack")), 2f, 1.2f);
			} else if (mob.attackAnim > 0.24f && attackprogress == 1) {
				attackprogress++;
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:rock_match")), 1f, 0.5f);
			} else if (mob.attackAnim > 0.5f && attackprogress == 2) {
				attackprogress++;
				mob.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 2f, 0.5f);
			} else if (mob.attackAnim > 0.56f && attackprogress == 3) {
				attackprogress++;
				AABB attackrange = CustomMathHelper.makeAttackRange(mob.getX() + mob.getHorizontalLookAngle().x * 2 + (mob.getHorizontalLeftLookAngle().x) * (1 - as1) * 3.3, mob.getY(),
						mob.getZ() + mob.getHorizontalLookAngle().z * 2 + (mob.getHorizontalLeftLookAngle().z) * (1 - as1) * 3.3, 5, 4, 6, 4);
				for (LivingEntity livingentity : mob.level.getEntitiesOfClass(LivingEntity.class, attackrange)) {
					if (livingentity != mob) {
						if (livingentity != mob) {
							if (livingentity.hurt(DamageSource.mobAttack(mob), (float) (mob.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5)))
								livingentity.push(0, -2, 0);
						}
					}
				}
			} else if (mob.attackAnim > 0.6f && attackprogress == 4) {
				attackprogress++;
				vec31 = new Vec3(mob.getX() + mob.getHorizontalLookAngle().x * 6, mob.getY(), mob.getZ() + mob.getHorizontalLookAngle().z * 6);
				EarthQuakeEntity.shoot(mob.level, mob, (float) mob.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5f, 5, 10, 4, mob.getYRot(), vec31);
				mob.playSound(SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR, 2f, 0.5f);
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:rock_smash")), 2f, 0.5f);
				AABB attackrange = CustomMathHelper.makeAttackRange(mob.getX() + mob.getHorizontalLookAngle().x * 3, mob.getY(), mob.getZ() + mob.getHorizontalLookAngle().z * 3, 0, 5, 5, 5);
				for (LivingEntity livingentity : mob.level.getEntitiesOfClass(LivingEntity.class, attackrange)) {
					if (livingentity != mob) {
						/*
						IThrowAbility.hurtAndThrowTargetVerticallyCustom(mob, livingentity,
								(float) (mob.getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.3),
								mob.getAttributeValue(Attributes.ATTACK_KNOCKBACK) / 2);
						*/
						livingentity.invulnerableTime = 0;
						mob.defaultAttack(livingentity);
					}
				}
			}
		}
	}

	class insertArmsGoal extends LockAngleGoal {
		int attackprogress;
		Vec3 vec31;
		boolean isActivated;

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		public insertArmsGoal(AnimatedTempleEntity mob) {
			super(mob);
		}

		public boolean canUse() {
			if (isActivated)
				return mob.swinging;
			int as = this.mob.entityData.get(ATTACK_STATE);
			return super.canUse() && as == 4;
		}

		public void start() {
			super.start();
			isActivated = true;
			attackprogress = 0;
		}

		public void tick() {
			if (attackprogress == 0) {
				attackprogress++;
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:redstonegolem_prepare_attack")), 2f, 1f);
			} else if (mob.attackAnim > 0.12f && attackprogress == 1) {
				attackprogress++;
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:rock_match")), 1f, 0.5f);
			} else if (mob.attackAnim > 0.22f && attackprogress == 2) {
				attackprogress++;
				mob.playSound(SoundEvents.IRON_GOLEM_DAMAGE, 2f, 0.5f);
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_step")), 1f, 2f);
				vec31 = new Vec3(mob.getX() + mob.getHorizontalLookAngle().x * 4 + mob.getHorizontalLeftLookAngle().x * 3, mob.getY(), mob.getZ() + mob.getHorizontalLookAngle().z * 4 + mob.getHorizontalLeftLookAngle().z * 3);
				EarthQuakeEntity.shoot(mob.level, mob, (float) mob.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5f, 4, 1, 1, mob.getYRot(), vec31);
				vec31 = new Vec3(mob.getX() + mob.getHorizontalLookAngle().x * 4 - mob.getHorizontalLeftLookAngle().x * 3, mob.getY(), mob.getZ() + mob.getHorizontalLookAngle().z * 4 - mob.getHorizontalLeftLookAngle().z * 3);
				EarthQuakeEntity.shoot(mob.level, mob, (float) mob.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5f, 4, 1, 1, mob.getYRot(), vec31);
				AABB attackrange = CustomMathHelper.makeAttackRange(mob.getX() + mob.getHorizontalLookAngle().x * 2, mob.getY(), mob.getZ() + mob.getHorizontalLookAngle().z * 2, 0, 7, 7, 7);
				for (LivingEntity livingentity : mob.level.getEntitiesOfClass(LivingEntity.class, attackrange)) {
					if (livingentity != mob) {
						livingentity.invulnerableTime = 0;
						IThrowAbility.hurtAndThrowTargetVerticallyCustom(mob, livingentity, (float) (mob.getAttributeValue(Attributes.ATTACK_DAMAGE)), 1);
					}
				}
				mob.setAttackState(5);
			} else if (mob.attackAnim < 0.76f && attackprogress == 3) {
				if (mob.swingTime % 10 == 0) {
					mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_step")), 2f, 2f);
					vec31 = new Vec3(mob.getX() + mob.getHorizontalLookAngle().x * 4 + mob.getHorizontalLeftLookAngle().x * 3, mob.getY(), mob.getZ() + mob.getHorizontalLookAngle().z * 4 + mob.getHorizontalLeftLookAngle().z * 3);
					EarthQuakeEntity.shoot(mob.level, mob, (float) mob.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5f, 4, 1, 1, mob.getYRot(), vec31);
					vec31 = new Vec3(mob.getX() + mob.getHorizontalLookAngle().x * 4 - mob.getHorizontalLeftLookAngle().x * 3, mob.getY(), mob.getZ() + mob.getHorizontalLookAngle().z * 4 - mob.getHorizontalLeftLookAngle().z * 3);
					EarthQuakeEntity.shoot(mob.level, mob, (float) mob.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5f, 4, 1, 1, mob.getYRot(), vec31);
				}
			} else if (attackprogress == 3) {
				attackprogress++;
				mob.setAttackState(6);
			}
			if (mob.attackAnim > 0.22f && mob.attackAnim < 0.76f) {
				mob.setDeltaMovement(mob.getDeltaMovement().multiply(0.3, 1, 0.3));
			}
		}

		public void stop() {
			isActivated = false;
		}
	}

	class massiveJumpGoal extends LockAngleGoal {
		int attackprogress;
		Vec3 vec31;
		boolean isActivated;
		int cooltime = 30;

		public massiveJumpGoal(AnimatedTempleEntity mob) {
			super(mob);
		}

		public boolean canUse() {
			if (isActivated)
				return mob.swinging;
			if (!mob.swinging && mob.getTarget() != null) {
				cooltime--;
				return cooltime <= 0 && mob.isOnGround();
			}
			return false;
		}

		public void stop() {
			isActivated = false;
			cooltime = 60;
		}

		public void start() {
			super.start();
			isActivated = true;
			mob.swing(InteractionHand.MAIN_HAND);
			mob.setAttackState(7);
			attackprogress = 0;
			if (mob.getTarget() != null)
				vec31 = mob.getTarget().position();
			else
				vec31 = mob.position().add(mob.getLookAngle().scale(20));
		}

		public void tick() {
			if (attackprogress == 0) {
				attackprogress++;
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_step")), 2f, 1f);
			} else if (mob.attackAnim > 0.35f && attackprogress == 1) {
				attackprogress++;
				Explosion.BlockInteraction explosion$blockinteraction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(mob.level, mob) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
				mob.playSound(SoundEvents.TOTEM_USE, 2f, 0.5f);
				mob.setDeltaMovement((vec31.x - mob.getX()) * 0.16, 1.5, (vec31.z - mob.getZ()) * 0.16);
				mob.level.explode(mob, mob.getX(), mob.getY() - 1.5, mob.getZ(), 4, explosion$blockinteraction);
			}
		}
	}

	public boolean defaultAttack(Entity entity) {
		return super.doHurtTarget(entity);
	}

	public Vec3 getHorizontalLookAngle() {
		return this.calculateViewVector(0, this.getYRot());
	}

	public Vec3 getHorizontalLeftLookAngle() {
		return this.calculateViewVector(0, this.getYRot() - 90);
	}

	public Vec3 getHorizontalRightLookAngle() {
		return this.calculateViewVector(0, this.getYRot() + 90);
	}

	public boolean removeWhenFarAway(double p_27519_) {
		return false;
	}

	public int maxSwingTime() {
		int as = this.entityData.get(ATTACK_STATE);
		int i = 1;
		if (as == 1) {
			i = 60;
		} else if (as <= 3) {
			i = 41;
		} else if (as <= 6) {
			i = 140;
		} else if (as == 7) {
			i = 25;
		} else if (as <= 9) {
			i = 52;
		}
		return i;
	}

	protected void updateSwingTime() {
		int as = this.entityData.get(ATTACK_STATE);
		int i = this.maxSwingTime();
		if (this.swinging) {
			++this.swingTime;
			if (this.swingTime >= i) {
				this.swingTime = 0;
				this.swinging = false;
				this.entityData.set(ATTACK_STATE, 0);
			}
		} else {
			this.swingTime = 0;
			this.entityData.set(ATTACK_STATE, 0);
		}
		this.attackAnim = (float) this.swingTime / (float) i;
	}

	public boolean doHurtTarget(Entity entity) {
		double random = Math.random();
		if (random < 0.1)
			this.entityData.set(ATTACK_STATE, 4);
		else
			this.entityData.set(ATTACK_STATE, this.random.nextInt(3) + 1);
		return false;
	}

	protected PathNavigation createNavigation(Level p_33348_) {
		if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(p_33348_, this))
			return new TRabusEntity.RavagerNavigation(this, p_33348_);
		else
			return new GroundPathNavigation(this, p_33348_);
	}

	public void tick() {
		this.squish += (this.entityData.get(SQUASH_AMOUNT) - this.squish) * 0.5F;
		this.oSquish = this.squish;
		if (this.isAlive() && this.entityData.get(SQUASH_AMOUNT) > 0)
			this.entityData.set(SQUASH_AMOUNT, this.entityData.get(SQUASH_AMOUNT) * 0.8f);
		this.getPersistentData().putFloat("squashAmount", this.squish);
		this.getPersistentData().putFloat("squashAmount0", this.oSquish);
		super.tick();
	}

	public void aiStep() {
		super.aiStep();
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
		}
		if (this.getDeltaMovement().horizontalDistanceSqr() > (double) 2.5000003E-7F) {
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
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEFINED;
	}

	protected float getSoundVolume() {
		return 2;
	}

	public float getVoicePitch() {
		return 0.5f;
	}

	protected float nextStep() {
		return (float) ((int) this.moveDist + 2f);
	}

	@Override
	public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:redstonegolem_idle"));
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		super.playStepSound(pos, blockIn);
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:redstonegolem_step")), 2f, 0.5f);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:redstonegolem_hit"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:redstonegolem_heavy_death"));
	}

	public static void init() {
		SpawnPlacements.register(CrimsonStevesMobsModEntities.ANIMATED_TEMPLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos, random) -> (world.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(world, pos, random) && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)));
	}

	public void swing(InteractionHand p_21012_, boolean p_21013_) {
		if (this.isAlive()) {
			ItemStack stack = this.getItemInHand(p_21012_);
			if (!stack.isEmpty() && stack.onEntitySwing(this))
				return;
			if (!this.swinging || this.swingTime < 0) {
				this.swingTime = -1;
				this.swinging = true;
				this.swingingArm = p_21012_;
				if (this.level instanceof ServerLevel) {
					ClientboundAnimatePacket clientboundanimatepacket = new ClientboundAnimatePacket(this, p_21012_ == InteractionHand.MAIN_HAND ? 0 : 3);
					ServerChunkCache serverchunkcache = ((ServerLevel) this.level).getChunkSource();
					if (p_21013_) {
						serverchunkcache.broadcastAndSend(this, clientboundanimatepacket);
					} else {
						serverchunkcache.broadcast(this, clientboundanimatepacket);
					}
				}
			}
		}
	}

	public int customDeathTime;

	public void die(DamageSource p_21014_) {
		super.die(p_21014_);
		slamAnimationState.stop();
		leftSlamAnimationState.stop();
		rightSlamAnimationState.stop();
		insertArmsStartAnimationState.stop();
		insertArmsIdleAnimationState.stop();
		insertArmsStopAnimationState.stop();
		this.deathAnimationState.start(this.tickCount);
	}

	protected void tickDeath() {
		++this.customDeathTime;
		if (this.customDeathTime == 70 && !this.level.isClientSide()) {
			this.level.broadcastEntityEvent(this, (byte) 60);
			this.remove(Entity.RemovalReason.KILLED);
		}
	}

	protected void doPush(Entity p_28839_) {
		if (p_28839_ instanceof LivingEntity mob) {
			if (mob.isAttackable() && this.random.nextInt(40) == 0 && !this.isAlliedTo(mob)) {
				this.setTarget(mob);
			}
		}
		super.doPush(p_28839_);
	}

	public boolean hurt(DamageSource source, float amount) {
		if (source.getEntity() instanceof Mob _lve) {
			if (!this.isAlliedTo(_lve) && _lve.getTarget() == this && this.getTarget() != null ? this.distanceTo(_lve) < this.distanceTo(this.getTarget()) : false)
				this.setTarget(_lve);
		}
		amount = (float) Math.pow((double) amount, 0.85);
		if (amount > 4) {
			amount -= 4;
			return super.hurt(source, amount);
		}
		this.playSound(SoundEvents.STONE_BREAK);
		return false;
	}

	public class LockAngleGoal extends Goal {
		final AnimatedTempleEntity mob;

		public LockAngleGoal(AnimatedTempleEntity mob) {
			this.mob = mob;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		public boolean canUse() {
			return mob.swinging && mob.isAlive();
		}

		public void start() {
			mob.getNavigation().stop();
			if (mob.getTarget() != null) {
				mob.lookAt(EntityAnchorArgument.Anchor.FEET, mob.getTarget().position());
				//mob.lookAt(mob.getTarget(), 30, 30);
			}
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 400);
		builder = builder.add(Attributes.ARMOR, 8);
		builder = builder.add(Attributes.ARMOR_TOUGHNESS, 8);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 18);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 8);
		return builder;
	}
}
