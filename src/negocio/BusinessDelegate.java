package negocio;

import launcher.DAOFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BusinessDelegate {

    BOStock bostock;
    BOArticulo boArticulo;
    BOCategorias boCategorias;
    BOListas boListas;
    BOPedido boPedido;
    BOCesta boCesta;
    BOUsuario boUsuario;

    public BusinessDelegate(DAOFactory daoFactory) {
        bostock = new BOStock(daoFactory.getDAOStock());
        boArticulo = new BOArticulo(daoFactory.getDAOArticulo(), daoFactory.getDAOCategorias(), daoFactory.getDAOStock());
        boCategorias = new BOCategorias(daoFactory.getDAOCategorias());
        boListas = new BOListas(daoFactory.getDAOListas());
        boPedido = new BOPedido(daoFactory.getDAOPedidos());
        boCesta = new BOCesta(daoFactory.getDAOCesta());
        boUsuario = new BOUsuario(daoFactory.getDAOUsuario());
    }

    public void registerObserver(Observer observer) {
        boolean añadido = false;
        if (observer instanceof CestaObserver co) {
            boCesta.addObserver(co);
            añadido = true;
        }
        if (observer instanceof FavsObserver fo) {
            boCesta.addObserver(fo);
            añadido = true;
        }
        if (observer instanceof UserObserver ao) {
            boUsuario.addObserver(ao);
            añadido = true;
        }
        if (observer instanceof PedidoObserver po) {
            boPedido.addObserver(po);
            añadido = true;
        }
        if (!añadido)
            throw new IllegalArgumentException("Observer no soportado");
    }

    public void crearPedido() {
        TUsuario toUsuario = boUsuario.read();

        if (toUsuario == null) {
            throw new RuntimeException("Tienes que estar logueado para realizar un pedido");
        }

        Set<TOAArticuloEnPedido> toaArticuloEnPedidos = boCesta.getCesta().getListaArticulos().stream().map(toArticuloEnCesta -> {
            tArticulo articulo = boArticulo.buscarArticulo(toArticuloEnCesta.getIdArticulo());
            return new TOAArticuloEnPedido(toArticuloEnCesta, articulo.getPrecio());
        }).collect(Collectors.toSet());

        Set<TOArticuloEnReservas> reservas = boCesta.getReservas(toUsuario.getId());
        List<tStock> stocks = new ArrayList<>();
        for (TOAArticuloEnPedido art : toaArticuloEnPedidos) {
            TOArticuloEnCesta artCesta = art.getToArticuloEnCesta();
            tArticulo artt = boArticulo.buscarArticulo(artCesta.getIdArticulo());
            Articulo arti = new Articulo(artt);
            if (boCategorias.esExclusivo(arti)) {

                if(reservas.stream().mapToInt(TOArticuloEnReservas::getIdArticulo).anyMatch(i->i == artCesta.getIdArticulo()))
                    throw new RuntimeException("No tienes reserva de ese articulo");

                if (!toUsuario.getSuscripcion().equals(Suscripciones.PREMIUM)) {
                    throw new RuntimeException("No se puede comprar el articulo exclusivo " + artCesta.getIdArticulo() + " talla " + artCesta.getTalla() + " color " + artCesta.getColor());
                }

                if(artCesta.getCantidad() > 1)
                    throw new RuntimeException("No se puede comprar mas de una unidad de un articulo exclusivo");

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/aaaa");
                try {
                    Date date = sdf.parse(boCategorias.getFechaLanz(artCesta.getIdArticulo()));
                    if (date.getTime() - new Date().getTime() > 1000 * 60 * 60 * 24){
                        throw new RuntimeException("No se puede comprar el articulo exclusivo " + artCesta.getIdArticulo() + " talla " + artCesta.getTalla() + " color " + artCesta.getColor() + " porque no ha salido a la venta");
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }

            int stockAnt = bostock.getStock(artCesta.getIdArticulo(), artCesta.getColor().toString(), artCesta.getTalla().toString());
            if (stockAnt - artCesta.getCantidad() < 0) {
                throw new RuntimeException("No hay stock suficiente de " + boArticulo.buscarArticulo(artCesta.getIdArticulo()).getNombre() + " talla " + artCesta.getTalla() + " color " + artCesta.getColor() + " para realizar el pedido. Stock disponible: " + stockAnt + " unidades.");
            }
            tStock newStock = new tStock(
                    artCesta.getIdArticulo(),
                    artCesta.getColor().toString(),
                    artCesta.getTalla().toString(),
                    stockAnt - art.getToArticuloEnCesta().getCantidad());
            stocks.add(newStock);
        }

        double precioTotal = toaArticuloEnPedidos.stream().mapToDouble(articuloEnPedido -> articuloEnPedido.getPrecio() * articuloEnPedido.getToArticuloEnCesta().getCantidad()).sum();

        if (boUsuario.read().getSuscripcion() != Suscripciones.PRIME) {
            precioTotal += 5;
        }

        if (toUsuario.getSaldo() < precioTotal)
            throw new RuntimeException("Saldo insuficiente");

        TOPedido toPedido = new TOPedido()
                .setTOAArticulosEnPedido(new TOArticulosEnPedido(toaArticuloEnPedidos))
                .setDireccion(toUsuario.getDireccion())
                .setIDUsuario(toUsuario.getId())
                .setStatus(TOStatusPedido.REPARTO.toString())
                .setFecha(new Date(System.currentTimeMillis())
                );

        boPedido.crearPedido(toPedido);

        boUsuario.actualizarSaldo(-precioTotal);

        for (tStock s : stocks) {
            bostock.modificarArticuloStock(s);
        }

        for(TOArticuloEnReservas res : reservas){
            boCesta.removeArticuloDeReservas(res);
        }

        boCesta.vaciarCesta(toUsuario.getId());
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

    public int getStockColor(int id, String color) {
        return bostock.getStockColor(id, color);
    }

    public int getStockTalla(int id, String t) {
        return bostock.getStockTalla(id, t);
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

    public Collection<TOPedido> getPedidosUsuario() {
        return boPedido.getPedidosUsuario(boUsuario.read().getId());
    }

    public void altaArticulo(tArticulo a, String fechal, String genero, double descuento, int s) {
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

    public String getFechaLanz(int id) {
        return boCategorias.getFechaLanz(id);
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

    public boolean esExclusivo(Articulo art) {
        return boCategorias.esExclusivo(art);
    }

    public void cancelarPedido(int id) {
        boPedido.cancelarPedido(id);
        boUsuario.actualizarSaldo(boPedido.getAllPedidos().stream().filter(toPedido -> toPedido.getID() == id).findFirst().orElseThrow()
                .getTOAArticulosEnPedido().getArticulosSet().stream().mapToDouble(TOAArticuloEnPedido::getPrecio).sum());
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
        TUsuario newUser = boUsuario.create(tUsuario);
        boCesta.guardarCesta(newUser.getId());
        return newUser;
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

    public void actualizarSaldo(double cantidad) {
        boUsuario.actualizarSaldo(cantidad);
    }


    public void actualizarSusc(Suscripciones susc) {
        boUsuario.actualizarSuscr(susc);
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
        boCesta.updateCesta(true, boUsuario.read().getId());
    }

    public void logout() {
        boUsuario.logout();
        boCesta.updateCesta(false, 0);
    }

    public void actualizarSaldoAdmin(double cantidad, int id) {
        boUsuario.actualizarSaldoAdmin(cantidad, id);
    }

    public void unregisterObserver(Observer observer) {
        if (observer instanceof CestaObserver co)
            boCesta.removeObserver(co);
        else if (observer instanceof FavsObserver fo)
            boCesta.removeObserver(fo);
        else if (observer instanceof UserObserver ao)
            boUsuario.removeObserver(ao);
        else
            throw new IllegalArgumentException("Observer no soportado");
    }

    public boolean esPremium() {
        return boUsuario.esPremium();
    }

    public void addArticuloAReservas(TOArticuloEnReservas artEnReservas) {
        if (boUsuario.esPremium()) //TODO Comprobar el stock
            boCesta.addArticuloAReservas(artEnReservas);
    }

    public void removeArticuloDeReservas(TOArticuloEnReservas artEnReservas) {
        if (boUsuario.esPremium()) //TODO Sumar stock
            boCesta.removeArticuloDeReservas(artEnReservas);
    }

    public void updateCesta() {
        if (boUsuario.read() != null) {
            boCesta.updateCesta(true, boUsuario.read().getId());
        }
    }

    public TOPedido getLastPedido() {
        if (boUsuario.read() == null) return null;
        return boPedido.getLastPedido(boUsuario.read().getId());
    }
}
