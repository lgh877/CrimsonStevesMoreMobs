public class ModelAnimatedWardenPigModel<T extends PathfinderMob> extends HierarchicalModel<T> implements ArmedModel {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "animated_warden_pig_model"), "main");
	private final ModelPart root;
	protected final ModelPart body;
	protected final ModelPart head;
	protected final ModelPart leftLeg;
	protected final ModelPart leftArm;
	protected final ModelPart rightArm;
	protected final ModelPart rightLeg;

	public void translateToHand(HumanoidArm p_102925_, PoseStack pose) {
		body.translateAndRotate(pose);
		if (p_102925_ == HumanoidArm.LEFT) {
			leftArm.translateAndRotate(pose);
			pose.translate(0, -0.29, 0);
		} else {
			rightArm.translateAndRotate(pose);
			pose.translate(0, -0.29, 0);
		}
	}

	public ModelAnimatedWardenPigModel(ModelPart p_233512_) {
		super(RenderType::entityCutoutNoCull);
		this.root = p_233512_;
		this.body = this.root.getChild("body");
		this.head = this.body.getChild("head");
		this.rightLeg = this.root.getChild("right_leg");
		this.leftLeg = this.root.getChild("left_leg");
		this.rightArm = this.body.getChild("right_arm");
		this.leftArm = this.body.getChild("left_arm");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition body = partdefinition.addOrReplaceChild("body",
				CubeListBuilder.create().texOffs(28, 8).addBox(-5.0F, -16.0F, -5.0F, 10.0F, 16.0F, 8.0F),
				PartPose.offset(0.0F, 18.0F, 1.0F));
		body.addOrReplaceChild("head",
				CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F).texOffs(16, 16)
						.addBox(-2.0F, -4.0F, -5.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -15.0F, -3.0F));
		body.addOrReplaceChild("left_arm",
				CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F),
				PartPose.offset(2.9F, -14.0F, -5.0F));
		body.addOrReplaceChild("right_arm",
				CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F),
				PartPose.offset(-2.9F, -14.0F, -5.0F));
		partdefinition.addOrReplaceChild("left_leg",
				CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F),
				PartPose.offset(2.9F, 18.0F, 1.0F));
		partdefinition.addOrReplaceChild("right_leg",
				CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F),
				PartPose.offset(-2.9F, 18.0F, 1.0F));
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
		this.head.xRot = f4 * ((float) Math.PI / 180F) - 0.1745F;
		this.head.yRot = f3 * ((float) Math.PI / 180F);
		this.body.xRot = 0.1745F;
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
		this.leftLeg.xRot += 1.0F * f2 * f;
		this.rightLeg.xRot += 1.0F * Mth.cos(f1 + (float) Math.PI) * f;
		this.leftArm.xRot += -(0.8F * f2 * f) - 0.1745F;
		this.leftArm.zRot += 0.0F;
		this.rightArm.xRot += -(0.8F * f3 * f) - 0.1745F;
		this.rightArm.zRot += 0.0F;
	}
}
