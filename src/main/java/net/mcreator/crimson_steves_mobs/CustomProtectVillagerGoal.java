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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

import java.util.List;
import java.util.EnumSet;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CustomProtectVillagerGoal<T extends LivingEntity> extends TargetGoal {
	private final PathfinderMob golem;
	protected final Class<T> targetType;
	@Nullable
	private LivingEntity potentialTarget;
	private int targetChangeTicks;
	private final TargetingConditions attackTargeting = TargetingConditions.forCombat().range(64.0D);

	public CustomProtectVillagerGoal(PathfinderMob p_26029_, Class<T> p_26054_) {
		super(p_26029_, false, true);
		this.targetType = p_26054_;
		this.golem = p_26029_;
		this.setFlags(EnumSet.of(Goal.Flag.TARGET));
	}

	public boolean canUse() {
		if (this.golem.getTarget() != null)
			return false;
		AABB aabb = this.golem.getBoundingBox().inflate(10.0D, 8.0D, 10.0D);
		List<? extends LivingEntity> list = this.golem.level.getNearbyEntities(this.targetType, this.attackTargeting, this.golem, aabb);
		List<Player> list1 = this.golem.level.getNearbyPlayers(this.attackTargeting, this.golem, aabb);
		for (LivingEntity livingentity : list) {
			if (this.targetType == Villager.class) {
				Villager villager = (Villager) livingentity;
				for (Player player : list1) {
					int i = villager.getPlayerReputation(player);
					if (i <= -100) {
						this.potentialTarget = player;
					}
				}
			} else if (targetChangeTicks == 0 && livingentity.getLastHurtByMob() != null && livingentity.getLastHurtByMob() != this.golem) {
				this.targetChangeTicks = 100;
				this.potentialTarget = livingentity.getLastHurtByMob();
			}
		}
		if (this.potentialTarget == null) {
			return false;
		} else {
			return !(this.potentialTarget instanceof Player) || !this.potentialTarget.isSpectator() && !((Player) this.potentialTarget).isCreative();
		}
	}

	public void tick() {
		super.tick();
		if (targetChangeTicks > 0)
			this.targetChangeTicks--;
	}

	public void start() {
		this.golem.setTarget(this.potentialTarget);
		super.start();
	}
}
