package launcher;

import negocio.*;

public interface SAFactory {
    SAArticulo getArticuloSA(BusinessDelegate businessDelegate);

    SACategorias getCategoriasSA(BusinessDelegate businessDelegate);

    SACesta getCestaSA(BusinessDelegate businessDelegate);

    SAListas getListaSA(BusinessDelegate businessDelegate);

    SAPedidos getPedidosSA(BusinessDelegate businessDelegate);

    SAStock getStockSA(BusinessDelegate businessDelegate);

    SAUsuario getUsuarioSA(BusinessDelegate businessDelegate);
}
