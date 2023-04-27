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

import net.minecraft.world.entity.AnimationState;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public interface ICanBeAnimated {
	AnimationState getAnimationState(String input);

	default float getAnimationSpeed(String input) {
		return 1f;
	}

	default float getAnimationSpeed() {
		return 1f;
	}

	default int maxSwingTime() {
		return 1;
	}
}
