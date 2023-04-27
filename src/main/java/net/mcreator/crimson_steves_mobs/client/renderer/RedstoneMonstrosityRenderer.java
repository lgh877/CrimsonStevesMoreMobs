
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.mcreator.crimson_steves_mobs.entity.RedstoneMonstrosityEntity;
import net.mcreator.crimson_steves_mobs.PreAnimatedRedstoneMonstrosityModel;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class RedstoneMonstrosityRenderer
		extends
			MobRenderer<RedstoneMonstrosityEntity, PreAnimatedRedstoneMonstrosityModel<RedstoneMonstrosityEntity>> {
	public RedstoneMonstrosityRenderer(EntityRendererProvider.Context context) {
		super(context, new PreAnimatedRedstoneMonstrosityModel(context.bakeLayer(PreAnimatedRedstoneMonstrosityModel.LAYER_LOCATION)), 3f);
		this.addLayer(new EyesLayer<RedstoneMonstrosityEntity, PreAnimatedRedstoneMonstrosityModel<RedstoneMonstrosityEntity>>(this) {
			float lightUp = 0;

			public void render(PoseStack p_116983_, MultiBufferSource p_116984_, int p_116985_, RedstoneMonstrosityEntity p_116986_, float p_116987_,
					float p_116988_, float p_116989_, float p_116990_, float p_116991_, float p_116992_) {
				if ("jeb_".equals(p_116986_.getName().getString())) {
					p_116989_ = (Mth.sin((p_116986_.tickCount + p_116988_) / 2) + 1) / 2;
					p_116990_ = (Mth.sin((p_116986_.tickCount + p_116988_) / 3) + 1) / 2;
					p_116991_ = (Mth.sin((p_116986_.tickCount + p_116988_) / 2.5f) + 1) / 2;
				} else if (p_116986_.getHealth() < p_116986_.getMaxHealth() * 0.25f) {
					p_116989_ = 1;
					p_116990_ = (Mth.sin(p_116986_.tickCount + p_116988_) + 1) * p_116986_.getLightUpAmount(1) / 3;
					p_116991_ = 0;
				} else if (p_116986_.getHealth() < p_116986_.getMaxHealth() * 0.5f) {
					p_116989_ = p_116986_.getLightUpAmount(0);
					p_116990_ = (Mth.sin((p_116986_.tickCount + p_116988_) * 0.2f) + 1) * p_116986_.getLightUpAmount(1) / 4;
					p_116991_ = 0;
				} else {
					p_116989_ = p_116986_.getLightUpAmount(0);
					p_116990_ = 0;
					p_116991_ = 0;
				}
				if (!p_116986_.isAlive()) {
					p_116989_ *= Mth.clamp((float) (20 - p_116986_.customDeathTime) / 20, 0, 1);
					p_116990_ *= Mth.clamp((float) (20 - p_116986_.customDeathTime) / 20, 0, 1);
				}
				//if (p_116986_.deathTime < 10 || "jeb_".equals(p_116986_.getName().getString())) {
				VertexConsumer vertexconsumer = p_116984_.getBuffer(this.renderType());
				this.getParentModel().renderToBuffer(p_116983_, vertexconsumer, 15728640, OverlayTexture.NO_OVERLAY, p_116989_, p_116990_, p_116991_,
						p_116992_);
				//}
			}

			@Override
			public RenderType renderType() {
				return RenderType.eyes(new ResourceLocation("crimson_steves_mobs:textures/entities/redstone_monstrosity_redstone_layer.png"));
			}
		});
		this.addLayer(new EyesLayer<RedstoneMonstrosityEntity, PreAnimatedRedstoneMonstrosityModel<RedstoneMonstrosityEntity>>(this) {
			public void render(PoseStack p_116983_, MultiBufferSource p_116984_, int p_116985_, RedstoneMonstrosityEntity p_116986_, float p_116987_,
					float p_116988_, float p_116989_, float p_116990_, float p_116991_, float p_116992_) {
				if (p_116986_.isAlive())
					super.render(p_116983_, p_116984_, p_116985_, p_116986_, p_116987_, p_116988_, p_116989_, p_116990_, p_116991_, p_116992_);
			}

			@Override
			public RenderType renderType() {
				return RenderType.eyes(new ResourceLocation("crimson_steves_mobs:textures/entities/redstone_monstrosity_eye_layer.png"));
			}
		});
	}

	@Override
	public ResourceLocation getTextureLocation(RedstoneMonstrosityEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/entities/redstone_monstrosity_inactive.png");
	}
}
