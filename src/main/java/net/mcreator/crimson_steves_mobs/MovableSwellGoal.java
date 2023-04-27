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

import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MovableSwellGoal extends Goal {
	public final Creeper creeper;
	public double distanceTo = 49;
	@Nullable
	private LivingEntity target;

	public MovableSwellGoal(Creeper p_25919_) {
		this.creeper = p_25919_;
	}

	public boolean canUse() {
		LivingEntity livingentity = this.creeper.getTarget();
		return this.creeper.getSwellDir() > 0 || livingentity != null && this.creeper.distanceToSqr(livingentity) < 9.0D;
	}

	public void start() {
		this.target = this.creeper.getTarget();
	}

	public void stop() {
		this.target = null;
	}

	public boolean requiresUpdateEveryTick() {
		return true;
	}

	public void tick() {
		if (this.target == null) {
			this.creeper.setSwellDir(-1);
		} else if (this.creeper.distanceToSqr(this.target) > distanceTo) {
			this.creeper.setSwellDir(-1);
		} else if (!this.creeper.getSensing().hasLineOfSight(this.target)) {
			this.creeper.setSwellDir(-1);
		} else {
			this.creeper.setSwellDir(1);
		}
	}
}
