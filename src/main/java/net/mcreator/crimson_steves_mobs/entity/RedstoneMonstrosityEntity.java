
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.animal.IronGolem;
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
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.chat.Component;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.SmartBodyHelper2;
import net.mcreator.crimson_steves_mobs.SlowRotMoveControl;
import net.mcreator.crimson_steves_mobs.IThrowAbility;
import net.mcreator.crimson_steves_mobs.ICanBeAnimated;
import net.mcreator.crimson_steves_mobs.CustomMathHelper;

import java.util.EnumSet;

public class RedstoneMonstrosityEntity extends Raider implements ICanBeAnimated {
	public AnimationState slamAnimationState = new AnimationState();
	public AnimationState leftSlamAnimationState = new AnimationState();
	public AnimationState rightSlamAnimationState = new AnimationState();
	public AnimationState insertArmsStartAnimationState = new AnimationState();
	public AnimationState insertArmsIdleAnimationState = new AnimationState();
	public AnimationState insertArmsStopAnimationState = new AnimationState();
	public AnimationState shootStartAnimationState = new AnimationState();
	public AnimationState shootStopAnimationState = new AnimationState();
	public AnimationState fastShootAnimationState = new AnimationState();
	public AnimationState deathAnimationState = new AnimationState();
	private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(RedstoneMonstrosityEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> ATTACK_SPEED = SynchedEntityData.defineId(RedstoneMonstrosityEntity.class, EntityDataSerializers.FLOAT);
	public int customDeathTime;

	public RedstoneMonstrosityEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.REDSTONE_MONSTROSITY.get(), world);
	}

	protected void updateControlFlags() {
		//boolean flag1 = !(this.getVehicle() instanceof Boat);
		this.goalSelector.setControlFlag(Goal.Flag.MOVE, true);
		this.goalSelector.setControlFlag(Goal.Flag.JUMP, true);
		this.goalSelector.setControlFlag(Goal.Flag.LOOK, true);
		this.goalSelector.setControlFlag(Goal.Flag.TARGET, true);
	}

	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.RED, ServerBossEvent.BossBarOverlay.PROGRESS);

	@Override
	public void startSeenByPlayer(ServerPlayer player) {
		super.startSeenByPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	public void applyRaidBuffs(int p_34079_, boolean p_34080_) {
	}

	public SoundEvent getCelebrateSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:redstonegolem_growl"));
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer player) {
		super.stopSeenByPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	int BossBarColor;

	@Override
	public void customServerAiStep() {
		super.customServerAiStep();
		if ("jeb_".equals(this.getName().getString())) {
			if (this.tickCount % 5 == 0)
				if (BossBarColor < ServerBossEvent.BossBarColor.values().length - 1) {
					BossBarColor++;
					bossInfo.setColor(ServerBossEvent.BossBarColor.values()[BossBarColor]);
				} else
					BossBarColor = 0;
		}
		this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
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

	float[] lightUp = new float[4];

	public float getLightUpAmount(int input) {
		lightUp[input] = Mth.lerp(0.06f, lightUp[input], Mth.clamp((this.getMaxHealth() - this.getHealth()) * 2 / this.getMaxHealth(), 0, 1));
		return lightUp[input];
	}

	/*
	public void tick() {
		super.tick();
		if (!this.navigation.isDone()
				&& ((this.getTarget() == null || !this.getTarget().isAlive()) || (this.getTarget() != null && this.horizontalCollision))) {
			Vec3 blockpos = this.navigation.getPath().getNextEntityPos(this);
			if (this.distanceToSqr(blockpos.x, blockpos.y, blockpos.z) < 2) {
				this.navigation.stop();
			}
			this.lookControl.setLookAt(blockpos.x, blockpos.y + this.getEyeHeight(), blockpos.z);
		}
	}
	*/
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

	public void makeStuckInBlock(BlockState p_33796_, Vec3 p_33797_) {
	}

	public RedstoneMonstrosityEntity(EntityType<RedstoneMonstrosityEntity> type, Level world) {
		super(type, world);
		xpReward = 500;
		maxUpStep = 2;
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
		this.moveControl = new SlowRotMoveControl(this);
		if ("jeb_".equals(this.getName().getString())) {
			this.setSprinting(true);
		} else {
			this.setSprinting(false);
		}
		setPersistenceRequired();
	}

	@Override
	protected BodyRotationControl createBodyControl() {
		return new SmartBodyHelper2(this);
	}

	/*
	public int getHeadRotSpeed() {
		return 1;
	}
	*/
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ATTACK_STATE, 0);
		this.entityData.define(ATTACK_SPEED, 1f);
	}

	/*
	public void addAdditionalSaveData(CompoundTag p_33619_) {
		super.addAdditionalSaveData(p_33619_);
		p_33619_.putInt("customDeathTime", this.customDeathTime);
	}
	public void readAdditionalSaveData(CompoundTag p_33607_) {
		super.readAdditionalSaveData(p_33607_);
		if (p_33607_.contains("customDeathTime")) {
			this.customDeathTime = p_33607_.getInt("customDeathTime");
			//this.deathAnimationState.start(this.tickCount);
			//this.deathAnimationState.updateTime(this.tickCount, this.customDeathTime);
		}
	}
	*/
	public float getAttackSpeed() {
		return this.entityData.get(ATTACK_SPEED);
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
		else if (input == "insertArmsStop2")
			return insertArmsStopAnimationState;
		else if (input == "shootStart")
			return shootStartAnimationState;
		else if (input == "shootStop")
			return shootStopAnimationState;
		else if (input == "fastShoot")
			return fastShootAnimationState;
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
						this.insertArmsStartAnimationState.stop();
						this.insertArmsStopAnimationState.start(this.tickCount);
						break;
					case 7 :
						this.shootStartAnimationState.start(this.tickCount);
						break;
					case 8 :
						this.shootStartAnimationState.stop();
						this.shootStopAnimationState.start(this.tickCount);
						break;
					case 9 :
						this.fastShootAnimationState.start(this.tickCount);
						break;
				}
		}
	}

	public int maxSwingTime() {
		int as = this.entityData.get(ATTACK_STATE);
		int i = 0;
		if (as == 1) {
			i = 60;
		} else if (as <= 3) {
			i = 41;
		} else if (as <= 6) {
			i = 140;
		} else if (as <= 8) {
			i = 60;
		} else if (as == 9) {
			i = 25;
		}
		return (int) (i / getAnimationSpeed());
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

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new slamAttackGoal(this));
		this.goalSelector.addGoal(0, new singleSlamAttackGoal(this));
		this.goalSelector.addGoal(0, new insertArmsGoal(this));
		this.goalSelector.addGoal(0, new bombShootingGoal(this));
		this.goalSelector.addGoal(0, new rapidBombShootingGoal(this));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) {
			final RandomSource random = RandomSource.create();

			public void tick() {
				super.tick();
				random.setSeed(mob.tickCount);
			}

			protected void checkAndPerformAttack(LivingEntity p_25557_, double p_25558_) {
				double d0 = this.getAttackReachSqr(p_25557_);
				if (!mob.swinging && (p_25558_ <= d0 * 0.5 || CustomMathHelper.isEntityInBox(p_25557_, mob, 2) || random.nextInt((int) (5 - RedstoneMonstrosityEntity.this.getAttackSpeed()) * 30) == 0)) {
					this.mob.swing(InteractionHand.MAIN_HAND);
					this.mob.doHurtTarget(p_25557_);
				}
			}
			/*
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				if (!mob.swinging) {
					if (CustomMathHelper.isEntityInBox(entity, mob, 3) || mob.distanceToSqr(entity) < super.getAttackReachSqr(entity) * 0.5)
						return Double.POSITIVE_INFINITY;
					else if (random.nextInt((int) ((5 - RedstoneMonstrosityEntity.this.getAttackSpeed()) * 30)) == 0)
						return Double.POSITIVE_INFINITY;
				}
				return -1;
			}
			*/
		});
		this.goalSelector.addGoal(2, new FloatGoal(this));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
		this.targetSelector.addGoal(0, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true, (p_199899_) -> {
			return !p_199899_.isBaby();
		}));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	class slamAttackGoal extends LockAngleGoal {
		int attackprogress;
		Vec3 vec31;

		public slamAttackGoal(RedstoneMonstrosityEntity mob) {
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
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_arm_move")), 2f, 0.7f);
			} else if (mob.attackAnim > 0.26f && attackprogress == 1) {
				attackprogress++;
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:rock_match")), 1f, 0.5f);
			} else if (mob.attackAnim > 0.44f && attackprogress == 2) {
				attackprogress++;
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_step")), 2f, 1f);
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
				vec31 = new Vec3(mob.getX() + mob.getHorizontalLookAngle().x * 4.5 + mob.getHorizontalLeftLookAngle().x * 3, mob.getY(), mob.getZ() + mob.getHorizontalLookAngle().z * 4.5 + mob.getHorizontalLeftLookAngle().z * 3);
				EarthQuakeEntity.shoot(mob.level, mob, (float) mob.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.75f, 5, 1, 1, mob.getYRot(), vec31);
				vec31 = new Vec3(mob.getX() + mob.getHorizontalLookAngle().x * 4.5 - mob.getHorizontalLeftLookAngle().x * 3, mob.getY(), mob.getZ() + mob.getHorizontalLookAngle().z * 4.5 - mob.getHorizontalLeftLookAngle().z * 3);
				EarthQuakeEntity.shoot(mob.level, mob, (float) mob.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.75f, 5, 1, 1, mob.getYRot(), vec31);
				//mob.playSound(SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR, 2f, 0.5f);
				mob.playSound(SoundEvents.GENERIC_EXPLODE, 2f, 1f);
				AABB attackrange = CustomMathHelper.makeAttackRange(mob.getX() + mob.getHorizontalLookAngle().x * 2, mob.getY(), mob.getZ() + mob.getHorizontalLookAngle().z * 2, 0, 7, 5, 7);
				for (LivingEntity livingentity : mob.level.getEntitiesOfClass(LivingEntity.class, attackrange)) {
					if (!mob.isAlliedTo(livingentity)) {
						livingentity.invulnerableTime = 0;
						IThrowAbility.hurtAndThrowTargetVerticallyCustom(mob, livingentity, (float) (mob.getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.5f), (int) (mob.getAttributeValue(Attributes.ATTACK_KNOCKBACK) * 0.7));
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

		public singleSlamAttackGoal(RedstoneMonstrosityEntity mob) {
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
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_arm_move")), 2f, 1.2f);
			} else if (mob.attackAnim > 0.24f && attackprogress == 1) {
				attackprogress++;
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:rock_match")), 1f, 0.5f);
			} else if (mob.attackAnim > 0.4f && attackprogress == 2) {
				attackprogress++;
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_step")), 2f, 1f);
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
				vec31 = new Vec3(mob.getX() + mob.getHorizontalLookAngle().x * 5.5, mob.getY(), mob.getZ() + mob.getHorizontalLookAngle().z * 5.5);
				EarthQuakeEntity.shoot(mob.level, mob, (float) mob.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5f, 5, 1, 4, mob.getYRot(), vec31);
				mob.playSound(SoundEvents.GENERIC_EXPLODE, 2f, 1f);
				AABB attackrange = CustomMathHelper.makeAttackRange(mob.getX() + mob.getHorizontalLookAngle().x * 3, mob.getY(), mob.getZ() + mob.getHorizontalLookAngle().z * 3, 0, 5, 5, 5);
				for (LivingEntity livingentity : mob.level.getEntitiesOfClass(LivingEntity.class, attackrange)) {
					if (!mob.isAlliedTo(livingentity)) {
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
		RandomSource random = RandomSource.create();

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		public insertArmsGoal(RedstoneMonstrosityEntity mob) {
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
			random.setSeed(mob.tickCount);
			if (attackprogress == 0) {
				attackprogress++;
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_arm_move")), 2f, 1f);
			} else if (mob.attackAnim > 0.12f && attackprogress == 1) {
				attackprogress++;
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:rock_match")), 1f, 0.5f);
			} else if (mob.attackAnim > 0.22f && attackprogress == 2) {
				attackprogress++;
				mob.playSound(SoundEvents.IRON_GOLEM_DAMAGE, 2f, 0.5f);
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_step")), 2f, 1f);
				vec31 = new Vec3(mob.getX() + mob.getHorizontalLookAngle().x * 4 + mob.getHorizontalLeftLookAngle().x * 3, mob.getY(), mob.getZ() + mob.getHorizontalLookAngle().z * 4 + mob.getHorizontalLeftLookAngle().z * 3);
				EarthQuakeEntity.shoot(mob.level, mob, (float) mob.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5f, 4, 1, 1, mob.getYRot(), vec31);
				vec31 = new Vec3(mob.getX() + mob.getHorizontalLookAngle().x * 4 - mob.getHorizontalLeftLookAngle().x * 3, mob.getY(), mob.getZ() + mob.getHorizontalLookAngle().z * 4 - mob.getHorizontalLeftLookAngle().z * 3);
				EarthQuakeEntity.shoot(mob.level, mob, (float) mob.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5f, 4, 1, 1, mob.getYRot(), vec31);
				AABB attackrange = CustomMathHelper.makeAttackRange(mob.getX() + mob.getHorizontalLookAngle().x * 2, mob.getY(), mob.getZ() + mob.getHorizontalLookAngle().z * 2, 0, 7, 7, 7);
				for (LivingEntity livingentity : mob.level.getEntitiesOfClass(LivingEntity.class, attackrange)) {
					if (!mob.isAlliedTo(livingentity)) {
						livingentity.invulnerableTime = 0;
						IThrowAbility.hurtAndThrowTargetVerticallyCustom(mob, livingentity, (float) (mob.getAttributeValue(Attributes.ATTACK_DAMAGE)), 1);
					}
				}
				//mob.setAttackState(5);
			} else if (mob.attackAnim > 0.22f && mob.attackAnim <= 0.76f) {
				//attackprogress++;
				if (random.nextInt(20) < mob.getAttackSpeed()) {
					vec31 = mob.position().add((random.nextFloat() - 0.5) * 20, 0, (random.nextFloat() - 0.5) * 20);
					BlockPos blockpos = new BlockPos(vec31.x, mob.getY(), vec31.z);
					boolean flag = false;
					double d0 = 0.0D;
					do {
						BlockPos blockpos1 = blockpos.below();
						BlockState blockstate = mob.level.getBlockState(blockpos1);
						if (blockstate.isFaceSturdy(mob.level, blockpos1, Direction.UP)) {
							if (!mob.level.isEmptyBlock(blockpos)) {
								BlockState blockstate1 = mob.level.getBlockState(blockpos);
								VoxelShape voxelshape = blockstate1.getCollisionShape(mob.level, blockpos);
								if (!voxelshape.isEmpty()) {
									d0 = voxelshape.max(Direction.Axis.Y);
								}
							}
							flag = true;
							break;
						}
						blockpos = blockpos.below();
					} while (blockpos.getY() >= Mth.floor(mob.getY()) - 10);
					if (flag && mob.level instanceof ServerLevel _level) {
						_level.sendParticles(ParticleTypes.LAVA, vec31.x, (double) blockpos.getY() + d0, vec31.z, 20, 0, random.nextGaussian(), 0, random.nextGaussian());
						MinionRedstoneGolemEntity entityToSpawn = new MinionRedstoneGolemEntity(CrimsonStevesMobsModEntities.MINION_REDSTONE_GOLEM.get(), _level);
						entityToSpawn.moveTo(vec31.x, (double) blockpos.getY() + d0, vec31.z, 0, 0);
						entityToSpawn.setOwner(mob);
						entityToSpawn.push(0, 0.4, 0);
						entityToSpawn.finalizeSpawn(_level, mob.level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
						entityToSpawn.getAttribute(Attributes.MAX_HEALTH).setBaseValue(entityToSpawn.getMaxHealth() * 0.5f);
						mob.addMobToTeam(entityToSpawn);
						mob.level.addFreshEntity(entityToSpawn);
					}
				}
				//if (mob.swingTime % 10 == 0) {
				//}
			} else if (mob.attackAnim > 0.82f && attackprogress == 3) {
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

	class rapidBombShootingGoal extends LockAngleGoal {
		int attackprogress;
		Vec3 vec31;
		RandomSource random = RandomSource.create();

		public rapidBombShootingGoal(RedstoneMonstrosityEntity mob) {
			super(mob);
		}

		public boolean canUse() {
			int as = this.mob.entityData.get(ATTACK_STATE);
			return super.canUse() && as == 9;
		}

		public void start() {
			super.start();
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
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_shoot")), 2f, 1f);
				for (int i = 0; i < 6; i++) {
					RedstoneBombEntity snowball = new RedstoneBombEntity(CrimsonStevesMobsModEntities.REDSTONE_BOMB.get(), mob, mob.level);
					double d0 = vec31.y - 1;
					double d1 = vec31.x - mob.getX();
					double d2 = d0 - snowball.getY();
					double d3 = vec31.z - mob.getZ();
					double d4 = Math.sqrt(d1 * d1 + d3 * d3) * (double) 0.2F;
					snowball.moveTo(mob.getX(), mob.getY() + 3.3, mob.getZ());
					snowball.setSilent(true);
					snowball.setBaseDamage(10);
					snowball.shoot(d1, d2 + d4, d3, 1.6F, 30);
					mob.level.addFreshEntity(snowball);
				}
			}
		}
	}

	class bombShootingGoal extends LockAngleGoal {
		int attackprogress;
		Vec3 vec31;
		boolean isActivated;
		RandomSource random = RandomSource.create();

		public bombShootingGoal(RedstoneMonstrosityEntity mob) {
			super(mob);
		}

		public boolean canUse() {
			if (isActivated)
				return mob.swinging;
			int as = this.mob.entityData.get(ATTACK_STATE);
			return super.canUse() && as == 7;
		}

		public void start() {
			super.start();
			isActivated = true;
			attackprogress = 0;
			if (mob.getTarget() != null)
				vec31 = mob.getTarget().position();
			else
				vec31 = mob.position().add(mob.getLookAngle().scale(20));
		}

		public void tick() {
			if (attackprogress == 0) {
				attackprogress++;
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_arm_move")), 2f, 1f);
			} else if (mob.attackAnim > 0.33f && attackprogress == 1) {
				attackprogress++;
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:rock_match")), 1f, 0.5f);
			} else if (mob.attackAnim > 0.6f && attackprogress == 2) {
				attackprogress++;
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_shoot")), 2f, 1f);
				for (int i = 0; i < 10; i++) {
					RedstoneBombEntity snowball = new RedstoneBombEntity(CrimsonStevesMobsModEntities.REDSTONE_BOMB.get(), mob, mob.level);
					double d0 = vec31.y - 2.5;
					double d1 = vec31.x - mob.getX();
					double d2 = d0 - snowball.getY();
					double d3 = vec31.z - mob.getZ();
					double d4 = Math.sqrt(d1 * d1 + d3 * d3) * (double) 0.2F;
					snowball.moveTo(mob.getX(), mob.getY() + 4.5, mob.getZ());
					snowball.setSilent(true);
					snowball.setBaseDamage(10);
					snowball.shoot(d1, d2 + d4, d3, 1.6F, 30);
					mob.level.addFreshEntity(snowball);
				}
				mob.setAttackState(8);
			} else if (mob.attackAnim > 0.81f && attackprogress == 3) {
				attackprogress++;
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_arm_move")), 2f, 1f);
			} else if (mob.attackAnim > 0.95f && attackprogress == 4) {
				attackprogress++;
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:rock_match")), 1f, 0.5f);
			}
		}

		public void stop() {
			isActivated = false;
		}
	}

	public void addMobToTeam(Entity mob) {
		if (this.getTeam() != null) {
			this.level.getScoreboard().addPlayerToTeam(mob.getStringUUID(), this.level.getScoreboard().getPlayerTeam(this.getTeam().getName()));
		}
	}

	public void setCustomName(Component component) {
		if ("jeb_".equals(component.getString())) {
			this.setSprinting(true);
		}
		bossInfo.setName(component);
		super.setCustomName(component);
	}

	public boolean doHurtTarget(Entity entity) {
		//if (this.entityData.get(ATTACK_STATE) == 0) {
		if ("jeb_".equals(this.getName().getString())) {
			this.entityData.set(ATTACK_SPEED, 3f);
		} else if (this.getHealth() > this.getMaxHealth() * 0.75f) {
			this.entityData.set(ATTACK_SPEED, 1f);
		} else if (this.getHealth() > this.getMaxHealth() * 0.5f) {
			this.entityData.set(ATTACK_SPEED, 1.25f);
		} else if (this.getHealth() > this.getMaxHealth() * 0.25f) {
			this.entityData.set(ATTACK_SPEED, 1.5f);
		} else {
			this.entityData.set(ATTACK_SPEED, 1.75f);
		}
		if (this.random.nextInt(9) == 0)
			this.entityData.set(ATTACK_STATE, 4);
		else if (CustomMathHelper.isEntityInBox((LivingEntity) entity, this, 2) || this.distanceToSqr(entity) < this.getAttackReachSqr((LivingEntity) entity) * 0.5) {
			this.entityData.set(ATTACK_STATE, this.random.nextInt(3) + 1);
		} else
			this.entityData.set(ATTACK_STATE, this.random.nextInt(3) > 0 ? 7 : 9);
		//}
		return false;
	}

	protected double getAttackReachSqr(LivingEntity p_25556_) {
		return (double) (this.getBbWidth() * 2.0F * this.getBbWidth() * 2.0F + p_25556_.getBbWidth());
	}

	protected PathNavigation createNavigation(Level p_33348_) {
		if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(p_33348_, this))
			return new TRabusEntity.RavagerNavigation(this, p_33348_);
		else
			return new GroundPathNavigation(this, p_33348_);
	}

	protected boolean canRide(Entity p_31508_) {
		return false;
	}

	public void aiStep() {
		super.aiStep();
		if (this.getHealth() < this.getMaxHealth() * 0.25f && this.tickCount % 2 == 0) {
			this.level.addParticle(ParticleTypes.LAVA, this.getRandomX(this.getBbWidth() / 4), this.getRandomY(), this.getRandomZ(this.getBbWidth() / 4), 0.0D, 0.0D, 0.0D);
			this.level.addParticle(ParticleTypes.LARGE_SMOKE, this.getRandomX(this.getBbWidth() / 4), this.getRandomY(), this.getRandomZ(this.getBbWidth() / 4), 0.0D, 0.0D, 0.0D);
		}
		if (this.getHealth() < this.getMaxHealth() * 0.75f) {
			this.level.addParticle(ParticleTypes.LARGE_SMOKE, this.getRandomX(this.getBbWidth() / 4), this.getRandomY(), this.getRandomZ(this.getBbWidth() / 4), 0.0D, 0.0D, 0.0D);
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

	public void die(DamageSource p_21014_) {
		super.die(p_21014_);
		slamAnimationState.stop();
		leftSlamAnimationState.stop();
		rightSlamAnimationState.stop();
		insertArmsStartAnimationState.stop();
		insertArmsIdleAnimationState.stop();
		insertArmsStopAnimationState.stop();
		shootStartAnimationState.stop();
		shootStopAnimationState.stop();
		fastShootAnimationState.stop();
		this.deathAnimationState.start(this.tickCount);
	}

	protected void tickDeath() {
		++this.customDeathTime;
		if (this.customDeathTime == 70 && !this.level.isClientSide()) {
			this.level.broadcastEntityEvent(this, (byte) 60);
			this.remove(Entity.RemovalReason.KILLED);
		}
	}

	public boolean canBeLeader() {
		return false;
	}

	private void makePoofParticles() {
		for (int i = 0; i < 200; ++i) {
			double d0 = this.random.nextGaussian() * 0.02D;
			double d1 = this.random.nextGaussian() * 0.02D;
			double d2 = this.random.nextGaussian() * 0.02D;
			this.level.addParticle(ParticleTypes.LARGE_SMOKE, this.getRandomX(this.getBbWidth() / 4), this.getY() + this.getBbHeight() * this.random.nextFloat(), this.getRandomZ(this.getBbWidth() / 4), d0, d1, d2);
		}
	}

	public void handleEntityEvent(byte p_20975_) {
		if (p_20975_ == 60) {
			makePoofParticles();
		} else {
			super.handleEntityEvent(p_20975_);
		}
	}

	public float getAnimationSpeed() {
		if ("jeb_".equals(this.getName().getString()))
			return 3;
		return this.entityData.get(ATTACK_SPEED);
	}

	@Override
	public MobType getMobType() {
		return MobType.ILLAGER;
	}

	protected float getSoundVolume() {
		return 2;
	}

	protected float nextStep() {
		return (float) ((int) this.moveDist + 2f);
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
			if (_lve.isAttackable() && !this.isAlliedTo(_lve) && _lve.getTarget() == this && this.getTarget() != null ? this.distanceTo(_lve) < this.distanceTo(this.getTarget()) : false)
				this.setTarget(_lve);
		}
		return super.hurt(source, amount);
	}

	public class LockAngleGoal extends Goal {
		final RedstoneMonstrosityEntity mob;

		public LockAngleGoal(RedstoneMonstrosityEntity mob) {
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
			}
		}
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_idle"));
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_step")), 1, 1);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		if ("jeb_".equals(this.getName().getString())) {
			this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_death")));
			return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:jeb_nooooo"));
		}
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_death"));
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 450);
		builder = builder.add(Attributes.ARMOR, 15);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 18);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 10);
		return builder;
	}
}
