
package net.mcreator.crimson_steves_mobs.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerPlayer;

import net.mcreator.crimson_steves_mobs.entity.EarthQuakeEntity;

import java.util.function.Predicate;

public class EarthQuakeItem extends ProjectileWeaponItem {
	public EarthQuakeItem() {
		super(new Item.Properties().tab(null).durability(100));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		entity.startUsingItem(hand);
		return new InteractionResultHolder(InteractionResult.SUCCESS, entity.getItemInHand(hand));
	}

	public int getDefaultProjectileRange() {
		return 16;
	}

	public Predicate<ItemStack> getAllSupportedProjectiles() {
		return ARROW_ONLY;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemstack) {
		return UseAnim.BOW;
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 72000;
	}

	@Override
	public void releaseUsing(ItemStack itemstack, Level world, LivingEntity entityLiving, int timeLeft) {
		if (!world.isClientSide() && entityLiving.isOnGround()) {
			double x = entityLiving.getX();
			double y = entityLiving.getY();
			double z = entityLiving.getZ();
			EarthQuakeEntity entityarrow = EarthQuakeEntity.shoot(world, entityLiving, world.getRandom(), 1, 5, 0);
			if (entityLiving instanceof ServerPlayer entity)
				itemstack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(entity.getUsedItemHand()));
		}
	}
}
