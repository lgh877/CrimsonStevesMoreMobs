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

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;

import net.mcreator.crimson_steves_mobs.entity.EnderRavagerEntity;
import net.mcreator.crimson_steves_mobs.client.model.ModelanimatedRavagerModel;

@OnlyIn(Dist.CLIENT)
public class EnderRavagerModel<T extends EnderRavagerEntity> extends ModelanimatedRavagerModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("workspace", "ender_ravager_model"), "main");

	public EnderRavagerModel(ModelPart p_170889_) {
		super(p_170889_);
	}

	public void prepareMobModel(T p_103621_, float p_103622_, float p_103623_, float p_103624_) {
		super.prepareMobModel(p_103621_, p_103622_, p_103623_, p_103624_);
		this.neck.x = (float) (Math.random() - 0.5) * p_103621_.getAttackDelayTime() / 4;
		this.neck.y += (float) (Math.random() - 0.5) * p_103621_.getAttackDelayTime() / 4;
	}
}
