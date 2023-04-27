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
import net.minecraft.client.model.HierarchicalModel;

@OnlyIn(Dist.CLIENT)
public abstract class MonstrosityWithAnimations<T extends PathfinderMob & ICanBeAnimated> extends HierarchicalModel<T> {
	public void setupAnim(T e, float f, float f1, float f2, float f3, float f4) {
		this.animate(e.getAnimationState("slam"), MonstrosityAnimations.REDSTONE_MONSTROSITY_SLAM, f2, e.getAnimationSpeed());
		this.animate(e.getAnimationState("leftSlam"), MonstrosityAnimations.REDSTONE_MONSTROSITY_LEFTSLAM, f2, e.getAnimationSpeed());
		this.animate(e.getAnimationState("rightSlam"), MonstrosityAnimations.REDSTONE_MONSTROSITY_RIGHTSLAM, f2, e.getAnimationSpeed());
		this.animate(e.getAnimationState("insertArmsStart"), MonstrosityAnimations.REDSTONE_MONSTROSITY_INSERTINGARMSSTART, f2, e.getAnimationSpeed());
		this.animate(e.getAnimationState("insertArmsIdle"), MonstrosityAnimations.REDSTONE_MONSTROSITY_INSERTINGARMSIDLE, f2, e.getAnimationSpeed());
		this.animate(e.getAnimationState("insertArmsStop"), MonstrosityAnimations.REDSTONE_MONSTROSITY_INSERTINGARMSSTOP, f2, e.getAnimationSpeed());
		this.animate(e.getAnimationState("insertArmsStop2"), MonstrosityAnimations.REDSTONE_MONSTROSITY_INSERTARMSSTOP2, f2, e.getAnimationSpeed());
		this.animate(e.getAnimationState("shootStart"), MonstrosityAnimations.REDSTONE_MONSTROSITY_SHOOTSTART, f2, e.getAnimationSpeed());
		this.animate(e.getAnimationState("shootIdle"), MonstrosityAnimations.REDSTONE_MONSTROSITY_SHOOTIDLE, f2, e.getAnimationSpeed());
		this.animate(e.getAnimationState("shootStop"), MonstrosityAnimations.REDSTONE_MONSTROSITY_SHOOTSTOP, f2, e.getAnimationSpeed());
		this.animate(e.getAnimationState("fastShoot"), MonstrosityAnimations.REDSTONE_MONSTROSITY_FASTSHOOT, f2, e.getAnimationSpeed());
		this.animate(e.getAnimationState("push"), MonstrosityAnimations.REDSTONE_MONSTROSITY_PUSH, f2, e.getAnimationSpeed());
		this.animate(e.getAnimationState("leap"), MonstrosityAnimations.REDSTONE_MONSTROSITY_LEAP, f2, e.getAnimationSpeed());
		this.animate(e.getAnimationState("death_type1"), MonstrosityAnimations.REDSTONE_MONSTROSITY_DEATH_TYPE1, f2);
	}
}
