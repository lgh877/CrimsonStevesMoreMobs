package net.mcreator.crimson_steves_mobs.client.model;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.ArmedModel;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class ModelAnimatedRedstoneGolemModel<T extends LivingEntity> extends HierarchicalModel<T> implements ArmedModel, HeadedModel {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("crimson_steves_mobs", "model_animated_redstone_golem_model"), "main");
	protected final ModelPart whole;
	protected final ModelPart lowerBody;
	protected final ModelPart upperBody;
	protected final ModelPart head;
	protected final ModelPart redstoneBlock;
	protected final ModelPart leftShoulder;
	protected final ModelPart leftWrist;
	// public final ModelPart[] leftFinger = new ModelPart[3];
	protected final ModelPart rightShoulder;
	protected final ModelPart rightWrist;
	// public final ModelPart[] rightFinger = new ModelPart[3];
	protected final ModelPart leftLeg;
	protected final ModelPart rightLeg;
	protected final float pi = (float) Math.PI;

	public ModelAnimatedRedstoneGolemModel(ModelPart root) {
		super(RenderType::entityCutoutNoCull);
		this.whole = root.getChild("whole");
		this.lowerBody = whole.getChild("lowerBody");
		this.upperBody = lowerBody.getChild("upperBody");
		this.redstoneBlock = upperBody.getChild("redstoneBlock");
		this.head = upperBody.getChild("head");
		this.leftShoulder = upperBody.getChild("leftShoulder");
		this.leftWrist = leftShoulder.getChild("leftWrist");
		this.rightShoulder = upperBody.getChild("rightShoulder");
		this.rightWrist = rightShoulder.getChild("rightWrist");
		/*
		 * for (int i = 0; i < 3; i++) { this.leftFinger[i] =
		 * leftWrist.getChild(getSegmentName1(i)); this.rightFinger[i] =
		 * rightWrist.getChild(getSegmentName2(i)); }
		 */
		this.leftLeg = whole.getChild("leftLeg");
		this.rightLeg = whole.getChild("rightLeg");
	}

	public ModelPart root() {
		return this.whole;
	}

	/*
	 * private static String getSegmentName1(int p_170706_) { return "leftFinger" +
	 * p_170706_ + 1; } private static String getSegmentName2(int p_170706_) {
	 * return "rightFinger" + p_170706_ + 1; }
	 */
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition whole = partdefinition.addOrReplaceChild("whole", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition lowerBody = whole.addOrReplaceChild("lowerBody", CubeListBuilder.create(), PartPose.offset(0.0F, -20.0F, 0.0F));
		PartDefinition pelvis_r1 = lowerBody.addOrReplaceChild("pelvis_r1", CubeListBuilder.create().texOffs(72, 6).addBox(-11.0F, -7.0F, -1.25F, 22.0F, 14.0F, 8.0F), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 1.5708F, 0.0F, 0.0F));
		PartDefinition upperBody = lowerBody.addOrReplaceChild("upperBody", CubeListBuilder.create(), PartPose.offset(0.0F, -7.8F, 1.6F));
		PartDefinition body_r1 = upperBody.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(0, 28).addBox(-20.0F, -16.0F, -22.0F, 40.0F, 20.0F, 32.0F, new CubeDeformation(0.5F)),
				PartPose.offsetAndRotation(0.0F, -22.2F, 4.4F, 1.5708F, 0.0F, 0.0F));
		PartDefinition redstoneBlock = upperBody.addOrReplaceChild("redstoneBlock",
				CubeListBuilder.create().texOffs(132, 16).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 16.0F).texOffs(180, 0).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(1.0F)), PartPose.offset(0.0F, -12.2F, -1.6F));
		PartDefinition head = upperBody.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -24.2F, -11.6F));
		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -3.75F, -10.0F, 16.0F, 12.0F, 16.0F), PartPose.offsetAndRotation(0.0F, -2.0F, -8.0F, 1.5708F, 0.0F, 0.0F));
		PartDefinition leftShoulder = upperBody.addOrReplaceChild("leftShoulder",
				CubeListBuilder.create().texOffs(31, 116).addBox(0.0F, -8.0F, -6.0F, 14.0F, 24.0F, 12.0F).texOffs(31, 188).addBox(0.0F, -8.0F, -6.0F, 14.0F, 24.0F, 12.0F, new CubeDeformation(0.3F)), PartPose.offset(20.0F, -22.2F, -1.6F));
		PartDefinition leftWrist = leftShoulder.addOrReplaceChild("leftWrist", CubeListBuilder.create().texOffs(83, 118).addBox(-6.0F, 0.0F, -6.0F, 11.0F, 22.0F, 12.0F), PartPose.offset(11.5F, 16.0F, 0.0F));
		PartDefinition leftFinger1 = leftWrist.addOrReplaceChild("leftFinger1", CubeListBuilder.create(), PartPose.offset(1.5F, 23.0F, -2.5F));
		PartDefinition finger4_r1 = leftFinger1.addOrReplaceChild("finger4_r1", CubeListBuilder.create().texOffs(145, 80).addBox(-34.75F, 10.625F, -0.7188F, 3.0F, 10.0F, 5.0F),
				PartPose.offsetAndRotation(-33.25F, -11.625F, 1.7813F, 0.0F, -3.1416F, 0.0F));
		PartDefinition leftFinger2 = leftWrist.addOrReplaceChild("leftFinger2", CubeListBuilder.create(), PartPose.offset(1.5F, 22.0F, 3.25F));
		PartDefinition finger5_r1 = leftFinger2.addOrReplaceChild("finger5_r1", CubeListBuilder.create().texOffs(145, 80).addBox(-34.75F, 10.625F, -6.4688F, 3.0F, 10.0F, 5.0F),
				PartPose.offsetAndRotation(-33.25F, -10.625F, -3.9688F, 0.0F, -3.1416F, 0.0F));
		PartDefinition leftFinger3 = leftWrist.addOrReplaceChild("leftFinger3", CubeListBuilder.create(), PartPose.offset(-3.25F, 22.0F, -2.5F));
		PartDefinition finger6_r1 = leftFinger3.addOrReplaceChild("finger6_r1", CubeListBuilder.create().texOffs(129, 82).mirror().addBox(-30.0F, 10.625F, -0.7188F, 3.0F, 8.0F, 5.0F).mirror(false),
				PartPose.offsetAndRotation(-28.5F, -10.625F, 1.7813F, 0.0F, 3.1416F, 0.0F));
		PartDefinition rightShoulder = upperBody.addOrReplaceChild("rightShoulder",
				CubeListBuilder.create().texOffs(31, 80).addBox(-14.5F, -8.0F, -6.0F, 14.0F, 24.0F, 12.0F).texOffs(31, 152).addBox(-14.5F, -8.0F, -6.0F, 14.0F, 24.0F, 12.0F, new CubeDeformation(0.3F)), PartPose.offset(-19.5F, -22.2F, -1.6F));
		PartDefinition rightWrist = rightShoulder.addOrReplaceChild("rightWrist", CubeListBuilder.create().texOffs(83, 82).addBox(-5.5F, -0.25F, -6.0F, 11.0F, 22.0F, 12.0F), PartPose.offset(-12.5F, 16.25F, 0.0F));
		PartDefinition rightFinger1 = rightWrist.addOrReplaceChild("rightFinger1", CubeListBuilder.create(), PartPose.offset(-2.0F, 21.75F, -2.5F));
		PartDefinition finger1_r1 = rightFinger1.addOrReplaceChild("finger1_r1", CubeListBuilder.create().texOffs(145, 80).mirror().addBox(32.25F, 10.625F, -0.7188F, 3.0F, 10.0F, 5.0F).mirror(false),
				PartPose.offsetAndRotation(33.75F, -10.625F, 1.7813F, 0.0F, 3.1416F, 0.0F));
		PartDefinition rightFinger2 = rightWrist.addOrReplaceChild("rightFinger2", CubeListBuilder.create(), PartPose.offset(-2.0F, 21.75F, 3.25F));
		PartDefinition finger2_r1 = rightFinger2.addOrReplaceChild("finger2_r1", CubeListBuilder.create().texOffs(145, 80).mirror().addBox(32.25F, 10.625F, -6.4688F, 3.0F, 10.0F, 5.0F).mirror(false),
				PartPose.offsetAndRotation(33.75F, -10.625F, -3.9688F, 0.0F, 3.1416F, 0.0F));
		PartDefinition rightFinger3 = rightWrist.addOrReplaceChild("rightFinger3", CubeListBuilder.create(), PartPose.offset(2.75F, 22.75F, -2.5F));
		PartDefinition finger3_r1 = rightFinger3.addOrReplaceChild("finger3_r1", CubeListBuilder.create().texOffs(129, 82).addBox(27.5F, 10.625F, -0.7188F, 3.0F, 8.0F, 5.0F), PartPose.offsetAndRotation(29.0F, -11.625F, 1.7813F, 0.0F, 3.1416F, 0.0F));
		PartDefinition leftLeg = whole.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(192, 48).addBox(-6.0F, 0.0F, -6.0F, 12.0F, 20.0F, 12.0F), PartPose.offset(14.0F, -20.0F, 0.0F));
		PartDefinition rightLeg = whole.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(144, 48).addBox(-6.0F, 0.0F, -6.0F, 12.0F, 20.0F, 12.0F), PartPose.offset(-14.0F, -20.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	public void renderToBuffer(PoseStack p_102034_, VertexConsumer p_102035_, int p_102036_, int p_102037_, float p_102038_, float p_102039_, float p_102040_, float p_102041_) {
		super.renderToBuffer(p_102034_, p_102035_, p_102036_, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);
	}

	public void setupAnim(T e, float f, float f1, float f2, float f3, float f4) {
		float idle = (Mth.sin(f2 * 0.03f) + 1) * Mth.clamp(1 - f1, 0, 1);
		float pitch = f4 * (pi / 180F);
		float headYaw = f3 * (pi / 180F);
		float walk = (Mth.sin(f) + 1) * f1;
		float walk1 = (Mth.cos(f * 0.5f) + 1) * f1;
		float walk2 = (-Mth.cos(f * 0.5f) + 1) * f1;
		// basic settings
		this.whole.zRot = Mth.sin(f * 0.5f) * f1 * 0.1f;
		this.head.xRot = pitch - (idle + walk) * 0.14f - pi / 18;
		this.head.yRot = headYaw * 0.5f;
		this.upperBody.xRot = (idle + walk) * 0.07f + pi / 36;
		this.upperBody.yRot = headYaw * 0.5f + idle * 0.07f + pi / 36;
		upperBody.setPos(0.0F, -7.8F, 1.6F);
		redstoneBlock.setPos(0.0F, -12.2F, -1.6F);
		this.lowerBody.xRot = (idle + walk) * 0.07f + pi / 36;
		lowerBody.setPos(0.0F, -20.0F, 0.0F);
		this.leftShoulder.xRot = -(f1 + (walk2 - f1) * 5 + idle) * 0.07f - pi / 18;
		this.leftShoulder.yRot = 0;
		this.leftShoulder.zRot = 0;
		leftShoulder.setPos(20.0F, -22.2F, -1.6F);
		this.leftWrist.xRot = -(f1 + walk + idle) * 0.2f;
		this.leftWrist.yRot = 0;
		this.leftWrist.zRot = 0;
		leftWrist.setPos(11.5F, 16.0F, 0.0F);
		this.rightShoulder.xRot = -(f1 + (walk1 - f1) * 5 + idle) * 0.07f - pi / 18;
		this.rightShoulder.yRot = 0;
		this.rightShoulder.zRot = 0;
		rightShoulder.setPos(-19.5F, -22.2F, -1.6F);
		this.rightWrist.xRot = -(f1 + walk + idle) * 0.2f;
		this.rightWrist.yRot = 0;
		this.rightWrist.zRot = 0;
		rightWrist.setPos(-12.5F, 16.25F, 0.0F);
		leftLeg.setPos(14.0F, -20.0F, 0.0F);
		rightLeg.setPos(-14.0F, -20.0F, 0.0F);
		boolean flag1 = e.getMainArm() == HumanoidArm.RIGHT;
		boolean flag2 = e.getMainHandItem().isEmpty();
		boolean flag3 = e.getOffhandItem().isEmpty();
		if (!flag2) {
			if (flag1)
				this.rightShoulder.xRot -= pi * 0.12f;
			else
				this.leftShoulder.xRot -= pi * 0.12f;
		}
		if (!flag3) {
			if (flag1)
				this.leftShoulder.xRot -= pi * 0.12f;
			else
				this.rightShoulder.xRot -= pi * 0.12f;
		}
		if (this.riding) {
			this.rightShoulder.xRot += (-(float) Math.PI / 5F);
			this.leftShoulder.xRot += (-(float) Math.PI / 5F);
			this.rightLeg.xRot = -1.4137167F;
			this.rightLeg.yRot = ((float) Math.PI / 10F);
			this.rightLeg.zRot = 0.07853982F;
			this.leftLeg.xRot = -1.4137167F;
			this.leftLeg.yRot = (-(float) Math.PI / 10F);
			this.leftLeg.zRot = -0.07853982F;
		} else {
			this.leftLeg.xRot = Mth.cos(f * 0.5f + pi * 1.4f) * f1 * 0.4f;
			this.leftLeg.yRot = 0;
			this.leftLeg.zRot = 0;
			this.leftLeg.y += Mth.clamp(Mth.sin(f * 0.5f) * f1 * 5, -Float.POSITIVE_INFINITY, 0);
			this.leftLeg.z += Mth.cos(f * 0.5f + pi) * f1 * 5;
			this.rightLeg.xRot = Mth.cos(f * 0.5F + pi * 0.4f) * f1 * 0.4f;
			this.rightLeg.yRot = 0;
			this.rightLeg.zRot = 0;
			this.rightLeg.y += Mth.clamp(Mth.sin(f * 0.5f + pi) * f1 * 5, -Float.POSITIVE_INFINITY, 0);
			this.rightLeg.z += Mth.cos(f * 0.5f) * f1 * 5;
		}
		//
		if (e.isShiftKeyDown()) {
			this.redstoneBlock.x += (float) (Math.random() - 0.5) * 3;
			this.redstoneBlock.y += (float) (Math.random() - 0.5) * 3;
			this.redstoneBlock.z += (Mth.sin(f2 * 1.7f) + 1) * 2;
			this.lowerBody.xRot = pi * 5 / 18;
			this.lowerBody.x += (float) (Math.random() - 0.5) * 2;
			this.lowerBody.z += (float) (Math.random() - 0.5) * 2;
			this.upperBody.xRot = pi / 18;
			this.upperBody.yRot = 0;
			this.upperBody.x += (float) (Math.random() - 0.5) * 2;
			this.upperBody.z += (float) (Math.random() - 0.5) * 2;
			this.leftShoulder.xRot = -pi * 5 / 18;
			this.leftShoulder.yRot = -0.1f;
			this.leftShoulder.x += (float) (Math.random() - 0.5) * 3;
			this.leftShoulder.z += (float) (Math.random() - 0.5) * 3;
			this.leftWrist.xRot = -pi / 9;
			this.leftWrist.yRot = 0.4f;
			this.rightShoulder.xRot = -pi * 5 / 18;
			this.rightShoulder.yRot = 0.1f;
			this.rightShoulder.x += (float) (Math.random() - 0.5) * 3;
			this.rightShoulder.z += (float) (Math.random() - 0.5) * 3;
			this.rightWrist.xRot = -pi / 9;
			this.rightWrist.yRot = -0.4f;
			if (this.attackTime > 0) {
				float a = 1 - this.attackTime;
				a = a * a * a;
				float b = Mth.sin(a * pi);
				this.upperBody.z += -b * 8;
				this.lowerBody.z += -b * 8;
				this.leftShoulder.z += -b * 8;
				this.rightShoulder.z += -b * 8;
			}
		} else {
			if (this.attackTime > 0) {
				float a = 1 - this.attackTime;
				a = a * a * a;
				float b = Mth.sin(a * pi);
				if (this.getAttackArm(e) == HumanoidArm.RIGHT) {
					this.upperBody.yRot += -b;
					this.head.yRot += b;
					this.rightShoulder.xRot *= 1 - b;
					this.rightShoulder.xRot += (-pi / 2 + pitch) * b;
					this.rightShoulder.zRot += -a * pi * 2;
					// this.rightShoulder.yRot = (headYaw + 1) * b;
					this.rightWrist.xRot *= 1 - b;
					// this.rightWrist.zRot += b;
				} else {
					this.upperBody.yRot += b;
					this.head.yRot += -b;
					this.leftShoulder.xRot *= 1 - b;
					this.leftShoulder.xRot += (-pi / 2 + pitch) * b;
					this.leftShoulder.zRot += a * pi * 2;
					// this.rightShoulder.yRot = (headYaw + 1) * b;
					this.leftShoulder.xRot *= 1 - b;
					// this.rightWrist.zRot += b;
				}
			}
			// shootState
			if (e.getPersistentData().getBoolean("shootState")) {
				if (flag1) {
					this.animateShootAnim(this.leftShoulder, this.leftWrist, pitch, headYaw, e, f2);
				} else {
					this.animateShootAnim(this.rightShoulder, this.rightWrist, pitch, headYaw, e, f2);
				}
			}
			// ==========
		}
	}

	private void animateShootAnim(ModelPart shoulder, ModelPart wrist, float pitch, float yaw, T e, float f2) {
		wrist.xRot = -shoulder.xRot + pitch - pi / 2;
		wrist.yRot = -shoulder.yRot + yaw * 0.5f;
		if (e.getPersistentData().getBoolean("shootActive")) {
			wrist.x += (float) (Math.random() - 0.5) * 4;
			wrist.y += (float) (Math.random() - 0.5) * 4;
		}
	}

	public ModelPart getHead() {
		return this.lowerBody;
	}

	public void translateToHead(PoseStack pose) {
		whole.translateAndRotate(pose);
		lowerBody.translateAndRotate(pose);
		upperBody.translateAndRotate(pose);
	}

	public void translateToHand(HumanoidArm p_102925_, PoseStack pose) {
		whole.translateAndRotate(pose);
		lowerBody.translateAndRotate(pose);
		upperBody.translateAndRotate(pose);
		if (p_102925_ == HumanoidArm.LEFT) {
			leftShoulder.translateAndRotate(pose);
			leftWrist.translateAndRotate(pose);
			pose.translate(-0.2, 0, 0.4);
		} else {
			rightShoulder.translateAndRotate(pose);
			rightWrist.translateAndRotate(pose);
			pose.translate(0.2, 0, 0.4);
		}
	}

	private ModelPart getArm(HumanoidArm p_102923_) {
		return p_102923_ == HumanoidArm.LEFT ? this.leftWrist : this.rightWrist;
	}

	private HumanoidArm getAttackArm(T p_102857_) {
		HumanoidArm humanoidarm = p_102857_.getMainArm();
		return p_102857_.swingingArm == InteractionHand.MAIN_HAND ? humanoidarm : humanoidarm.getOpposite();
	}
}
