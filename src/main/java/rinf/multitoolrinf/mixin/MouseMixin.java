package rinf.multitoolrinf.mixin;

import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rinf.multitoolrinf.client.MouseStats;

@Mixin(Mouse.class)
public class MouseMixin {
    @Inject(at = @At("TAIL"), method = "onMouseScroll")
    private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        MouseStats.amount = vertical;
    }

}