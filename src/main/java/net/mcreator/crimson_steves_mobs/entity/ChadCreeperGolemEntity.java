package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MoveBackToVillageGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.Difficulty;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.SmartBodyHelper2;
import net.mcreator.crimson_steves_mobs.SlowRotMoveControl;
import net.mcreator.crimson_steves_mobs.IThrowAbility;
import net.mcreator.crimson_steves_mobs.CustomMathHelper;

import java.util.Collection;

@Mod.EventBusSubscriber
public class ChadCreeperGolemEntity extends Monster {
	private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(ChadCreeperGolemEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_IS_POWERED = SynchedEntityData.defineId(ChadCreeperGolemEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> DATA_SWELL_DIR = SynchedEntityData.defineId(ChadCreeperGolemEntity.class, EntityDataSerializers.INT);
	private int explosionRadius = 5;
	private int explosionRadius2 = 1;

	public void addAdditionalSaveData(CompoundTag p_32304_) {
		super.addAdditionalSaveData(p_32304_);
		p_32304_.putByte("attackExplosionRadius2", (byte) this.explosionRadius2);
		p_32304_.putByte("attackExplosionRadius", (byte) this.explosionRadius);
	}

	public void readAdditionalSaveData(CompoundTag p_32296_) {
		super.readAdditionalSaveData(p_32296_);
		if (p_32296_.contains("attackExplosionRadius2", 99)) {
			this.explosionRadius = p_32296_.getByte("attackExplosionRadius2");
		}
		if (p_32296_.contains("attackExplosionRadius", 99)) {
			this.explosionRadius = p_32296_.getByte("attackExplosionRadius");
		}
	}

	public ChadCreeperGolemEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.CHAD_CREEPER_GOLEM.get(), world);
	}

	public int customDeathTime;

	protected void tickDeath() {
		customDeathTime++;
		this.entityData.set(DATA_SWELL_DIR, getSwell() + 1);
		this.playSound(SoundEvents.CREEPER_HURT, 2, customDeathTime / 40 + 0.5f);
		if (customDeathTime > 80) {
			this.explodeCreeper();
		}
	}

	private void explodeCreeper() {
		if (!this.level.isClientSide) {
			Explosion.BlockInteraction explosion$blockinteraction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
			float f = this.isPowered() ? 2.0F : 1.0F;
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++)
					this.level.explode(this, this.getX() + (i - 1) * 3, this.getY() + 1, this.getZ() + (j - 1) * 3, (float) this.explosionRadius * f, explosion$blockinteraction);
			this.discard();
			this.spawnLingeringCloud(this);
		}
	}

	private void spawnLingeringCloud(Entity entity) {
		Collection<MobEffectInstance> collection = this.getActiveEffects();
		if (!collection.isEmpty()) {
			AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level, entity.getX(), entity.getY(), entity.getZ());
			areaeffectcloud.setRadius(1.5F);
			areaeffectcloud.setRadiusOnUse(-0.5F);
			areaeffectcloud.setWaitTime(5);
			areaeffectcloud.setDuration(areaeffectcloud.getDuration() / 2);
			areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float) areaeffectcloud.getDuration());
			for (MobEffectInstance mobeffectinstance : collection) {
				areaeffectcloud.addEffect(new MobEffectInstance(mobeffectinstance));
			}
			this.level.addFreshEntity(areaeffectcloud);
		}
	}

	protected void dropFromLootTable(DamageSource p_21021_, boolean p_21022_) {
		ResourceLocation resourcelocation = this.getLootTable();
		LootTable loottable = this.level.getServer().getLootTables().get(resourcelocation);
		LootContext.Builder lootcontext$builder = this.createLootContext(p_21022_, p_21021_);
		LootContext ctx = lootcontext$builder.create(LootContextParamSets.ENTITY);
		for (int i = 0; i < 10; i++)
			loottable.getRandomItems(ctx).forEach(this::spawnAtLocation);
	}

	public ResourceLocation getDefaultLootTable() {
		return EntityType.CREEPER.getDefaultLootTable();
	}

	public boolean isPowered() {
		return this.entityData.get(DATA_IS_POWERED);
	}

	public void tick() {
		super.tick();
		if (this.isDeadOrDying())
			this.dead = true;
	}

	protected SoundEvent getHurtSound(DamageSource p_32309_) {
		return SoundEvents.CREEPER_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.CREEPER_DEATH;
	}

	public ChadCreeperGolemEntity(EntityType<ChadCreeperGolemEntity> type, Level world) {
		super(type, world);
		xpReward = 40;
		this.refreshDimensions();
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

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ATTACKING, false);
		this.entityData.define(DATA_IS_POWERED, false);
		this.entityData.define(DATA_SWELL_DIR, 0);
	}

	public int getSwell() {
		return this.entityData.get(DATA_SWELL_DIR);
	}

	public void setAttacking(boolean input) {
		this.entityData.set(ATTACKING, input);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) {
			public void tick() {
				super.tick();
				if (ChadCreeperGolemEntity.this.getTarget() != null && ChadCreeperGolemEntity.this.entityData.get(ATTACKING)) {
					ChadCreeperGolemEntity.this.lookAt(EntityAnchorArgument.Anchor.FEET, ChadCreeperGolemEntity.this.getTarget().position());
				}
			}

			protected void checkAndPerformAttack(LivingEntity p_25557_, double p_25558_) {
				double d0 = this.getAttackReachSqr(p_25557_) * 1.3;
				if (p_25558_ <= d0 && !ChadCreeperGolemEntity.this.entityData.get(ATTACKING) && ChadCreeperGolemEntity.this.attackCoolTime <= 0) {
					//this.resetAttackCooldown();
					ChadCreeperGolemEntity.this.entityData.set(ATTACKING, true);
					ChadCreeperGolemEntity.this.attackCoolTime = 20 + Mth.randomBetweenInclusive(RandomSource.create(), 0, 20);
					/*
					this.mob.swing(InteractionHand.MAIN_HAND);
					this.mob.doHurtTarget(p_25557_);
					*/
				}
			}

			protected double getAttackReachSqr(LivingEntity entity) {
				if (!mob.swinging) {
					return CustomMathHelper.isEntityInBox(entity, mob, 1.8) ? Double.POSITIVE_INFINITY : super.getAttackReachSqr(entity);
				}
				return -1;
			}
		});
		this.goalSelector.addGoal(2, new MoveBackToVillageGoal(this, 1D, false));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	protected double getAttackReachSqr(LivingEntity entity) {
		return CustomMathHelper.isEntityInBox(entity, this, 1.2) ? Double.POSITIVE_INFINITY : (double) (this.getBbWidth() * this.getBbWidth() * 2.25F + entity.getBbWidth());
	}

	public int attackCoolTime;

	public void aiStep() {
		super.aiStep();
		if (this.getDeltaMovement().horizontalDistanceSqr() > (double) 2.5000003E-7F && this.random.nextInt(4) == 0) {
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
		float shiftDownAmount = this.getPersistentData().getFloat("ShiftDownAmount");
		float attackReadyAnimationAmount = this.getPersistentData().getFloat("attackReadyAnimationAmount");
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
						if (!this.level.isClientSide) {
							Explosion.BlockInteraction explosion$blockinteraction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
							float f = this.isPowered() ? 2.0F : 1.0F;
							this.level.explode(this, this.getX() + this.getLookAngle().x * 1.5, this.getY() + 1 + this.getLookAngle().y * 1.5, this.getZ() + this.getLookAngle().z * 1.5, (float) this.explosionRadius2 * f, explosion$blockinteraction);
						}
					}
				} else {
					this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 0.5f);
					if (!this.level.isClientSide) {
						Explosion.BlockInteraction explosion$blockinteraction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
						float f = this.isPowered() ? 2.0F : 1.0F;
						this.level.explode(this, this.getX() + this.getLookAngle().x * 1.5, this.getY() + 1 + this.getLookAngle().y * 1.5, this.getZ() + this.getLookAngle().z * 1.5, (float) this.explosionRadius2 * f, explosion$blockinteraction);
					}
				}
			}
		} else {
			if (attackCoolTime > 0)
				attackCoolTime--;
			attackReadyAnimationAmount = 0;
		}
		if (shiftDownAmount != this.getPersistentData().getFloat("ShiftDownAmount")) {
			this.getPersistentData().putFloat("ShiftDownAmount", shiftDownAmount);
		}
		if (attackReadyAnimationAmount != this.getPersistentData().getFloat("attackReadyAnimationAmount")) {
			this.getPersistentData().putFloat("attackReadyAnimationAmount", attackReadyAnimationAmount);
		}
	}

	public boolean doHurtTarget(Entity p_34491_) {
		//this.playSound(SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR, 1.0F, 0.5f);
		/*
		AABB attackrange = CustomMathHelper.makeAttackRange(p_34491_.getX(), p_34491_.getY(), p_34491_.getZ(), 0, 2, 2, 2);
		for (LivingEntity livingentity : this.level.getEntitiesOfClass(LivingEntity.class, attackrange)) {
			if ((!(livingentity instanceof Enemy) && !this.isAlliedTo(livingentity)) || this.getTarget() == livingentity) {
				IThrowAbility.hurtAndThrowTargetVerticallyCustom(this, livingentity, (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) / 3, this.getAttributeValue(Attributes.ATTACK_KNOCKBACK) / 2);
				livingentity.invulnerableTime = 0;
			}
		}
		*/
		if (!this.level.isClientSide) {
			Explosion.BlockInteraction explosion$blockinteraction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
			float f = this.isPowered() ? 2.0F : 1.0F;
			this.level.explode(this, p_34491_.getX(), p_34491_.getY() + p_34491_.getEyeHeight(), p_34491_.getZ(), (float) this.explosionRadius2 * f, explosion$blockinteraction);
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

	public float getScale() {
		return 0.95f;
	}

	public void makeStuckInBlock(BlockState p_33796_, Vec3 p_33797_) {
	}

	public static void init() {
		SpawnPlacements.register(CrimsonStevesMobsModEntities.CHAD_CREEPER_GOLEM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos,
				random) -> (world.getDifficulty() != Difficulty.PEACEFUL && CustomMathHelper.isOverworld(world) && Monster.isDarkEnoughToSpawn(world, pos, random) && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)));
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 130);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 8);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.7);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1.5);
		return builder;
	}
}
