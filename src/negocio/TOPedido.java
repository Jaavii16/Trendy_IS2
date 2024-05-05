package negocio;

import java.io.Serializable;
import java.util.Date;

public class TOPedido implements Serializable {

    private int ID;
    private String direccion;
    private int IDUsuario;
    private String status;
    private Date fecha;

    private TOArticulosEnPedido toArticulosEnPedido;

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

    public TOArticulosEnPedido getTOAArticulosEnPedido() {
        return toArticulosEnPedido;
    }

    public TOPedido setTOAArticulosEnPedido(TOArticulosEnPedido toaCestaPedido) {
        this.toArticulosEnPedido = toaCestaPedido;
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

    public int getIDUsuario() {
        return IDUsuario;
    }

    public TOPedido setIDUsuario(int IDUsuario) {
        this.IDUsuario = IDUsuario;
        return this;
    }

}
