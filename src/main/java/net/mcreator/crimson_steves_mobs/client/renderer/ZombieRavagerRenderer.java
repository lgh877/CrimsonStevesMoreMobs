
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.crimson_steves_mobs.entity.ZombieRavagerEntity;
import net.mcreator.crimson_steves_mobs.WardenAnimatedRavagerModel;

public class ZombieRavagerRenderer extends MobRenderer<ZombieRavagerEntity, WardenAnimatedRavagerModel<ZombieRavagerEntity>> {
	public ZombieRavagerRenderer(EntityRendererProvider.Context context) {
		super(context, new WardenAnimatedRavagerModel(context.bakeLayer(WardenAnimatedRavagerModel.LAYER_LOCATION)), 1.1f);
	}

	@Override
	public ResourceLocation getTextureLocation(ZombieRavagerEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/entities/zombieravager.png");
	}
}
