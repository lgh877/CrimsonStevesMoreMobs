
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.EntityModel;

import net.mcreator.crimson_steves_mobs.entity.CreesteveEntity;

import com.mojang.blaze3d.vertex.PoseStack;

public class CreesteveRenderer extends HumanoidMobRenderer<CreesteveEntity, HumanoidModel<CreesteveEntity>> {
	public CreesteveRenderer(EntityRendererProvider.Context context) {
		super(context, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
				new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR))));
		this.addLayer(new CreeckinPowerLayer(this, context.getModelSet()));
	}

	protected void scale(CreesteveEntity p_114046_, PoseStack p_114047_, float p_114048_) {
		float f = p_114046_.getSwelling(p_114048_);
		float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
		f = Mth.clamp(f, 0.0F, 1.0F);
		f *= f;
		f *= f;
		float f4 = Mth.sin(f * (float) Math.PI * 5);
		float f2 = (1.0F - f4 * 0.6F) * f1;
		float f3 = (1.0F + f4 * 0.2F) / f1;
		p_114047_.scale(f2, f3, f2);
	}

	protected float getWhiteOverlayProgress(CreesteveEntity p_114043_, float p_114044_) {
		float f = p_114043_.getSwelling(p_114044_);
		return (int) (f * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(CreesteveEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/entities/creesteve.png");
	}

	@OnlyIn(Dist.CLIENT)
	class CreeckinPowerLayer extends EnergySwirlLayer<CreesteveEntity, HumanoidModel<CreesteveEntity>> {
		private static final ResourceLocation POWER_LOCATION = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
		private final HumanoidModel<CreesteveEntity> model;

		public CreeckinPowerLayer(RenderLayerParent<CreesteveEntity, HumanoidModel<CreesteveEntity>> p_174471_, EntityModelSet p_174472_) {
			super(p_174471_);
			this.model = new HumanoidModel<>(p_174472_.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR));
		}

		protected float xOffset(float p_116683_) {
			return p_116683_ * 0.01F;
		}

		protected ResourceLocation getTextureLocation() {
			return POWER_LOCATION;
		}

		protected EntityModel<CreesteveEntity> model() {
			return this.model;
		}
	}
}
