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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.entity.LivingEntity;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VehicleAbsorbDamageGoal {
	@SubscribeEvent
	public static void onEntityHurt(LivingHurtEvent event) {
		LivingEntity entity = event.getEntity();
		if ((entity.getRootVehicle() instanceof IProtectiveVehicle) && entity.isPassenger()) {
			if (event.getSource().getEntity() == entity.getRootVehicle() && entity.isAlliedTo(entity.getRootVehicle()))
				event.setCanceled(true);
			else {
				((LivingEntity) entity.getRootVehicle()).hurt(event.getSource(), event.getAmount());
				event.setCanceled(true);
			}
		}
	}
}
