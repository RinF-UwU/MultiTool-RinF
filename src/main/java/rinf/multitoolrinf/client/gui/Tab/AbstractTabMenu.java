package rinf.multitoolrinf.client.gui.Tab;

import com.google.common.collect.Lists;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.Window;

import java.awt.*;
import java.util.List;

public abstract class AbstractTabMenu implements Drawable, Element {
    private static int x = 0;
    private static int y = 0;
    private static int wight = 150;
    private static int height;
    private static final List<ClickableWidget> widgets = Lists.newArrayList();
    public AbstractTabMenu() {
        init(this.getX());
    }

    protected void init(int x) {
        Window window = MinecraftClient.getInstance().getWindow();
        this.setX(x);
        this.setHeight(window.getScaledHeight());
    }

    public void addDrawableElement(ClickableWidget widget) {
        widgets.add(widget);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(x, y, x + wight, y + height, -804253680);

        for (ClickableWidget widget : widgets) {
            widget.render(context, mouseX + this.getX(), mouseY + this.getY(), delta);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (ClickableWidget widget : widgets) {
            widget.mouseClicked(mouseX - this.getX(), mouseY - this.getY(), button);
        }

        return Element.super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void setFocused(boolean focused) {

    }

    @Override
    public boolean isFocused() {
        return false;
    }

    public int getX() { return x; }

    public void setX(int x) { this.x = x; }

    public int getY() { return y; }

    public void setY(int y) { this.y = y; }

    public int getWight() { return wight; }

    public void setWight(int wight) { this.wight = wight; }

    public int getHeight() { return height; }

    public void setHeight(int height) { this.height = height; }
}
