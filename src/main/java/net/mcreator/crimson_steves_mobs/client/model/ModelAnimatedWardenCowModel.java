package net.mcreator.crimson_steves_mobs.client.model;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.ArmedModel;

import com.mojang.blaze3d.vertex.PoseStack;

public class ModelAnimatedWardenCowModel<T extends PathfinderMob> extends HierarchicalModel<T> implements ArmedModel {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("crimson_steves_mobs", "model_animated_warden_cow_model"), "main");
	public final ModelPart root;
	protected final ModelPart body;
	protected final ModelPart head;
	protected final ModelPart leftLeg;
	protected final ModelPart leftArm;
	protected final ModelPart rightArm;
	protected final ModelPart rightLeg;

	public ModelAnimatedWardenCowModel(ModelPart p_233512_) {
		super(RenderType::entityCutoutNoCull);
		this.root = p_233512_;
		this.body = this.root.getChild("body");
		this.head = this.body.getChild("head");
		this.rightLeg = this.root.getChild("right_leg");
		this.leftLeg = this.root.getChild("left_leg");
		this.rightArm = this.body.getChild("right_arm");
		this.leftArm = this.body.getChild("left_arm");
	}

	public void translateToHand(HumanoidArm p_102925_, PoseStack pose) {
		body.translateAndRotate(pose);
		if (p_102925_ == HumanoidArm.LEFT) {
			leftArm.translateAndRotate(pose);
		} else {
			rightArm.translateAndRotate(pose);
		}
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 4).addBox(-6.0F, -18.0F, -5.0F, 12.0F, 18.0F, 10.0F).texOffs(52, 0).addBox(-2.0F, -6.0F, -6.0F, 4.0F, 6.0F, 1.0F),
				PartPose.offset(0.0F, 13.0F, 2.0F));
		body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -6.0F, 8.0F, 8.0F, 6.0F).texOffs(22, 0).addBox(4.0F, -6.0F, -4.0F, 1.0F, 3.0F, 1.0F).texOffs(22, 0).addBox(-5.0F, -6.0F, -4.0F, 1.0F, 3.0F, 1.0F),
				PartPose.offset(0.0F, -18.0F, -2.0F));
		body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 16).addBox(-1.9F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-4.0F, -12.0F, -6.0F));
		body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.1F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(4.0F, -12.0F, -6.0F));
		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-4.0F, 12.0F, 2.0F));
		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(4.0F, 12.0F, 2.0F));
		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	public ModelPart root() {
		return this.root;
	}

	public void setupAnim(T e, float f, float f1, float f2, float f3, float f4) {
		this.root().getAllParts().forEach((model) -> {
			model.xRot = 0;
			model.yRot = 0;
			model.zRot = 0;
		});
		float partialTicks = f2 - (float) e.tickCount;
		this.head.xRot += f4 * ((float) Math.PI / 180F) - 0.3491F;
		this.head.yRot += f3 * ((float) Math.PI / 180F);
		this.body.xRot += 0.3491F;
		this.animateWalk(f, f1);
		this.animateIdlePose(f2);
		if (this.attackTime > 0) {
			float a = 1 - this.attackTime;
			a = a * a;
			float b = a * (float) Math.PI * 2;
			float c = Mth.sin(a * (float) Math.PI);
			this.body.xRot += c * (f4 * ((float) Math.PI / 180F) + 0.5f);
			this.leftArm.xRot -= c * (f4 * ((float) Math.PI / 180F) + 1f);
			this.leftArm.zRot += b;
			this.rightArm.xRot -= c * (f4 * ((float) Math.PI / 180F) + 1f);
			this.rightArm.zRot -= b;
		}
	}

	private void animateIdlePose(float p_233515_) {
		float f = p_233515_ * 0.1F;
		float f1 = Mth.cos(f);
		float f2 = Mth.sin(f);
		this.head.zRot += 0.06F * f1;
		this.head.xRot += 0.06F * f2;
		this.body.zRot += 0.025F * f2;
		this.body.xRot += 0.025F * f1;
	}

	private void animateWalk(float p_233539_, float p_233540_) {
		float f = Math.min(0.5F, 3.0F * p_233540_);
		float f1 = p_233539_ * 0.8662F;
		float f2 = Mth.cos(f1);
		float f3 = Mth.sin(f1);
		float f4 = Math.min(0.35F, f);
		this.head.zRot += 0.3F * f3 * f;
		this.head.xRot += 1.2F * Mth.cos(f1 + ((float) Math.PI / 2F)) * f4;
		this.body.zRot += 0.1F * f3 * f;
		this.body.xRot += 1.0F * f2 * f4;
		this.leftLeg.xRot = 1.0F * f2 * f;
		this.rightLeg.xRot += 1.0F * Mth.cos(f1 + (float) Math.PI) * f;
		this.leftArm.xRot += -(0.8F * f2 * f) - 0.3491F;
		this.leftArm.zRot += 0.0F;
		this.rightArm.xRot += -(0.8F * f3 * f) - 0.3491F;
		this.rightArm.zRot += 0.0F;
	}
}
