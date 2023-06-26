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
import rinf.multitoolrinf.client.gui.Widgets.Buttons.RChangeTabPositionButton;
import rinf.multitoolrinf.client.gui.Widgets.Buttons.RTabButton;

import java.util.List;

public class TabPanel implements Drawable, Element {
    public static boolean isMouseOver;
    public static int x;
    private static int tabButtonsOffsetX;
    public static int windowWidth;
    public static int windowHeight;
    public static final int underPanelHeight = 58;
    private static int scrollValue = 0;
    private static Position position = Position.LEFT;
    private static final List<RTabButton> tabButtons = Lists.newArrayList();
    private static AbstractTabMenu currentTabMenu;
    private static RChangeTabPositionButton changeTabPositionButton = new RChangeTabPositionButton(0, 0);
    public static TabPanel tabPanel = new TabPanel();

    private TabPanel() {
        this.init();

        addTabButton(Identifiers.CALCULATOR_ICON, new CalculatorTabMenu());
    }
    public void init() {
        Window window = MinecraftClient.getInstance().getWindow();
        windowWidth = window.getScaledWidth();
        windowHeight = window.getScaledHeight();
        if (position == Position.LEFT) {
            x = -28;
            tabButtonsOffsetX = 2;
            if (currentTabMenu != null) currentTabMenu.init(0);
        }
        else {
            x = windowWidth - 4;
            tabButtonsOffsetX = 6;
            if (currentTabMenu != null) currentTabMenu.init(windowWidth - currentTabMenu.getWight());
        }
    }
    public void addTabButton(Identifier icon, AbstractTabMenu tabMenu) {
        RTabButton tabButton = new RTabButton(icon, tabMenu);
        tabButton.setPosition(x + tabButtonsOffsetX, tabButtons.size() * 26 + 6);
        tabButtons.add(tabButton);
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        isMouseOver = this.isMouseOver(mouseX, mouseY);
        float m = (delta * 6);
        if (m < 1 && m != 0) m = 1;
        int u;
        int changeTabPositionButtonX;

        if (currentTabMenu != null)
            currentTabMenu.render(context, mouseX, mouseY, delta);

        if (position == Position.LEFT) {
            u = 10;
            if (isMouseOver && x < 0) {
                x += m;
                if (x > 0) x = 0;
            }
            else if (!isMouseOver && x > -28) {
                x -= m;
                if (x < -28) x = -28;
            }
            changeTabPositionButtonX = x + 3;
        } else {
            u = 0;
            if (isMouseOver && x > windowWidth - 32) {
                x -= m;
                if (x < windowWidth - 32) x = windowWidth - 32;
            }
            else if (!isMouseOver && x < windowWidth - 4) {
                x += m;
                if (x > windowWidth - 4) x = windowWidth - 4;
            }
            changeTabPositionButtonX = x + 5;
        }
        context.setShaderColor(0.8f, 0.8f, 0.8f, 1.0f);
        context.drawNineSlicedTexture(Identifiers.DEMO_BACKGROUND_TEXTURE, x, 0, 32, windowHeight, 5, 238, 166, u, 0);
        context.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        int tabButtonNumber = 0;
        for (RTabButton tabButton : tabButtons) {
            tabButton.setY((tabButtonNumber * 26 + 6) + scrollValue);
            tabButton.setPosition(x + tabButtonsOffsetX, tabButton.getY());
            tabButton.render(context, mouseX, mouseY, delta);

            tabButtonNumber++;
        }

        context.drawNineSlicedTexture(Identifiers.DEMO_BACKGROUND_TEXTURE, x, windowHeight - underPanelHeight, 32, underPanelHeight, 5, 238, 166, u, 0);
        changeTabPositionButton.setPosition(changeTabPositionButtonX, windowHeight - 13);
        changeTabPositionButton.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        if (currentTabMenu != null && !isMouseOver) currentTabMenu.mouseClicked(mouseX, mouseY, button);

        if (mouseY < windowHeight - underPanelHeight) {
            for (RTabButton tabButton : tabButtons) {
                tabButton.mouseClicked(mouseX, mouseY, button);
            }
        }

        changeTabPositionButton.mouseClicked(mouseX, mouseY, button);

        return Element.super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (currentTabMenu != null && !isMouseOver) currentTabMenu.mouseReleased(mouseX, mouseY, button);
        return Element.super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (currentTabMenu != null && !isMouseOver) currentTabMenu.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        return Element.super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (isMouseOver) {

            scrollValue += amount * MinecraftClient.getInstance().options.getMouseWheelSensitivity().getValue() * 12;

            if (scrollValue > 0) {
                scrollValue = 0;
            } else if (scrollValue < getMinScrollValue()) {
                scrollValue = getMinScrollValue();
            }
        } else if (currentTabMenu != null) {
            currentTabMenu.mouseScrolled(mouseX, mouseY, amount);
        }

        return Element.super.mouseScrolled(mouseX, mouseY, amount);
    }

    private int getMinScrollValue() {
        if ((tabButtons.size() * 26) < (windowHeight - underPanelHeight - 16))
            return 0;

        return -(tabButtons.size() * 26) + (windowHeight - underPanelHeight - 16);
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX < x + 32 && mouseX > x;
    }

    @Override
    public void setFocused(boolean focused) {
    }

    @Override
    public boolean isFocused() {
        return false;
    }

    public static Position getPosition() { return position; }

    public static void setPosition(Position position) {
        TabPanel.position = position;
        tabPanel.init();
    }

    public static AbstractTabMenu getCurrentTabMenu() {
        return currentTabMenu;
    }

    public static void setTabMenu(AbstractTabMenu tabMenu) {
        currentTabMenu = tabMenu;
        if (position == Position.LEFT) {
            if (currentTabMenu != null) currentTabMenu.init(0);
        }
        else {
            if (currentTabMenu != null) currentTabMenu.init(windowWidth - currentTabMenu.getWight());
        }
    }

    public static void clearTabMenu() {
        currentTabMenu = null;
    }

    public enum Position {
        RIGHT,
        LEFT
    }
}
