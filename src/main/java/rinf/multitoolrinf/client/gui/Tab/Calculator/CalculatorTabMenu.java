package rinf.multitoolrinf.client.gui.Tab.Calculator;

import rinf.multitoolrinf.client.gui.Tab.AbstractTabMenu;
import rinf.multitoolrinf.client.gui.Tab.TabPanel;
import rinf.multitoolrinf.client.gui.Widgets.Containers.RContainer;
import rinf.multitoolrinf.mixin.HandlerScreenMixin;

public class CalculatorTabMenu extends AbstractTabMenu {
    RContainer mainContainer = RContainer.ContainerChild(this);
    @Override
    protected void init(int x) {
        super.init(x);
        //this.mainContainer.setBorders(new RContainer.Borders(1, 2, 3, 4));
        this.addDrawableElement(mainContainer);
    }
}
