package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
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
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.SmartBodyHelper2;
import net.mcreator.crimson_steves_mobs.SlowRotMoveControl;
import net.mcreator.crimson_steves_mobs.LockAngleGoal;
import net.mcreator.crimson_steves_mobs.IMutantZombie;

public class LavaMutantZombieEntity extends Monster implements IMutantZombie {
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
	}

	public boolean isSlamming() {
		return this.entityData.get(SLAM);
	}

	public boolean isShouting() {
		return this.entityData.get(SHOUT);
	}

	public void makeStuckInBlock(BlockState p_33796_, Vec3 p_33797_) {
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
		this.goalSelector.addGoal(1, new FloatGoal(this));
		registerAttackGoals();
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
	}

	class groundSlamAttack extends LockAngleGoal {
		public groundSlamAttack(LavaMutantZombieEntity mob) {
			super(mob);
		}
	}

	boolean attack;

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

	/*
	@Override
	public boolean doHurtTarget(Entity entity) {
		attack = true;
		return false;
	}
	*/
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
		this.playSound(SoundEvents.LAVA_AMBIENT, 1.5f, 0.5f);
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
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1.5);
		return builder;
	}
}
