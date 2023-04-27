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

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.Mob;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import java.util.EnumSet;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public abstract class LockAngleGoal<T extends Mob> extends Goal {
	public final T mob;

	public LockAngleGoal(T mob) {
		this.mob = mob;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	public void start() {
		mob.getNavigation().stop();
		if (mob.getTarget() != null) {
			mob.lookAt(EntityAnchorArgument.Anchor.FEET, mob.getTarget().position());
		}
	}
}
