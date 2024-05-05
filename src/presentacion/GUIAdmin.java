package presentacion;

import negocio.SAFacade;
import negocio.TOStatusPedido;
import negocio.tArticulo;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Objects;


public class GUIAdmin extends JPanel {

    private JPanel crearArticulo;
    private JPanel añadirSaldo;
    private JPanel cambiarSuscripcion;
    private JPanel cambiarEstadoPedido;
    private JPanel cambiarArticulo;
    private JButton bCrearArticulo, bAñadirSaldo, bCambiarSuscripcion, bCambiarEstadoPedido, bcambiarArticulo;
    private SAFacade saFacade;


    public GUIAdmin(SAFacade SaFacade) {
        this.saFacade = SaFacade;
        initGUI();
    }

    private void initGUI() {

        setLayout(new BorderLayout());

        setBorder(new TitledBorder("Admin"));

        initCrearArticulo();
        initAñadirSaldo();
        initCambiarSuscripcion();
        initCambiarEstadoPedido();
        initCambiarArticulo();


        JPanel paneles = new JPanel();
        paneles.setLayout(new OverlayLayout(paneles));
        paneles.add(crearArticulo);
        paneles.add(añadirSaldo);
        paneles.add(cambiarSuscripcion);
        paneles.add(cambiarEstadoPedido);
        paneles.add(cambiarArticulo);

        crearArticulo.setVisible(true);

        this.add(paneles, BorderLayout.CENTER);

        JPanel botones = new JPanel();

        bCrearArticulo = new JButton("Crear Articulo");
        bCrearArticulo.setAlignmentX(CENTER_ALIGNMENT);
        bCrearArticulo.addActionListener(e -> {
            añadirSaldo.setVisible(false);
            cambiarSuscripcion.setVisible(false);
            cambiarEstadoPedido.setVisible(false);
            this.crearArticulo.setVisible(true);
        });
        botones.add(bCrearArticulo);

        bAñadirSaldo = new JButton("Añadir Saldo");
        bAñadirSaldo.setAlignmentX(CENTER_ALIGNMENT);
        bAñadirSaldo.addActionListener(e -> {
            crearArticulo.setVisible(false);
            cambiarSuscripcion.setVisible(false);
            cambiarEstadoPedido.setVisible(false);
            this.añadirSaldo.setVisible(true);
        });
        botones.add(bAñadirSaldo);

        bCambiarSuscripcion = new JButton("Cambiar Suscripcion");
        bCambiarSuscripcion.setAlignmentX(CENTER_ALIGNMENT);
        bCambiarSuscripcion.addActionListener(e -> {
            crearArticulo.setVisible(false);
            añadirSaldo.setVisible(false);
            cambiarEstadoPedido.setVisible(false);
            this.cambiarSuscripcion.setVisible(true);
        });
        botones.add(bCambiarSuscripcion);

        bCambiarEstadoPedido = new JButton("Cambiar Estado Pedido");
        bCambiarEstadoPedido.setAlignmentX(CENTER_ALIGNMENT);
        bCambiarEstadoPedido.addActionListener(e -> {
            crearArticulo.setVisible(false);
            añadirSaldo.setVisible(false);
            cambiarSuscripcion.setVisible(false);
            this.cambiarEstadoPedido.setVisible(true);
        });
        botones.add(bCambiarEstadoPedido);


        bcambiarArticulo = new JButton("Cambiar Articulo");
        bcambiarArticulo.setAlignmentX(CENTER_ALIGNMENT);
        botones.add(bcambiarArticulo);
        bcambiarArticulo.addActionListener(e -> {
            crearArticulo.setVisible(false);
            añadirSaldo.setVisible(false);
            cambiarSuscripcion.setVisible(false);
            cambiarEstadoPedido.setVisible(false);
            this.cambiarArticulo.setVisible(true);
        });
        add(botones, BorderLayout.NORTH);

    }

    private void initCambiarArticulo(){
        cambiarArticulo = new JPanel();
        cambiarArticulo.setLayout(new BoxLayout(cambiarArticulo, BoxLayout.Y_AXIS));
        cambiarArticulo.setBorder(new TitledBorder("Cambiar Articulo"));
        cambiarArticulo.setVisible(false);

        JLabel lId = new JLabel("ID: ");
        this.cambiarArticulo.add(lId);
        JTextField tId = new JTextField();
        tId.setMaximumSize(new Dimension(200, 20));
        this.cambiarArticulo.add(tId);

        JLabel lNombre = new JLabel("Nombre: ");
        this.cambiarArticulo.add(lNombre);
        JTextField tNombre = new JTextField();
        tNombre.setMaximumSize(new Dimension(200, 20));
        this.cambiarArticulo.add(tNombre);

        JLabel lSubcategoria = new JLabel("Subcategoria: ");
        this.cambiarArticulo.add(lSubcategoria);
        JTextField tSubcategoria = new JTextField();
        tSubcategoria.setMaximumSize(new Dimension(200, 20));
        this.cambiarArticulo.add(tSubcategoria);

        JLabel lPrecio = new JLabel("Precio: ");
        this.cambiarArticulo.add(lPrecio);
        JTextField tPrecio = new JTextField();
        tPrecio.setMaximumSize(new Dimension(200, 20));
        this.cambiarArticulo.add(tPrecio);

        JButton bCambiar = new JButton("Cambiar");
        this.cambiarArticulo.add(bCambiar);
        bCambiar.addActionListener(e -> {
            tArticulo art = new tArticulo(Integer.parseInt(tId.getText()), tNombre.getText(), tSubcategoria.getText(), Double.parseDouble(tPrecio.getText()));
            saFacade.modificarArticulo(art);
            this.cambiarArticulo.setVisible(false);
            this.setVisible(true);
        });

        JButton bCancelar = new JButton("Cancelar");
        this.cambiarArticulo.add(bCancelar);
        bCancelar.addActionListener(e -> {
            cambiarArticulo.setVisible(false);
            this.setVisible(true);
        });

        JPanel wrapper = new JPanel();
        cambiarArticulo.add(wrapper);

    }

    private void initCrearArticulo() {

        //TArticulo

        crearArticulo = new JPanel();
        crearArticulo.setLayout(new BoxLayout(crearArticulo, BoxLayout.Y_AXIS));
        crearArticulo.setBorder(new TitledBorder("Crear Articulo"));
        crearArticulo.setVisible(false);

        JLabel lId = new JLabel("ID: ");
        this.crearArticulo.add(lId);
        JTextField tId = new JTextField();
        tId.setMaximumSize(new Dimension(200, 20));
        this.crearArticulo.add(tId);

        JLabel lNombre = new JLabel("Nombre: ");
        this.crearArticulo.add(lNombre);
        JTextField tNombre = new JTextField();
        tNombre.setMaximumSize(new Dimension(200, 20));
        this.crearArticulo.add(tNombre);

        JLabel lSubcategoria = new JLabel("Subcategoria: ");
        this.crearArticulo.add(lSubcategoria);
        JTextField tSubcategoria = new JTextField();
        tSubcategoria.setMaximumSize(new Dimension(200, 20));
        this.crearArticulo.add(tSubcategoria);

        JLabel lPrecio = new JLabel("Precio: ");
        this.crearArticulo.add(lPrecio);
        JTextField tPrecio = new JTextField();
        tPrecio.setMaximumSize(new Dimension(200, 20));
        this.crearArticulo.add(tPrecio);


        //resto
        JLabel lFecha = new JLabel("Fecha: ");
        this.crearArticulo.add(lFecha);
        JTextField tFecha = new JTextField();
        tFecha.setMaximumSize(new Dimension(200, 20));
        this.crearArticulo.add(tFecha);

        JLabel lDescuento = new JLabel("Descuento: ");
        this.crearArticulo.add(lDescuento);
        JTextField tDescuento = new JTextField();
        tDescuento.setMaximumSize(new Dimension(200, 20));
        this.crearArticulo.add(tDescuento);

        JLabel lGenero = new JLabel("Genero: ");
        this.crearArticulo.add(lGenero);
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Hombre");
        model.addElement("Mujer");
        //TODO mirar de meter bien los generos
        JComboBox<String> cbGenero = new JComboBox<>(model);
        cbGenero.setMaximumSize(new Dimension(200, 20));
        this.crearArticulo.add(cbGenero);

        JLabel lStock = new JLabel("Stock: ");
        this.crearArticulo.add(lStock);
        JTextField tStock = new JTextField();
        tStock.setMaximumSize(new Dimension(200, 20));
        this.crearArticulo.add(tStock);


        JButton bCrear = new JButton("Crear");
        this.crearArticulo.add(bCrear);
        bCrear.addActionListener(e -> {
            tArticulo art = new tArticulo(Integer.parseInt(tId.getText()), tNombre.getText(), tSubcategoria.getText(), Double.parseDouble(tPrecio.getText()));
            String gen = String.valueOf(cbGenero.getSelectedItem());
            String fecha = "";
            if (!Objects.equals(tFecha.getText(), "")) fecha = tFecha.getText();
            double descuento = 0.0;
            if (!Objects.equals(tDescuento.getText(), "")) descuento = Double.parseDouble(tDescuento.getText());
            saFacade.altaArticulo(art, fecha, gen, descuento, Integer.parseInt(tStock.getText()));
        });

        JButton bCancelar = new JButton("Cancelar");
        this.crearArticulo.add(bCancelar);
        bCancelar.addActionListener(e -> {
            crearArticulo.setVisible(false);
            this.setVisible(true);
        });

        JPanel wrapper = new JPanel();
        crearArticulo.add(wrapper);
    }

    private void initAñadirSaldo() {

        añadirSaldo = new JPanel();
        añadirSaldo.setLayout(new BoxLayout(añadirSaldo, BoxLayout.Y_AXIS));
        añadirSaldo.setBorder(new TitledBorder("Añadir Saldo"));
        añadirSaldo.setVisible(false);

        JLabel lId = new JLabel("ID: ");
        this.añadirSaldo.add(lId);
        JTextField tId = new JTextField();
        tId.setMaximumSize(new Dimension(200, 20));
        this.añadirSaldo.add(tId);

        JLabel lSaldo = new JLabel("Saldo: ");
        this.añadirSaldo.add(lSaldo);
        JTextField tSaldo = new JTextField();
        tSaldo.setMaximumSize(new Dimension(200, 20));
        this.añadirSaldo.add(tSaldo);

        JButton bAñadir = new JButton("Añadir");
        this.añadirSaldo.add(bAñadir);
        bAñadir.addActionListener(e -> {
            saFacade.actualizarSaldoAdmin(Double.parseDouble(tSaldo.getText()), Integer.parseInt(tId.getText()));
        });

        JButton bCancelar = new JButton("Cancelar");
        this.añadirSaldo.add(bCancelar);
        bCancelar.addActionListener(e -> {
            añadirSaldo.setVisible(false);
            this.setVisible(true);
        });

        JPanel wrapper = new JPanel();
        añadirSaldo.add(wrapper);
    }

    private void initCambiarSuscripcion() {

        cambiarSuscripcion = new JPanel();
        cambiarSuscripcion.setLayout(new BoxLayout(cambiarSuscripcion, BoxLayout.Y_AXIS));
        cambiarSuscripcion.setBorder(new TitledBorder("Cambiar Suscripcion"));
        cambiarSuscripcion.setVisible(false);

        JLabel lId = new JLabel("ID: ");
        this.cambiarSuscripcion.add(lId);
        JTextField tId = new JTextField();
        tId.setMaximumSize(new Dimension(200, 20));
        this.cambiarSuscripcion.add(tId);

        JLabel lSuscripcion = new JLabel("Suscripcion: ");
        this.cambiarSuscripcion.add(lSuscripcion);
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        JComboBox<String> cbSuscripcion = new JComboBox<>(model);
        cbSuscripcion.setMaximumSize(new Dimension(200, 20));
        this.cambiarSuscripcion.add(cbSuscripcion);

        JButton bCambiar = new JButton("Cambiar");
        this.cambiarSuscripcion.add(bCambiar);
        bCambiar.addActionListener(e -> {
            saFacade.actualizarSuscrAdmin(Integer.parseInt(tId.getText()), cbSuscripcion.getSelectedIndex()); //TODO Cambiar
        });

        JButton bCancelar = new JButton("Cancelar");
        this.cambiarSuscripcion.add(bCancelar);
        bCancelar.addActionListener(e -> {
            cambiarSuscripcion.setVisible(false);
            this.setVisible(true);
        });

        JPanel wrapper = new JPanel();
        cambiarSuscripcion.add(wrapper);
    }

    private void initCambiarEstadoPedido() {
        cambiarEstadoPedido = new JPanel();
        cambiarEstadoPedido.setVisible(false);
        cambiarEstadoPedido.setLayout(new BoxLayout(cambiarEstadoPedido, BoxLayout.Y_AXIS));
        cambiarEstadoPedido.setBorder(new TitledBorder("Cambiar Estado Pedido"));

        JLabel lId = new JLabel("ID: ");
        this.cambiarEstadoPedido.add(lId);

        JTextField tId = new JTextField();
        tId.setMaximumSize(new Dimension(200, 20));
        this.cambiarEstadoPedido.add(tId);

        DefaultComboBoxModel<TOStatusPedido> model = new DefaultComboBoxModel<>(TOStatusPedido.values());
        JComboBox<TOStatusPedido> cbEstado = new JComboBox<>(model);
        cbEstado.setMaximumSize(new Dimension(200, 20));
        this.cambiarEstadoPedido.add(cbEstado);

        JButton bCambiar = new JButton("Cambiar");
        this.cambiarEstadoPedido.add(bCambiar);
        bCambiar.addActionListener(e -> {
            saFacade.cambiarStatus(Integer.parseInt(tId.getText()), (TOStatusPedido) cbEstado.getSelectedItem());
        });

        JButton bCancelar = new JButton("Cancelar");
        this.cambiarEstadoPedido.add(bCancelar);
        bCancelar.addActionListener(e -> {
            cambiarEstadoPedido.setVisible(false);
            this.setVisible(true);
        });

        JPanel wrapper = new JPanel();
        cambiarEstadoPedido.add(wrapper);
    }
}
