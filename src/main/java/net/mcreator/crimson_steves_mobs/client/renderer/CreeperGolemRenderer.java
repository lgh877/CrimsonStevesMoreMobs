
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.EntityModel;

import net.mcreator.crimson_steves_mobs.entity.CreeperGolemEntity;
import net.mcreator.crimson_steves_mobs.client.model.ModelAnimatedIronGolemModel;

import com.mojang.math.Vector3f;
import com.mojang.blaze3d.vertex.PoseStack;

public class CreeperGolemRenderer extends MobRenderer<CreeperGolemEntity, ModelAnimatedIronGolemModel<CreeperGolemEntity>> {
	public CreeperGolemRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelAnimatedIronGolemModel(context.bakeLayer(ModelAnimatedIronGolemModel.LAYER_LOCATION)), 0.9f);
		this.addLayer(new CreeperGolemPowerLayer(this, context.getModelSet()));
	}

	protected void scale(CreeperGolemEntity p_114046_, PoseStack p_114047_, float p_114048_) {
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

	protected float getWhiteOverlayProgress(CreeperGolemEntity p_114043_, float p_114044_) {
		float f = p_114043_.getSwelling(p_114044_);
		return (int) (f * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
	}

	protected void setupRotations(CreeperGolemEntity p_115014_, PoseStack p_115015_, float p_115016_, float p_115017_, float p_115018_) {
		super.setupRotations(p_115014_, p_115015_, p_115016_, p_115017_, p_115018_);
		if (!((double) p_115014_.animationSpeed < 0.01D)) {
			float f = 13.0F;
			float f1 = p_115014_.animationPosition - p_115014_.animationSpeed * (1.0F - p_115018_) + 6.0F;
			float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
			p_115015_.mulPose(Vector3f.ZP.rotationDegrees(6.5F * f2));
		}
	}

	@Override
	public ResourceLocation getTextureLocation(CreeperGolemEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/entities/creeper_golem.png");
	}

	@OnlyIn(Dist.CLIENT)
	class CreeperGolemPowerLayer extends EnergySwirlLayer<CreeperGolemEntity, ModelAnimatedIronGolemModel<CreeperGolemEntity>> {
		private static final ResourceLocation POWER_LOCATION = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
		private final ModelAnimatedIronGolemModel<CreeperGolemEntity> model;

		public CreeperGolemPowerLayer(RenderLayerParent<CreeperGolemEntity, ModelAnimatedIronGolemModel<CreeperGolemEntity>> p_174471_,
				EntityModelSet p_174472_) {
			super(p_174471_);
			this.model = new ModelAnimatedIronGolemModel<>(p_174472_.bakeLayer(ModelAnimatedIronGolemModel.LAYER_LOCATION));
		}

		public void render(PoseStack p_116970_, MultiBufferSource p_116971_, int p_116972_, CreeperGolemEntity p_116973_, float p_116974_,
				float p_116975_, float p_116976_, float p_116977_, float p_116978_, float p_116979_) {
			p_116970_.pushPose();
			p_116970_.scale(1.1f, 1.1f, 1.1f);
			super.render(p_116970_, p_116971_, p_116972_, p_116973_, p_116974_, p_116975_, p_116976_, p_116977_, p_116978_, p_116979_);
			p_116970_.popPose();
		}

		protected float xOffset(float p_116683_) {
			return p_116683_ * 0.01F;
		}

		protected ResourceLocation getTextureLocation() {
			return POWER_LOCATION;
		}

		protected EntityModel<CreeperGolemEntity> model() {
			return this.model;
		}
	}
}
