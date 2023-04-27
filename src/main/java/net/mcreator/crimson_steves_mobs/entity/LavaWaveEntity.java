
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.CustomMathHelper;

public class LavaWaveEntity extends ThrowableItemProjectile {
	//private static final EntityDataAccessor<Integer> SHOCK_RANGE = SynchedEntityData.defineId(EarthQuakeEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> SIZE = SynchedEntityData.defineId(LavaWaveEntity.class, EntityDataSerializers.FLOAT);
	//public float size = 2;
	public short shockRange = 10;
	public float damage = 5;
	public short shockSpeed = 5;
	public short yaw = 0;
	private Vec3 lookVec = this.getLookAngle();
	public float xDir = 0;
	public float zDir = 0;
	public float initialXDir = 0;
	public float initialZDir = 0;

	/*
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOCK_RANGE, 0);
	}
	*/
	//private short lifeTime;
	public LavaWaveEntity(PlayMessages.SpawnEntity packet, Level world) {
		super(CrimsonStevesMobsModEntities.LAVA_WAVE.get(), world);
	}

	public LavaWaveEntity(EntityType<? extends LavaWaveEntity> type, Level world) {
		super(type, world);
		//this.setInvisible(true);
		//this.refreshDimensions();
	}

	public LavaWaveEntity(Level p_37399_, LivingEntity p_37400_) {
		super(CrimsonStevesMobsModEntities.LAVA_WAVE.get(), p_37400_, p_37399_);
	}

	public LavaWaveEntity(Level p_37394_, double p_37395_, double p_37396_, double p_37397_) {
		super(CrimsonStevesMobsModEntities.LAVA_WAVE.get(), p_37395_, p_37396_, p_37397_, p_37394_);
	}

	/*
	public EntityDimensions getDimensions(Pose pose) {
		return new EntityDimensions(size, 0.5f, true);
	}
	*/
	public void addAdditionalSaveData(CompoundTag p_32304_) {
		super.addAdditionalSaveData(p_32304_);
		p_32304_.putShort("shockRange", this.shockRange);
		//p_32304_.putShort("lifeTime", this.lifeTime);
		p_32304_.putShort("shockSpeed", this.shockSpeed);
		p_32304_.putShort("yaw", this.yaw);
		p_32304_.putFloat("damage", this.damage);
		p_32304_.putFloat("xDir", this.xDir);
		p_32304_.putFloat("zDir", this.zDir);
		p_32304_.putFloat("initialXDir", this.initialXDir);
		p_32304_.putFloat("initialZDir", this.initialZDir);
		p_32304_.putFloat("size", this.entityData.get(SIZE));
	}

	public void readAdditionalSaveData(CompoundTag p_32296_) {
		super.readAdditionalSaveData(p_32296_);
		this.shockRange = p_32296_.getShort("shockRange");
		//this.lifeTime = p_32296_.getShort("lifeTime");
		this.shockSpeed = p_32296_.getShort("shockSpeed");
		this.yaw = p_32296_.getShort("yaw");
		this.damage = p_32296_.getFloat("damage");
		this.xDir = p_32296_.getFloat("xDir");
		this.zDir = p_32296_.getFloat("zDir");
		this.initialXDir = p_32296_.getFloat("initialXDir");
		this.initialZDir = p_32296_.getFloat("initialZDir");
		this.entityData.set(SIZE, p_32296_.getFloat("size"));
		this.lookVec = this.calculateViewVector(0, this.yaw);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SIZE, 2f);
	}

	public boolean isPickable() {
		return false;
	}

	public boolean isOnFire() {
		return false;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	protected Item getDefaultItem() {
		return Items.AIR;
	}

	public boolean isNoGravity() {
		return true;
	}

	protected BlockPos getNextPosition() {
		return new BlockPos(this.getX() + this.lookVec.x * this.entityData.get(SIZE) / 2, this.getY(), this.getZ() + this.lookVec.z * this.entityData.get(SIZE) / 2);
	}

	@Override
	public void tick() {
		super.tick();
		int i = Mth.floor(this.getX());
		int j = Mth.floor(this.getY() - (double) 0.2F);
		int k = Mth.floor(this.getZ());
		BlockPos pos = new BlockPos(i, j, k);
		BlockState blockstate5 = this.level.getBlockState(pos);
		if (!blockstate5.isAir()) {
			if (this.tickCount % 10 == 0) {
				this.playSound(SoundEvents.BLAZE_SHOOT, 1, 0.5f);
			}
			for (int p = 0; p < 12 * Mth.clamp((int) Math.pow((double) this.entityData.get(SIZE), 1.5), 1, Integer.MAX_VALUE); p++)
				this.level.addParticle(ParticleTypes.DRIPPING_LAVA, this.getX() + ((double) this.random.nextFloat() - 0.5D) * (double) this.entityData.get(SIZE), this.getY() + 0.3 * Math.random(),
						this.getZ() + ((double) this.random.nextFloat() - 0.5D) * (double) this.entityData.get(SIZE), 4.0D * ((double) this.random.nextFloat() - 0.5D), (double) this.random.nextFloat() * 16 + 0.7D,
						((double) this.random.nextFloat() - 0.5D) * 4.0D);
			for (int p = 0; p < 2 * Mth.clamp((int) Math.pow((double) this.entityData.get(SIZE), 1.5), 1, Integer.MAX_VALUE); p++)
				this.level.addParticle(ParticleTypes.LAVA, this.getX() + ((double) this.random.nextFloat() - 0.5D) * (double) this.entityData.get(SIZE), this.getY() + 0.2,
						this.getZ() + ((double) this.random.nextFloat() - 0.5D) * (double) this.entityData.get(SIZE), 4.0D * ((double) this.random.nextFloat() - 0.5D), 0, ((double) this.random.nextFloat() - 0.5D) * 4.0D);
		}
		if (!this.level.isClientSide)
			if (this.tickCount % this.shockSpeed == 0) {
				this.lookVec = new Vec3(initialXDir, 0, initialZDir).normalize();
				Vec3 dirD = new Vec3(xDir - this.getX(), 0, zDir - this.getZ()).normalize();
				initialXDir += dirD.x * 2;
				initialZDir += dirD.z * 2;
				//this.moveTo(this.getX() + this.getLookAngle().x * size / 2, this.getY(), this.getZ() + this.getLookAngle().z * size / 2);
				BlockPos blockpos = this.getNextPosition();
				boolean flag = false;
				double d0 = 0.0D;
				do {
					BlockPos blockpos1 = blockpos.below();
					BlockState blockstate = this.level.getBlockState(blockpos1);
					if (blockstate.isFaceSturdy(this.level, blockpos1, Direction.UP)) {
						if (!this.level.isEmptyBlock(blockpos)) {
							BlockState blockstate1 = this.level.getBlockState(blockpos);
							VoxelShape voxelshape = blockstate1.getCollisionShape(this.level, blockpos);
							if (!voxelshape.isEmpty()) {
								d0 = voxelshape.max(Direction.Axis.Y);
							}
						}
						flag = true;
						break;
					}
					blockpos = blockpos.below();
				} while (blockpos.getY() >= Mth.floor(this.getY()) - 3);
				if (flag) {
					//this.setPos(this.getX() + this.lookVec.x * size / 2, blockpos.getY() + d0, this.getZ() + this.lookVec.z * size / 2);
					this.setPos(this.getX() + this.lookVec.x, blockpos.getY() + d0, this.getZ() + this.lookVec.z);
					AABB attackRange = CustomMathHelper.makeAttackRange(this.getX(), this.getY(), this.getZ(), 0, this.entityData.get(SIZE), 0.6, this.entityData.get(SIZE));
					for (LivingEntity livingentity : this.level.getEntitiesOfClass(LivingEntity.class, attackRange)) {
						Entity owner = this.getOwner();
						if (owner == null) {
							if (livingentity.hurt(DamageSource.indirectMobAttack(this, null).setIsFire(), this.damage)) {
								this.strongKnockback(livingentity, 0.5);
								livingentity.setSecondsOnFire(15);
							} else {
								livingentity.hurt(DamageSource.indirectMobAttack(this, null), this.damage / 2);
								this.strongKnockback(livingentity, 0.5);
								livingentity.setSecondsOnFire(15);
							}
						} else {
							if (livingentity != owner)
								if (!livingentity.isAlliedTo(owner) && livingentity.hurt(DamageSource.indirectMobAttack(this, (LivingEntity) this.getOwner()).setIsFire(), this.damage)) {
									this.strongKnockback(livingentity, 0.5);
									livingentity.setSecondsOnFire(15);
								} else {
									livingentity.hurt(DamageSource.indirectMobAttack(this, (LivingEntity) this.getOwner()), this.damage / 2);
									this.strongKnockback(livingentity, 0.5);
									livingentity.setSecondsOnFire(15);
								}
						}
					}
				} else {
					this.discard();
				}
				/*
				BlockState blockstate = this.level.getBlockState(blockpos);
				if (!blockstate.isAir()) {
					for (int i = 0; i < 10 * size; i++)
						this.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate).setPos(blockpos),
								this.getX() + ((double) this.random.nextFloat() - 0.5D) * (double) size, this.getY(),
								this.getZ() + ((double) this.random.nextFloat() - 0.5D) * (double) size, 4.0D * ((double) this.random.nextFloat() - 0.5D),
								0.5D, ((double) this.random.nextFloat() - 0.5D) * 4.0D);
				}
				*/
				if (this.shockRange > 0)
					this.shockRange--;
				else
					this.discard();
			}
	}

	public void setDeltaMovement(double a1, double a2, double a3) {
	}

	private void strongKnockback(Entity p_33340_, double modifier) {
		double d0 = (p_33340_.getX() - this.getX());
		double d1 = (p_33340_.getZ() - this.getZ());
		double d2 = Math.max(d0 * d0 + d1 * d1, 0.001D) * 2;
		p_33340_.push(d0 * modifier / d2, 0.5D * modifier, d1 * modifier / d2);
	}

	public static LavaWaveEntity shoot(Level world, LivingEntity entity, RandomSource random, int shockSpeed, float damage, int knockback) {
		LavaWaveEntity entityarrow = new LavaWaveEntity(world, entity);
		//entityarrow.setRot(0, entity.getYRot());
		entityarrow.moveTo(entity.getX(), entity.getY(), entity.getZ());
		/*
		entityarrow.getPersistentData().putFloat("size", 2);
		entityarrow.getPersistentData().putFloat("damage", 8);
		entityarrow.getPersistentData().putShort("shockRange", (short) 10);
		entityarrow.getPersistentData().putShort("shockSpeed", (short) 5);
		*/
		entityarrow.damage = damage;
		entityarrow.shockRange = 30;
		entityarrow.shockSpeed = (short) shockSpeed;
		//entityarrow.getPersistentData().putShort("yaw", (short) entity.getYRot());
		entityarrow.yaw = (short) (entity.getYRot() + ((random.nextFloat() - 0.5D) * knockback));
		world.addFreshEntity(entityarrow);
		return entityarrow;
	}

	public void setSize(float input) {
		this.entityData.set(SIZE, input);
	}

	public static LavaWaveEntity shoot(Level world, LivingEntity entity, float damage, float size, int shockRange, int shockSpeed, float yaw) {
		LavaWaveEntity entityarrow = new LavaWaveEntity(world, entity);
		entityarrow.moveTo(entity.getX(), entity.getY(), entity.getZ());
		entityarrow.shockRange = (short) shockRange;
		entityarrow.shockSpeed = (short) shockSpeed;
		entityarrow.setSize(size);
		entityarrow.damage = damage;
		entityarrow.yaw = (short) yaw;
		entityarrow.initialXDir = (float) entityarrow.calculateViewVector(0, entityarrow.yaw).x;
		entityarrow.initialZDir = (float) entityarrow.calculateViewVector(0, entityarrow.yaw).z;
		//entityarrow.moveTo(vec3);
		world.addFreshEntity(entityarrow);
		return entityarrow;
	}

	public static LavaWaveEntity shoot(Level world, LivingEntity entity, float damage, float size, int shockRange, int shockSpeed, float yaw, Vec3 vec3) {
		LavaWaveEntity entityarrow = new LavaWaveEntity(world, entity);
		entityarrow.moveTo(vec3);
		entityarrow.shockRange = (short) shockRange;
		entityarrow.shockSpeed = (short) shockSpeed;
		entityarrow.setSize(size);
		entityarrow.damage = damage;
		entityarrow.yaw = (short) yaw;
		entityarrow.initialXDir = (float) entityarrow.calculateViewVector(0, entityarrow.yaw).x;
		entityarrow.initialZDir = (float) entityarrow.calculateViewVector(0, entityarrow.yaw).z;
		//entityarrow.moveTo(vec3);
		world.addFreshEntity(entityarrow);
		return entityarrow;
	}

	public static LavaWaveEntity shoot(Level world, LivingEntity entity, float damage, float size, int shockRange, int shockSpeed, float yaw, Vec3 vec3, float xDir, float zDir) {
		LavaWaveEntity entityarrow = new LavaWaveEntity(world, entity);
		entityarrow.moveTo(vec3);
		entityarrow.shockRange = (short) shockRange;
		entityarrow.shockSpeed = (short) shockSpeed;
		entityarrow.setSize(size);
		entityarrow.damage = damage;
		entityarrow.yaw = (short) yaw;
		entityarrow.initialXDir = (float) entityarrow.calculateViewVector(0, entityarrow.yaw).x;
		entityarrow.initialZDir = (float) entityarrow.calculateViewVector(0, entityarrow.yaw).z;
		entityarrow.xDir = xDir;
		entityarrow.zDir = zDir;
		//entityarrow.moveTo(vec3);
		world.addFreshEntity(entityarrow);
		return entityarrow;
	}
}
