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

import com.mojang.math.Vector3f;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

@OnlyIn(Dist.CLIENT)
public class PreAnimatedRedstoneMonstrosityModel<T extends PathfinderMob & ICanBeAnimated> extends MonstrosityWithAnimations<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("crimson_steves_mobs", "pre_animated_redstone_monstrosity"), "main");
	protected final ModelPart root;
	protected final ModelPart root2;
	protected final ModelPart lowerBody;
	protected final ModelPart upperBody;
	protected final ModelPart head;
	protected final ModelPart jaw;
	protected final ModelPart leftHorn;
	protected final ModelPart rightHorn;
	protected final ModelPart leftShoulder;
	protected final ModelPart leftWrist;
	protected final ModelPart leftHand;
	protected final ModelPart leftFinger1;
	protected final ModelPart leftFinger2;
	protected final ModelPart leftFinger3;
	protected final ModelPart rightShoulder;
	protected final ModelPart rightWrist;
	protected final ModelPart rightHand;
	protected final ModelPart rightFinger1;
	protected final ModelPart rightFinger2;
	protected final ModelPart rightFinger3;
	protected final ModelPart leftLeg;
	protected final ModelPart rightLeg;
	protected final ModelPart[] BodyParts;
	protected final float pi = (float) Math.PI;

	public PreAnimatedRedstoneMonstrosityModel(ModelPart root) {
		this.root2 = root;
		this.root = root2.getChild("whole");
		this.leftLeg = this.root.getChild("leftLeg");
		this.rightLeg = this.root.getChild("rightLeg");
		this.lowerBody = this.root.getChild("lowerBody");
		this.upperBody = lowerBody.getChild("upperBody");
		this.head = upperBody.getChild("head");
		this.jaw = head.getChild("jaw");
		this.leftHorn = head.getChild("leftHorn");
		this.rightHorn = head.getChild("rightHorn");
		this.leftShoulder = upperBody.getChild("leftShoulder");
		this.leftWrist = leftShoulder.getChild("leftWrist");
		this.leftHand = leftWrist.getChild("leftHand");
		this.leftFinger1 = leftHand.getChild("leftFinger1");
		this.leftFinger2 = leftHand.getChild("leftFinger2");
		this.leftFinger3 = leftHand.getChild("leftFinger3");
		this.rightShoulder = upperBody.getChild("rightShoulder");
		this.rightWrist = rightShoulder.getChild("rightWrist");
		this.rightHand = rightWrist.getChild("rightHand");
		this.rightFinger1 = rightHand.getChild("rightFinger1");
		this.rightFinger2 = rightHand.getChild("rightFinger2");
		this.rightFinger3 = rightHand.getChild("rightFinger3");
		BodyParts = new ModelPart[]{lowerBody, upperBody, leftShoulder, leftWrist, leftHand, rightShoulder, rightWrist, rightHand, jaw};
	}

	public ModelPart root() {
		return this.root2;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition whole = partdefinition.addOrReplaceChild("whole", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition leftLeg = whole.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(197, 87).addBox(-12.0F, -2.5F, -9.5F, 24.0F, 29.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(21.0F, -26.5F, -1.5F));
		PartDefinition rightLeg = whole.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(197, 87).mirror().addBox(-12.0F, -2.5F, -9.5F, 24.0F, 29.0F, 19.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(-21.0F, -26.5F, -1.5F));
		PartDefinition lowerBody = whole.addOrReplaceChild("lowerBody", CubeListBuilder.create().texOffs(99, 52).addBox(-14.0F, -9.5F, -10.5F, 28.0F, 11.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -26.75F, -1.5F));
		PartDefinition upperBody = lowerBody.addOrReplaceChild("upperBody",
				CubeListBuilder.create().texOffs(197, 0).addBox(-38.0F, -54.25F, -14.5F, 74.0F, 57.0F, 30.0F, new CubeDeformation(0.0F)).texOffs(375, 3).addBox(-16.0F, -48.0F, 15.0F, 28.0F, 16.0F, 11.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -12.0F, 0.0F));
		PartDefinition head = upperBody.addOrReplaceChild("head", CubeListBuilder.create().texOffs(98, 0).addBox(-14.0F, -17.7917F, -11.0833F, 28.0F, 31.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -30.7083F, -24.3167F));
		PartDefinition leftHorn = head.addOrReplaceChild("leftHorn",
				CubeListBuilder.create().texOffs(188, 161).addBox(0.0F, -6.5F, -6.5F, 20.0F, 13.0F, 13.0F, new CubeDeformation(0.0F)).texOffs(254, 163).addBox(11.0F, -21.5F, -6.5F, 9.0F, 15.0F, 13.0F, new CubeDeformation(0.0F)),
				PartPose.offset(14.0F, -6.0417F, 0.3167F));
		PartDefinition rightHorn = head.addOrReplaceChild("rightHorn",
				CubeListBuilder.create().texOffs(188, 135).addBox(-20.0F, -6.5F, -6.5F, 20.0F, 13.0F, 13.0F, new CubeDeformation(0.0F)).texOffs(254, 135).addBox(-20.0F, -21.5F, -6.5F, 9.0F, 15.0F, 13.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-14.0F, -6.0417F, 0.3167F));
		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 0).addBox(-13.5F, -7.5417F, -22.1833F, 27.0F, 10.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, 11.0F));
		PartDefinition leftShoulder = upperBody.addOrReplaceChild("leftShoulder",
				CubeListBuilder.create().texOffs(0, 134).addBox(-3.25F, -11.0F, -13.5F, 37.0F, 23.0F, 27.0F, new CubeDeformation(0.0F)).texOffs(0, 84).addBox(-3.25F, -34.0F, -13.5F, 20.0F, 23.0F, 27.0F, new CubeDeformation(0.0F)),
				PartPose.offset(39.25F, -39.25F, 0.0F));
		PartDefinition leftWrist = leftShoulder.addOrReplaceChild("leftWrist", CubeListBuilder.create().texOffs(112, 118).mirror().addBox(-11.0F, -3.0F, -8.0F, 22.0F, 22.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(15.25F, 15.0F, 0.0F));
		PartDefinition leftHand = leftWrist.addOrReplaceChild("leftHand", CubeListBuilder.create().texOffs(298, 136).mirror().addBox(-14.25F, -4.0F, -14.5F, 29.0F, 20.0F, 29.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(0.0F, 23.0F, 0.0F));
		PartDefinition leftFinger1 = leftHand.addOrReplaceChild("leftFinger1", CubeListBuilder.create().texOffs(82, 37).mirror().addBox(-1.5F, -1.0F, -2.5F, 3.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(6.5F, 17.0F, -5.0F));
		PartDefinition leftFinger2 = leftHand.addOrReplaceChild("leftFinger2", CubeListBuilder.create().texOffs(82, 37).mirror().addBox(-1.5F, -1.0F, -2.5F, 3.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(6.5F, 17.0F, 5.0F));
		PartDefinition leftFinger3 = leftHand.addOrReplaceChild("leftFinger3", CubeListBuilder.create().texOffs(82, 37).mirror().addBox(-1.5F, -1.0F, -2.5F, 3.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(-5.5F, 17.0F, 0.0F));
		PartDefinition rightShoulder = upperBody.addOrReplaceChild("rightShoulder",
				CubeListBuilder.create().texOffs(0, 31).addBox(-16.75F, -34.0F, -13.5F, 20.0F, 23.0F, 27.0F, new CubeDeformation(0.0F)).texOffs(0, 184).addBox(-33.75F, -11.0F, -13.5F, 37.0F, 23.0F, 27.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-41.25F, -39.25F, 0.0F));
		PartDefinition rightWrist = rightShoulder.addOrReplaceChild("rightWrist", CubeListBuilder.create().texOffs(112, 118).addBox(-11.5F, -3.0F, -8.0F, 22.0F, 22.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-15.25F, 15.0F, 0.0F));
		PartDefinition rightHand = rightWrist.addOrReplaceChild("rightHand", CubeListBuilder.create().texOffs(283, 87).addBox(-15.0F, -4.0F, -14.5F, 29.0F, 20.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 23.0F, 0.0F));
		PartDefinition rightFinger1 = rightHand.addOrReplaceChild("rightFinger1", CubeListBuilder.create().texOffs(82, 37).addBox(-1.5F, -1.0F, -2.5F, 3.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.5F, 17.0F, -5.0F));
		PartDefinition rightFinger2 = rightHand.addOrReplaceChild("rightFinger2", CubeListBuilder.create().texOffs(82, 37).addBox(-1.5F, -1.0F, -2.5F, 3.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(5.5F, 17.0F, 0.0F));
		PartDefinition rightFinger3 = rightHand.addOrReplaceChild("rightFinger3", CubeListBuilder.create().texOffs(82, 37).addBox(-1.5F, -1.0F, -2.5F, 3.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.5F, 17.0F, 5.0F));
		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	protected float squashAmount;
	protected float partialTicks;
	protected float pitch;
	protected float headYaw;
	protected float cycle;
	protected float idle;
	protected float rebound;

	@Override
	public void setupAnim(T e, float f, float f1, float f2, float f3, float f4) {
		partialTicks = f2 - (float) e.tickCount;
		pitch = f4 * (pi / 180F);
		headYaw = f3 * (pi / 180F);
		cycle = 0.3f;
		idle = (Mth.sin(f2 * cycle * 0.1f) + 1) * (1 - f1);
		rebound = ((f * cycle) % pi) / pi;
		rebound = 1 - rebound;
		rebound = rebound * rebound;
		squashAmount = Mth.lerp(partialTicks, e.getPersistentData().getFloat("squashAmount0"), e.getPersistentData().getFloat("squashAmount"));
		/*
		float rebound2 = ((f * cycle * 0.5f) % (pi * 2)) / (pi * 2);
		rebound2 = 1 - rebound2;
		rebound2 = rebound2 * rebound2;
		float rebound3 = ((f * cycle * 0.5f) % (pi * 2)) / (pi * 2);
		rebound3 = 1 - rebound3;
		rebound3 = rebound3 * rebound3;
		*/
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
		if ("big brain".equals(e.getName().getString()))
			head.offsetScale(new Vector3f(1f, 1f, 1f));
		if ("pelican".equals(e.getName().getString()))
			head.offsetScale(new Vector3f(0f, 0f, 1.5f));
		this.lowerBody.xRot += idle * 0.05f + 0.15f;
		this.lowerBody.y += Mth.sin(rebound * pi) * 2f * f1 + squashAmount;
		this.upperBody.xRot += idle * 0.05f + 0.15f;
		this.upperBody.y += Mth.sin(rebound * pi) * 2f * f1 + squashAmount;
		this.leftShoulder.xRot += (Mth.cos(f * cycle) - 2) * f1 * 0.1f - 0.1f - idle * 0.1f;
		this.leftShoulder.y += Mth.sin(rebound * pi) * 3f * f1 + squashAmount / 2;
		this.leftWrist.xRot += (Mth.cos(f * cycle) - 2) * f1 * 0.1f - 0.2f - idle * 0.1f;
		this.rightShoulder.xRot += (Mth.cos(f * cycle + pi) - 2) * f1 * 0.1f - 0.1f - idle * 0.1f;
		this.rightShoulder.y += Mth.sin(rebound * pi) * 3f * f1 + squashAmount / 2;
		this.rightWrist.xRot += (Mth.cos(f * cycle + pi) - 2) * f1 * 0.1f - 0.2f - idle * 0.1f;
		this.jaw.xRot += (Mth.sin(f2 * cycle * 0.1f) + 1) * 0.2f;
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
		//this.root.zRot += Mth.sin(f * cycle) * f1 * 0.05f;
		this.head.xRot += pitch - idle * 0.1f - 0.3f;
		this.head.yRot += headYaw * 0.5f;
		this.upperBody.yRot += headYaw * 0.25f;
		this.lowerBody.yRot += headYaw * 0.25f;
		this.lowerBody.zRot += Mth.sin(f * cycle) * f1 * 0.05f;
		/*
		this.leftLeg.xRot += Mth.cos(rebound * pi * 2 + pi * 1.4f) * f1 * 0.2f;
		this.leftLeg.y += Mth.clamp(Mth.sin(rebound * pi * 2) * f1 * 12, -Float.POSITIVE_INFINITY, 1);
		this.leftLeg.z += Mth.cos(rebound * pi * 2 + pi) * f1 * 8;
		this.rightLeg.xRot += Mth.cos(rebound * pi * 2 + pi * 0.4f) * f1 * 0.2f;
		this.rightLeg.y += Mth.clamp(Mth.sin(rebound * pi * 2 + pi) * f1 * 12, -Float.POSITIVE_INFINITY, 1);
		this.rightLeg.z += Mth.cos(rebound * pi * 2) * f1 * 8;
		*/
		this.leftLeg.xRot += Mth.cos(f * cycle + pi * 1.4f) * f1 * 0.2f;
		this.leftLeg.y += Mth.clamp(Mth.sin(f * cycle) * f1 * 12, -Float.POSITIVE_INFINITY, 1);
		this.leftLeg.z += Mth.cos(f * cycle + pi) * f1 * 8;
		this.rightLeg.xRot += Mth.cos(f * cycle + pi * 0.4f) * f1 * 0.2f;
		this.rightLeg.y += Mth.clamp(Mth.sin(f * cycle + pi) * f1 * 12, -Float.POSITIVE_INFINITY, 1);
		this.rightLeg.z += Mth.cos(f * cycle) * f1 * 8;
		super.setupAnim(e, f, f1, f2, f3, f4);
		if ("Grumm".equals(e.getName().getString())) {
			this.leftShoulder.xRot *= -1;
			this.leftWrist.xRot *= -1;
			this.rightShoulder.xRot *= -1;
			this.rightWrist.xRot *= -1;
			this.lowerBody.xRot *= -1f;
			this.lowerBody.yRot *= -1f;
			this.lowerBody.zRot *= -1f;
			this.upperBody.xRot *= -1f;
			this.upperBody.yRot *= -1f;
			this.upperBody.zRot *= -1f;
			this.head.zRot += pi;
			this.leftShoulder.xRot -= pi * 2 / 3;
			this.leftWrist.xRot -= pi / 3;
			this.rightShoulder.xRot -= pi * 2 / 3;
			this.rightWrist.xRot -= pi / 3;
		}
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
