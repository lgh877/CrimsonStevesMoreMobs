package net.mcreator.crimson_steves_mobs.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;

public class CrimsonStevesMobsConfig {
    public static ForgeConfigSpec.BooleanValue toggle_undeadVsIllagers;
    public static ForgeConfigSpec.BooleanValue toggle_zombieInfection;

    public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> t_rabus_raidcount;
    public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> cyborg_vindicator_raidcount;
    public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> crude_redstone_golem_raidcount;
    public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> crude_redstone_monstrosity_raidcount;
    public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> redstone_monstrosity_raidcount;
    public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> minion_redstone_golem_raidcount;
    public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> phantom_tamer_raidcount;

    public static void init(ForgeConfigSpec.Builder common, ForgeConfigSpec.Builder client) {
        common.push("Settings");

        toggle_undeadVsIllagers = common
                .comment("Should undead mobs and Illagers actively fight each other upon sight?",
                        "Default = true")
                .define("toggle_undeadVsIllagers", true);

        toggle_zombieInfection = common
                .comment("Should certain mobs (mainly Illagers) zombify upon being killed by an undead?",
                        "Default = true")
                .define("toggle_zombieInfection", true);

        common.push("Raider spawns");

        t_rabus_raidcount = common
                .comment("How many T-Rabus each wave", "Requires game restart", "Must have no more and no less than 8 integers")
                .worldRestart()
                .defineList("t_rabus_raidcount", Arrays.asList(0, 0, 0, 0, 1, 0, 1, 0), s -> s instanceof Integer);

        cyborg_vindicator_raidcount = common
                .comment("How many Cyborg Vindicator each wave", "Requires game restart", "Must have no more and no less than 8 integers")
                .worldRestart()
                .defineList("cyborg_vindicator_raidcount", Arrays.asList(0, 1, 1, 1, 1, 1, 1, 0), s -> s instanceof Integer);

        crude_redstone_golem_raidcount = common
                .comment("How many Crude Redstone Golem each wave", "Requires game restart", "Must have no more and no less than 8 integers")
                .worldRestart()
                .defineList("crude_redstone_golem_raidcount", Arrays.asList(0, 0, 0, 0, 0, 1, 0, 3), s -> s instanceof Integer);

        crude_redstone_monstrosity_raidcount = common
                .comment("How many Crude Redstone Monstrosity each wave", "Requires game restart", "Must have no more and no less than 8 integers")
                .worldRestart()
                .defineList("crude_redstone_monstrosity_raidcount", Arrays.asList(0, 0, 0, 0, 0, 1, 0, 0), s -> s instanceof Integer);

        redstone_monstrosity_raidcount = common
                .comment("How many Redstone Monstrosity each wave", "Requires game restart", "Must have no more and no less than 8 integers")
                .worldRestart()
                .defineList("redstone_monstrosity_raidcount", Arrays.asList(0, 0, 0, 0, 0, 0, 1, 0), s -> s instanceof Integer);

        minion_redstone_golem_raidcount = common
                .comment("How many Minion Redstone Golem each wave", "Requires game restart", "Must have no more and no less than 8 integers")
                .worldRestart()
                .defineList("minion_redstone_golem_raidcount", Arrays.asList(0, 2, 3, 4, 5, 6, 6, 6), s -> s instanceof Integer);

        minion_redstone_golem_raidcount = common
                .comment("How many Minion Redstone Golem each wave", "Requires game restart", "Must have no more and no less than 8 integers")
                .worldRestart()
                .defineList("minion_redstone_golem_raidcount", Arrays.asList(0, 2, 3, 4, 5, 6, 6, 6), s -> s instanceof Integer);

        phantom_tamer_raidcount = common
                .comment("How many Phantom Tamer each wave", "Requires game restart", "Must have no more and no less than 8 integers")
                .worldRestart()
                .defineList("phantom_tamer_raidcount", Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0), s -> s instanceof Integer);

        common.pop();

        common.pop();
    }
}
