package presentacion;

import javax.swing.*;

public abstract class MainGUIPanel extends JScrollPane {
    protected MainGUIPanel() {
        super(null, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }

    public abstract void update();

    public abstract void reset();
}
