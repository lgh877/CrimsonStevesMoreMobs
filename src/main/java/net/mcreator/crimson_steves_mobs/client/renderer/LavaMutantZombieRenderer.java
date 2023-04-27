package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;

import net.mcreator.crimson_steves_mobs.entity.LavaMutantZombieEntity;
import net.mcreator.crimson_steves_mobs.MutantZombieModel;
import net.mcreator.crimson_steves_mobs.CustomSheetedDecalTextureLayer;

public class LavaMutantZombieRenderer extends MobRenderer<LavaMutantZombieEntity, MutantZombieModel<LavaMutantZombieEntity>> {
	public LavaMutantZombieRenderer(EntityRendererProvider.Context context) {
		super(context, new MutantZombieModel(context.bakeLayer(MutantZombieModel.LAYER_LOCATION)), 1.5f);
		this.addLayer(new CustomSheetedDecalTextureLayer<>(this, (p_234805_, p_234806_, p_234807_) -> {
			return 0;
		}, MutantZombieModel::getBodyLayerModelParts) {
			public ResourceLocation getResourceLocation(LavaMutantZombieEntity entity, float partialTicks, float ageInTicks) {
				int i = (int) ((ageInTicks / 3) % 40) < 20 ? (int) ((ageInTicks / 3) % 40) : 39 - (int) ((ageInTicks / 3) % 40);
				return new ResourceLocation("crimson_steves_mobs:textures/lava_still" + i + ".png");
			}
		});
		this.addLayer(new CustomSheetedDecalTextureLayer<>(this, (p_234805_, p_234806_, p_234807_) -> {
			return 0;
		}, MutantZombieModel::getBodyLayerModelParts) {
			public RenderType getRenderType(LavaMutantZombieEntity entity, float partialTicks, float ageInTicks) {
				return RenderType.entityTranslucentEmissive(getResourceLocation(entity, partialTicks, ageInTicks), true);
			}

			public ResourceLocation getResourceLocation(LavaMutantZombieEntity entity, float partialTicks, float ageInTicks) {
				int i = (int) ((ageInTicks / 3) % 40) < 20 ? (int) ((ageInTicks / 3) % 40) : 39 - (int) ((ageInTicks / 3) % 40);
				return new ResourceLocation("crimson_steves_mobs:textures/lava_still" + i + ".png");
			}
		});
		this.addLayer(new EyesLayer<LavaMutantZombieEntity, MutantZombieModel<LavaMutantZombieEntity>>(this) {
			@Override
			public RenderType renderType() {
				return RenderType.entityTranslucent(new ResourceLocation("crimson_steves_mobs:textures/entities/lava_mutant_zombie_layer.png"));
			}
		});
	}

	@Override
	public ResourceLocation getTextureLocation(LavaMutantZombieEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/entities/nothing.png");
	}
}
