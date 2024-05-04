package presentacion;

import negocio.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GUIHome extends MainGUIPanel implements AuthObserver {

    private final GUIWindow mainWindow;
    private JPanel contentPanel;
    private final SAFacade saFachade;
    private TOPedido lastPedido;
    private JPanel jpArticulosExclusivos;
    private JPanel jpLastPedido;
    private JLabel jlWelcome;

    public GUIHome(GUIWindow mainWindow, SAFacade saFachade) {
        super();
        this.mainWindow = mainWindow;
        this.saFachade = saFachade;
        this.saFachade.registerObserver(this);
        initGUI();
    }

    private void initGUI() {

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel jpTitle = new JPanel();
        jlWelcome = new JLabel();
        jlWelcome.setFont(new Font("Arial", Font.BOLD, 20));
        jpTitle.add(jlWelcome);
        jpTitle.setBorder(BorderFactory.createTitledBorder("Home"));
        contentPanel.add(jpTitle);

        jpArticulosExclusivos = new JPanel();

        putArticulosExclusivos();

        JScrollPane jspArticulosExclusivos = new JScrollPane(jpArticulosExclusivos, VERTICAL_SCROLLBAR_NEVER, HORIZONTAL_SCROLLBAR_AS_NEEDED);

        jspArticulosExclusivos.setBorder(new TitledBorder("Articulos exclusivos"));

        contentPanel.add(jspArticulosExclusivos);

        jpLastPedido = new JPanel();

        updatePedido(jpLastPedido);

        jpLastPedido.setBorder(new TitledBorder("Ultimo pedido"));

        contentPanel.add(jpLastPedido);

        setViewportView(contentPanel);
    }

    private void putArticulosExclusivos() {
        jpArticulosExclusivos.removeAll();

        List<Articulo> exclusivos = saFachade.buscaArticulosCategoria("Exclusivos");

        if (exclusivos.isEmpty()) {
            jpArticulosExclusivos.add(new JLabel("No hay articulos exclusivos"));
        } else {

            exclusivos.forEach(articulo -> {
                JPanel jpArticulo = new JPanel(new BorderLayout());
                jpArticulo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                jpArticulo.add(new JLabel(articulo.getName()), BorderLayout.NORTH);
                jpArticulo.add(new JLabel(articulo.getPrecio() + "€"), BorderLayout.SOUTH);
                jpArticulo.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        mainWindow.goToArticulo(articulo.getID());
                    }
                });
                jpArticulosExclusivos.add(jpArticulo);
            });
        }

    }

    private void emptyLastPedido(JPanel jpLastPedido) {
        jpLastPedido.removeAll();

        JPanel jpLastPedidoPanel = new JPanel();
        jpLastPedidoPanel.setLayout(new BorderLayout());

        JLabel noHayPedidos = new JLabel("No hay pedidos");
        noHayPedidos.setFont(new Font("Arial", Font.BOLD, 20));
        jpLastPedidoPanel.add(noHayPedidos, BorderLayout.NORTH);
        JButton crearPedidoButton = new JButton("Hacer pedido");
        crearPedidoButton.addActionListener(e -> mainWindow.showCesta());
        jpLastPedidoPanel.add(crearPedidoButton, BorderLayout.SOUTH);

        jpLastPedido.add(jpLastPedidoPanel);
    }

    private void notEmptyLastPedido(JPanel jpLastPedido) {

        jpLastPedido.removeAll();

        JPanel jpLastPedidoTitle = new JPanel(new BorderLayout());
        jpLastPedidoTitle.add(new JLabel("Ultimo pedido: + " + lastPedido.getID()), BorderLayout.WEST);
        jpLastPedidoTitle.add(new JLabel(lastPedido.getFecha().toString()), BorderLayout.EAST);
        jpLastPedido.add(jpLastPedidoTitle);

        JPanel jpLastPedidoStatus = new JPanel(new BorderLayout());

        jpLastPedidoStatus.add(new JLabel("Estado: " + lastPedido.getStatus()), BorderLayout.WEST);
        if (lastPedido.getStatus().toUpperCase().equals(TOStatusPedido.REPARTO.toString())) {
            JButton cancelarPedidoButton = new JButton("Cancelar pedido");
            cancelarPedidoButton.addActionListener(e -> {
                int sel = JOptionPane.showConfirmDialog(this, "Estas seguro de que quieres cancelar el pedido?", "Cancelar pedido", JOptionPane.YES_NO_OPTION);
                if (sel == JOptionPane.YES_OPTION) {
                    saFachade.cancelarPedido(lastPedido.getID());
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

        JButton irPedidoButton = new JButton("Ver pedido");
        irPedidoButton.addActionListener(e -> mainWindow.showPedido(lastPedido));
        jpLastPedido.add(irPedidoButton);
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
        if (saFachade.getUsuario() != null) {
            jlWelcome.setText("Hola, " + saFachade.getUsuario().getNombre());
        } else {
            jlWelcome.setText("Bienvenido!");
        }
        putArticulosExclusivos();
        updatePedido(jpLastPedido);
    }

    @Override
    public void reset() {
        getVerticalScrollBar().setValue(0);
    }

    @Override
    public void onAuthChanged(boolean isAuth, int idUsuario) {
        if (saFachade.getUsuario() != null) {
            jlWelcome.setText("Hola, " + saFachade.getUsuario().getNombre());
        } else {
            jlWelcome.setText("Bienvenido!");
        }
        putArticulosExclusivos();
        updatePedido(jpLastPedido);
    }
}
