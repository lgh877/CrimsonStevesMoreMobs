
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Difficulty;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.CustomMathHelper;

import javax.annotation.Nullable;

import java.util.UUID;

public class CreesteveEntity extends Creeper {
	private static final UUID SPEED_MODIFIER_BABY_UUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final AttributeModifier SPEED_MODIFIER_BABY = new AttributeModifier(SPEED_MODIFIER_BABY_UUID, "Baby speed boost", 0.5D,
			AttributeModifier.Operation.MULTIPLY_BASE);
	private static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(CreesteveEntity.class, EntityDataSerializers.BOOLEAN);
	private boolean wasDied;
	private int explosionRadius = 3;

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(DATA_BABY_ID, false);
	}

	public void addAdditionalSaveData(CompoundTag p_33619_) {
		super.addAdditionalSaveData(p_33619_);
		p_33619_.putBoolean("wasDied", this.wasDied);
		p_33619_.putBoolean("IsBaby", this.isBaby());
		p_33619_.putByte("ExplosionRadius", (byte) this.explosionRadius);
	}

	public void readAdditionalSaveData(CompoundTag p_33607_) {
		super.readAdditionalSaveData(p_33607_);
		this.wasDied = p_33607_.getBoolean("wasDied");
		this.setBaby(p_33607_.getBoolean("IsBaby"));
		p_33607_.putByte("ExplosionRadius", (byte) this.explosionRadius);
	}

	public void setBaby(boolean p_34309_) {
		this.getEntityData().set(DATA_BABY_ID, p_34309_);
		if (this.level != null && !this.level.isClientSide) {
			AttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
			attributeinstance.removeModifier(SPEED_MODIFIER_BABY);
			if (p_34309_) {
				attributeinstance.addTransientModifier(SPEED_MODIFIER_BABY);
			}
		}
	}

	public boolean causeFallDamage(float p_148875_, float p_148876_, DamageSource p_148877_) {
		if (isBaby() || this.wasDied)
			return false;
		else
			return super.causeFallDamage(p_148875_, p_148876_, p_148877_);
	}

	public boolean isBaby() {
		return this.getEntityData().get(DATA_BABY_ID);
	}

	protected float getStandingEyeHeight(Pose p_34313_, EntityDimensions p_34314_) {
		return this.isBaby() ? 0.93F : 1.74F;
	}

	public ResourceLocation getDefaultLootTable() {
		return EntityType.CREEPER.getDefaultLootTable();
	}

	public CreesteveEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.CREESTEVE.get(), world);
	}

	public CreesteveEntity(EntityType<CreesteveEntity> type, Level world) {
		super(type, world);
		xpReward = 10;
		setPersistenceRequired();
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1, false));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
	}

	public void die(DamageSource p_21014_) {
		if (!this.wasDied) {
			this.setHealth(this.getMaxHealth() * 0.2f);
			this.ignite();
			this.setPose(Pose.FALL_FLYING);
			this.wasDied = true;
		} else
			super.die(p_21014_);
	}

	public float getWalkTargetValue(BlockPos p_33013_, LevelReader p_33014_) {
		return 0;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source == DamageSource.LIGHTNING_BOLT)
			return false;
		return super.hurt(source, amount);
	}

	public boolean doHurtTarget(Entity entity) {
		if (!this.wasDied) {
			float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
			boolean flag = entity.hurt(DamageSource.mobAttack(this), f);
			if (flag) {
				this.doEnchantDamageEffects(this, entity);
			}
			return flag;
		}
		return true;
	}

	public boolean removeWhenFarAway(double p_27598_) {
		return false;
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_34297_, DifficultyInstance p_34298_, MobSpawnType p_34299_,
			@Nullable SpawnGroupData p_34300_, @Nullable CompoundTag p_34301_) {
		RandomSource randomsource = p_34297_.getRandom();
		this.populateDefaultEquipmentSlots(randomsource, p_34298_);
		this.populateDefaultEquipmentEnchantments(randomsource, p_34298_);
		if (randomsource.nextInt(10) == 0) {
			this.setBaby(true);
			explosionRadius = 2;
			CreeckinEntity chicken1 = new CreeckinEntity(CrimsonStevesMobsModEntities.CREECKIN.get(), this.level);
			chicken1.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
			chicken1.finalizeSpawn(p_34297_, p_34298_, MobSpawnType.JOCKEY, (SpawnGroupData) null, (CompoundTag) null);
			chicken1.setChickenJockey(true);
			this.startRiding(chicken1);
			p_34297_.addFreshEntity(chicken1);
		} else if (randomsource.nextInt(10) == 0) {
			explosionRadius = 2;
			this.setBaby(true);
		}
		this.addAdditionalSaveData(this.getPersistentData());
		this.readAdditionalSaveData(this.getPersistentData());
		return super.finalizeSpawn(p_34297_, p_34298_, p_34299_, p_34300_, p_34301_);
	}

	protected void populateDefaultEquipmentSlots(RandomSource p_219165_, DifficultyInstance p_219166_) {
		super.populateDefaultEquipmentSlots(p_219165_, p_219166_);
		if (p_219165_.nextFloat() < (this.level.getDifficulty() == Difficulty.HARD ? 0.05F : 0.01F)) {
			int i = p_219165_.nextInt(3);
			if (i == 0) {
				this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
			} else {
				this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
			}
		}
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:creesteve_hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		if (!this.wasDied)
			return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:creesteve_death"));
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
	}

	public static void init() {
		SpawnPlacements.register(CrimsonStevesMobsModEntities.CREESTEVE.get(), SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos, random) -> (world.getDifficulty() != Difficulty.PEACEFUL
						&& CustomMathHelper.isOverworld(world) && CustomMathHelper.checkAnimalSpawnRules(entityType, world, reason, pos, random)));
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 20);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 2);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		return builder;
	}
}
