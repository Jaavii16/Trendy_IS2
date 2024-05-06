package presentacion;

import integracion.DBConnection;
import negocio.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class GUIHome extends MainGUIPanel implements UserObserver {

    private final GUIWindow mainWindow;
    private JPanel contentPanel;
    private final SAFacade saFachade;
    private TOPedido lastPedido;
    private JPanel jpArticulosExclusivos;
    private JPanel jpLastPedido;
    private JLabel jlWelcome;

    public GUIHome(SAFacade saFachade, GUIWindow mainWindow) {
        super();
        this.mainWindow = mainWindow;
        this.saFachade = saFachade;
        this.saFachade.registerObserver(this);
        initGUI();
    }

    private void initGUI() {

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel jpHome = new JPanel();
        jpHome.setLayout(new GridLayout(0, 1));
        jpHome.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        jpHome.setBorder(BorderFactory.createTitledBorder("Home"));

        JPanel jpWelcome = new JPanel();
        jlWelcome = new JLabel();
        jlWelcome.setFont(new Font("Arial", Font.BOLD, 20));
        jpWelcome.add(jlWelcome);
        jpHome.add(jpWelcome);


        JPanel jpStatsHoy = new JPanel();
        jpStatsHoy.setLayout(new BoxLayout(jpStatsHoy, BoxLayout.Y_AXIS));

        JLabel jlInfo = new JLabel("Estadisticas de hoy:");
        jlInfo.setFont(new Font("Arial", Font.BOLD, 15));
        jlInfo.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        jpStatsHoy.add(jlInfo);

        LocalDate now = LocalDate.now();
        JLabel jlNumPedidos = new JLabel("Numero de pedidos creados: " +
                saFachade.getAllPedidos()
                        .stream().filter(p -> {
                                    java.sql.Date date = (Date) p.getFecha();
                                    return now.equals(date.toLocalDate());
                                }
                        ).count()
        );
        jlNumPedidos.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        jpStatsHoy.add(jlNumPedidos);

        jpHome.add(jpStatsHoy);


        JPanel jpStatsTotal = new JPanel();
        jpStatsTotal.setLayout(new BoxLayout(jpStatsTotal, BoxLayout.Y_AXIS));

        JLabel jlInfoTotal = new JLabel("Estadisticas generales:");
        jlInfoTotal.setFont(new Font("Arial", Font.BOLD, 15));
        jlInfoTotal.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        jpStatsTotal.add(jlInfoTotal);

        long startTime = System.currentTimeMillis();
        try (Connection ignored = DBConnection.connect()) {
            JLabel jlConectionTime = new JLabel("Tiempo de consulta a base de datos: " + (System.currentTimeMillis() - startTime) + "ms");
            jlConectionTime.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            jpStatsTotal.add(jlConectionTime);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        JLabel jlUsuarios = new JLabel("Numero de usuarios registrados: " + saFachade.readAll().size());
        jlUsuarios.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        jpStatsTotal.add(jlUsuarios);

        AtomicInteger numArticulos = new AtomicInteger();
        Set<Integer> ids = new HashSet<>();
        try {
            saFachade.getCategorias().forEach(cat -> {
                try {
                    saFachade.buscaArticulosCategoria(cat).forEach(art -> {
                        if (!ids.contains(art.getID())) {
                            ids.add(art.getID());
                            numArticulos.addAndGet(1);
                        }
                    });
                } catch (Exception e) {
                }
            });
        } catch (Exception e) {
        }
        JLabel jlArticulos = new JLabel("Numero de articulos: " + numArticulos);
        jlArticulos.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        jpStatsTotal.add(jlArticulos);

        jpHome.add(jpStatsTotal);

        contentPanel.add(jpHome);


        jpArticulosExclusivos = new JPanel();

        putArticulosExclusivos();

        jpArticulosExclusivos.setBorder(new TitledBorder("Articulos exclusivos"));

        contentPanel.add(jpArticulosExclusivos);

        jpLastPedido = new JPanel();

        jpLastPedido.setLayout(new BoxLayout(jpLastPedido, BoxLayout.Y_AXIS));

        updatePedido(jpLastPedido);

        jpLastPedido.setBorder(new TitledBorder("Ultimo pedido"));

        contentPanel.add(jpLastPedido);

        setViewportView(contentPanel);
    }

    private void putArticulosExclusivos() {
        jpArticulosExclusivos.removeAll();
        try {
            List<Articulo> exclusivos = saFachade.buscaArticulosCategoria("Exclusivos");
            exclusivos.forEach(articulo -> {
                JPanel jpArticulo = new JPanel(new BorderLayout());
                jpArticulo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                jpArticulo.setPreferredSize(new Dimension(100, 100));
                jpArticulo.setBackground(Color.WHITE);
                JTextArea jtaNombre = new JTextArea(articulo.getName());
                jtaNombre.setEditable(false);
                jtaNombre.setLineWrap(true);
                jtaNombre.setWrapStyleWord(true);
                jtaNombre.getCaret().deinstall(jtaNombre);
                jtaNombre.setBackground(null);
                jpArticulo.add(jtaNombre, BorderLayout.CENTER);
                jpArticulo.add(new JLabel(articulo.getSubcat().toString()), BorderLayout.NORTH);
                jpArticulo.add(new JLabel(articulo.getPrecio() + "€"), BorderLayout.SOUTH);
                jpArticulo.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        mainWindow.goToArticulo(articulo.getID());
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        jpArticulo.setBackground(Color.LIGHT_GRAY);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        jpArticulo.setBackground(Color.WHITE);
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        jpArticulo.setBackground(Color.GRAY);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        jpArticulo.setBackground(Color.WHITE);
                    }
                });
                jtaNombre.addMouseListener(jpArticulo.getMouseListeners()[0]);
                jpArticulosExclusivos.add(jpArticulo);
            });
        } catch (Exception e) {
            JPanel noHay = new JPanel();
            JLabel noHayArticulosExclusivos = new JLabel("No hay articulos exclusivos");
            noHay.add(noHayArticulosExclusivos);
            jpArticulosExclusivos.add(noHay);
        }

    }

    private void emptyLastPedido(JPanel jpLastPedido) {
        jpLastPedido.removeAll();

        JPanel jpLastPedidoPanel = new JPanel();
        jpLastPedidoPanel.setLayout(new BoxLayout(jpLastPedidoPanel, BoxLayout.Y_AXIS));

        JPanel noHay = new JPanel();
        JLabel noHayPedidos = new JLabel("No hay pedidos");
        noHayPedidos.setFont(new Font("Arial", Font.BOLD, 20));
        noHay.add(noHayPedidos);
        jpLastPedidoPanel.add(noHay);

        JButton crearPedidoButton = new JButton("Hacer pedido");
        crearPedidoButton.addActionListener(e -> mainWindow.showCesta());
        crearPedidoButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        jpLastPedidoPanel.add(crearPedidoButton);

        jpLastPedidoPanel.add(new JPanel());

        jpLastPedido.add(jpLastPedidoPanel);
    }

    private void notEmptyLastPedido(JPanel jpLastPedido) {

        jpLastPedido.removeAll();

        JPanel jpLastPedidoPanel = new JPanel();
        jpLastPedidoPanel.setLayout(new BoxLayout(jpLastPedidoPanel, BoxLayout.Y_AXIS));

        jpLastPedido.add(jpLastPedidoPanel);

        JLabel jlID = new JLabel("ID: " + lastPedido.getID());
        jlID.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        jpLastPedidoPanel.add(jlID);

        JLabel jlFecha = new JLabel("Fecha: " + new SimpleDateFormat("dd/MM/yyyy").format(lastPedido.getFecha()));
        jlFecha.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        jpLastPedidoPanel.add(jlFecha);

        JLabel jlEstado = new JLabel("Estado: " + lastPedido.getStatus().toUpperCase());
        jlEstado.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        jpLastPedidoPanel.add(jlEstado);
        if (lastPedido.getStatus().toUpperCase().equals(TOStatusPedido.REPARTO.toString())) {
            JButton cancelarPedidoButton = new JButton("Cancelar pedido");
            cancelarPedidoButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            cancelarPedidoButton.addActionListener(e -> {
                int sel = JOptionPane.showConfirmDialog(this, "Estas seguro de que quieres cancelar el pedido?", "Cancelar pedido", JOptionPane.YES_NO_OPTION);
                if (sel == JOptionPane.YES_OPTION) {
                    saFachade.cancelarPedido(lastPedido.getID());
                    jpLastPedidoPanel.remove(cancelarPedidoButton);
                    lastPedido = saFachade.getLastPedido();
                    if (lastPedido != null) {
                        notEmptyLastPedido(jpLastPedido);
                    } else {
                        emptyLastPedido(jpLastPedido);
                    }
                    jpLastPedidoPanel.revalidate();
                    jpLastPedidoPanel.repaint();
                }
            });

            jpLastPedidoPanel.add(cancelarPedidoButton);
        }

        JButton irPedidoButton = new JButton("Ver pedido");
        irPedidoButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        irPedidoButton.addActionListener(e -> mainWindow.showPedido(lastPedido));
        jpLastPedidoPanel.add(irPedidoButton);

        JPanel jpWrap = new JPanel();
        jpLastPedidoPanel.add(jpWrap);
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
    public void onUserDataChanged(boolean isAuth, int idUsuario) {
        if (saFachade.getUsuario() != null) {
            jlWelcome.setText("Hola, " + saFachade.getUsuario().getNombre());
        } else {
            jlWelcome.setText("Bienvenido!");
        }
        putArticulosExclusivos();
        updatePedido(jpLastPedido);
    }
}
