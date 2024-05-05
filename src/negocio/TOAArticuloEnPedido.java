package negocio;

import java.util.Objects;

public class TOAArticuloEnPedido {
    private TOArticuloEnCesta toArticuloEnCesta;
    private double precio;

    public TOAArticuloEnPedido(TOArticuloEnCesta toArticuloEnCesta, double precio) {
        this.toArticuloEnCesta = toArticuloEnCesta;
        this.precio = precio;
    }

    public TOArticuloEnCesta getToArticuloEnCesta() {
        return toArticuloEnCesta;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TOAArticuloEnPedido that = (TOAArticuloEnPedido) o;
        return Double.compare(precio, that.precio) == 0 && Objects.equals(toArticuloEnCesta, that.toArticuloEnCesta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toArticuloEnCesta, precio);
    }
}
