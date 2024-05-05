package negocio;

import java.util.Set;

public class TOArticulosEnPedido {

    private Set<TOAArticuloEnPedido> toAArticuloEnPedido;

    public TOArticulosEnPedido(Set<TOAArticuloEnPedido> toAArticuloEnPedido) {
        this.toAArticuloEnPedido = toAArticuloEnPedido;
    }

    public Set<TOAArticuloEnPedido> getArticulosSet() {
        return toAArticuloEnPedido;
    }

}
