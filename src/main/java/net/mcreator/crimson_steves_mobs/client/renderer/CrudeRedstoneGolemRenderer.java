
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.crimson_steves_mobs.entity.CrudeRedstoneGolemEntity;
import net.mcreator.crimson_steves_mobs.GolemAnimatedWardenRenderer;
import net.mcreator.crimson_steves_mobs.GolemAnimatedWarden;
import net.mcreator.crimson_steves_mobs.CustomWardenEmissiveLayer;

public class CrudeRedstoneGolemRenderer extends GolemAnimatedWardenRenderer<CrudeRedstoneGolemEntity, GolemAnimatedWarden<CrudeRedstoneGolemEntity>> {
	public CrudeRedstoneGolemRenderer(EntityRendererProvider.Context context) {
		super(context, new GolemAnimatedWarden(context.bakeLayer(GolemAnimatedWarden.LAYER_LOCATION)), 1f);
		this.addLayer(new CustomWardenEmissiveLayer<>(this, new ResourceLocation("crimson_steves_mobs:textures/entities/redstone-golem-layer.png"), (p_234805_, p_234806_, p_234807_) -> {
			return Math.max(0.0F, Mth.cos(p_234807_ * 0.2F));
		}, GolemAnimatedWarden::getPulsatingSpotsLayerModelParts));
	}

	@Override
	public ResourceLocation getTextureLocation(CrudeRedstoneGolemEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/redstone-golem-on-planetminecraft-com.png");
	}
}
