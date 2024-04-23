package model.articulo;

import java.util.*;
import java.util.function.Predicate;

public class SAListasImp implements SAListas{

    /*
    En la funcion dao de buscar categoria hagamos que devuelva directamente a la lista de articulos
    y entonces llamemos (dentro de esa) a la función buscar artículo. Así lo devuelve todo el dao
     */


    NListasImp nlistas = new NListasImp();

    @Override
    public List<Articulo> buscaArticulosCategoria(String cat) {
        return nlistas.buscaArticulosCategoria(cat);
    }


    public List<Articulo> buscaFiltro(List<Articulo> lista, Predicate<Articulo> pred){
        return nlistas.buscaFiltro(lista, pred);
    }
}
