
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;

import net.mcreator.crimson_steves_mobs.entity.AnimatedTempleEntity;
import net.mcreator.crimson_steves_mobs.PreAnimatedRedstoneMonstrosityModel;

public class AnimatedTempleRenderer extends MobRenderer<AnimatedTempleEntity, PreAnimatedRedstoneMonstrosityModel<AnimatedTempleEntity>> {
	public AnimatedTempleRenderer(EntityRendererProvider.Context context) {
		super(context, new PreAnimatedRedstoneMonstrosityModel(context.bakeLayer(PreAnimatedRedstoneMonstrosityModel.LAYER_LOCATION)), 3f);
		this.addLayer(new EyesLayer<AnimatedTempleEntity, PreAnimatedRedstoneMonstrosityModel<AnimatedTempleEntity>>(this) {
			@Override
			public RenderType renderType() {
				return RenderType.eyes(new ResourceLocation("crimson_steves_mobs:textures/entities/stone_monstrosity_eyes.png"));
			}
		});
	}

	@Override
	public ResourceLocation getTextureLocation(AnimatedTempleEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/entities/stone_monstrosity.png");
	}
}
