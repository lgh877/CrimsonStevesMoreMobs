
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;

import net.mcreator.crimson_steves_mobs.entity.RavagerEndermanEntity;
import net.mcreator.crimson_steves_mobs.RavagerEndermanModel;

public class RavagerEndermanRenderer extends MobRenderer<RavagerEndermanEntity, RavagerEndermanModel<RavagerEndermanEntity>> {
	public RavagerEndermanRenderer(EntityRendererProvider.Context context) {
		super(context, new RavagerEndermanModel(context.bakeLayer(RavagerEndermanModel.LAYER_LOCATION)), 1.1f);
		this.addLayer(new EyesLayer<RavagerEndermanEntity, RavagerEndermanModel<RavagerEndermanEntity>>(this) {
			@Override
			public RenderType renderType() {
				return RenderType.eyes(new ResourceLocation("crimson_steves_mobs:textures/ravager_enderman_eye.png"));
			}
		});
	}

	@Override
	public ResourceLocation getTextureLocation(RavagerEndermanEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/ravager_enderman.png");
	}
}
