
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.crimson_steves_mobs.entity.ZombiePillagerEntity;
import net.mcreator.crimson_steves_mobs.WardenAnimatedIllagerModel;

public class ZombiePillagerRenderer extends MobRenderer<ZombiePillagerEntity, WardenAnimatedIllagerModel<ZombiePillagerEntity>> {
	public ZombiePillagerRenderer(EntityRendererProvider.Context context) {
		super(context, new WardenAnimatedIllagerModel(context.bakeLayer(WardenAnimatedIllagerModel.LAYER_LOCATION)), 0.5f);
		this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
	}

	@Override
	public ResourceLocation getTextureLocation(ZombiePillagerEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/entities/zombie_pillager.png");
	}
}
