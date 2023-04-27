/**
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside net.mcreator.workspace as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
*/
package net.mcreator.crimson_steves_mobs;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.model.EntityModel;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

@OnlyIn(Dist.CLIENT)
public class PavagerChargedLayer<T extends Entity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	public PavagerChargedLayer(RenderLayerParent<T, M> p_116967_) {
		super(p_116967_);
	}

	public boolean isCharged(T mob) {
		return true;
	}

	public ResourceLocation getResourceLocation(T mob) {
		return new ResourceLocation("textures/entity/creeper/creeper_armor.png");
	}

	public void render(PoseStack poseStackIn, MultiBufferSource bufferIn, int p_116972_, T entity, float p_116974_, float p_116975_, float p_116976_, float p_116977_, float p_116978_, float p_116979_) {
		if (isCharged(entity)) {
			poseStackIn.pushPose();
			poseStackIn.scale(1.1f, 1.1f, 1.1f);
			float f = (float) entity.tickCount + p_116976_;
			VertexConsumer vertexconsumer = bufferIn.getBuffer(RenderType.energySwirl(getResourceLocation(entity), f * 0.01F % 1.0F, f * 0.01F % 1.0F));
			this.getParentModel().renderToBuffer(poseStackIn, vertexconsumer, p_116972_, OverlayTexture.NO_OVERLAY, 1F, 1f, 1F, 1F);
			poseStackIn.popPose();
		}
	}
}
