
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.ai.goal.RangedCrossbowAttackGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Difficulty;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.EmergableZombie;
import net.mcreator.crimson_steves_mobs.CustomMathHelper;

import javax.annotation.Nullable;

import java.util.Map;

@Mod.EventBusSubscriber
public class ZombiePillagerEntity extends EmergableZombie implements CrossbowAttackMob, InventoryCarrier {
	private static final EntityDataAccessor<Boolean> IS_CHARGING_CROSSBOW = SynchedEntityData.defineId(ZombiePillagerEntity.class, EntityDataSerializers.BOOLEAN);
	private static final int INVENTORY_SIZE = 5;
	private static final int SLOT_OFFSET = 300;
	private static final float CROSSBOW_POWER = 1.6F;
	private final SimpleContainer inventory = new SimpleContainer(5);
	private final RangedCrossbowAttackGoal<ZombiePillagerEntity> bowGoal = new RangedCrossbowAttackGoal<>(this, 1.0D, 24.0F);
	private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.2D, false) {
		public void stop() {
			super.stop();
			mob.setAggressive(false);
		}

		public void start() {
			super.start();
			mob.setAggressive(true);
		}
	};

	protected boolean convertsInWater() {
		return false;
	}

	public ResourceLocation getDefaultLootTable() {
		return EntityType.ZOMBIE.getDefaultLootTable();
	}

	public ZombiePillagerEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.ZOMBIE_PILLAGER.get(), world);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(IS_CHARGING_CROSSBOW, false);
	}

	public boolean canFireProjectileWeapon(ProjectileWeaponItem p_33280_) {
		return p_33280_ instanceof CrossbowItem;
	}

	public boolean isChargingCrossbow() {
		return this.entityData.get(IS_CHARGING_CROSSBOW);
	}

	public void setChargingCrossbow(boolean p_33302_) {
		this.entityData.set(IS_CHARGING_CROSSBOW, p_33302_);
	}

	public void onCrossbowAttackPerformed() {
		this.noActionTime = 0;
	}

	public void addAdditionalSaveData(CompoundTag p_33300_) {
		super.addAdditionalSaveData(p_33300_);
		ListTag listtag = new ListTag();
		for (int i = 0; i < this.inventory.getContainerSize(); ++i) {
			ItemStack itemstack = this.inventory.getItem(i);
			if (!itemstack.isEmpty()) {
				listtag.add(itemstack.save(new CompoundTag()));
			}
		}
		p_33300_.put("Inventory", listtag);
	}

	public void readAdditionalSaveData(CompoundTag p_33291_) {
		super.readAdditionalSaveData(p_33291_);
		ListTag listtag = p_33291_.getList("Inventory", 10);
		for (int i = 0; i < listtag.size(); ++i) {
			ItemStack itemstack = ItemStack.of(listtag.getCompound(i));
			if (!itemstack.isEmpty()) {
				this.inventory.addItem(itemstack);
			}
		}
		this.reassessWeaponGoal();
		this.setCanPickUpLoot(true);
	}

	protected void populateDefaultEquipmentSlots(DifficultyInstance p_33270_) {
		super.populateDefaultEquipmentSlots(this.random, p_33270_);
		if (this.random.nextInt(3) == 0)
			this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.CROSSBOW));
	}

	protected void enchantSpawnedWeapon(float p_33316_) {
		super.enchantSpawnedWeapon(this.random, p_33316_);
		if (this.random.nextInt(300) == 0) {
			ItemStack itemstack = this.getMainHandItem();
			if (itemstack.is(Items.CROSSBOW)) {
				Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemstack);
				map.putIfAbsent(Enchantments.PIERCING, 1);
				EnchantmentHelper.setEnchantments(map, itemstack);
				this.setItemSlot(EquipmentSlot.MAINHAND, itemstack);
			}
		}
	}

	public void setItemSlot(EquipmentSlot p_32138_, ItemStack p_32139_) {
		super.setItemSlot(p_32138_, p_32139_);
		if (!this.level.isClientSide) {
			this.reassessWeaponGoal();
		}
	}

	public void performRangedAttack(LivingEntity p_33272_, float p_33273_) {
		this.performCrossbowAttack(this, 1.6F);
	}

	public void shootCrossbowProjectile(LivingEntity p_33275_, ItemStack p_33276_, Projectile p_33277_, float p_33278_) {
		this.shootCrossbowProjectile(this, p_33275_, p_33277_, p_33278_, 1.6F);
	}

	public SimpleContainer getInventory() {
		return this.inventory;
	}

	protected void pickUpItem(ItemEntity p_33296_) {
		ItemStack itemstack = p_33296_.getItem();
		if (itemstack.getItem() instanceof BannerItem) {
			super.pickUpItem(p_33296_);
		} else if (this.wantsItem(itemstack)) {
			this.onItemPickup(p_33296_);
			ItemStack itemstack1 = this.inventory.addItem(itemstack);
			if (itemstack1.isEmpty()) {
				p_33296_.discard();
			} else {
				itemstack.setCount(itemstack1.getCount());
			}
		}
	}

	private boolean wantsItem(ItemStack p_149745_) {
		return p_149745_.is(Items.WHITE_BANNER);
	}

	public SlotAccess getSlot(int p_149743_) {
		int i = p_149743_ - 300;
		return i >= 0 && i < this.inventory.getContainerSize() ? SlotAccess.forContainer(this.inventory, i) : super.getSlot(p_149743_);
	}

	public void reassessWeaponGoal() {
		if (this.level != null && !this.level.isClientSide) {
			this.goalSelector.removeGoal(this.meleeGoal);
			this.goalSelector.removeGoal(this.bowGoal);
			ItemStack itemstack = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof net.minecraft.world.item.CrossbowItem));
			if (itemstack.getItem() instanceof CrossbowItem) {
				this.goalSelector.addGoal(1, this.bowGoal);
			} else {
				this.goalSelector.addGoal(1, this.meleeGoal);
			}
		}
	}

	public ZombiePillagerEntity(EntityType<ZombiePillagerEntity> type, Level world) {
		super(type, world);
		xpReward *= 2;
		this.reassessWeaponGoal();
		this.getPersistentData().putBoolean("zombieArmAnimation", true);
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_34297_, DifficultyInstance p_34298_, MobSpawnType p_34299_, @Nullable SpawnGroupData p_34300_, @Nullable CompoundTag p_34301_) {
		p_34300_ = super.finalizeSpawn(p_34297_, p_34298_, p_34299_, p_34300_, p_34301_);
		this.emergeTicks = 30;
		this.moveTo(this.getX(), this.getY() - 3, this.getZ());
		return p_34300_;
	}

	public boolean isBaby() {
		return false;
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

	@Override
	public SoundEvent getAmbientSound() {
		return SoundEvents.ZOMBIE_VILLAGER_AMBIENT;
		//return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.vindicator.ambient"));
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.zombie.step")), 0.15f, 1);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return SoundEvents.ZOMBIE_VILLAGER_HURT;
		//return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.vindicator.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.ZOMBIE_VILLAGER_DEATH;
		//return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.vindicator.death"));
	}

	public static void init() {
		SpawnPlacements.register(CrimsonStevesMobsModEntities.ZOMBIE_PILLAGER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos,
				random) -> (world.getDifficulty() != Difficulty.PEACEFUL && CustomMathHelper.isOverworld(world) && Monster.isDarkEnoughToSpawn(world, pos, random) && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)));
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
		builder = builder.add(Attributes.MAX_HEALTH, 32);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		builder = builder.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
		return builder;
	}
}
