
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvents;
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
import net.mcreator.crimson_steves_mobs.PavagerFollowParentGoal;
import net.mcreator.crimson_steves_mobs.CustomProtectVillagerGoal;
import net.mcreator.crimson_steves_mobs.CustomMathHelper;

import javax.annotation.Nullable;

import java.util.UUID;

@Mod.EventBusSubscriber
public class PavagerEntity extends Animal implements NeutralMob {
	private int stunnedTick;
	private static final EntityDataAccessor<Boolean> IS_CHARGED = SynchedEntityData.defineId(PavagerEntity.class, EntityDataSerializers.BOOLEAN);
	private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.CARROT, Items.POTATO, Items.BEETROOT);
	private int remainingPersistentAngerTime;
	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	@Nullable
	private UUID persistentAngerTarget;

	public void makeStuckInBlock(BlockState p_33796_, Vec3 p_33797_) {
		if (this.tickCount % 6 == 0)
			super.makeStuckInBlock(p_33796_, p_33797_);
	}

	protected PathNavigation createNavigation(Level p_33348_) {
		return new EnderRavagerEntity.RavagerNavigation(this, p_33348_);
	}

	static class RavagerNavigation extends GroundPathNavigation {
		public RavagerNavigation(Mob p_33379_, Level p_33380_) {
			super(p_33379_, p_33380_);
		}

		protected PathFinder createPathFinder(int p_33382_) {
			this.nodeEvaluator = new EnderRavagerEntity.RavagerNodeEvaluator();
			return new PathFinder(this.nodeEvaluator, p_33382_);
		}
	}

	static class RavagerNodeEvaluator extends WalkNodeEvaluator {
		protected BlockPathTypes evaluateBlockPathType(BlockGetter p_33387_, boolean p_33388_, boolean p_33389_, BlockPos p_33390_, BlockPathTypes p_33391_) {
			return p_33391_ == BlockPathTypes.LEAVES ? BlockPathTypes.OPEN : super.evaluateBlockPathType(p_33387_, p_33388_, p_33389_, p_33390_, p_33391_);
		}
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(IS_CHARGED, false);
	}

	public void addAdditionalSaveData(CompoundTag p_33353_) {
		super.addAdditionalSaveData(p_33353_);
		if (this.entityData.get(IS_CHARGED)) {
			p_33353_.putBoolean("powered", true);
		}
		p_33353_.putInt("StunTick", this.stunnedTick);
		this.addPersistentAngerSaveData(p_33353_);
	}

	public void readAdditionalSaveData(CompoundTag p_33344_) {
		super.readAdditionalSaveData(p_33344_);
		this.entityData.set(IS_CHARGED, p_33344_.getBoolean("powered"));
		this.stunnedTick = p_33344_.getInt("StunTick");
		this.readPersistentAngerSaveData(this.level, p_33344_);
	}

	public PavagerEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.PAVAGER.get(), world);
	}

	public float getScale() {
		return super.getScale() * 0.75f;
	}

	public ResourceLocation getDefaultLootTable() {
		return EntityType.PIG.getDefaultLootTable();
	}

	protected void dropFromLootTable(DamageSource p_21021_, boolean p_21022_) {
		ResourceLocation resourcelocation = this.getLootTable();
		LootTable loottable = this.level.getServer().getLootTables().get(resourcelocation);
		LootContext.Builder lootcontext$builder = this.createLootContext(p_21022_, p_21021_);
		LootContext ctx = lootcontext$builder.create(LootContextParamSets.ENTITY);
		for (int i = 0; i < 4; i++)
			loottable.getRandomItems(ctx).forEach(this::spawnAtLocation);
	}

	public PavagerEntity(EntityType<PavagerEntity> type, Level world) {
		super(type, world);
		this.maxUpStep = 1.0F;
		this.refreshDimensions();
		xpReward = 12;
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

	public float getVoicePitch() {
		return super.getVoicePitch() * 0.6f;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				float f = PavagerEntity.this.getBbWidth() - 0.1F;
				return (double) (f * 2.0F * f * 2.0F + entity.getBbWidth());
			}
		});
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Zombie.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, false));
		this.targetSelector.addGoal(1, new CustomProtectVillagerGoal(this, Pig.class));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new PavagerFollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, FOOD_ITEMS, false));
		this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(5, new FloatGoal(this));
	}

	public float getSteeringSpeed() {
		return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.225F;
	}

	public boolean isFood(ItemStack p_29508_) {
		return FOOD_ITEMS.test(p_29508_);
	}

	public int getRemainingPersistentAngerTime() {
		return this.remainingPersistentAngerTime;
	}

	public void setRemainingPersistentAngerTime(int p_28859_) {
		this.remainingPersistentAngerTime = p_28859_;
	}

	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	public void setPersistentAngerTarget(@Nullable UUID p_28855_) {
		this.persistentAngerTarget = p_28855_;
	}

	@Nullable
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

	public void aiStep() {
		super.aiStep();
		this.updateSwingTime();
		if (!this.level.isClientSide) {
			this.updatePersistentAnger((ServerLevel) this.level, true);
		}
		if (this.isAlive()) {
			if (this.getTarget() != null && !this.getTarget().isAlive())
				this.forgetCurrentTargetAndRefreshUniversalAnger();
			if (this.isImmobile()) {
				this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.0D);
			} else {
				double d0 = this.getTarget() != null ? 0.35D : 0.3D;
				double d1 = this.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue();
				this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(Mth.lerp(0.1D, d1, d0));
			}
			if (this.horizontalCollision && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this)) {
				boolean flag = false;
				AABB aabb = this.getBoundingBox().inflate(0.2D);
				for (BlockPos blockpos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
					BlockState blockstate = this.level.getBlockState(blockpos);
					Block block = blockstate.getBlock();
					if (block instanceof LeavesBlock) {
						flag = this.level.destroyBlock(blockpos, true, this) || flag;
					}
				}
			}
			if (this.stunnedTick > 0) {
				--this.stunnedTick;
				this.yHeadRot = this.random.nextFloat() * 360;
				this.yHeadRotO = this.random.nextFloat() * 360;
			}
		}
	}

	public boolean hurt(DamageSource p_21016_, float p_21017_) {
		if (this.isPowered() && (p_21016_ == DamageSource.LIGHTNING_BOLT || p_21016_ == DamageSource.ON_FIRE || p_21016_ == DamageSource.IN_FIRE))
			return false;
		return super.hurt(p_21016_, p_21017_);
	}

	public boolean doHurtTarget(Entity entityIn) {
		this.playSound(SoundEvents.PLAYER_ATTACK_CRIT, this.getSoundVolume(), this.getVoicePitch());
		if (this.isPowered()) {
			if (level instanceof ServerLevel _level) {
				LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
				entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos(entityIn.getX(), entityIn.getY(), entityIn.getZ())));
				_level.addFreshEntity(entityToSpawn);
			}
		}
		return super.doHurtTarget(entityIn);
	}

	protected boolean isImmobile() {
		return super.isImmobile() || this.swinging || this.stunnedTick > 0;
	}

	protected void blockedByShield(LivingEntity p_33361_) {
		if (this.random.nextDouble() < 0.5D) {
			this.stunnedTick = 40;
			this.level.broadcastEntityEvent(this, (byte) 39);
		}
	}

	public void handleEntityEvent(byte p_33335_) {
		if (p_33335_ == 39) {
			this.stunnedTick = 40;
		}
		super.handleEntityEvent(p_33335_);
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.pig.ambient"));
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.pig.step")), 0.15f, 1);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.pig.death"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.zoglin.angry"));
	}

	protected static boolean isBrightEnoughToSpawn(BlockAndTintGetter p_186210_, BlockPos p_186211_) {
		return p_186210_.getRawBrightness(p_186211_, 0) > 8;
	}

	public static void init() {
		SpawnPlacements.register(CrimsonStevesMobsModEntities.PAVAGER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos, random) -> (Animal.checkAnimalSpawnRules(entityType, world, reason, pos, random) && CustomMathHelper.isOverworld(world)));
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
		PavagerEntity retval = CrimsonStevesMobsModEntities.PAVAGER.get().create(serverWorld);
		retval.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(retval.blockPosition()), MobSpawnType.BREEDING, null, null);
		return retval;
	}

	public void thunderHit(ServerLevel p_29473_, LightningBolt p_29474_) {
		if (!this.isPowered()) {
			super.thunderHit(p_29473_, p_29474_);
			this.entityData.set(IS_CHARGED, true);
		}
	}

	public boolean isPowered() {
		return this.entityData.get(IS_CHARGED);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 55);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 7);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1.5);
		return builder;
	}
}
