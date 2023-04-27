
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Difficulty;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.EmergableZombie;
import net.mcreator.crimson_steves_mobs.CustomMathHelper;

import javax.annotation.Nullable;

import java.util.Random;
import java.util.EnumSet;

@Mod.EventBusSubscriber
public class ZombieEvokerEntity extends EmergableZombie {
	public boolean skillActive;

	protected boolean convertsInWater() {
		return false;
	}

	public ZombieEvokerEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.ZOMBIE_EVOKER.get(), world);
	}

	public ZombieEvokerEntity(EntityType<ZombieEvokerEntity> type, Level world) {
		super(type, world);
		xpReward *= 10;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public ResourceLocation getDefaultLootTable() {
		return EntityType.ZOMBIE.getDefaultLootTable();
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new SummonZombiesGoal(this));
		this.goalSelector.addGoal(1, new MassiveHealGoal(this));
		this.goalSelector.addGoal(1, new FangsCastingGoal(this));
		this.goalSelector.addGoal(1, new SummonZombieRavagerGoal(this));
	}

	protected void tickDeath() {
		if (this.deathTime == 18) {
			if (this.onGround && this.random.nextInt(4) == 0 && !this.level.isClientSide) {
				ZombieEvokerEntity entityToSpawn = new ZombieEvokerEntity(CrimsonStevesMobsModEntities.ZOMBIE_EVOKER.get(), (ServerLevel) level);
				entityToSpawn.moveTo(this.getX(), this.getY(), this.getZ(), getXRot(), getYRot());
				entityToSpawn.finalizeSpawn((ServerLevel) level, this.level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
				entityToSpawn.setNoAi(this.isNoAi());
				if (this.hasCustomName()) {
					entityToSpawn.setCustomName(this.getCustomName());
					entityToSpawn.setCustomNameVisible(this.isCustomNameVisible());
				}
				if (this.isPersistenceRequired()) {
					entityToSpawn.setPersistenceRequired();
				}
				entityToSpawn.setInvulnerable(this.isInvulnerable());
				entityToSpawn.setCanPickUpLoot(this.canPickUpLoot());
				for (EquipmentSlot equipmentslot : EquipmentSlot.values()) {
					ItemStack itemstack = this.getItemBySlot(equipmentslot);
					if (!itemstack.isEmpty()) {
						entityToSpawn.setItemSlot(equipmentslot, itemstack.copy());
						entityToSpawn.setDropChance(equipmentslot, this.getEquipmentDropChance(equipmentslot));
						itemstack.setCount(0);
					}
				}
				this.addMobToTeam(entityToSpawn);
				this.level.addFreshEntity(entityToSpawn);
				this.level.broadcastEntityEvent(this, (byte) 35);
				//this.playSound(SoundEvents.TOTEM_USE, 1, 1);
				this.discard();
			} else {
				this.spawnAtLocation(new ItemStack(Items.TOTEM_OF_UNDYING));
			}
		}
		super.tickDeath();
	}

	public void addMobToTeam(Entity mob) {
		if (this.getTeam() != null) {
			this.level.getScoreboard().addPlayerToTeam(mob.getStringUUID(), this.level.getScoreboard().getPlayerTeam(this.getTeam().getName()));
		}
	}

	class MassiveHealGoal extends Goal {
		final ZombieEvokerEntity mob;
		private int skillReadyTicks;
		final Random random = new Random();
		private final TargetingConditions vexCountTargeting = TargetingConditions.forNonCombat().range(3.0D).ignoreLineOfSight().ignoreInvisibilityTesting();

		public MassiveHealGoal(ZombieEvokerEntity mob) {
			this.mob = mob;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		public boolean canUse() {
			if (mob.isAggressive() && !mob.skillActive) {
				int i = mob.level.getNearbyEntities(Zombie.class, this.vexCountTargeting, mob, mob.getBoundingBox().inflate(3.0D)).size() - 1;
				return random.nextInt(80) <= i && random.nextInt(80) <= i;
			}
			return skillReadyTicks > 0;
		}

		public void start() {
			mob.skillActive = true;
			mob.setAggressive(true);
			skillReadyTicks = 40;
			//mob.getPersistentData().putBoolean("Dancing", true);
			mob.getNavigation().stop();
			mob.playSound(SoundEvents.EVOKER_PREPARE_WOLOLO, 2, 0.5f);
		}

		public void tick() {
			this.skillReadyTicks--;
			if (skillReadyTicks == 1) {
				if (!mob.level.isClientSide) {
					double yoffset = mob.isPassenger() ? mob.getVehicle().getY() : mob.getY();
					mob.playSound(SoundEvents.ZOMBIE_VILLAGER_CURE, 2, 1f);
					AreaEffectCloud areaeffectcloud = new AreaEffectCloud(mob.level, mob.getX(), yoffset, mob.getZ());
					areaeffectcloud.setRadius(5F);
					areaeffectcloud.setWaitTime(0);
					areaeffectcloud.setDuration(10);
					//areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float) areaeffectcloud.getDuration());
					areaeffectcloud.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 2));
					mob.level.addFreshEntity(areaeffectcloud);
				} else
					skillReadyTicks++;
			}
		}

		public void stop() {
			mob.skillActive = false;
			mob.setAggressive(false);
		}
	}

	class FangsCastingGoal extends Goal {
		final ZombieEvokerEntity mob;
		private int skillReadyTicks;
		final Random random = new Random();
		Vec3 vec31;
		Vec3 vec32;
		Vec3 vec33;
		double dy;

		public FangsCastingGoal(ZombieEvokerEntity mob) {
			this.mob = mob;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		public boolean canUse() {
			if (mob.isAggressive() && !mob.skillActive)
				return random.nextInt(160) == 0;
			return skillReadyTicks > 0;
		}

		public void start() {
			mob.skillActive = true;
			mob.setAggressive(true);
			skillReadyTicks = 30;
			//mob.getPersistentData().putBoolean("Dancing", true);
			mob.getNavigation().stop();
			mob.playSound(SoundEvents.EVOKER_PREPARE_ATTACK, 2, 1);
			vec32 = mob.getTarget().position();
			dy = Math.max(vec32.y, mob.getY());
		}

		public void tick() {
			this.skillReadyTicks--;
			if ((skillReadyTicks + 1) % 2 == 0) {
				vec33 = ZombieEvokerEntity.this.calculateViewVector(random.nextInt(360), random.nextInt(180));
				vec31 = vec32.add(vec33.x * 5, 0, vec33.z * 5);
				BlockPos blockpos = new BlockPos(vec31.x, vec31.y, vec31.z);
				boolean flag = false;
				double d0 = 0.0D;
				do {
					BlockPos blockpos1 = blockpos.below();
					BlockState blockstate = mob.level.getBlockState(blockpos1);
					if (blockstate.isFaceSturdy(mob.level, blockpos1, Direction.UP)) {
						if (!mob.level.isEmptyBlock(blockpos)) {
							BlockState blockstate1 = mob.level.getBlockState(blockpos);
							VoxelShape voxelshape = blockstate1.getCollisionShape(mob.level, blockpos);
							if (!voxelshape.isEmpty()) {
								d0 = voxelshape.max(Direction.Axis.Y);
							}
						}
						flag = true;
						break;
					}
					blockpos = blockpos.below();
				} while (blockpos.getY() >= Mth.floor(dy) - 10);
				if (flag && !mob.level.isClientSide)
					mob.level.addFreshEntity(new EvokerFangs(mob.level, vec31.x, (double) blockpos.getY() + d0, vec31.z, random.nextInt(360), 0, mob));
			}
		}

		public void stop() {
			mob.skillActive = false;
			mob.setAggressive(false);
			//mob.getPersistentData().putBoolean("Dancing", false);
		}
	}

	class SummonZombieRavagerGoal extends Goal {
		final ZombieEvokerEntity mob;
		private int skillReadyTicks;
		final Random random = new Random();

		public SummonZombieRavagerGoal(ZombieEvokerEntity mob) {
			this.mob = mob;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		public boolean canUse() {
			if (mob.isAggressive() && !mob.skillActive && !mob.isPassenger())
				return random.nextInt(30) == 0 && random.nextInt(30) == 0;
			return skillReadyTicks > 0;
		}

		public void start() {
			mob.skillActive = true;
			mob.setAggressive(true);
			skillReadyTicks = 60;
			//mob.getPersistentData().putBoolean("Dancing", true);
			mob.getNavigation().stop();
		}

		public void tick() {
			this.skillReadyTicks--;
			if (skillReadyTicks > 45) {
				if (skillReadyTicks % 3 == 0)
					mob.playSound(SoundEvents.EVOKER_CAST_SPELL, 3, 0.5f + (float) (60 - skillReadyTicks / 10));
			} else if (skillReadyTicks == 44) {
				BlockPos blockpos = new BlockPos(mob.getX(), mob.getY(), mob.getZ());
				boolean flag = false;
				double d0 = 0.0D;
				do {
					BlockPos blockpos1 = blockpos.below();
					BlockState blockstate = mob.level.getBlockState(blockpos1);
					if (blockstate.isFaceSturdy(mob.level, blockpos1, Direction.UP)) {
						if (!mob.level.isEmptyBlock(blockpos)) {
							BlockState blockstate1 = mob.level.getBlockState(blockpos);
							VoxelShape voxelshape = blockstate1.getCollisionShape(mob.level, blockpos);
							if (!voxelshape.isEmpty()) {
								d0 = voxelshape.max(Direction.Axis.Y);
							}
						}
						flag = true;
						break;
					}
					blockpos = blockpos.below();
				} while (blockpos.getY() >= Mth.floor(mob.getY()) - 20);
				if (flag && mob.level instanceof ServerLevel _level) {
					ZombieRavagerEntity entityToSpawn = new ZombieRavagerEntity(CrimsonStevesMobsModEntities.ZOMBIE_RAVAGER.get(), (ServerLevel) mob.level);
					mob.playSound(SoundEvents.EVOKER_PREPARE_SUMMON, 3, 0.5f);
					entityToSpawn.moveTo(mob.getX(), blockpos.getY() + d0, mob.getZ(), mob.getYRot(), mob.getXRot());
					entityToSpawn.finalizeSpawn(_level, mob.level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
					//mob.emergeTicks = 40;
					mob.addMobToTeam(entityToSpawn);
					mob.level.addFreshEntity(entityToSpawn);
				}
			}
		}

		public void stop() {
			mob.skillActive = false;
			mob.setAggressive(false);
			//mob.getPersistentData().putBoolean("Dancing", false);
		}
	}

	class SummonZombiesGoal extends Goal {
		final ZombieEvokerEntity mob;
		private int skillReadyTicks;
		final Random random = new Random();
		Vec3 vec31;

		//int mobSpawnType;
		public SummonZombiesGoal(ZombieEvokerEntity mob) {
			this.mob = mob;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		public boolean canUse() {
			if (mob.isAggressive() && !mob.skillActive)
				return random.nextInt(160) == 0;
			return skillReadyTicks > 0;
		}

		public void start() {
			mob.skillActive = true;
			mob.setAggressive(true);
			skillReadyTicks = 40;
			//mob.getPersistentData().putBoolean("Dancing", true);
			mob.getNavigation().stop();
			mob.playSound(SoundEvents.EVOKER_CAST_SPELL, 2, 1);
			/*
			for (int i = 0; i < 3; i++) {
				BlockPos blockpos = new BlockPos(mob.getX() + (random.nextFloat() - 0.5) * 6, mob.getY(),
						mob.getZ() + (random.nextFloat() - 0.5) * 6);
				boolean flag = false;
				double d0 = 0.0D;
				do {
					BlockPos blockpos1 = blockpos.below();
					BlockState blockstate = mob.level.getBlockState(blockpos1);
					if (blockstate.isFaceSturdy(mob.level, blockpos1, Direction.UP)) {
						if (!mob.level.isEmptyBlock(blockpos)) {
							BlockState blockstate1 = mob.level.getBlockState(blockpos);
							VoxelShape voxelshape = blockstate1.getCollisionShape(mob.level, blockpos);
							if (!voxelshape.isEmpty()) {
								d0 = voxelshape.max(Direction.Axis.Y);
							}
						}
						flag = true;
						break;
					}
					blockpos = blockpos.below();
				} while (blockpos.getY() >= Mth.floor(mob.getY()) - 10);
				if (mob.level instanceof ServerLevel _level) {
					Zombie entityToSpawn;
					if (random.nextInt(2) == 0)
						entityToSpawn = new ZombieVindicatorEntity(WorkspaceModEntities.ZOMBIE_VINDICATOR.get(), _level);
					else
						entityToSpawn = new ZombiePillagerEntity(WorkspaceModEntities.ZOMBIE_PILLAGER.get(), _level);
					entityToSpawn.moveTo(blockpos.getX(), blockpos.getY() + d0, blockpos.getZ(), random.nextFloat() * 360F, 0);
					entityToSpawn.finalizeSpawn(_level, mob.level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED,
							null, null);
					mob.addMobToTeam(entityToSpawn);
					mob.level.addFreshEntity(entityToSpawn);
				}
			}
			*/
		}

		public void tick() {
			this.skillReadyTicks--;
			if ((this.skillReadyTicks + 1) % 12 == 0) {
				double yoffset = mob.isPassenger() ? mob.getVehicle().getY() : mob.getY();
				vec31 = mob.position().add((random.nextFloat() - 0.5) * 6, 0, (random.nextFloat() - 0.5) * 6);
				BlockPos blockpos = new BlockPos(vec31.x, mob.getY(), vec31.z);
				boolean flag = false;
				double d0 = 0.0D;
				do {
					BlockPos blockpos1 = blockpos.below();
					BlockState blockstate = mob.level.getBlockState(blockpos1);
					if (blockstate.isFaceSturdy(mob.level, blockpos1, Direction.UP)) {
						if (!mob.level.isEmptyBlock(blockpos)) {
							BlockState blockstate1 = mob.level.getBlockState(blockpos);
							VoxelShape voxelshape = blockstate1.getCollisionShape(mob.level, blockpos);
							if (!voxelshape.isEmpty()) {
								d0 = voxelshape.max(Direction.Axis.Y);
							}
						}
						flag = true;
						break;
					}
					blockpos = blockpos.below();
				} while (blockpos.getY() >= Mth.floor(yoffset) - 10);
				if (flag && mob.level instanceof ServerLevel _level) {
					Zombie entityToSpawn;
					//mobSpawnType = random.nextInt(100);
					/*
					if (mobSpawnType == 0)
						entityToSpawn = new ZombieRavagerEntity(WorkspaceModEntities.ZOMBIE_RAVAGER.get(), _level);
					else if (mobSpawnType < 50)
						entityToSpawn = new ZombiePillagerEntity(WorkspaceModEntities.ZOMBIE_PILLAGER.get(), _level);
					else
						entityToSpawn = new ZombieVindicatorEntity(WorkspaceModEntities.ZOMBIE_VINDICATOR.get(), _level);
					*/
					if (random.nextInt(2) == 0)
						entityToSpawn = new ZombiePillagerEntity(CrimsonStevesMobsModEntities.ZOMBIE_PILLAGER.get(), _level);
					else
						entityToSpawn = new ZombieVindicatorEntity(CrimsonStevesMobsModEntities.ZOMBIE_VINDICATOR.get(), _level);
					entityToSpawn.moveTo(vec31.x, blockpos.getY() + d0, vec31.z, random.nextFloat() * 360F, 0);
					entityToSpawn.finalizeSpawn(_level, mob.level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
					mob.addMobToTeam(entityToSpawn);
					mob.level.addFreshEntity(entityToSpawn);
				}
			}
		}

		public void stop() {
			mob.skillActive = false;
			mob.setAggressive(false);
			//mob.getPersistentData().putBoolean("Dancing", false);
		}
	}

	public void customServerAiStep() {
		super.customServerAiStep();
		this.setShiftKeyDown(this.skillActive);
		//this.getPersistentData().putBoolean("Dancing", true);
		//this.getPersistentData().putBoolean("Dancing", false);
	}

	public boolean isBaby() {
		return false;
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.zombie_villager.ambient"));
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.zombie.step")), 0.15f, 1);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.zombie_villager.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.zombie_villager.death"));
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_34297_, DifficultyInstance p_34298_, MobSpawnType p_34299_, @Nullable SpawnGroupData p_34300_, @Nullable CompoundTag p_34301_) {
		p_34300_ = super.finalizeSpawn(p_34297_, p_34298_, p_34299_, p_34300_, p_34301_);
		this.emergeTicks = 30;
		this.moveTo(this.getX(), this.getY() - 3, this.getZ());
		return p_34300_;
	}

	public static void init() {
		SpawnPlacements.register(CrimsonStevesMobsModEntities.ZOMBIE_EVOKER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos,
				random) -> (world.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(world, pos, random) && CustomMathHelper.isOverworld(world) && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)));
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.15);
		builder = builder.add(Attributes.MAX_HEALTH, 60);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 1);
		return builder;
	}
}
