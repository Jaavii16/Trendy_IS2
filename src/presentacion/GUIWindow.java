package presentacion;

import negocio.SAFacade;

import negocio.TOPedido;
import org.apache.commons.lang3.function.TriFunction;
import org.apache.commons.lang3.tuple.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.function.BiConsumer;

public class GUIWindow extends JFrame {

    private final SAFacade saFacade;

    private Thread introAnimationThread;

    private JPanel mainPanel;

    private JPanel controlPanel;

    private Pair<JButton, Integer> lastPressedButton;
    private JScrollPane lastPanel;

    public GUIWindow(SAFacade saFacade) {
        this.saFacade = saFacade;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setIconAndPlayIntroAnimation();
        initPanels();
        waitForAnimation();
    }

    private void setIconAndPlayIntroAnimation() {
        try {
            Random random = new Random();
            int loadingTimeMs = random.nextInt(1000, 3000);

            File img = new File("resources/imgs/trendy_logo.png"); //TODO Usar getResource
            BufferedImage imgBuffered = ImageIO.read(img);

            // Define los bordes a recortar
            int margin = 130;

            // Obtiene las dimensiones de la imagen original
            int originalWidth = imgBuffered.getWidth();
            int originalHeight = imgBuffered.getHeight();

            // Define las dimensiones de la imagen recortada
            int croppedWidth = originalWidth - 2 * margin;
            int croppedHeight = originalHeight - 2 * margin;

            // Crea la imagen recortada
            BufferedImage croppedImage = imgBuffered.getSubimage(margin, margin, croppedWidth, croppedHeight);

            setIconImage(croppedImage);

            ImageAnimation introAnimation = new ImageAnimation(imgBuffered, loadingTimeMs, 1.5);

            introAnimationThread = new Thread(introAnimation::play);

            setContentPane(introAnimation);
            pack();
            setLocationRelativeToMouseAndSetSize();
            setVisible(true);

            try {
                Thread.sleep(random.nextLong(500, 1000));
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), e.getMessage(), "Error al cargar Trendy", JOptionPane.ERROR_MESSAGE);
            }

            introAnimationThread.start();

            Timer animationTimer = new Timer(loadingTimeMs, e -> introAnimation.stop());
            animationTimer.setRepeats(false);
            animationTimer.start();
        } catch (IOException ignored) {

        }
    }

    private void initPanels() {
        mainPanel = new JPanel(new BorderLayout());

        BiConsumer<JButton, JScrollPane> changePanel = changePanelAction();

        BiConsumer<JButton, MainGUIPanel> buttonAction = buttonAction(changePanel);

        TriFunction<String, MainGUIPanel, BiConsumer<JButton, MainGUIPanel>, JButton> buttonCreator = buttonCreatorFunction();

        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));

        MainGUIPanel homePanel = new HomePanel(this, saFacade);
        MainGUIPanel userPanel = new GUIPerfil(saFacade);
        MainGUIPanel cestaPanel = new GUICesta(saFacade);
        MainGUIPanel searchPanel = new GUIPpalCategorias(saFacade);
        GUILogin authDialog = new GUILogin(saFacade);

        controlPanel.add(Box.createHorizontalGlue());
        JButton homeButton = buttonCreator.apply("Home", homePanel, buttonAction);
        lastPressedButton = Pair.of(homeButton, 0);
        controlPanel.add(Box.createHorizontalGlue());
        buttonCreator.apply("Search", searchPanel, buttonAction);
        controlPanel.add(Box.createHorizontalGlue());
        buttonCreator.apply("Cesta", cestaPanel, buttonAction);
        controlPanel.add(Box.createHorizontalGlue());
        buttonCreator.apply("User", userPanel, (button, panel) -> {
            if (!saFacade.getUsuario()) {
                authDialog.open(this);
            } else {
                buttonAction.accept(button, panel);
            }
        });
        controlPanel.add(Box.createHorizontalGlue());

        lastPanel = homePanel;
        mainPanel.add(homePanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
    }

    private TriFunction<String, MainGUIPanel, BiConsumer<JButton, MainGUIPanel>, JButton> buttonCreatorFunction() {
        return (text, panel, action) -> {
            JButton button = new JButton(text);
            button.addActionListener(e -> action.accept(button, panel));
            controlPanel.add(button);
            return button;
        };
    }

    private BiConsumer<JButton, MainGUIPanel> buttonAction(BiConsumer<JButton, JScrollPane> changePanel) {
        return (button, panel) -> {
            if (lastPressedButton.getLeft() == button) {
                if (lastPressedButton.getRight() <= 1) {
                    panel.update();
                } else {
                    panel.reset();
                }
                lastPressedButton = Pair.of(button, lastPressedButton.getRight() + 1);
            } else {
                lastPressedButton = Pair.of(button, 0);
                changePanel.accept(button, panel);
            }
        };
    }

    private BiConsumer<JButton, JScrollPane> changePanelAction() {
        return (button, panel) -> {
            Transitions.makeWhiteFadeTransition(lastPanel, panel, 1, (from, to) -> {
                mainPanel.remove(from);
                mainPanel.add(to, BorderLayout.CENTER);
            });
            lastPanel = panel;
        };
    }

    private void waitForAnimation() {
        if (introAnimationThread != null) {
            try {
                introAnimationThread.join();
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), e.getMessage(), "Error al cargar Trendy", JOptionPane.ERROR_MESSAGE);
            }
        }

        setContentPane(mainPanel);
        pack();
    }

    private void setLocationRelativeToMouseAndSetSize() {
        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        Point point = pointerInfo.getLocation();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        for (GraphicsDevice g : gs) {
            GraphicsConfiguration[] gc = g.getConfigurations();
            for (GraphicsConfiguration c : gc) {
                Rectangle bounds = c.getBounds();
                if (bounds.contains(point)) {
                    if (bounds.getWidth() > bounds.getHeight()) {
                        //distancia proporcional en ambos ejes segun el eje menor
                        //this.setSize((int) ((bounds.getHeight() * 0.75 / (bounds.getHeight())) * bounds.getWidth()), (int) (bounds.getHeight() * 0.75));

                        //distancia igual en ambos ejes segun el eje menor
                        this.setSize((int) (bounds.getHeight() * 0.75), (int) (bounds.getHeight() * 0.75));
                    } else {
                        //distancia proporcional en ambos ejes segun el eje menor
                        //this.setSize((int) (bounds.getWidth() * 0.75), (int) ((bounds.getWidth() * 0.75 / (bounds.getWidth())) * bounds.getHeight()));

                        //distancia igual en ambos ejes segun el eje menor
                        this.setSize((int) (bounds.getWidth() * 0.75), (int) (bounds.getWidth() * 0.75));
                    }
                    int x = (int) (bounds.getX() + (bounds.getWidth() - this.getWidth()) / 2);
                    int y = (int) (bounds.getY() + (bounds.getHeight() - this.getHeight()) / 2);
                    this.setLocation(x, y);
                    return;
                }
            }
        }
    }

    //TODO
    public void showPedido(TOPedido lastPedido) {
    }
}
