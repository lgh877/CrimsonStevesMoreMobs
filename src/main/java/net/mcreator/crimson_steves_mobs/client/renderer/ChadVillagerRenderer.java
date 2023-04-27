
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.crimson_steves_mobs.entity.ChadVillagerEntity;
import net.mcreator.crimson_steves_mobs.client.model.ModelAnimatedIronGolemModel;

import com.mojang.math.Vector3f;
import com.mojang.blaze3d.vertex.PoseStack;

public class ChadVillagerRenderer extends MobRenderer<ChadVillagerEntity, ModelAnimatedIronGolemModel<ChadVillagerEntity>> {
	public ChadVillagerRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelAnimatedIronGolemModel(context.bakeLayer(ModelAnimatedIronGolemModel.LAYER_LOCATION)), 0.7f);
	}

	protected void setupRotations(ChadVillagerEntity p_115014_, PoseStack p_115015_, float p_115016_, float p_115017_, float p_115018_) {
		super.setupRotations(p_115014_, p_115015_, p_115016_, p_115017_, p_115018_);
		if (!((double) p_115014_.animationSpeed < 0.01D)) {
			float f = 13.0F;
			float f1 = p_115014_.animationPosition - p_115014_.animationSpeed * (1.0F - p_115018_) + 6.0F;
			float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
			p_115015_.mulPose(Vector3f.ZP.rotationDegrees(6.5F * f2));
		}
	}

	protected void scale(ChadVillagerEntity p_115983_, PoseStack p_115984_, float p_115985_) {
		this.shadowRadius = 0.5f * p_115983_.getScale();
		p_115984_.scale(p_115983_.getScale(), p_115983_.getScale(), p_115983_.getScale());
	}

	@Override
	public ResourceLocation getTextureLocation(ChadVillagerEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/strong-villager-on-planetminecraft-com.png");
	}
}
