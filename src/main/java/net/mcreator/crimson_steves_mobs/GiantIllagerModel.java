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

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

@OnlyIn(Dist.CLIENT)
public class GiantIllagerModel<T extends PathfinderMob & ICanBeAnimated> extends MonstrosityWithAnimations<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("workspace", "giant_illager"), "main");
	protected final ModelPart root;
	protected final ModelPart lowerBody;
	protected final ModelPart upperBody;
	protected final ModelPart head;
	protected final ModelPart jaw;
	protected final ModelPart leftShoulder;
	protected final ModelPart leftWrist;
	protected final ModelPart leftHand;
	protected final ModelPart rightShoulder;
	protected final ModelPart rightWrist;
	protected final ModelPart rightHand;
	protected final ModelPart leftLeg;
	protected final ModelPart rightLeg;
	protected final ModelPart[] BodyParts;
	protected final float pi = (float) Math.PI;

	public GiantIllagerModel(ModelPart root) {
		this.root = root.getChild("whole");
		this.leftLeg = this.root.getChild("leftLeg");
		this.rightLeg = this.root.getChild("rightLeg");
		this.lowerBody = this.root.getChild("lowerBody");
		this.upperBody = lowerBody.getChild("upperBody");
		this.head = upperBody.getChild("head");
		this.jaw = head.getChild("jaw");
		this.leftShoulder = upperBody.getChild("leftShoulder");
		this.leftWrist = leftShoulder.getChild("leftWrist");
		this.leftHand = leftWrist.getChild("leftHand");
		this.rightShoulder = upperBody.getChild("rightShoulder");
		this.rightWrist = rightShoulder.getChild("rightWrist");
		this.rightHand = rightWrist.getChild("rightHand");
		BodyParts = new ModelPart[]{lowerBody, upperBody, leftShoulder, leftWrist, leftHand, rightShoulder, rightWrist, rightHand, jaw};
	}

	public ModelPart root() {
		return this.root;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition whole = partdefinition.addOrReplaceChild("whole", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition lowerBody = whole.addOrReplaceChild("lowerBody", CubeListBuilder.create(), PartPose.offset(0.0F, -36.0F, 0.0F));
		PartDefinition belt = lowerBody.addOrReplaceChild("belt", CubeListBuilder.create().texOffs(0, 98).addBox(-37.0F, -3.0F, 2.0F, 74.0F, 36.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -17.0F));
		PartDefinition pelvis_r1 = belt.addOrReplaceChild("pelvis_r1", CubeListBuilder.create().texOffs(250, 206).addBox(-12.0F, -12.0F, -2.0F, 24.0F, 24.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
		PartDefinition upperBody = lowerBody.addOrReplaceChild("upperBody", CubeListBuilder.create().texOffs(0, 0).addBox(-37.0F, -68.0F, -15.0F, 74.0F, 68.0F, 30.0F, new CubeDeformation(0.0F)).texOffs(112, 241)
				.addBox(0.0F, -68.0F, 15.0F, 0.0F, 68.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(208, 25).addBox(0.0F, -84.0F, -15.0F, 0.0F, 16.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition head = upperBody.addOrReplaceChild(
				"head", CubeListBuilder.create().texOffs(130, 205).addBox(-18.0F, -21.0F, -21.0F, 36.0F, 31.0F, 21.0F, new CubeDeformation(0.01F)).texOffs(208, 97).addBox(0.0F, -37.0F, -21.0F, 0.0F, 16.0F, 21.0F, new CubeDeformation(0.01F))
						.texOffs(0, 265).addBox(-6.0F, -6.0F, -27.0F, 12.0F, 22.0F, 6.0F, new CubeDeformation(0.01F)).texOffs(45, 343).addBox(-17.5F, 3.0F, -21.0F, 35.0F, 0.0F, 21.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -47.0F, -15.0F));
		PartDefinition brown = head.addOrReplaceChild("brown", CubeListBuilder.create().texOffs(36, 265).addBox(-18.0F, -2.0F, -2.0F, 36.0F, 4.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -8.0F, -21.0F));
		PartDefinition jaw = head.addOrReplaceChild("jaw",
				CubeListBuilder.create().texOffs(0, 236).addBox(-17.5F, -8.0F, -21.5F, 35.0F, 8.0F, 21.0F, new CubeDeformation(0.0F)).texOffs(45, 343).addBox(-17.5F, -2.0F, -21.5F, 35.0F, 0.0F, 21.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 13.0F, 0.5F));
		PartDefinition rightShoulder = upperBody.addOrReplaceChild("rightShoulder", CubeListBuilder.create().texOffs(0, 164).addBox(-28.5F, -22.0F, -14.0F, 37.0F, 44.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(-45.5F, -46.0F, 0.0F));
		PartDefinition rightWrist = rightShoulder.addOrReplaceChild("rightWrist",
				CubeListBuilder.create().texOffs(222, 235).addBox(-13.5F, 0.0F, -11.0F, 27.0F, 16.0F, 22.0F, new CubeDeformation(0.0F)).texOffs(130, 164).addBox(-18.0F, 7.5F, -16.5F, 36.0F, 8.0F, 33.0F, new CubeDeformation(0.5F)),
				PartPose.offset(-10.0F, 22.0F, 0.0F));
		PartDefinition rightHand = rightWrist.addOrReplaceChild("rightHand", CubeListBuilder.create().texOffs(181, 71).addBox(-15.5F, 0.5F, -13.5F, 31.0F, 20.0F, 27.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 16.0F, 0.0F));
		PartDefinition right_finger1 = rightHand.addOrReplaceChild("right_finger1", CubeListBuilder.create().texOffs(144, 257).addBox(-2.5F, 0.0F, -11.5F, 5.0F, 10.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 21.0F, 0.0F));
		PartDefinition right_finger2 = rightHand.addOrReplaceChild("right_finger2", CubeListBuilder.create().texOffs(235, 173).addBox(-2.5F, 0.0F, -6.0F, 5.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, 21.0F, 3.5F));
		PartDefinition mace = rightHand.addOrReplaceChild("mace",
				CubeListBuilder.create().texOffs(0, 273).addBox(-6.0F, -6.0F, -32.5F, 12.0F, 12.0F, 52.0F, new CubeDeformation(1.0F)).texOffs(152, 273).addBox(-12.0F, -12.0F, -82.5F, 24.0F, 24.0F, 48.0F, new CubeDeformation(1.0F)).texOffs(248, 273)
						.addBox(-9.0F, -9.0F, 21.5F, 18.0F, 18.0F, 18.0F, new CubeDeformation(1.0F)).texOffs(0, 337).addBox(-15.0F, -15.0F, -75.5F, 30.0F, 30.0F, 3.0F, new CubeDeformation(1.0F)).texOffs(0, 337)
						.addBox(-15.0F, -15.0F, -57.5F, 30.0F, 30.0F, 3.0F, new CubeDeformation(1.0F)).texOffs(67, 337).addBox(-15.5F, -22.0F, -74.0F, 31.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(67, 337)
						.addBox(-15.5F, -22.0F, -56.0F, 31.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.5F, 25.0F, 0.0F));
		PartDefinition mace_r1 = mace.addOrReplaceChild("mace_r1", CubeListBuilder.create().texOffs(67, 337).mirror().addBox(-15.5F, -3.0F, -18.0F, 31.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(67, 337).mirror()
				.addBox(-15.5F, -3.0F, 0.0F, 31.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 19.0F, -74.0F, 3.1416F, 0.0F, 0.0F));
		PartDefinition mace_r2 = mace.addOrReplaceChild("mace_r2",
				CubeListBuilder.create().texOffs(67, 337).addBox(-34.5F, -22.0F, 18.0F, 31.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(67, 337).addBox(-34.5F, -22.0F, 0.0F, 31.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -19.0F, -74.0F, 0.0F, 0.0F, -1.5708F));
		PartDefinition mace_r3 = mace.addOrReplaceChild("mace_r3",
				CubeListBuilder.create().texOffs(67, 337).addBox(-15.5F, -3.0F, -18.0F, 31.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(67, 337).addBox(-15.5F, -3.0F, 0.0F, 31.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(19.0F, 0.0F, -74.0F, 3.1416F, 0.0F, -1.5708F));
		PartDefinition leftShoulder = upperBody.addOrReplaceChild("leftShoulder", CubeListBuilder.create().texOffs(0, 164).mirror().addBox(-8.5F, -22.0F, -14.0F, 37.0F, 44.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(45.5F, -46.0F, 0.0F));
		PartDefinition leftWrist = leftShoulder.addOrReplaceChild("leftWrist", CubeListBuilder.create().texOffs(222, 235).mirror().addBox(-13.5F, 0.0F, -11.0F, 27.0F, 16.0F, 22.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(130, 164)
				.addBox(-18.0F, 7.5F, -16.5F, 36.0F, 8.0F, 33.0F, new CubeDeformation(0.5F)), PartPose.offset(10.0F, 22.0F, 0.0F));
		PartDefinition leftHand = leftWrist.addOrReplaceChild("leftHand", CubeListBuilder.create().texOffs(181, 71).mirror().addBox(-16.0F, 0.5F, -13.5F, 31.0F, 20.0F, 27.0F, new CubeDeformation(0.5F)).mirror(false),
				PartPose.offset(0.5F, 16.0F, 0.0F));
		PartDefinition left_finger1 = leftHand.addOrReplaceChild("left_finger1", CubeListBuilder.create().texOffs(144, 257).addBox(-2.5F, 0.0F, -11.5F, 5.0F, 10.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(9.5F, 21.0F, 0.0F));
		PartDefinition left_finger2 = leftHand.addOrReplaceChild("left_finger2", CubeListBuilder.create().texOffs(235, 173).addBox(-2.5F, 0.0F, -6.0F, 5.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.5F, 21.0F, 4.5F));
		PartDefinition rightLeg = whole.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(235, 118).addBox(-12.0F, 0.0F, -9.5F, 24.0F, 36.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(-21.0F, -36.0F, 0.0F));
		PartDefinition leftLeg = whole.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(208, 0).mirror().addBox(-12.0F, 0.0F, -9.5F, 24.0F, 36.0F, 19.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(21.0F, -36.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(T e, float f, float f1, float f2, float f3, float f4) {
		float pitch = f4 * (pi / 180F);
		float headYaw = f3 * (pi / 180F);
		float cycle = 0.3f;
		float idle = (Mth.sin(f2 * cycle * 0.1f) + 1) * (1 - f1);
		//float idle1 = (Mth.sin(-f2 * cycle * 0.1f) + 1) * (1 - f1);
		this.root().getAllParts().forEach(ModelPart::resetPose);
		/*
		lowerBody.setPos(0.0F, -26.75F, -1.5F);
		upperBody.setPos(0.0F, -12.0F, 0.0F);
		leftShoulder.setPos(39.25F, -39.25F, 0.0F);
		leftWrist.setPos(15.25F, 15.0F, 0.0F);
		rightShoulder.setPos(-39.25F, -39.25F, 0.0F);
		rightWrist.setPos(-15.25F, 15.0F, 0.0F);
		leftLeg.setPos(21.0F, -26.5F, -1.5F);
		rightLeg.setPos(-21.0F, -26.5F, -1.5F);
		*/
		this.lowerBody.xRot += idle * 0.05f + 0.15f;
		this.upperBody.xRot += idle * 0.05f + 0.15f;
		this.leftShoulder.xRot += (Mth.cos(f * cycle) - 2) * f1 * 0.1f - 0.1f - idle * 0.1f;
		this.leftWrist.xRot += (Mth.cos(f * cycle) - 2) * f1 * 0.1f - 0.2f - idle * 0.1f;
		this.rightShoulder.xRot += (Mth.cos(f * cycle + pi) - 2) * f1 * 0.1f - 0.1f - idle * 0.1f;
		this.rightWrist.xRot += (Mth.cos(f * cycle + pi) - 2) * f1 * 0.1f - 0.2f - idle * 0.1f;
		this.jaw.xRot += idle * 0.2f;
		if (this.attackTime > 0) {
			float a = 1 - this.attackTime;
			//a = (float) Math.pow((double) a, e.maxSwingTime() / 15);
			a = 1 - Mth.clamp(Mth.sin(a * (float) Math.PI) * (2 + e.maxSwingTime() / 40), 0, 1);
			for (int bp = 0; bp < this.BodyParts.length; bp++) {
				this.BodyParts[bp].xRot *= a;
				this.BodyParts[bp].yRot *= a;
				this.BodyParts[bp].zRot *= a;
			}
		}
		this.root.zRot = Mth.sin(f * cycle) * f1 * 0.1f;
		this.head.xRot += pitch - idle * 0.1f - 0.3f;
		this.head.yRot += headYaw * 0.5f;
		this.upperBody.yRot += headYaw * 0.25f;
		this.lowerBody.yRot += headYaw * 0.25f;
		this.leftLeg.xRot += Mth.cos(f * cycle + pi * 1.4f) * f1 * 0.2f;
		this.leftLeg.y += Mth.clamp(Mth.sin(f * cycle) * f1 * 12, -Float.POSITIVE_INFINITY, 0);
		this.leftLeg.z += Mth.cos(f * cycle + pi) * f1 * 8;
		this.rightLeg.xRot += Mth.cos(f * cycle + pi * 0.4f) * f1 * 0.2f;
		this.rightLeg.y += Mth.clamp(Mth.sin(f * cycle + pi) * f1 * 12, -Float.POSITIVE_INFINITY, 0);
		this.rightLeg.z += Mth.cos(f * cycle) * f1 * 8;
		super.setupAnim(e, f, f1, f2, f3, f4);
		/*
		this.animate(e.getAnimationState("slam"), MonstrosityAnimations.REDSTONE_MONSTROSITY_SLAM, f2);
		this.animate(e.getAnimationState("leftSlam"), MonstrosityAnimations.REDSTONE_MONSTROSITY_LEFTSLAM, f2);
		this.animate(e.getAnimationState("rightSlam"), MonstrosityAnimations.REDSTONE_MONSTROSITY_RIGHTSLAM, f2);
		this.animate(e.getAnimationState("insertArmsStart"), MonstrosityAnimations.REDSTONE_MONSTROSITY_INSERTINGARMSSTART, f2);
		this.animate(e.getAnimationState("insertArmsIdle"), MonstrosityAnimations.REDSTONE_MONSTROSITY_INSERTINGARMSIDLE, f2);
		this.animate(e.getAnimationState("insertArmsStop"), MonstrosityAnimations.REDSTONE_MONSTROSITY_INSERTINGARMSSTOP, f2);
		*/
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
