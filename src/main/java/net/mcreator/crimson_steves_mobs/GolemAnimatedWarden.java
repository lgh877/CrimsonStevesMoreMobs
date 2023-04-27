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

import net.minecraft.world.entity.Mob;
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

import java.util.List;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

import com.google.common.collect.ImmutableList;

@OnlyIn(Dist.CLIENT)
public class GolemAnimatedWarden<T extends Mob> extends HierarchicalModel<T> implements ArmedModel {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("crimson_steves_mobs", "golem_animated_warden_model"), "main");
	private static final float DEFAULT_ARM_X_Y = 13.0F;
	private static final float DEFAULT_ARM_Z = 1.0F;
	public final ModelPart root;
	protected final ModelPart bone;
	protected final ModelPart body;
	protected final ModelPart head;
	protected final ModelPart rightTendril;
	protected final ModelPart leftTendril;
	protected final ModelPart leftLeg;
	protected final ModelPart leftArm;
	protected final ModelPart leftRibcage;
	protected final ModelPart rightArm;
	protected final ModelPart rightLeg;
	protected final ModelPart rightRibcage;
	private final List<ModelPart> tendrilsLayerModelParts;
	private final List<ModelPart> heartLayerModelParts;
	private final List<ModelPart> bioluminescentLayerModelParts;
	private final List<ModelPart> pulsatingSpotsLayerModelParts;
	protected final float pi = (float) Math.PI;

	public void translateToHand(HumanoidArm p_102925_, PoseStack pose) {
		root.translateAndRotate(pose);
		bone.translateAndRotate(pose);
		body.translateAndRotate(pose);
		if (p_102925_ == HumanoidArm.LEFT) {
			leftArm.translateAndRotate(pose);
			pose.translate(-0.1, 0, 0);
		} else {
			rightArm.translateAndRotate(pose);
			pose.translate(0.1, 0, 0);
		}
	}

	public GolemAnimatedWarden(ModelPart p_233512_) {
		super(RenderType::entityCutoutNoCull);
		this.root = p_233512_;
		this.bone = p_233512_.getChild("bone");
		this.body = this.bone.getChild("body");
		this.head = this.body.getChild("head");
		this.rightLeg = this.bone.getChild("right_leg");
		this.leftLeg = this.bone.getChild("left_leg");
		this.rightArm = this.body.getChild("right_arm");
		this.leftArm = this.body.getChild("left_arm");
		this.rightTendril = this.head.getChild("right_tendril");
		this.leftTendril = this.head.getChild("left_tendril");
		this.rightRibcage = this.body.getChild("right_ribcage");
		this.leftRibcage = this.body.getChild("left_ribcage");
		this.tendrilsLayerModelParts = ImmutableList.of(this.leftTendril, this.rightTendril);
		this.heartLayerModelParts = ImmutableList.of(this.body);
		this.bioluminescentLayerModelParts = ImmutableList.of(this.head, this.leftArm, this.rightArm, this.leftLeg, this.rightLeg);
		this.pulsatingSpotsLayerModelParts = ImmutableList.of(this.body, this.head, this.leftArm, this.rightArm, this.leftLeg, this.rightLeg,
				this.leftRibcage, this.rightRibcage);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition partdefinition1 = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition partdefinition2 = partdefinition1.addOrReplaceChild("body",
				CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -13.0F, -4.0F, 18.0F, 21.0F, 11.0F), PartPose.offset(0.0F, -21.0F, 0.0F));
		partdefinition2.addOrReplaceChild("right_ribcage", CubeListBuilder.create().texOffs(90, 11).addBox(-2.0F, -11.0F, -0.1F, 9.0F, 21.0F, 0.0F),
				PartPose.offset(-7.0F, -2.0F, -4.0F));
		partdefinition2.addOrReplaceChild("left_ribcage",
				CubeListBuilder.create().texOffs(90, 11).mirror().addBox(-7.0F, -11.0F, -0.1F, 9.0F, 21.0F, 0.0F).mirror(false),
				PartPose.offset(7.0F, -2.0F, -4.0F));
		PartDefinition partdefinition3 = partdefinition2.addOrReplaceChild("head",
				CubeListBuilder.create().texOffs(0, 32).addBox(-8.0F, -16.0F, -5.0F, 16.0F, 16.0F, 10.0F), PartPose.offset(0.0F, -13.0F, 0.0F));
		partdefinition3.addOrReplaceChild("right_tendril", CubeListBuilder.create().texOffs(52, 32).addBox(-16.0F, -13.0F, 0.0F, 16.0F, 16.0F, 0.0F),
				PartPose.offset(-8.0F, -12.0F, 0.0F));
		partdefinition3.addOrReplaceChild("left_tendril", CubeListBuilder.create().texOffs(58, 0).addBox(0.0F, -13.0F, 0.0F, 16.0F, 16.0F, 0.0F),
				PartPose.offset(8.0F, -12.0F, 0.0F));
		partdefinition2.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(44, 50).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 28.0F, 8.0F),
				PartPose.offset(-13.0F, -13.0F, 1.0F));
		partdefinition2.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 58).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 28.0F, 8.0F),
				PartPose.offset(13.0F, -13.0F, 1.0F));
		partdefinition1.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(76, 48).addBox(-3.1F, 0.0F, -3.0F, 6.0F, 13.0F, 6.0F),
				PartPose.offset(-5.9F, -13.0F, 0.0F));
		partdefinition1.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(76, 76).addBox(-2.9F, 0.0F, -3.0F, 6.0F, 13.0F, 6.0F),
				PartPose.offset(5.9F, -13.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	public void renderToBuffer(PoseStack p_102034_, VertexConsumer p_102035_, int p_102036_, int p_102037_, float p_102038_, float p_102039_,
			float p_102040_, float p_102041_) {
		super.renderToBuffer(p_102034_, p_102035_, p_102036_, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);
	}

	public void setupAnim(T e, float f, float f1, float f2, float f3, float f4) {
		this.root().getAllParts().forEach((model) -> {
			model.xRot = 0;
			model.yRot = 0;
			model.zRot = 0;
		});
		float partialTicks = f2 - (float) e.tickCount;
		float shiftDownAmount = e.getPersistentData().getFloat("ShiftDownAmount");
		if (e.isSprinting()) {
			f2 *= 3;
			f1 *= 1.5f;
		}
		this.root.zRot = Mth.sin(f * 0.5f) * f1 * 0.1f;
		this.root.xRot = (Mth.sin(f) + 1) * f1 * 0.08f;
		this.body.xRot += ((Mth.sin(f) + 1) * f1 + 1) * 0.08f;
		this.head.xRot += -((Mth.sin(f) + 1) * f1 + 0.5f) * 0.16f;
		this.leftArm.xRot += this.head.xRot - f1 / 2;
		this.rightArm.xRot += this.head.xRot - f1 / 2;
		this.animateHeadLookTarget(f3, f4);
		this.animateIdlePose(f2);
		this.animateTendrils(e, f2, partialTicks);
		leftLeg.setPos(5.9F, -13.0F, 0.0F);
		rightLeg.setPos(-5.9F, -13.0F, 0.0F);
		if (shiftDownAmount > 0) {
			this.leftRibcage.yRot -= pi * shiftDownAmount / 3;
			this.rightRibcage.yRot += pi * shiftDownAmount / 3;
		}
		// this.animate(p_233531_.attackAnimationState, WardenAnimation.WARDEN_ATTACK,
		// p_233534_);
		// this.animate(p_233531_.sonicBoomAnimationState,
		// WardenAnimation.WARDEN_SONIC_BOOM, p_233534_);
		// this.animate(p_233531_.diggingAnimationState, WardenAnimation.WARDEN_DIG,
		// p_233534_);
		// this.animate(p_233531_.emergeAnimationState, WardenAnimation.WARDEN_EMERGE,
		// p_233534_);
		// this.animate(p_233531_.roarAnimationState, WardenAnimation.WARDEN_ROAR,
		// p_233534_);
		// this.animate(p_233531_.sniffAnimationState, WardenAnimation.WARDEN_SNIFF,
		// p_233534_);
		if (this.riding) {
			this.rightArm.xRot += (-pi / 5F);
			this.leftArm.xRot += (-pi / 5F);
			this.rightLeg.xRot += -1.4137167F;
			this.rightLeg.yRot += (pi / 10F);
			this.rightLeg.zRot += 0.07853982F;
			this.leftLeg.xRot += -1.4137167F;
			this.leftLeg.yRot += (-pi / 10F);
			this.leftLeg.zRot += -0.07853982F;
		} else {
			this.leftLeg.xRot += Mth.cos(f * 0.5f + pi * 1.4f) * f1 * 0.4f;
			this.leftLeg.y += Mth.clamp(Mth.sin(f * 0.5f) * f1 * 3, -Float.POSITIVE_INFINITY, 0);
			this.leftLeg.z += Mth.cos(f * 0.5f + pi) * f1 * 3;
			this.rightLeg.xRot += Mth.cos(f * 0.5F + pi * 0.4f) * f1 * 0.4f;
			this.rightLeg.y += Mth.clamp(Mth.sin(f * 0.5f + pi) * f1 * 3, -Float.POSITIVE_INFINITY, 0);
			this.rightLeg.z += Mth.cos(f * 0.5f) * f1 * 3;
			if (e.isSprinting()) {
				this.animateWalk(f, f1);
			}
		}
		if (this.attackTime > 0) {
			float a = 1 - this.attackTime;
			a = a * a;
			float c = Mth.sin(a * pi);
			this.leftArm.xRot -= c * 2.5f;
			this.rightArm.xRot -= c * 2.5f;
		}
	}

	private void animateWalk(float p_233539_, float p_233540_) {
		float f = Math.min(0.5F, 3.0F * p_233540_);
		float f1 = p_233539_ * 0.5F;
		float f2 = Mth.cos(f1);
		float f3 = Mth.sin(f1);
		float f4 = Math.min(0.35F, f);
		this.head.zRot += 0.3F * f3 * f;
		this.head.xRot += 1.2F * Mth.cos(f1 + ((float) Math.PI / 2F)) * f4;
		this.leftTendril.yRot += f3 * f;
		this.rightTendril.yRot += -f3 * f;
		this.body.zRot += 0.1F * f3 * f;
		this.body.xRot += 1.0F * f2 * f4;
		this.leftLeg.xRot += 0.5F * f2 * f;
		this.rightLeg.xRot += 0.5F * Mth.cos(f1 + (float) Math.PI) * f;
		this.leftArm.xRot += -(0.4F * f2 * f);
		this.rightArm.xRot += -(0.4F * f3 * f);
	}

	private void animateHeadLookTarget(float p_233517_, float p_233518_) {
		this.head.xRot = p_233518_ * (pi / 180F);
		this.head.yRot = p_233517_ * (pi / 180F);
	}

	private void animateIdlePose(float p_233515_) {
		float f = p_233515_ * 0.1F;
		float f1 = Mth.cos(f);
		float f2 = Mth.sin(f);
		this.head.zRot += 0.06F * f1;
		this.head.xRot += 0.06F * f2;
		this.leftTendril.xRot += 0.16F * f1;
		this.rightTendril.xRot += -0.16F * f1;
		this.body.zRot += 0.025F * f2;
		this.body.xRot += 0.025F * f1;
	}

	private void animateTendrils(T p_233527_, float p_233528_, float p_233529_) {
		if (p_233527_.hurtTime > 0) {
			float f = 0;
			f = Mth.sin(Mth.triangleWave((float) p_233527_.hurtTime - p_233529_, 10.0F) * pi);
			this.leftTendril.zRot += f;
			this.rightTendril.zRot += -f;
		}
	}

	public ModelPart root() {
		return this.root;
	}

	public List<ModelPart> getTendrilsLayerModelParts() {
		return this.tendrilsLayerModelParts;
	}

	public List<ModelPart> getHeartLayerModelParts() {
		return this.heartLayerModelParts;
	}

	public List<ModelPart> getBioluminescentLayerModelParts() {
		return this.bioluminescentLayerModelParts;
	}

	public List<ModelPart> getPulsatingSpotsLayerModelParts() {
		return this.pulsatingSpotsLayerModelParts;
	}
}
