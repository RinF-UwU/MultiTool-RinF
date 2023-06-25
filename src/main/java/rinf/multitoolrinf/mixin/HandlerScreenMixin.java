package rinf.multitoolrinf.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rinf.multitoolrinf.client.MouseStats;
import rinf.multitoolrinf.client.gui.Tab.TabPanel;

@Mixin(HandledScreen.class)
public abstract class HandlerScreenMixin {

    private static final TabPanel tabPanel = new TabPanel();

    @Inject(at = @At("TAIL"), method = "init")
    protected void init(CallbackInfo ci) {
        tabPanel.setPosition(TabPanel.Position.LEFT);
    }
    @Inject(at = @At("TAIL"), method = "render")
    public void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        tabPanel.render(context, mouseX, mouseY, delta);
    }
    @Inject(at = @At("TAIL"), method = "mouseClicked")
    public void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        tabPanel.mouseClicked(mouseX, mouseY, button);
    }

    public void mouseScrolled(double mouseX, double mouseY, double amount) {
        tabPanel.mouseScrolled(mouseX, mouseY, amount);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void tickHEAD(CallbackInfo ci) {
        if (MouseStats.amount != 0) {
            mouseScrolled(MinecraftClient.getInstance().mouse.getX() / MinecraftClient.getInstance().getWindow().getScaleFactor(), MinecraftClient.getInstance().mouse.getY() / MinecraftClient.getInstance().getWindow().getScaleFactor(), MouseStats.amount);
        }
    }
}
