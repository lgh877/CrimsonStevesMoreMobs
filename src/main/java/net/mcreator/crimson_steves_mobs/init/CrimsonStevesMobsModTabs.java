
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.crimson_steves_mobs.init;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;

public class CrimsonStevesMobsModTabs {
	public static CreativeModeTab TAB_ILLAGERS_TAB;
	public static CreativeModeTab TAB_MONSTERS_TAB;
	public static CreativeModeTab TAB_OTHERS_TAB;
	public static CreativeModeTab TAB_ITEMS_OF_CSMM;

	public static void load() {
		TAB_ILLAGERS_TAB = new CreativeModeTab("tabillagers_tab") {
			@Override
			public ItemStack makeIcon() {
				return new ItemStack(Items.CROSSBOW);
			}

			@Override
			public boolean hasSearchBar() {
				return false;
			}
		};
		TAB_MONSTERS_TAB = new CreativeModeTab("tabmonsters_tab") {
			@Override
			public ItemStack makeIcon() {
				return new ItemStack(Blocks.ZOMBIE_HEAD);
			}

			@Override
			public boolean hasSearchBar() {
				return false;
			}
		};
		TAB_OTHERS_TAB = new CreativeModeTab("tabothers_tab") {
			@Override
			public ItemStack makeIcon() {
				return new ItemStack(Items.PIG_SPAWN_EGG);
			}

			@Override
			public boolean hasSearchBar() {
				return false;
			}
		};
		TAB_ITEMS_OF_CSMM = new CreativeModeTab("tabitems_of_csmm") {
			@Override
			public ItemStack makeIcon() {
				return new ItemStack(CrimsonStevesMobsModItems.EXPLOSIVE_EGG.get());
			}

			@Override
			public boolean hasSearchBar() {
				return false;
			}
		};
	}
}
