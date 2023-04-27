
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.ItemParticleOption;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;

import java.util.Random;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class BigSnowBallEntity extends AbstractArrow implements ItemSupplier {
	public BigSnowBallEntity(PlayMessages.SpawnEntity packet, Level world) {
		super(CrimsonStevesMobsModEntities.BIG_SNOW_BALL.get(), world);
	}

	public BigSnowBallEntity(EntityType<? extends BigSnowBallEntity> type, Level world) {
		super(type, world);
	}

	public BigSnowBallEntity(EntityType<? extends BigSnowBallEntity> type, double x, double y, double z, Level world) {
		super(type, x, y, z, world);
	}

	public BigSnowBallEntity(EntityType<? extends BigSnowBallEntity> type, LivingEntity entity, Level world) {
		super(type, entity, world);
	}

	public EntityDimensions getDimensions(Pose p_29531_) {
		return super.getDimensions(p_29531_).scale(2, 2);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public ItemStack getItem() {
		return new ItemStack(Blocks.SNOW_BLOCK);
	}

	@Override
	protected ItemStack getPickupItem() {
		return ItemStack.EMPTY;
	}

	@Override
	public void onHitEntity(EntityHitResult entityHitResult) {
		entityHitResult.getEntity().invulnerableTime = 0;
		super.onHitEntity(entityHitResult);
		impactExplode();
	}

	@Override
	protected void doPostHurtEffects(LivingEntity entity) {
		super.doPostHurtEffects(entity);
		entity.setTicksFrozen(entity.getTicksFrozen() + 60);
		entity.setArrowCount(entity.getArrowCount() - 1);
	}

	@Override
	public void tick() {
		super.tick();
		this.level.addParticle(ParticleTypes.SNOWFLAKE, this.getX() + Math.random() - 0.5, this.getY() + Math.random(),
				this.getZ() + Math.random() - 0.5, 0.0D, 0.0D, 0.0D);
		this.level.addParticle(ParticleTypes.SNOWFLAKE, this.getX() + Math.random() - 0.5, this.getY() + Math.random(),
				this.getZ() + Math.random() - 0.5, 0.0D, 0.0D, 0.0D);
		this.level.addParticle(ParticleTypes.SNOWFLAKE, this.getX() + Math.random() - 0.5, this.getY() + Math.random(),
				this.getZ() + Math.random() - 0.5, 0.0D, 0.0D, 0.0D);
		if (this.inGround) {
			impactExplode();
			this.discard();
		}
	}

	/*
		public void handleEntityEvent(byte p_37402_) {
			if (p_37402_ == 3) {
				for (int i = 0; i < 45; i++)
					this.level.addParticle(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY() + 0.3, this.getZ(), (Math.random() - 0.5) / 2,
							(Math.random() - 0.5) / 2, (Math.random() - 0.5) / 2);
			}
		}
	*/
	private void impactExplode() {
		if (this.level instanceof ServerLevel _level)
			_level.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Blocks.SNOW_BLOCK)), this.getX(), this.getY(), this.getZ(),
					30, 0.5, 0.5, 0.5, 0.2);
	}

	public static BigSnowBallEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
		BigSnowBallEntity entityarrow = new BigSnowBallEntity(CrimsonStevesMobsModEntities.BIG_SNOW_BALL.get(), entity, world);
		entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
		entityarrow.setSilent(true);
		entityarrow.setCritArrow(false);
		entityarrow.setBaseDamage(damage);
		entityarrow.setKnockback(knockback);
		world.addFreshEntity(entityarrow);
		world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("")),
				SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
		return entityarrow;
	}

	public static BigSnowBallEntity shoot(LivingEntity entity, LivingEntity target) {
		BigSnowBallEntity entityarrow = new BigSnowBallEntity(CrimsonStevesMobsModEntities.BIG_SNOW_BALL.get(), entity, entity.level);
		double dx = target.getX() - entity.getX();
		double dy = target.getY() + target.getEyeHeight() - 1.1;
		double dz = target.getZ() - entity.getZ();
		entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 1f * 2, 12.0F);
		entityarrow.setSilent(true);
		entityarrow.setBaseDamage(5);
		entityarrow.setKnockback(0);
		entityarrow.setCritArrow(false);
		entity.level.addFreshEntity(entityarrow);
		entity.level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("")),
				SoundSource.PLAYERS, 1, 1f / (new Random().nextFloat() * 0.5f + 1));
		return entityarrow;
	}
}
