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

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.BlockPos;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EmergableZombie extends Zombie {
	public int emergeTicks;

	public boolean isAlliedTo(Entity entity) {
		if (this.getTeam() != null) {
			return super.isAlliedTo(entity);
		} else if ((entity instanceof Enemy) && (entity instanceof Mob zombie)) {
			if (zombie.getMobType() == MobType.UNDEAD && zombie.getTarget() != this)
				return true;
		}
		return super.isAlliedTo(entity);
	}

	public void addAdditionalSaveData(CompoundTag p_33619_) {
		super.addAdditionalSaveData(p_33619_);
		p_33619_.putInt("emergeTicks", emergeTicks);
	}

	public void readAdditionalSaveData(CompoundTag p_33607_) {
		super.readAdditionalSaveData(p_33607_);
		this.emergeTicks = p_33607_.getInt("emergeTicks");
	}

	protected void customServerAiStep() {
		if (emergeTicks > 0) {
			this.emergeTicks--;
			this.setNoGravity(true);
			this.noPhysics = true;
			this.moveTo(this.getX(), this.getY() + 0.1, this.getZ());
			if (emergeTicks == 0) {
				this.setNoGravity(false);
				this.noPhysics = false;
			}
			int i = Mth.floor(this.getX());
			int j = Mth.floor(this.getY() - (double) 0.2F + (double) this.emergeTicks / 10);
			int k = Mth.floor(this.getZ());
			BlockPos pos = new BlockPos(i, j, k);
			BlockState blockstate = this.level.getBlockState(pos);
			if (this.level instanceof ServerLevel _level)
				if (!blockstate.isAir()) {
					for (int q = 0; q < 5; q++)
						_level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockstate).setPos(pos),
								this.getX() + ((double) this.random.nextFloat() - 0.5D) * (double) this.getBbWidth() * 1.5,
								this.getY() + 0.2D + (double) this.emergeTicks / 10,
								this.getZ() + ((double) this.random.nextFloat() - 0.5D) * (double) this.getBbWidth() * 1.5, 1, 0, 0, 0, 0.5);
				}
		}
	}

	public boolean isInvulnerableTo(DamageSource source) {
		if (this.emergeTicks > 0)
			return super.isInvulnerableTo(source) || source == DamageSource.IN_WALL;
		return super.isInvulnerableTo(source);
	}

	public EmergableZombie(EntityType<? extends Zombie> type, Level world) {
		super(type, world);
	}
}
