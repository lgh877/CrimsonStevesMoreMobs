package net.mcreator.crimson_steves_mobs.client.model;

import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.EntityModel;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

// Made with Blockbench 4.4.3
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports
public class Modelredstone_grandarmor<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("crimson_steves_mobs", "modelredstone_grandarmor"), "main");
	public final ModelPart Right_shoe;
	public final ModelPart Left_shoe;
	public final ModelPart leggingsRight;
	public final ModelPart leggingsLeft;
	public final ModelPart right_shoulderpad;
	public final ModelPart left_shoulderpad;
	public final ModelPart body;
	public final ModelPart head;

	public Modelredstone_grandarmor(ModelPart root) {
		this.Right_shoe = root.getChild("Right_shoe");
		this.Left_shoe = root.getChild("Left_shoe");
		this.leggingsRight = root.getChild("leggingsRight");
		this.leggingsLeft = root.getChild("leggingsLeft");
		this.right_shoulderpad = root.getChild("right_shoulderpad");
		this.left_shoulderpad = root.getChild("left_shoulderpad");
		this.body = root.getChild("body");
		this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition Right_shoe = partdefinition.addOrReplaceChild("Right_shoe", CubeListBuilder.create().texOffs(24, 32).mirror().addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false),
				PartPose.offset(-1.9F, 12.0F, 0.0F));
		PartDefinition Left_shoe = partdefinition.addOrReplaceChild("Left_shoe", CubeListBuilder.create().texOffs(24, 32).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(1.9F, 12.0F, 0.0F));
		PartDefinition leggingsRight = partdefinition.addOrReplaceChild("leggingsRight", CubeListBuilder.create().texOffs(0, 34).mirror().addBox(-2.1F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false),
				PartPose.offset(-1.9F, 12.0F, 0.0F));
		PartDefinition leggingsLeft = partdefinition.addOrReplaceChild("leggingsLeft", CubeListBuilder.create().texOffs(0, 34).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(1.9F, 12.0F, 0.0F));
		PartDefinition right_shoulderpad = partdefinition.addOrReplaceChild("right_shoulderpad", CubeListBuilder.create().texOffs(32, 0).mirror().addBox(-4.0F, -4.0F, -3.0F, 5.0F, 7.0F, 6.0F, new CubeDeformation(0.3F)).mirror(false).texOffs(10, 42)
				.mirror().addBox(-6.5F, -1.0F, -3.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offset(-5.0F, 2.0F, 0.0F));
		PartDefinition left_shoulderpad = partdefinition.addOrReplaceChild("left_shoulderpad",
				CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -4.0F, -3.0F, 5.0F, 7.0F, 6.0F, new CubeDeformation(0.3F)).texOffs(10, 42).addBox(4.5F, -1.0F, -3.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.3F)),
				PartPose.offset(5.0F, 2.0F, 0.0F));
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition outer_chestplate = body.addOrReplaceChild("outer_chestplate",
				CubeListBuilder.create().texOffs(0, 17).addBox(14.0F, 0.0F, -2.0F, 9.0F, 12.0F, 5.0F, new CubeDeformation(0.25F)).texOffs(0, 58).addBox(16.0F, 2.0F, 3.0F, 5.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-18.5F, 0.0F, -0.5F));
		PartDefinition inner_chestplate = body.addOrReplaceChild("inner_chestplate", CubeListBuilder.create().texOffs(28, 13).addBox(-18.0F, -3.0F, -2.0F, 8.0F, 15.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(14.0F, 3.0F, 0.0F));
		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -9.0F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.6F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition left_horn = head.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(35, 44).mirror().addBox(2.5F, -35.7738F, -6.2174F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(40, 32).mirror()
				.addBox(3.5F, -38.75F, -6.25F, 2.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 28.0F, 4.0F));
		PartDefinition right_horn = head.addOrReplaceChild("right_horn",
				CubeListBuilder.create().texOffs(35, 44).addBox(-3.5F, -35.7738F, -6.2174F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(40, 32).addBox(-5.5F, -38.75F, -6.25F, 2.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-2.0F, 28.0F, 4.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Right_shoe.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Left_shoe.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leggingsRight.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leggingsLeft.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_shoulderpad.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		left_shoulderpad.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
