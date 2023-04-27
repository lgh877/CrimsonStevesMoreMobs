
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.crimson_steves_mobs.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;

import net.mcreator.crimson_steves_mobs.client.renderer.ZombiefiedChadVillagerRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.ZombieVindicatorRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.ZombieRavagerRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.ZombiePillagerRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.ZombiePigRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.ZombieEvokerRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.ZombieCowRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.VehiclePhantomRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.TamedPhantomRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.TRabusRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.RedstonePoweredIronGolemRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.RedstoneMonstrosityRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.RedstoneBombRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.RavagerEndermanRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.PhantomTamerRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.PavagerRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.MinionRedstoneGolemRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.LavaMutantZombieRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.InsomniaLumpRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.GiantIllagerRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.FlyingChickenRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.ExplosivePhantomRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.EnderRavagerRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.EliteEnderRavagerRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.DerpigRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.CyborgVindicatorRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.CrudeRedstoneMonstrosityRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.CrudeRedstoneGolemRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.CreesteveRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.CreepigRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.CreeperGolemRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.CreeckinRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.ConstructOfEnderHeadRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.ChadVillagerRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.ChadCreeperGolemRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.BigSnowGolemRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.BigSnowBallRenderer;
import net.mcreator.crimson_steves_mobs.client.renderer.AnimatedTempleRenderer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CrimsonStevesMobsModEntityRenderers {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.CHAD_VILLAGER.get(), ChadVillagerRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.PAVAGER.get(), PavagerRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.ENDER_RAVAGER.get(), EnderRavagerRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.ELITE_ENDER_RAVAGER.get(), EliteEnderRavagerRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.T_RABUS.get(), TRabusRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.RAVAGER_ENDERMAN.get(), RavagerEndermanRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.DERPIG.get(), DerpigRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.ZOMBIE_COW.get(), ZombieCowRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.ZOMBIE_PIG.get(), ZombiePigRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.ZOMBIEFIED_CHAD_VILLAGER.get(), ZombiefiedChadVillagerRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.REDSTONE_POWERED_IRON_GOLEM.get(), RedstonePoweredIronGolemRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.CYBORG_VINDICATOR.get(), CyborgVindicatorRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.CRUDE_REDSTONE_GOLEM.get(), CrudeRedstoneGolemRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.CRUDE_REDSTONE_MONSTROSITY.get(), CrudeRedstoneMonstrosityRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.MINION_REDSTONE_GOLEM.get(), MinionRedstoneGolemRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.REDSTONE_BOMB.get(), RedstoneBombRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.BIG_SNOW_GOLEM.get(), BigSnowGolemRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.BIG_SNOW_BALL.get(), BigSnowBallRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.HARDENED_SNOW_BALL.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.EARTH_QUAKE.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.ZOMBIE_VINDICATOR.get(), ZombieVindicatorRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.ZOMBIE_PILLAGER.get(), ZombiePillagerRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.TAMED_PHANTOM.get(), TamedPhantomRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.EXPLOSIVE_PHANTOM.get(), ExplosivePhantomRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.VEHICLE_PHANTOM.get(), VehiclePhantomRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.PHANTOM_TAMER.get(), PhantomTamerRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.INSOMNIA_LUMP.get(), InsomniaLumpRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.ZOMBIE_EVOKER.get(), ZombieEvokerRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.ZOMBIE_RAVAGER.get(), ZombieRavagerRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.CREEPIG.get(), CreepigRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.CREEPER_GOLEM.get(), CreeperGolemRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.ANIMATED_TEMPLE.get(), AnimatedTempleRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.GIANT_ILLAGER.get(), GiantIllagerRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.REDSTONE_MONSTROSITY.get(), RedstoneMonstrosityRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.CREESTEVE.get(), CreesteveRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.FLYING_CHICKEN.get(), FlyingChickenRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.CREECKIN.get(), CreeckinRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.CHAD_CREEPER_GOLEM.get(), ChadCreeperGolemRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.CONSTRUCT_OF_ENDER_HEAD.get(), ConstructOfEnderHeadRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.LAVA_MUTANT_ZOMBIE.get(), LavaMutantZombieRenderer::new);
		event.registerEntityRenderer(CrimsonStevesMobsModEntities.LAVA_WAVE.get(), ThrownItemRenderer::new);
	}
}
