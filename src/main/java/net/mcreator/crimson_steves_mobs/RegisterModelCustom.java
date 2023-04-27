/**
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside net.mcreator.workspace as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
*/
package net.mcreator.crimson_steves_mobs;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class RegisterModelCustom {
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(PavagerModel.LAYER_LOCATION, PavagerModel::createBodyLayer);
		event.registerLayerDefinition(EnderRavagerModel.LAYER_LOCATION, EnderRavagerModel::createBodyLayer);
		event.registerLayerDefinition(RavagerEndermanModel.LAYER_LOCATION, RavagerEndermanModel::createBodyLayer);
		event.registerLayerDefinition(AnimatedRedstoneGolemModel.LAYER_LOCATION, AnimatedRedstoneGolemModel::createBodyLayer);
		event.registerLayerDefinition(ChadVindicatorModel.LAYER_LOCATION, ChadVindicatorModel::createBodyLayer);
		event.registerLayerDefinition(GolemAnimatedWarden.LAYER_LOCATION, GolemAnimatedWarden::createBodyLayer);
		event.registerLayerDefinition(BigSnowGolemModel.LAYER_LOCATION, BigSnowGolemModel::createBodyLayer);
		event.registerLayerDefinition(WardenAnimatedRavagerModel.LAYER_LOCATION, WardenAnimatedRavagerModel::createBodyLayer);
		event.registerLayerDefinition(WardenAnimatedIllagerModel.LAYER_LOCATION, WardenAnimatedIllagerModel::createBodyLayer);
		event.registerLayerDefinition(PreAnimatedRedstoneMonstrosityModel.LAYER_LOCATION, PreAnimatedRedstoneMonstrosityModel::createBodyLayer);
		event.registerLayerDefinition(GiantIllagerModel.LAYER_LOCATION, GiantIllagerModel::createBodyLayer);
		event.registerLayerDefinition(MutantZombieModel.LAYER_LOCATION, MutantZombieModel::createBodyLayer);
		event.registerLayerDefinition(IronGolemHeadModel.LAYER_LOCATION, IronGolemHeadModel::createBodyLayer);
		event.registerLayerDefinition(IronGolemArmsModel.LAYER_LOCATION, IronGolemArmsModel::createBodyLayer);
		event.registerLayerDefinition(IronGolemBodyModel.LAYER_LOCATION, IronGolemBodyModel::createBodyLayer);
	}
}
