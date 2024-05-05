package presentacion;

import negocio.TOPedido;

import javax.swing.*;

public class GUIPedido extends JPanel {
    public GUIPedido(TOPedido toPedido, JButton backButton) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new JLabel("ID: " + toPedido.getID()));
        this.add(new JLabel("Direccion: " + toPedido.getDireccion()));
        this.add(new JLabel("IDCesta: " + toPedido.getIDCesta()));
        this.add(new JLabel("IDUsuario: " + toPedido.getIDUsuario()));
        this.add(new JLabel("Status: " + toPedido.getStatus()));
        this.add(new JLabel("Fecha: " + toPedido.getFecha()));
        //TODO Hacer tabla de articulos en pedido, boton de cancelar, etc
        this.add(backButton);
    }
}
