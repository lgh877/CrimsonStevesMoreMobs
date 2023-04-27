
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Difficulty;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.EmergableZombie;
import net.mcreator.crimson_steves_mobs.CustomMathHelper;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class ZombiePigEntity extends EmergableZombie {
	private static final EntityDataAccessor<Integer> TEXTURE = SynchedEntityData.defineId(ZombiePigEntity.class, EntityDataSerializers.INT);
	private boolean wasSpawned;

	protected boolean convertsInWater() {
		return false;
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(TEXTURE, 0);
	}

	public void addAdditionalSaveData(CompoundTag p_29864_) {
		super.addAdditionalSaveData(p_29864_);
		p_29864_.putInt("Type", this.entityData.get(TEXTURE));
		p_29864_.putBoolean("wasSpawned", this.wasSpawned);
	}

	public void readAdditionalSaveData(CompoundTag p_29845_) {
		super.readAdditionalSaveData(p_29845_);
		this.wasSpawned = p_29845_.getBoolean("wasSpawned");
		if (!this.wasSpawned) {
			this.setColor(this.random.nextInt(5));
			this.wasSpawned = true;
		}
		this.setColor(p_29845_.getInt("Type"));
	}

	public void setColor(int p_29856_) {
		this.entityData.set(TEXTURE, p_29856_);
	}

	public int getColor() {
		return this.entityData.get(TEXTURE);
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_29835_, DifficultyInstance p_29836_, MobSpawnType p_29837_, @Nullable SpawnGroupData p_29838_, @Nullable CompoundTag p_29839_) {
		this.emergeTicks = 30;
		this.moveTo(this.getX(), this.getY() - 3, this.getZ());
		if (p_29837_ != MobSpawnType.CONVERSION) {
			if (this.random.nextFloat() < (this.level.getDifficulty() == Difficulty.HARD ? 0.05F : 0.01F)) {
				int i = this.random.nextInt(3);
				if (i == 0) {
					this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
				} else {
					this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
				}
				this.populateDefaultEquipmentEnchantments(this.random, p_29836_);
			}
		}
		return super.finalizeSpawn(p_29835_, p_29836_, p_29837_, p_29838_, p_29839_);
	}

	public ZombiePigEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.ZOMBIE_PIG.get(), world);
	}

	public ZombiePigEntity(EntityType<ZombiePigEntity> type, Level world) {
		super(type, world);
		if (!this.wasSpawned) {
			this.setColor(this.random.nextInt(5));
			this.wasSpawned = true;
		}
	}

	public ResourceLocation getDefaultLootTable() {
		return EntityType.ZOMBIE.getDefaultLootTable();
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
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	public boolean doHurtTarget(Entity entity) {
		if (!(entity instanceof LivingEntity)) {
			return false;
		} else {
			if (!this.level.isClientSide && !this.getMainHandItem().isEmpty()) {
				Item item1 = this.getMainHandItem().getItem();
				item1.hurtEnemy(this.getMainHandItem(), (LivingEntity) entity, this);
			}
		}
		return super.doHurtTarget(entity);
	}

	@Override
	public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.zoglin.ambient"));
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		super.playStepSound(pos, blockIn);
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.zoglin.step")), 0.15f, 1);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.zoglin.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.zoglin.death"));
	}

	public static void init() {
		SpawnPlacements.register(CrimsonStevesMobsModEntities.ZOMBIE_PIG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos,
				random) -> (world.getDifficulty() != Difficulty.PEACEFUL && CustomMathHelper.isOverworld(world) && Monster.isDarkEnoughToSpawn(world, pos, random) && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)));
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
		builder = builder.add(Attributes.MAX_HEALTH, 24);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
		return builder;
	}
}
