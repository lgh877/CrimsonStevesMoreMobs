
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.crimson_steves_mobs.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;

import net.mcreator.crimson_steves_mobs.entity.ZombiefiedChadVillagerEntity;
import net.mcreator.crimson_steves_mobs.entity.ZombieVindicatorEntity;
import net.mcreator.crimson_steves_mobs.entity.ZombieRavagerEntity;
import net.mcreator.crimson_steves_mobs.entity.ZombiePillagerEntity;
import net.mcreator.crimson_steves_mobs.entity.ZombiePigEntity;
import net.mcreator.crimson_steves_mobs.entity.ZombieEvokerEntity;
import net.mcreator.crimson_steves_mobs.entity.ZombieCowEntity;
import net.mcreator.crimson_steves_mobs.entity.VehiclePhantomEntity;
import net.mcreator.crimson_steves_mobs.entity.TamedPhantomEntity;
import net.mcreator.crimson_steves_mobs.entity.TRabusEntity;
import net.mcreator.crimson_steves_mobs.entity.RedstonePoweredIronGolemEntity;
import net.mcreator.crimson_steves_mobs.entity.RedstoneMonstrosityEntity;
import net.mcreator.crimson_steves_mobs.entity.RedstoneBombEntity;
import net.mcreator.crimson_steves_mobs.entity.RavagerEndermanEntity;
import net.mcreator.crimson_steves_mobs.entity.PhantomTamerEntity;
import net.mcreator.crimson_steves_mobs.entity.PavagerEntity;
import net.mcreator.crimson_steves_mobs.entity.MinionRedstoneGolemEntity;
import net.mcreator.crimson_steves_mobs.entity.LavaWaveEntity;
import net.mcreator.crimson_steves_mobs.entity.LavaMutantZombieEntity;
import net.mcreator.crimson_steves_mobs.entity.InsomniaLumpEntity;
import net.mcreator.crimson_steves_mobs.entity.HardenedSnowBallEntity;
import net.mcreator.crimson_steves_mobs.entity.GiantIllagerEntity;
import net.mcreator.crimson_steves_mobs.entity.FlyingChickenEntity;
import net.mcreator.crimson_steves_mobs.entity.ExplosivePhantomEntity;
import net.mcreator.crimson_steves_mobs.entity.EnderRavagerEntity;
import net.mcreator.crimson_steves_mobs.entity.EliteEnderRavagerEntity;
import net.mcreator.crimson_steves_mobs.entity.EarthQuakeEntity;
import net.mcreator.crimson_steves_mobs.entity.DerpigEntity;
import net.mcreator.crimson_steves_mobs.entity.CyborgVindicatorEntity;
import net.mcreator.crimson_steves_mobs.entity.CrudeRedstoneMonstrosityEntity;
import net.mcreator.crimson_steves_mobs.entity.CrudeRedstoneGolemEntity;
import net.mcreator.crimson_steves_mobs.entity.CreesteveEntity;
import net.mcreator.crimson_steves_mobs.entity.CreepigEntity;
import net.mcreator.crimson_steves_mobs.entity.CreeperGolemEntity;
import net.mcreator.crimson_steves_mobs.entity.CreeckinEntity;
import net.mcreator.crimson_steves_mobs.entity.ConstructOfEnderHeadEntity;
import net.mcreator.crimson_steves_mobs.entity.ChadVillagerEntity;
import net.mcreator.crimson_steves_mobs.entity.ChadCreeperGolemEntity;
import net.mcreator.crimson_steves_mobs.entity.BigSnowGolemEntity;
import net.mcreator.crimson_steves_mobs.entity.BigSnowBallEntity;
import net.mcreator.crimson_steves_mobs.entity.AnimatedTempleEntity;
import net.mcreator.crimson_steves_mobs.CrimsonStevesMobsMod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CrimsonStevesMobsModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CrimsonStevesMobsMod.MODID);
	public static final RegistryObject<EntityType<ChadVillagerEntity>> CHAD_VILLAGER = register("chad_villager",
			EntityType.Builder.<ChadVillagerEntity>of(ChadVillagerEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(ChadVillagerEntity::new)

					.sized(1.4f, 2.7f));
	public static final RegistryObject<EntityType<PavagerEntity>> PAVAGER = register("pavager",
			EntityType.Builder.<PavagerEntity>of(PavagerEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(PavagerEntity::new)

					.sized(2f, 2.2f));
	public static final RegistryObject<EntityType<EnderRavagerEntity>> ENDER_RAVAGER = register("ender_ravager",
			EntityType.Builder.<EnderRavagerEntity>of(EnderRavagerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(EnderRavagerEntity::new)

					.sized(2f, 2.2f));
	public static final RegistryObject<EntityType<EliteEnderRavagerEntity>> ELITE_ENDER_RAVAGER = register("elite_ender_ravager",
			EntityType.Builder.<EliteEnderRavagerEntity>of(EliteEnderRavagerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(EliteEnderRavagerEntity::new)

					.sized(2f, 2.2f));
	public static final RegistryObject<EntityType<TRabusEntity>> T_RABUS = register("t_rabus",
			EntityType.Builder.<TRabusEntity>of(TRabusEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(TRabusEntity::new).fireImmune().sized(1.4f, 1.4f));
	public static final RegistryObject<EntityType<RavagerEndermanEntity>> RAVAGER_ENDERMAN = register("ravager_enderman",
			EntityType.Builder.<RavagerEndermanEntity>of(RavagerEndermanEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(RavagerEndermanEntity::new)

					.sized(2f, 2.2f));
	public static final RegistryObject<EntityType<DerpigEntity>> DERPIG = register("derpig",
			EntityType.Builder.<DerpigEntity>of(DerpigEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(DerpigEntity::new)

					.sized(0.7f, 1.7f));
	public static final RegistryObject<EntityType<ZombieCowEntity>> ZOMBIE_COW = register("zombie_cow",
			EntityType.Builder.<ZombieCowEntity>of(ZombieCowEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(ZombieCowEntity::new)

					.sized(0.9f, 1.9f));
	public static final RegistryObject<EntityType<ZombiePigEntity>> ZOMBIE_PIG = register("zombie_pig",
			EntityType.Builder.<ZombiePigEntity>of(ZombiePigEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(ZombiePigEntity::new)

					.sized(0.7f, 1.7f));
	public static final RegistryObject<EntityType<ZombiefiedChadVillagerEntity>> ZOMBIEFIED_CHAD_VILLAGER = register("zombiefied_chad_villager",
			EntityType.Builder.<ZombiefiedChadVillagerEntity>of(ZombiefiedChadVillagerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(ZombiefiedChadVillagerEntity::new)

					.sized(1.4f, 2.7f));
	public static final RegistryObject<EntityType<RedstonePoweredIronGolemEntity>> REDSTONE_POWERED_IRON_GOLEM = register("redstone_powered_iron_golem",
			EntityType.Builder.<RedstonePoweredIronGolemEntity>of(RedstonePoweredIronGolemEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(RedstonePoweredIronGolemEntity::new).fireImmune().sized(3f, 4f));
	public static final RegistryObject<EntityType<CyborgVindicatorEntity>> CYBORG_VINDICATOR = register("cyborg_vindicator",
			EntityType.Builder.<CyborgVindicatorEntity>of(CyborgVindicatorEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CyborgVindicatorEntity::new)

					.sized(3f, 4f));
	public static final RegistryObject<EntityType<CrudeRedstoneGolemEntity>> CRUDE_REDSTONE_GOLEM = register("crude_redstone_golem",
			EntityType.Builder.<CrudeRedstoneGolemEntity>of(CrudeRedstoneGolemEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CrudeRedstoneGolemEntity::new)

					.sized(1.5f, 3f));
	public static final RegistryObject<EntityType<CrudeRedstoneMonstrosityEntity>> CRUDE_REDSTONE_MONSTROSITY = register("crude_redstone_monstrosity",
			EntityType.Builder.<CrudeRedstoneMonstrosityEntity>of(CrudeRedstoneMonstrosityEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(CrudeRedstoneMonstrosityEntity::new)

					.sized(1.5f, 3f));
	public static final RegistryObject<EntityType<MinionRedstoneGolemEntity>> MINION_REDSTONE_GOLEM = register("minion_redstone_golem",
			EntityType.Builder.<MinionRedstoneGolemEntity>of(MinionRedstoneGolemEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(MinionRedstoneGolemEntity::new)

					.sized(1.5f, 3f));
	public static final RegistryObject<EntityType<RedstoneBombEntity>> REDSTONE_BOMB = register("projectile_redstone_bomb",
			EntityType.Builder.<RedstoneBombEntity>of(RedstoneBombEntity::new, MobCategory.MISC).setCustomClientFactory(RedstoneBombEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<BigSnowGolemEntity>> BIG_SNOW_GOLEM = register("big_snow_golem",
			EntityType.Builder.<BigSnowGolemEntity>of(BigSnowGolemEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(BigSnowGolemEntity::new)

					.sized(3f, 4f));
	public static final RegistryObject<EntityType<BigSnowBallEntity>> BIG_SNOW_BALL = register("projectile_big_snow_ball",
			EntityType.Builder.<BigSnowBallEntity>of(BigSnowBallEntity::new, MobCategory.MISC).setCustomClientFactory(BigSnowBallEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<HardenedSnowBallEntity>> HARDENED_SNOW_BALL = register("projectile_hardened_snow_ball", EntityType.Builder.<HardenedSnowBallEntity>of(HardenedSnowBallEntity::new, MobCategory.MISC)
			.setCustomClientFactory(HardenedSnowBallEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<EarthQuakeEntity>> EARTH_QUAKE = register("projectile_earth_quake",
			EntityType.Builder.<EarthQuakeEntity>of(EarthQuakeEntity::new, MobCategory.MISC).setCustomClientFactory(EarthQuakeEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<ZombieVindicatorEntity>> ZOMBIE_VINDICATOR = register("zombie_vindicator",
			EntityType.Builder.<ZombieVindicatorEntity>of(ZombieVindicatorEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(ZombieVindicatorEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<ZombiePillagerEntity>> ZOMBIE_PILLAGER = register("zombie_pillager",
			EntityType.Builder.<ZombiePillagerEntity>of(ZombiePillagerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(ZombiePillagerEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<TamedPhantomEntity>> TAMED_PHANTOM = register("tamed_phantom",
			EntityType.Builder.<TamedPhantomEntity>of(TamedPhantomEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(TamedPhantomEntity::new)

					.sized(0.9f, 0.5f));
	public static final RegistryObject<EntityType<ExplosivePhantomEntity>> EXPLOSIVE_PHANTOM = register("explosive_phantom",
			EntityType.Builder.<ExplosivePhantomEntity>of(ExplosivePhantomEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(ExplosivePhantomEntity::new)

					.sized(0.9f, 0.5f));
	public static final RegistryObject<EntityType<VehiclePhantomEntity>> VEHICLE_PHANTOM = register("vehicle_phantom",
			EntityType.Builder.<VehiclePhantomEntity>of(VehiclePhantomEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(VehiclePhantomEntity::new)

					.sized(0.9f, 0.5f));
	public static final RegistryObject<EntityType<PhantomTamerEntity>> PHANTOM_TAMER = register("phantom_tamer",
			EntityType.Builder.<PhantomTamerEntity>of(PhantomTamerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(PhantomTamerEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<InsomniaLumpEntity>> INSOMNIA_LUMP = register("projectile_insomnia_lump",
			EntityType.Builder.<InsomniaLumpEntity>of(InsomniaLumpEntity::new, MobCategory.MISC).setCustomClientFactory(InsomniaLumpEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<ZombieEvokerEntity>> ZOMBIE_EVOKER = register("zombie_evoker",
			EntityType.Builder.<ZombieEvokerEntity>of(ZombieEvokerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(ZombieEvokerEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<ZombieRavagerEntity>> ZOMBIE_RAVAGER = register("zombie_ravager",
			EntityType.Builder.<ZombieRavagerEntity>of(ZombieRavagerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(ZombieRavagerEntity::new)

					.sized(1.95f, 2.2f));
	public static final RegistryObject<EntityType<CreepigEntity>> CREEPIG = register("creepig",
			EntityType.Builder.<CreepigEntity>of(CreepigEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CreepigEntity::new)

					.sized(0.9f, 0.9f));
	public static final RegistryObject<EntityType<CreeperGolemEntity>> CREEPER_GOLEM = register("creeper_golem",
			EntityType.Builder.<CreeperGolemEntity>of(CreeperGolemEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CreeperGolemEntity::new)

					.sized(1.4f, 2.7f));
	public static final RegistryObject<EntityType<AnimatedTempleEntity>> ANIMATED_TEMPLE = register("animated_temple",
			EntityType.Builder.<AnimatedTempleEntity>of(AnimatedTempleEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AnimatedTempleEntity::new)

					.sized(4.5f, 6f));
	public static final RegistryObject<EntityType<GiantIllagerEntity>> GIANT_ILLAGER = register("giant_illager",
			EntityType.Builder.<GiantIllagerEntity>of(GiantIllagerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(GiantIllagerEntity::new)

					.sized(5f, 6f));
	public static final RegistryObject<EntityType<RedstoneMonstrosityEntity>> REDSTONE_MONSTROSITY = register("redstone_monstrosity", EntityType.Builder.<RedstoneMonstrosityEntity>of(RedstoneMonstrosityEntity::new, MobCategory.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(RedstoneMonstrosityEntity::new).fireImmune().sized(4f, 6f));
	public static final RegistryObject<EntityType<CreesteveEntity>> CREESTEVE = register("creesteve",
			EntityType.Builder.<CreesteveEntity>of(CreesteveEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CreesteveEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<FlyingChickenEntity>> FLYING_CHICKEN = register("flying_chicken",
			EntityType.Builder.<FlyingChickenEntity>of(FlyingChickenEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(FlyingChickenEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<CreeckinEntity>> CREECKIN = register("creeckin",
			EntityType.Builder.<CreeckinEntity>of(CreeckinEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CreeckinEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<ChadCreeperGolemEntity>> CHAD_CREEPER_GOLEM = register("chad_creeper_golem",
			EntityType.Builder.<ChadCreeperGolemEntity>of(ChadCreeperGolemEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(ChadCreeperGolemEntity::new)

					.sized(3f, 4f));
	public static final RegistryObject<EntityType<ConstructOfEnderHeadEntity>> CONSTRUCT_OF_ENDER_HEAD = register("construct_of_ender_head",
			EntityType.Builder.<ConstructOfEnderHeadEntity>of(ConstructOfEnderHeadEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(ConstructOfEnderHeadEntity::new)

					.sized(1.3f, 1.5f));
	public static final RegistryObject<EntityType<LavaMutantZombieEntity>> LAVA_MUTANT_ZOMBIE = register("lava_mutant_zombie", EntityType.Builder.<LavaMutantZombieEntity>of(LavaMutantZombieEntity::new, MobCategory.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(LavaMutantZombieEntity::new).fireImmune().sized(2f, 2.8f));
	public static final RegistryObject<EntityType<LavaWaveEntity>> LAVA_WAVE = register("projectile_lava_wave",
			EntityType.Builder.<LavaWaveEntity>of(LavaWaveEntity::new, MobCategory.MISC).setCustomClientFactory(LavaWaveEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));

	private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(registryname));
	}

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			ChadVillagerEntity.init();
			PavagerEntity.init();
			EnderRavagerEntity.init();
			EliteEnderRavagerEntity.init();
			TRabusEntity.init();
			RavagerEndermanEntity.init();
			DerpigEntity.init();
			ZombieCowEntity.init();
			ZombiePigEntity.init();
			ZombiefiedChadVillagerEntity.init();
			RedstonePoweredIronGolemEntity.init();
			CyborgVindicatorEntity.init();
			CrudeRedstoneGolemEntity.init();
			CrudeRedstoneMonstrosityEntity.init();
			MinionRedstoneGolemEntity.init();
			BigSnowGolemEntity.init();
			ZombieVindicatorEntity.init();
			ZombiePillagerEntity.init();
			TamedPhantomEntity.init();
			ExplosivePhantomEntity.init();
			VehiclePhantomEntity.init();
			PhantomTamerEntity.init();
			ZombieEvokerEntity.init();
			ZombieRavagerEntity.init();
			CreepigEntity.init();
			CreeperGolemEntity.init();
			AnimatedTempleEntity.init();
			GiantIllagerEntity.init();
			RedstoneMonstrosityEntity.init();
			CreesteveEntity.init();
			FlyingChickenEntity.init();
			CreeckinEntity.init();
			ChadCreeperGolemEntity.init();
			ConstructOfEnderHeadEntity.init();
			LavaMutantZombieEntity.init();
		});
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(CHAD_VILLAGER.get(), ChadVillagerEntity.createAttributes().build());
		event.put(PAVAGER.get(), PavagerEntity.createAttributes().build());
		event.put(ENDER_RAVAGER.get(), EnderRavagerEntity.createAttributes().build());
		event.put(ELITE_ENDER_RAVAGER.get(), EliteEnderRavagerEntity.createAttributes().build());
		event.put(T_RABUS.get(), TRabusEntity.createAttributes().build());
		event.put(RAVAGER_ENDERMAN.get(), RavagerEndermanEntity.createAttributes().build());
		event.put(DERPIG.get(), DerpigEntity.createAttributes().build());
		event.put(ZOMBIE_COW.get(), ZombieCowEntity.createAttributes().build());
		event.put(ZOMBIE_PIG.get(), ZombiePigEntity.createAttributes().build());
		event.put(ZOMBIEFIED_CHAD_VILLAGER.get(), ZombiefiedChadVillagerEntity.createAttributes().build());
		event.put(REDSTONE_POWERED_IRON_GOLEM.get(), RedstonePoweredIronGolemEntity.createAttributes().build());
		event.put(CYBORG_VINDICATOR.get(), CyborgVindicatorEntity.createAttributes().build());
		event.put(CRUDE_REDSTONE_GOLEM.get(), CrudeRedstoneGolemEntity.createAttributes().build());
		event.put(CRUDE_REDSTONE_MONSTROSITY.get(), CrudeRedstoneMonstrosityEntity.createAttributes().build());
		event.put(MINION_REDSTONE_GOLEM.get(), MinionRedstoneGolemEntity.createAttributes().build());
		event.put(BIG_SNOW_GOLEM.get(), BigSnowGolemEntity.createAttributes().build());
		event.put(ZOMBIE_VINDICATOR.get(), ZombieVindicatorEntity.createAttributes().build());
		event.put(ZOMBIE_PILLAGER.get(), ZombiePillagerEntity.createAttributes().build());
		event.put(TAMED_PHANTOM.get(), TamedPhantomEntity.createAttributes().build());
		event.put(EXPLOSIVE_PHANTOM.get(), ExplosivePhantomEntity.createAttributes().build());
		event.put(VEHICLE_PHANTOM.get(), VehiclePhantomEntity.createAttributes().build());
		event.put(PHANTOM_TAMER.get(), PhantomTamerEntity.createAttributes().build());
		event.put(ZOMBIE_EVOKER.get(), ZombieEvokerEntity.createAttributes().build());
		event.put(ZOMBIE_RAVAGER.get(), ZombieRavagerEntity.createAttributes().build());
		event.put(CREEPIG.get(), CreepigEntity.createAttributes().build());
		event.put(CREEPER_GOLEM.get(), CreeperGolemEntity.createAttributes().build());
		event.put(ANIMATED_TEMPLE.get(), AnimatedTempleEntity.createAttributes().build());
		event.put(GIANT_ILLAGER.get(), GiantIllagerEntity.createAttributes().build());
		event.put(REDSTONE_MONSTROSITY.get(), RedstoneMonstrosityEntity.createAttributes().build());
		event.put(CREESTEVE.get(), CreesteveEntity.createAttributes().build());
		event.put(FLYING_CHICKEN.get(), FlyingChickenEntity.createAttributes().build());
		event.put(CREECKIN.get(), CreeckinEntity.createAttributes().build());
		event.put(CHAD_CREEPER_GOLEM.get(), ChadCreeperGolemEntity.createAttributes().build());
		event.put(CONSTRUCT_OF_ENDER_HEAD.get(), ConstructOfEnderHeadEntity.createAttributes().build());
		event.put(LAVA_MUTANT_ZOMBIE.get(), LavaMutantZombieEntity.createAttributes().build());
	}
}
