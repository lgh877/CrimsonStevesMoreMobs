
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.IProtectiveVehicle;

import javax.annotation.Nullable;

public class VehiclePhantomEntity extends TamedPhantomEntity implements IProtectiveVehicle {
	public VehiclePhantomEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.VEHICLE_PHANTOM.get(), world);
	}

	public void makeStuckInBlock(BlockState p_33796_, Vec3 p_33797_) {
	}

	public VehiclePhantomEntity(EntityType<VehiclePhantomEntity> type, Level world) {
		super(type, world);
		xpReward = 40;
	}

	public void setPhantomSize(int p_33109_) {
		super.setPhantomSize(10);
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

	protected void tickDeath() {
		super.tickDeath();
		this.push(0, -0.5, 0);
		if (!this.level.getBlockState(this.blockPosition().below()).isAir() || this.onGround) {
			this.dead = true;
			this.discard();
			if (this.getOwner() != null) {
				ServerLevel serverlevel = (ServerLevel) this.level;
				for (int i = 0; i < 3; i++) {
					ExplosivePhantomEntity vex = new ExplosivePhantomEntity(CrimsonStevesMobsModEntities.EXPLOSIVE_PHANTOM.get(), this.level);
					vex.moveTo(this.blockPosition(), 0.0F, 0.0F);
					vex.finalizeSpawn(serverlevel, this.level.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
					vex.setOwner(this.getOwner());
					vex.setLimitedLife(100);
					serverlevel.addFreshEntityWithPassengers(vex);
				}
			}
		}
	}

	protected void whenLifeTickDone() {
		if (this.hasLimitedLife && --this.limitedLifeTicks <= 0) {
			this.push(0, -0.5, 0);
			if (!this.level.getBlockState(this.blockPosition().below()).isAir() || this.onGround) {
				this.dead = true;
				this.discard();
				if (this.getOwner() != null) {
					ServerLevel serverlevel = (ServerLevel) this.level;
					for (int i = 0; i < 3; i++) {
						ExplosivePhantomEntity vex = new ExplosivePhantomEntity(CrimsonStevesMobsModEntities.EXPLOSIVE_PHANTOM.get(), this.level);
						vex.moveTo(this.blockPosition(), 0.0F, 0.0F);
						vex.finalizeSpawn(serverlevel, this.level.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
						vex.setOwner(this.getOwner());
						vex.setLimitedLife(100);
						serverlevel.addFreshEntityWithPassengers(vex);
					}
				}
			}
		}
	}

	public boolean removeWhenFarAway(double p_27519_) {
		return false;
	}

	public boolean doHurtTarget(Entity entity) {
		float f1 = getAttackDamage();
		boolean flag = entity.hurt(DamageSource.mobAttack(this), f1);
		if (!this.level.isClientSide && entity.isOnGround()) {
			EarthQuakeEntity.shoot(this.level, this, this.random, 1, f1, 75);
			EarthQuakeEntity.shoot(this.level, this, this.random, 1, f1, 75);
			EarthQuakeEntity.shoot(this.level, this, this.random, 1, f1, 75);
			this.playSound(SoundEvents.GENERIC_EXPLODE, 1.5f, 2f);
		}
		return flag;
	}

	private float getAttackDamage() {
		return (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) / 1.5f;
	}

	public static void init() {
	}

	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33126_, DifficultyInstance p_33127_, MobSpawnType p_33128_, @Nullable SpawnGroupData p_33129_, @Nullable CompoundTag p_33130_) {
		return super.finalizeSpawn(p_33126_, p_33127_, p_33128_, p_33129_, p_33130_);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 40);
		builder = builder.add(Attributes.ARMOR, 15);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 10);
		builder = builder.add(Attributes.FOLLOW_RANGE, 128);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 5);
		return builder;
	}
}
