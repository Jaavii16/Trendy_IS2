package launcher;

import negocio.*;

public class SAFactoryTrendy implements SAFactory {

    @Override
    public SACategorias getCategoriasSA(BusinessDelegate businessDelegate) {
        return new SACategoriasImp(businessDelegate);
    }

    @Override
    public SACesta getCestaSA(BusinessDelegate businessDelegate) {
        return new SACestaimp(businessDelegate);
    }


    @Override
    public SAArticulo getArticuloSA(BusinessDelegate businessDelegate){
        return new SAArticuloImp(businessDelegate);
    }

    @Override
    public SAListas getListaSA(BusinessDelegate businessDelegate){
        return new SAListasImp(businessDelegate);
    }

    @Override
    public SAPedidos getPedidosSA(BusinessDelegate businessDelegate){
        return new SAPedidosImp(businessDelegate);
    }


    @Override
    public SAStock getStockSA(BusinessDelegate businessDelegate){
        return new SAStockImp(businessDelegate);
    }

    @Override
    public SAUsuario getUsuarioSA(BusinessDelegate businessDelegate){
        return new SAUsuarioImp(businessDelegate);
    }

}
