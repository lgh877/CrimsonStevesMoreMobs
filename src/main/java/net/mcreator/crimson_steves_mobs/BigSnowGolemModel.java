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

import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;

import net.mcreator.crimson_steves_mobs.entity.BigSnowGolemEntity;

@OnlyIn(Dist.CLIENT)
public class BigSnowGolemModel<T extends BigSnowGolemEntity> extends AnimatedRedstoneGolemModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("workspace", "big_snow_golem"), "main");

	public BigSnowGolemModel(ModelPart p_170889_) {
		super(p_170889_);
	}

	public void setupAnim(T e, float f, float f1, float f2, float f3, float f4) {
		super.setupAnim(e, f, f1, f2, f3, f4);
		float pitch = f4 * (pi / 180F);
		float headYaw = f3 * (pi / 180F);
		boolean flag1 = e.getMainArm() == HumanoidArm.RIGHT;
		if (e.isShooting()) {
			this.upperBody.x += (float) (Math.random() - 0.5) * 4;
			this.upperBody.z += (float) (Math.random() - 0.5) * 4;
			if (flag1) {
				this.animateShootAnim(this.leftShoulder, this.leftWrist, pitch, headYaw, e, f2);
			} else {
				this.animateShootAnim(this.rightShoulder, this.rightWrist, pitch, headYaw, e, f2);
			}
		}
	}

	private void animateShootAnim(ModelPart shoulder, ModelPart wrist, float pitch, float yaw, T e, float f2) {
		shoulder.xRot += -pi / 6 + pitch / 3;
		wrist.xRot = -shoulder.xRot + pitch - pi / 2;
		wrist.yRot = -shoulder.yRot + yaw * 0.5f;
		wrist.x += (float) (Math.random() - 0.5) * 4;
		wrist.y += (float) (Math.random() - 0.5) * 4;
		wrist.z += (float) (Math.random() - 0.5) * 4;
	}
}
