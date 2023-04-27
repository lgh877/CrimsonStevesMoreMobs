
package net.mcreator.crimson_steves_mobs.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundEvents;

import net.mcreator.crimson_steves_mobs.entity.HardenedSnowBallEntity;

public class HardenedSnowBallItem extends Item {
	public HardenedSnowBallItem() {
		super(new Item.Properties().tab(null).durability(1000));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		entity.startUsingItem(hand);
		return new InteractionResultHolder(InteractionResult.SUCCESS, entity.getItemInHand(hand));
	}

	@Deprecated
	public void onUseTick(Level world, LivingEntity entityLiving, ItemStack itemstack, int p_41431_) {
		if (!world.isClientSide()) {
			double x = entityLiving.getX();
			double y = entityLiving.getY();
			double z = entityLiving.getZ();
			HardenedSnowBallEntity.shoot(world, entityLiving, 0.1f);
			HardenedSnowBallEntity.shoot(world, entityLiving, 0.25f);
			HardenedSnowBallEntity.shoot(world, entityLiving, 0.4f);
			itemstack.hurtAndBreak(1, entityLiving, e -> e.broadcastBreakEvent(entityLiving.getUsedItemHand()));
		}
		if (p_41431_ % 20 == 0)
			entityLiving.playSound(SoundEvents.ELYTRA_FLYING, (float) (Math.random() * 1.5) + 0.5f, 2);
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemstack) {
		return UseAnim.BOW;
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 72000;
	}
}
