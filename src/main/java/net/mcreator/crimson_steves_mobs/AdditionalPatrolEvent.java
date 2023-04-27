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
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerLevel;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModGameRules;
import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.entity.MinionRedstoneGolemEntity;
import net.mcreator.crimson_steves_mobs.entity.CyborgVindicatorEntity;
import net.mcreator.crimson_steves_mobs.entity.CrudeRedstoneGolemEntity;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AdditionalPatrolEvent {
	final static RandomSource random = RandomSource.create();

	@SubscribeEvent
	public static void onSpawnEntity(LivingSpawnEvent.SpecialSpawn event) {
		if (event.getEntity().level instanceof ServerLevel _level) {
			if (event.getEntity() instanceof PatrollingMonster mob) {
				if (mob.level.getLevelData().getGameRules().getBoolean(CrimsonStevesMobsModGameRules.SHOULDSPAWNADDITIONALPATROLS)
						&& mob.isPatrolLeader() && !_level.isRaided(mob.blockPosition())) {
					if (mob.level.dayTime() > 60000) {
						for (int i = 0; i < 4; i++) {
							MinionRedstoneGolemEntity entityToSpawn = new MinionRedstoneGolemEntity(
									CrimsonStevesMobsModEntities.MINION_REDSTONE_GOLEM.get(), _level);
							entityToSpawn.moveTo(mob.getX(), mob.getY(), mob.getZ(), mob.getYRot(), mob.getXRot());
							entityToSpawn.setOwner(mob);
							entityToSpawn.setPatrolTarget(mob.getPatrolTarget());
							entityToSpawn.finalizeSpawn(_level, mob.level.getCurrentDifficultyAt(entityToSpawn.blockPosition()),
									MobSpawnType.MOB_SUMMONED, null, null);
							mob.level.addFreshEntity(entityToSpawn);
						}
					}
					if (mob.level.dayTime() > 360000 && mob.getType() != CrimsonStevesMobsModEntities.CYBORG_VINDICATOR.get()) {
						CyborgVindicatorEntity entityToSpawn = new CyborgVindicatorEntity(CrimsonStevesMobsModEntities.CYBORG_VINDICATOR.get(),
								_level);
						entityToSpawn.moveTo(mob.getX(), mob.getY(), mob.getZ(), mob.getYRot(), mob.getXRot());
						entityToSpawn.setPatrolTarget(mob.getPatrolTarget());
						entityToSpawn.setSize(10, true);
						mob.startRiding(entityToSpawn);
						mob.level.addFreshEntity(entityToSpawn);
					}
					if (mob.level.dayTime() > 1200000) {
						for (int i = 0; i < 2; i++) {
							CrudeRedstoneGolemEntity entityToSpawn = new CrudeRedstoneGolemEntity(
									CrimsonStevesMobsModEntities.CRUDE_REDSTONE_GOLEM.get(), _level);
							entityToSpawn.moveTo(mob.getX(), mob.getY(), mob.getZ(), mob.getYRot(), mob.getXRot());
							entityToSpawn.setPatrolTarget(mob.getPatrolTarget());
							mob.level.addFreshEntity(entityToSpawn);
						}
					}
				}
			} /* else if (event.getEntity().getType() == EntityType.ZOMBIE && !((Mob) event.getEntity()).isBaby() && Math.random() < 0.03
					&& event.getEntity().level.getLevelData().getGameRules().getBoolean(CrimsonStevesMobsModGameRules.SHOULDSPAWNFAKERAVAGER)) {
				((Mob) event.getEntity()).addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, Integer.MAX_VALUE));
				((Mob) event.getEntity()).addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, Integer.MAX_VALUE));
				ItemStack itemstack = new ItemStack(CrimsonStevesMobsModItems.FAKE_RAVAGER_CHESTPLATE.get());
				int armorV = random.nextInt(2);
				if (armorV == 0)
					itemstack = new ItemStack(CrimsonStevesMobsModItems.FAKE_IRON_GOLEM_CHESTPLATE.get());
				((Mob) event.getEntity()).setItemSlot(EquipmentSlot.CHEST, itemstack);
				}
				*/
		}
	}
}
