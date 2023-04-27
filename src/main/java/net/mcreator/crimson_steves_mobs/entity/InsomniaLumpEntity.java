
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;

public class InsomniaLumpEntity extends ThrowableItemProjectile {
	public double xPos;
	public double yPos;
	public double zPos;
	public int lifeTime;

	public void addAdditionalSaveData(CompoundTag p_33619_) {
		super.addAdditionalSaveData(p_33619_);
		p_33619_.putDouble("xPos", this.xPos);
		p_33619_.putDouble("yPos", this.yPos);
		p_33619_.putDouble("zPos", this.zPos);
		p_33619_.putInt("lifeTime", this.lifeTime);
	}

	public void readAdditionalSaveData(CompoundTag p_33607_) {
		super.readAdditionalSaveData(p_33607_);
		this.xPos = p_33607_.getDouble("xPos");
		this.yPos = p_33607_.getDouble("yPos");
		this.zPos = p_33607_.getDouble("zPos");
		this.lifeTime = p_33607_.getInt("lifeTime");
		this.setNoGravity(true);
	}

	public InsomniaLumpEntity(PlayMessages.SpawnEntity packet, Level world) {
		super(CrimsonStevesMobsModEntities.INSOMNIA_LUMP.get(), world);
	}

	public InsomniaLumpEntity(EntityType<? extends InsomniaLumpEntity> type, Level world) {
		super(type, world);
	}

	public InsomniaLumpEntity(Level level, LivingEntity entity) {
		super(CrimsonStevesMobsModEntities.INSOMNIA_LUMP.get(), entity, level);
		this.setNoGravity(true);
	}

	public InsomniaLumpEntity(Level level, double x, double y, double z) {
		super(CrimsonStevesMobsModEntities.INSOMNIA_LUMP.get(), x, y, z, level);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	protected Item getDefaultItem() {
		return Items.LAPIS_BLOCK;
	}

	protected void onHit(HitResult p_37406_) {
		super.onHit(p_37406_);
		this.explode();
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.level.isClientSide) {
			this.lifeTime--;
			if (tickCount % 3 == 0) {
				((ServerLevel) level).sendParticles(ParticleTypes.SOUL, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0.05);
				((ServerLevel) level).sendParticles(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), 3, 0, 0, 0, 0.05);
			}
			if (this.lifeTime > 30) {
				Vec3 vec31 = new Vec3(this.xPos - this.getX(), this.yPos - this.getY(), this.zPos - this.getZ()).normalize();
				this.push(vec31.x, vec31.y, vec31.z);
				this.setDeltaMovement(this.getDeltaMovement().scale(0.5));
			} else if (this.lifeTime > 0) {
				this.push(0, -0.05, 0);
			} else {
				this.explode();
			}
		}
		/*
		for (int i = 0; i < 2; ++i) {
			this.level.addParticle(ParticleTypes.LARGE_SMOKE, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
		}
		*/
	}

	protected void explode() {
		this.discard();
		if (!this.level.isClientSide) {
			((ServerLevel) level).sendParticles(ParticleTypes.SOUL, this.getX(), this.getY(), this.getZ(), 15, 0, 0, 0, 0.15);
			((ServerLevel) level).sendParticles(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), 45, 0, 0, 0, 0.15);
			this.playSound(SoundEvents.GENERIC_SPLASH, 2, 1);
			for (LivingEntity livingentity : this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2))) {
				this.dealDamageTo(livingentity);
			}
			this.discard();
		}
	}

	private void dealDamageTo(LivingEntity p_36945_) {
		LivingEntity livingentity = (LivingEntity) this.getOwner();
		if (p_36945_.isAlive() && !p_36945_.isInvulnerable() && p_36945_ != livingentity) {
			if (livingentity == null) {
				p_36945_.invulnerableTime = 0;
				p_36945_.hurt(DamageSource.MAGIC, 6.0F);
			} else {
				if (livingentity.isAlliedTo(p_36945_)) {
					return;
				}
				p_36945_.invulnerableTime = 0;
				p_36945_.hurt(DamageSource.indirectMagic(this, livingentity), 6.0F);
			}
		}
	}

	public RandomSource getRandom() {
		return this.random;
	}

	public static InsomniaLumpEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
		InsomniaLumpEntity entityarrow = new InsomniaLumpEntity(world, entity);
		entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
		entityarrow.setSilent(true);
		entityarrow.setNoGravity(true);
		world.addFreshEntity(entityarrow);
		return entityarrow;
	}

	public static InsomniaLumpEntity shoot(LivingEntity entity, LivingEntity target, float multiplier) {
		InsomniaLumpEntity entityarrow = new InsomniaLumpEntity(entity.level, entity);
		double dx = target.getX();
		double dy = target.getY() + target.getEyeHeight() + 6;
		double dz = target.getZ();
		Vec2 vec21 = new Vec2((float) (dx - entity.getX()), (float) (dz - entity.getZ())).normalized();
		entityarrow.setDeltaMovement((entityarrow.getRandom().nextFloat() - 0.5) * 6, 3, (entityarrow.getRandom().nextFloat() - 0.5) * 6);
		entityarrow.xPos = dx + vec21.x * multiplier;
		entityarrow.yPos = dy;
		entityarrow.zPos = dz + vec21.y * multiplier;
		entityarrow.lifeTime = 100;
		entityarrow.setSilent(true);
		entityarrow.setNoGravity(true);
		entity.level.addFreshEntity(entityarrow);
		return entityarrow;
	}
}
