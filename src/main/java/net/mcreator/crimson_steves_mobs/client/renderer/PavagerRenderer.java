
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.crimson_steves_mobs.entity.PavagerEntity;
import net.mcreator.crimson_steves_mobs.PavagerModel;
import net.mcreator.crimson_steves_mobs.PavagerChargedLayer;

import com.mojang.blaze3d.vertex.PoseStack;

public class PavagerRenderer extends MobRenderer<PavagerEntity, PavagerModel<PavagerEntity>> {
	public PavagerRenderer(EntityRendererProvider.Context context) {
		super(context, new PavagerModel(context.bakeLayer(PavagerModel.LAYER_LOCATION)), 1.1f);
		this.addLayer(new PavagerChargedLayer<>(this) {
			public boolean isCharged(PavagerEntity mob) {
				return mob.isPowered();
			}
		});
	}

	protected void scale(PavagerEntity p_116314_, PoseStack p_116315_, float p_116316_) {
		this.shadowRadius = 1.1f * p_116314_.getScale();
		p_116315_.scale(p_116314_.getScale(), p_116314_.getScale(), p_116314_.getScale());
	}

	@Override
	public ResourceLocation getTextureLocation(PavagerEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/pavager-on-planetminecraft-com.png");
	}
}
