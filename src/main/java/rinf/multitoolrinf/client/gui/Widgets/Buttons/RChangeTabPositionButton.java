package rinf.multitoolrinf.client.gui.Widgets.Buttons;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;
import rinf.multitoolrinf.Identifiers;
import rinf.multitoolrinf.client.gui.Tab.TabPanel;

public class RChangeTabPositionButton extends PressableWidget {
    public RChangeTabPositionButton(int x, int y) {
        super(x, y, 24, 8, Text.empty());
    }

    @Override
    protected void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        if (TabPanel.getPosition() == TabPanel.Position.LEFT) {
            context.drawTexture(Identifiers.CHANGE_TAB_POSITION_ARROW_TO_RIGHT, this.getX(), this.getY(), 0, 0, 24, 8, 24, 8);
        } else {
            context.drawTexture(Identifiers.CHANGE_TAB_POSITION_ARROW_TO_LEFT, this.getX(), this.getY(), 0, 0, 24, 8, 24, 8);
        }
    }

    @Override
    public void onPress() {
        if (TabPanel.getPosition() == TabPanel.Position.LEFT) {
            TabPanel.setPosition(TabPanel.Position.RIGHT);
        } else {
            TabPanel.setPosition(TabPanel.Position.LEFT);
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}
