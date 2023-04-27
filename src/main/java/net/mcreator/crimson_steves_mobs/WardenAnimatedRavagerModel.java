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
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.HierarchicalModel;

@OnlyIn(Dist.CLIENT)
public class WardenAnimatedRavagerModel<T extends PathfinderMob> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("workspace", "warden_animated_ravager_model"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart mouth;
	private final ModelPart body;
	private final ModelPart rightHindLeg;
	private final ModelPart leftHindLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftFrontLeg;
	private final ModelPart neck;
	protected final float pi = (float) Math.PI;

	public WardenAnimatedRavagerModel(ModelPart p_170889_) {
		this.root = p_170889_;
		this.neck = p_170889_.getChild("neck");
		this.body = p_170889_.getChild("body");
		this.head = this.neck.getChild("head");
		this.mouth = this.head.getChild("mouth");
		this.rightHindLeg = p_170889_.getChild("right_leg");
		this.leftHindLeg = p_170889_.getChild("left_leg");
		this.rightFrontLeg = p_170889_.getChild("right_arm");
		this.leftFrontLeg = p_170889_.getChild("left_arm");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		int i = 16;
		PartDefinition partdefinition1 = partdefinition.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(68, 73).addBox(-5.0F, -1.0F, -18.0F, 10.0F, 10.0F, 18.0F), PartPose.offset(0.0F, -7.0F, 5.5F));
		PartDefinition partdefinition2 = partdefinition1.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -20.0F, -14.0F, 16.0F, 20.0F, 16.0F).texOffs(0, 0).addBox(-2.0F, -6.0F, -18.0F, 4.0F, 8.0F, 4.0F),
				PartPose.offset(0.0F, 16.0F, -17.0F));
		partdefinition2.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(74, 55).addBox(0.0F, -14.0F, -2.0F, 2.0F, 14.0F, 4.0F), PartPose.offsetAndRotation(-10.0F, -14.0F, -8.0F, 1.0995574F, 0.0F, 0.0F));
		partdefinition2.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(74, 55).mirror().addBox(0.0F, -14.0F, -2.0F, 2.0F, 14.0F, 4.0F), PartPose.offsetAndRotation(8.0F, -14.0F, -8.0F, 1.0995574F, 0.0F, 0.0F));
		partdefinition2.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(0, 36).addBox(-8.0F, 0.0F, -16.0F, 16.0F, 3.0F, 16.0F), PartPose.offset(0.0F, -2.0F, 2.0F));
		partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 55).addBox(-7.0F, -10.0F, -7.0F, 14.0F, 16.0F, 20.0F).texOffs(0, 91).addBox(-6.0F, 6.0F, -7.0F, 12.0F, 13.0F, 18.0F),
				PartPose.offsetAndRotation(0.0F, 1.0F, 2.0F, ((float) Math.PI / 2F), 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(96, 0).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F), PartPose.offset(-8.0F, -13.0F, 18.0F));
		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(96, 0).mirror().addBox(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F), PartPose.offset(8.0F, -13.0F, 18.0F));
		partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(64, 0).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F), PartPose.offset(-8.0F, -13.0F, -5.0F));
		partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(64, 0).mirror().addBox(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F), PartPose.offset(8.0F, -13.0F, -5.0F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	public ModelPart root() {
		return this.root;
	}

	public void setupAnim(T e, float f, float f1, float f2, float f3, float f4) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.xRot += f4 * (pi / 180F);
		this.head.yRot += f3 * (pi / 180F);
		//float f = Math.min((float) p_103626_.getDeltaMovement().lengthSqr() * 25.0F, 1F);
		this.animateIdlePose(f2);
		this.leftFrontLeg.xRot += Mth.cos(f * 0.5f + pi * 1.4f) * f1 * 0.3f;
		this.leftFrontLeg.y += Mth.clamp(Mth.sin(f * 0.5f) * f1 * 8, -Float.POSITIVE_INFINITY, 1);
		this.leftFrontLeg.z += Mth.cos(f * 0.5f + pi) * f1 * 8;
		this.rightFrontLeg.xRot += Mth.cos(f * 0.5F + pi * 0.4f) * f1 * 0.3f;
		this.rightFrontLeg.y += Mth.clamp(Mth.sin(f * 0.5f + pi) * f1 * 8, -Float.POSITIVE_INFINITY, 1);
		this.rightFrontLeg.z += Mth.cos(f * 0.5f) * f1 * 8;
		this.leftHindLeg.xRot += Mth.cos(f * 0.5F + pi * 0.4f) * f1 * 0.3f;
		this.leftHindLeg.y += Mth.clamp(Mth.sin(f * 0.5f + pi) * f1 * 8, -Float.POSITIVE_INFINITY, 1);
		this.leftHindLeg.z += Mth.cos(f * 0.5f) * f1 * 8;
		this.rightHindLeg.xRot += Mth.cos(f * 0.5f + pi * 1.4f) * f1 * 0.3f;
		this.rightHindLeg.y += Mth.clamp(Mth.sin(f * 0.5f) * f1 * 8, -Float.POSITIVE_INFINITY, 1);
		this.rightHindLeg.z += Mth.cos(f * 0.5f + pi) * f1 * 8;
		if (this.attackTime > 0) {
			float a = 1 - this.attackTime;
			a = a * a * a;
			this.neck.z += -Mth.sin(a * pi) * 16;
			this.mouth.xRot += Mth.sin(a * pi);
		}
		/*
		this.rightHindLeg.xRot += Mth.cos(f * 0.6662F) * f6;
		this.leftHindLeg.xRot += Mth.cos(f * 0.6662F + (float) Math.PI) * f6;
		this.rightFrontLeg.xRot += Mth.cos(f * 0.6662F + (float) Math.PI) * f6;
		this.leftFrontLeg.xRot += Mth.cos(f * 0.6662F) * f6;
		*/
		float shiftDownAmount = e.getPersistentData().getFloat("ShiftDownAmount");
		if (shiftDownAmount > 0) {
			this.neck.x += (float) (Math.random() - 0.5) * 3 * shiftDownAmount;
			this.neck.y += (float) (Math.random() - 0.5) * 3 * shiftDownAmount;
			this.neck.z -= 8 * shiftDownAmount;
			this.head.xRot -= (float) Math.PI * shiftDownAmount / 6;
			this.mouth.xRot += (float) Math.PI * shiftDownAmount / 3;
		}
	}

	private void animateIdlePose(float p_233515_) {
		float f = p_233515_ * 0.1F;
		float f1 = Mth.cos(f);
		float f2 = Mth.sin(f);
		this.head.zRot += 0.06F * f1;
		this.head.xRot += 0.06F * f2;
		this.mouth.xRot += 0.2F * f2 + 0.2f;
		this.neck.zRot += 0.06F * f1;
		this.neck.xRot += 0.06F * f2;
		this.body.zRot += 0.025F * f2;
		this.body.xRot += 0.025F * f1;
	}
}
