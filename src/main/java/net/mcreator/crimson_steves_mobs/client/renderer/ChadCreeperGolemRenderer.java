package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.mcreator.crimson_steves_mobs.entity.ChadCreeperGolemEntity;
import net.mcreator.crimson_steves_mobs.CustomSheetedDecalTextureLayer;
import net.mcreator.crimson_steves_mobs.AnimatedRedstoneGolemRenderer;
import net.mcreator.crimson_steves_mobs.AnimatedRedstoneGolemModel;

import com.mojang.math.Vector3f;
import com.mojang.math.Matrix4f;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class ChadCreeperGolemRenderer extends AnimatedRedstoneGolemRenderer<ChadCreeperGolemEntity, AnimatedRedstoneGolemModel<ChadCreeperGolemEntity>> {
	public ChadCreeperGolemRenderer(EntityRendererProvider.Context context) {
		super(context, new AnimatedRedstoneGolemModel(context.bakeLayer(AnimatedRedstoneGolemModel.LAYER_LOCATION)), 1.1f);
		this.addLayer(new CustomSheetedDecalTextureLayer<>(this, (p_234805_, p_234806_, p_234807_) -> {
			return 0;
		}, AnimatedRedstoneGolemModel::getBodyLayerModelParts) {
			public ResourceLocation getResourceLocation(ChadCreeperGolemEntity entity, float partialTicks, float ageInTicks) {
				return new ResourceLocation("crimson_steves_mobs:textures/creeper_skin.png");
			}

			public float getWhiteOverlayProgress(ChadCreeperGolemEntity entity, float partialTicks, float ageInTicks) {
				return entity.isAlive() ? 0 : Mth.clamp((float) entity.getSwell() / 60f, 0, 1);
			}
		});
		this.addLayer(new EyesLayer<ChadCreeperGolemEntity, AnimatedRedstoneGolemModel<ChadCreeperGolemEntity>>(this) {
			@Override
			public RenderType renderType() {
				return RenderType.entityTranslucent(new ResourceLocation("crimson_steves_mobs:textures/entities/chad_creeper_golem_layer.png"));
			}

			public float getWhiteOverlayProgress(ChadCreeperGolemEntity entity) {
				return entity.isAlive() ? 0 : Mth.clamp((float) entity.getSwell() / 60f, 0, 1);
			}

			public void render(PoseStack p_116983_, MultiBufferSource p_116984_, int p_116985_, ChadCreeperGolemEntity p_116986_, float p_116987_, float p_116988_, float p_116989_, float p_116990_, float p_116991_, float p_116992_) {
				if (!p_116986_.isInvisible()) {
					VertexConsumer vertexconsumer = p_116984_.getBuffer(this.renderType());
					this.getParentModel().renderToBuffer(p_116983_, vertexconsumer, p_116985_, LivingEntityRenderer.getOverlayCoords(p_116986_, getWhiteOverlayProgress(p_116986_)), 1.0F, 1.0F, 1.0F, 1.0F);
				}
			}
		});
		this.addLayer(new EyesLayer<ChadCreeperGolemEntity, AnimatedRedstoneGolemModel<ChadCreeperGolemEntity>>(this) {
			//OverlayTexture.pack(OverlayTexture.u(p_115340_), OverlayTexture.v(p_115339_.hurtTime > 0 || p_115339_.deathTime > 0))
			@Override
			public RenderType renderType() {
				return RenderType.entityCutoutNoCull(new ResourceLocation("crimson_steves_mobs:textures/entities/chad_creeper_golem_tnt_layer.png"));
			}

			public void render(PoseStack p_116983_, MultiBufferSource p_116984_, int p_116985_, ChadCreeperGolemEntity p_116986_, float p_116987_, float p_116988_, float p_116989_, float p_116990_, float p_116991_, float p_116992_) {
				if (!p_116986_.isInvisible()) {
					VertexConsumer vertexconsumer = p_116984_.getBuffer(this.renderType());
					float whiteOverlayValue = 0;
					p_116983_.pushPose();
					if (p_116986_.getHealth() < p_116986_.getMaxHealth() * 0.5f) {
						whiteOverlayValue = 0.5f - p_116986_.getHealth() / p_116986_.getMaxHealth();
						p_116983_.translate((Math.random() - 0.5) * whiteOverlayValue * 0.2, (Math.random() - 0.5) * whiteOverlayValue * 0.2, (Math.random() - 0.5) * whiteOverlayValue * 0.2);
						p_116983_.scale(1 + (float) (Math.random() * 0.5 * whiteOverlayValue), 1 + (float) (Math.random() * 0.5 * whiteOverlayValue), 1 + (float) (Math.random() * 0.5 * whiteOverlayValue));
						if (p_116986_.isDeadOrDying()) {
							float f5 = ((float) p_116986_.getSwell() + p_116987_) / 80.0F;
							float f7 = Math.min(f5 > 0.8F ? (f5 - 0.8F) / 0.2F : 0.0F, 1.0F);
							RandomSource randomsource = RandomSource.create(432L);
							VertexConsumer vertexconsumer2 = p_116984_.getBuffer(RenderType.lightning()); //any rendertype
							p_116983_.pushPose();
							p_116983_.translate(0.0D, 0.0D, 0.0D);
							p_116983_.scale(0.2F, 0.2F, 0.2F); //scale thing
							// replace 60 for more beams
							for (int i = 0; (float) i < (f5 + f5 * f5) / 2.0F * 60.0F; ++i) {
								p_116983_.mulPose(Vector3f.XP.rotationDegrees(randomsource.nextFloat() * 360.0F));
								p_116983_.mulPose(Vector3f.YP.rotationDegrees(randomsource.nextFloat() * 360.0F));
								p_116983_.mulPose(Vector3f.ZP.rotationDegrees(randomsource.nextFloat() * 360.0F));
								p_116983_.mulPose(Vector3f.XP.rotationDegrees(randomsource.nextFloat() * 360.0F));
								p_116983_.mulPose(Vector3f.YP.rotationDegrees(randomsource.nextFloat() * 360.0F));
								p_116983_.mulPose(Vector3f.ZP.rotationDegrees(randomsource.nextFloat() * 360.0F + f5 * 90.0F));
								float f3 = randomsource.nextFloat() * 20.0F + 5.0F + f7 * 10.0F;
								float f4 = randomsource.nextFloat() * 2.0F + 1.0F + f7 * 2.0F;
								Matrix4f matrix4f = p_116983_.last().pose();
								int j = (int) (255.0F * (1.0F - f7));
								vertex01(vertexconsumer2, matrix4f, j);
								vertex2(vertexconsumer2, matrix4f, f3, f4 * p_116986_.getSwell() * 0.1f);
								vertex3(vertexconsumer2, matrix4f, f3, f4 * p_116986_.getSwell() * 0.1f);
								vertex01(vertexconsumer2, matrix4f, j);
								vertex3(vertexconsumer2, matrix4f, f3, f4 * p_116986_.getSwell() * 0.1f);
								vertex4(vertexconsumer2, matrix4f, f3, f4 * p_116986_.getSwell() * 0.1f);
								vertex01(vertexconsumer2, matrix4f, j);
								vertex4(vertexconsumer2, matrix4f, f3, f4 * p_116986_.getSwell() * 0.1f);
								vertex2(vertexconsumer2, matrix4f, f3, f4 * p_116986_.getSwell() * 0.1f);
							}
							p_116983_.popPose();
						}
					}
					this.getParentModel().renderToBuffer(p_116983_, vertexconsumer, p_116985_, OverlayTexture.pack(OverlayTexture.u(whiteOverlayValue), OverlayTexture.v(false)), 1.0F, 1.0F, 1.0F, 1.0F);
					p_116983_.popPose();
				}
			}

			private static void vertex01(VertexConsumer p_114220_, Matrix4f p_114221_, int p_114222_) {
				p_114220_.vertex(p_114221_, 0.0F, 0.0F, 0.0F).color(255, 255, 255, p_114222_).endVertex();
			}

			private static void vertex2(VertexConsumer p_114215_, Matrix4f p_114216_, float p_114217_, float p_114218_) {
				p_114215_.vertex(p_114216_, -3 * p_114218_, p_114217_, -0.5F * p_114218_).color(0, 255, 255, 0).endVertex();
			}

			private static void vertex3(VertexConsumer p_114224_, Matrix4f p_114225_, float p_114226_, float p_114227_) {
				p_114224_.vertex(p_114225_, 3 * p_114227_, p_114226_, -0.5F * p_114227_).color(0, 255, 255, 0).endVertex();
			}

			private static void vertex4(VertexConsumer p_114229_, Matrix4f p_114230_, float p_114231_, float p_114232_) {
				p_114229_.vertex(p_114230_, 0.0F, p_114231_, 1.0F * p_114232_).color(0, 255, 255, 0).endVertex();
			}
		});
		/*
		this.addLayer(new CustomWardenEmissiveLayer<>(this, new ResourceLocation("crimson_steves_mobs:textures/entities/redstone_powered_iron_golem_block.png"), (p_234805_, p_234806_, p_234807_) -> {
			if (p_234805_.isShiftKeyDown())
				return Math.max(0.4F, Mth.cos(p_234807_ * 2));
			return Math.max(0.2F, Mth.cos(p_234807_ * 0.1f));
		}, AnimatedRedstoneGolemModel::getBlockLayerModelParts));
		*/
	}

	/*
	protected float getWhiteOverlayProgress(ChadCreeperGolemEntity p_114043_, float p_114044_) {
		return Mth.clamp((float) p_114043_.getSwell() / 60, 0, 1);
	}
	*/
	protected void scale(ChadCreeperGolemEntity p_114046_, PoseStack p_114047_, float p_114048_) {
		super.scale(p_114046_, p_114047_, p_114048_);
		if (p_114046_.getHealth() < p_114046_.getMaxHealth() * 0.5f) {
			float whiteOverlayValue = 0.5f - p_114046_.getHealth() / p_114046_.getMaxHealth();
			p_114047_.scale(1 + (float) (Math.random() * 0.5 * whiteOverlayValue), 1 + (float) (Math.random() * 0.5 * whiteOverlayValue), 1 + (float) (Math.random() * 0.5 * whiteOverlayValue));
		}
	}

	@Override
	public ResourceLocation getTextureLocation(ChadCreeperGolemEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/entities/nothing.png");
	}
}
