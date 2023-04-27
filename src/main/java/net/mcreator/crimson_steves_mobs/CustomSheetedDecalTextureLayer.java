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

import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.PoseStack;

public class CustomSheetedDecalTextureLayer<T extends LivingEntity, M extends HierarchicalModel<T>> extends RenderLayer<T, M> {
	private final CustomSheetedDecalTextureLayer.AlphaFunction<T> alphaFunction;
	private final CustomSheetedDecalTextureLayer.DrawSelector<T, M> drawSelector;

	public RenderType getRenderType(T entity, float partialTicks, float ageInTicks) {
		return RenderType.entityCutoutNoCull(getResourceLocation(entity, partialTicks, ageInTicks));
	}

	public ResourceLocation getResourceLocation(T entity, float partialTicks, float ageInTicks) {
		return new ResourceLocation("crimson_steves_mobs:textures/creeper_skin.png");
	}

	public CustomSheetedDecalTextureLayer(RenderLayerParent<T, M> parent, CustomSheetedDecalTextureLayer.AlphaFunction<T> p_234887_, CustomSheetedDecalTextureLayer.DrawSelector<T, M> p_234888_) {
		super(parent);
		this.alphaFunction = p_234887_;
		this.drawSelector = p_234888_;
	}

	public boolean shouldBeSeen(T entity, float partialTicks, float ageInTicks) {
		return !entity.isInvisible();
	}

	public float getWhiteOverlayProgress(T entity, float partialTicks, float ageInTicks) {
		return 0;
	}

	@Override
	public void render(PoseStack stack, MultiBufferSource buffer, int light, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (shouldBeSeen(entity, partialTicks, ageInTicks)) {
			this.onlyDrawSelectedParts();
			this.getParentModel().renderToBuffer(stack, new SheetedDecalTextureGenerator(buffer.getBuffer(getRenderType(entity, partialTicks, ageInTicks)), stack.last().pose(), stack.last().normal()), light,
					LivingEntityRenderer.getOverlayCoords(entity, getWhiteOverlayProgress(entity, partialTicks, ageInTicks)), 1.0F, 1.0F, 1.0F, 1.0f);
			this.resetDrawForAllParts();
		}
		/*this.getParentModel().renderToBuffer(stack, new SheetedDecalTextureGenerator(buffer.getBuffer(getRenderType(entity, partialTicks, ageInTicks)), stack.last().pose(), stack.last().normal()), light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F,
				1.0F);
		*/
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
