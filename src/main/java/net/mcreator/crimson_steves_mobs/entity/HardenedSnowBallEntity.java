
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ItemParticleOption;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;

public class HardenedSnowBallEntity extends ThrowableItemProjectile {
	public HardenedSnowBallEntity(PlayMessages.SpawnEntity packet, Level world) {
		super(CrimsonStevesMobsModEntities.HARDENED_SNOW_BALL.get(), world);
	}

	public HardenedSnowBallEntity(EntityType<? extends HardenedSnowBallEntity> p_37391_, Level world) {
		super(p_37391_, world);
	}

	public HardenedSnowBallEntity(Level p_37399_, LivingEntity p_37400_) {
		super(CrimsonStevesMobsModEntities.HARDENED_SNOW_BALL.get(), p_37400_, p_37399_);
	}

	public HardenedSnowBallEntity(Level p_37394_, double p_37395_, double p_37396_, double p_37397_) {
		super(CrimsonStevesMobsModEntities.HARDENED_SNOW_BALL.get(), p_37395_, p_37396_, p_37397_, p_37394_);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	protected Item getDefaultItem() {
		return Items.SNOWBALL;
	}

	private ParticleOptions getParticle() {
		ItemStack itemstack = this.getItemRaw();
		return (ParticleOptions) (itemstack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleOption(ParticleTypes.ITEM, itemstack));
	}

	public void handleEntityEvent(byte p_37402_) {
		if (p_37402_ == 3) {
			ParticleOptions particleoptions = this.getParticle();
			for (int i = 0; i < 8; ++i) {
				this.level.addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			}
		}
	}

	protected void onHitEntity(EntityHitResult p_37404_) {
		Entity entity = p_37404_.getEntity();
		entity.invulnerableTime = 0;
		entity.setTicksFrozen(entity.getTicksFrozen() + 20);
		float i = entity instanceof Blaze ? 2.5f : 0.5f;
		if (entity.isFullyFrozen())
			i *= 2;
		entity.hurt(DamageSource.thrown(this, this.getOwner()), i);
		super.onHitEntity(p_37404_);
	}

	protected void onHit(HitResult p_37406_) {
		super.onHit(p_37406_);
		if (!this.level.isClientSide) {
			this.level.broadcastEntityEvent(this, (byte) 3);
			this.discard();
		}
	}

	public static HardenedSnowBallEntity shoot(Level world, LivingEntity entity, float power) {
		HardenedSnowBallEntity entityarrow = new HardenedSnowBallEntity(world, entity);
		entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 20);
		world.addFreshEntity(entityarrow);
		return entityarrow;
	}

	public static HardenedSnowBallEntity shoot(Level world, LivingEntity entity, float power, Vec3 vec3) {
		HardenedSnowBallEntity entityarrow = new HardenedSnowBallEntity(world, entity);
		entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 20);
		entityarrow.moveTo(vec3);
		world.addFreshEntity(entityarrow);
		return entityarrow;
	}
}
