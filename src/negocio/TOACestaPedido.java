package negocio;

import java.util.Set;

public class TOACestaPedido {
    private TOACestaUsuario toACestaUsuario;
    private Set<TOAArticuloEnPedido> toAArticuloEnPedido;

    public TOACestaPedido(TOACestaUsuario toACestaUsuario, Set<TOAArticuloEnPedido> toAArticuloEnPedido) {
        this.toACestaUsuario = toACestaUsuario;
        this.toAArticuloEnPedido = toAArticuloEnPedido;
    }

    public TOACestaUsuario getToACestaUsuario() {
        return toACestaUsuario;
    }

    public Set<TOAArticuloEnPedido> getToAArticuloEnPedido() {
        return toAArticuloEnPedido;
    }
}
