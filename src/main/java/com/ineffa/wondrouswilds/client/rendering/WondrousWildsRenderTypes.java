package com.ineffa.wondrouswilds.client.rendering;

import com.ineffa.wondrouswilds.WondrousWilds;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

public class WondrousWildsRenderTypes extends RenderLayer {

    public WondrousWildsRenderTypes(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }

    private static final Function<Identifier, RenderLayer> TRANSLUCENT_GLOW = Util.memoize(texture -> {
        RenderPhase.Texture texture2 = new RenderPhase.Texture(texture, false, false);
        return RenderLayer.of(WondrousWilds.MOD_ID + ":translucent_glow", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, false, true, MultiPhaseParameters.builder().shader(EYES_SHADER).texture(texture2).transparency(TRANSLUCENT_TRANSPARENCY).writeMaskState(COLOR_MASK).build(false));
    });

    public static RenderLayer getTranslucentGlow(Identifier texture) {
        return TRANSLUCENT_GLOW.apply(texture);
    }
}
