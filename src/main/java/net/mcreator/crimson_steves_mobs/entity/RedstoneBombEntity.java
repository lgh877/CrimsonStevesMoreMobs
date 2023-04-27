
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.particles.ParticleTypes;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class RedstoneBombEntity extends AbstractArrow implements ItemSupplier {
	public EntityDimensions getDimensions(Pose p_29531_) {
		return super.getDimensions(p_29531_).scale(2, 2);
	}

	public RedstoneBombEntity(PlayMessages.SpawnEntity packet, Level world) {
		super(CrimsonStevesMobsModEntities.REDSTONE_BOMB.get(), world);
	}

	public RedstoneBombEntity(EntityType<? extends RedstoneBombEntity> type, Level world) {
		super(type, world);
	}

	public RedstoneBombEntity(EntityType<? extends RedstoneBombEntity> type, double x, double y, double z, Level world) {
		super(type, x, y, z, world);
	}

	public RedstoneBombEntity(EntityType<? extends RedstoneBombEntity> type, LivingEntity entity, Level world) {
		super(type, entity, world);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public ItemStack getItem() {
		return new ItemStack(Blocks.REDSTONE_BLOCK);
	}

	@Override
	protected ItemStack getPickupItem() {
		return ItemStack.EMPTY;
	}

	@Override
	protected void doPostHurtEffects(LivingEntity entity) {
		super.doPostHurtEffects(entity);
		entity.setArrowCount(entity.getArrowCount() - 1);
	}

	@Override
	public void onHitEntity(EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);
		entityHitResult.getEntity().invulnerableTime = 0;
		if (!this.level.isClientSide) {
			Explosion.BlockInteraction explosion$blockinteraction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this)
					? Explosion.BlockInteraction.DESTROY
					: Explosion.BlockInteraction.NONE;
			if (this.getOwner() != null)
				this.level.explode(this.getOwner(), this.getX(), this.getY(), this.getZ(), 2, explosion$blockinteraction);
			else
				this.level.explode(this, this.getX(), this.getY(), this.getZ(), 2, explosion$blockinteraction);
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.ANGRY_VILLAGER, this.getX(), this.getY(), this.getZ(), 3, this.getBbWidth(), this.getBbHeight(),
					this.getBbWidth(), 0);
		if (this.inGround) {
			if (!this.level.isClientSide) {
				Explosion.BlockInteraction explosion$blockinteraction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level,
						this) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
				if (this.getOwner() != null) {
					this.level.explode(this.getOwner(), this.getX(), this.getY(), this.getZ(), 2, explosion$blockinteraction);
				} else {
					this.level.explode(this, this.getX(), this.getY(), this.getZ(), 2, explosion$blockinteraction);
				}
			}
			this.discard();
		}
	}

	public static RedstoneBombEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
		RedstoneBombEntity entityarrow = new RedstoneBombEntity(CrimsonStevesMobsModEntities.REDSTONE_BOMB.get(), entity, world);
		entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
		entityarrow.setSilent(true);
		entityarrow.setCritArrow(false);
		entityarrow.setBaseDamage(damage);
		entityarrow.setKnockback(knockback);
		world.addFreshEntity(entityarrow);
		return entityarrow;
	}

	public static RedstoneBombEntity shoot(LivingEntity entity, LivingEntity target) {
		RandomSource rand = RandomSource.create();
		RedstoneBombEntity entityarrow = new RedstoneBombEntity(CrimsonStevesMobsModEntities.REDSTONE_BOMB.get(), entity, entity.level);
		double dx = target.getX() - entity.getX();
		double dy = target.getY() + target.getEyeHeight() - 1.1;
		double dz = target.getZ() - entity.getZ();
		entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 1f * 2, 12.0F);
		entityarrow.setSilent(true);
		entityarrow.setBaseDamage(8);
		entityarrow.setKnockback(0);
		entity.level.addFreshEntity(entityarrow);
		return entityarrow;
	}
}
