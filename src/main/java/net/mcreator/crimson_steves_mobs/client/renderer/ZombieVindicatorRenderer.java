
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.crimson_steves_mobs.entity.ZombieVindicatorEntity;
import net.mcreator.crimson_steves_mobs.WardenAnimatedIllagerModel;

public class ZombieVindicatorRenderer extends MobRenderer<ZombieVindicatorEntity, WardenAnimatedIllagerModel<ZombieVindicatorEntity>> {
	public ZombieVindicatorRenderer(EntityRendererProvider.Context context) {
		super(context, new WardenAnimatedIllagerModel(context.bakeLayer(WardenAnimatedIllagerModel.LAYER_LOCATION)), 0.5f);
		this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
	}

	@Override
	public ResourceLocation getTextureLocation(ZombieVindicatorEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/entities/zombie_vindicator.png");
	}
}
