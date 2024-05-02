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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.function.Consumer;

public class GUIWindow extends JFrame {

    private final SAFacade saFacade;

    private Thread introAnimationThread;

    private JPanel mainPanel;

    private JPanel controlPanel;

    private Pair<MainGUIPanel, Integer> lastPanel;
    private MainGUIPanel homePanel;
    private MainGUIPanel userPanel;
    private MainGUIPanel cestaPanel;
    private MainGUIPanel searchPanel;
    private GUILogin authDialog;

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
        autoLogin();
        waitForAnimation();
    }

    private void autoLogin() {
        File login = new File("trendy-storage/login.txt");
        if (login.exists()) {
            String[] credentials = null;
            try {
                FileReader reader = new FileReader(login);
                BufferedReader bufferedReader = new BufferedReader(reader);
                credentials = new String[2];
                credentials[0] = bufferedReader.readLine();
                credentials[1] = bufferedReader.readLine();
            } catch (IOException e) {
            }

            if (credentials != null) {
                saFacade.login(credentials[0], credentials[1]);
            }
        }
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

        JLabel trendy = new JLabel("Trendy");
        trendy.setFont(new Font("Arial", Font.BOLD, 30));
        trendy.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(trendy, BorderLayout.NORTH);

        Consumer<JScrollPane> changePanel = changePanelAction();

        Consumer<MainGUIPanel> buttonAction = buttonAction(changePanel);

        TriFunction<Icon, MainGUIPanel, Consumer<MainGUIPanel>, JButton> buttonCreator = buttonCreatorFunction();

        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 0));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        homePanel = new HomePanel(this, saFacade);
        userPanel = new GUIPerfil(saFacade);
        cestaPanel = new GUICesta(saFacade);
        searchPanel = new GUIPpalCategorias(saFacade);
        authDialog = new GUILogin(saFacade);

        mainPanel.add(homePanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        URL homeURL = ClassLoader.getSystemResource("imgs/home.png");
        URL searchURL = ClassLoader.getSystemResource("imgs/search.png");
        URL cestaURL = ClassLoader.getSystemResource("imgs/shopping_cart.png");
        URL userURL = ClassLoader.getSystemResource("imgs/user.png");

        ImageIcon homeIcon = new ImageIcon(homeURL, "Home");
        homeIcon.setImage(homeIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        ImageIcon searchIcon = new ImageIcon(searchURL, "Search");
        searchIcon.setImage(searchIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        ImageIcon cestaIcon = new ImageIcon(cestaURL, "Cesta");
        cestaIcon.setImage(cestaIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        ImageIcon userIcon = new ImageIcon(userURL, "User");
        userIcon.setImage(userIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));

        buttonCreator.apply(homeIcon, homePanel, buttonAction);
        lastPanel = Pair.of(homePanel, 0);
        buttonCreator.apply(searchIcon, searchPanel, buttonAction);
        buttonCreator.apply(cestaIcon, cestaPanel, buttonAction);
        buttonCreator.apply(userIcon, userPanel, (panel) -> {
            if (saFacade.getUsuario() == null) {
                authDialog.open(this);
            } else {
                buttonAction.accept(panel);
            }
        });
    }

    private TriFunction<Icon, MainGUIPanel, Consumer<MainGUIPanel>, JButton> buttonCreatorFunction() {
        return (icon, panel, action) -> {
            JButton button = new JButton(icon);
            button.addActionListener(e -> action.accept(panel));
            controlPanel.add(button);
            return button;
        };
    }

    private Consumer<MainGUIPanel> buttonAction(Consumer<JScrollPane> changePanel) {
        return (panel) -> {
            if (lastPanel.getLeft() == panel) {
                if (lastPanel.getRight() <= 1) {
                    panel.update();
                } else {
                    panel.reset();
                }
                lastPanel = Pair.of(panel, lastPanel.getRight() + 1);
            } else {
                changePanel.accept(panel);
                lastPanel = Pair.of(panel, 0);
            }
        };
    }

    private Consumer<JScrollPane> changePanelAction() {
        return (panel) -> Transitions.makeWhiteFadeTransition(lastPanel.getLeft(), panel, 1, (from, to) -> {
            mainPanel.remove(from);
            mainPanel.add(to, BorderLayout.CENTER);
        });
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
        revalidate();
        repaint();
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


    public void showPedido(TOPedido lastPedido) {
        buttonAction(changePanelAction()).accept(userPanel);
        //TODO userPanel.showPedido(lastPedido);
    }


    public void showGUIPedidos() {
        buttonAction(changePanelAction()).accept(userPanel);
        //TODO userPanel.goToPedidos();
    }
}
