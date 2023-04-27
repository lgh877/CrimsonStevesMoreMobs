
package net.mcreator.crimson_steves_mobs.item;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.Minecraft;

import net.mcreator.crimson_steves_mobs.init.CrimsonStevesMobsModTabs;
import net.mcreator.crimson_steves_mobs.client.model.ModelAnimatedIronGolemModel;

import java.util.function.Consumer;

import com.mojang.math.Vector3f;

public abstract class FakeIronGolemItem extends ArmorItem {
	public FakeIronGolemItem(EquipmentSlot slot, Item.Properties properties) {
		super(new ArmorMaterial() {
			@Override
			public int getDurabilityForSlot(EquipmentSlot slot) {
				return new int[]{13, 15, 16, 11}[slot.getIndex()] * 10;
			}

			@Override
			public int getDefenseForSlot(EquipmentSlot slot) {
				return new int[]{2, 5, 15, 2}[slot.getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 9;
			}

			@Override
			public SoundEvent getEquipSound() {
				return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair"));
			}

			@Override
			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(Items.IRON_INGOT));
			}

			@Override
			public String getName() {
				return "fake_iron_golem";
			}

			@Override
			public float getToughness() {
				return 5f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0.4f;
			}
		}, slot, properties);
	}

	public static class Chestplate extends FakeIronGolemItem {
		public Chestplate() {
			super(EquipmentSlot.CHEST, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_ITEMS_OF_CSMM));
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public Model getGenericArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					ModelAnimatedIronGolemModel armorModel = new ModelAnimatedIronGolemModel(
							Minecraft.getInstance().getEntityModels().bakeLayer(ModelAnimatedIronGolemModel.LAYER_LOCATION));
					((EntityModel) defaultModel).copyPropertiesTo(armorModel);
					armorModel.root.offsetScale(new Vector3f(-0.4f, -0.4f, -0.4f));
					armorModel.root.zRot = defaultModel.rightLeg.xRot / 20;
					armorModel.head.setRotation(defaultModel.head.xRot, defaultModel.head.yRot, defaultModel.head.zRot);
					if (defaultModel.riding) {
						armorModel.root.y = 5;
						armorModel.rightLeg.setRotation(defaultModel.rightLeg.xRot, defaultModel.rightLeg.yRot, defaultModel.rightLeg.zRot);
						armorModel.leftLeg.setRotation(defaultModel.leftLeg.xRot, defaultModel.leftLeg.yRot, defaultModel.leftLeg.zRot);
					} else {
						armorModel.root.y = 10;
						armorModel.rightLeg.setRotation(defaultModel.rightLeg.xRot / 2, defaultModel.rightLeg.yRot / 2,
								defaultModel.rightLeg.zRot / 2);
						armorModel.leftLeg.setRotation(defaultModel.leftLeg.xRot / 2, defaultModel.leftLeg.yRot / 2, defaultModel.leftLeg.zRot / 2);
					}
					armorModel.rightArm.setRotation(defaultModel.leftLeg.xRot / 2, defaultModel.leftLeg.yRot / 2, defaultModel.leftLeg.zRot / 2);
					armorModel.leftArm.setRotation(defaultModel.rightLeg.xRot / 2, defaultModel.rightLeg.yRot / 2, defaultModel.rightLeg.zRot / 2);
					if (armorModel.attackTime > 0) {
						armorModel.leftArm.xRot = -Mth.sin(armorModel.attackTime * (float) Math.PI) * 3;
						armorModel.rightArm.xRot = -Mth.sin(armorModel.attackTime * (float) Math.PI) * 3;
					}
					return armorModel;
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "textures/entity/iron_golem/iron_golem.png";
		}
	}
}
