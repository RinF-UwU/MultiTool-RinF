package rinf.multitoolrinf.client.gui.Widgets.Containers;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.text.Text;
import rinf.multitoolrinf.client.gui.Tab.AbstractTabMenu;

import java.util.List;

public class RContainer extends ClickableWidget {
    private Borders borders = new Borders(1, 2, 3, 4);
    private AbstractTabMenu parentTabMenu;
    private List<ClickableWidget> content = Lists.newArrayList();

    public static RContainer ContainerChild(AbstractTabMenu parentTabMenu) {
        var container = new RContainer(parentTabMenu.getX(), parentTabMenu.getY(), parentTabMenu.getWigth(), parentTabMenu.getHeight());
        container.setParentTabMenu(parentTabMenu);
        return container;
    }

    public RContainer(int x, int y, int width, int height) {
        super(x, y, width, height, Text.empty());
        this.setX(x);
        this.setY(y);;
        this.setWidth(width);
        this.setHeight(height);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        if (this.parentTabMenu != null) {
            this.setX(parentTabMenu.getX());
            this.setY(parentTabMenu.getY());
            this.setWidth(parentTabMenu.getWigth());
            this.setHeight(parentTabMenu.getHeight());
        }

        for (ClickableWidget widget : content) {
            var widgetPositionX = widget.getX();
            var widgetPositionY = widget.getY();
            widget.setPosition(widgetPositionX + this.getX(), widgetPositionY + this.getY());
            widget.render(context, mouseX, mouseY, delta);
            widget.setPosition(widgetPositionX, widgetPositionY);
        }

        context.fill(this.getX() + this.borders.left,
                    this.getY() + this.borders.top,
                    (this.getWidth() + this.getX()) - this.borders.right,
                    (this.getHeight() + this.getY()) - this.borders.down,
                    -200);
    }

    @Override
    protected void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {

    }

    @Override
    public void playDownSound(SoundManager soundManager) {
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    public void addDrawableElement(ClickableWidget widget) {
        this.content.add(widget);
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public AbstractTabMenu getParentTabMenu() {
        return parentTabMenu;
    }

    public void setParentTabMenu(AbstractTabMenu parentTabMenu) {
        this.parentTabMenu = parentTabMenu;
    }

    public Borders getBorders() {
        return borders;
    }

    public void setBorders(Borders borders) {
        this.borders = borders;
    }

    public static class Borders {
        public int top;
        public int down;
        public int left;
        public int right;
        public Borders(int top, int down, int left, int right) {
            this.top = top;
            this.down = down;
            this.left = left;
            this.right = right;
        }
    }
}
