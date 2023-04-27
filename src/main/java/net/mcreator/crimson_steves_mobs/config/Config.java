package net.mcreator.crimson_steves_mobs.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.mcreator.crimson_steves_mobs.CrimsonStevesMobsMod;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.io.File;

@Mod.EventBusSubscriber
public class Config {
    private static final ForgeConfigSpec.Builder common_builder = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec common_config;

    private static final ForgeConfigSpec.Builder client_builder = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec client_config;

    static
    {
        CrimsonStevesMobsConfig.init(common_builder, client_builder);

        common_config = common_builder.build();
        client_config = client_builder.build();
    }

    public static void loadConfig(ForgeConfigSpec config, String path)
    {
        CrimsonStevesMobsMod.LOGGER.info("Loading config: " + path);
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        CrimsonStevesMobsMod.LOGGER.info("Built config: " + path);
        file.load();
        CrimsonStevesMobsMod.LOGGER.info("Loaded config: " + path);
        config.setConfig(file);
    }
}
