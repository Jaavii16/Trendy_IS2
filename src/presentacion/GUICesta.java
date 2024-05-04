package presentacion;

import negocio.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class GUICesta extends MainGUIPanel implements CestaObserver, FavsObserver, ReservasObserver {

    SAFacade facade;
    JPanel mainPanel;
    private HashMap<String, JPanel> panelMap;
    private HashMap<Integer, JPanel> favsMap;
    private HashMap<String, JPanel> reserMap;
    private JPanel panelCesta;
    private JPanel panelFavs;
    private JPanel panelReservas;

    private static final String PANELCESTA = "Panel_cesta";
    private static final String PANELFAVORITOS = "Panel_favoritos";

    private static final String PANELRESERVAS = "Panel_reservas";

    private JLabel mensajeCesta = new JLabel("La cesta se encuentra vacia...");
    private JLabel mensajeFavs = new JLabel("La lista de favoritos se encuentra vacia...");
    private JLabel mensajesReservas = new JLabel("No hay reservas...");

    private JPanel cards;

    public GUICesta(SAFacade saFacade) {
        this.facade = saFacade;
        facade.registerObserver(this);
        panelMap = new HashMap<>();
        favsMap = new HashMap<>();
        reserMap = new HashMap<>();
        initGui();
    }

    private void initGui() {

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        setViewportView(mainPanel);
        cards = new JPanel(new CardLayout());
        //PANEL DE LOS BOTONES
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        mainPanel.add(buttonPanel, BorderLayout.PAGE_START);
        mainPanel.add(cards, BorderLayout.CENTER);

        //CREAMOS PANELES

        //PANEL CESTA
        JPanel panelCestaComprar = new JPanel(new BorderLayout());
        panelCestaComprar.setBorder(BorderFactory.createTitledBorder("Cesta"));

        panelCesta = new JPanel();
        panelCesta.setLayout(new BoxLayout(panelCesta, BoxLayout.Y_AXIS));
        panelCesta.setBorder(BorderFactory.createTitledBorder("Articulos en la cesta"));
        panelCestaComprar.add(panelCesta, BorderLayout.CENTER);

        //PANEL FAVORITOS
        panelFavs = new JPanel();
        panelFavs.setBorder(BorderFactory.createTitledBorder("Favoritos"));
        panelFavs.setLayout(new BoxLayout(panelFavs, BoxLayout.Y_AXIS));
        panelFavs.setVisible(false);

        //PANEL RESERVAS
        panelReservas = new JPanel();
        panelReservas.setBorder(BorderFactory.createTitledBorder("Reservas"));
        panelReservas.setLayout(new BoxLayout(panelReservas, BoxLayout.Y_AXIS));
        panelReservas.setVisible(false);


        // Añadir paneles al panel de cartas
        cards.add(panelCestaComprar, PANELCESTA);
        cards.add(panelFavs, PANELFAVORITOS);
        cards.add(panelReservas, PANELRESERVAS);

        //PARA CAMBIAR LOS PANELES CON LOS ACTION LISTENERS
        CardLayout cl = (CardLayout) (cards.getLayout());

        //CREAMOS BOTONES
        //BOTON CESTA
        JButton cesta = new JButton("Cesta");
        cesta.setAlignmentX(Component.CENTER_ALIGNMENT);
        cesta.addActionListener((e -> {
            cl.show(cards, "Panel_cesta");
            revalidate();
            repaint();
        }));
        buttonPanel.add(cesta);

        //BOTON FAVORITOS
        JButton favs = new JButton("Favoritos");
        favs.setAlignmentX(Component.CENTER_ALIGNMENT);
        favs.addActionListener((e -> {
            cl.show(cards, "Panel_favoritos");
            revalidate();
            repaint();
        }));
        buttonPanel.add(favs);

        //BOTON REALIZAR PEDIDO
        JButton pedir = new JButton("Realizar pedido");
        pedir.setAlignmentX(Component.CENTER_ALIGNMENT);
        pedir.addActionListener((e -> {
            this.facade.crearPedido();
        }));
        panelCestaComprar.add(pedir, BorderLayout.PAGE_END);

        //BOTON REALIZAR RESERVA
        if (facade.getUsuario() != null) {//TODO ver por qué es null el getUsuario
            if (facade.getUsuario().getSuscripcion().equals(Suscripciones.PREMIUM.name())) {
                JButton reserva = new JButton("Reservar");
                reserva.setAlignmentX(Component.CENTER_ALIGNMENT);
                reserva.addActionListener((e -> {
                    this.facade.crearPedido();
                }));
                buttonPanel.add(reserva);
            }
        }

        mainPanel.setOpaque(true);

        this.setViewportView(mainPanel);

        //TODO mirar porque creo que cuando herede de JScrollPane no va a funcionar
        /*this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);*/
    }


    @Override
    public void onCestaChanged(TOCesta cesta) {
        panelCesta.removeAll();//elimino lo antiguo
        panelMap.clear();
        TreeSet<TOArticuloEnCesta> lista = (TreeSet<TOArticuloEnCesta>) cesta.getListaArticulos();
        if (lista.isEmpty()) {
            panelCesta.add(mensajeCesta);
        } else {
            Iterator<TOArticuloEnCesta> art_it = lista.iterator();
            while (art_it.hasNext()) {
                TOArticuloEnCesta art = art_it.next();
                JPanel articulo = new JPanel();
                articulo.setLayout(new BoxLayout(articulo, BoxLayout.X_AXIS));
                articulo.add(new JLabel(facade.buscarArticulo(art.getIdArticulo()).getNombre()));
                articulo.add(new JLabel("/" + art.getTalla()));
                articulo.add(new JLabel("/" + art.getCantidad() + "Uds."));
                addButtons(articulo, art);
                panelMap.put(art.getIdArticulo() + "", articulo);
                panelCesta.add(articulo);
            }
        }
        panelCesta.revalidate();
        panelCesta.repaint();
    }

    @Override
    public void onArticuloAdded(TOArticuloEnCesta articulo) {
        panelCesta.remove(mensajeCesta);
        addArticuloCesta(articulo);
    }


    public void onArticuloUpdated(TOArticuloEnCesta articulo) {
        //ELIMINAMOS EL PANEL CON LA INFORMACION ANTIGUA
        JPanel eliminar = panelMap.get(articulo.getIdArticulo() + ""); //TODO Cambiar el mapa para que las keys sean TOArticuloEnCesta (porque no solo se diferencian por idArticulo (el equals es de id, color y talla))
        panelCesta.remove(eliminar);
        //CREO UN PANEL CON LOS DATOS DEL ARTICULO CAMBIADOS
        addArticuloCesta(articulo);
    }

    private void addArticuloCesta(TOArticuloEnCesta articulo) {
        JPanel _articulo = new JPanel();
        _articulo.setLayout(new BoxLayout(_articulo, BoxLayout.X_AXIS));
        _articulo.add(new JLabel(facade.buscarArticulo(articulo.getIdArticulo()).getNombre()));
        _articulo.add(new JLabel("/" + articulo.getTalla()));
        _articulo.add(new JLabel("/" + articulo.getCantidad() + "Uds.")); //TODO Añadir color (y a lo mejor hacer en un JTable)
        addButtons(_articulo, articulo);
        panelMap.put(articulo.getIdArticulo() + "", _articulo);
        panelCesta.add(_articulo);
        panelCesta.revalidate();
        panelCesta.repaint();
    }

    @Override
    public void onArticuloRemoved(TOArticuloEnCesta articulo) {
        JPanel eliminar = panelMap.get(articulo.getIdArticulo() + "");
        panelCesta.remove(eliminar);
        panelMap.remove(articulo.getIdArticulo() + "");
        if (panelMap.isEmpty()) {
            panelCesta.add(mensajeCesta);
        }
        panelCesta.revalidate();
        panelCesta.repaint();
    }

    private void addButtons(JPanel panel, TOArticuloEnCesta art) {
        int stock = facade.getStock(art.getIdArticulo(), art.getColor().name(), art.getTalla().name());
        SpinnerNumberModel cantidad = new SpinnerNumberModel(art.getCantidad(), 0, stock, 1);
        JSpinner unidades = new JSpinner(cantidad);
        unidades.setMaximumSize(new Dimension(50, 20));
        panel.add(unidades);
        JButton anyadir = new JButton("Confirmar");
        panel.add(anyadir);
        anyadir.addActionListener((e -> {
            int uds = (int) unidades.getValue(); //TODO Comprobar stock?
            if (uds == 0) {
                facade.removeArticuloDeCesta(art);
            } else {
                art.setCantidad(uds);
                facade.actualizarArticuloEnCesta(art);
            }
        }));
    }


    @Override
    public void update() {
        initGui();
    }

    @Override
    public void reset() {
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, "Panel_cesta");
    }

    @Override
    public void onArticuloAdded(TOArticuloEnFavoritos toArticuloEnFavoritos) {
        JPanel _articulo = new JPanel();
        _articulo.setLayout(new BoxLayout(_articulo, BoxLayout.X_AXIS));
        _articulo.add(new JLabel(toArticuloEnFavoritos.getIdArticulo() + ""));
        favsMap.put(toArticuloEnFavoritos.getIdArticulo(), _articulo);
        panelFavs.add(_articulo);
        JButton delete = new JButton("Eliminar");
        delete.setAlignmentX(Component.RIGHT_ALIGNMENT);
        _articulo.add(delete);
        delete.addActionListener((e -> {
            favsMap.remove(toArticuloEnFavoritos.getIdArticulo());
            panelFavs.remove(_articulo);
        }));
        panelFavs.revalidate();
        panelFavs.repaint();
    }

    @Override
    public void onArticuloRemoved(TOArticuloEnFavoritos toArticuloEnFavoritos) {
        JPanel eliminar = favsMap.get(toArticuloEnFavoritos.getIdArticulo());
        favsMap.remove(toArticuloEnFavoritos.getIdArticulo());
        /*if (favsMap.isEmpty()) {
            panelFavs.add(mensajeFavs);
        }*/
        //TODO mirar si hace falta
        panelFavs.remove(eliminar);
        panelFavs.revalidate();
        panelFavs.repaint();
    }

    @Override
    public void onFavoritosChanged(Set<TOArticuloEnFavoritos> favoritos) {
        if (favoritos != null) {
            panelFavs.removeAll();//elimino lo antiguo
            favsMap.clear();
            Set<TOArticuloEnFavoritos> lista = favoritos;
            if (lista.isEmpty()) {
                panelFavs.add(mensajeFavs);
            } else {
                Iterator<TOArticuloEnFavoritos> art_it = lista.iterator();
                while (art_it.hasNext()) {
                    TOArticuloEnFavoritos art = art_it.next();
                    JPanel articulo = new JPanel(new BoxLayout(panelFavs, BoxLayout.X_AXIS));
                    articulo.add(new JLabel(art.getIdArticulo() + ""));
                    favsMap.put(art.getIdArticulo(), articulo);
                    panelFavs.add(articulo);
                    JButton delete = new JButton("Eliminar");
                    delete.setAlignmentX(Component.RIGHT_ALIGNMENT);
                    articulo.add(delete);
                    delete.addActionListener((e -> {
                        favsMap.remove(art.getIdArticulo());
                        panelFavs.remove(articulo);
                    }));
                }
            }
            panelFavs.revalidate();
            panelFavs.repaint();
        }
    }

    @Override
    public void onArticuloAdded(TOArticuloEnReservas toArticuloEnReservas) {

    }

    @Override
    public void onArticuloRemoved(TOArticuloEnReservas toArticuloEnReservas) {

    }

    @Override
    public void onReservasChanged(Set<TOArticuloEnReservas> reservas) {

    }
}
