package negocio;

import launcher.SAFactory;

import java.util.Collection;
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
    private final BusinessDelegate businessDelegate;

    public SAFacade(BusinessDelegate businessDelegate, SAFactory saFactory) {
        this.businessDelegate = businessDelegate;

        saCategorias = saFactory.getCategoriasSA(businessDelegate);
        saArticulo = saFactory.getArticuloSA(businessDelegate);
        saCesta = saFactory.getCestaSA(businessDelegate);
        saLista = saFactory.getListaSA(businessDelegate);
        saPedidos = saFactory.getPedidosSA(businessDelegate);
        saStock = saFactory.getStockSA(businessDelegate);
        saUsuario = saFactory.getUsuarioSA(businessDelegate);
    }

    public void registerObserver(Observer observer) {
        businessDelegate.registerObserver(observer);
    }

    //FUNCIONES MÓDULO ARTÍCULOS

    public tArticulo buscarArticulo(int id) {
        return saArticulo.buscarArticulo(id);
    }

    public void altaArticulo(tArticulo a, String fechal, String genero, double descuento, int stock) {
        saArticulo.altaArticulo(a, fechal, genero, descuento, stock);
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

    public boolean esExclusivo(Articulo art) {
        return saCategorias.esExclusivo(art);
    }

    public void altaArticuloStock(int id, int s) {
        saStock.altaArticuloStock(id, s);
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

    public int getStockColor(int id, String color) {
        return saStock.getStockColor(id, color);
    }

    public int getStockTalla(int id, String t) {
        return saStock.getStockTalla(id, t);
    }

    public List<String> getCategorias() {
        return saCategorias.getCategorias();
    }

    //FUNCIONES MODULO PEDIDOS
    public void crearPedido() {
        saPedidos.crearPedido();
    }

    public Collection<TOPedido> getAllPedidos() {
        return saPedidos.getAllPedidos();
    }

    public Collection<TOPedido> getPedidosUsuario() {
        return saPedidos.getPedidosUsuario();
    }

    public void cambiarStatus(int ID, TOStatusPedido TOStatusPedido) {
        saPedidos.cambiarStatus(ID, TOStatusPedido);
    }

    public void cancelarPedido(int ID) {
        saPedidos.cancelarPedido(ID);
    }

    public TOPedido getLastPedido() {
        return saPedidos.getLastPedido();
    }

    //FUNCIONES MÓDULO USUARIO
    public boolean create(TUsuario tUsuario) {
        return saUsuario.create(tUsuario);
    }

    public TUsuario getUsuario() {
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

    public void actualizarSuscr(Suscripciones susc) {
        saUsuario.actualizarSuscr(susc);
    }

    public void actualizarSuscrAdmin(int userID, int id) {
        saUsuario.actualizarSuscrAdmin(userID, id);
    }

    public void login(String correo, String contraseña) {
        saUsuario.login(correo, contraseña);
    }

    public void logout() {
        saUsuario.logout();
    }

    public void actualizarSaldoAdmin(double cantidad, int id) {
        saUsuario.actualizarSaldoAdmin(cantidad, id);
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

    public void unregisterObserver(Observer observer) {
        businessDelegate.unregisterObserver(observer);
    }

    public boolean esPremium() {
        return saUsuario.esPremium();
    }

    public void addArticuloAReservas(TOArticuloEnReservas artEnReservas) {
        saCesta.addArticuloAReservas(artEnReservas);
    }

    public void removeArticuloDeReservas(TOArticuloEnReservas artEnReservas) {
        saCesta.removeArticuloDeReservas(artEnReservas);
    }

    public void updateCesta() {
        saCesta.updateCesta();
    }

    public String getFechaLanz(int id) {
        return saCategorias.getFechaLanz(id);
    }
}