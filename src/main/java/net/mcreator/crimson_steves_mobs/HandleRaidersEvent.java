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

import org.apache.commons.lang3.ArrayUtils;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.level.LevelEvent;

import net.minecraft.world.entity.raid.Raid;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HandleRaidersEvent {
	@SubscribeEvent
	public static void addRaidMembers(LevelEvent.Load event) {
		RaidWaveMembers.registerWaveMembers();
	}

	// remove our illagers from raids after leaving the world to prevent a post-mod-removal crash
	@SubscribeEvent
	public static void removeRaidMembers(LevelEvent.Unload event) {
		Raid.RaiderType[] members = Raid.RaiderType.values();
		for (Raid.RaiderType member : members) {
			if (RaidWaveMembers.CUSTOM_RAID_MEMBERS.contains(member)) {
				ArrayUtils.remove(members, member.ordinal());
				CrimsonStevesMobsMod.LOGGER.info("Removed " + member.name() + " from Raids to prevent a post-mod-removal crash");
			}
		}
	}
}
