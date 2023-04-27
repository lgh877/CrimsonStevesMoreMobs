
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.IllagerModel;

import com.mojang.blaze3d.vertex.PoseStack;

public class PhantomTamerRenderer<T extends SpellcasterIllager> extends IllagerRenderer<T> {
	public PhantomTamerRenderer(EntityRendererProvider.Context context) {
		super(context, new IllagerModel<>(context.bakeLayer(ModelLayers.EVOKER)), 0.5F);
		this.addLayer(new ItemInHandLayer<T, IllagerModel<T>>(this, context.getItemInHandRenderer()) {
			public void render(PoseStack p_114569_, MultiBufferSource p_114570_, int p_114571_, T p_114572_, float p_114573_, float p_114574_,
					float p_114575_, float p_114576_, float p_114577_, float p_114578_) {
				if (p_114572_.isCastingSpell()) {
					super.render(p_114569_, p_114570_, p_114571_, p_114572_, p_114573_, p_114574_, p_114575_, p_114576_, p_114577_, p_114578_);
				}
			}
		});
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/entities/phantom_tamer_illager.png");
	}
}
