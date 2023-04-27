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

import net.minecraft.world.entity.Mob;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import com.mojang.blaze3d.vertex.PoseStack;

public abstract class GolemAnimatedWardenRenderer<T extends Mob, M extends GolemAnimatedWarden<T>> extends MobRenderer<T, M> {
	public GolemAnimatedWardenRenderer(EntityRendererProvider.Context context, M model, float size) {
		super(context, model, size);
		this.addLayer(new CustomItemInHandLayer<>(this, 0.9, 2f, context.getItemInHandRenderer()));
	}

	protected void scale(T p_116314_, PoseStack p_116315_, float p_116316_) {
		float f = p_116314_.getScale();
		this.shadowRadius = f;
		p_116315_.scale(f, f, f);
	}
	/*
	@Override
	public ResourceLocation getTextureLocation(CrudeRedstoneGolemEntity entity) {
		return new ResourceLocation("workspace:textures/redstone-golem-on-planetminecraft-com.png");
	}
	*/
}
