
package net.mcreator.crimson_steves_mobs.item;

import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.network.chat.Component;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModTabs;
import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.entity.CreeckinEntity;

import java.util.List;

public class ExplosiveEggItem extends Item {
	public ExplosiveEggItem() {
		super(new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_ITEMS_OF_CSMM).stacksTo(16).rarity(Rarity.COMMON));
	}

	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
		if (!entity.level.isClientSide) {
			entity.getPersistentData().putInt("timeOnGround", entity.getPersistentData().getInt("timeOnGround") + 1);
			if (entity.getPersistentData().getInt("timeOnGround") > 200) {
				entity.playSound(SoundEvents.CHICKEN_EGG, 1.0F, 1);
				entity.gameEvent(GameEvent.ENTITY_PLACE);
				CreeckinEntity chicken1 = new CreeckinEntity(CrimsonStevesMobsModEntities.CREECKIN.get(), entity.level);
				chicken1.moveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), 0.0F);
				((ServerLevelAccessor) entity.level).addFreshEntity(chicken1);
				entity.discard();
			}
		}
		return false;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(Component.literal("Automatically hatches out after a minute when you drop it"));
	}
}
