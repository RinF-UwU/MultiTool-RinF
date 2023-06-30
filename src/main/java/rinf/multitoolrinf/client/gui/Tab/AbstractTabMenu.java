package rinf.multitoolrinf.client.gui.Tab;

import com.google.common.collect.Lists;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.Window;
import net.minecraft.util.Colors;
import rinf.multitoolrinf.client.gui.Widgets.Buttons.RCloseButton;
import rinf.multitoolrinf.client.gui.Widgets.Containers.RContainer;

import java.util.List;

public abstract class AbstractTabMenu implements Drawable, Element {
    private boolean resize = false;
    private static int resizeBorderSize = 4;
    private static int x = 0;
    private static int y = 0;
    private static int wigth = 150;
    private static int height;
    private static int windowWight;
    private final List<ClickableWidget> content = Lists.newArrayList();
    private RCloseButton closeButton = new RCloseButton(0, 0, button -> this.close());
    public AbstractTabMenu() {
        init(this.getX());
    }

    protected void init(int x) {
        Window window = MinecraftClient.getInstance().getWindow();
        windowWight = window.getScaledWidth();
        this.setX(x);
        this.setHeight(window.getScaledHeight());
        if (getWigth() > window.getScaledWidth()) setWight(window.getScaledWidth());
        else if (getWigth() < 36) setWight(36);
        this.content.clear();
    }

    public void addDrawableElement(ClickableWidget widget) {
        this.content.add(widget);
    }

    public void clearDrawableElements() {
        this.content.clear();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.setShaderColor(1, 1, 1, 0.5f);
        context.fill(x, y, x + wigth, y + height, -804253680);
        context.setShaderColor(1, 1, 1, 1);

        for (ClickableWidget widget : content) {
            widget.render(context, mouseX + this.getX(), mouseY + this.getY(), delta);
        }

        context.setShaderColor(1, 1, 1, 0.5f);
        if (TabPanel.getPosition() == TabPanel.Position.LEFT) {
            if (mouseX > wigth - resizeBorderSize && mouseX < x + wigth)
                context.fill(wigth - resizeBorderSize, y, x + wigth, y + height, Colors.GRAY);
            else context.fill(wigth - resizeBorderSize + 2, y, x + wigth, y + height, Colors.GRAY);
            closeButton.setPosition(wigth - resizeBorderSize - 24, 6);
        } else {
            if (mouseX > x && mouseX < x + resizeBorderSize)
                context.fill(x, y, x + resizeBorderSize, y + height, Colors.GRAY);
            else context.fill(x, y, x + resizeBorderSize - 2, y + height, Colors.GRAY);
            closeButton.setPosition(x + resizeBorderSize + 4, 6);
        }
        context.setShaderColor(1, 1, 1, 1);
        closeButton.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        closeButton.mouseClicked(mouseX, mouseY, button);

        //Перевірка на початок зміни розміру
        if (TabPanel.getPosition() == TabPanel.Position.LEFT) {
            if (mouseX < wigth && mouseX > wigth - resizeBorderSize) resize = true;
        } else {
            if (mouseX > x && mouseX < x + resizeBorderSize) resize = true;
        }

        for (ClickableWidget widget : content) {
            widget.mouseClicked(mouseX - this.getX(), mouseY - this.getY(), button);
        }

        return Element.super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (resize) resize = false; //Кінець зміни розміру
        return Element.super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {

        //Зміна розміру
        if (resize) {
            if (TabPanel.getPosition() == TabPanel.Position.LEFT) {
                wigth = (int) mouseX + (resizeBorderSize / 2);
            } else {
                wigth += x - mouseX + (resizeBorderSize / 2);
                x = windowWight - wigth;
            }
        }

        if (getWigth() < 36) setWight(36);
        if (TabPanel.getPosition() == TabPanel.Position.RIGHT) x = windowWight - wigth;

        return Element.super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    public void close() {
        TabPanel.clearTabMenu();
    }

    @Override
    public void setFocused(boolean focused) {

    }

    @Override
    public boolean isFocused() {
        return false;
    }

    public static int getResizeBorderSize() {
        return resizeBorderSize;
    }

    public static void setResizeBorderSize(int resizeBorderSize) {
        AbstractTabMenu.resizeBorderSize = resizeBorderSize;
    }

    public int getX() { return x; }

    public void setX(int x) { AbstractTabMenu.x = x; }

    public int getY() { return y; }

    public void setY(int y) { AbstractTabMenu.y = y; }

    public int getWigth() { return wigth; }

    public void setWight(int wight) { AbstractTabMenu.wigth = wight; }

    public int getHeight() { return height; }

    public void setHeight(int height) { AbstractTabMenu.height = height; }
}
