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

import net.minecraft.world.entity.monster.AbstractIllager;
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

import net.mcreator.crimson_steves_mobs.entity.CyborgVindicatorEntity;

@OnlyIn(Dist.CLIENT)
public class ChadVindicatorModel<T extends CyborgVindicatorEntity> extends AnimatedRedstoneGolemModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("workspace", "chad_vindicator"), "main");

	public ChadVindicatorModel(ModelPart p_170889_) {
		super(p_170889_);
	}

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
		PartDefinition head = upperBody.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -10.75F, -12.0F, 12.0F, 15.0F, 12.0F).texOffs(0, 0).addBox(-1.5F, 0.0F, -15.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -24.2F, -11.6F));
		PartDefinition head_r1 = head.addOrReplaceChild("head_r", CubeListBuilder.create(), PartPose.offset(0.0F, -24.2F, -11.6F));
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

	public void setupAnim(T e, float f, float f1, float f2, float f3, float f4) {
		if (!e.isRaged())
			this.attackTime = (float) Math.pow(this.attackTime, Mth.clamp((100 - e.getSize()) / 100, 0.5, 1));
		AbstractIllager.IllagerArmPose armpose = e.getArmPose();
		if (armpose == AbstractIllager.IllagerArmPose.ATTACKING) {
			f2 *= 20;
			f *= 2;
			f1 *= 2;
		}
		super.setupAnim(e, f, f1, f2, f3, f4);
		this.head.zRot = 0;
		if (e.ambientSoundTime < -60) {
			float a = 1 - (80 + e.ambientSoundTime + f2 - e.tickCount) / 20;
			a = a * a * a;
			this.head.zRot += 0.5f * Mth.sin(a * (float) Math.PI * 2);
		}
		if (armpose == AbstractIllager.IllagerArmPose.ATTACKING) {
			f2 *= 3;
			this.redstoneBlock.x += (float) (Math.random() - 0.5) * 3;
			this.redstoneBlock.y += (float) (Math.random() - 0.5) * 3;
			this.redstoneBlock.z += (Mth.sin(f2 * 1.7f) + 1) * 2;
			this.leftShoulder.xRot = this.rightShoulder.xRot;
			this.leftShoulder.yRot = this.rightShoulder.yRot;
			this.leftShoulder.zRot = -this.rightShoulder.zRot;
			this.head.xRot += (float) (Math.random() - 0.5) * 0.3f;
			this.head.yRot += (float) (Math.random() - 0.5) * 0.3f;
			this.head.zRot += (float) (Math.random() - 0.5) * 0.3f;
		}
		if (armpose == AbstractIllager.IllagerArmPose.CELEBRATING) {
			this.rightShoulder.zRot += (this.pi / 180F) * (70.0F + Mth.cos(f2) * 10.0F);
			this.rightShoulder.xRot += Mth.sin(f2);
			this.rightWrist.xRot += Mth.sin(f2);
			this.leftShoulder.zRot = this.rightShoulder.zRot * -1.0F;
			this.leftShoulder.xRot += Mth.sin(f2 + this.pi);
			this.leftWrist.xRot += Mth.sin(f2 + this.pi);
		}
	}
}
