package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.scores.Team;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.IMinion;

import javax.annotation.Nullable;

import java.util.UUID;

public class ConstructOfEnderHeadEntity extends Monster implements IMinion {
	public boolean left;
	private Mob owner;
	private UUID ownerUUID;

	public void makeStuckInBlock(BlockState p_33796_, Vec3 p_33797_) {
	}

	public void readAdditionalSaveData(CompoundTag p_36941_) {
		super.readAdditionalSaveData(p_36941_);
		if (p_36941_.hasUUID("Owner")) {
			this.ownerUUID = p_36941_.getUUID("Owner");
		}
	}

	public void addAdditionalSaveData(CompoundTag p_36943_) {
		super.addAdditionalSaveData(p_36943_);
		if (this.ownerUUID != null) {
			p_36943_.putUUID("Owner", this.ownerUUID);
		}
	}

	@Nullable
	public Team getTeam() {
		return getOwner() != null && getOwner().isAlive() ? this.getOwner().getTeam() : super.getTeam();
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

	public ConstructOfEnderHeadEntity(EntityType<ConstructOfEnderHeadEntity> type, Level world) {
		super(type, world);
		xpReward = 200;
		setPersistenceRequired();
	}

	public static void init() {
	}

	public ConstructOfEnderHeadEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.CONSTRUCT_OF_ENDER_HEAD.get(), world);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 200);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 8);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.7);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1.5);
		return builder;
	}
}
