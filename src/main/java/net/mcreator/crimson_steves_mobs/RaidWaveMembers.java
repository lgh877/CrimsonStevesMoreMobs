/**
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside net.mcreator.crimson_steves_mobs as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
*/
package net.mcreator.crimson_steves_mobs;

import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.EntityType;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.config.CrimsonStevesMobsConfig;

import java.util.List;
import java.util.ArrayList;

public class RaidWaveMembers {
	public static final List<Raid.RaiderType> CUSTOM_RAID_MEMBERS = new ArrayList<>();
	public static Raid.RaiderType T_RABUS;
	public static Raid.RaiderType CYBORG_VINDICATOR;
	public static Raid.RaiderType CRUDE_REDSTONE_GOLEM;
	public static Raid.RaiderType CRUDE_REDSTONE_MONSTROSITY;
	public static Raid.RaiderType REDSTONE_MONSTROSITY;
	public static Raid.RaiderType MINION_REDSTONE_GOLEM;
	public static Raid.RaiderType PHANTOM_TAMER;

	public static void registerWaveMembers() {
		T_RABUS = translateToWaves("t_rabus_boss", CrimsonStevesMobsModEntities.T_RABUS.get(), CrimsonStevesMobsConfig.t_rabus_raidcount.get());
		CYBORG_VINDICATOR = translateToWaves("modid:cyborg_vindicator", CrimsonStevesMobsModEntities.CYBORG_VINDICATOR.get(), CrimsonStevesMobsConfig.cyborg_vindicator_raidcount.get());
		CRUDE_REDSTONE_GOLEM = translateToWaves("crude_redstone_golem", CrimsonStevesMobsModEntities.CRUDE_REDSTONE_GOLEM.get(), CrimsonStevesMobsConfig.crude_redstone_golem_raidcount.get());
		CRUDE_REDSTONE_MONSTROSITY = translateToWaves("crude_redstone_monstrosity", CrimsonStevesMobsModEntities.CRUDE_REDSTONE_MONSTROSITY.get(), CrimsonStevesMobsConfig.crude_redstone_monstrosity_raidcount.get());
		REDSTONE_MONSTROSITY = translateToWaves("original_redstone_monstrosity", CrimsonStevesMobsModEntities.REDSTONE_MONSTROSITY.get(), CrimsonStevesMobsConfig.redstone_monstrosity_raidcount.get());
		MINION_REDSTONE_GOLEM = translateToWaves("mini_crude_redstone_golem", CrimsonStevesMobsModEntities.MINION_REDSTONE_GOLEM.get(), CrimsonStevesMobsConfig.minion_redstone_golem_raidcount.get());
		PHANTOM_TAMER = translateToWaves("phantom_tamer", CrimsonStevesMobsModEntities.PHANTOM_TAMER.get(), CrimsonStevesMobsConfig.phantom_tamer_raidcount.get());
	}

	private static Raid.RaiderType translateToWaves(String name, EntityType<? extends Raider> type, List<? extends Integer> list) {
		Raid.RaiderType member = Raid.RaiderType.create(name, type, new int[]{list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6), list.get(7)});
		CUSTOM_RAID_MEMBERS.add(member);
		return member;
	}
}
