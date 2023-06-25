package rinf.multitoolrinf.client.gui.Tab;

import com.google.common.collect.Lists;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.util.Window;
import net.minecraft.util.Identifier;
import rinf.multitoolrinf.Identifiers;
import rinf.multitoolrinf.client.gui.Tab.Calculator.CalculatorTabMenu;
import rinf.multitoolrinf.client.gui.Widgets.RTabButton;

import java.util.List;

public class TabPanel implements Drawable, Element {
    private final Identifier DEMO_BACKGROUND_TEXTURE = Identifier.of(Identifier.DEFAULT_NAMESPACE, "textures/gui/demo_background.png");
    public boolean isMouseOver;
    public int x;
    private int tabButtonsOffsetX;
    public int windowWidth;
    public int windowHeight;
    public final int underPanelHeight = 58;
    private static int scrollValue = 0;
    private Position position = Position.LEFT;
    private static final List<RTabButton> tabButtons = Lists.newArrayList();
    private static AbstractTabMenu currentTabMenu;
    public TabPanel() {
        this.init();

        addTabButton(Identifiers.CALCULATOR_ICON, new CalculatorTabMenu());
        addTabButton(Identifiers.CALCULATOR_ICON, new CalculatorTabMenu());
    }
    protected void init() {
        Window window = MinecraftClient.getInstance().getWindow();
        this.windowWidth = window.getScaledWidth();
        this.windowHeight = window.getScaledHeight();
        if (this.position == Position.LEFT) {
            this.x = -28;
            this.tabButtonsOffsetX = 2;
            if (currentTabMenu != null) currentTabMenu.init(0);
        }
        else {
            x = this.windowWidth - 4;
            this.tabButtonsOffsetX = 6;
            if (currentTabMenu != null) currentTabMenu.init(this.windowWidth - currentTabMenu.getWight());
        }
    }
    public void addTabButton(Identifier icon, AbstractTabMenu tabMenu) {
        RTabButton tabButton = new RTabButton(icon, tabMenu);
        tabButton.setPosition(this.x + this.tabButtonsOffsetX, tabButtons.size() * 26 + 6);
        tabButtons.add(tabButton);
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.isMouseOver = this.isMouseOver(mouseX, mouseY);
        float m = (delta * 6);
        if (m < 1 && m != 0) m = 1;
        int u;

        if (currentTabMenu != null)
            currentTabMenu.render(context, mouseX, mouseY, delta);

        if (this.position == Position.LEFT) {
            u = 10;
            if (this.isMouseOver && this.x < 0) {
                this.x += m;
                if (this.x > 0) this.x = 0;
            }
            else if (!this.isMouseOver && this.x > -28) {
                this.x -= m;
                if (this.x < -28) this.x = -28;
            }
        } else {
            u = 0;
            if (this.isMouseOver && this.x > this.windowWidth - 32) {
                this.x -= m;
                if (this.x < this.windowWidth - 32) this.x = this.windowWidth - 32;
            }
            else if (!this.isMouseOver && this.x < this.windowWidth - 4) {
                this.x += m;
                if (this.x > this.windowWidth - 4) this.x = this.windowWidth - 4;
            }
        }
        context.setShaderColor(0.8f, 0.8f, 0.8f, 1.0f);
        context.drawNineSlicedTexture(this.DEMO_BACKGROUND_TEXTURE, this.x, 0, 32, this.windowHeight, 5, 238, 166, u, 0);
        context.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        int tabButtonNumber = 0;
        for (RTabButton tabButton : tabButtons) {
            tabButton.setY((tabButtonNumber * 26 + 6) + scrollValue);
            tabButton.setPosition(this.x + this.tabButtonsOffsetX, tabButton.getY());
            tabButton.render(context, mouseX, mouseY, delta);

            tabButtonNumber++;
        }

        context.drawNineSlicedTexture(this.DEMO_BACKGROUND_TEXTURE, this.x, this.windowHeight - this.underPanelHeight, 32, this.underPanelHeight, 5, 238, 166, u, 0);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        if (currentTabMenu != null) currentTabMenu.mouseClicked(mouseX, mouseY, button);

        if (mouseY < this.windowHeight - this.underPanelHeight) {
            for (RTabButton tabButton : tabButtons) {
                tabButton.mouseClicked(mouseX, mouseY, button);
            }
        }

        return Element.super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (this.isMouseOver) {

            scrollValue += amount * MinecraftClient.getInstance().options.getMouseWheelSensitivity().getValue() * 12;

            if (scrollValue > 0) {
                scrollValue = 0;
            } else if (scrollValue < getMinScrollValue()) {
                scrollValue = getMinScrollValue();
            }
        }

        return Element.super.mouseScrolled(mouseX, mouseY, amount);
    }

    private int getMinScrollValue() {
        if ((tabButtons.size() * 26) < (this.windowHeight - this.underPanelHeight - 16))
            return 0;

        return -(tabButtons.size() * 26) + (this.windowHeight - this.underPanelHeight - 16);
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX < this.x + 32 && mouseX > this.x;
    }

    @Override
    public void setFocused(boolean focused) {
    }

    @Override
    public boolean isFocused() {
        return false;
    }

    public Position getPosition() { return position; }

    public void setPosition(Position position) {
        this.position = position;
        this.init();
    }

    public static AbstractTabMenu getCurrentTabMenu() {
        return currentTabMenu;
    }

    public static void setCurrentTabMenu(AbstractTabMenu currentTabMenu) {
        TabPanel.currentTabMenu = currentTabMenu;
    }

    public enum Position {
        RIGHT,
        LEFT
    }
}
