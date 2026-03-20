package org.fiddlemc.fiddle.client.ui.loadingoverlay;

import net.minecraft.resources.Identifier;
import org.fiddlemc.fiddle.impl.branding.FiddleNamespace;

/**
 * Holder for {@link #IDENTIFIER}.
 */
public final class LogoIdentifier {

    private LogoIdentifier() {
        throw new UnsupportedOperationException();
    }

    public static final Identifier IDENTIFIER = Identifier.fromNamespaceAndPath(FiddleNamespace.FIDDLE, "textures/gui/title/spout.png");

}
