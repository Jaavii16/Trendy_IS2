package presentacion;

import negocio.SAFacade;
import negocio.TOStatusPedido;
import negocio.tArticulo;

import javax.swing.*;
import java.awt.*;


public class GUIAdmin extends JPanel {

    private JPanel crearArticulo;
    private JPanel añadirSaldo;
    private JPanel cambiarSuscripcion;
    private JPanel cambiarEstadoPedido;
    private JButton bCrearArticulo, bAñadirSaldo, bCambiarSuscripcion, bCambiarEstadoPedido;
    private SAFacade saFacade;


    public GUIAdmin(SAFacade SaFacade) {
        this.saFacade = SaFacade;
        initGUI();
    }

    private void initGUI() {

        setLayout(new BorderLayout());

        initCrearArticulo();
        initAñadirSaldo();
        initCambiarSuscripcion();
        initCambiarEstadoPedido();

        JPanel paneles = new JPanel();
        paneles.setLayout(new OverlayLayout(paneles));
        paneles.add(crearArticulo);
        paneles.add(añadirSaldo);
        paneles.add(cambiarSuscripcion);
        paneles.add(cambiarEstadoPedido);

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

        add(botones, BorderLayout.NORTH);
    }

    private void initCrearArticulo() {

        //TArticulo

        crearArticulo = new JPanel();
        crearArticulo.setLayout(new BoxLayout(crearArticulo, BoxLayout.Y_AXIS));

        JLabel lId = new JLabel("ID: ");
        this.crearArticulo.add(lId);
        JTextField tId = new JTextField();
        this.crearArticulo.add(tId);

        JLabel lNombre = new JLabel("Nombre: ");
        this.crearArticulo.add(lNombre);
        JTextField tNombre = new JTextField();
        this.crearArticulo.add(tNombre);

        JLabel lSubcategoria = new JLabel("Subcategoria: ");
        this.crearArticulo.add(lSubcategoria);
        JTextField tSubcategoria = new JTextField();
        this.crearArticulo.add(tSubcategoria);

        JLabel lPrecio = new JLabel("Precio: ");
        this.crearArticulo.add(lPrecio);
        JTextField tPrecio = new JTextField();
        this.crearArticulo.add(tPrecio);


        //resto
        JLabel lFecha = new JLabel("Fecha: ");
        this.crearArticulo.add(lFecha);
        JTextField tFecha = new JTextField();
        this.crearArticulo.add(tFecha);

        JLabel lDescuento = new JLabel("Descuento: ");
        this.crearArticulo.add(lDescuento);
        JTextField tDescuento = new JTextField();
        this.crearArticulo.add(tDescuento);

        JLabel lGenero = new JLabel("Genero: ");
        this.crearArticulo.add(lGenero);
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Hombre");
        model.addElement("Mujer");
        //TODO mirar de meter bien los generos
        JComboBox<String> cbGenero = new JComboBox<>(model);
        this.crearArticulo.add(cbGenero);

        JLabel lStock = new JLabel("Stock: ");
        this.crearArticulo.add(lStock);
        JTextField tStock = new JTextField();
        this.crearArticulo.add(tStock);


        JButton bCrear = new JButton("Crear");
        this.crearArticulo.add(bCrear);
        bCrear.addActionListener(e -> {
            tArticulo art = new tArticulo(Integer.parseInt(tId.getText()), tNombre.getText(), tSubcategoria.getText(), Double.parseDouble(tPrecio.getText()));
            saFacade.altaArticulo(art, tFecha.getText(), (String) cbGenero.getSelectedItem(), Integer.parseInt(tDescuento.getText()), Integer.parseInt(tStock.getText()));
        });

        JButton bCancelar = new JButton("Cancelar");
        this.crearArticulo.add(bCancelar);
        bCancelar.addActionListener(e -> {
            crearArticulo.setVisible(false);
            this.setVisible(true);
        });

    }

    private void initAñadirSaldo() {

        añadirSaldo = new JPanel();

        JLabel lId = new JLabel("ID: ");
        this.añadirSaldo.add(lId);
        JTextField tId = new JTextField();
        this.añadirSaldo.add(tId);

        JLabel lSaldo = new JLabel("Saldo: ");
        this.añadirSaldo.add(lSaldo);
        JTextField tSaldo = new JTextField();
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
    }

    private void initCambiarSuscripcion() {

        cambiarSuscripcion = new JPanel();

        JLabel lId = new JLabel("ID: ");
        this.cambiarSuscripcion.add(lId);
        JTextField tId = new JTextField();
        this.cambiarSuscripcion.add(tId);

        JLabel lSuscripcion = new JLabel("Suscripcion: ");
        this.cambiarSuscripcion.add(lSuscripcion);
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        JComboBox<String> cbSuscripcion = new JComboBox<>(model);
        this.cambiarSuscripcion.add(cbSuscripcion);

        JButton bCambiar = new JButton("Cambiar");
        this.cambiarSuscripcion.add(bCambiar);
        bCambiar.addActionListener(e -> {
            saFacade.actualizarSuscrAdmin(Integer.parseInt(tId.getText()), cbSuscripcion.getSelectedIndex());
        });

        JButton bCancelar = new JButton("Cancelar");
        this.cambiarSuscripcion.add(bCancelar);
        bCancelar.addActionListener(e -> {
            cambiarSuscripcion.setVisible(false);
            this.setVisible(true);
        });
    }

    private void initCambiarEstadoPedido() {
        cambiarEstadoPedido = new JPanel();

        JLabel lId = new JLabel("ID: ");
        this.cambiarEstadoPedido.add(lId);

        JTextField tId = new JTextField();
        this.cambiarEstadoPedido.add(tId);

        DefaultComboBoxModel<TOStatusPedido> model = new DefaultComboBoxModel<>(TOStatusPedido.values());
        JComboBox<TOStatusPedido> cbEstado = new JComboBox<>(model);
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
    }
}
