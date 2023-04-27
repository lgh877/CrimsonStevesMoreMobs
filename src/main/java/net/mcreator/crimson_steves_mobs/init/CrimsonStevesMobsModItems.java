
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.crimson_steves_mobs.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.common.ForgeSpawnEggItem;

import net.minecraft.world.item.Item;

import net.mcreator.crimson_steves_mobs.item.RedstoneGrandArmorItem;
import net.mcreator.crimson_steves_mobs.item.RedstoneBombItem;
import net.mcreator.crimson_steves_mobs.item.LavaWaveItem;
import net.mcreator.crimson_steves_mobs.item.InsomniaLumpItem;
import net.mcreator.crimson_steves_mobs.item.HardenedSnowBallItem;
import net.mcreator.crimson_steves_mobs.item.FakeRavagerItem;
import net.mcreator.crimson_steves_mobs.item.FakeIronGolemItem;
import net.mcreator.crimson_steves_mobs.item.ExplosiveEggItem;
import net.mcreator.crimson_steves_mobs.item.EarthQuakeItem;
import net.mcreator.crimson_steves_mobs.item.BigSnowBallItem;
import net.mcreator.crimson_steves_mobs.CrimsonStevesMobsMod;

public class CrimsonStevesMobsModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, CrimsonStevesMobsMod.MODID);
	public static final RegistryObject<Item> CHAD_VILLAGER_SPAWN_EGG = REGISTRY.register("chad_villager_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.CHAD_VILLAGER, -8628136, -7576219, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_OTHERS_TAB)));
	public static final RegistryObject<Item> PAVAGER_SPAWN_EGG = REGISTRY.register("pavager_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.PAVAGER, -5211269, -606530, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_OTHERS_TAB)));
	public static final RegistryObject<Item> ENDER_RAVAGER_SPAWN_EGG = REGISTRY.register("ender_ravager_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.ENDER_RAVAGER, -13893588, -14809039, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_MONSTERS_TAB)));
	public static final RegistryObject<Item> ELITE_ENDER_RAVAGER_SPAWN_EGG = REGISTRY.register("elite_ender_ravager_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.ELITE_ENDER_RAVAGER, -4565534, -3497510, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_MONSTERS_TAB)));
	public static final RegistryObject<Item> T_RABUS_SPAWN_EGG = REGISTRY.register("t_rabus_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.T_RABUS, -10066330, -65536, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_ILLAGERS_TAB)));
	public static final RegistryObject<Item> RAVAGER_ENDERMAN_SPAWN_EGG = REGISTRY.register("ravager_enderman_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.RAVAGER_ENDERMAN, -14935012, -13620173, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_MONSTERS_TAB)));
	public static final RegistryObject<Item> DERPIG_SPAWN_EGG = REGISTRY.register("derpig_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.DERPIG, -26215, -39322, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_MONSTERS_TAB)));
	public static final RegistryObject<Item> ZOMBIE_COW_SPAWN_EGG = REGISTRY.register("zombie_cow_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.ZOMBIE_COW, -10092442, -13434880, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_MONSTERS_TAB)));
	public static final RegistryObject<Item> ZOMBIE_PIG_SPAWN_EGG = REGISTRY.register("zombie_pig_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.ZOMBIE_PIG, -26215, -16764160, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_MONSTERS_TAB)));
	public static final RegistryObject<Item> ZOMBIEFIED_CHAD_VILLAGER_SPAWN_EGG = REGISTRY.register("zombiefied_chad_villager_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.ZOMBIEFIED_CHAD_VILLAGER, -16751053, -11189437, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_MONSTERS_TAB)));
	public static final RegistryObject<Item> REDSTONE_POWERED_IRON_GOLEM_SPAWN_EGG = REGISTRY.register("redstone_powered_iron_golem_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.REDSTONE_POWERED_IRON_GOLEM, -3355444, -1, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_OTHERS_TAB)));
	public static final RegistryObject<Item> CYBORG_VINDICATOR_SPAWN_EGG = REGISTRY.register("cyborg_vindicator_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.CYBORG_VINDICATOR, -10066330, -13421773, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_ILLAGERS_TAB)));
	public static final RegistryObject<Item> CRUDE_REDSTONE_GOLEM_SPAWN_EGG = REGISTRY.register("crude_redstone_golem_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.CRUDE_REDSTONE_GOLEM, -10092544, -14672611, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_ILLAGERS_TAB)));
	public static final RegistryObject<Item> CRUDE_REDSTONE_MONSTROSITY_SPAWN_EGG = REGISTRY.register("crude_redstone_monstrosity_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.CRUDE_REDSTONE_MONSTROSITY, -14483456, -16777216, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_ILLAGERS_TAB)));
	public static final RegistryObject<Item> MINION_REDSTONE_GOLEM_SPAWN_EGG = REGISTRY.register("minion_redstone_golem_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.MINION_REDSTONE_GOLEM, -13434880, -13434880, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_ILLAGERS_TAB)));
	public static final RegistryObject<Item> REDSTONE_BOMB = REGISTRY.register("redstone_bomb", () -> new RedstoneBombItem());
	public static final RegistryObject<Item> BIG_SNOW_GOLEM_SPAWN_EGG = REGISTRY.register("big_snow_golem_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.BIG_SNOW_GOLEM, -1, -1, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_OTHERS_TAB)));
	public static final RegistryObject<Item> BIG_SNOW_BALL = REGISTRY.register("big_snow_ball", () -> new BigSnowBallItem());
	public static final RegistryObject<Item> HARDENED_SNOW_BALL = REGISTRY.register("hardened_snow_ball", () -> new HardenedSnowBallItem());
	public static final RegistryObject<Item> EARTH_QUAKE = REGISTRY.register("earth_quake", () -> new EarthQuakeItem());
	public static final RegistryObject<Item> ZOMBIE_VINDICATOR_SPAWN_EGG = REGISTRY.register("zombie_vindicator_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.ZOMBIE_VINDICATOR, -16751002, -13421773, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_MONSTERS_TAB)));
	public static final RegistryObject<Item> ZOMBIE_PILLAGER_SPAWN_EGG = REGISTRY.register("zombie_pillager_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.ZOMBIE_PILLAGER, -16751053, -13421773, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_MONSTERS_TAB)));
	public static final RegistryObject<Item> TAMED_PHANTOM_SPAWN_EGG = REGISTRY.register("tamed_phantom_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.TAMED_PHANTOM, -10066330, -16777165, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_ILLAGERS_TAB)));
	public static final RegistryObject<Item> EXPLOSIVE_PHANTOM_SPAWN_EGG = REGISTRY.register("explosive_phantom_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.EXPLOSIVE_PHANTOM, -10066330, -6737152, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_ILLAGERS_TAB)));
	public static final RegistryObject<Item> VEHICLE_PHANTOM_SPAWN_EGG = REGISTRY.register("vehicle_phantom_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.VEHICLE_PHANTOM, -13421773, -16777114, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_ILLAGERS_TAB)));
	public static final RegistryObject<Item> PHANTOM_TAMER_SPAWN_EGG = REGISTRY.register("phantom_tamer_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.PHANTOM_TAMER, -10066330, -13421569, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_ILLAGERS_TAB)));
	public static final RegistryObject<Item> INSOMNIA_LUMP = REGISTRY.register("insomnia_lump", () -> new InsomniaLumpItem());
	public static final RegistryObject<Item> ZOMBIE_EVOKER_SPAWN_EGG = REGISTRY.register("zombie_evoker_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.ZOMBIE_EVOKER, -16764109, -10066330, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_MONSTERS_TAB)));
	public static final RegistryObject<Item> ZOMBIE_RAVAGER_SPAWN_EGG = REGISTRY.register("zombie_ravager_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.ZOMBIE_RAVAGER, -13421773, -16764109, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_MONSTERS_TAB)));
	public static final RegistryObject<Item> CREEPIG_SPAWN_EGG = REGISTRY.register("creepig_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.CREEPIG, -16737997, -8211943, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_MONSTERS_TAB)));
	public static final RegistryObject<Item> CREEPER_GOLEM_SPAWN_EGG = REGISTRY.register("creeper_golem_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.CREEPER_GOLEM, -16751053, -16764109, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_MONSTERS_TAB)));
	public static final RegistryObject<Item> ANIMATED_TEMPLE_SPAWN_EGG = REGISTRY.register("animated_temple_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.ANIMATED_TEMPLE, -12500159, -12825025, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_MONSTERS_TAB)));
	public static final RegistryObject<Item> GIANT_ILLAGER_SPAWN_EGG = REGISTRY.register("giant_illager_spawn_egg", () -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.GIANT_ILLAGER, -10066330, -13421773, new Item.Properties().tab(null)));
	public static final RegistryObject<Item> REDSTONE_MONSTROSITY_SPAWN_EGG = REGISTRY.register("redstone_monstrosity_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.REDSTONE_MONSTROSITY, -10092544, -13421773, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_ILLAGERS_TAB)));
	public static final RegistryObject<Item> CREESTEVE_SPAWN_EGG = REGISTRY.register("creesteve_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.CREESTEVE, -16737895, -16737997, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_MONSTERS_TAB)));
	public static final RegistryObject<Item> REDSTONE_GRAND_ARMOR_HELMET = REGISTRY.register("redstone_grand_armor_helmet", () -> new RedstoneGrandArmorItem.Helmet());
	public static final RegistryObject<Item> REDSTONE_GRAND_ARMOR_CHESTPLATE = REGISTRY.register("redstone_grand_armor_chestplate", () -> new RedstoneGrandArmorItem.Chestplate());
	public static final RegistryObject<Item> REDSTONE_GRAND_ARMOR_LEGGINGS = REGISTRY.register("redstone_grand_armor_leggings", () -> new RedstoneGrandArmorItem.Leggings());
	public static final RegistryObject<Item> REDSTONE_GRAND_ARMOR_BOOTS = REGISTRY.register("redstone_grand_armor_boots", () -> new RedstoneGrandArmorItem.Boots());
	public static final RegistryObject<Item> FAKE_RAVAGER_CHESTPLATE = REGISTRY.register("fake_ravager_chestplate", () -> new FakeRavagerItem.Chestplate());
	public static final RegistryObject<Item> FAKE_IRON_GOLEM_CHESTPLATE = REGISTRY.register("fake_iron_golem_chestplate", () -> new FakeIronGolemItem.Chestplate());
	public static final RegistryObject<Item> FLYING_CHICKEN_SPAWN_EGG = REGISTRY.register("flying_chicken_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.FLYING_CHICKEN, -1, -3407872, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_OTHERS_TAB)));
	public static final RegistryObject<Item> CREECKIN_SPAWN_EGG = REGISTRY.register("creeckin_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.CREECKIN, -16738048, -10092544, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_MONSTERS_TAB)));
	public static final RegistryObject<Item> EXPLOSIVE_EGG = REGISTRY.register("explosive_egg", () -> new ExplosiveEggItem());
	public static final RegistryObject<Item> CHAD_CREEPER_GOLEM_SPAWN_EGG = REGISTRY.register("chad_creeper_golem_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.CHAD_CREEPER_GOLEM, -16764160, -16751104, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_MONSTERS_TAB)));
	public static final RegistryObject<Item> LAVA_MUTANT_ZOMBIE_SPAWN_EGG = REGISTRY.register("lava_mutant_zombie_spawn_egg",
			() -> new ForgeSpawnEggItem(CrimsonStevesMobsModEntities.LAVA_MUTANT_ZOMBIE, -3394816, -3381760, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_MONSTERS_TAB)));
	public static final RegistryObject<Item> LAVA_WAVE = REGISTRY.register("lava_wave", () -> new LavaWaveItem());
}
