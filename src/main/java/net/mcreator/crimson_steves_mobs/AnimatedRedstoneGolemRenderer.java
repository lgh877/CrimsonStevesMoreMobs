/**
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside net.mcreator.workspace as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
*/
package net.mcreator.crimson_steves_mobs;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.SkullModelBase;

import java.util.Map;

import com.mojang.math.Vector3f;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.authlib.GameProfile;

public abstract class AnimatedRedstoneGolemRenderer<T extends Mob, M extends AnimatedRedstoneGolemModel<T>> extends MobRenderer<T, M> {
	public AnimatedRedstoneGolemRenderer(EntityRendererProvider.Context context, M model, float size) {
		super(context, model, size);
		this.addLayer(new CustomItemInHandLayer<>(this, 0.6, 3f, context.getItemInHandRenderer()));
		this.addLayer(new ChadVindicatorHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
	}

	protected void scale(T p_116314_, PoseStack p_116315_, float p_116316_) {
		float f = p_116314_.getScale();
		this.shadowRadius = f * 1.6f;
		p_116315_.scale(f, f, f);
	}

	/*
	@Override
	public ResourceLocation getTextureLocation(CrudeRedstoneGolemEntity entity) {
		return new ResourceLocation("workspace:textures/redstone-golem-on-planetminecraft-com.png");
	}
	*/
	@OnlyIn(Dist.CLIENT)
	private class ChadVindicatorHeadLayer<T extends LivingEntity, M extends AnimatedRedstoneGolemModel<T>> extends RenderLayer<T, M> {
		private final float scaleX;
		private final float scaleY;
		private final float scaleZ;
		private final Map<SkullBlock.Type, SkullModelBase> skullModels;
		private final ItemInHandRenderer itemInHandRenderer;

		public ChadVindicatorHeadLayer(RenderLayerParent<T, M> p_174475_, EntityModelSet p_174476_, ItemInHandRenderer p_234827_) {
			this(p_174475_, p_174476_, 1.0F, 1.0F, 1.0F, p_234827_);
		}

		public ChadVindicatorHeadLayer(RenderLayerParent<T, M> p_174478_, EntityModelSet p_174479_, float p_174480_, float p_174481_, float p_174482_,
				ItemInHandRenderer p_234827_) {
			super(p_174478_);
			this.scaleX = p_174480_;
			this.scaleY = p_174481_;
			this.scaleZ = p_174482_;
			this.skullModels = SkullBlockRenderer.createSkullRenderers(p_174479_);
			this.itemInHandRenderer = p_234827_;
		}

		public void render(PoseStack p_116731_, MultiBufferSource p_116732_, int p_116733_, T p_116734_, float p_116735_, float p_116736_,
				float p_116737_, float p_116738_, float p_116739_, float p_116740_) {
			ItemStack itemstack = p_116734_.getItemBySlot(EquipmentSlot.HEAD);
			if (!itemstack.isEmpty()) {
				Item item = itemstack.getItem();
				p_116731_.pushPose();
				p_116731_.scale(this.scaleX, this.scaleY, this.scaleZ);
				this.getParentModel().translateToHead(p_116731_);
				if (item instanceof BannerItem)
					p_116731_.translate(0, 0.6, 0);
				if (item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof AbstractSkullBlock) {
					float f2 = 1.1875F;
					p_116731_.scale(1.1875F, -1.1875F, -1.1875F);
					GameProfile gameprofile = null;
					if (itemstack.hasTag()) {
						CompoundTag compoundtag = itemstack.getTag();
						if (compoundtag.contains("SkullOwner", 10)) {
							gameprofile = NbtUtils.readGameProfile(compoundtag.getCompound("SkullOwner"));
						}
					}
					p_116731_.translate(-0.5D, 0.0D, -0.5D);
					SkullBlock.Type skullblock$type = ((AbstractSkullBlock) ((BlockItem) item).getBlock()).getType();
					SkullModelBase skullmodelbase = this.skullModels.get(skullblock$type);
					RenderType rendertype = SkullBlockRenderer.getRenderType(skullblock$type, gameprofile);
					SkullBlockRenderer.renderSkull((Direction) null, 180.0F, p_116735_, p_116731_, p_116732_, p_116733_, skullmodelbase, rendertype);
				} else if (!(item instanceof ArmorItem) || ((ArmorItem) item).getSlot() != EquipmentSlot.HEAD) {
					translateToHead(p_116731_, p_116734_);
					this.itemInHandRenderer.renderItem(p_116734_, itemstack, ItemTransforms.TransformType.HEAD, false, p_116731_, p_116732_,
							p_116733_);
				}
				p_116731_.popPose();
			}
		}

		public void translateToHead(PoseStack p_174484_, T mob) {
			float f = 0.625F;
			float scale = mob.getScale();
			p_174484_.translate(0.0D, -2.3D, 0);
			p_174484_.mulPose(Vector3f.YP.rotationDegrees(180.0F));
			p_174484_.scale(0.625F / scale, -0.625F / scale, -0.625F / scale);
		}
	}
}
