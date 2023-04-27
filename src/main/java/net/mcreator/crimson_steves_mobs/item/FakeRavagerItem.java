
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
import net.mcreator.crimson_steves_mobs.client.model.ModelanimatedRavagerModel;

import java.util.function.Consumer;

import com.mojang.math.Vector3f;

public abstract class FakeRavagerItem extends ArmorItem {
	public FakeRavagerItem(EquipmentSlot slot, Item.Properties properties) {
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
				return 25;
			}

			@Override
			public SoundEvent getEquipSound() {
				return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ravager.celebrate"));
			}

			@Override
			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(Items.IRON_INGOT));
			}

			@Override
			public String getName() {
				return "fake_ravager";
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

	public static class Chestplate extends FakeRavagerItem {
		public Chestplate() {
			super(EquipmentSlot.CHEST, new Item.Properties().tab(CrimsonStevesMobsModTabs.TAB_ITEMS_OF_CSMM));
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public Model getGenericArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					ModelanimatedRavagerModel armorModel = new ModelanimatedRavagerModel(
							Minecraft.getInstance().getEntityModels().bakeLayer(ModelanimatedRavagerModel.LAYER_LOCATION));
					((EntityModel) defaultModel).copyPropertiesTo(armorModel);
					float a = Mth.sin(armorModel.attackTime * (float) Math.PI);
					armorModel.root.offsetScale(new Vector3f(-0.4f, -0.4f, -0.4f));
					armorModel.root.y = 10;
					armorModel.neck.z = 5.5f - a * 16;
					armorModel.neck.setRotation(defaultModel.head.xRot * a, defaultModel.head.yRot * a, defaultModel.head.zRot * a);
					armorModel.mouth.xRot = a;
					armorModel.head.setRotation(defaultModel.head.xRot * (1 - a), defaultModel.head.yRot * (1 - a), defaultModel.head.zRot * (1 - a));
					armorModel.rightHindLeg.setRotation(defaultModel.rightLeg.xRot / 2, defaultModel.rightLeg.yRot / 2,
							defaultModel.rightLeg.zRot / 2);
					armorModel.leftHindLeg.setRotation(defaultModel.leftLeg.xRot / 2, defaultModel.leftLeg.yRot / 2, defaultModel.leftLeg.zRot / 2);
					armorModel.rightFrontLeg.setRotation(defaultModel.leftLeg.xRot / 2, defaultModel.leftLeg.yRot / 2, defaultModel.leftLeg.zRot / 2);
					armorModel.leftFrontLeg.setRotation(defaultModel.rightLeg.xRot / 2, defaultModel.rightLeg.yRot / 2,
							defaultModel.rightLeg.zRot / 2);
					return armorModel;
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "textures/entity/illager/ravager.png";
		}
	}
}
