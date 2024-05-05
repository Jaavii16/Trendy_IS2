package negocio;

import java.io.Serializable;
import java.util.Date;

public class TOPedido implements Serializable {

    private int ID;
    private String direccion;

    private String status;
    private Date fecha;

    private TOACestaPedido toaCestaPedido;

    public TOPedido() {
    }

    public int getID() {
        return ID;
    }

    public TOPedido setID(int ID) {
        this.ID = ID;
        return this;
    }

    public String getDireccion() {
        return direccion;
    }

    public TOPedido setDireccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public TOACestaPedido getToACestaPedido() {
        return toaCestaPedido;
    }

    public TOPedido setToACestaPedido(TOACestaPedido toaCestaPedido) {
        this.toaCestaPedido = toaCestaPedido;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TOPedido setStatus(String status) {
        this.status = status;
        return this;
    }

    public Date getFecha() {
        return fecha;
    }

    public TOPedido setFecha(Date fecha) {
        this.fecha = fecha;
        return this;
    }
}
