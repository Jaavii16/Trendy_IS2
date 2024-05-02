package presentacion;

import negocio.CestaObserver;
import negocio.SAFacade;
import negocio.TOArticuloEnCesta;
import negocio.TOCesta;

import javax.swing.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

public class GUICesta extends MainGUIPanel implements CestaObserver {

    SAFacade facade;
    JPanel mainPanel;
    private HashMap<String, JPanel> panelMap;

    public GUICesta(SAFacade saFacade) {
        this.facade = saFacade;
        facade.registerObserver(this);
        panelMap = new HashMap<>();
        initGui();
    }

    private void initGui() {

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.setOpaque(true);

        this.setViewportView(mainPanel);

        //TODO mirar porque creo que cuando herede de JScrollPane no va a funcionar
        /*this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);*/
    }

    @Override
    public void onCestaChanged(TOCesta cesta) {
        mainPanel.removeAll();//elimino lo antiguo
        panelMap.clear();
        TreeSet<TOArticuloEnCesta> lista = (TreeSet<TOArticuloEnCesta>) cesta.getListaArticulos();
        Iterator<TOArticuloEnCesta> art_it = lista.iterator();
        while (art_it.hasNext()) {
            TOArticuloEnCesta art = art_it.next();
            JPanel articulo = new JPanel(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
            articulo.add(new JLabel(art.toString()));//nombre¿?
            articulo.add(new JLabel(art.getTalla() + ""));
            articulo.add(new JLabel(art.getCantidad() + ""));
            addButtons(articulo, art);
            panelMap.put(art.getIdArticulo() + "", articulo);
            mainPanel.add(articulo);
        }
        mainPanel.repaint();
    }

    @Override
    public void onArticuloAdded(TOArticuloEnCesta articulo) {
        JPanel _articulo = new JPanel(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        _articulo.add(new JLabel(articulo.toString()));//nombre¿?
        _articulo.add(new JLabel(articulo.getTalla() + ""));
        _articulo.add(new JLabel(articulo.getCantidad() + ""));
        addButtons(_articulo, articulo);
        panelMap.put(articulo.getIdArticulo() + "", _articulo);
        mainPanel.add(_articulo);
        mainPanel.repaint();
    }


    public void onArticuloUpdated(TOArticuloEnCesta articulo) {
        //ELIMINAMOS EL PANEL CON LA INFORMACION ANTIGUA
        JPanel eliminar = panelMap.get(articulo.getIdArticulo() + "");
        //CREO UN PANEL CON LOS DATOS DEL ARTICULO CAMBIADOS
        JPanel _articulo = new JPanel(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        _articulo.add(new JLabel(articulo.toString()));//nombre¿?
        _articulo.add(new JLabel(articulo.getTalla() + ""));
        _articulo.add(new JLabel(articulo.getCantidad() + ""));
        addButtons(_articulo, articulo);
        panelMap.put(articulo.getIdArticulo() + "", _articulo);
        mainPanel.add(_articulo);
        mainPanel.repaint();
    }

    @Override
    public void onArticuloRemoved(TOArticuloEnCesta articulo) {
        JPanel eliminar = panelMap.get(articulo.getIdArticulo() + "");
        panelMap.remove(eliminar);
        mainPanel.remove(eliminar);
        mainPanel.repaint();
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

    }

}
