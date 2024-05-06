package negocio;

import java.time.LocalDateTime;
import java.util.Objects;

public class TOArticuloEnCesta implements Comparable<TOArticuloEnCesta>, java.io.Serializable {

    private int idArticulo;
    private int cantidad;
    private BOStock.Talla talla;
    private BOStock.Color color;
    private LocalDateTime fechaAñadido;

    public int getIdArticulo() {
        return idArticulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public BOStock.Talla getTalla() {
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

    public TOArticuloEnCesta setTalla(BOStock.Talla talla) {
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
        if (this.equals(o))
            return 0;
        return fechaAñadido.compareTo(o.fechaAñadido);
    }
}
