
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;

import net.mcreator.crimson_steves_mobs.entity.EliteEnderRavagerEntity;
import net.mcreator.crimson_steves_mobs.EnderRavagerModel;

import com.mojang.blaze3d.vertex.PoseStack;

public class EliteEnderRavagerRenderer extends MobRenderer<EliteEnderRavagerEntity, EnderRavagerModel<EliteEnderRavagerEntity>> {
	public EliteEnderRavagerRenderer(EntityRendererProvider.Context context) {
		super(context, new EnderRavagerModel(context.bakeLayer(EnderRavagerModel.LAYER_LOCATION)), 1.1f);
		this.addLayer(new EyesLayer<EliteEnderRavagerEntity, EnderRavagerModel<EliteEnderRavagerEntity>>(this) {
			@Override
			public RenderType renderType() {
				return RenderType.eyes(new ResourceLocation(
						"crimson_steves_mobs:textures/marble-ender-guardian-another-version-of-dinodesmond-s-mob-skin-on-planetminecraft-com-glowing.png"));
			}
		});
	}

	protected void scale(EliteEnderRavagerEntity p_116314_, PoseStack p_116315_, float p_116316_) {
		float f = p_116314_.getScale();
		this.shadowRadius = 1.5f * f;
		p_116315_.scale(f, f, f);
	}

	@Override
	public ResourceLocation getTextureLocation(EliteEnderRavagerEntity entity) {
		return new ResourceLocation(
				"crimson_steves_mobs:textures/marble-ender-guardian-another-version-of-dinodesmond-s-mob-skin-on-planetminecraft-com.png");
	}
}
