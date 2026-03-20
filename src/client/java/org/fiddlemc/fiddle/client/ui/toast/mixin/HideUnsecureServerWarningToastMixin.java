package org.fiddlemc.fiddle.client.ui.toast.mixin;

import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ToastManager.class)
public abstract class HideUnsecureServerWarningToastMixin {

    @Inject(method = "addToast", at = @At("HEAD"), cancellable = true)
    private void onAddToast(Toast toast, CallbackInfo info) {
        if (toast instanceof SystemToast sys && sys.getToken() == SystemToast.SystemToastId.UNSECURE_SERVER_WARNING) {
            info.cancel();
        }
    }

}
