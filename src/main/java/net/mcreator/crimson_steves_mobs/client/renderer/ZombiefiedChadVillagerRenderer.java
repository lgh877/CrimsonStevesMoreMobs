
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.crimson_steves_mobs.entity.ZombiefiedChadVillagerEntity;
import net.mcreator.crimson_steves_mobs.client.model.ModelAnimatedWardenIronGolemModel;

import com.mojang.blaze3d.vertex.PoseStack;

public class ZombiefiedChadVillagerRenderer
		extends
			MobRenderer<ZombiefiedChadVillagerEntity, ModelAnimatedWardenIronGolemModel<ZombiefiedChadVillagerEntity>> {
	public ZombiefiedChadVillagerRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelAnimatedWardenIronGolemModel(context.bakeLayer(ModelAnimatedWardenIronGolemModel.LAYER_LOCATION)), 0.7f);
	}

	protected void scale(ZombiefiedChadVillagerEntity p_115983_, PoseStack p_115984_, float p_115985_) {
		this.shadowRadius = 0.5f * p_115983_.getScale();
		p_115984_.scale(p_115983_.getScale(), p_115983_.getScale(), p_115983_.getScale());
	}

	@Override
	public ResourceLocation getTextureLocation(ZombiefiedChadVillagerEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/trollager-on-planetminecraft-com.png");
	}
}
