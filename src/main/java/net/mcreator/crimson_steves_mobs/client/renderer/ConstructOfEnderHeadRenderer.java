package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.crimson_steves_mobs.entity.ConstructOfEnderHeadEntity;
import net.mcreator.crimson_steves_mobs.IronGolemHeadModel;
import net.mcreator.crimson_steves_mobs.CustomWardenEmissiveLayer;

public class ConstructOfEnderHeadRenderer extends MobRenderer<ConstructOfEnderHeadEntity, IronGolemHeadModel<ConstructOfEnderHeadEntity>> {
	public ConstructOfEnderHeadRenderer(EntityRendererProvider.Context context) {
		super(context, new IronGolemHeadModel(context.bakeLayer(IronGolemHeadModel.LAYER_LOCATION)), 1f);
		/*
		this.addLayer(new CustomSheetedDecalTextureLayer<>(this, (p_234805_, p_234806_, p_234807_) -> {
			return 0;
		}, GolemAnimatedWarden::getPulsatingSpotsLayerModelParts));
		*/
		this.addLayer(new CustomWardenEmissiveLayer<>(this, new ResourceLocation("crimson_steves_mobs:textures/entities/ancient_guardian_words_layer.png"), (p_234805_, p_234806_, p_234807_) -> {
			return Math.max(0.0F, Mth.cos(p_234807_ * 0.2F));
		}, IronGolemHeadModel::headLayerModelParts));
	}

	@Override
	public ResourceLocation getTextureLocation(ConstructOfEnderHeadEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/entity/ancient_guardian.png");
	}
}
