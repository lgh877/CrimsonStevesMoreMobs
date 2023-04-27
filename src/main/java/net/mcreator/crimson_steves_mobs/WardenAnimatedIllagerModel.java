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

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.AnimationUtils;

import com.mojang.math.Vector3f;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

@OnlyIn(Dist.CLIENT)
public class WardenAnimatedIllagerModel<T extends PathfinderMob> extends HierarchicalModel<T> implements ArmedModel {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("workspace", "warden_animated_illager_model"), "main");
	public final ModelPart root;
	protected final ModelPart body;
	protected final ModelPart head;
	protected final ModelPart Arms;
	protected final ModelPart leftArm;
	protected final ModelPart rightArm;
	protected final ModelPart leftLeg;
	protected final ModelPart rightLeg;
	protected final float pi = (float) Math.PI;

	public WardenAnimatedIllagerModel(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.head = this.body.getChild("head");
		this.Arms = this.body.getChild("arms");
		this.rightArm = this.body.getChild("rightArm");
		this.leftArm = this.body.getChild("leftArm");
		this.leftLeg = root.getChild("left_leg");
		this.rightLeg = root.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition body = partdefinition.addOrReplaceChild("body",
				CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, -12.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 38).addBox(-4.0F, -12.0F, -3.0F, 8.0F, 18.0F, 6.0F, new CubeDeformation(0.25F)),
				PartPose.offset(0.0F, 12.0F, 0.0F));
		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));
		PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));
		PartDefinition arms = body.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(0.0F, -8.5F, 0.3F));
		PartDefinition arms_rotation = arms.addOrReplaceChild("arms_rotation",
				CubeListBuilder.create().texOffs(44, 22).addBox(-8.0F, 0.0F, -2.05F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(40, 38).addBox(-4.0F, 4.0F, -2.05F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -2.0F, 0.05F, -0.7505F, 0.0F, 0.0F));
		PartDefinition arms_flipped = arms_rotation.addOrReplaceChild("arms_flipped", CubeListBuilder.create().texOffs(44, 22).mirror().addBox(4.0F, -24.0F, -2.05F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition leftArm = body.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(6.0F, -10.0F, 0.0F));
		PartDefinition rightArm = body.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(40, 46).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -10.0F, 0.0F));
		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(2.0F, 12.0F, 0.0F));
		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T e, float f, float f1, float f2, float f3, float f4) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if ("big brain".equals(e.getName().getString()))
			head.offsetScale(new Vector3f(1f, 1f, 1f));
		if (e.getPersistentData().getBoolean("shouldHideArms")) {
			Arms.visible = true;
			leftArm.visible = false;
			rightArm.visible = false;
		} else {
			Arms.visible = false;
			leftArm.visible = true;
			rightArm.visible = true;
		}
		if (e.getPersistentData().getBoolean("zombieArmAnimation")) {
			AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, e.isAggressive(), this.attackTime, f2);
		} else if (e.isAggressive()) {
			AnimationUtils.swingWeaponDown(this.rightArm, this.leftArm, e, this.attackTime, f2);
		}
		if (e.getPersistentData().getBoolean("Dancing") || e.isShiftKeyDown()) {
			float sin1 = Mth.sin(f2 * 2);
			this.head.x += Mth.sin(f2 / 2);
			this.head.y += sin1 + 0.4F;
			this.rightArm.zRot += ((float) Math.PI / 180F) * (70.0F + Mth.cos(f2) * 10.0F);
			this.leftArm.zRot += this.rightArm.zRot * -1.0F;
			this.rightArm.y += sin1 * 0.5F + 1.5F;
			this.leftArm.y += sin1 * 0.5F + 1.5F;
			this.body.y += sin1 * 0.35F;
		}
		Arms.xRot -= pi / 5.5f;
		this.head.xRot = f4 * ((float) Math.PI / 180F);
		this.head.yRot = f3 * ((float) Math.PI / 180F);
		this.animateWalk(f, f1);
		this.animateIdlePose(f2);
		if (this.attackTime > 0) {
			float a = 1 - this.attackTime;
			a = a * a;
			//float b = a * (float) Math.PI * 2;
			float c = Mth.sin(a * (float) Math.PI);
			this.body.xRot += c * (f4 * ((float) Math.PI / 180F) + 0.5f);
			//this.leftArm.xRot -= c * (f4 * ((float) Math.PI / 180F) + 1f);
			//this.leftArm.zRot += b;
			//this.rightArm.xRot -= c * (f4 * ((float) Math.PI / 180F) + 1f);
			//this.rightArm.zRot -= b;
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
		this.leftLeg.xRot += 1.0F * f2 * f;
		this.rightLeg.xRot += 1.0F * Mth.cos(f1 + (float) Math.PI) * f;
		this.leftArm.xRot += -(0.8F * f2 * f);
		this.leftArm.zRot += 0.0F;
		this.rightArm.xRot += -(0.8F * f3 * f);
		this.rightArm.zRot += 0.0F;
	}

	public void translateToHand(HumanoidArm p_102925_, PoseStack pose) {
		root.translateAndRotate(pose);
		body.translateAndRotate(pose);
		if (p_102925_ == HumanoidArm.LEFT) {
			leftArm.translateAndRotate(pose);
		} else {
			rightArm.translateAndRotate(pose);
		}
	}

	public ModelPart root() {
		return this.root;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
