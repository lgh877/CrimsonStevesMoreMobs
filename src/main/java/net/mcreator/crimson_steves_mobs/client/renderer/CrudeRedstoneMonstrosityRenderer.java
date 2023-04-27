
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;

import net.mcreator.crimson_steves_mobs.entity.CrudeRedstoneMonstrosityEntity;
import net.mcreator.crimson_steves_mobs.GolemAnimatedWardenRenderer;
import net.mcreator.crimson_steves_mobs.GolemAnimatedWarden;
import net.mcreator.crimson_steves_mobs.CustomWardenEmissiveLayer;

import com.mojang.blaze3d.vertex.PoseStack;

public class CrudeRedstoneMonstrosityRenderer extends GolemAnimatedWardenRenderer<CrudeRedstoneMonstrosityEntity, GolemAnimatedWarden<CrudeRedstoneMonstrosityEntity>> {
	public CrudeRedstoneMonstrosityRenderer(EntityRendererProvider.Context context) {
		super(context, new GolemAnimatedWarden(context.bakeLayer(GolemAnimatedWarden.LAYER_LOCATION)), 1f);
		this.addLayer(new CustomWardenEmissiveLayer<>(this, new ResourceLocation("crimson_steves_mobs:textures/entities/redstone-monstrosity-layer.png"), (p_234805_, p_234806_, p_234807_) -> {
			float a = 1 + ((p_234805_.getMaxHealth() - p_234805_.getHealth()) / p_234805_.getMaxHealth()) * 3;
			return Math.max(0.2F + a * 0.1f, Mth.cos(p_234807_ * 0.1F * a) * Mth.sin(p_234807_ * 0.15F));
		}, GolemAnimatedWarden::getPulsatingSpotsLayerModelParts) {
			public RenderType getRenderType() {
				return RenderType.entityTranslucentEmissive(this.texture);
			}
		});
		this.addLayer(new CustomWardenEmissiveLayer<>(this, new ResourceLocation("crimson_steves_mobs:textures/entities/redstone-monstrosity-eyes.png"), (p_234805_, p_234806_, p_234807_) -> {
			return p_234805_.eyeLightAmount;
		}, GolemAnimatedWarden::getPulsatingSpotsLayerModelParts));
	}

	protected void scale(CrudeRedstoneMonstrosityEntity p_115983_, PoseStack p_115984_, float p_115985_) {
		super.scale(p_115983_, p_115984_, p_115985_);
		float a = p_115983_.getSquashAmount();
		if (a > 0) {
			a = (a - p_115985_) / 10;
			a = a * a * a;
			a = Mth.sin(a);
			p_115984_.scale(1 + a, 1 - a * 0.5f, 1 + a);
		}
	}

	@Override
	public ResourceLocation getTextureLocation(CrudeRedstoneMonstrosityEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/entities/redstone-monstrosity-without-color.png");
	}
}
