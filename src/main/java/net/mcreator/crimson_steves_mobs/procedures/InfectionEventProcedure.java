package net.mcreator.crimson_steves_mobs.procedures;

import net.mcreator.crimson_steves_mobs.config.CrimsonStevesMobsConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class InfectionEventProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity(),
					event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		execute(null, world, x, y, z, entity, sourceentity);
	}

	/*
	public <T extends Mob> T convertToCustom(Mob livingentity, EntityType<T> p_21407_) {
		if (livingentity.level instanceof ServerLevel _level) {
			T t = p_21407_.create(livingentity.level);
			t.finalizeSpawn(_level, livingentity.level.getCurrentDifficultyAt(livingentity.blockPosition()), MobSpawnType.CONVERSION, null, null);
			//((ZombiePigEntity) t).setColor(this.random.nextInt(5));
			t.copyPosition(livingentity);
			t.setBaby(livingentity.isBaby());
			t.setNoAi(livingentity.isNoAi());
			if (livingentity.getTarget() != null)
				t.setTarget(livingentity.getTarget());
			if (livingentity.hasCustomName()) {
				t.setCustomName(livingentity.getCustomName());
				t.setCustomNameVisible(livingentity.isCustomNameVisible());
			}
			if (livingentity.isPersistenceRequired()) {
				t.setPersistenceRequired();
			}
			t.setCanPickUpLoot(livingentity.canPickUpLoot());
			for (EquipmentSlot equipmentslot : EquipmentSlot.values()) {
				ItemStack itemstack = livingentity.getItemBySlot(equipmentslot);
				if (!itemstack.isEmpty()) {
					t.setItemSlot(equipmentslot, itemstack.copy());
					t.setDropChance(equipmentslot, 0.02f);
					itemstack.setCount(0);
				}
			}
			t.setInvulnerable(livingentity.isInvulnerable());
			livingentity.level.addFreshEntity(t);
			if (livingentity.isPassenger()) {
				Entity entity = livingentity.getVehicle();
				livingentity.stopRiding();
				t.startRiding(entity, true);
			}
			return t;
		} else {
			return (T) null;
		}
	}
	*/
	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (!CrimsonStevesMobsConfig.toggle_zombieInfection.get()) {
			return;
		}
		if (entity == null || sourceentity == null)
			return;
		if (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMobType() == MobType.UNDEAD : false) {
			if (entity instanceof Animal mob) {
				if (entity.getType() == EntityType.PIG) {
					if (!entity.level.isClientSide())
						mob.convertTo(CrimsonStevesMobsModEntities.ZOMBIE_PIG.get(), true);
				} else if (entity.getType() == EntityType.COW) {
					if (!entity.level.isClientSide())
						mob.convertTo(CrimsonStevesMobsModEntities.ZOMBIE_COW.get(), true);
				}
			} else if (entity instanceof Raider mob) {
				if (entity.getType() == EntityType.VINDICATOR) {
					if (!entity.level.isClientSide())
						mob.convertTo(CrimsonStevesMobsModEntities.ZOMBIE_VINDICATOR.get(), true);
				} else if (entity.getType() == EntityType.PILLAGER) {
					if (!entity.level.isClientSide())
						mob.convertTo(CrimsonStevesMobsModEntities.ZOMBIE_PILLAGER.get(), true);
				} else if (entity.getType() == EntityType.EVOKER) {
					if (!entity.level.isClientSide())
						mob.convertTo(CrimsonStevesMobsModEntities.ZOMBIE_EVOKER.get(), true);
				} else if (entity.getType() == EntityType.RAVAGER) {
					if (!entity.level.isClientSide())
						mob.convertTo(CrimsonStevesMobsModEntities.ZOMBIE_RAVAGER.get(), true);
				}
			}
		}
	}
}
