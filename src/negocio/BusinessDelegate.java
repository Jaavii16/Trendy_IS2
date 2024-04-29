package negocio;

import integracion.*;
import launcher.DAOFactory;

import java.util.List;
import java.util.function.Predicate;

import integracion.DAOCestaimp;

public class BusinessDelegate {

    DAOStock daostock = new DAOStockImp();
    DAOCategorias daocat = new DAOCategoriasImp();
    DAOArticulo daoart = new DAOArticuloImp();
    DAOListas daolistas = new DAOListasImp();


    BOStock bostock = new BOStock(daostock);
    BOArticulo boArticulo = new BOArticulo(daoart, daocat);
    BOCategorias boCategorias = new BOCategorias(daocat);
    BOListas boListas = new BOListas(daolistas);

    DAOCesta daoCesta = new DAOCestaimp();

    BOCesta boCesta = new BOCesta(daoCesta);

    private DAOFactory daoFactory;

    public BusinessDelegate(DAOFactory daoFactory){
        this.daoFactory = daoFactory;
    }

    public void altaArticuloStock(tStock s){
        bostock.altaArticuloStock(s);
    }
    public void bajaArticuloStock(int id){
        bostock.bajaArticuloStock(id);
    }
    public void modificarArticuloStock(tStock s){
        bostock.modificarArticuloStock(s);
    }
    public int getStock(int id, String color, String t){
        return bostock.getStock(id, color, t);
    }

    public tArticulo buscarArticulo(int id){
        return boArticulo.buscarArticulo(id);
    }

    public void altaArticulo(tArticulo a, String fechal,String genero, int descuento){
        boArticulo.altaArticulo(a, fechal, genero, descuento);
    }
    public void bajaArticulo(tArticulo a){
        boArticulo.bajaArticulo(a);
    }
    public void modificarArticulo(tArticulo a){
        boArticulo.modificarArticulo(a);
    }

    public void altaArticuloCat(int id, String fechal, int descuento, String genero){
        boCategorias.altaArticuloCat(id, fechal, descuento, genero);
    }
    public void bajaArticuloCat(int id){
        boCategorias.bajaArticuloCat(id);
    }
    public void modificarArticulo(int id, String fechal, int descuento, String genero){
        boCategorias.modificarArticulo(id, fechal, descuento, genero);
    }

    public List<Articulo> buscaArticulosCategoria(String cat){
        return boListas.buscaArticulosCategoria(cat);
    }

    public List<Articulo> buscaFiltro(List<Articulo> lista, Predicate<Articulo> pred){
        return boListas.buscaFiltro(lista, pred);
    }

    public void addArticuloACesta(TOArticuloEnCesta toArticuloEnCesta) {
        boCesta.addArticuloACesta(toArticuloEnCesta);
    }

    public void actualizarArticuloEnCesta(TOArticuloEnCesta toArticuloEnCesta) {
        boCesta.actualizarArticuloEnCesta(toArticuloEnCesta);
    }

    public void removeArticuloDeCesta(TOArticuloEnCesta toArticuloEnCesta) {
        boCesta.removeArticuloEnCesta(toArticuloEnCesta);
    }

}
