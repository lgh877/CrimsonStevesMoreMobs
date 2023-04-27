
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.SmartBodyHelper2;
import net.mcreator.crimson_steves_mobs.SlowRotMoveControl;
import net.mcreator.crimson_steves_mobs.EmergableZombie;
import net.mcreator.crimson_steves_mobs.CustomMathHelper;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class DerpigEntity extends EmergableZombie {
	private static final EntityDataAccessor<Integer> TEXTURE = SynchedEntityData.defineId(DerpigEntity.class, EntityDataSerializers.INT);

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
	}

	public void readAdditionalSaveData(CompoundTag p_29845_) {
		super.readAdditionalSaveData(p_29845_);
		this.setColor(p_29845_.getInt("Type"));
	}

	public void setColor(int p_29856_) {
		this.entityData.set(TEXTURE, p_29856_);
	}

	public int getColor() {
		return this.entityData.get(TEXTURE);
	}

	public void setBaby(boolean p_21451_) {
		this.setColor(this.random.nextInt(4));
		super.setBaby(p_21451_);
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_29835_, DifficultyInstance p_29836_, MobSpawnType p_29837_, @Nullable SpawnGroupData p_29838_, @Nullable CompoundTag p_29839_) {
		this.emergeTicks = 30;
		this.moveTo(this.getX(), this.getY() - 3, this.getZ());
		if (this.random.nextFloat() < (this.level.getDifficulty() == Difficulty.HARD ? 0.05F : 0.01F)) {
			int i = this.random.nextInt(3);
			if (i == 0) {
				this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
			} else {
				this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
			}
		}
		this.setColor(this.random.nextInt(4));
		return super.finalizeSpawn(p_29835_, p_29836_, p_29837_, p_29838_, p_29839_);
	}

	public DerpigEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.DERPIG.get(), world);
	}

	public DerpigEntity(EntityType<DerpigEntity> type, Level world) {
		super(type, world);
		xpReward = 3;
		this.moveControl = new SlowRotMoveControl(this);
	}

	@Override
	protected BodyRotationControl createBodyControl() {
		return new SmartBodyHelper2(this);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(1, new LeapAtTargetGoal(this, (float) 0.5));
	}

	public ResourceLocation getDefaultLootTable() {
		return EntityType.PIG.getDefaultLootTable();
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	protected boolean isSunSensitive() {
		return false;
	}

	public void remove(Entity.RemovalReason p_149847_) {
		if (this.isDeadOrDying())
			this.convertToCustom(CrimsonStevesMobsModEntities.ZOMBIE_PIG.get());
		super.remove(p_149847_);
	}

	@Nullable
	public <T extends Mob> T convertToCustom(EntityType<T> p_21407_) {
		if (level instanceof ServerLevel _level) {
			T t = p_21407_.create(this.level);
			//t.finalizeSpawn(_level, level.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.CONVERSION, null, null);
			//((ZombiePigEntity) t).setColor(this.random.nextInt(5));
			t.copyPosition(this);
			t.setBaby(this.isBaby());
			t.setNoAi(this.isNoAi());
			if (this.getTarget() != null)
				t.setTarget(this.getTarget());
			if (this.hasCustomName()) {
				t.setCustomName(this.getCustomName());
				t.setCustomNameVisible(this.isCustomNameVisible());
			}
			if (this.isPersistenceRequired()) {
				t.setPersistenceRequired();
			}
			t.setCanPickUpLoot(this.canPickUpLoot());
			for (EquipmentSlot equipmentslot : EquipmentSlot.values()) {
				ItemStack itemstack = this.getItemBySlot(equipmentslot);
				if (!itemstack.isEmpty()) {
					t.setItemSlot(equipmentslot, itemstack.copy());
					t.setDropChance(equipmentslot, this.getEquipmentDropChance(equipmentslot));
					itemstack.setCount(0);
				}
			}
			t.setInvulnerable(this.isInvulnerable());
			this.level.addFreshEntity(t);
			if (this.isPassenger()) {
				Entity entity = this.getVehicle();
				this.stopRiding();
				t.startRiding(entity, true);
			}
			return t;
		} else {
			return (T) null;
		}
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

	public float getWalkTargetValue(BlockPos p_33013_, LevelReader p_33014_) {
		return 0;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.pig.ambient"));
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.pig.step")), 0.15f, 1);
	}

	protected void updateNoActionTime() {
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.pig.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.pig.death"));
	}

	public static void init() {
		SpawnPlacements.register(CrimsonStevesMobsModEntities.DERPIG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos, random) -> (world.getDifficulty() != Difficulty.PEACEFUL && CustomMathHelper.isOverworld(world) && CustomMathHelper.checkAnimalSpawnRules(entityType, world, reason, pos, random)));
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 12);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
		return builder;
	}
}
