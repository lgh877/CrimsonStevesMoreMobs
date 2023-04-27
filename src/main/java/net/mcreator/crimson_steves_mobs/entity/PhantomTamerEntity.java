
package net.mcreator.crimson_steves_mobs.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.Difficulty;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModEntities;
import net.mcreator.crimson_steves_mobs.CustomMathHelper;

@Mod.EventBusSubscriber
public class PhantomTamerEntity extends SpellcasterIllager {
	public boolean isAlliedTo(Entity p_34110_) {
		if (this.getTeam() != null) {
			return super.isAlliedTo(p_34110_);
		} else if (p_34110_ instanceof LivingEntity && ((LivingEntity) p_34110_).getMobType() == MobType.ILLAGER) {
			return this.getTeam() == null && p_34110_.getTeam() == null;
		} else {
			return false;
		}
	}

	public PhantomTamerEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CrimsonStevesMobsModEntities.PHANTOM_TAMER.get(), world);
	}

	public PhantomTamerEntity(EntityType<PhantomTamerEntity> type, Level world) {
		super(type, world);
		xpReward = 50;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
	}

	public void readAdditionalSaveData(CompoundTag p_32642_) {
		super.readAdditionalSaveData(p_32642_);
	}

	public SoundEvent getCelebrateSound() {
		return SoundEvents.EVOKER_CELEBRATE;
	}

	protected SoundEvent getCastingSoundEvent() {
		return SoundEvents.EVOKER_CAST_SPELL;
	}

	public void applyRaidBuffs(int p_32632_, boolean p_32633_) {
	}

	public void addAdditionalSaveData(CompoundTag p_32646_) {
		super.addAdditionalSaveData(p_32646_);
	}

	protected void customServerAiStep() {
		super.customServerAiStep();
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new SpellcasterIllager.SpellcasterCastingSpellGoal());
		this.goalSelector.addGoal(4, new SummonMeleePhantomGoal());
		this.goalSelector.addGoal(4, new SummonExplosivePhantomGoal());
		this.goalSelector.addGoal(4, new RidePhantomGoal());
		this.goalSelector.addGoal(6, new AttackSpellGoal());
		this.goalSelector.addGoal(6, new EarthQuakeSpellGoal());
		this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6D));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
		this.targetSelector.addGoal(2, (new NearestAttackableTargetGoal<>(this, Player.class, true)).setUnseenMemoryTicks(300));
		this.targetSelector.addGoal(3, (new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false)).setUnseenMemoryTicks(300));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, false));
	}

	public void tick() {
		super.tick();
		if (this.level.isClientSide && this.isCastingSpell()) {
			float f = this.yBodyRot * ((float) Math.PI / 180F) + Mth.cos((float) this.tickCount * 0.6662F) * 0.25F;
			float f1 = Mth.cos(f);
			float f2 = Mth.sin(f);
			this.level.addParticle(ParticleTypes.RAIN, this.getX() + (double) f1 * 0.6D, this.getY() + 1.8D, this.getZ() + (double) f2 * 0.6D, 0, 0,
					0);
			this.level.addParticle(ParticleTypes.RAIN, this.getX() - (double) f1 * 0.6D, this.getY() + 1.8D, this.getZ() - (double) f2 * 0.6D, 0, 0,
					0);
		}
	}

	@Override
	public MobType getMobType() {
		return MobType.ILLAGER;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.ambient"));
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.death"));
	}

	public static void init() {
		SpawnPlacements.register(CrimsonStevesMobsModEntities.PHANTOM_TAMER.get(), SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos,
						random) -> (world.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(world, pos, random)
								&& CustomMathHelper.isOverworld(world) && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)));
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 80);
		builder = builder.add(Attributes.ARMOR, 8);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		return builder;
	}

	public boolean removeWhenFarAway(double p_27519_) {
		return false;
	}

	private void addMobToTeam(Entity mob) {
		if (this.getTeam() != null) {
			this.level.getScoreboard().addPlayerToTeam(mob.getStringUUID(), this.level.getScoreboard().getPlayerTeam(this.getTeam().getName()));
		}
	}

	class SummonMeleePhantomGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {
		public boolean canUse() {
			if (!super.canUse()) {
				return false;
			} else {
				return PhantomTamerEntity.this.random.nextInt(8) == 0;
			}
		}

		protected int getCastingTime() {
			return 100;
		}

		protected int getCastingInterval() {
			return 200;
		}

		protected void performSpellCasting() {
			ServerLevel serverlevel = (ServerLevel) PhantomTamerEntity.this.level;
			for (int i = 0; i < 2; i++) {
				BlockPos blockpos = PhantomTamerEntity.this.blockPosition().offset(-2 + PhantomTamerEntity.this.random.nextInt(5), 1,
						-2 + PhantomTamerEntity.this.random.nextInt(5));
				TamedPhantomEntity vex = new TamedPhantomEntity(CrimsonStevesMobsModEntities.TAMED_PHANTOM.get(), PhantomTamerEntity.this.level);
				vex.moveTo(blockpos, 0.0F, 0.0F);
				vex.finalizeSpawn(serverlevel, PhantomTamerEntity.this.level.getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED,
						(SpawnGroupData) null, (CompoundTag) null);
				vex.setOwner(PhantomTamerEntity.this);
				vex.push(0, 1, 0);
				vex.setLimitedLife(10 * (30 + PhantomTamerEntity.this.random.nextInt(90)));
				PhantomTamerEntity.this.addMobToTeam(vex);
				serverlevel.addFreshEntityWithPassengers(vex);
			}
		}

		protected SoundEvent getSpellPrepareSound() {
			return SoundEvents.EVOKER_PREPARE_SUMMON;
		}

		protected SpellcasterIllager.IllagerSpell getSpell() {
			return SpellcasterIllager.IllagerSpell.SUMMON_VEX;
		}
	}

	class SummonExplosivePhantomGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {
		public boolean canUse() {
			if (!super.canUse()) {
				return false;
			} else {
				return PhantomTamerEntity.this.random.nextInt(5) == 0;
			}
		}

		protected int getCastingTime() {
			return 100;
		}

		protected int getCastingInterval() {
			return 200;
		}

		protected void performSpellCasting() {
			ServerLevel serverlevel = (ServerLevel) PhantomTamerEntity.this.level;
			BlockPos blockpos = PhantomTamerEntity.this.blockPosition().offset(-2 + PhantomTamerEntity.this.random.nextInt(5), 1,
					-2 + PhantomTamerEntity.this.random.nextInt(5));
			ExplosivePhantomEntity vex = new ExplosivePhantomEntity(CrimsonStevesMobsModEntities.EXPLOSIVE_PHANTOM.get(),
					PhantomTamerEntity.this.level);
			vex.moveTo(blockpos, 0.0F, 0.0F);
			vex.finalizeSpawn(serverlevel, PhantomTamerEntity.this.level.getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED,
					(SpawnGroupData) null, (CompoundTag) null);
			vex.setOwner(PhantomTamerEntity.this);
			vex.push(0, 1, 0);
			PhantomTamerEntity.this.addMobToTeam(vex);
			vex.setLimitedLife(5 * (30 + PhantomTamerEntity.this.random.nextInt(90)));
			serverlevel.addFreshEntityWithPassengers(vex);
		}

		protected SoundEvent getSpellPrepareSound() {
			return SoundEvents.EVOKER_PREPARE_SUMMON;
		}

		protected SpellcasterIllager.IllagerSpell getSpell() {
			return SpellcasterIllager.IllagerSpell.SUMMON_VEX;
		}
	}

	class AttackSpellGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {
		public boolean canUse() {
			if (!super.canUse()) {
				return false;
			} else {
				return PhantomTamerEntity.this.distanceTo(PhantomTamerEntity.this.getTarget()) > 16 || PhantomTamerEntity.this.isPassenger();
			}
		}

		protected int getCastingTime() {
			return 100;
		}

		protected int getCastingInterval() {
			return 200;
		}

		protected void performSpellCasting() {
			/*
			EarthQuakeEntity.shoot(PhantomTamerEntity.this.level, PhantomTamerEntity.this, PhantomTamerEntity.this.random, 5, 6, 360);
			EarthQuakeEntity.shoot(PhantomTamerEntity.this.level, PhantomTamerEntity.this, PhantomTamerEntity.this.random, 5, 6, 360);
			EarthQuakeEntity.shoot(PhantomTamerEntity.this.level, PhantomTamerEntity.this, PhantomTamerEntity.this.random, 5, 6, 360);
			EarthQuakeEntity.shoot(PhantomTamerEntity.this.level, PhantomTamerEntity.this, PhantomTamerEntity.this.random, 5, 6, 360);
			EarthQuakeEntity.shoot(PhantomTamerEntity.this.level, PhantomTamerEntity.this, PhantomTamerEntity.this.random, 5, 6, 360);
			*/
			for (int i = 0; i < 16; ++i) {
				InsomniaLumpEntity.shoot(PhantomTamerEntity.this, PhantomTamerEntity.this.getTarget(), 16 - i * 2);
			}
			PhantomTamerEntity.this.playSound(SoundEvents.AMBIENT_CAVE, 3f, 2f);
		}

		protected SoundEvent getSpellPrepareSound() {
			return SoundEvents.EVOKER_PREPARE_ATTACK;
		}

		protected SpellcasterIllager.IllagerSpell getSpell() {
			return SpellcasterIllager.IllagerSpell.FANGS;
		}
	}

	class EarthQuakeSpellGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {
		public boolean canUse() {
			if (!super.canUse()) {
				return false;
			} else {
				return PhantomTamerEntity.this.distanceTo(PhantomTamerEntity.this.getTarget()) < 16 && !PhantomTamerEntity.this.isPassenger();
			}
		}

		protected int getCastingTime() {
			return 100;
		}

		protected int getCastingInterval() {
			return 200;
		}

		protected void performSpellCasting() {
			/*
			EarthQuakeEntity.shoot(PhantomTamerEntity.this.level, PhantomTamerEntity.this, PhantomTamerEntity.this.random, 5, 6, 360);
			EarthQuakeEntity.shoot(PhantomTamerEntity.this.level, PhantomTamerEntity.this, PhantomTamerEntity.this.random, 5, 6, 360);
			EarthQuakeEntity.shoot(PhantomTamerEntity.this.level, PhantomTamerEntity.this, PhantomTamerEntity.this.random, 5, 6, 360);
			EarthQuakeEntity.shoot(PhantomTamerEntity.this.level, PhantomTamerEntity.this, PhantomTamerEntity.this.random, 5, 6, 360);
			EarthQuakeEntity.shoot(PhantomTamerEntity.this.level, PhantomTamerEntity.this, PhantomTamerEntity.this.random, 5, 6, 360);
			*/
			for (int i = 0; i < 18; ++i) {
				EarthQuakeEntity.shoot(PhantomTamerEntity.this.level, PhantomTamerEntity.this, 6, 3, 18, 2, 20 * i);
			}
			PhantomTamerEntity.this.playSound(SoundEvents.GENERIC_EXPLODE, 3f, 2f);
		}

		protected SoundEvent getSpellPrepareSound() {
			return SoundEvents.EVOKER_PREPARE_ATTACK;
		}

		protected SpellcasterIllager.IllagerSpell getSpell() {
			return SpellcasterIllager.IllagerSpell.FANGS;
		}
	}

	class RidePhantomGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {
		public boolean canUse() {
			if (!super.canUse()) {
				return false;
			} else {
				return PhantomTamerEntity.this.random.nextInt(10) == 0 && !(PhantomTamerEntity.this.getVehicle() instanceof VehiclePhantomEntity);
			}
		}

		protected int getCastingTime() {
			return 100;
		}

		protected int getCastingInterval() {
			return 200;
		}

		protected void performSpellCasting() {
			ServerLevel serverlevel = (ServerLevel) PhantomTamerEntity.this.level;
			BlockPos blockpos = PhantomTamerEntity.this.blockPosition().offset(-2 + PhantomTamerEntity.this.random.nextInt(5), 1,
					-2 + PhantomTamerEntity.this.random.nextInt(5));
			VehiclePhantomEntity vex = new VehiclePhantomEntity(CrimsonStevesMobsModEntities.VEHICLE_PHANTOM.get(), PhantomTamerEntity.this.level);
			vex.moveTo(blockpos, 0.0F, 0.0F);
			vex.finalizeSpawn(serverlevel, PhantomTamerEntity.this.level.getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED,
					(SpawnGroupData) null, (CompoundTag) null);
			vex.setOwner(PhantomTamerEntity.this);
			vex.setLimitedLife(10 * (30 + PhantomTamerEntity.this.random.nextInt(90)));
			PhantomTamerEntity.this.addMobToTeam(vex);
			serverlevel.addFreshEntityWithPassengers(vex);
			PhantomTamerEntity.this.startRiding(vex);
		}

		protected SoundEvent getSpellPrepareSound() {
			return SoundEvents.EVOKER_PREPARE_SUMMON;
		}

		protected SpellcasterIllager.IllagerSpell getSpell() {
			return SpellcasterIllager.IllagerSpell.SUMMON_VEX;
		}
	}
}
