
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.crimson_steves_mobs.init;

import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.level.GameRules;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CrimsonStevesMobsModGameRules {
	public static final GameRules.Key<GameRules.BooleanValue> SHOULDSPAWNADDITIONALPATROLS = GameRules.register("shouldSpawnAdditionalPatrols", GameRules.Category.SPAWNING, GameRules.BooleanValue.create(true));
	public static final GameRules.Key<GameRules.BooleanValue> SHOULDSPAWNFAKERAVAGER = GameRules.register("shouldSpawnFakeRavager", GameRules.Category.SPAWNING, GameRules.BooleanValue.create(true));
}
