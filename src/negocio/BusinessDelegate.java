package negocio;

import integracion.*;
import launcher.DAOFactory;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public class BusinessDelegate {

    DAOStock daostock = new DAOStockImp();
    DAOCategorias daocat = new DAOCategoriasImp();
    DAOArticulo daoart = new DAOArticuloImp();
    DAOListas daolistas = new DAOListasImp();

    BOStock bostock = new BOStock(daostock);
    BOArticulo boArticulo = new BOArticulo(daoart, daocat, daostock);
    BOCategorias boCategorias = new BOCategorias(daocat);
    BOListas boListas = new BOListas(daolistas);

    DAOPedidos daoPedidos = new DAOPedidosImp();
    BOPedido boPedido = new BOPedido(daoPedidos);
    DAOCesta daoCesta = new DAOCestaimp();

    BOCesta boCesta = new BOCesta(daoCesta);

    DAOUsuario daoUsuario = new DAOImpUsuario();
    BOUsuario boUsuario = new BOUsuario(daoUsuario);

    private DAOFactory daoFactory; //TODO Usar daoFactory

    public BusinessDelegate(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        boUsuario.addObserver(boCesta);
    }

    public void registerObserver(Observer observer) {
        if (observer instanceof CestaObserver co)
            boCesta.addObserver(co);
        else if (observer instanceof FavsObserver fo)
            boCesta.addObserver(fo);
        else if (observer instanceof AuthObserver ao)
            boUsuario.addObserver(ao);
        else
            throw new IllegalArgumentException("Observer no soportado");
    }

    public void crearPedido() {
        //TODO Hacer cuando este el usuario y la cesta
    }

    public void altaArticuloStock(int id, int s) {
        bostock.altaArticuloStock(id, s);
    }

    public void bajaArticuloStock(int id) {
        bostock.bajaArticuloStock(id);
    }

    public void modificarArticuloStock(tStock s) {
        bostock.modificarArticuloStock(s);
    }

    public int getStock(int id, String color, String t) {
        return bostock.getStock(id, color, t);
    }

    public List<String> getCategorias() {
        return this.boCategorias.getCategorias();
    }

    public Collection<TOPedido> getAllPedidos() {
        return boPedido.getAllPedidos();
    }

    public tArticulo buscarArticulo(int id) {
        return boArticulo.buscarArticulo(id);
    }

    public Collection<TOPedido> getPedidosUsuario(int idUsuario) {
        return boPedido.getPedidosUsuario(idUsuario);
    }

    public void altaArticulo(tArticulo a, String fechal, String genero, int descuento, int s) {
        boArticulo.altaArticulo(a, fechal, genero, descuento, s);
    }

    public void bajaArticulo(tArticulo a) {
        boArticulo.bajaArticulo(a);
    }

    public void modificarArticulo(tArticulo a) {
        boArticulo.modificarArticulo(a);
    }

    public Collection<TOPedido> getPedidosStatus(TOStatusPedido toStatusPedido) {
        return boPedido.getPedidosStatus(toStatusPedido);
    }

    public void altaArticuloCat(int id, String fechal, int descuento, String genero) {
        boCategorias.altaArticuloCat(id, fechal, descuento, genero);
    }

    public void bajaArticuloCat(int id) {
        boCategorias.bajaArticuloCat(id);
    }

    public void modificarArticulo(int id, String fechal, int descuento, String genero) {
        boCategorias.modificarArticulo(id, fechal, descuento, genero);
    }

    public void actualizaExclusivos() {
        boCategorias.actualizaExclusivos();
    }

    public Collection<TOPedido> getPedidosFecha(Date fecha) {
        return boPedido.getPedidosFecha(fecha);
    }

    public List<Articulo> buscaArticulosCategoria(String cat) {
        return boListas.buscaArticulosCategoria(cat);
    }

    public void cambiarStatus(int id, TOStatusPedido toStatusPedido) {
        boPedido.cambiarStatus(id, toStatusPedido);
    }

    public List<Articulo> buscaFiltro(List<Articulo> lista, Predicate<Articulo> pred) {
        return boListas.buscaFiltro(lista, pred);
    }

    public void cancelarPedido(int id) {
        boPedido.cancelarPedido(id);
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

    public TUsuario create(TUsuario tUsuario) {
        return boUsuario.create(tUsuario);
    }

    public TUsuario read() {
        return boUsuario.read();
    }

    public Collection<TUsuario> readAll() {
        return boUsuario.readAll();
    }

    public void update(TUsuario tUsuario) {
        boUsuario.update(tUsuario);
    }

    public void delete(int id) {
        boUsuario.delete(id);
    }

    public void actualizarSaldo(int cantidad) {
        boUsuario.actualizarCesta(cantidad);
    }

    public void actualizarSusc(int id) {
        boUsuario.actualizarSuscr(id);
    }

    public void actualizarSuscAdmin(int userID, int id) {
        boUsuario.actualizarSuscrAdmin(userID, id);
    }

    public void addArticuloAFavoritos(TOArticuloEnFavoritos toArticuloEnFavoritos) {
        boCesta.addArticuloAFavoritos(toArticuloEnFavoritos);
    }

    public void removeArticuloDeFavoritos(TOArticuloEnFavoritos toArticuloEnFavoritos) {
        boCesta.removeArticuloDeFavoritos(toArticuloEnFavoritos);
    }

    public void login(String correo, String contraseña) {
        boUsuario.login(correo, contraseña);
    }

    public void logout() {
        boUsuario.logout();
    }

    public void actualizarSaldoAdmin(int cantidad, int id) {
        boUsuario.actualizarSaldoAdmin(cantidad, id);
    }

}
