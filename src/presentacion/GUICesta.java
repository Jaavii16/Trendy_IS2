package presentacion;

import negocio.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class GUICesta extends MainGUIPanel implements CestaObserver, FavsObserver {

    SAFacade facade;
    JPanel mainPanel;
    private HashMap<String, JPanel> panelMap;
    private HashMap<Integer, JPanel> favsMap;
    private JPanel panelCesta;
    private JPanel panelFavs;

    private static final String PANELCESTA = "Panel_cesta";
    private static final String PANELFAVORITOS = "Panel_favoritos";

    private JLabel mensajeCesta = new JLabel("La cesta se encuentra vacia...");
    private JLabel mensajeFavs = new JLabel("La lista de favoritos se encuentra vacia...");

    private JPanel cards;

    public GUICesta(SAFacade saFacade) {
        this.facade = saFacade;
        facade.registerObserver(this);
        panelMap = new HashMap<>();
        favsMap = new HashMap<>();
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
        panelCesta = new JPanel();
        panelCesta.setLayout(new BoxLayout(panelCesta, BoxLayout.Y_AXIS));
        panelCesta.setBorder(BorderFactory.createTitledBorder("Cesta"));

        //PANEL FAVORITOS
        panelFavs = new JPanel();
        panelFavs.setBorder(BorderFactory.createTitledBorder("Favoritos"));
        panelFavs.setLayout(new BoxLayout(panelFavs, BoxLayout.Y_AXIS));
        panelFavs.setVisible(false);

        // Añadir paneles al panel de cartas
        cards.add(panelCesta, PANELCESTA);
        cards.add(panelFavs, PANELFAVORITOS);

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
        buttonPanel.add(pedir);


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
                JPanel articulo = new JPanel(new BoxLayout(panelCesta, BoxLayout.X_AXIS));
                articulo.add(new JLabel(art.toString()));//nombre¿?
                articulo.add(new JLabel(art.getTalla() + ""));
                articulo.add(new JLabel(art.getCantidad() + ""));
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
        JPanel _articulo = new JPanel(new BoxLayout(panelCesta, BoxLayout.X_AXIS));
        _articulo.add(new JLabel(articulo.toString()));//nombre¿?
        _articulo.add(new JLabel(articulo.getTalla() + ""));
        _articulo.add(new JLabel(articulo.getCantidad() + ""));
        addButtons(_articulo, articulo);
        panelMap.put(articulo.getIdArticulo() + "", _articulo);
        panelCesta.add(_articulo);
        panelCesta.revalidate();
        panelCesta.repaint();
    }


    public void onArticuloUpdated(TOArticuloEnCesta articulo) {
        //ELIMINAMOS EL PANEL CON LA INFORMACION ANTIGUA
        JPanel eliminar = panelMap.get(articulo.getIdArticulo() + "");
        panelCesta.remove(eliminar);
        //CREO UN PANEL CON LOS DATOS DEL ARTICULO CAMBIADOS
        JPanel _articulo = new JPanel(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        _articulo.add(new JLabel(articulo.toString()));//nombre¿?
        _articulo.add(new JLabel(articulo.getTalla() + ""));
        _articulo.add(new JLabel(articulo.getCantidad() + ""));
        addButtons(_articulo, articulo);
        panelMap.put(articulo.getIdArticulo() + "", _articulo);
        panelCesta.add(_articulo);
        panelCesta.repaint();
    }

    @Override
    public void onArticuloRemoved(TOArticuloEnCesta articulo) {
        JPanel eliminar = panelMap.get(articulo.getIdArticulo() + "");
        panelMap.remove(articulo.getIdArticulo() + "");
        /*if (panelMap.isEmpty()) {
            panelFavs.add(mensajeFavs);
        }*/
        //TODO mirar si hace falta
        panelCesta.remove(eliminar);
        panelCesta.repaint();
    }

    private void addButtons(JPanel panel, TOArticuloEnCesta art) {
        int stock = facade.getStock(art.getIdArticulo(), art.getColor().name(), art.getTalla().name());
        SpinnerNumberModel cantidad = new SpinnerNumberModel(0, 0, stock, 1);
        JSpinner unidades = new JSpinner(cantidad);
        panel.add(unidades);
        JButton anyadir = new JButton("Confirmar");
        panel.add(anyadir);
        anyadir.addActionListener((e -> {
            int uds = (int) unidades.getValue();
            if (uds == 0) {
                facade.removeArticuloDeCesta(art);
            }
            art.setCantidad(uds);
        }));
    }


    @Override
    public void update() {

    }

    @Override
    public void reset() {
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, "Panel_cesta");
    }

    @Override
    public void onArticuloAdded(TOArticuloEnFavoritos toArticuloEnFavoritos) {
        JPanel _articulo = new JPanel(new BoxLayout(panelFavs, BoxLayout.X_AXIS));
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
}
