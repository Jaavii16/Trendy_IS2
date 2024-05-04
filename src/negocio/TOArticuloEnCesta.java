package negocio;

import java.time.LocalDateTime;
import java.util.Objects;

public class TOArticuloEnCesta implements Comparable<TOArticuloEnCesta>, java.io.Serializable {

    public enum Talla {
        XS, S, M, L, XL
    }

    private int idArticulo;
    private int cantidad;
    private Talla talla;
    private BOStock.Color color;
    private LocalDateTime fechaAñadido;

    public int getIdArticulo() {
        return idArticulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Talla getTalla() {
        return talla;
    }

    public LocalDateTime getFechaAñadido() {
        return fechaAñadido;
    }

    public BOStock.Color getColor() {
        return color;
    }

    public TOArticuloEnCesta setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
        return this;
    }

    public TOArticuloEnCesta setCantidad(int cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public TOArticuloEnCesta setTalla(Talla talla) {
        this.talla = talla;
        return this;
    }

    public TOArticuloEnCesta setFechaAñadido(LocalDateTime fechaAñadido) {
        this.fechaAñadido = fechaAñadido;
        return this;
    }

    public TOArticuloEnCesta setColor(BOStock.Color color) {
        this.color = color;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TOArticuloEnCesta that = (TOArticuloEnCesta) o;
        return idArticulo == that.idArticulo && talla == that.talla && color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idArticulo, talla, color);
    }

    @Override
    public int compareTo(TOArticuloEnCesta o) {
        if (this.equals(o)) //TODO Pensar si esto va asi o si quitar el equals
            return 0;
        return fechaAñadido.compareTo(o.fechaAñadido);
    }
}
