
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.particles.ParticleTypes;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;

public class ExplosivePhantomEntity extends TamedPhantomEntity {
	public ExplosivePhantomEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.EXPLOSIVE_PHANTOM.get(), world);
	}

	public void makeStuckInBlock(BlockState p_33796_, Vec3 p_33797_) {
	}

	public ExplosivePhantomEntity(EntityType<ExplosivePhantomEntity> type, Level world) {
		super(type, world);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
	}

	@Override
	public MobType getMobType() {
		return MobType.ILLAGER;
	}

	public boolean doHurtTarget(Entity entity) {
		if (!this.level.isClientSide) {
			Explosion.BlockInteraction explosion$blockinteraction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
			this.dead = true;
			this.level.explode(this, this.getX(), this.getY(), this.getZ(), 2, explosion$blockinteraction);
			this.discard();
		}
		return super.doHurtTarget(entity);
	}

	protected void whenLifeTickDone() {
		if (this.hasLimitedLife && --this.limitedLifeTicks <= 0) {
			this.push(0, -0.5, 0);
			if ((!this.level.getBlockState(this.blockPosition().below()).isAir() || this.onGround) && !this.level.isClientSide) {
				Explosion.BlockInteraction explosion$blockinteraction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
				this.dead = true;
				this.level.explode(this, this.getX(), this.getY(), this.getZ(), 2, explosion$blockinteraction);
				this.discard();
			}
		}
	}

	public void aiStep() {
		super.aiStep();
		if (this.horizontalCollision && !this.level.isClientSide) {
			Explosion.BlockInteraction explosion$blockinteraction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
			this.dead = true;
			this.level.explode(this, this.getX(), this.getY(), this.getZ(), 2, explosion$blockinteraction);
			this.discard();
		}
		if (this.tickCount % 5 == 0)
			this.level.addParticle(ParticleTypes.LAVA, this.getRandomX(this.getBbWidth() / 2), this.getRandomY(), this.getRandomZ(this.getBbWidth() / 2), 0.0D, 0.0D, 0.0D);
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 10);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 6);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		return builder;
	}
}
