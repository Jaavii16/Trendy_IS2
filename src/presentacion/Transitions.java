package presentacion;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.BiConsumer;

public class Transitions {

    private static volatile Queue<Runnable> transitionQueue = new LinkedList<>();

    private static final Thread transitionThread = new Thread(() -> {
        while (true) {
            if (!transitionQueue.isEmpty()) {
                transitionQueue.poll().run();
            }
        }
    });

    static {
        transitionThread.setName("Transition Thread");
        transitionThread.start();
    }

    /**
     * Makes a transition between two components. Uses a in-mid-out transition, where in is "from", out is "to",
     * and mid is an auxiliar mid component that is used to make the transition. The panel setter is used twice,
     * once to change the panel "from" to the auxiliar panel when the transition starts, and once to change the
     * auxiliar panel to the panel "to" when the transition ends. That means that the panel setter should be able
     * to transition through two components
     *
     * @param from        Component to transition from
     * @param to          Component to transition to
     * @param delay       delay between each step of the transition
     * @param panelSetter used to change from the first component to the second component in the container
     *                    where the transition is happening, read the description for more information
     */
    public static void makeWhiteFadeTransition(Component from, Component to, int delay, BiConsumer<Component, Component> panelSetter) {
        transitionQueue.add(() -> whiteFadeRun(from, to, delay, panelSetter));
    }

    private static void whiteFadeRun(Component from, Component to, int delay, BiConsumer<Component, Component> panelSetter) {
        JPanel printPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        printPanel.setOpaque(false);
        printPanel.setLayout(new OverlayLayout(printPanel));
        panelSetter.accept(from, printPanel);

        printPanel.add(from);

        for (int i = 0; i <= 150; i++) {
            printPanel.setBackground(new Color(204, 204, 204, i));
            try {
                Thread.sleep(delay * 2L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printPanel.revalidate();
            printPanel.repaint();
        }

        printPanel.remove(from);
        printPanel.add(to);

        for (int i = 150; i >= 0; i--) {
            printPanel.setBackground(new Color(204, 204, 204, i));
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printPanel.revalidate();
            printPanel.repaint();
        }

        panelSetter.accept(printPanel, to);
    }
}
