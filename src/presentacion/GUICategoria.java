package presentacion;

import negocio.Articulo;
import negocio.BOStock;
import negocio.SAFacade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class GUICategoria extends MainGUIPanel implements ActionListener {
    //Después de haberle dado a una categoría en si

    Map<JButton, Articulo> articulos;
    private JPanel pcat;
    private SAFacade sa;
    private String cat;
    private JButton atras;
    private GUIPpalCategorias guippal;
    private JPanel art;
    private String subcatfiltro;
    private String colorfiltro;
    private String preciofiltro;
    private String tallafiltro;
    private static String[] valoresprecio = {"10 €", "25 €", "50 €"};

    GUICategoria(SAFacade sa, String cat, GUIPpalCategorias ppal) {
        this.sa = sa;
        this.cat = cat;
        this.guippal = ppal;
        subcatfiltro = "";
        colorfiltro = "";
        preciofiltro = "";
        tallafiltro = "";
        initGUI();
    }

    private void initGUI() {

        pcat = new JPanel();
        pcat.setLayout(new BorderLayout());

        //PANEL QUE TENDRÁ LA LISTA DE ARTICULOS DE LA CATEGORÍA
        art = new JPanel();
        art.setLayout(new BoxLayout(art, BoxLayout.Y_AXIS));
        art.setAlignmentX(JComponent.LEFT_ALIGNMENT);

        this.articulos = new HashMap<>();
        añadeBotones();

        //Parte de arriba de la interfaz
        JPanel arriba = new JPanel();
        //BOTÓN ATRÁS
        this.atras = new JButton("Atras");
        atras.setToolTipText("Vuelve a las categorías");
        atras.addActionListener((e) -> {
            this.guippal.setViewportView(this.guippal.getPanelView());
        });
        atras.setAlignmentX(LEFT_ALIGNMENT);
        arriba.add(atras);

        //BOTON FILTRAR
        JMenuBar fmenuBar = new JMenuBar();
        JMenu filtro = new JMenu("Filtrar");
        filtro.setToolTipText("Filtra los productos con determinadas caracteristicas");
        filtro.setAlignmentX(RIGHT_ALIGNMENT);

        JMenu mcolor = new JMenu("Color");
        for (BOStock.Color a : BOStock.Color.values()) {
            JCheckBoxMenuItem colores = new JCheckBoxMenuItem(BOStock.colorToString(a));
            colores.addActionListener((e) -> {
                colorfiltro = colores.getText();
            });
            mcolor.add(colores);
        }
        filtro.add(mcolor);

        JMenu mprecio = new JMenu("Precio max.");
        for (String a : valoresprecio) {
            JCheckBoxMenuItem vprecios = new JCheckBoxMenuItem(a);
            vprecios.addActionListener((e) -> {
                StringTokenizer tokens = new StringTokenizer(vprecios.getText());
                preciofiltro = tokens.nextToken();
            });
            mprecio.add(vprecios);
        }
        filtro.add(mprecio);

        JMenu msubcat = new JMenu("Subcategoria");
        for (Articulo.Subcategoria a : Articulo.Subcategoria.values()) {
            JCheckBoxMenuItem subcat = new JCheckBoxMenuItem(Articulo.subcategoriaToString(a));
            subcat.addActionListener((e) -> {
                subcatfiltro = subcat.getText();
            });
            msubcat.add(subcat);
        }
        filtro.add(msubcat);

        JMenu mtalla = new JMenu("Talla");
        for (BOStock.Talla a : BOStock.Talla.values()) {
            JCheckBoxMenuItem talla = new JCheckBoxMenuItem(BOStock.tallatoString(a));
            talla.addActionListener((e) -> {
                tallafiltro = talla.getText();
            });
            mtalla.add(talla);
        }
        filtro.add(mtalla);

        fmenuBar.add(filtro);
        arriba.add(fmenuBar);

        //BOTON BUSCAR (aplica los filtros seleccionados)
        JButton buscar = new JButton("Buscar");
        buscar.setToolTipText("Aplica los filtros seleccionados");
        buscar.addActionListener((e) -> {
            update();
        });
        arriba.add(buscar);

        pcat.add(arriba, BorderLayout.PAGE_START);

        this.setVisible(true);
        this.setViewportView(pcat);
    }

    public void actionPerformed(ActionEvent e) {
        Articulo a = this.articulos.get(e.getSource());
        if (a != null) {
            this.setVisible(false);
            GUIArticulo art = new GUIArticulo(a, cat, this, this.sa);
            this.guippal.setViewportView(art);
        }
    }

    private void añadeBotones() {
        List<Articulo> lista = this.sa.buscaArticulosCategoria(this.cat);
        if (!preciofiltro.equals("")) {
            double p = Double.parseDouble(preciofiltro);
            lista = this.sa.buscaFiltro(lista, Articulo -> Articulo.getPrecio() <= p);
        }
        if (!subcatfiltro.equals(""))
            lista = this.sa.buscaFiltro(lista, Articulo -> Articulo.subcategoriaToString(Articulo.getSubcat()).equals(subcatfiltro));
        if (!colorfiltro.equals(""))
            lista = this.sa.buscaFiltro(lista, Articulo -> sa.getStockColor(Articulo.getID(), colorfiltro) > 0);
        if (!tallafiltro.equals(""))
            lista = this.sa.buscaFiltro(lista, Articulo -> sa.getStockTalla(Articulo.getID(), tallafiltro) > 0);

        for (Articulo a : lista) {
            JButton botonart = new JButton(a.getName());
            botonart.setToolTipText("Muestra este articulo");
            botonart.addActionListener(this);
            articulos.put(botonart, a);
        }
        for (JButton b : this.articulos.keySet()) {
            art.add(b);
        }
        pcat.add(art, BorderLayout.CENTER);
    }

    @Override
    public void update() {
        for (JButton b : articulos.keySet()) {
            art.remove(b);
        }
        articulos.clear();
        añadeBotones();
        this.subcatfiltro = "";
        this.colorfiltro = "";
        this.preciofiltro = "";
        this.tallafiltro = "";
    }

    @Override
    public void reset() {
        this.guippal.setViewportView(this.guippal.getPanelView());
        this.setVisible(false);
    }

    public JPanel getPanelView() {
        return this.pcat;
    }

    public void setJPanelViewCat() {
        this.guippal.setJPanelViewCat(this.pcat);
    }
}
