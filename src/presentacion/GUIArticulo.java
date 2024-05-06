package presentacion;

import negocio.*;

import javax.swing.*;
import java.awt.*;

public class GUIArticulo extends MainGUIPanel {
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

    GUIArticulo(Articulo art, String cat, GUICategoria categoria, SAFacade sa) {
        this.sa = sa;
        this.art = art;
        this.categoria = cat;
        this.guicategoria = categoria;
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
        JLabel ltalla = new JLabel("Talla");
        DefaultComboBoxModel<String> tallas = new DefaultComboBoxModel<>();
        for (BOStock.Talla a : BOStock.Talla.values()) {
            tallas.addElement(BOStock.tallatoString(a));
        }
        boxtallas = new JComboBox(tallas);
        boxtallas.addActionListener((e) -> {
            String aux = (String) boxtallas.getSelectedItem();
            this.tallaselect = BOStock.stringtoTalla(aux);
        });
        talla.add(ltalla);
        talla.add(boxtallas);
        talla.setMaximumSize(new Dimension(100, 100));
        centro.add(Box.createRigidArea(new Dimension(100, 100)));
        centro.add(talla);

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
        color.setMaximumSize(new Dimension(110, 100));
        centro.add(color);

        //Unidades:
        JPanel unidades = new JPanel();
        JLabel lunidades = new JLabel("Unidades a comprar");
        //Se añaden a la cesta, otra cosa es lo que se puedan comprar
        uds = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
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
        this.guicategoria.setJPanelViewCat();
        this.setVisible(false);
        this.guicategoria.setVisible(false);
    }

    private void añadirBotones() {
        end.removeAll();

        if (sa.esExclusivo(art)) {
            reservar = new JButton("Reservar");
            reservar.addActionListener((e) -> {
                if (sa.esPremium()) {
                    TOArticuloEnReservas artEnReservas = new TOArticuloEnReservas(
                            art.getID(),
                            sa.getUsuario().getId(),
                            BOStock.Talla.valueOf(((String) boxtallas.getSelectedItem()).toUpperCase()),
                            BOStock.Color.valueOf(((String) boxcolores.getSelectedItem()).toUpperCase())
                    );
                    try{
                        sa.addArticuloAReservas(artEnReservas);
                        JOptionPane.showMessageDialog(this, "Añadido a reservas con éxito");
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(this, "No se ha podido añadir a reservas");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Solo para usuarios con suscripcion premium");
                }

            });
            end.add(reservar);
        } else {
            cesta = new JButton("Añadir a cesta");
            cesta.addActionListener((e) -> {
                try{
                    TOArticuloEnCesta artEnCesta = new TOArticuloEnCesta();
                    artEnCesta.setCantidad((int) uds.getValue());
                    artEnCesta.setColor(BOStock.Color.valueOf(((String) boxcolores.getSelectedItem()).toUpperCase()));
                    artEnCesta.setTalla(BOStock.Talla.valueOf(((String) boxtallas.getSelectedItem()).toUpperCase()));
                    artEnCesta.setIdArticulo(art.getID());
                    sa.addArticuloACesta(artEnCesta);
                    JOptionPane.showMessageDialog(this, "Añadido a la cesta correctamente");
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "No se ha podido añadir a la cesta");
                }
            });
            end.add(cesta);
        }

        favoritos = new JButton("Añadir a favoritos");
        favoritos.addActionListener(e -> {
            try{
                TOArticuloEnFavoritos artEnFavs = new TOArticuloEnFavoritos(art.getID(), sa.getUsuario().getId());
                sa.addArticuloAFavoritos(artEnFavs);
                JOptionPane.showMessageDialog(this, "Añadido a favoritos con éxito");
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "No se ha podido añadir a favoritos");
            }

        });
        end.add(favoritos);
    }
}
