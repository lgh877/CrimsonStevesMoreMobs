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

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.util.RandomSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.BlockPos;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CustomMathHelper {
	public static AABB makeAttackRange(double x, double y, double z, double height, double sizeX, double sizeY, double sizeZ) {
		y += height;
		AABB attackRange = new AABB(x - (sizeX / 2d), y - (sizeY / 2), z - (sizeZ / 2d), x + (sizeX / 2d), y + (sizeY / 2), z + (sizeZ / 2d));
		return attackRange;
	}

	public static boolean checkAnimalSpawnRules(EntityType p_218105_, LevelAccessor p_218106_, MobSpawnType p_218107_, BlockPos p_218108_, RandomSource p_218109_) {
		return p_218106_.getBlockState(p_218108_.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && p_218106_.canSeeSkyFromBelowWater(p_218108_)/*isBrightEnoughToSpawn(p_218106_, p_218108_)*/;
	}

	protected static boolean isBrightEnoughToSpawn(BlockAndTintGetter p_186210_, BlockPos p_186211_) {
		return p_186210_.getRawBrightness(p_186211_, 0) > 8;
	}

	public static boolean isOverworld(LevelAccessor world) {
		return ((Level) world).dimension() == (Level.OVERWORLD);
	}

	public static boolean isEntityInBox(LivingEntity entityIn, LivingEntity caster, double sizeup) {
		/*
		if (caster.getBoundingBox().inflate(sizeup).contains(entityIn.position())) {
			return true;
		}
		*/
		return caster.getBoundingBox().inflate(sizeup).intersects(entityIn.getBoundingBox());
	}
}
