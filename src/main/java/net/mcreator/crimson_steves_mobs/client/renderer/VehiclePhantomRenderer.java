
package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.PhantomEyesLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.PhantomModel;

import net.mcreator.crimson_steves_mobs.entity.VehiclePhantomEntity;

import com.mojang.math.Vector3f;
import com.mojang.blaze3d.vertex.PoseStack;

public class VehiclePhantomRenderer extends MobRenderer<VehiclePhantomEntity, PhantomModel<VehiclePhantomEntity>> {
	public VehiclePhantomRenderer(EntityRendererProvider.Context context) {
		super(context, new PhantomModel(context.bakeLayer(ModelLayers.PHANTOM)), 0.75f);
		this.addLayer(new PhantomEyesLayer<>(this));
	}

	protected void scale(VehiclePhantomEntity p_115681_, PoseStack p_115682_, float p_115683_) {
		int i = p_115681_.getPhantomSize();
		float f = 1.0F + 0.15F * (float) i;
		p_115682_.scale(f, f, f);
		this.shadowRadius = 0.75f * Mth.sqrt(f);
		p_115682_.translate(0.0D, 1.3125D, 0.1875D);
	}

	protected void setupRotations(VehiclePhantomEntity p_115685_, PoseStack p_115686_, float p_115687_, float p_115688_, float p_115689_) {
		super.setupRotations(p_115685_, p_115686_, p_115687_, p_115688_, p_115689_);
		p_115686_.mulPose(Vector3f.XP.rotationDegrees(p_115685_.getXRot()));
	}

	@Override
	public ResourceLocation getTextureLocation(VehiclePhantomEntity entity) {
		return new ResourceLocation("crimson_steves_mobs:textures/entities/phantom.png");
	}
}
