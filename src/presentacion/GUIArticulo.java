package presentacion;

import negocio.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class GUIArticulo extends MainGUIPanel implements Observable<CestaObserver> {
    //Al haberle dado a un artículo de una categoría

    private Articulo art;
    private JPanel partview;
    private JButton atras;
    private JButton cesta;
    private JButton reservar;
    private JButton favoritos;
    private String categoria;

    private JMenu tallas;
    private JSpinner uds;
    private GUICategoria guicategoria;
    private SAFacade sa;
    private BOStock.Talla tallaselect;
    private JLabel nombre;
    private JLabel precio;
    private JLabel cat;
    private JLabel subcat;
    private JPanel end;
    private JComboBox boxcolores;
    private JComboBox boxtallas;

    private Set<CestaObserver> observers;

    GUIArticulo(Articulo art, String cat, GUICategoria categoria, SAFacade sa) {
        this.sa = sa;
        this.art = art;
        this.categoria = cat;
        this.guicategoria = categoria;
        observers = new HashSet<>();
        initGUI();
    }

    private void initGUI() {
        partview = new JPanel();
        this.partview.setLayout(new BorderLayout());

        JPanel arriba = new JPanel();
        arriba.setLayout(new BoxLayout(arriba, BoxLayout.X_AXIS));

        //BOTON ATRAS
        atras = new JButton("Atras");
        atras.setToolTipText("Vuelve a los articulos de la categoria seleccionada previamente");
        atras.addActionListener((e) -> {
            this.guicategoria.setJPanelViewCat();
        });

        arriba.add(atras);
        arriba.add(Box.createRigidArea(new Dimension(30, 0)));

        //NOMBRE Y PRECIO:
        nombre = new JLabel(this.art.getName());
        arriba.add(nombre);
        arriba.add(Box.createRigidArea(new Dimension(10, 0)));

        precio = new JLabel(String.valueOf(this.art.getPrecio()) + " €");
        arriba.add(precio);
        arriba.add(Box.createRigidArea(new Dimension(30, 0)));

        //CATEGORIA Y SUBCATEGORIA
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        cat = new JLabel("Categoria: " + this.categoria);
        info.add(cat);
        subcat = new JLabel("Subcategoria: " + art.subcategoriaToString(art.getSubcat()));
        info.add(subcat);
        arriba.add(info);

        partview.add(arriba, BorderLayout.PAGE_START);

        //EL CENTRO, LA INFO DEL ARTICULO
        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        JPanel talla = new JPanel();
        talla.setLayout(new BoxLayout(talla, BoxLayout.X_AXIS));
        JLabel ltalla = new JLabel("Color");
        DefaultComboBoxModel<String> tallas = new DefaultComboBoxModel<>();
        for (BOStock.Talla a : BOStock.Talla.values()) {
            tallas.addElement(BOStock.tallatoString(a));
        }
        boxtallas = new JComboBox(tallas);
        talla.add(ltalla);
        talla.add(boxtallas);
        centro.add(talla);
        /*
        this.t = new JPanel();
        t.setLayout(new BoxLayout(t, BoxLayout.X_AXIS));

        JLabel talla = new JLabel("Tallas:");
        //Botones de las tallas -> mejor hacemos el tipico menu
        this.tallas = new JMenu("Selecciona talla");
        JCheckBoxMenuItem XS = new JCheckBoxMenuItem("XS");
        XS.setActionCommand("XS");
        JCheckBoxMenuItem S = new JCheckBoxMenuItem("S");
        S.setActionCommand("S");
        JCheckBoxMenuItem M = new JCheckBoxMenuItem("M");
        M.setActionCommand("M");
        JCheckBoxMenuItem L = new JCheckBoxMenuItem("L");
        L.setActionCommand("L");
        JCheckBoxMenuItem XL = new JCheckBoxMenuItem("XL");
        XL.setActionCommand("XL");

        tallas.add(XS);
        tallas.add(S);
        tallas.add(M);
        tallas.add(L);
        tallas.add(XL);
        tallas.addActionListener((e) -> {
            String aux = e.getActionCommand();
            this.tallaselect = BOStock.stringtoTalla(e.getActionCommand());
        });

        t.add(talla);
        t.add(tallas);
        centro.add(t);
        */
        //Colores:
        JPanel color = new JPanel();
        color.setLayout(new BoxLayout(color, BoxLayout.X_AXIS));
        JLabel c = new JLabel("Color");
        DefaultComboBoxModel<String> colores = new DefaultComboBoxModel<>();
        for (BOStock.Color a : BOStock.Color.values()) {
            colores.addElement(BOStock.colorToString(a));
        }
        boxcolores = new JComboBox(colores);
        color.add(c);
        color.add(boxcolores);
        centro.add(color);

        //Unidades:
        JPanel unidades = new JPanel();
        JLabel lunidades = new JLabel("Unidades a comprar");
        //Inicializamos las tallas para que salga la M al principio:
        this.tallaselect = BOStock.Talla.M;
        int stock = this.sa.getStock(this.art.getID(), (String) boxcolores.getSelectedItem(), String.valueOf(this.tallaselect));
        uds = new JSpinner(new SpinnerNumberModel(1, 1, stock, 1));
        unidades.add(lunidades);
        unidades.add(uds);
        centro.add(unidades);

        partview.add(centro, BorderLayout.CENTER);

        //END, BOTONES
        end = new JPanel();
        end.setLayout(new BoxLayout(end, BoxLayout.X_AXIS));
        end.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        añadirBotones();

        partview.add(end, BorderLayout.PAGE_END);

        this.setVisible(true);
        this.setViewportView(partview);
    }

    @Override
    public void update() {
        nombre.setText(this.art.getName());
        precio.setText(String.valueOf(this.art.getPrecio()) + " €");
        cat.setText("Categoria: " + this.categoria);
        subcat.setText("Subcategoria: " + art.subcategoriaToString(art.getSubcat()));
        añadirBotones();
    }

    @Override
    public void reset() {
        //this.setViewportView(); la principal supongo
        this.setVisible(false);
        this.guicategoria.setVisible(false);
    }

    private void añadirBotones() {
        for (Component a : end.getComponents()) {
            end.remove(a);
        }

        //CREAR FUNCION PARA COMPROBAR SI ES PREMIUM PORQUE TENEMOS QUE VER QUE EN CASO DE QUE SEA
        //SE PUEDA RESERVAR EL ARTICULO
        if(sa.esPremium()){
            if (sa.esExclusivo(art)) {
                reservar = new JButton("Reservar");
                reservar.addActionListener((e) -> {
                    //se reserva

                });
                end.add(reservar);
            }
        }

        cesta = new JButton("Añadir a cesta");
        cesta.addActionListener((e) -> {
            //se añade a la cesta (sa)
            TOArticuloEnCesta artEnCesta = new TOArticuloEnCesta();
            artEnCesta.setCantidad((int)uds.getValue());
            artEnCesta.setColor((BOStock.Color)boxcolores.getSelectedItem());
            artEnCesta.setTalla((TOArticuloEnCesta.Talla)boxtallas.getSelectedItem());
            artEnCesta.setIdArticulo(art.getID());
            //artEnCesta.setFechaAñadido(); //TODO no se como coger la fecha
            sa.addArticuloACesta(artEnCesta);
            for(CestaObserver o: observers){
                o.onArticuloAdded(artEnCesta);
            }
        });
        end.add(cesta);

        favoritos = new JButton("Añadir a favoritos");
        favoritos.addActionListener(e -> {
            //se añade a favoritos (sa)
        });
        end.add(favoritos);
    }

    @Override
    public void addObserver(CestaObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(CestaObserver observer) {
        observers.remove(observer);
    }
}
