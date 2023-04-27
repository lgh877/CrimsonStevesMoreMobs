
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.scores.Team;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.SetAttackTargetSameAsSummoner;
import net.mcreator.crimson_steves_mobs.IMinion;
import net.mcreator.crimson_steves_mobs.CustomFollowOwnerGoal;

import javax.annotation.Nullable;

import java.util.UUID;

public class MinionRedstoneGolemEntity extends CrudeRedstoneGolemEntity implements IMinion {
	private Mob owner;
	private UUID ownerUUID;

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

	public MinionRedstoneGolemEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.MINION_REDSTONE_GOLEM.get(), world);
	}

	public MinionRedstoneGolemEntity(EntityType<MinionRedstoneGolemEntity> type, Level world) {
		super(type, world);
		this.refreshDimensions();
		xpReward = 7;
	}

	public float getScale() {
		return 0.4f;
	}

	public float getVoicePitch() {
		return super.getVoicePitch() * 1.5f;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		this.targetSelector.addGoal(0, new SetAttackTargetSameAsSummoner(this));
		this.goalSelector.addGoal(6, new CustomFollowOwnerGoal(this, 1.0D, 15.0F, 5.0F, false));
		super.registerGoals();
	}

	public void aiStep() {
		super.aiStep();
		if (this.getTarget() != null && !this.getTarget().isAlive())
			this.setTarget((LivingEntity) null);
	}

	@Override
	public MobType getMobType() {
		return MobType.ILLAGER;
	}

	public static void init() {
		SpawnPlacements.register(CrimsonStevesMobsModEntities.MINION_REDSTONE_GOLEM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 20);
		builder = builder.add(Attributes.ARMOR, 10);
		builder = builder.add(Attributes.FOLLOW_RANGE, 30);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		return builder;
	}
}
