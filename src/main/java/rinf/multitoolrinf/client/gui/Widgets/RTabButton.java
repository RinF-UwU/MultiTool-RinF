package rinf.multitoolrinf.client.gui.Widgets;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import rinf.multitoolrinf.Identifiers;
import rinf.multitoolrinf.client.gui.Tab.AbstractTabMenu;
import rinf.multitoolrinf.client.gui.Tab.TabPanel;

public class RTabButton extends PressableWidget {
    private final Identifier icon;
    private final AbstractTabMenu tabMenu;

    public RTabButton(Identifier icon, AbstractTabMenu tabMenu) {
        super(0, 0, 24, 24, Text.empty());
        this.icon = icon;
        this.tabMenu = tabMenu;
    }


    @Override
    public void onPress() {
        TabPanel.setCurrentTabMenu(tabMenu);
    }

    @Override
    protected void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.isHovered()) context.setShaderColor(0.7f, 0.7f, 0.7f, 1.0f);
        context.drawNineSlicedTexture(Identifiers.DEMO_BACKGROUND_TEXTURE, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 5, 248, 166, 0, 0);
        context.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        context.drawTexture(this.icon, this.getX() + 4, this.getY() + 4, 0, 0, 16, 16, 16, 16);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
    }
}
