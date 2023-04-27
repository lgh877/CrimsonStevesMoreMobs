
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
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
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.Difficulty;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.BlockPos;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.MovableSwellGoal;
import net.mcreator.crimson_steves_mobs.CustomMathHelper;

import java.util.Collection;

@Mod.EventBusSubscriber
public class CreeperGolemEntity extends Creeper {
	private int explosionRadius = 1;

	public void addAdditionalSaveData(CompoundTag p_32304_) {
		super.addAdditionalSaveData(p_32304_);
		p_32304_.putByte("attackExplosionRadius", (byte) this.explosionRadius);
	}

	public void readAdditionalSaveData(CompoundTag p_32296_) {
		super.readAdditionalSaveData(p_32296_);
		if (p_32296_.contains("attackExplosionRadius", 99)) {
			this.explosionRadius = p_32296_.getByte("attackExplosionRadius");
		}
	}

	public CreeperGolemEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.CREEPER_GOLEM.get(), world);
	}

	public CreeperGolemEntity(EntityType<CreeperGolemEntity> type, Level world) {
		super(type, world);
		xpReward = 100;
	}

	public ResourceLocation getDefaultLootTable() {
		return EntityType.CREEPER.getDefaultLootTable();
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	protected void dropFromLootTable(DamageSource p_21021_, boolean p_21022_) {
		ResourceLocation resourcelocation = this.getLootTable();
		LootTable loottable = this.level.getServer().getLootTables().get(resourcelocation);
		LootContext.Builder lootcontext$builder = this.createLootContext(p_21022_, p_21021_);
		LootContext ctx = lootcontext$builder.create(LootContextParamSets.ENTITY);
		for (int i = 0; i < 4; i++)
			loottable.getRandomItems(ctx).forEach(this::spawnAtLocation);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(2, new MovableSwellGoal(this) {
			public boolean canUse() {
				LivingEntity livingentity = this.creeper.getTarget();
				return creeper.getHealth() < creeper.getMaxHealth() * 0.3f
						&& (this.creeper.getSwellDir() > 0 || livingentity != null && this.creeper.distanceToSqr(livingentity) < 16.0D);
			}
		});
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1, false) {
			protected double getAttackReachSqr(LivingEntity entity) {
				if (!mob.swinging) {
					return CustomMathHelper.isEntityInBox(entity, mob, 1) ? Double.POSITIVE_INFINITY : super.getAttackReachSqr(entity) * 0.5;
				}
				return -1;
			}
		});
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
	}

	public void aiStep() {
		super.aiStep();
		if (this.getDeltaMovement().horizontalDistanceSqr() > (double) 2.5000003E-7F && this.random.nextInt(5) == 0) {
			int i = Mth.floor(this.getX());
			int j = Mth.floor(this.getY() - (double) 0.2F);
			int k = Mth.floor(this.getZ());
			BlockPos pos = new BlockPos(i, j, k);
			BlockState blockstate = this.level.getBlockState(pos);
			if (!blockstate.isAir()) {
				this.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate).setPos(pos),
						this.getX() + ((double) this.random.nextFloat() - 0.5D) * (double) this.getBbWidth(), this.getY() + 0.1D,
						this.getZ() + ((double) this.random.nextFloat() - 0.5D) * (double) this.getBbWidth(),
						4.0D * ((double) this.random.nextFloat() - 0.5D), 0.5D, ((double) this.random.nextFloat() - 0.5D) * 4.0D);
			}
		}
	}

	public boolean doHurtTarget(Entity entity) {
		if (this.random.nextInt(4) == 0 && !this.level.isClientSide) {
			entity.invulnerableTime = 0;
			Explosion.BlockInteraction explosion$blockinteraction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this)
					? Explosion.BlockInteraction.DESTROY
					: Explosion.BlockInteraction.NONE;
			float f = this.isPowered() ? 2.0F : 1.0F;
			this.spawnLingeringCloud(entity);
			this.level.explode(this, entity.getX(), entity.getY(), entity.getZ(), (float) this.explosionRadius * f, explosion$blockinteraction);
		}
		float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
		boolean flag = entity.hurt(DamageSource.mobAttack(this), f);
		if (flag) {
			this.doEnchantDamageEffects(this, entity);
		}
		return flag;
	}

	private void spawnLingeringCloud(Entity entity) {
		Collection<MobEffectInstance> collection = this.getActiveEffects();
		if (!collection.isEmpty()) {
			AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level, entity.getX(), entity.getY(), entity.getZ());
			areaeffectcloud.setRadius(1.5F);
			areaeffectcloud.setRadiusOnUse(-0.5F);
			areaeffectcloud.setWaitTime(5);
			areaeffectcloud.setDuration(areaeffectcloud.getDuration() / 2);
			areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float) areaeffectcloud.getDuration());
			for (MobEffectInstance mobeffectinstance : collection) {
				areaeffectcloud.addEffect(new MobEffectInstance(mobeffectinstance));
			}
			this.level.addFreshEntity(areaeffectcloud);
		}
	}

	public boolean isInvulnerableTo(DamageSource source) {
		return source == DamageSource.LIGHTNING_BOLT || super.isInvulnerableTo(source);
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEFINED;
	}

	public static void init() {
		SpawnPlacements.register(CrimsonStevesMobsModEntities.CREEPER_GOLEM.get(), SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos, random) -> (world.getDifficulty() != Difficulty.PEACEFUL && CustomMathHelper.isOverworld(world)
						&& Monster.isDarkEnoughToSpawn(world, pos, random) && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)));
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 90);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 8);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.7);
		return builder;
	}
}
