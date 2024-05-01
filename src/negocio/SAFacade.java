package negocio;

import launcher.SAFactory;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public class SAFacade {

    private final SACategorias saCategorias;
    private final SAArticulo saArticulo;
    private final SACesta saCesta;
    private final SAListas saLista;
    private final SAPedidos saPedidos;
    private final SAStock saStock;
    private final SAUsuario saUsuario;

    public SAFacade(BusinessDelegate businessDelegate, SAFactory saFactory) {
        saCategorias = saFactory.getCategoriasSA(businessDelegate);
        saArticulo = saFactory.getArticuloSA(businessDelegate);
        saCesta = saFactory.getCestaSA(businessDelegate);
        saLista = saFactory.getListaSA(businessDelegate);
        saPedidos = saFactory.getPedidosSA(businessDelegate);
        saStock = saFactory.getStockSA(businessDelegate);
        saUsuario = saFactory.getUsuarioSA(businessDelegate);
    }

    public void registerObserver(Observer observer) {
        //TODO Preguntar si registrar un observer hay que hacerlo a traves de businessDelegate o de los sa
    }

    //FUNCIONES MÓDULO ARTÍCULOS

    public tArticulo buscarArticulo(int id) {
        return saArticulo.buscarArticulo(id);
    }

    public void altaArticulo(tArticulo a, String fechal, String genero, int descuento) {
        saArticulo.altaArticulo(a, fechal, genero, descuento);
    }

    public void bajaArticulo(tArticulo a) {
        saArticulo.bajaArticulo(a);
    }

    public void modificarArticulo(tArticulo a) {
        saArticulo.modificarArticulo(a);
    }

    public void altaArticuloCat(int id, String fechal, int descuento, String genero) {
        saCategorias.altaArticuloCat(id, fechal, descuento, genero);
    }

    public void bajaArticuloCat(int id) {
        saCategorias.bajaArticuloCat(id);
    }

    public void modificarArticulo(int id, String fechal, int descuento, String genero) {
        saCategorias.modificarArticulo(id, fechal, descuento, genero);
    }

    public List<Articulo> buscaArticulosCategoria(String cat) {
        return saLista.buscaArticulosCategoria(cat);
    }

    public List<Articulo> buscaFiltro(List<Articulo> lista, Predicate<Articulo> pred) {
        return saLista.buscaFiltro(lista, pred);
    }

    public void altaArticuloStock(tStock s) {
        saStock.altaArticuloStock(s);
    }

    public void bajaArticuloStock(int id) {
        saStock.bajaArticuloStock(id);
    }

    public void modificarArticuloStock(tStock s) {
        saStock.modificarArticuloStock(s);
    }

    public int getStock(int id, String color, String t) {
        return saStock.getStock(id, color, t);
    }

    //FUNCIONES MODULO PEDIDOS
    public void crearPedido() {
        saPedidos.crearPedido();
    }

    public Collection<TOPedido> getAllPedidos() {
        return saPedidos.getAllPedidos();
    }

    public Collection<TOPedido> getPedidosUsuario(int IDUsuario) {
        return saPedidos.getPedidosUsuario(IDUsuario);
    }

    public Collection<TOPedido> getPedidosStatus(TOStatusPedido TOStatusPedido) {
        return saPedidos.getPedidosStatus(TOStatusPedido);
    }

    public Collection<TOPedido> getPedidosFecha(Date fecha) {
        return saPedidos.getPedidosFecha(fecha);
    }

    public void cambiarStatus(int ID, TOStatusPedido TOStatusPedido) {
        saPedidos.cambiarStatus(ID, TOStatusPedido);
    }

    public void cancelarPedido(int ID) {
        saPedidos.cancelarPedido(ID);
    }

    public TOPedido getLastPedido() {
        return null; //TODO
    }

    //FUNCIONES MÓDULO USUARIO
    public boolean create(TUsuario tUsuario) {
        return saUsuario.create(tUsuario);
    }

    public String getUsuario() {
        return saUsuario.getUsuario();
    }

    public Collection<TUsuario> readAll() {
        return saUsuario.readAll();
    }

    public void update(TUsuario tUsuario) {
        saUsuario.update(tUsuario);
    }

    public void delete(int id) {
        saUsuario.delete(id);
    }

    public void actualizarSaldo(int cantidad) {
        saUsuario.actualizarSaldo(cantidad);
    }

    public void actualizarSuscr(int id) {
        saUsuario.actualizarSuscr(id);
    }

    public void login(String correo, String contraseña) {
        saUsuario.login(correo, contraseña);
    }

    public void logout() {
        saUsuario.logout();
    }

    //FUNCIONES MODULO CESTA

    public void addArticuloACesta(TOArticuloEnCesta toArticuloEnCesta) {
        saCesta.addArticuloACesta(toArticuloEnCesta);
    }

    public void actualizarArticuloEnCesta(TOArticuloEnCesta toArticuloEnCesta) {
        saCesta.actualizarArticuloEnCesta(toArticuloEnCesta);
    }

    public void removeArticuloDeCesta(TOArticuloEnCesta toArticuloEnCesta) {
        saCesta.removeArticuloDeCesta(toArticuloEnCesta);
    }

    public void addArticuloAFavoritos(TOArticuloEnFavoritos toArticuloEnFavoritos) {
        saCesta.addArticuloAFavoritos(toArticuloEnFavoritos);
    }

    public void removeArticuloDeFavoritos(TOArticuloEnFavoritos toArticuloEnFavoritos) {
        saCesta.removeArticuloDeFavoritos(toArticuloEnFavoritos);

    }
}