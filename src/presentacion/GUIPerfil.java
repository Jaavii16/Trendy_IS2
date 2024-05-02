package presentacion;

import negocio.*;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;
import java.util.function.Consumer;

public class GUIPerfil extends MainGUIPanel implements AuthObserver {

    private SAFacade saFacade;
    private TUsuario tUsuario;
    private GUIWindow window;

    private JTextField _nombre;
    private JTextField _apellidos;
    private JTextField _contrasenya;
    private JTextField _repContrasenya;
    private JTextField _correo;
    private JTextField _pais;
    private JTextField _anyoNac;
    private JTextField _direccion;
    private JComboBox<Character> _sexo;
    private DefaultComboBoxModel<Character> sexModel = new DefaultComboBoxModel<>();

    private JPanel cards;
    private static final String PANELINI = "Panel_ini";
    private static final String PANELMOD = "Panel_mod";
    private static final String PANELPEDIDOS = "Panel_ped";
    private static final String PANELSUSCR = "Panel_susc";
    private static final String PANELSALDO = "Panel_saldo";
    private static final String PANELADMIN = "Panel_admin";

    private boolean botonAdmin;
    private JButton goToAdmin;
    private JPanel buttonPanel;
    private DefaultTableModel pedidosModel;
    private JPanel mainPanel;
    private JPanel panelIni;
    private JLabel nombre;
    private JLabel suscripcion;
    private JLabel saldo;

    public GUIPerfil(SAFacade facade, GUIWindow guiWindow) {
        saFacade = facade;
        saFacade.registerObserver(this);
        window = guiWindow;
        initGUI();
    }


    private void setComboBox() {
        sexModel.addElement('M');
        sexModel.addElement('F');
    }

    @Override
    public void update() {
        //TODO
    }

    @Override
    public void reset() {
        //TODO
    }

    @Override
    public void onAuthChanged(boolean isAuth, int idUsuario) { //TODO Poner TUsuario en parametros
        if (isAuth && saFacade.getUsuario().getAdmin()) {
            if (!botonAdmin) {
                buttonPanel.add(goToAdmin);
                botonAdmin = true;
            }
        } else if (isAuth) {
            if (botonAdmin) {
                buttonPanel.remove(goToAdmin);
                botonAdmin = false;
            }
        }
        TUsuario tUsu = saFacade.getUsuario();
        if (tUsu != null) {
//            if (panelIni.getComponentCount() > 1) {
//                panelIni.remove(nombre);
//                panelIni.remove(suscripcion);
//                panelIni.remove(saldo);
//            }
            panelIni.removeAll();
            nombre = new JLabel(tUsu.getNombre() + " " + (tUsu.getApellidos() != null ? tUsu.getApellidos() : " "));
            nombre.setAlignmentX(Component.CENTER_ALIGNMENT);
            suscripcion = new JLabel("Suscripcion actual: " + tUsu.getSuscripcion());
            suscripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
            saldo = new JLabel("Saldo: " + tUsu.getSaldo());
            saldo.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelIni.add(nombre);
            panelIni.add(suscripcion);
            panelIni.add(saldo);
            panelIni.revalidate();
            panelIni.repaint();
        }
        //TODO mirar lo del panel inicial del label con el nombre
    }

    class VentanaMensaje extends JFrame {
        public VentanaMensaje(String mensaje) {
            JLabel etiqueta = new JLabel(mensaje);
            etiqueta.setAlignmentX(Component.CENTER_ALIGNMENT);
            getContentPane().add(etiqueta);
            setSize(300, 100);
            setLocation(GUIPerfil.this.getWidth() / 2 + GUIPerfil.this.getLocation().x, GUIPerfil.this.getHeight() / 2 + GUIPerfil.this.getLocation().y);
            setVisible(true);
        }
    }

    private void initGUI() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(window.getSize());
        cards = new JPanel(new CardLayout());
        setViewportView(mainPanel);
        //CREAMOS LOS PANELES
        //PANEL DE MODIFICAR DATOS
        JPanel panelMod = new JPanel();
        panelMod.setLayout(new BoxLayout(panelMod, BoxLayout.Y_AXIS));
        panelMod.setVisible(false);
        panelMod.setBorder(BorderFactory.createTitledBorder("Modificar datos"));
        configurarPanelMod(cards, panelMod);

        //PANEL DE VER MIS PEDIDOS
        JPanel panelPedidos = new JPanel();
        panelPedidos.setLayout(new BorderLayout());
        panelPedidos.setBorder(new TitledBorder("Pedidos"));
        panelPedidos.setVisible(false);
        panelPedidos.setBorder(BorderFactory.createTitledBorder("Pedidos"));
        configurarPanelPedidos(panelPedidos);

        //PANEL DE ACTUALIZAR SUSCRIPCION
        JPanel panelSuscr = new JPanel();
        panelSuscr.setLayout(new BoxLayout(panelSuscr, BoxLayout.Y_AXIS));
        panelSuscr.setVisible(false);
        panelSuscr.setBorder(BorderFactory.createTitledBorder("Actualizar suscripción"));
        configurarPanelSuscr(cards, panelSuscr);

        //PANEL DE AÑADIR SALDO
        JPanel panelSaldo = new JPanel();
        panelSaldo.setLayout(new BoxLayout(panelSaldo, BoxLayout.Y_AXIS));
        panelSaldo.setVisible(false);
        panelSaldo.setBorder(BorderFactory.createTitledBorder("Añadir saldo"));
        configurarPanelSaldo(cards, panelSaldo);

        //PANEL QUE SE VA A MOSTRAR AL PRINCIPIO
        panelIni = new JPanel();
        panelIni.setLayout(new BoxLayout(panelIni, BoxLayout.Y_AXIS));
        panelIni.setBorder(BorderFactory.createTitledBorder("Inicio"));


        //PANEL DE ADMIN
        JPanel panelAdmin = new GUIAdmin(saFacade);
        panelAdmin.setVisible(false);

        // Añadir paneles al panel de cartas
        cards.add(panelIni, PANELINI);
        cards.add(panelMod, PANELMOD);
        cards.add(panelPedidos, PANELPEDIDOS);
        cards.add(panelSuscr, PANELSUSCR);
        cards.add(panelSaldo, PANELSALDO);
        cards.add(panelAdmin, PANELADMIN);


        //PARA CAMBIAR LOS PANELES CON LOS ACTION LISTENERS
        CardLayout cl = (CardLayout) (cards.getLayout());


        //PANEL PARA AÑADIR LOS BOTONES
        buttonPanel = new JPanel();

        //BOTON PARA VOLVER AL PANEL INICIAL
        JButton ini = new JButton("Inicio");
        buttonPanel.add(ini);
        ini.addActionListener((e -> {
            cl.show(cards, "Panel_ini");
            revalidate();
            repaint();
        }));


        //BOTON PARA MODIFICAR DATOS
        JButton mod_datos = new JButton("Modificar datos");
        buttonPanel.add(mod_datos);
        mod_datos.addActionListener((e -> {
            cl.show(cards, "Panel_mod");
            revalidate();
            repaint();
        }));

        //BOTON PARA VER MIS PEDIDOS
        JButton ver_pedidos = new JButton("Ver mis pedidos");
        buttonPanel.add(ver_pedidos);
        ver_pedidos.addActionListener((e -> {
            cl.show(cards, "Panel_ped");
            revalidate();
            repaint();
        }));

        //BOTON PARA ACTUALIZAR LA SUSCRIPCION
        JButton act_suscripcion = new JButton("Actualizar suscripcion");
        buttonPanel.add(act_suscripcion);
        act_suscripcion.addActionListener((e -> {
            cl.show(cards, "Panel_susc");
            revalidate();
            repaint();
        }));

        //BOTON PARA AÑADIR SALDO
        JButton add_saldo = new JButton("Añadir saldo");
        buttonPanel.add(add_saldo);
        add_saldo.addActionListener((e -> {
            cl.show(cards, "Panel_saldo");
            revalidate();
            repaint();
        }));

        //BOTON PARA IR AL PANEL DE ADMIN
        goToAdmin = new JButton("Admin");
        goToAdmin.addActionListener((e -> {
            cl.show(cards, "Panel_admin");
            revalidate();
            repaint();
        }));

        //BOTON DE LOG OUT
        JButton log_out = new JButton("Logout");
        buttonPanel.add(log_out);
        log_out.addActionListener((e -> {
            saFacade.logout();
            window.goHome();
            revalidate();
            repaint();
        }));

        // Agrega los paneles y el panel de botones al panel principal
        mainPanel.add(cards, BorderLayout.CENTER);

        mainPanel.add(buttonPanel, BorderLayout.PAGE_START);
    }


    private void addJLabel(String text, Container container) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(label);
    }


    private void configurarPanelMod(JPanel cards, JPanel modPanel) {
        JTextArea texto_ayuda = new JTextArea("Complete todos los campos aunque no los quiera cambiar");
        texto_ayuda.setBackground(modPanel.getBackground());
        texto_ayuda.setLineWrap(true);
        texto_ayuda.setWrapStyleWord(true);
        texto_ayuda.setEditable(false);

        addJLabel("Nombre", modPanel);
        _nombre = new JTextField();
        _nombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        _nombre.setMaximumSize(new Dimension(200, 20));
        _nombre.setToolTipText("Introduzca su nombre");
        modPanel.add(_nombre);

        addJLabel("Apellidos", modPanel);
        _apellidos = new JTextField();
        _apellidos.setAlignmentX(Component.CENTER_ALIGNMENT);
        _apellidos.setMaximumSize(new Dimension(200, 20));
        _apellidos.setToolTipText("Intruduzca sus apellidos");
        modPanel.add(_apellidos);

        addJLabel("Contraseña", modPanel);
        _contrasenya = new JTextField();
        _contrasenya.setAlignmentX(Component.CENTER_ALIGNMENT);
        _contrasenya.setMaximumSize(new Dimension(200, 20));
        _contrasenya.setToolTipText("Introduzca una contraseña");
        modPanel.add(_contrasenya);

        addJLabel("Correo", modPanel);
        _correo = new JTextField();
        _correo.setAlignmentX(Component.CENTER_ALIGNMENT);
        _correo.setMaximumSize(new Dimension(200, 20));
        _correo.setToolTipText("Introduzca su correo electronico");
        modPanel.add(_correo);

        addJLabel("Pais", modPanel);
        _pais = new JTextField();
        _pais.setAlignmentX(Component.CENTER_ALIGNMENT);
        _pais.setMaximumSize(new Dimension(200, 20));
        _pais.setToolTipText("Intruduzca su pais");
        modPanel.add(_pais);

        addJLabel("Direccion", modPanel);
        _direccion = new JTextField();
        _direccion.setAlignmentX(Component.CENTER_ALIGNMENT);
        _direccion.setMaximumSize(new Dimension(200, 20));
        _direccion.setToolTipText("Introduzca su direccion");
        modPanel.add(_direccion);

        addJLabel("Sexo", modPanel);
        setComboBox();
        _sexo = new JComboBox<>(sexModel);
        _sexo.setAlignmentX(Component.CENTER_ALIGNMENT);
        _sexo.setMaximumSize(new Dimension(200, 20));
        _sexo.setToolTipText("Elija su sexo");
        modPanel.add(_sexo);


        CardLayout cl = (CardLayout) (cards.getLayout());
        JButton _cancel = new JButton("Cancelar");
        _cancel.addActionListener((e) -> {
            cl.show(cards, "Panel_ini");
        });
        _cancel.setAlignmentX(Component.CENTER_ALIGNMENT);
        modPanel.add(_cancel);

        JButton _confirmar = new JButton("Confirmar");
        _confirmar.addActionListener((e) -> {
            TUsuario usuario = crearUsuario();
            try {
                saFacade.update(usuario);
                VentanaMensaje ventana = new VentanaMensaje("Los datos se han modificado correctamente");
            } catch (RuntimeException re) {
                throw new RuntimeException("No se han podido actualizar los datos: ");
            } finally {
                cl.show(cards, "Panel_ini");
            }
        });
        _confirmar.setAlignmentX(Component.CENTER_ALIGNMENT);
        modPanel.add(_confirmar);
    }

    private void configurarPanelPedidos(JPanel panelPedidos) {


        JPanel filtros = new JPanel(); //TODO Hacer PedidosObserver

        filtros.setLayout(new GridLayout());

        JLabel filtro = new JLabel("Filtros: ");
        filtros.add(filtro);

        JLabel statusLabel = new JLabel("Status");
        filtros.add(statusLabel);

        JComboBox<TOStatusPedido> status = new JComboBox<>(new DefaultComboBoxModel<>(TOStatusPedido.values()));
        filtros.add(status);

        JLabel fechaLabel = new JLabel("Fecha inicio");
        filtros.add(fechaLabel);

        JDatePicker fechaInicio = new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel(), new Properties()), new DateComponentFormatter());
        filtros.add((Component) fechaInicio);

        JLabel fechaFinLabel = new JLabel("Fecha fin");
        filtros.add(fechaFinLabel);

        JDatePicker fechaFin = new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel(), new Properties()), new DateComponentFormatter());
        filtros.add((Component) fechaFin);

        panelPedidos.add(filtros, BorderLayout.PAGE_START);

        Consumer<Collection<TOPedido>> updateTable = (articulos) -> {
            pedidosModel.setRowCount(0);
            articulos.forEach(toPedido -> pedidosModel.addRow(new Object[]{toPedido.getID(), toPedido.getFecha(), toPedido.getStatus(), 0})); //TODO Poner precio
        };

        status.addActionListener(e -> {
            TOStatusPedido toStatusPedido = (TOStatusPedido) status.getSelectedItem();
            Date fechaInicioDate = (Date) fechaInicio.getModel().getValue();
            Date fechaFinDate = (Date) fechaFin.getModel().getValue();
            Collection<TOPedido> pedidos = saFacade.getAllPedidos();
            pedidos = pedidos.stream().filter(toPedido -> fechaInicioDate == null || toPedido.getFecha().after(fechaInicioDate))
                    .filter(toPedido -> fechaFinDate == null || toPedido.getFecha().before(fechaFinDate))
                    .filter(toPedido -> toStatusPedido == null || toPedido.getStatus().equals(toStatusPedido.toString()))
                    .toList();
            updateTable.accept(pedidos);
        });


        pedidosModel = new DefaultTableModel();

        pedidosModel.addColumn("ID");
        pedidosModel.addColumn("Fecha");
        pedidosModel.addColumn("Status");
        pedidosModel.addColumn("Precio");

        JTable tablaPedidos = new JTable(pedidosModel);

        tablaPedidos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tablaPedidos.getSelectedRow();
                int idPedido = (int) tablaPedidos.getValueAt(row, 0);
                TOPedido toPedido = saFacade.getAllPedidos().stream().filter(toPedido1 -> toPedido1.getID() == idPedido).findFirst().orElse(null);

                JButton backButton = new JButton("Atras");

                GUIPedido guiPedido = new GUIPedido(toPedido, backButton);

                backButton.addActionListener(e1 -> Transitions.makeWhiteFadeTransition(guiPedido, panelPedidos, 1, (from, to) -> {
                    mainPanel.remove(from);
                    mainPanel.add(to, BorderLayout.CENTER);
                }));

                Transitions.makeWhiteFadeTransition(panelPedidos, guiPedido, 1, (from, to) -> {
                    mainPanel.remove(from);
                    mainPanel.add(to, BorderLayout.CENTER);
                });
            }
        });


        JScrollPane scrollPane = new JScrollPane(tablaPedidos);
        panelPedidos.add(scrollPane, BorderLayout.CENTER);
        /*
        JButton atras = new JButton("Atras");
        panelPedidos.add(atras);
        atras.addActionListener((e -> {
            cl.show(cards, "Panel_ini");
        }));
        */
    }

    private void configurarPanelSaldo(JPanel cards, JPanel panelSaldo) {
        JLabel cant = new JLabel("Introduzca la cantidad que desea añadir");
        cant.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelSaldo.add(cant);
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 200, 5);
        JSpinner sumarASaldo = new JSpinner(spinnerModel);
        sumarASaldo.setMaximumSize(panelSaldo.getMaximumSize());
        sumarASaldo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelSaldo.add(sumarASaldo);

        CardLayout cl = (CardLayout) (cards.getLayout());

        JButton confirmar = new JButton("Confirmar");
        confirmar.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelSaldo.add(confirmar);
        confirmar.addActionListener((e -> {
            int cantidad = (int) sumarASaldo.getValue();
            try {
                saFacade.actualizarSaldo(cantidad);
                this.tUsuario = saFacade.getUsuario();
                if (this.tUsuario != null) {
                    double nuevoSaldo = this.tUsuario.getSaldo() + cantidad;
                    saldo.setText(nuevoSaldo + "");
                }
                VentanaMensaje ventanaMensaje = new VentanaMensaje("Saldo añadido con éxito!");
            } catch (RuntimeException exception) {
                VentanaMensaje ventanaMensaje = new VentanaMensaje("Algo ha fallado...");
            } finally {
                cl.show(cards, "Panel_ini");
            }
        }));

        JButton atras = new JButton("Atras");
        atras.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelSaldo.add(atras);
        atras.addActionListener((e -> {
            cl.show(cards, "Panel_ini");
        }));
    }

    private void configurarPanelSuscr(JPanel cards, JPanel panelSuscr) {
        JLabel mensaje = new JLabel("Elija la suscripcion que desea:");
        mensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelSuscr.add(mensaje);

        DefaultComboBoxModel<String> suscr = new DefaultComboBoxModel<>();
        for (Suscripciones v : Suscripciones.values()) {
            suscr.addElement(v.name());
        }
        JComboBox<String> comboBoxSusc = new JComboBox<>(suscr);
        comboBoxSusc.setMaximumSize(panelSuscr.getMaximumSize());
        comboBoxSusc.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelSuscr.add(comboBoxSusc);

        CardLayout cl = (CardLayout) (cards.getLayout());

        JButton confirmar = new JButton("Confirmar");
        panelSuscr.add(confirmar);
        confirmar.addActionListener((e -> {
            suscripcion.setText(comboBoxSusc.getSelectedItem() + "");
            saFacade.actualizarSuscr(comboBoxSusc.getSelectedIndex());
            VentanaMensaje ventanaMensaje = new VentanaMensaje("Suscripcion actualizada con éxito!");
            cl.show(cards, "Panel_ini");
        }));
    }


    private TUsuario crearUsuario() {
        String nombre, apellidos, correo, contrasenya, pais, dir;
        char sexo;
        int anyo;
        nombre = _nombre.getText();
        apellidos = _apellidos.getText();
        this.nombre.setText(nombre + " " + apellidos);
        correo = _correo.getText();
        contrasenya = _contrasenya.getText();
        anyo = Integer.parseInt(_anyoNac.getText());
        pais = _pais.getText();
        dir = _direccion.getText();
        sexo = (char) _sexo.getSelectedItem();
        return new TUsuario(nombre, apellidos, correo, contrasenya, anyo, sexo, pais, dir, false);
    }


}
