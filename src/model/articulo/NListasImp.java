package model.articulo;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class NListasImp {

    DAOListas dao = new DAOListasImp();



    public List<Articulo> buscaArticulosCategoria(String cat) {
        return dao.buscaArticulosCategoria(cat);
    }


    public List<Articulo> buscaFiltro(List<Articulo> lista, Predicate<Articulo> pred) {
        //Color, precio 3 rangos, subcat
        List<Articulo> filtro = new LinkedList<>();

        for(Articulo a: lista){
            if(pred.test(a)){
                filtro.add(a);
            }
        }

        return filtro;
    }
}
