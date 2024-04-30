package negocio;

import java.util.*;
import java.util.function.Predicate;

public class SAListasImp extends AbstractSA implements SAListas {


    public SAListasImp(BusinessDelegate b){
       super(b);
    }

    @Override
    public List<Articulo> buscaArticulosCategoria(String cat) {
        return businessDelegate.buscaArticulosCategoria(cat);
    }


    public List<Articulo> buscaFiltro(List<Articulo> lista, Predicate<Articulo> pred){
        return businessDelegate.buscaFiltro(lista, pred);
    }
}
