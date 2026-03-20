package org.fiddlemc.fiddle.client.ui.loadingoverlay.mixin;

import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LoadingOverlay.class)
public interface LoadingOverlayLogoAccessor {

    @Accessor("MOJANG_STUDIOS_LOGO_LOCATION")
    static void setMojangStudiosLogoLocation(Identifier id) {
        throw new UnsupportedOperationException();
    }

}
