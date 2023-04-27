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

import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.Mob;
import net.minecraft.util.Mth;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SmartBodyHelper2 extends BodyRotationControl {
	private final Mob mob;
	private static final float MAX_ROTATE = 75;
	private int rotationTickCounter;
	private static final int HISTORY_SIZE = 10;
	private float prevRenderYawHead;
	private final double[] histPosX = new double[HISTORY_SIZE];
	private final double[] histPosZ = new double[HISTORY_SIZE];

	public SmartBodyHelper2(Mob mob) {
		super(mob);
		this.mob = mob;
	}

	@Override
	public void clientTick() {
		for (int i = histPosX.length - 1; i > 0; i--) {
			histPosX[i] = histPosX[i - 1];
			histPosZ[i] = histPosZ[i - 1];
		}
		if (this.hasMoved()) {
			this.mob.yBodyRot = this.mob.getYRot();
			this.func_220664_c();
			this.prevRenderYawHead = this.mob.yHeadRot;
			this.rotationTickCounter = 0;
		} else {
			if (this.noMobPassengers()) {
				if (Math.abs(this.mob.yHeadRot - this.prevRenderYawHead) > 15.0F) {
					this.rotationTickCounter = 0;
					this.prevRenderYawHead = this.mob.yHeadRot;
					this.func_220663_b();
				} else {
					float limit = MAX_ROTATE;
					++this.rotationTickCounter;
					final int speed = 10;
					if (this.rotationTickCounter > speed) {
						limit = Math.max(1 - (rotationTickCounter - speed) / (float) speed, 0) * MAX_ROTATE;
					}
					mob.yBodyRot = approach(mob.yHeadRot, mob.yBodyRot, limit);// https://gist.github.com/TheGreyGhost/b5ea2acd1c651a2d6350#file-gistfile1-txt-L60
				}
			}
		}
	}

	private void func_220663_b() {
		this.mob.yBodyRot = Mth.rotateIfNecessary(this.mob.yBodyRot, this.mob.yHeadRot, (float) this.mob.getMaxHeadYRot());
	}

	private void func_220664_c() {
		this.mob.yHeadRot = Mth.rotateIfNecessary(this.mob.yHeadRot, this.mob.yBodyRot, (float) this.mob.getMaxHeadYRot());
	}

	private boolean noMobPassengers() {
		return this.mob.getPassengers().isEmpty() || !(this.mob.getPassengers().get(0) instanceof Mob);
	}

	private boolean hasMoved() {
		double d0 = this.mob.getX() - this.mob.xo;
		double d1 = this.mob.getZ() - this.mob.zo;
		return d0 * d0 + d1 * d1 > (double) 2.5000003E-7F;
	}

	public static float approach(float target, float current, float limit) {
		float delta = Mth.wrapDegrees(current - target);
		if (delta < -limit) {
			delta = -limit;
		} else if (delta >= limit) {
			delta = limit;
		}
		return target + delta * 0.55F;
	}
}
