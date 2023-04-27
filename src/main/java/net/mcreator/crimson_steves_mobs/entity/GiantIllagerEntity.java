
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.animal.IronGolem;
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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.SmartBodyHelper2;
import net.mcreator.crimson_steves_mobs.SlowRotMoveControl;
import net.mcreator.crimson_steves_mobs.ICanBeAnimated;
import net.mcreator.crimson_steves_mobs.CustomMathHelper;

import java.util.EnumSet;

public class GiantIllagerEntity extends Raider implements ICanBeAnimated {
	public AnimationState rightSlamAnimationState = new AnimationState();
	private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(GiantIllagerEntity.class, EntityDataSerializers.INT);

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ATTACK_STATE, 0);
	}

	public void makeStuckInBlock(BlockState p_33796_, Vec3 p_33797_) {
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

	public void setAttackState(int input) {
		this.entityData.set(ATTACK_STATE, input);
	}

	public AnimationState getAnimationState(String input) {
		if (input == "rightSlam")
			return rightSlamAnimationState;
		return new AnimationState();
	}

	public void onSyncedDataUpdated(EntityDataAccessor<?> p_219422_) {
		if (ATTACK_STATE.equals(p_219422_)) {
			if (this.level.isClientSide)
				switch (this.entityData.get(ATTACK_STATE)) {
					case 0 :
						break;
					case 1 :
						this.rightSlamAnimationState.start(this.tickCount);
						break;
				}
		}
	}

	public int maxSwingTime() {
		int as = this.entityData.get(ATTACK_STATE);
		int i = 0;
		if (as == 1) {
			i = 41;
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

	public GiantIllagerEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.GIANT_ILLAGER.get(), world);
	}

	public GiantIllagerEntity(EntityType<GiantIllagerEntity> type, Level world) {
		super(type, world);
		xpReward = 400;
		this.moveControl = new SlowRotMoveControl(this);
		this.discard();
	}

	@Override
	protected BodyRotationControl createBodyControl() {
		return new SmartBodyHelper2(this);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
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

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new singleSlamAttackGoal(this));
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				if (CustomMathHelper.isEntityInBox(entity, mob, 4) && !mob.swinging) {
					return Double.POSITIVE_INFINITY;
				}
				return -1;
			}
		});
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.4D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
		this.targetSelector.addGoal(2, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true, (p_199899_) -> {
			return !p_199899_.isBaby();
		}));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	public boolean doHurtTarget(Entity entity) {
		this.setAttackState(1);
		return false;
	}

	class singleSlamAttackGoal extends LockAngleGoal {
		int attackprogress;

		public singleSlamAttackGoal(GiantIllagerEntity mob) {
			super(mob);
		}

		public boolean canUse() {
			int as = this.mob.entityData.get(ATTACK_STATE);
			return super.canUse() && as == 1;
		}

		public void start() {
			super.start();
			attackprogress = 0;
		}

		public void tick() {
			if (attackprogress == 0) {
				attackprogress++;
				mob.playSound(SoundEvents.RAVAGER_ROAR, 2f, mob.getVoicePitch());
			} else if (mob.attackAnim > 0.5f && attackprogress == 1) {
				attackprogress++;
				mob.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 2f, 0.5f);
			} else if (mob.attackAnim > 0.6f && attackprogress == 2) {
				attackprogress++;
				mob.playSound(SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR, 2f, 0.5f);
				mob.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:rock_smash")), 2f, 0.5f);
				AABB attackrange = CustomMathHelper.makeAttackRange(mob.getX() + mob.getHorizontalLookAngle().x * 3, mob.getY(), mob.getZ() + mob.getHorizontalLookAngle().z * 3, 0, 6, 6, 6);
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

	public boolean defaultAttack(Entity entity) {
		return super.doHurtTarget(entity);
	}

	public Vec3 getHorizontalLookAngle() {
		return this.calculateViewVector(0, this.getYRot());
	}

	public Vec3 getHorizontalLeftLookAngle() {
		return this.calculateViewVector(0, this.getYRot() - 90);
	}

	public void applyRaidBuffs(int p_33337_, boolean p_33338_) {
	}

	public boolean canBeLeader() {
		return false;
	}

	@Override
	public MobType getMobType() {
		return MobType.ILLAGER;
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	public SoundEvent getCelebrateSound() {
		return SoundEvents.RAVAGER_CELEBRATE;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ravager.ambient"));
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ravager.step")), 1f, 0.5f);
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
	}

	public class LockAngleGoal extends Goal {
		final GiantIllagerEntity mob;

		public LockAngleGoal(GiantIllagerEntity mob) {
			this.mob = mob;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		public boolean canUse() {
			return mob.swinging;
		}

		public void start() {
			mob.getNavigation().stop();
			if (mob.getTarget() != null) {
				mob.lookAt(EntityAnchorArgument.Anchor.FEET, mob.getTarget().position());
			}
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 500);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 20);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.8);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 5);
		return builder;
	}
}
