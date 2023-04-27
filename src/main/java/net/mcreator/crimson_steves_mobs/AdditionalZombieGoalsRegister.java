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

import net.mcreator.crimson_steves_mobs.config.CrimsonStevesMobsConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;

import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;

import net.mcreator.crimson_steves_mobs.entity.ChadVillagerEntity;

import java.util.function.Predicate;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AdditionalZombieGoalsRegister {
	private static final Predicate<LivingEntity> LIVING_ENTITY_SELECTOR = (p_31504_) -> {
		return p_31504_.getMobType() == MobType.UNDEAD && p_31504_.attackable();
	};
	private static final Predicate<LivingEntity> ZOMBIE_LIVING_ENTITY_SELECTOR = (p_31504_) -> {
		return (p_31504_ instanceof Raider || p_31504_.getMobType() == MobType.ILLAGER) && p_31504_.attackable();
	};
	private static final Predicate<LivingEntity> ILLAGER_LIVING_ENTITY_SELECTOR = (p_31504_) -> {
		return !(p_31504_ instanceof Raider) && p_31504_.getMobType() == MobType.UNDEAD && p_31504_.attackable();
	};

	@SubscribeEvent
	public static void onEntityJoinWorld(final EntityJoinLevelEvent event) {
		if (CrimsonStevesMobsConfig.toggle_undeadVsIllagers.get()) {
			if (event.getEntity() instanceof Mob zombie) {
				if (!(zombie instanceof Raider) && (zombie.getMobType() == MobType.UNDEAD) && zombie instanceof Enemy) {
					zombie.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(zombie, ChadVillagerEntity.class, true));
					zombie.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(zombie, AbstractGolem.class, true));
					zombie.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(zombie, Animal.class, true));
					zombie.targetSelector.addGoal(2,
							new NearestAttackableTargetGoal<>(zombie, LivingEntity.class, 0, false, false, ZOMBIE_LIVING_ENTITY_SELECTOR));
				}
			}
			if (event.getEntity() instanceof Mob zombie) {
				if ((zombie.getMobType() == MobType.ILLAGER || (zombie instanceof Raider)) && zombie instanceof Enemy) {
					zombie.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(zombie, ChadVillagerEntity.class, true));
					zombie.targetSelector.addGoal(2,
							new NearestAttackableTargetGoal<>(zombie, LivingEntity.class, 0, false, false, ILLAGER_LIVING_ENTITY_SELECTOR));
					zombie.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(zombie, AbstractGolem.class, true));
				}
			}
		}
	}
}
