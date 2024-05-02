package negocio;

import integracion.DAOListas;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class BOListas {
    DAOListas dao;

    public BOListas(DAOListas dao) {
        this.dao = dao;
    }

    public List<Articulo> buscaArticulosCategoria(String cat) {
        return dao.buscaArticulosCategoria(cat);
    }


    public List<Articulo> buscaFiltro(List<Articulo> lista, Predicate<Articulo> pred) {
        //Color, precio 3 rangos, subcat
        List<Articulo> filtro = new LinkedList<>();

        for (Articulo a : lista) {
            if (pred.test(a)) {
                filtro.add(a);
            }
        }

        return filtro;
    }
}
