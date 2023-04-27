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
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.InteractionHand;
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

import java.util.List;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

import com.google.common.collect.ImmutableList;

@OnlyIn(Dist.CLIENT)
public class MutantZombieModel<T extends LivingEntity & IMutantZombie> extends HierarchicalModel<T> implements ArmedModel {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(CrimsonStevesMobsMod.MODID, "mutant_zombie_model"), "main");
	protected final ModelPart root;
	protected final ModelPart whole;
	protected final ModelPart leftThigh;
	protected final ModelPart leftShank;
	protected final ModelPart rightThigh;
	protected final ModelPart rightShank;
	protected final ModelPart lowerBody;
	protected final ModelPart upperBody;
	protected final ModelPart head;
	protected final ModelPart leftShoulder;
	protected final ModelPart leftWrist;
	protected final ModelPart rightShoulder;
	protected final ModelPart rightWrist;
	protected float partialTicks;
	protected final float pi = (float) Math.PI;
	public final List<ModelPart> BodyParts;

	public ModelPart root() {
		return this.root;
	}

	public List<ModelPart> getBodyLayerModelParts() {
		return this.BodyParts;
	}

	public MutantZombieModel(ModelPart root2) {
		this.root = root2;
		this.whole = root.getChild("whole");
		this.leftThigh = whole.getChild("leftThigh");
		this.leftShank = leftThigh.getChild("leftShank");
		this.rightThigh = whole.getChild("rightThigh");
		this.rightShank = rightThigh.getChild("rightShank");
		this.lowerBody = whole.getChild("lowerBody");
		this.upperBody = lowerBody.getChild("upperBody");
		this.leftShoulder = upperBody.getChild("leftShoulder");
		this.leftWrist = leftShoulder.getChild("leftWrist");
		this.rightShoulder = upperBody.getChild("rightShoulder");
		this.rightWrist = rightShoulder.getChild("rightWrist");
		this.head = upperBody.getChild("head");
		this.BodyParts = ImmutableList.of(head, upperBody, leftShoulder, leftWrist, rightShoulder, rightWrist, lowerBody, leftThigh, leftShank, rightThigh, rightShank);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition whole = partdefinition.addOrReplaceChild("whole", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition lowerBody = whole.addOrReplaceChild("lowerBody", CubeListBuilder.create().texOffs(0, 44).addBox(-7.0F, -16.0F, -6.0F, 14.0F, 16.0F, 12.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -16.0F, 8.0F, 0.4363F, 0.0F, 0.0F));
		PartDefinition upperBody = lowerBody.addOrReplaceChild("upperBody", CubeListBuilder.create().texOffs(0, 16).addBox(-12.0F, -12.0F, -8.0F, 24.0F, 12.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -16.0F, 0.0F, 0.4363F, 0.0F, 0.0F));
		PartDefinition head = upperBody.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.0F, -4.0F, -0.8727F, 0.0F, 0.0F));
		PartDefinition leftShoulder = upperBody.addOrReplaceChild("leftShoulder", CubeListBuilder.create().texOffs(104, 0).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(14.0F, -7.0F, 0.0F, -0.4702F, -0.1001F, -0.1942F));
		PartDefinition leftWrist = leftShoulder.addOrReplaceChild("leftWrist", CubeListBuilder.create().texOffs(104, 22).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, -1.0472F, 0.0F, 0.0F));
		PartDefinition rightShoulder = upperBody.addOrReplaceChild("rightShoulder", CubeListBuilder.create().texOffs(104, 0).mirror().addBox(-3.0F, -2.0F, -3.0F, 6.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(-14.0F, -7.0F, 0.0F, -0.4702F, 0.1001F, 0.1942F));
		PartDefinition rightWrist = rightShoulder.addOrReplaceChild("rightWrist", CubeListBuilder.create().texOffs(104, 22).mirror().addBox(-3.0F, 0.0F, -3.0F, 6.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, -1.0472F, 0.0F, 0.0F));
		PartDefinition leftThigh = whole.addOrReplaceChild("leftThigh", CubeListBuilder.create().texOffs(80, 0).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(5.0F, -16.0F, 8.0F, -0.3491F, 0.0F, 0.0F));
		PartDefinition leftShank = leftThigh.addOrReplaceChild("leftShank", CubeListBuilder.create().texOffs(80, 17).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 9.0F, 0.0F, 0.3491F, 0.0F, 0.0F));
		PartDefinition rightThigh = whole.addOrReplaceChild("rightThigh", CubeListBuilder.create().texOffs(80, 0).mirror().addBox(-3.0F, -2.0F, -3.0F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(-5.0F, -16.0F, 8.0F, -0.3491F, 0.0F, 0.0F));
		PartDefinition rightShank = rightThigh.addOrReplaceChild("rightShank", CubeListBuilder.create().texOffs(80, 17).mirror().addBox(-3.0F, 0.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(0.0F, 9.0F, 0.0F, 0.3491F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	public void translateToHead(PoseStack pose) {
		whole.translateAndRotate(pose);
		lowerBody.translateAndRotate(pose);
		upperBody.translateAndRotate(pose);
		head.translateAndRotate(pose);
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

	public void prepareMobModel(T e, float f, float f1, float f2) {
		super.prepareMobModel(e, f, f1, f2);
		partialTicks = f2;
		slam = e.getSlamAnim(f2);
		shout = e.getShoutAnim(f2);
	}

	protected float pitch;
	protected float headYaw;
	protected float slam;
	protected float shout;

	@Override
	public void setupAnim(T e, float f, float f1, float f2, float f3, float f4) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		boolean flag = e.swingingArm == InteractionHand.MAIN_HAND;
		pitch = f4 * (pi / 180F);
		headYaw = f3 * (pi / 180F);
		setupAttackAnimation(e, flag);
		this.head.xRot += pitch;
		this.head.yRot += headYaw * 0.7f;
		this.upperBody.yRot += headYaw * 0.2f;
		this.lowerBody.yRot += headYaw * 0.1f;
		this.leftShoulder.xRot += Mth.sin(f * 0.6f) * f1;
		this.rightShoulder.xRot += -Mth.sin(f * 0.6f) * f1;
		this.leftThigh.xRot += -Mth.sin(f * 0.6f) * f1;
		this.rightThigh.xRot += Mth.sin(f * 0.6f) * f1;
		if (slam > 0) {
			if (slam < 0.25f) {
				float a = slam * 4;
				a = 1 - a * a;
				leftShoulder.xRot *= a;
				rightShoulder.xRot *= a;
				leftShoulder.xRot += -(1 - a) * 3;
				rightShoulder.xRot += -(1 - a) * 3;
				upperBody.xRot += -(1 - a) / 2;
				lowerBody.xRot += -(1 - a) / 2;
			} else if (slam < 0.5f) {
				float a = (slam - 0.25f) * 4;
				a = a * a * a;
				leftShoulder.xRot *= 0;
				rightShoulder.xRot *= 0;
				leftShoulder.xRot += -3 + a * 0.5f;
				rightShoulder.xRot += -3 + a * 0.5f;
				upperBody.xRot += -0.5f + a * 1.2f;
				lowerBody.xRot += -0.5f + a * 1.2f;
			} else if (slam < 0.75f) {
				float a = (slam - 0.5f) * 4;
				a = 1 - a * a * a;
				leftShoulder.xRot *= 0;
				rightShoulder.xRot *= 0;
				leftShoulder.xRot += -2.5f;
				rightShoulder.xRot += -2.5f;
				leftShoulder.y += (float) (0.5 - Math.random()) * a * 3;
				leftShoulder.z += (float) (0.5 - Math.random()) * a * 3;
				rightShoulder.y += (float) (0.5 - Math.random()) * a * 3;
				rightShoulder.z += (float) (0.5 - Math.random()) * a * 3;
				upperBody.xRot += 0.7f;
				upperBody.y += (float) (0.5 - Math.random()) * a;
				upperBody.z += (float) (0.5 - Math.random()) * a;
				lowerBody.xRot += 0.7f;
				lowerBody.y += (float) (0.5 - Math.random()) * a;
				lowerBody.z += (float) (0.5 - Math.random()) * a;
			} else {
				float a = (slam - 0.75f) * 4;
				a = a * a;
				leftShoulder.xRot *= a;
				rightShoulder.xRot *= a;
				leftShoulder.xRot += (-1 + a) * 2.5f;
				rightShoulder.xRot += (-1 + a) * 2.5f;
				upperBody.xRot += (1 - a) * 0.7f;
				lowerBody.xRot += (1 - a) * 0.7f;
			}
		}
		//setupAnim2(e, f, f1, f2, f3, f4);
	}

	protected void setupAttackAnimation(T e, boolean flag) {
		if (!(this.attackTime <= 0.0F)) {
			HumanoidArm humanoidarm = this.getAttackArm(e);
			ModelPart modelpart = flag ? this.rightShoulder : this.leftShoulder;
			float f = this.attackTime;
			this.upperBody.yRot = Mth.sin(Mth.sqrt(f) * ((float) Math.PI * 2F)) * 0.2F;
			if (humanoidarm == HumanoidArm.LEFT) {
				this.upperBody.yRot *= -1.0F;
			}
			this.rightShoulder.yRot += this.upperBody.yRot;
			this.leftShoulder.yRot += this.upperBody.yRot;
			this.leftShoulder.xRot += this.upperBody.yRot;
			f = 1.0F - this.attackTime;
			f = f * f * f;
			f = 1.0F - f;
			float f1 = Mth.sin(f * (float) Math.PI);
			float f2 = Mth.sin(this.attackTime * (float) Math.PI) * -(this.head.xRot - 0.7F) * 0.75F;
			modelpart.xRot -= f1 * 1.2F + f2;
			modelpart.yRot += this.upperBody.yRot * 2.0F;
			modelpart.zRot += Mth.sin(this.attackTime * (float) Math.PI) * -0.4F;
		}
	}

	/*
	public void setupAnim2(T e, float f, float f1, float f2, float f3, float f4) {
		this.head.xRot += pitch;
		this.head.yRot += headYaw * 0.7f;
		this.upperBody.yRot += headYaw * 0.2f;
		this.lowerBody.yRot += headYaw * 0.1f;
		this.leftShoulder.xRot += Mth.sin(f * 0.6f) * f1;
		this.rightShoulder.xRot += -Mth.sin(f * 0.6f) * f1;
		this.leftThigh.xRot += -Mth.sin(f * 0.6f) * f1;
		this.rightThigh.xRot += Mth.sin(f * 0.6f) * f1;
	}
	*/
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root().render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
