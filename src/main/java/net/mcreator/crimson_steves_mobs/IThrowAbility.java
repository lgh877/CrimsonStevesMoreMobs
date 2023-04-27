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

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;

public interface IThrowAbility {
	static boolean hurtAndThrowTargetVertically(LivingEntity p_34643_, LivingEntity p_34644_) {
		float f1 = (float) p_34643_.getAttributeValue(Attributes.ATTACK_DAMAGE);
		float f;
		if (!p_34643_.isBaby() && (int) f1 > 0) {
			f = f1 / 2.0F + (float) p_34643_.level.random.nextInt((int) f1);
		} else {
			f = f1;
		}
		boolean flag = p_34644_.hurt(DamageSource.mobAttack(p_34643_), f);
		if (flag) {
			p_34643_.doEnchantDamageEffects(p_34643_, p_34644_);
			if (!p_34643_.isBaby()) {
				throwTargetVertically(p_34643_, p_34644_);
			}
		} else {
			throwTargetVertically(p_34643_, p_34644_);
		}
		return flag;
	}

	static boolean hurtAndThrowTargetVerticallyCustom(LivingEntity p_34643_, LivingEntity p_34644_, float f, double power) {
		boolean flag = p_34644_.hurt(DamageSource.mobAttack(p_34643_), f);
		if (flag) {
			p_34643_.doEnchantDamageEffects(p_34643_, p_34644_);
			throwTargetVerticallyCustom(p_34643_, p_34644_, power);
		} else {
			throwTargetVerticallyCustom(p_34643_, p_34644_, power);
		}
		return flag;
	}

	static void throwTargetVertically(LivingEntity p_34646_, LivingEntity p_34647_) {
		double d0 = p_34646_.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
		double d1 = p_34647_.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
		double d2 = d0 - d1;
		if (!(d2 <= 0.0D)) {
			p_34647_.stopRiding();
			double d3 = p_34647_.getX() - p_34646_.getX();
			double d4 = p_34647_.getZ() - p_34646_.getZ();
			float f = (float) (p_34646_.level.random.nextInt(21) - 10);
			double d5 = d2 * (double) (p_34646_.level.random.nextFloat() * 0.5F + 0.2F);
			Vec3 vec3 = (new Vec3(d3, 0.0D, d4)).normalize().scale(d5).yRot(f);
			double d6 = d2 * (double) p_34646_.level.random.nextFloat() * 0.5D;
			p_34647_.push(vec3.x, d6, vec3.z);
			p_34647_.hurtMarked = true;
		}
	}

	static void throwTargetVerticallyCustom(LivingEntity p_34646_, LivingEntity p_34647_, double power) {
		double d0 = power;
		double d1 = p_34647_.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
		double d2 = d0 - d1;
		if (d0 > 0 && !(d2 <= 0.0D)) {
			p_34647_.stopRiding();
			double d3 = p_34647_.getX() - p_34646_.getX();
			double d4 = p_34647_.getZ() - p_34646_.getZ();
			float f = (float) (p_34646_.level.random.nextInt(21) - 10);
			double d5 = d2 * (double) (p_34646_.level.random.nextFloat() * 0.5F + 0.2F);
			Vec3 vec3 = (new Vec3(d3, 0.0D, d4)).normalize().scale(d5).yRot(f);
			double d6 = d2 * (double) p_34646_.level.random.nextFloat() * 0.5D;
			p_34647_.push(vec3.x, d6, vec3.z);
			p_34647_.hurtMarked = true;
		}
	}

	static boolean hurtAndThrowTargetHorizontally(LivingEntity p_34643_, LivingEntity p_34644_) {
		float f1 = (float) p_34643_.getAttributeValue(Attributes.ATTACK_DAMAGE);
		float f;
		if (!p_34643_.isBaby() && (int) f1 > 0) {
			f = f1 / 2.0F + (float) p_34643_.level.random.nextInt((int) f1);
		} else {
			f = f1;
		}
		boolean flag = p_34644_.hurt(DamageSource.mobAttack(p_34643_), f);
		if (flag) {
			p_34643_.doEnchantDamageEffects(p_34643_, p_34644_);
			if (!p_34643_.isBaby()) {
				throwTargetHorizontally(p_34643_, p_34644_);
			}
		} else {
			throwTargetHorizontally(p_34643_, p_34644_);
		}
		return flag;
	}

	static boolean hurtAndThrowTargetHorizontallyCustom(LivingEntity p_34643_, LivingEntity p_34644_, float f, double power) {
		boolean flag = p_34644_.hurt(DamageSource.mobAttack(p_34643_), f);
		if (flag) {
			p_34643_.doEnchantDamageEffects(p_34643_, p_34644_);
			throwTargetHorizontallyCustom(p_34643_, p_34644_, power);
		} else {
			throwTargetHorizontallyCustom(p_34643_, p_34644_, power);
		}
		return flag;
	}

	static void throwTargetHorizontally(LivingEntity p_34646_, LivingEntity p_34647_) {
		double d0 = p_34646_.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
		double d1 = p_34647_.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
		double d2 = d0 - d1;
		if (!(d2 <= 0.0D)) {
			p_34647_.stopRiding();
			double d3 = p_34647_.getX() - p_34646_.getX();
			double d4 = p_34647_.getZ() - p_34646_.getZ();
			float f = (float) (p_34646_.level.random.nextInt(21) - 10);
			double d5 = d2 * (double) (p_34646_.level.random.nextFloat() * 0.5F + 0.2F);
			Vec3 vec3 = (new Vec3(d3, 0.0D, d4)).normalize().scale(d5).yRot(f);
			p_34647_.push(vec3.x * d2, d2 * 0.01, vec3.z * d2);
			p_34647_.hurtMarked = true;
		}
	}

	static void throwTargetHorizontallyCustom(LivingEntity p_34646_, LivingEntity p_34647_, double power) {
		double d0 = power;
		double d1 = p_34647_.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
		double d2 = d0 - d1;
		if (d0 > 0 && !(d2 <= 0.0D)) {
			p_34647_.stopRiding();
			double d3 = p_34647_.getX() - p_34646_.getX();
			double d4 = p_34647_.getZ() - p_34646_.getZ();
			float f = (float) (p_34646_.level.random.nextInt(21) - 10);
			double d5 = d2 * (double) (p_34646_.level.random.nextFloat() * 0.5F + 0.2F);
			Vec3 vec3 = (new Vec3(d3, 0.0D, d4)).normalize().scale(d5).yRot(f);
			p_34647_.push(vec3.x * d2, d2 * 0.01, vec3.z * d2);
			p_34647_.hurtMarked = true;
		}
	}
}
