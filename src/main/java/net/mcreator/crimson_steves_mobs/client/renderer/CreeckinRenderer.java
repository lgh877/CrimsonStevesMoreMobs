
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.ChickenModel;

import net.mcreator.crimson_steves_mobs.entity.CreeckinEntity;

import com.mojang.blaze3d.vertex.PoseStack;

public class CreeckinRenderer extends MobRenderer<CreeckinEntity, ChickenModel<CreeckinEntity>> {
	public CreeckinRenderer(EntityRendererProvider.Context context) {
		super(context, new ChickenModel(context.bakeLayer(ModelLayers.CHICKEN)), 0.3f);
		this.addLayer(new CreeckinPowerLayer(this, context.getModelSet()));
	}

	protected void scale(CreeckinEntity p_114046_, PoseStack p_114047_, float p_114048_) {
		float f = p_114046_.getSwelling(p_114048_);
		float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
		f = Mth.clamp(f, 0.0F, 1.0F);
		f *= f;
		f *= f;
		float f2 = (1.0F + f * 0.4F) * f1;
		float f3 = (1.0F + f * 0.1F) / f1;
		p_114047_.scale(f2, f3, f2);
	}

	protected float getBob(CreeckinEntity p_114000_, float p_114001_) {
		float f = Mth.lerp(p_114001_, p_114000_.oFlap, p_114000_.flap);
		float f1 = Mth.lerp(p_114001_, p_114000_.oFlapSpeed, p_114000_.flapSpeed);
		return (Mth.sin(f) + 1.0F) * f1;
	}

	protected float getWhiteOverlayProgress(CreeckinEntity p_114043_, float p_114044_) {
		float f = p_114043_.getSwelling(p_114044_);
		return (int) (f * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(CreeckinEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/entities/chicken_creeper.png");
	}

	@OnlyIn(Dist.CLIENT)
	class CreeckinPowerLayer extends EnergySwirlLayer<CreeckinEntity, ChickenModel<CreeckinEntity>> {
		private static final ResourceLocation POWER_LOCATION = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
		private final ChickenModel<CreeckinEntity> model;

		public CreeckinPowerLayer(RenderLayerParent<CreeckinEntity, ChickenModel<CreeckinEntity>> p_174471_, EntityModelSet p_174472_) {
			super(p_174471_);
			this.model = new ChickenModel<>(p_174472_.bakeLayer(ModelLayers.CHICKEN));
		}

		protected float xOffset(float p_116683_) {
			return p_116683_ * 0.01F;
		}

		protected ResourceLocation getTextureLocation() {
			return POWER_LOCATION;
		}

		protected EntityModel<CreeckinEntity> model() {
			return this.model;
		}
	}
}
