
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.ai.village.ReputationEventType;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.ai.gossip.GossipContainer;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.MoveBackToVillageGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.GolemRandomStrollInVillageGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ReputationEventHandler;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.BlockPos;

import net.mcreator.crimson_steves_mobs.procedures.ChadVillagerNaturalEntitySpawningConditionProcedure;
import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.CustomProtectVillagerGoal;
import net.mcreator.crimson_steves_mobs.BaseNeutralMob;

import javax.annotation.Nullable;

import com.mojang.serialization.Dynamic;

@Mod.EventBusSubscriber
public class ChadVillagerEntity extends BaseNeutralMob implements ReputationEventHandler {
	private final GossipContainer gossips = new GossipContainer();
	private long lastGossipDecayTime;
	private long lastGossipTime;

	public void addAdditionalSaveData(CompoundTag p_28867_) {
		super.addAdditionalSaveData(p_28867_);
		p_28867_.put("Gossips", this.gossips.store(NbtOps.INSTANCE).getValue());
	}

	public void readAdditionalSaveData(CompoundTag p_28857_) {
		super.readAdditionalSaveData(p_28857_);
		ListTag listtag = p_28857_.getList("Gossips", 10);
		this.gossips.update(new Dynamic<>(NbtOps.INSTANCE, listtag));
	}

	public ChadVillagerEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.CHAD_VILLAGER.get(), world);
	}

	public ChadVillagerEntity(EntityType<ChadVillagerEntity> type, Level world) {
		super(type, world);
		this.maxUpStep = 1.0F;
		setPersistenceRequired();
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false, (p_28879_) -> {
			return p_28879_ instanceof Enemy && !(p_28879_ instanceof Creeper);
		}));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.1D, true));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(6, new FloatGoal(this));
		this.targetSelector.addGoal(1, new CustomProtectVillagerGoal(this, Villager.class));
		this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9D, 32.0F));
		this.goalSelector.addGoal(2, new MoveBackToVillageGoal(this, 0.6D, false));
		this.goalSelector.addGoal(4, new GolemRandomStrollInVillageGoal(this, 0.6D));
	}

	public void gossip(ServerLevel p_35412_, ChadVillagerEntity p_35413_, long p_35414_) {
		if ((p_35414_ < this.lastGossipTime || p_35414_ >= this.lastGossipTime + 1200L)
				&& (p_35414_ < p_35413_.lastGossipTime || p_35414_ >= p_35413_.lastGossipTime + 1200L)) {
			this.gossips.transferFrom(p_35413_.gossips, this.random, 10);
			this.lastGossipTime = p_35414_;
			p_35413_.lastGossipTime = p_35414_;
		}
	}

	private void maybeDecayGossip() {
		long i = this.level.getGameTime();
		if (this.lastGossipDecayTime == 0L) {
			this.lastGossipDecayTime = i;
		} else if (i >= this.lastGossipDecayTime + 24000L) {
			this.gossips.decay();
			this.lastGossipDecayTime = i;
		}
	}

	protected void doPush(Entity p_28839_) {
		if (p_28839_ instanceof Enemy && !(p_28839_ instanceof Creeper) && this.getRandom().nextInt(20) == 0 && !this.isAlliedTo(p_28839_)) {
			this.setTarget((LivingEntity) p_28839_);
		}
		super.doPush(p_28839_);
	}

	public void aiStep() {
		if (this.getDeltaMovement().horizontalDistanceSqr() > (double) 2.5000003E-7F && this.random.nextInt(5) == 0) {
			int i = Mth.floor(this.getX());
			int j = Mth.floor(this.getY() - (double) 0.2F);
			int k = Mth.floor(this.getZ());
			BlockPos pos = new BlockPos(i, j, k);
			BlockState blockstate = this.level.getBlockState(pos);
			if (!blockstate.isAir()) {
				this.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate).setPos(pos),
						this.getX() + ((double) this.random.nextFloat() - 0.5D) * (double) this.getBbWidth(), this.getY() + 0.1D,
						this.getZ() + ((double) this.random.nextFloat() - 0.5D) * (double) this.getBbWidth(),
						4.0D * ((double) this.random.nextFloat() - 0.5D), 0.5D, ((double) this.random.nextFloat() - 0.5D) * 4.0D);
			}
		}
		if (!this.level.isClientSide) {
			this.updatePersistentAnger((ServerLevel) this.level, true);
		}
		super.aiStep();
	}

	public float getWalkTargetValue(BlockPos p_27573_, LevelReader p_27574_) {
		return p_27574_.getBlockState(p_27573_.below()).is(Blocks.GRASS_BLOCK) ? 10.0F : p_27574_.getPathfindingCostFromLightLevels(p_27573_);
	}

	public void setLastHurtByMob(@Nullable LivingEntity p_35423_) {
		if (p_35423_ != null && this.level instanceof ServerLevel) {
			((ServerLevel) this.level).onReputationEvent(ReputationEventType.VILLAGER_HURT, p_35423_, this);
			if (this.isAlive()) {
				this.level.broadcastEntityEvent(this, (byte) 13);
			}
		}
		super.setLastHurtByMob(p_35423_);
	}

	public void handleEntityEvent(byte p_35391_) {
		if (p_35391_ == 13) {
			this.addParticlesAroundSelf(ParticleTypes.ANGRY_VILLAGER);
		} else {
			super.handleEntityEvent(p_35391_);
		}
	}

	protected void addParticlesAroundSelf(ParticleOptions p_35288_) {
		for (int i = 0; i < 5; ++i) {
			double d0 = this.random.nextGaussian() * 0.02D;
			double d1 = this.random.nextGaussian() * 0.02D;
			double d2 = this.random.nextGaussian() * 0.02D;
			this.level.addParticle(p_35288_, this.getRandomX(1.0D), this.getRandomY() + 1.0D, this.getRandomZ(1.0D), d0, d1, d2);
		}
	}

	public void onReputationEventFrom(ReputationEventType p_35431_, Entity p_35432_) {
		if (p_35431_ == ReputationEventType.ZOMBIE_VILLAGER_CURED) {
			this.gossips.add(p_35432_.getUUID(), GossipType.MAJOR_POSITIVE, 20);
			this.gossips.add(p_35432_.getUUID(), GossipType.MINOR_POSITIVE, 25);
		} else if (p_35431_ == ReputationEventType.TRADE) {
			this.gossips.add(p_35432_.getUUID(), GossipType.TRADING, 2);
		} else if (p_35431_ == ReputationEventType.VILLAGER_HURT) {
			this.gossips.add(p_35432_.getUUID(), GossipType.MINOR_NEGATIVE, 25);
		} else if (p_35431_ == ReputationEventType.VILLAGER_KILLED) {
			this.gossips.add(p_35432_.getUUID(), GossipType.MAJOR_NEGATIVE, 25);
		}
	}

	public boolean doHurtTarget(Entity entityIn) {
		this.playSound(SoundEvents.VILLAGER_NO, this.getSoundVolume(), this.getVoicePitch());
		return super.doHurtTarget(entityIn);
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
		if (source.getEntity() instanceof LivingEntity _livEnt ? _livEnt.getMobType() == MobType.UNDEAD : false) {
			if (level instanceof ServerLevel _level) {
				ZombiefiedChadVillagerEntity entityToSpawn = new ZombiefiedChadVillagerEntity(
						CrimsonStevesMobsModEntities.ZOMBIEFIED_CHAD_VILLAGER.get(), _level);
				entityToSpawn.setPos(this.getPosition(0.5f));
				//entityToSpawn.finalizeSpawn(_level, level.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.CONVERSION, null, null);
				level.addFreshEntity(entityToSpawn);
			}
			this.discard();
		}
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	public float getVoicePitch() {
		return super.getVoicePitch() * 0.6f;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.ambient"));
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.step")), 0.15f, 1);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.death"));
	}

	public static void init() {
		SpawnPlacements.register(CrimsonStevesMobsModEntities.CHAD_VILLAGER.get(), SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos, random) -> {
					int x = pos.getX();
					int y = pos.getY();
					int z = pos.getZ();
					return ChadVillagerNaturalEntitySpawningConditionProcedure.execute(world, x, y, z);
					//
				});
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 60);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 25);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		return builder;
	}
}
