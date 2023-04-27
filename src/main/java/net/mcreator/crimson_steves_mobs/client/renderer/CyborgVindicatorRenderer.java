
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.mcreator.crimson_steves_mobs.entity.CyborgVindicatorEntity;
import net.mcreator.crimson_steves_mobs.ChadVindicatorModel;
import net.mcreator.crimson_steves_mobs.AnimatedRedstoneGolemRenderer;

import com.mojang.blaze3d.vertex.PoseStack;

public class CyborgVindicatorRenderer extends AnimatedRedstoneGolemRenderer<CyborgVindicatorEntity, ChadVindicatorModel<CyborgVindicatorEntity>> {
	public CyborgVindicatorRenderer(EntityRendererProvider.Context context) {
		super(context, new ChadVindicatorModel(context.bakeLayer(ChadVindicatorModel.LAYER_LOCATION)), 1.1f);
		this.addLayer(new EyesLayer<CyborgVindicatorEntity, ChadVindicatorModel<CyborgVindicatorEntity>>(this) {
			public void render(PoseStack p_116983_, MultiBufferSource p_116984_, int p_116985_, CyborgVindicatorEntity p_116986_, float p_116987_,
					float p_116988_, float p_116989_, float p_116990_, float p_116991_, float p_116992_) {
				if (p_116986_.isShiftKeyDown() || p_116986_.getArmPose() == AbstractIllager.IllagerArmPose.ATTACKING) {
					super.render(p_116983_, p_116984_, p_116985_, p_116986_, p_116987_, p_116988_, p_116989_, p_116990_, p_116991_, p_116992_);
				}
			}

			@Override
			public RenderType renderType() {
				return RenderType.eyes(new ResourceLocation("crimson_steves_mobs:textures/cyborg_vindicator_on.png"));
			}
		});
		this.addLayer(new EyesLayer<CyborgVindicatorEntity, ChadVindicatorModel<CyborgVindicatorEntity>>(this) {
			@Override
			public RenderType renderType() {
				return RenderType.eyes(new ResourceLocation("crimson_steves_mobs:textures/entities/cyborg_vindicator_eye_layer.png"));
			}
		});
	}

	public void render(CyborgVindicatorEntity p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_,
			int p_115460_) {
		super.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
	}

	@Override
	public ResourceLocation getTextureLocation(CyborgVindicatorEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/cyborg_vindicator.png");
	}
}
