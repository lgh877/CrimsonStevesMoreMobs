
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.SetAttackTargetSameAsSummoner;
import net.mcreator.crimson_steves_mobs.IMinion;

import javax.annotation.Nullable;

import java.util.UUID;

public class TamedPhantomEntity extends Phantom implements IMinion {
	private Mob owner;
	private UUID ownerUUID;
	protected boolean hasLimitedLife;
	protected int limitedLifeTicks;

	public boolean isAlliedTo(Entity p_34110_) {
		if (this.getTeam() != null) {
			return super.isAlliedTo(p_34110_);
		} else if (p_34110_ instanceof LivingEntity && ((LivingEntity) p_34110_).getMobType() == MobType.ILLAGER) {
			return this.getTeam() == null && p_34110_.getTeam() == null;
		} else {
			return false;
		}
	}

	public void readAdditionalSaveData(CompoundTag p_36941_) {
		super.readAdditionalSaveData(p_36941_);
		if (p_36941_.hasUUID("Owner")) {
			this.ownerUUID = p_36941_.getUUID("Owner");
		}
		if (p_36941_.contains("LifeTicks")) {
			this.setLimitedLife(p_36941_.getInt("LifeTicks"));
		}
	}

	public void addAdditionalSaveData(CompoundTag p_36943_) {
		super.addAdditionalSaveData(p_36943_);
		if (this.ownerUUID != null) {
			p_36943_.putUUID("Owner", this.ownerUUID);
		}
		if (this.hasLimitedLife) {
			p_36943_.putInt("LifeTicks", this.limitedLifeTicks);
		}
	}

	public void setLimitedLife(int p_33988_) {
		this.hasLimitedLife = true;
		this.limitedLifeTicks = p_33988_;
	}

	public void setOwner(@Nullable Mob p_36939_) {
		this.owner = p_36939_;
		this.ownerUUID = p_36939_ == null ? null : p_36939_.getUUID();
	}

	@Nullable
	public Mob getOwner() {
		if (this.owner == null && this.ownerUUID != null && this.level instanceof ServerLevel) {
			Entity entity = ((ServerLevel) this.level).getEntity(this.ownerUUID);
			if (entity instanceof Mob) {
				this.owner = (Mob) entity;
			}
		}
		return this.owner;
	}

	@Override
	protected boolean isSunBurnTick() {
		return false;
	}

	public void tick() {
		super.tick();
		whenLifeTickDone();
	}

	protected void whenLifeTickDone() {
		if (this.hasLimitedLife && --this.limitedLifeTicks <= 0) {
			this.limitedLifeTicks = 20;
			this.hurt(DamageSource.STARVE, this.getMaxHealth() * 0.15f);
		}
	}

	public TamedPhantomEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.TAMED_PHANTOM.get(), world);
	}

	public TamedPhantomEntity(EntityType<? extends TamedPhantomEntity> type, Level world) {
		super(type, world);
		xpReward = 10;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		this.targetSelector.addGoal(0, new SetAttackTargetSameAsSummoner(this));
		super.registerGoals();
	}

	@Override
	public MobType getMobType() {
		return MobType.ILLAGER;
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
