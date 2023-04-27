/**
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside net.mcreator.crimson_steves_mobs as this package is managed by MCreator.
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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.Util;

import java.util.function.BiFunction;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;

@OnlyIn(Dist.CLIENT)
public abstract class CustomRenderTypes extends RenderStateShard {
	public CustomRenderTypes(String a1, Runnable a2, Runnable a3) {
		super(a1, a2, a3);
	}

	private static final BiFunction<ResourceLocation, ResourceLocation, RenderType> END_PORTAL = Util.memoize((texture, texture2) -> {
		RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder().setShaderState(RENDERTYPE_END_PORTAL_SHADER)
				.setTextureState(RenderStateShard.MultiTextureStateShard.builder().add(texture, false, false).add(texture2, false, false).build()).setCullState(NO_CULL).createCompositeState(false);
		//I think you'll need to change the name to MODID:name for preventing crash when used with mods that use same code.
		return RenderType.create("custom_end_portal", DefaultVertexFormat.POSITION, VertexFormat.Mode.QUADS, 256, false, false, rendertype$compositestate);
	});

	public static RenderType customEndPortal(ResourceLocation texture) {
		return END_PORTAL.apply(texture, texture);
	}

	public static RenderType customEndPortal2(ResourceLocation texture, ResourceLocation texture2) {
		return END_PORTAL.apply(texture, texture2);
	}
}
