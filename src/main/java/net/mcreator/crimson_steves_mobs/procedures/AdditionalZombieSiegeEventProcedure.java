package net.mcreator.crimson_steves_mobs.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.village.VillageSiegeEvent;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.entity.ZombiefiedChadVillagerEntity;
import net.mcreator.crimson_steves_mobs.entity.ZombiePigEntity;
import net.mcreator.crimson_steves_mobs.entity.ZombieEvokerEntity;
import net.mcreator.crimson_steves_mobs.entity.ZombieCowEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class AdditionalZombieSiegeEventProcedure {
	@SubscribeEvent
	public static void onVillageSiege(VillageSiegeEvent event) {
		execute(event, event.getLevel(), event.getAttemptedSpawnPos().x, event.getAttemptedSpawnPos().y, event.getAttemptedSpawnPos().z);
	}

	public static void execute(LevelAccessor world, double x, double y, double z) {
		execute(null, world, x, y, z);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z) {
		double mobCount = 0;
		double customX = 0;
		double customZ = 0;
		for (int index0 = 0; index0 < (int) (Mth.nextInt(RandomSource.create(), 1, 10)); index0++) {
			customX = Mth.nextInt(RandomSource.create(), 1, 20) - 10 + x;
			customZ = Mth.nextInt(RandomSource.create(), 1, 20) - 10 + z;
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = new ZombieCowEntity(CrimsonStevesMobsModEntities.ZOMBIE_COW.get(), _level);
				entityToSpawn.moveTo(customX, (world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) customX, (int) customZ)), customZ, world.getRandom().nextFloat() * 360F, 0);
				if (entityToSpawn instanceof Mob _mobToSpawn)
					_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
				world.addFreshEntity(entityToSpawn);
			}
		}
		for (int index1 = 0; index1 < (int) (Mth.nextInt(RandomSource.create(), 1, 10)); index1++) {
			customX = Mth.nextInt(RandomSource.create(), 1, 20) - 10 + x;
			customZ = Mth.nextInt(RandomSource.create(), 1, 20) - 10 + z;
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = new ZombiePigEntity(CrimsonStevesMobsModEntities.ZOMBIE_PIG.get(), _level);
				entityToSpawn.moveTo(customX, (world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) customX, (int) customZ)), customZ, world.getRandom().nextFloat() * 360F, 0);
				if (entityToSpawn instanceof Mob _mobToSpawn)
					_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
				world.addFreshEntity(entityToSpawn);
			}
		}
		for (int index2 = 0; index2 < (int) (Mth.nextInt(RandomSource.create(), 1, 3)); index2++) {
			customX = Mth.nextInt(RandomSource.create(), 1, 20) - 10 + x;
			customZ = Mth.nextInt(RandomSource.create(), 1, 20) - 10 + z;
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = new ZombiefiedChadVillagerEntity(CrimsonStevesMobsModEntities.ZOMBIEFIED_CHAD_VILLAGER.get(), _level);
				entityToSpawn.moveTo(customX, (world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) customX, (int) customZ)), customZ, world.getRandom().nextFloat() * 360F, 0);
				if (entityToSpawn instanceof Mob _mobToSpawn)
					_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
				world.addFreshEntity(entityToSpawn);
			}
		}
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = new ZombieEvokerEntity(CrimsonStevesMobsModEntities.ZOMBIE_EVOKER.get(), _level);
			entityToSpawn.moveTo(x, y, z, world.getRandom().nextFloat() * 360F, 0);
			if (entityToSpawn instanceof Mob _mobToSpawn)
				_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
			world.addFreshEntity(entityToSpawn);
		}
	}
}
