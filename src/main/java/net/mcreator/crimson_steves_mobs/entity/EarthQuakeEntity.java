
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Blocks;
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
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.CustomMathHelper;

public class EarthQuakeEntity extends ThrowableItemProjectile {
	//private static final EntityDataAccessor<Integer> SHOCK_RANGE = SynchedEntityData.defineId(EarthQuakeEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> SIZE = SynchedEntityData.defineId(EarthQuakeEntity.class, EntityDataSerializers.FLOAT);
	//public float size = 2;
	public short shockRange = 10;
	public float damage = 5;
	public short shockSpeed = 5;
	public short yaw = 0;
	private Vec3 lookVec = this.getLookAngle();

	/*
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOCK_RANGE, 0);
	}
	*/
	//private short lifeTime;
	public EarthQuakeEntity(PlayMessages.SpawnEntity packet, Level world) {
		super(CrimsonStevesMobsModEntities.EARTH_QUAKE.get(), world);
	}

	public EarthQuakeEntity(EntityType<? extends EarthQuakeEntity> type, Level world) {
		super(type, world);
		//this.setInvisible(true);
		//this.refreshDimensions();
	}

	public EarthQuakeEntity(Level p_37399_, LivingEntity p_37400_) {
		super(CrimsonStevesMobsModEntities.EARTH_QUAKE.get(), p_37400_, p_37399_);
	}

	public EarthQuakeEntity(Level p_37394_, double p_37395_, double p_37396_, double p_37397_) {
		super(CrimsonStevesMobsModEntities.EARTH_QUAKE.get(), p_37395_, p_37396_, p_37397_, p_37394_);
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
		p_32304_.putFloat("size", this.entityData.get(SIZE));
	}

	public void readAdditionalSaveData(CompoundTag p_32296_) {
		super.readAdditionalSaveData(p_32296_);
		this.shockRange = p_32296_.getShort("shockRange");
		//this.lifeTime = p_32296_.getShort("lifeTime");
		this.shockSpeed = p_32296_.getShort("shockSpeed");
		this.yaw = p_32296_.getShort("yaw");
		this.damage = p_32296_.getFloat("damage");
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
		return new BlockPos(this.getX() + this.lookVec.x * this.entityData.get(SIZE) / 2, this.getY(),
				this.getZ() + this.lookVec.z * this.entityData.get(SIZE) / 2);
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
			if (this.tickCount % 3 == 0) {
				SoundType soundtype = blockstate5.is(Blocks.SNOW)
						? blockstate5.getSoundType(level, pos, this)
						: blockstate5.getSoundType(level, pos, this);
				this.playSound(soundtype.getStepSound(), 1, soundtype.getPitch());
			}
			for (int p = 0; p < 4 * Mth.clamp((int) Math.pow((double) this.entityData.get(SIZE), 1.5), 1, Integer.MAX_VALUE); p++)
				this.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate5).setPos(pos),
						this.getX() + ((double) this.random.nextFloat() - 0.5D) * (double) this.entityData.get(SIZE), this.getY(),
						this.getZ() + ((double) this.random.nextFloat() - 0.5D) * (double) this.entityData.get(SIZE),
						4.0D * ((double) this.random.nextFloat() - 0.5D), (double) this.random.nextFloat() * 5 + 0.5D,
						((double) this.random.nextFloat() - 0.5D) * 4.0D);
		}
		if (!this.level.isClientSide)
			if (this.tickCount % this.shockSpeed == 0) {
				this.lookVec = this.calculateViewVector(0, this.yaw);
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
					AABB attackRange = CustomMathHelper.makeAttackRange(this.getX(), this.getY(), this.getZ(), 0, this.entityData.get(SIZE), 0.6,
							this.entityData.get(SIZE));
					for (LivingEntity livingentity : this.level.getEntitiesOfClass(LivingEntity.class, attackRange)) {
						Entity owner = this.getOwner();
						if (owner == null) {
							if (livingentity.hurt(DamageSource.indirectMobAttack(this, null), this.damage)) {
								this.strongKnockback(livingentity, 0.5);
							}
						} else {
							if (livingentity != owner)
								if (!livingentity.isAlliedTo(owner)
										&& livingentity.hurt(DamageSource.indirectMobAttack(this, (LivingEntity) this.getOwner()), this.damage)) {
									this.strongKnockback(livingentity, 0.5);
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

	public static EarthQuakeEntity shoot(Level world, LivingEntity entity, RandomSource random, int shockSpeed, float damage, int knockback) {
		EarthQuakeEntity entityarrow = new EarthQuakeEntity(world, entity);
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

	public static EarthQuakeEntity shoot(Level world, LivingEntity entity, float damage, float size, int shockRange, int shockSpeed, float yaw) {
		EarthQuakeEntity entityarrow = new EarthQuakeEntity(world, entity);
		entityarrow.moveTo(entity.getX(), entity.getY(), entity.getZ());
		entityarrow.shockRange = (short) shockRange;
		entityarrow.shockSpeed = (short) shockSpeed;
		entityarrow.setSize(size);
		//entityarrow.size = size;
		entityarrow.damage = damage;
		entityarrow.yaw = (short) yaw;
		//entityarrow.moveTo(vec3);
		world.addFreshEntity(entityarrow);
		return entityarrow;
	}

	public static EarthQuakeEntity shoot(Level world, LivingEntity entity, float damage, float size, int shockRange, int shockSpeed, float yaw,
			Vec3 vec3) {
		EarthQuakeEntity entityarrow = new EarthQuakeEntity(world, entity);
		entityarrow.moveTo(vec3);
		entityarrow.shockRange = (short) shockRange;
		entityarrow.shockSpeed = (short) shockSpeed;
		entityarrow.setSize(size);
		entityarrow.damage = damage;
		entityarrow.yaw = (short) yaw;
		//entityarrow.moveTo(vec3);
		world.addFreshEntity(entityarrow);
		return entityarrow;
	}
}
