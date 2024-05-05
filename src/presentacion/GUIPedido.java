package presentacion;

import negocio.SAFacade;
import negocio.TOAArticuloEnPedido;
import negocio.TOPedido;
import negocio.tArticulo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUIPedido extends JPanel {
    public GUIPedido(SAFacade saFacade, GUIWindow mainWindow, TOPedido toPedido, JButton backButton) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel infoPanel = new JPanel();

        JLabel idLabel = new JLabel("ID: " + toPedido.getID());
        JLabel fechaLabel = new JLabel("Fecha: " + toPedido.getFecha());
        JLabel estadoLabel = new JLabel("Estado: " + toPedido.getStatus());
        JLabel precioLabel = new JLabel("Precio: " + toPedido.getTOAArticulosEnPedido().getArticulosSet().stream().mapToDouble(TOAArticuloEnPedido::getPrecio).sum());

        infoPanel.add(idLabel);
        infoPanel.add(fechaLabel);
        infoPanel.add(estadoLabel);
        infoPanel.add(precioLabel);

        this.add(infoPanel);

        JPanel articulosPanel = new JPanel();

        toPedido.getTOAArticulosEnPedido().getArticulosSet().forEach(toaArticuloEnPedido -> {
            tArticulo articulo = saFacade.buscarArticulo(toaArticuloEnPedido.getToArticuloEnCesta().getIdArticulo());

            JPanel jpArticulo = new JPanel(new BorderLayout());
            jpArticulo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            jpArticulo.setPreferredSize(new Dimension(100, 100));
            jpArticulo.setBackground(Color.WHITE);
            JTextArea jtaNombre = new JTextArea(articulo.getNombre() + " " + toaArticuloEnPedido.getToArticuloEnCesta().getTalla() + " " + toaArticuloEnPedido.getToArticuloEnCesta().getColor() + " x" + toaArticuloEnPedido.getToArticuloEnCesta().getCantidad());
            jtaNombre.setEditable(false);
            jtaNombre.setLineWrap(true);
            jtaNombre.setWrapStyleWord(true);
            jtaNombre.getCaret().deinstall(jtaNombre);
            jtaNombre.setBackground(null);
            jpArticulo.add(jtaNombre, BorderLayout.CENTER);
            jpArticulo.add(new JLabel(articulo.getSubcat()), BorderLayout.NORTH);
            jpArticulo.add(new JLabel(toaArticuloEnPedido.getPrecio() + "€"), BorderLayout.SOUTH);
            jpArticulo.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new Thread(() -> mainWindow.goToArticulo(articulo.getID())).start();
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
            articulosPanel.add(jpArticulo);
        });

        this.add(articulosPanel);

        this.add(backButton);
    }
}
