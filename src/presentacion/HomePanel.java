package presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomePanel extends MainGUIPanel {

    private final GUIWindow mainWindow;
    private JPanel contentPanel;
    private final SAFacade saFachade;
    private PedidoView lastPedido;
    private JPanel jpArticulosExclusivos;

    public HomePanel(GUIWindow mainWindow, SAFacade saFachade) {
        super();
        this.mainWindow = mainWindow;
        this.saFachade = saFachade;
        initGUI();
    }

    private void initGUI() {

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        contentPanel.add(Box.createVerticalGlue());

        JLabel jlWelcome;
        if (saFachade.isLogged()) {
            jlWelcome = new JLabel("Bonjour invité, " + saFachade.getLoggedUser().getUsername());
        } else {
            jlWelcome = new JLabel("Benvinguts");
        }
        jlWelcome.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        contentPanel.add(jlWelcome);


        contentPanel.add(Box.createVerticalGlue());


        jpArticulosExclusivos = new JPanel();
        jpArticulosExclusivos.setLayout(new BoxLayout(jpArticulosExclusivos, BoxLayout.X_AXIS));
        jpArticulosExclusivos.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        jpArticulosExclusivos.add(Box.createHorizontalGlue());
        putArticulosExclusivos();

        JScrollPane jspArticulosExclusivos = new JScrollPane(jpArticulosExclusivos,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        contentPanel.add(jspArticulosExclusivos);


        contentPanel.add(Box.createVerticalGlue());


        JPanel jpLastPedido = new JPanel();
        jpLastPedido.setLayout(new BoxLayout(jpLastPedido, BoxLayout.Y_AXIS));
        jpLastPedido.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        updatePedido(jpLastPedido);

        jpLastPedido.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainWindow.showPedido(lastPedido);
            }
        });

        contentPanel.add(jpLastPedido);

        setViewportView(contentPanel);
    }

    private void putArticulosExclusivos() {
        jpArticulosExclusivos.removeAll();
        for (ArticuloView articulo : saFachade.getArticulosExclusivos()) {
            jpArticulosExclusivos.add(articulo);
            jpArticulosExclusivos.add(Box.createHorizontalGlue());
        }
    }

    private void emptyLastPedido(JPanel jpLastPedido) {
        jpLastPedido.removeAll();

        JPanel jpLastPedidoTitle = new JPanel(new BorderLayout());
        jpLastPedidoTitle.add(new JLabel("No tens cap comanda activa"), BorderLayout.WEST);
        jpLastPedido.add(jpLastPedidoTitle);
    }

    private void notEmptyLastPedido(JPanel jpLastPedido) {

        jpLastPedido.removeAll();

        JPanel jpLastPedidoTitle = new JPanel(new BorderLayout());
        jpLastPedidoTitle.add(new JLabel("Últim comanda: + " + lastPedido.getID()), BorderLayout.WEST);
        jpLastPedidoTitle.add(new JLabel(lastPedido.getFecha().toString()), BorderLayout.EAST);
        jpLastPedido.add(jpLastPedidoTitle);

        JPanel jpLastPedidoStatus = new JPanel(new BorderLayout());
        jpLastPedidoStatus.add(new JLabel("Estat: " + lastPedido.getStatus().toString()), BorderLayout.WEST);
        if (lastPedido.getStatus() == TOStatusPedido.REPARTO) {
            JButton cancelarPedidoButton = new JButton("Cancelar pedido");
            cancelarPedidoButton.addActionListener(e -> {
                int sel = JOptionPane.showConfirmDialog(this, "Estas seguro de que quieres cancelar el pedido?", "Cancelar pedido", JOptionPane.YES_NO_OPTION);
                if (sel == JOptionPane.YES_OPTION) {
                    saFachade.cancelarPedido(lastPedido);
                    jpLastPedidoStatus.remove(cancelarPedidoButton);
                    lastPedido = saFachade.getLastPedido();
                    if (lastPedido != null) {
                        notEmptyLastPedido(jpLastPedido);
                    } else {
                        emptyLastPedido(jpLastPedidoStatus);
                    }
                    jpLastPedidoStatus.revalidate();
                    jpLastPedidoStatus.repaint();
                }
            });

            jpLastPedidoStatus.add(cancelarPedidoButton, BorderLayout.EAST);
        }
        jpLastPedido.add(jpLastPedidoStatus);
    }

    private void updatePedido(JPanel jpLastPedido) {
        lastPedido = saFachade.getLastPedido();

        if (lastPedido != null) {
            notEmptyLastPedido(jpLastPedido);
        } else {
            emptyLastPedido(jpLastPedido);
        }
    }

    @Override
    public void update() {
        putArticulosExclusivos();
        updatePedido(jpArticulosExclusivos);
    }

    @Override
    public void reset() {
        getVerticalScrollBar().setValue(0);
    }
}
