package net.mcreator.crimson_steves_mobs.client.renderer;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.MultiBufferSource;

import net.mcreator.crimson_steves_mobs.entity.BigSnowBallEntity;

import com.mojang.blaze3d.vertex.PoseStack;

public class BigSnowBallRenderer extends ThrownItemRenderer<BigSnowBallEntity> {
	public BigSnowBallRenderer(EntityRendererProvider.Context context) {
		super(context, 4, false);
	}

	public void render(BigSnowBallEntity p_116085_, float p_116086_, float p_116087_, PoseStack p_116088_, MultiBufferSource p_116089_,
			int p_116090_) {
		super.render(p_116085_, p_116086_, p_116087_, p_116088_, p_116089_, p_116090_);
	}
}
