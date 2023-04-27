
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.crimson_steves_mobs.entity.ZombieEvokerEntity;
import net.mcreator.crimson_steves_mobs.WardenAnimatedIllagerModel;

public class ZombieEvokerRenderer extends MobRenderer<ZombieEvokerEntity, WardenAnimatedIllagerModel<ZombieEvokerEntity>> {
	public ZombieEvokerRenderer(EntityRendererProvider.Context context) {
		super(context, new WardenAnimatedIllagerModel(context.bakeLayer(WardenAnimatedIllagerModel.LAYER_LOCATION)), 0.5f);
		this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
	}

	@Override
	public ResourceLocation getTextureLocation(ZombieEvokerEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/entities/zombie_evoker.png");
	}
}
