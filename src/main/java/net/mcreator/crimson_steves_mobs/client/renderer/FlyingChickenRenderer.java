
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.ChickenModel;

import net.mcreator.crimson_steves_mobs.entity.FlyingChickenEntity;

public class FlyingChickenRenderer extends MobRenderer<FlyingChickenEntity, ChickenModel<FlyingChickenEntity>> {
	public FlyingChickenRenderer(EntityRendererProvider.Context context) {
		super(context, new ChickenModel(context.bakeLayer(ModelLayers.CHICKEN)), 0.3f);
	}

	protected float getBob(FlyingChickenEntity p_114000_, float p_114001_) {
		float f = Mth.lerp(p_114001_, p_114000_.oFlap, p_114000_.flap);
		float f1 = Mth.lerp(p_114001_, p_114000_.oFlapSpeed, p_114000_.flapSpeed);
		return (Mth.sin(f) + 1.0F) * f1;
	}

	@Override
	public ResourceLocation getTextureLocation(FlyingChickenEntity entity) {
		return new ResourceLocation("textures/entity/chicken.png");
	}
}
