
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;

import net.mcreator.crimson_steves_mobs.entity.RedstonePoweredIronGolemEntity;
import net.mcreator.crimson_steves_mobs.CustomWardenEmissiveLayer;
import net.mcreator.crimson_steves_mobs.AnimatedRedstoneGolemRenderer;
import net.mcreator.crimson_steves_mobs.AnimatedRedstoneGolemModel;

public class RedstonePoweredIronGolemRenderer
		extends
			AnimatedRedstoneGolemRenderer<RedstonePoweredIronGolemEntity, AnimatedRedstoneGolemModel<RedstonePoweredIronGolemEntity>> {
	public RedstonePoweredIronGolemRenderer(EntityRendererProvider.Context context) {
		super(context, new AnimatedRedstoneGolemModel(context.bakeLayer(AnimatedRedstoneGolemModel.LAYER_LOCATION)), 1.1f);
		this.addLayer(new EyesLayer<RedstonePoweredIronGolemEntity, AnimatedRedstoneGolemModel<RedstonePoweredIronGolemEntity>>(this) {
			@Override
			public RenderType renderType() {
				return RenderType.eyes(new ResourceLocation("crimson_steves_mobs:textures/redstone_powered_golem_eyes.png"));
			}
		});
		this.addLayer(new CustomWardenEmissiveLayer<>(this,
				new ResourceLocation("crimson_steves_mobs:textures/entities/redstone_powered_iron_golem_block.png"),
				(p_234805_, p_234806_, p_234807_) -> {
					if (p_234805_.isShiftKeyDown())
						return Math.max(0.4F, Mth.cos(p_234807_ * 2));
					return Math.max(0.2F, Mth.cos(p_234807_ * 0.1f));
				}, AnimatedRedstoneGolemModel::getBlockLayerModelParts));
	}

	@Override
	public ResourceLocation getTextureLocation(RedstonePoweredIronGolemEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/redstone_powered_iron_golem.png");
	}
}
