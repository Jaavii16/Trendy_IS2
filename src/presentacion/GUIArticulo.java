package presentacion;

import negocio.Articulo;
import negocio.BOStock;
import negocio.SAFacade;

import javax.swing.*;
import java.awt.*;

public class GUIArticulo extends MainGUIPanel {
    //Al haberle dado a un artículo de una categoría

    private Articulo art;
    private JButton atras;
    private JButton cesta;
    private JButton reservar;
    private JButton favoritos;
    private String categoria;

    private JPanel t;
    private JMenu tallas;
    private DefaultComboBoxModel<String> colores;
    private JComboBox boxcolores;
    private JSpinner uds;
    private GUICategoria guicategoria;
    private SAFacade sa;
    private BOStock.Talla tallaselect;

    GUIArticulo(Articulo art, String cat, GUICategoria categoria, SAFacade sa) {
        this.sa = sa;
        this.art = art;
        this.categoria = cat;
        this.guicategoria = categoria;
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());

        JPanel arriba = new JPanel();
        arriba.setLayout(new BoxLayout(arriba, BoxLayout.X_AXIS));

        //BOTON ATRAS
        atras = new JButton("Atras");
        atras.setToolTipText("Vuelve a los articulos de la categoria seleccionada previamente");
        atras.addActionListener((e) -> {
            this.guicategoria.setVisible(true);
            this.setVisible(false);
        });

        arriba.add(atras);
        arriba.add(Box.createRigidArea(new Dimension(30, 0)));

        //NOMBRE Y PRECIO:
        JLabel nombre = new JLabel(this.art.getName());
        arriba.add(nombre);
        arriba.add(Box.createRigidArea(new Dimension(10, 0)));

        JLabel precio = new JLabel(String.valueOf(this.art.getPrecio()) + " €");
        arriba.add(precio);
        arriba.add(Box.createRigidArea(new Dimension(30, 0)));


        //CATEGORIA Y SUBCATEGORIA
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        JLabel cat = new JLabel("Categoria: " + this.categoria);
        info.add(cat);
        JLabel subcat = new JLabel("Subcategoria: " + art.subcategoriaToString(art.getSubcat()));
        info.add(subcat);
        arriba.add(info);

        this.add(arriba, BorderLayout.PAGE_START);


        //EL CENTRO, LA INFO DEL ARTICULO
        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setAlignmentX(JComponent.CENTER_ALIGNMENT);

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

        //Colores:
        JPanel color = new JPanel();
        color.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JLabel c = new JLabel("Color");
        colores = new DefaultComboBoxModel<>();
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
        int stock = this.sa.getStock(this.art.getID(), (String) boxcolores.getSelectedItem(), String.valueOf(this.tallaselect));
        uds = new JSpinner(new SpinnerNumberModel(1, 1, stock, 1));
        unidades.add(lunidades);
        unidades.add(uds);
        centro.add(unidades);

        this.add(centro, BorderLayout.CENTER);

        //END, BOTONES
        JPanel end = new JPanel();
        end.setLayout(new BoxLayout(end, BoxLayout.X_AXIS));
        end.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        if (this.categoria == "EXCLUSIVOS") {
            reservar = new JButton("Reservar");
            reservar.addActionListener((e) -> {
                //se reserva

            });
        } else {
            cesta = new JButton("Añadir a cesta");
            cesta.addActionListener((e) -> {
                //se añade a la cesta (sa)
            });
        }

        favoritos = new JButton("Añadir a favoritos");
        favoritos.addActionListener(e -> {
            //se añade a favoritos (sa)
        });
        end.add(cesta);
        end.add(favoritos);

        this.add(end, BorderLayout.PAGE_END);
    }

    @Override
    public void update() {

    }

    @Override
    public void reset() {
        this.setVisible(false);
        this.guicategoria.setVisible(false);
    }
}
