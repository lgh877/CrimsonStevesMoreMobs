
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.BlockPos;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.SmartBodyHelper2;
import net.mcreator.crimson_steves_mobs.SlowRotMoveControl;
import net.mcreator.crimson_steves_mobs.CustomMathHelper;

public class CrudeRedstoneGolemEntity extends Raider {
	public float eyeLightAmount;
	private static final EntityDataAccessor<Boolean> RAGE = SynchedEntityData.defineId(CrudeRedstoneGolemEntity.class, EntityDataSerializers.BOOLEAN);

	public CrudeRedstoneGolemEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.CRUDE_REDSTONE_GOLEM.get(), world);
	}

	public void makeStuckInBlock(BlockState p_33796_, Vec3 p_33797_) {
		if (this.tickCount % 3 == 0)
			super.makeStuckInBlock(p_33796_, p_33797_);
	}

	public CrudeRedstoneGolemEntity(EntityType<? extends CrudeRedstoneGolemEntity> type, Level world) {
		super(type, world);
		this.maxUpStep = this.getScale();
		xpReward = 25;
		this.moveControl = new SlowRotMoveControl(this);
		setPersistenceRequired();
	}

	protected float getAttackDamage() {
		return (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
	}

	@Override
	protected BodyRotationControl createBodyControl() {
		return new SmartBodyHelper2(this);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(RAGE, false);
	}

	public void addAdditionalSaveData(CompoundTag p_33619_) {
		super.addAdditionalSaveData(p_33619_);
		p_33619_.putBoolean("isRaged", this.entityData.get(RAGE));
	}

	public void readAdditionalSaveData(CompoundTag p_33607_) {
		super.readAdditionalSaveData(p_33607_);
		this.entityData.set(RAGE, p_33607_.getBoolean("isRaged"));
	}

	public int getMaxHeadYRot() {
		return 33;
	}

	public boolean canBeLeader() {
		return false;
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

	protected PathNavigation createNavigation(Level p_33348_) {
		if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(p_33348_, this))
			return new TRabusEntity.RavagerNavigation(this, p_33348_);
		else
			return new GroundPathNavigation(this, p_33348_);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.2, false) {
			protected double getAttackReachSqr(LivingEntity entity) {
				if (mob.swinging || !mob.getSensing().hasLineOfSight(entity))
					return -1;
				if (CustomMathHelper.isEntityInBox(entity, mob, mob.getScale())) {
					return Double.POSITIVE_INFINITY;
				}
				float f = mob.getBbWidth() - 0.1F;
				return (double) (f * f + entity.getBbWidth() * entity.getBbWidth()) * 2;
			}
		});
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
	}

	public void applyRaidBuffs(int p_34079_, boolean p_34080_) {
	}

	public void aiStep() {
		super.aiStep();
		if (this.eyeLightAmount < 1) {
			this.eyeLightAmount = Mth.lerp(0.06f, this.eyeLightAmount, 1);
		}
		if (this.getEntityData().get(RAGE)) {
			this.setSprinting(true);
			if (this.getScale() >= 1 && this.tickCount % 2 == 0)
				for (int i = 0; i < (int) this.getScale(); i++)
					this.level.addParticle(ParticleTypes.LAVA, this.getRandomX(this.getBbWidth() / 2), this.getRandomY(), this.getRandomZ(this.getBbWidth() / 2), 0.0D, 0.0D, 0.0D);
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
		return super.doHurtTarget(entity);
	}

	public boolean hurt(DamageSource source, float amount) {
		boolean flag = super.hurt(source, amount);
		if (flag && this.getHealth() < this.getMaxHealth() * 0.4f && !this.getEntityData().get(RAGE)) {
			this.getEntityData().set(RAGE, true);
		}
		return flag;
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
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:redstonegolem_growl"));
	}

	@Override
	public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:redstonegolem_idle"));
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:redstonegolem_step")), 0.15f, 1);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:redstonegolem_hit"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:redstonegolem_death"));
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.22);
		builder = builder.add(Attributes.MAX_HEALTH, 125);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		builder = builder.add(Attributes.ARMOR, 8);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 12);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.6);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1);
		return builder;
	}
}
