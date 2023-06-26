package rinf.multitoolrinf.client.gui.Widgets.Buttons;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;
import rinf.multitoolrinf.Identifiers;

public class RCloseButton extends PressableWidget {
    protected final PressAction onPress;
    public RCloseButton(int x, int y, PressAction onPress) {
        super(x, y, 20, 20, Text.empty());
        this.onPress = onPress;
    }

    @Override
    protected void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.isHovered()) context.setShaderColor(1.0f, 0.7f, 0.7f, 1.0f);
        context.drawNineSlicedTexture(Identifiers.DEMO_BACKGROUND_TEXTURE, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 5, 248, 166, 0, 0);
        context.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        context.drawTexture(Identifiers.CLOSE_ICON, this.getX() + 5, this.getY() + 5, 0, 0, 10, 10, 10, 10);
    }

    @Override
    public void onPress() {
        this.onPress.onPress(this);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    @Environment(value= EnvType.CLIENT)
    public static interface PressAction {
        public void onPress(RCloseButton var1);
    }
}
