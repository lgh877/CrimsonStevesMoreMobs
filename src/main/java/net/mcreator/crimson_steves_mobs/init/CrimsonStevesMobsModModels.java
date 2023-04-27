
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.crimson_steves_mobs.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.mcreator.crimson_steves_mobs.client.model.Modelredstone_grandarmor;
import net.mcreator.crimson_steves_mobs.client.model.ModelanimatedRavagerModel;
import net.mcreator.crimson_steves_mobs.client.model.ModelCustomHoglinModel;
import net.mcreator.crimson_steves_mobs.client.model.ModelAnimatedWardenPigModel;
import net.mcreator.crimson_steves_mobs.client.model.ModelAnimatedWardenModel;
import net.mcreator.crimson_steves_mobs.client.model.ModelAnimatedWardenIronGolemModel;
import net.mcreator.crimson_steves_mobs.client.model.ModelAnimatedWardenCowModel;
import net.mcreator.crimson_steves_mobs.client.model.ModelAnimatedRedstoneGolemModel;
import net.mcreator.crimson_steves_mobs.client.model.ModelAnimatedIronGolemModel;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class CrimsonStevesMobsModModels {
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModelanimatedRavagerModel.LAYER_LOCATION, ModelanimatedRavagerModel::createBodyLayer);
		event.registerLayerDefinition(ModelAnimatedIronGolemModel.LAYER_LOCATION, ModelAnimatedIronGolemModel::createBodyLayer);
		event.registerLayerDefinition(ModelAnimatedWardenIronGolemModel.LAYER_LOCATION, ModelAnimatedWardenIronGolemModel::createBodyLayer);
		event.registerLayerDefinition(ModelAnimatedWardenModel.LAYER_LOCATION, ModelAnimatedWardenModel::createBodyLayer);
		event.registerLayerDefinition(ModelCustomHoglinModel.LAYER_LOCATION, ModelCustomHoglinModel::createBodyLayer);
		event.registerLayerDefinition(ModelAnimatedWardenCowModel.LAYER_LOCATION, ModelAnimatedWardenCowModel::createBodyLayer);
		event.registerLayerDefinition(ModelAnimatedRedstoneGolemModel.LAYER_LOCATION, ModelAnimatedRedstoneGolemModel::createBodyLayer);
		event.registerLayerDefinition(Modelredstone_grandarmor.LAYER_LOCATION, Modelredstone_grandarmor::createBodyLayer);
		event.registerLayerDefinition(ModelAnimatedWardenPigModel.LAYER_LOCATION, ModelAnimatedWardenPigModel::createBodyLayer);
	}
}
