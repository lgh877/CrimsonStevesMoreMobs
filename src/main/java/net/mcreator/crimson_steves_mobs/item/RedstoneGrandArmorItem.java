
package net.mcreator.crimson_steves_mobs.item;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.Minecraft;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModTabs;
import net.mcreator.crimson_steves_mobs.client.model.Modelredstone_grandarmor;

import java.util.function.Consumer;
import java.util.Map;
import java.util.Collections;

public abstract class RedstoneGrandArmorItem extends ArmorItem {
	final RandomSource random = RandomSource.create();

	public RedstoneGrandArmorItem(EquipmentSlot slot, Item.Properties properties) {
		super(new ArmorMaterial() {
			@Override
			public int getDurabilityForSlot(EquipmentSlot slot) {
				return new int[]{13, 15, 16, 11}[slot.getIndex()] * 25;
			}

			@Override
			public int getDefenseForSlot(EquipmentSlot slot) {
				return new int[]{3, 6, 8, 3}[slot.getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 9;
			}

			@Override
			public SoundEvent getEquipSound() {
				return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crimson_steves_mobs:redstonegolem_step"));
			}

			@Override
			public Ingredient getRepairIngredient() {
				return Ingredient.EMPTY;
			}

			@Override
			public String getName() {
				return "redstone_grand_armor";
			}

			@Override
			public float getToughness() {
				return 3f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0.1f;
			}
		}, slot, properties);
	}

	public static class Helmet extends RedstoneGrandArmorItem {
		public Helmet() {
			super(EquipmentSlot.HEAD, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_ITEMS_OF_CSMM).fireResistant());
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel armorModel = new HumanoidModel(new ModelPart(Collections.emptyList(),
							Map.of("head",
									new Modelredstone_grandarmor(
											Minecraft.getInstance().getEntityModels().bakeLayer(Modelredstone_grandarmor.LAYER_LOCATION)).head,
									"hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "body",
									new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_arm",
									new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_arm",
									new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_leg",
									new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_leg",
									new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
					armorModel.crouching = living.isShiftKeyDown();
					armorModel.riding = defaultModel.riding;
					armorModel.young = living.isBaby();
					return armorModel;
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			if (random.nextDouble() < 0.1)
				return "crimson_steves_mobs:textures/entities/redstone_grandarmor2.png";
			return "crimson_steves_mobs:textures/entities/redstone_grandarmor.png";
		}
	}

	public static class Chestplate extends RedstoneGrandArmorItem {
		public Chestplate() {
			super(EquipmentSlot.CHEST, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_ITEMS_OF_CSMM).fireResistant());
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel armorModel = new HumanoidModel(new ModelPart(Collections.emptyList(),
							Map.of("body",
									new Modelredstone_grandarmor(
											Minecraft.getInstance().getEntityModels().bakeLayer(Modelredstone_grandarmor.LAYER_LOCATION)).body,
									"left_arm",
									new Modelredstone_grandarmor(Minecraft
											.getInstance().getEntityModels().bakeLayer(Modelredstone_grandarmor.LAYER_LOCATION)).left_shoulderpad,
									"right_arm",
									new Modelredstone_grandarmor(Minecraft.getInstance().getEntityModels()
											.bakeLayer(Modelredstone_grandarmor.LAYER_LOCATION)).right_shoulderpad,
									"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "hat",
									new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_leg",
									new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_leg",
									new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
					armorModel.crouching = living.isShiftKeyDown();
					armorModel.riding = defaultModel.riding;
					armorModel.young = living.isBaby();
					return armorModel;
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			if (random.nextDouble() < 0.1)
				return "crimson_steves_mobs:textures/entities/redstone_grandarmor2.png";
			return "crimson_steves_mobs:textures/entities/redstone_grandarmor.png";
		}
	}

	public static class Leggings extends RedstoneGrandArmorItem {
		public Leggings() {
			super(EquipmentSlot.LEGS, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_ITEMS_OF_CSMM).fireResistant());
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel armorModel = new HumanoidModel(new ModelPart(Collections.emptyList(), Map.of("left_leg",
							new Modelredstone_grandarmor(
									Minecraft.getInstance().getEntityModels().bakeLayer(Modelredstone_grandarmor.LAYER_LOCATION)).leggingsLeft,
							"right_leg",
							new Modelredstone_grandarmor(
									Minecraft.getInstance().getEntityModels().bakeLayer(Modelredstone_grandarmor.LAYER_LOCATION)).leggingsRight,
							"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "hat",
							new ModelPart(Collections.emptyList(), Collections.emptyMap()), "body",
							new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_arm",
							new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_arm",
							new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
					armorModel.crouching = living.isShiftKeyDown();
					armorModel.riding = defaultModel.riding;
					armorModel.young = living.isBaby();
					return armorModel;
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			if (random.nextDouble() < 0.1)
				return "crimson_steves_mobs:textures/entities/redstone_grandarmor2.png";
			return "crimson_steves_mobs:textures/entities/redstone_grandarmor.png";
		}
	}

	public static class Boots extends RedstoneGrandArmorItem {
		public Boots() {
			super(EquipmentSlot.FEET, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_ITEMS_OF_CSMM).fireResistant());
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel armorModel = new HumanoidModel(new ModelPart(Collections.emptyList(),
							Map.of("left_leg",
									new Modelredstone_grandarmor(
											Minecraft.getInstance().getEntityModels().bakeLayer(Modelredstone_grandarmor.LAYER_LOCATION)).Left_shoe,
									"right_leg",
									new Modelredstone_grandarmor(
											Minecraft.getInstance().getEntityModels().bakeLayer(Modelredstone_grandarmor.LAYER_LOCATION)).Right_shoe,
									"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "hat",
									new ModelPart(Collections.emptyList(), Collections.emptyMap()), "body",
									new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_arm",
									new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_arm",
									new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
					armorModel.crouching = living.isShiftKeyDown();
					armorModel.riding = defaultModel.riding;
					armorModel.young = living.isBaby();
					return armorModel;
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "crimson_steves_mobs:textures/entities/redstone_grandarmor.png";
		}
	}
}
