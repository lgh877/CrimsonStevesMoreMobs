
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.Difficulty;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.BlockPos;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;

@Mod.EventBusSubscriber
public class EliteEnderRavagerEntity extends EnderRavagerEntity {
	public EliteEnderRavagerEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.ELITE_ENDER_RAVAGER.get(), world);
	}

	public void makeStuckInBlock(BlockState p_33796_, Vec3 p_33797_) {
	}

	public EliteEnderRavagerEntity(EntityType<EliteEnderRavagerEntity> type, Level world) {
		super(type, world);
		xpReward = 45;
		setNoAi(false);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:endersent_step")), 0.3f, 0.5f);
	}

	protected float teleportationChance() {
		return super.teleportationChance() * 2f;
	}

	public float getScale() {
		return super.getScale() * 1.3f;
	}

	public static boolean isOverworld(LevelAccessor world) {
		if (world instanceof Level _lvl) {
			if (_lvl.dimension() == Level.OVERWORLD || _lvl.dimension() == Level.END) {
				return true;
			}
		}
		return false;
	}

	public static void init() {
		SpawnPlacements.register(CrimsonStevesMobsModEntities.ELITE_ENDER_RAVAGER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos, random) -> (world.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(world, pos, random) && isOverworld(world) && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)));
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.22);
		builder = builder.add(Attributes.MAX_HEALTH, 200);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 20);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.7);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 3);
		return builder;
	}
}
