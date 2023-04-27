package net.mcreator.crimson_steves_mobs.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.npc.Villager;

public class ChadVillagerNaturalEntitySpawningConditionProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		return !world.getEntitiesOfClass(Villager.class, AABB.ofSize(new Vec3(x, y, z), 20, 20, 20), e -> true).isEmpty();
	}
}
