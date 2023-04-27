
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.crimson_steves_mobs.entity.BigSnowGolemEntity;
import net.mcreator.crimson_steves_mobs.BigSnowGolemModel;
import net.mcreator.crimson_steves_mobs.AnimatedRedstoneGolemRenderer;

public class BigSnowGolemRenderer extends AnimatedRedstoneGolemRenderer<BigSnowGolemEntity, BigSnowGolemModel<BigSnowGolemEntity>> {
	public BigSnowGolemRenderer(EntityRendererProvider.Context context) {
		super(context, new BigSnowGolemModel(context.bakeLayer(BigSnowGolemModel.LAYER_LOCATION)), 1.1f);
	}

	@Override
	public ResourceLocation getTextureLocation(BigSnowGolemEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/big_snow_golem.png");
	}
}
