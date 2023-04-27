
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.crimson_steves_mobs.entity.ZombieCowEntity;
import net.mcreator.crimson_steves_mobs.client.model.ModelAnimatedWardenCowModel;

import com.mojang.blaze3d.vertex.PoseStack;

public class ZombieCowRenderer extends MobRenderer<ZombieCowEntity, ModelAnimatedWardenCowModel<ZombieCowEntity>> {
	public ZombieCowRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelAnimatedWardenCowModel(context.bakeLayer(ModelAnimatedWardenCowModel.LAYER_LOCATION)), 0.5f);
		this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
	}

	protected void scale(ZombieCowEntity p_115983_, PoseStack p_115984_, float p_115985_) {
		this.shadowRadius = 0.5f * p_115983_.getScale();
		p_115984_.scale(p_115983_.getScale(), p_115983_.getScale(), p_115983_.getScale());
	}

	@Override
	public ResourceLocation getTextureLocation(ZombieCowEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/zombified-cow-on-planetminecraft-com.png");
	}
}
