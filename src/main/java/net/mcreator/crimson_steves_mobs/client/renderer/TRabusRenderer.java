
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;

import net.mcreator.crimson_steves_mobs.entity.TRabusEntity;
import net.mcreator.crimson_steves_mobs.client.model.ModelCustomHoglinModel;

import com.mojang.blaze3d.vertex.PoseStack;

public class TRabusRenderer extends MobRenderer<TRabusEntity, ModelCustomHoglinModel<TRabusEntity>> {
	public TRabusRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelCustomHoglinModel(context.bakeLayer(ModelCustomHoglinModel.LAYER_LOCATION)), 0.7f);
		this.addLayer(new EyesLayer<TRabusEntity, ModelCustomHoglinModel<TRabusEntity>>(this) {
			@Override
			public RenderType renderType() {
				return RenderType.eyes(new ResourceLocation("crimson_steves_mobs:textures/t-rabus-boss_overlay.png"));
			}
		});
	}

	protected void scale(TRabusEntity p_115983_, PoseStack p_115984_, float p_115985_) {
		this.shadowRadius = 1.8f;
		p_115984_.scale(2, 2, 2);
	}

	@Override
	public ResourceLocation getTextureLocation(TRabusEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/t-rabus-boss-on-planetminecraft-com.png");
	}
}
