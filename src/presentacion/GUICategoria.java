package presentacion;

import negocio.Articulo;
import negocio.BOStock;
import negocio.tArticulo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class GUICategoria extends JPanel implements ActionListener {
    //Después de haberle dado a una categoría en si
    //Map<JButton, String> articulos;
    //Boton ccon el nombre del artículo, precio del artículo
    Map<JButton, Articulo> articulos;
    private SAFacade sa;
    private String cat;
    private JButton atras;
    private JMenu filtro;
    private GUIPpalCategorias guippal;

    GUICategoria(SAFacade sa, String cat, GUIPpalCategorias ppal) {
        this.sa = sa;
        this.cat = cat;
        this.guippal = guippal;
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());

        //PANEL QUE TENDRÁ LA LISTA DE ARTICULOS DE LA CATEGORÍA
        JPanel art = new JPanel();
        art.setLayout(new BoxLayout(art, BoxLayout.Y_AXIS));
        art.setAlignmentX(JComponent.LEFT_ALIGNMENT);

        List<Articulo> lista = this.sa.buscaArticulosCategoria(this.cat);
        for (Articulo a : lista) {
            JButton botonart = new JButton(a.getName());
            botonart.setToolTipText("Muestra este articulo");
            botonart.addActionListener(this);
            articulos.put(botonart, a);
        }

        for (JButton b : this.articulos.keySet()) {
            art.add(b);
        }
        this.add(art, BorderLayout.CENTER);

        //Ahora hay que crear lo de arriba (el boton de atrás y el de filtrar)
        JPanel arriba = new JPanel();
        //BOTÓN ATRÁS
        this.atras = new JButton("Atras");
        atras.setToolTipText("Vuelve a las categorías");
        atras.addActionListener((e) -> {
            //llamar a la gui de categorias supongo
            this.guippal.setVisible(true);
            this.setVisible(false); //?? no estoy segura
        });
        atras.setAlignmentX(LEFT_ALIGNMENT);
        arriba.add(atras);

        //BOTON FILTRAR
        this.filtro = new JMenu("Filtrar");
        filtro.setToolTipText("Filtra los productos con determinadas caracteristicas");
        filtro.setAlignmentX(RIGHT_ALIGNMENT);

        JMenu mcolor = new JMenu("Color");
        for (BOStock.Color a : BOStock.Color.values()) {
            JCheckBoxMenuItem colores = new JCheckBoxMenuItem(BOStock.colorToString(a));
            mcolor.add(colores);
        }
        filtro.add(mcolor);

        JPanel precio = new JPanel();
        precio.setLayout(new BoxLayout(precio, BoxLayout.X_AXIS));
        JLabel lprecio = new JLabel("Precio máx.");
        DefaultComboBoxModel<String> precios = new DefaultComboBoxModel<>();
        precios.addElement("-");
        precios.addElement("10 €");
        precios.addElement("25 €");
        precios.addElement("50 €");
        JComboBox boxprecios = new JComboBox(precios);
        precio.add(lprecio);
        precio.add(boxprecios);
        filtro.add(precio);

        JMenu msubcat = new JMenu("Subcategoria");
        for (Articulo.Subcategoria a : Articulo.Subcategoria.values()) {
            JCheckBoxMenuItem subcat = new JCheckBoxMenuItem(Articulo.subcategoriaToString(a));
            msubcat.add(subcat);
        }
        filtro.add(msubcat);

        JPanel talla = new JPanel();
        talla.setLayout(new BoxLayout(talla, BoxLayout.X_AXIS));
        JLabel ltalla = new JLabel("Talla");
        DefaultComboBoxModel<String> tallas = new DefaultComboBoxModel<>();
        precios.addElement("XS");
        precios.addElement("S");
        precios.addElement("M");
        precios.addElement("L");
        precios.addElement("XL");
        JComboBox boxtallas = new JComboBox(tallas);
        talla.add(ltalla);
        talla.add(boxtallas);
        filtro.add(talla);

        arriba.add(filtro);
        this.add(arriba, BorderLayout.PAGE_START);
    }

    public void actionPerformed(ActionEvent e){
        Articulo a = this.articulos.get(e.getSource());
        if(a != null){
            GUIArticulo art = new GUIArticulo(a, cat, this, this.sa);
            this.setVisible(false);
        }
    }
}
