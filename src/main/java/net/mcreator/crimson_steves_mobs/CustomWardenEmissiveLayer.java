/**
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside net.mcreator.crimson_steves_mobs as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
*/
package net.mcreator.crimson_steves_mobs;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.HierarchicalModel;

import java.util.List;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

@OnlyIn(Dist.CLIENT)
public class CustomWardenEmissiveLayer<T extends LivingEntity, M extends HierarchicalModel<T>> extends RenderLayer<T, M> {
	public final ResourceLocation texture;
	private final CustomWardenEmissiveLayer.AlphaFunction<T> alphaFunction;
	private final CustomWardenEmissiveLayer.DrawSelector<T, M> drawSelector;

	public CustomWardenEmissiveLayer(RenderLayerParent<T, M> p_234885_, ResourceLocation p_234886_, CustomWardenEmissiveLayer.AlphaFunction<T> p_234887_, CustomWardenEmissiveLayer.DrawSelector<T, M> p_234888_) {
		super(p_234885_);
		this.texture = p_234886_;
		this.alphaFunction = p_234887_;
		this.drawSelector = p_234888_;
	}

	public void render(PoseStack p_234902_, MultiBufferSource p_234903_, int p_234904_, T p_234905_, float p_234906_, float p_234907_, float p_234908_, float p_234909_, float p_234910_, float p_234911_) {
		if (!p_234905_.isInvisible()) {
			this.onlyDrawSelectedParts();
			VertexConsumer vertexconsumer = p_234903_.getBuffer(getRenderType());
			this.getParentModel().renderToBuffer(p_234902_, vertexconsumer, p_234904_, LivingEntityRenderer.getOverlayCoords(p_234905_, 0.0F), 1.0F, 1.0F, 1.0F, this.alphaFunction.apply(p_234905_, p_234908_, p_234909_));
			this.resetDrawForAllParts();
		}
	}

	public RenderType getRenderType() {
		return RenderType.entityTranslucentEmissive(this.texture);
	}

	private void onlyDrawSelectedParts() {
		List<ModelPart> list = this.drawSelector.getPartsToDraw(this.getParentModel());
		this.getParentModel().root().getAllParts().forEach((p_234918_) -> {
			p_234918_.skipDraw = true;
		});
		list.forEach((p_234916_) -> {
			p_234916_.skipDraw = false;
		});
	}

	private void resetDrawForAllParts() {
		this.getParentModel().root().getAllParts().forEach((p_234913_) -> {
			p_234913_.skipDraw = false;
		});
	}

	@OnlyIn(Dist.CLIENT)
	public interface AlphaFunction<T extends LivingEntity> {
		float apply(T p_234920_, float p_234921_, float p_234922_);
	}

	@OnlyIn(Dist.CLIENT)
	public interface DrawSelector<T extends LivingEntity, M extends HierarchicalModel<T>> {
		List<ModelPart> getPartsToDraw(M p_234924_);
	}
}
