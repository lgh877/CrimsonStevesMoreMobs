
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.CustomMathHelper;

import java.util.EnumSet;

public class CrudeRedstoneMonstrosityEntity extends CrudeRedstoneGolemEntity implements RangedAttackMob {
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.RED, ServerBossEvent.BossBarOverlay.PROGRESS);
	private int waitTicks;
	private byte attackType;
	private static final EntityDataAccessor<Float> SQUASH_AMOUNT = SynchedEntityData.defineId(CrudeRedstoneMonstrosityEntity.class, EntityDataSerializers.FLOAT);

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SQUASH_AMOUNT, 0f);
	}

	public void makeStuckInBlock(BlockState p_33796_, Vec3 p_33797_) {
	}

	public CrudeRedstoneMonstrosityEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.CRUDE_REDSTONE_MONSTROSITY.get(), world);
	}

	protected void updateControlFlags() {
		//boolean flag1 = !(this.getVehicle() instanceof Boat);
		this.goalSelector.setControlFlag(Goal.Flag.MOVE, true);
		this.goalSelector.setControlFlag(Goal.Flag.JUMP, true);
		this.goalSelector.setControlFlag(Goal.Flag.LOOK, true);
		this.goalSelector.setControlFlag(Goal.Flag.TARGET, true);
	}

	public CrudeRedstoneMonstrosityEntity(EntityType<CrudeRedstoneMonstrosityEntity> type, Level world) {
		super(type, world);
		this.refreshDimensions();
		xpReward = 100;
		//this.maxUpStep = 2;
		this.setShiftKeyDown(false);
	}

	public float getScale() {
		return 2;
	}

	public float getVoicePitch() {
		return 1.4f;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new stopAndLookTargetGoal());
	}

	class stopAndLookTargetGoal extends Goal {
		CrudeRedstoneMonstrosityEntity mob = CrudeRedstoneMonstrosityEntity.this;

		public stopAndLookTargetGoal() {
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
		}

		public boolean canUse() {
			return mob.isShiftKeyDown();
		}

		public boolean canContinueToUse() {
			return mob.isShiftKeyDown();
		}

		public void start() {
			mob.getNavigation().stop();
		}

		public void tick() {
			if (mob.getTarget() != null) {
				mob.lookAt(EntityAnchorArgument.Anchor.FEET, mob.getTarget().position());
				mob.lookAt(EntityAnchorArgument.Anchor.EYES, mob.getTarget().position());
			}
		}
	}

	public void performRangedAttack(LivingEntity p_29912_, float p_29913_) {
		RedstoneBombEntity snowball = new RedstoneBombEntity(CrimsonStevesMobsModEntities.REDSTONE_BOMB.get(), this, this.level);
		double d0 = p_29912_.getY() + 2.2;
		double d1 = p_29912_.getX() - this.getX();
		double d2 = d0 - snowball.getY();
		double d3 = p_29912_.getZ() - this.getZ();
		double d4 = Math.sqrt(d1 * d1 + d3 * d3) * (double) 0.2F;
		snowball.moveTo(this.getX(), this.getY() + 2.2, this.getZ());
		snowball.shoot(d1, d2 + d4, d3, 1.6F, p_29913_);
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_shoot")), 1.5F, 1);
		this.level.addFreshEntity(snowball);
	}

	public void performRangedAttack(float p_29913_) {
		RedstoneBombEntity snowball = new RedstoneBombEntity(CrimsonStevesMobsModEntities.REDSTONE_BOMB.get(), this, this.level);
		snowball.shoot(this.getLookAngle().x, this.getLookAngle().y - 2, this.getLookAngle().z, 1.6F, p_29913_);
		snowball.moveTo(this.getX(), this.getY() + 2.2, this.getZ());
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_shoot")), 1.5F, 1);
		this.level.addFreshEntity(snowball);
	}

	@Override
	public MobType getMobType() {
		return MobType.ILLAGER;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_idle"));
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_step")), 0.5f, 1);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_death"));
	}

	@Override
	public boolean canChangeDimensions() {
		return false;
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

	protected double getAttackReachSqr(LivingEntity entity) {
		if (CustomMathHelper.isEntityInBox(entity, this, 1)) {
			return Double.POSITIVE_INFINITY;
		}
		float f = this.getBbWidth() - 0.1F;
		return (double) (f * f + entity.getBbWidth() * entity.getBbWidth()) * 2;
	}

	private int jumpCooldown = 160;
	public int jumpCount = 0;
	private int jumpIntervalTimer = 0;
	private Vec3 jumpTargetVec;

	public boolean causeFallDamage(float p_148711_, float p_148712_, DamageSource p_148713_) {
		return false;
	}

	public float getSquashAmount() {
		return this.entityData.get(SQUASH_AMOUNT);
	}

	public void setSquashAmount(float value) {
		this.entityData.set(SQUASH_AMOUNT, value);
	}

	public void aiStep() {
		super.aiStep();
		float shiftDownAmount = this.getPersistentData().getFloat("ShiftDownAmount");
		if (this.isShiftKeyDown()) {
			shiftDownAmount = Mth.lerp(0.2f, shiftDownAmount, 1);
		} else if (shiftDownAmount > 0) {
			shiftDownAmount = Mth.lerp(0.2f, shiftDownAmount, 0);
		}
		if (shiftDownAmount != this.getPersistentData().getFloat("ShiftDownAmount")) {
			this.getPersistentData().putFloat("ShiftDownAmount", shiftDownAmount);
		}
		if (this.entityData.get(SQUASH_AMOUNT) > 0) {
			this.entityData.set(SQUASH_AMOUNT, this.entityData.get(SQUASH_AMOUNT) - 1);
		}
	}

	@Override
	public void customServerAiStep() {
		super.customServerAiStep();
		if (this.isAlive()) {
			if (this.getTarget() != null) {
				if (jumpCooldown > 0)
					jumpCooldown--;
				if (jumpCooldown <= 0)
					jumpTargetVec = this.getTarget().position();
				if (this.waitTicks == 0 && !this.isShiftKeyDown()) {
					this.setShiftKeyDown(true);
					this.waitTicks = 230;
					this.attackType = (byte) this.random.nextInt(2);
					this.swing(InteractionHand.MAIN_HAND);
					this.playSound(SoundEvents.PISTON_EXTEND, 1.5f, 0.5f);
				}
			}
			if (jumpCooldown <= 0) {
				if (jumpCount < 5) {
					jumpIntervalTimer--;
					if (jumpIntervalTimer <= 0 && (!this.level.isEmptyBlock(this.blockPosition().offset(0, -0.1, 0)) || this.isOnGround())) {
						jumpCount++;
						jumpIntervalTimer = 15;
						if (jumpCount < 5) {
							setDeltaMovement((jumpTargetVec.x - getX()) * 0.15, 1.2, (jumpTargetVec.z - getZ()) * 0.15);
							hurtMarked = true;
						}
						if (jumpCount > 1) {
							this.playSound(SoundEvents.GENERIC_EXPLODE, 1.5f, 0.5f);
							for (int i = 0; i < 18; i++)
								EarthQuakeEntity.shoot(this.level, this, getAttackDamage() * 0.5f, 3, 18, 2, 20 * i);
							setSquashAmount(10);
						}
					}
				} else {
					jumpCooldown = 160 + this.random.nextInt(160);
					jumpCount = 0;
					jumpIntervalTimer = 15;
				}
			}
			if (this.waitTicks > 0)
				this.waitTicks--;
			if (this.waitTicks == 165 && this.isShiftKeyDown()) {
				this.setShiftKeyDown(false);
				this.playSound(SoundEvents.PISTON_CONTRACT, 1.5f, 0.5f);
			}
			if (this.isShiftKeyDown() && this.waitTicks < 215) {
				if (this.attackType == 0 && this.waitTicks % 7 == 5) {
					if (this.level instanceof ServerLevel _level) {
						_level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX(), this.getY() + 2.8, this.getZ(), 10, 0, 0, 0, 0.1f);
						_level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + this.getLookAngle().x * 3, this.getY() + 2.8 + this.getLookAngle().y * 3, this.getZ() + this.getLookAngle().z * 3, 10, 0, 0, 0, 0.2f);
						_level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + this.getLookAngle().x * 6, this.getY() + 2.8 + this.getLookAngle().y * 6, this.getZ() + this.getLookAngle().z * 6, 10, 0, 0, 0, 0.3f);
					}
					if (this.getTarget() != null)
						this.performRangedAttack(this.getTarget(), 30);
					else
						this.performRangedAttack(30);
				} else if (this.attackType == 1 && this.waitTicks % 11 == 7) {
					this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:monstrosity_shoot")), 1.5F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
					if (this.level instanceof ServerLevel _level) {
						MinionRedstoneGolemEntity entityToSpawn = new MinionRedstoneGolemEntity(CrimsonStevesMobsModEntities.MINION_REDSTONE_GOLEM.get(), _level);
						entityToSpawn.moveTo(this.getX(), this.getY() + 2.2, this.getZ(), this.getYRot(), 0);
						entityToSpawn.setOwner(this);
						entityToSpawn.push(this.getLookAngle().x, 0.4, this.getLookAngle().z);
						entityToSpawn.finalizeSpawn(_level, this.level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
						this.addMobToTeam(entityToSpawn);
						this.level.addFreshEntity(entityToSpawn);
					}
				}
			}
		}
		this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
	}

	public void addMobToTeam(Entity mob) {
		if (this.getTeam() != null) {
			this.level.getScoreboard().addPlayerToTeam(mob.getStringUUID(), this.level.getScoreboard().getPlayerTeam(this.getTeam().getName()));
		}
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
		builder = builder.add(Attributes.MAX_HEALTH, 350);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.ARMOR, 10);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 12);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.9);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 2);
		return builder;
	}
}
