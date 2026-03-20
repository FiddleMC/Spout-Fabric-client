package org.fiddlemc.fiddle.client.ui.loadingoverlay.mixin;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.renderer.texture.MipmapStrategy;
import net.minecraft.client.renderer.texture.ReloadableTexture;
import net.minecraft.client.renderer.texture.TextureContents;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import org.fiddlemc.fiddle.client.ui.loadingoverlay.LogoIdentifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.io.IOException;
import java.io.InputStream;

@Mixin(LoadingOverlay.class)
public abstract class ReplaceLoadingOverlayMixin {

    @Shadow
    @Final
    @Mutable
    private static int LOGO_BACKGROUND_COLOR;

    @Shadow
    @Final
    @Mutable
    private static int LOGO_BACKGROUND_COLOR_DARK;

    @Shadow
    @Final
    @Mutable
    private static int LOGO_SCALE;

    @Shadow
    @Final
    @Mutable
    private static float LOGO_QUARTER_FLOAT;

    @Shadow
    @Final
    @Mutable
    private static int LOGO_QUARTER;

    @Shadow
    @Final
    @Mutable
    private static int LOGO_HALF;

    @Shadow
    @Final
    @Mutable
    private static float LOGO_OVERLAP;

    @Shadow
    @Final
    @Mutable
    private static float SMOOTHING;

    @Shadow
    @Final
    @Mutable
    public static long FADE_OUT_TIME;

    @Shadow
    @Final
    @Mutable
    public static long FADE_IN_TIME;

    @Shadow
    @Final
    @Mutable
    public static Identifier MOJANG_STUDIOS_LOGO_LOCATION;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void fiddle$modifyConstants(CallbackInfo ci) {

        // Background colors (ARGB)
        LOGO_BACKGROUND_COLOR = 0xFF111111;
        LOGO_BACKGROUND_COLOR_DARK = 0xFF111111;

        // Logo sizing
        LOGO_SCALE = 320; // match your 320x320 texture
        LOGO_QUARTER = LOGO_SCALE / 4;
        LOGO_HALF = LOGO_SCALE / 2;
        LOGO_QUARTER_FLOAT = (float) LOGO_QUARTER;

    }

    @Inject(method = "registerTextures", at = @At("HEAD"), cancellable = true)
    private static void fiddle$replaceLogo(TextureManager textureManager, CallbackInfo ci) {
        textureManager.registerAndLoad(LogoIdentifier.IDENTIFIER, new ReloadableTexture(LogoIdentifier.IDENTIFIER) {

            @Override
            public TextureContents loadContents(ResourceManager resourceManager) throws IOException {
                try (InputStream input = resourceManager.open(LogoIdentifier.IDENTIFIER)) {
                    TextureContents result = new TextureContents(NativeImage.read(input),
                        new TextureMetadataSection(true, true, MipmapStrategy.MEAN, 0.0f));
                    return result;
                }
            }

        });

        ci.cancel(); // Prevent vanilla logo instance from being created
    }

    /**
     * Redirect the two specific blit calls in render that draw MOJANG_STUDIOS_LOGO_LOCATION.
     * The ordinal distinguishes the two calls (0 and 1).
     */
    @Redirect(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIIII)V",
            ordinal = 0
        )
    )
    private void redirectBlitFirst(
        GuiGraphics guiGraphics,
        RenderPipeline renderPipeline,
        Identifier texture,
        int i, int j,
        float f, float g,
        int k, int l,
        int m, int n,
        int o, int p,
        int q
    ) {
        if (texture.equals(LogoIdentifier.IDENTIFIER)) {
            // Adjust coordinates so the logo renders fully
            int n2 = (int) ((double) guiGraphics.guiWidth() * (double) 0.5F);
            int p2 = (int) ((double) guiGraphics.guiHeight() * (double) 0.5F);
            double d2 = Math.min((double) guiGraphics.guiWidth() * (double) 0.75F, (double) guiGraphics.guiHeight()) * (double) 0.25F;
            int q2 = (int) (d2 * (double) 0.5F);
            double e2 = d2 * (double) 4.0F;
            int r2 = (int) (e2 * (double) 0.5F);
            int k2 = k * 4 / 5;
            int l2 = l * 4 / 5;
            guiGraphics.blit(renderPipeline, texture, n2 - k2 / 2, p2 - l2, 0f, 0f, k2, l2 * 2, 320, 320, 320, 320, q);
        } else {
            guiGraphics.blit(renderPipeline, texture, i, j, f, g, k, l, m, n, o, p, q);
        }
    }

    @Redirect(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIIII)V",
            ordinal = 1
        )
    )
    private void redirectBlitSecond(
        GuiGraphics guiGraphics,
        RenderPipeline renderPipeline,
        Identifier texture,
        int i, int j,
        float f, float g,
        int k, int l,
        int m, int n,
        int o, int p,
        int q
    ) {
        if (texture.equals(LogoIdentifier.IDENTIFIER)) {
            // Don't draw anything
        } else {
            guiGraphics.blit(renderPipeline, texture, i, j, f, g, k, l, m, n, o, p, q);
        }
    }

}
