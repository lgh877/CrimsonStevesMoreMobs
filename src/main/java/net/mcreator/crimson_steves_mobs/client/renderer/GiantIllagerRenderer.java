
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.crimson_steves_mobs.entity.GiantIllagerEntity;
import net.mcreator.crimson_steves_mobs.GiantIllagerModel;

public class GiantIllagerRenderer extends MobRenderer<GiantIllagerEntity, GiantIllagerModel<GiantIllagerEntity>> {
	public GiantIllagerRenderer(EntityRendererProvider.Context context) {
		super(context, new GiantIllagerModel(context.bakeLayer(GiantIllagerModel.LAYER_LOCATION)), 4f);
	}

	@Override
	public ResourceLocation getTextureLocation(GiantIllagerEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/entities/nothing.png");
	}
}
