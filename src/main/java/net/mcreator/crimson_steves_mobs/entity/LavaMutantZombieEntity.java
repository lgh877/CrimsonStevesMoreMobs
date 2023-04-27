package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.Mth;
import net.minecraft.tags.FluidTags;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.SmartBodyHelper2;
import net.mcreator.crimson_steves_mobs.SlowRotMoveControl;
import net.mcreator.crimson_steves_mobs.LockAngleGoal;
import net.mcreator.crimson_steves_mobs.IMutantZombie;
import net.mcreator.crimson_steves_mobs.CustomMathHelper;

public class LavaMutantZombieEntity extends Monster implements IMutantZombie {
	private static final EntityDataAccessor<Boolean> SKILLACTIVE = SynchedEntityData.defineId(LavaMutantZombieEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> SLAM = SynchedEntityData.defineId(LavaMutantZombieEntity.class, EntityDataSerializers.BOOLEAN);
	private int slam0;
	private int slam;
	private static final EntityDataAccessor<Boolean> SHOUT = SynchedEntityData.defineId(LavaMutantZombieEntity.class, EntityDataSerializers.BOOLEAN);
	private int shout0;
	private int shout;

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SLAM, false);
		this.entityData.define(SHOUT, false);
		this.entityData.define(SKILLACTIVE, false);
	}

	final int slamTicks = 40;

	public boolean isSlamming() {
		return this.entityData.get(SLAM);
	}

	protected void updateSlamTime() {
		this.slam0 = this.slam;
		if (this.isSlamming()) {
			++this.slam;
			if (this.slam >= slamTicks) {
				this.slam = 0;
				this.entityData.set(SLAM, false);
				this.entityData.set(SKILLACTIVE, false);
			}
		} else {
			this.slam = 0;
		}
	}

	/*
	public float getSlamAnim(float p_21325_) {
		float f = this.slam - this.slam0;
		if (f < 0.0F) {
			++f;
		}
		return (float) ((this.slam0 + f * p_21325_) / slamTicks);
	}
	*/
	public float getSlamAnim(float p_29570_) {
		if (this.slam < this.slam0)
			return 1;
		return (float) (Mth.lerp(p_29570_, this.slam0, this.slam) / slamTicks);
	}

	final int shoutTicks = 120;

	public boolean isShouting() {
		return this.entityData.get(SHOUT);
	}

	protected void updateShoutingTime() {
		this.shout0 = this.shout;
		if (this.isShouting()) {
			++this.shout;
			if (this.shout >= shoutTicks) {
				this.shout = 0;
				this.entityData.set(SHOUT, false);
				this.entityData.set(SKILLACTIVE, false);
			}
		} else {
			this.shout = 0;
		}
	}

	public float getShoutAnim(float input) {
		return (float) (Mth.lerp(input, this.shout0, this.shout) / shoutTicks);
	}

	public void makeStuckInBlock(BlockState p_33796_, Vec3 p_33797_) {
	}

	public boolean isSkillActive() {
		return this.entityData.get(SKILLACTIVE);
	}

	public LavaMutantZombieEntity(EntityType<LavaMutantZombieEntity> type, Level world) {
		super(type, world);
		xpReward = 50;
		this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
		this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
		this.moveControl = new SlowRotMoveControl(this);
	}

	public boolean isAlliedTo(Entity entity) {
		if (this.getTeam() != null) {
			return super.isAlliedTo(entity);
		} else if ((entity instanceof Enemy) && (entity instanceof Mob zombie)) {
			if (zombie.getMobType() == MobType.UNDEAD && zombie.getTarget() != this)
				return true;
		}
		return super.isAlliedTo(entity);
	}

	public boolean isSensitiveToWater() {
		return true;
	}

	@Override
	protected BodyRotationControl createBodyControl() {
		return new SmartBodyHelper2(this);
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	public boolean trueReturn() {
		return true;
	}

	@Override
	protected void registerGoals() {
		registerAttackGoals();
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(4, new MoveTowardsTargetGoal(this, 1.0D, 64.0F));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0D, true, 4, this::trueReturn));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(ZombifiedPiglin.class));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractGolem.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
	}

	public void registerAttackGoals() {
		this.goalSelector.addGoal(0, new CustomMeleeAttackGoal(this));
		this.goalSelector.addGoal(0, new groundSlamAttack(this));
	}

	public class CustomMeleeAttackGoal extends Goal {
		protected final PathfinderMob mob;
		private int ticksUntilNextAttack;

		public CustomMeleeAttackGoal(PathfinderMob mob) {
			this.mob = mob;
		}

		public void tick() {
			LivingEntity livingentity = this.mob.getTarget();
			double d0 = this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
			this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
			this.checkAndPerformAttack(livingentity, d0);
		}

		public boolean canUse() {
			if (mob.getTarget() != null) {
				return !LavaMutantZombieEntity.this.entityData.get(SKILLACTIVE);
			}
			return false;
		}

		protected void checkAndPerformAttack(LivingEntity p_25557_, double p_25558_) {
			AABB attackrange = CustomMathHelper.makeAttackRange(mob.getX() + mob.getLookAngle().x, mob.getY() + mob.getLookAngle().y, mob.getZ() + mob.getLookAngle().z, mob.getEyeHeight() / 2, 2, 2, 2);
			if (attackrange.intersects(p_25557_.getBoundingBox()) && this.ticksUntilNextAttack <= 0) {
				this.resetAttackCooldown();
				this.mob.swing(InteractionHand.MAIN_HAND);
				p_25557_.setRemainingFireTicks(10);
				this.mob.doHurtTarget(p_25557_);
			}
		}

		protected void resetAttackCooldown() {
			this.ticksUntilNextAttack = this.adjustedTickDelay(30);
		}

		protected boolean isTimeToAttack() {
			return this.ticksUntilNextAttack <= 0;
		}

		protected int getTicksUntilNextAttack() {
			return this.ticksUntilNextAttack;
		}

		protected int getAttackInterval() {
			return this.adjustedTickDelay(20);
		}

		protected double getAttackReachSqr(LivingEntity p_25556_) {
			return (double) (this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + p_25556_.getBbWidth());
		}
	}

	class groundSlamAttack extends LockAngleGoal {
		int cooltime;
		boolean shooted;

		public groundSlamAttack(LavaMutantZombieEntity mob) {
			super(mob);
		}

		public boolean canUse() {
			if (mob.getTarget() != null) {
				cooltime--;
				return !LavaMutantZombieEntity.this.entityData.get(SKILLACTIVE) && mob.distanceTo(mob.getTarget()) < 14 && cooltime < 0;
			}
			return false;
		}

		public boolean canContinueToUse() {
			return LavaMutantZombieEntity.this.entityData.get(SKILLACTIVE);
		}

		public void start() {
			super.start();
			LavaMutantZombieEntity.this.entityData.set(SKILLACTIVE, true);
			LavaMutantZombieEntity.this.entityData.set(SLAM, true);
			shooted = false;
		}

		public void tick() {
			if (!shooted && (float) (LavaMutantZombieEntity.this.slam / 40f) > 0.4f) {
				shooted = true;
				mob.playSound(SoundEvents.GENERIC_EXPLODE);
				if (mob.getTarget() != null) {
					LavaWaveEntity.shoot(mob.level, mob, (float) mob.getAttributeValue(Attributes.ATTACK_DAMAGE), 2, 15, 3, mob.getYRot(), mob.position(), (float) mob.getTarget().getX(), (float) mob.getTarget().getZ());
				} else
					LavaWaveEntity.shoot(mob.level, mob, (float) mob.getAttributeValue(Attributes.ATTACK_DAMAGE), 2, 15, 3, mob.getYRot(), mob.position());
			}
		}

		public void stop() {
			cooltime = 20 + (int) (Math.random() * 40);
		}
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

	@Override
	public boolean doHurtTarget(Entity entity) {
		this.playSound(SoundEvents.BLAZE_HURT, 1, 0.5f);
		return super.doHurtTarget(entity);
	}

	public float getVoicePitch() {
		return 0.5f;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return SoundEvents.HUSK_AMBIENT;
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		//LavaWaveEntity.shoot(this.level, this, 10, 2, 1, 5, this.getYRot(), this.position());
		for (int i = 0; i < 4; ++i)
			this.level.addParticle(ParticleTypes.LAVA, this.getRandomX(0.5D), getY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
		this.playSound(SoundEvents.GENERIC_SPLASH, 0.5f, 0.5f);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return SoundEvents.HUSK_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.HUSK_DEATH;
	}

	public void aiStep() {
		super.aiStep();
		if (this.level.isClientSide) {
			if (this.random.nextInt(24) == 0 && !this.isSilent()) {
				this.level.playLocalSound(this.getX() + 0.5D, this.getY() + 0.5D, this.getZ() + 0.5D, SoundEvents.BLAZE_BURN, this.getSoundSource(), 1.0F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F, false);
			}
			for (int i = 0; i < 2; ++i) {
				this.level.addParticle(ParticleTypes.LARGE_SMOKE, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
			}
		}
		updateSlamTime();
		/*
		if (attack) {
			if (this.attackAnim > 0.8) {
				this.playSound(SoundEvents.GENERIC_EXPLODE);
				LavaWaveEntity.shoot(this.level, this, 10, 2, 10, 3, this.getYRot(), this.position());
				attack = false;
			} else if (this.getTarget() != null) {
				this.getNavigation().stop();
				this.lookAt(EntityAnchorArgument.Anchor.FEET, this.getTarget().position());
			}
		}
		*/
	}

	public boolean canStandOnFluid(FluidState p_204067_) {
		return p_204067_.is(FluidTags.LAVA);
	}

	public LavaMutantZombieEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.LAVA_MUTANT_ZOMBIE.get(), world);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 200);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 8);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.7);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 2);
		return builder;
	}
}
