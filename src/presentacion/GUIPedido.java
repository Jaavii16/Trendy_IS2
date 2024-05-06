package presentacion;

import negocio.*;

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

            if(articulo !=null){
                JPanel jpArticulo = new JPanel(new BorderLayout());
                jpArticulo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                jpArticulo.setPreferredSize(new Dimension(100, 100));
                jpArticulo.setBackground(Color.WHITE);
                JTextArea jtaNombre = new JTextArea(articulo.getNombre() + " " + toaArticuloEnPedido.getToArticuloEnCesta().getTalla() + " " + toaArticuloEnPedido.getToArticuloEnCesta().getColor());
                jtaNombre.setEditable(false);
                jtaNombre.setLineWrap(true);
                jtaNombre.setWrapStyleWord(true);
                jtaNombre.getCaret().deinstall(jtaNombre);
                jtaNombre.setBackground(null);
                jpArticulo.add(jtaNombre, BorderLayout.CENTER);
                jpArticulo.add(new JLabel(articulo.getSubcat()), BorderLayout.NORTH);
                jpArticulo.add(new JLabel(toaArticuloEnPedido.getPrecio() + "€ x" + toaArticuloEnPedido.getToArticuloEnCesta().getCantidad()), BorderLayout.SOUTH);
                jpArticulo.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        mainWindow.goToArticulo(articulo.getID());
                        revalidate();
                        repaint();
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
            }
        });

        this.add(articulosPanel);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(backButton);
        buttonsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, backButton.getPreferredSize().height));

        if (toPedido.getStatus().equalsIgnoreCase("REPARTO")) {
            JButton confirmButton = new JButton("Confirmar recepción");
            confirmButton.addActionListener(e -> {
                int sel = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que quieres confirmar la recepción del pedido?", "Confirmar recepción", JOptionPane.YES_NO_OPTION);

                if (sel != JOptionPane.YES_OPTION) {
                    return;
                }

                try{
                    saFacade.cambiarStatus(toPedido.getID(), TOStatusPedido.ENTREGADO);
                    estadoLabel.setText("Estado: ENTREGADO");
                    JOptionPane.showMessageDialog(this, "Entrega confirmada con exito");
                } catch (HeadlessException ex) {
                    JOptionPane.showMessageDialog(this, "No se ha podido confirmar la recepcion del pedido: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            });
            buttonsPanel.add(confirmButton);
        }

        if (toPedido.getStatus().equalsIgnoreCase("REPARTO") || toPedido.getStatus().equalsIgnoreCase("ENTREGADO")) {
            JButton cancelButton = new JButton("Cancelar/devolver");
            cancelButton.addActionListener(e -> {
                int sel = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que quieres cancelar el pedido?", "Cancelar pedido", JOptionPane.YES_NO_OPTION);

                if (sel != JOptionPane.YES_OPTION) {
                    return;
                }
                try{
                    saFacade.cancelarPedido(toPedido.getID());
                    estadoLabel.setText("Estado: CANCELADO");
                    JOptionPane.showMessageDialog(this, "Pedido cancelado con exito");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "No se ha podido cancelar el pedido: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }

            });
            buttonsPanel.add(cancelButton);
        }

        this.add(buttonsPanel);
    }
}
