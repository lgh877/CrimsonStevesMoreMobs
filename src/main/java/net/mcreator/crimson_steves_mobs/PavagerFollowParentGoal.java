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

import net.minecraft.world.entity.ai.goal.Goal;

import net.mcreator.crimson_steves_mobs.entity.PavagerEntity;

import javax.annotation.Nullable;

import java.util.List;

public class PavagerFollowParentGoal extends Goal {
	public static final int HORIZONTAL_SCAN_RANGE = 8;
	public static final int VERTICAL_SCAN_RANGE = 4;
	public static final int DONT_FOLLOW_IF_CLOSER_THAN = 3;
	private final PavagerEntity animal;
	@Nullable
	private PavagerEntity parent;
	private final double speedModifier;
	private int timeToRecalcPath;

	public PavagerFollowParentGoal(PavagerEntity p_25319_, double p_25320_) {
		this.animal = p_25319_;
		this.speedModifier = p_25320_;
	}

	public boolean canUse() {
		if (this.animal.getAge() >= 0) {
			return false;
		} else {
			List<? extends PavagerEntity> list = this.animal.level.getEntitiesOfClass(this.animal.getClass(),
					this.animal.getBoundingBox().inflate(8.0D, 4.0D, 8.0D));
			PavagerEntity animal = null;
			double d0 = Double.MAX_VALUE;
			for (PavagerEntity animal1 : list) {
				if (animal1.getAge() >= 0) {
					double d1 = this.animal.distanceToSqr(animal1);
					if (!(d1 > d0)) {
						d0 = d1;
						animal = animal1;
					}
				}
			}
			if (animal == null) {
				return false;
			} else if (d0 < 9.0D) {
				return false;
			} else {
				this.parent = animal;
				return true;
			}
		}
	}

	public boolean canContinueToUse() {
		if (this.animal.getAge() >= 0) {
			return false;
		} else if (!this.parent.isAlive()) {
			return false;
		} else {
			double d0 = this.animal.distanceToSqr(this.parent);
			return !(d0 < 9.0D) && !(d0 > 256.0D);
		}
	}

	public void start() {
		this.timeToRecalcPath = 0;
	}

	public void stop() {
		this.parent = null;
	}

	public void tick() {
		if (--this.timeToRecalcPath <= 0) {
			this.timeToRecalcPath = this.adjustedTickDelay(10);
			this.animal.getNavigation().moveTo(this.parent, this.speedModifier);
		}
	}
}
