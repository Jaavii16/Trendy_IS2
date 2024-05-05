package presentacion;

import negocio.SAFacade;
import negocio.TOStatusPedido;
import negocio.TUsuario;
import negocio.tArticulo;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Comparator;
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

        crearArticulo = new JPanel();
        añadirSaldo = new JPanel();
        cambiarSuscripcion = new JPanel();
        cambiarEstadoPedido = new JPanel();
        cambiarArticulo = new JPanel();

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

        this.add(paneles, BorderLayout.CENTER);

        JPanel botones = new JPanel();

        bCrearArticulo = new JButton("Crear Articulo");
        bCrearArticulo.setAlignmentX(CENTER_ALIGNMENT);
        bCrearArticulo.addActionListener(e -> {
            initCrearArticulo();
            cambiarArticulo.setVisible(false);
            añadirSaldo.setVisible(false);
            cambiarSuscripcion.setVisible(false);
            cambiarEstadoPedido.setVisible(false);
            this.crearArticulo.setVisible(true);
        });
        botones.add(bCrearArticulo);

        bAñadirSaldo = new JButton("Añadir Saldo");
        bAñadirSaldo.setAlignmentX(CENTER_ALIGNMENT);
        bAñadirSaldo.addActionListener(e -> {
            initAñadirSaldo();
            cambiarArticulo.setVisible(false);
            crearArticulo.setVisible(false);
            cambiarSuscripcion.setVisible(false);
            cambiarEstadoPedido.setVisible(false);
            this.añadirSaldo.setVisible(true);
        });
        botones.add(bAñadirSaldo);

        bCambiarSuscripcion = new JButton("Cambiar Suscripcion");
        bCambiarSuscripcion.setAlignmentX(CENTER_ALIGNMENT);
        bCambiarSuscripcion.addActionListener(e -> {
            initCambiarSuscripcion();
            cambiarArticulo.setVisible(false);
            crearArticulo.setVisible(false);
            añadirSaldo.setVisible(false);
            cambiarEstadoPedido.setVisible(false);
            this.cambiarSuscripcion.setVisible(true);
        });
        botones.add(bCambiarSuscripcion);

        bCambiarEstadoPedido = new JButton("Cambiar Estado Pedido");
        bCambiarEstadoPedido.setAlignmentX(CENTER_ALIGNMENT);
        bCambiarEstadoPedido.addActionListener(e -> {
            initCambiarEstadoPedido();
            cambiarArticulo.setVisible(false);
            crearArticulo.setVisible(false);
            añadirSaldo.setVisible(false);
            cambiarSuscripcion.setVisible(false);
            this.cambiarEstadoPedido.setVisible(true);
        });
        botones.add(bCambiarEstadoPedido);


        bcambiarArticulo = new JButton("Cambiar Articulo");
        bcambiarArticulo.setAlignmentX(CENTER_ALIGNMENT);
        bcambiarArticulo.addActionListener(e -> {
            initCambiarArticulo();
            crearArticulo.setVisible(false);
            añadirSaldo.setVisible(false);
            cambiarSuscripcion.setVisible(false);
            cambiarEstadoPedido.setVisible(false);
            this.cambiarArticulo.setVisible(true);
        });
        botones.add(bcambiarArticulo);

        add(botones, BorderLayout.NORTH);
    }

    private void initCambiarArticulo() {
        cambiarArticulo.removeAll();

        cambiarArticulo.setLayout(new BoxLayout(cambiarArticulo, BoxLayout.Y_AXIS));
        cambiarArticulo.setBorder(new TitledBorder("Cambiar Articulo"));
        cambiarArticulo.setVisible(false);

        JLabel lId = new JLabel("ID: ");
        this.cambiarArticulo.add(lId);
        JTextField tId = new JTextField();
        tId.setMaximumSize(new Dimension(200, 20));
        tId.setAlignmentX(CENTER_ALIGNMENT);
        this.cambiarArticulo.add(tId);

        JLabel lNombre = new JLabel("Nombre: ");
        this.cambiarArticulo.add(lNombre);
        JTextField tNombre = new JTextField();
        tNombre.setMaximumSize(new Dimension(200, 20));
        tNombre.setAlignmentX(CENTER_ALIGNMENT);
        this.cambiarArticulo.add(tNombre);

        JLabel lSubcategoria = new JLabel("Subcategoria: ");
        this.cambiarArticulo.add(lSubcategoria);
        JTextField tSubcategoria = new JTextField();
        tSubcategoria.setAlignmentX(CENTER_ALIGNMENT);
        tSubcategoria.setMaximumSize(new Dimension(200, 20));
        this.cambiarArticulo.add(tSubcategoria);

        JLabel lPrecio = new JLabel("Precio: ");
        this.cambiarArticulo.add(lPrecio);
        JTextField tPrecio = new JTextField();
        tPrecio.setAlignmentX(CENTER_ALIGNMENT);
        tPrecio.setMaximumSize(new Dimension(200, 20));
        this.cambiarArticulo.add(tPrecio);

        JButton bCambiar = new JButton("Cambiar");
        bCambiar.setAlignmentX(CENTER_ALIGNMENT);
        this.cambiarArticulo.add(bCambiar);

        bCambiar.addActionListener(e -> {
            tArticulo art = new tArticulo(Integer.parseInt(tId.getText()), tNombre.getText(), tSubcategoria.getText(), Double.parseDouble(tPrecio.getText()));
            try{
                saFacade.modificarArticulo(art);
            }catch(Exception exception){

            }
            this.cambiarArticulo.setVisible(false);
            this.setVisible(true);
        });

        JButton bCancelar = new JButton("Cancelar");
        bCancelar.setAlignmentX(CENTER_ALIGNMENT);
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

        crearArticulo.removeAll();

        crearArticulo.setLayout(new GridLayout(0, 2));
        crearArticulo.setBorder(new TitledBorder("Crear Articulo"));
        crearArticulo.setVisible(false);

        JPanel pId = new JPanel();
        pId.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel lId = new JLabel("ID: ");
        pId.add(lId);
        this.crearArticulo.add(pId);
        JTextField tId = new JTextField();
        tId.setMaximumSize(new Dimension(200, 20));
        this.crearArticulo.add(tId);

        JPanel pNombre = new JPanel();
        pNombre.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel lNombre = new JLabel("Nombre: ");
        pNombre.add(lNombre);
        this.crearArticulo.add(pNombre);
        JTextField tNombre = new JTextField();
        tNombre.setMaximumSize(new Dimension(200, 20));
        this.crearArticulo.add(tNombre);

        JPanel pSubcategoria = new JPanel();
        pSubcategoria.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel lSubcategoria = new JLabel("Subcategoria: ");
        pSubcategoria.add(lSubcategoria);
        this.crearArticulo.add(pSubcategoria);
        JTextField tSubcategoria = new JTextField();
        tSubcategoria.setMaximumSize(new Dimension(200, 20));
        this.crearArticulo.add(tSubcategoria);

        JPanel pPrecio = new JPanel();
        pPrecio.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel lPrecio = new JLabel("Precio: ");
        pPrecio.add(lPrecio);
        this.crearArticulo.add(pPrecio);
        JTextField tPrecio = new JTextField();
        tPrecio.setMaximumSize(new Dimension(200, 20));
        this.crearArticulo.add(tPrecio);


        //resto
        JPanel pFecha = new JPanel();
        pFecha.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel lFecha = new JLabel("Fecha: ");
        pFecha.add(lFecha);
        this.crearArticulo.add(pFecha);
        JTextField tFecha = new JTextField();
        tFecha.setMaximumSize(new Dimension(200, 20));
        this.crearArticulo.add(tFecha);

        JPanel pDescuento = new JPanel();
        pDescuento.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel lDescuento = new JLabel("Descuento: ");
        pDescuento.add(lDescuento);
        this.crearArticulo.add(pDescuento);
        JTextField tDescuento = new JTextField();
        tDescuento.setMaximumSize(new Dimension(200, 20));
        this.crearArticulo.add(tDescuento);

        JPanel pGenero = new JPanel();
        pGenero.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel lGenero = new JLabel("Genero: ");
        pGenero.add(lGenero);
        this.crearArticulo.add(pGenero);
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Hombre");
        model.addElement("Mujer");
        JComboBox<String> cbGenero = new JComboBox<>(model);
        cbGenero.setMaximumSize(new Dimension(200, 20));
        this.crearArticulo.add(cbGenero);

        JPanel pStock = new JPanel();
        pStock.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel lStock = new JLabel("Stock: ");
        pStock.add(lStock);
        this.crearArticulo.add(pStock);
        JTextField tStock = new JTextField();
        tStock.setMaximumSize(new Dimension(200, 20));
        this.crearArticulo.add(tStock);

        crearArticulo.add(new JPanel());
        crearArticulo.add(new JPanel());

        JButton bCancelar = new JButton("Cancelar");
        this.crearArticulo.add(bCancelar);
        bCancelar.addActionListener(e -> {
            crearArticulo.setVisible(false);
            this.setVisible(true);
        });

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


    }

    private void initAñadirSaldo() {

        añadirSaldo.removeAll();
        
        añadirSaldo.setBorder(new TitledBorder("Añadir Saldo"));
        añadirSaldo.setVisible(false);

        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(0, 2));

        añadirSaldo.add(grid);
        añadirSaldo.add(new JPanel());

        JPanel pId = new JPanel();
        pId.setLayout(new BoxLayout(pId, BoxLayout.Y_AXIS));
        JLabel lId = new JLabel("ID: ");
        pId.add(lId);
        grid.add(pId);

        JComboBox<Integer> tId = new JComboBox<>(saFacade.readAll().stream().mapToInt(TUsuario::getId).boxed().sorted(Comparator.reverseOrder()).toArray(Integer[]::new));
        tId.setMaximumSize(new Dimension(200, 20));
        grid.add(tId);

        JPanel pSaldo = new JPanel();
        pSaldo.setLayout(new BoxLayout(pSaldo, BoxLayout.Y_AXIS));
        JLabel lSaldo = new JLabel("Saldo: ");
        pSaldo.add(lSaldo);
        grid.add(pSaldo);

        JSpinner tSaldo = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1000.0, 5.0));
        tSaldo.setMaximumSize(new Dimension(200, 20));
        grid.add(tSaldo);

        JButton bCancelar = new JButton("Cancelar");
        grid.add(bCancelar);
        bCancelar.addActionListener(e -> {
            añadirSaldo.setVisible(false);
            this.setVisible(true);
        });

        JButton bAñadir = new JButton("Añadir");
        grid.add(bAñadir);
        bAñadir.addActionListener(e -> {
            saFacade.actualizarSaldoAdmin((Double) tSaldo.getModel().getValue(), (Integer) tId.getSelectedItem());
        });

        JPanel wrapper = new JPanel();
        añadirSaldo.add(wrapper);

    }

    private void initCambiarSuscripcion() {

        cambiarSuscripcion.removeAll();

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
        cambiarEstadoPedido.removeAll();

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
