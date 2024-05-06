package presentacion;

import negocio.*;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;

public class GUIPerfil extends MainGUIPanel implements UserObserver, PedidoObserver {

    private SAFacade saFacade;
    private TUsuario tUsuario;
    private GUIWindow window;

    private JTextField _nombre;
    private JTextField _apellidos;
    private JTextField _contrasenya;
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
    private JComboBox<TOStatusPedido> status;
    private JDatePicker fechaInicio;
    private JDatePicker fechaFin;
    private CardLayout cl;
    private JPanel panelMod;
    private Collection<TOPedido> pedidos;

    public GUIPerfil(SAFacade facade, GUIWindow guiWindow) {
        saFacade = facade;
        saFacade.registerObserver(this);
        tUsuario = saFacade.getUsuario();
        window = guiWindow;
        initGUI();
    }


    private void setComboBox() {
        sexModel.removeAllElements();
        sexModel.addElement('M');
        sexModel.addElement('F');
    }

    @Override
    public void update() {
        boolean iniciado = (saFacade.getUsuario()!= null);
        if(iniciado){
            onUserDataChanged(iniciado, saFacade.getUsuario().getId());
        }
    }

    @Override
    public void reset() {
        cl.show(cards, "Panel_ini");
    }

    @Override
    public void onUserDataChanged(boolean isAuth, int idUsuario) {
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
            panelMod.removeAll();
            configurarPanelMod(cards, panelMod);
            updateTable();
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
    }

    @Override
    public void onPedidoCreated(TOPedido toPedido) {
        updateTable();
    }

    @Override
    public void onPedidoUpdated(TOPedido toPedido) {
        updateTable();
    }


    private void initGUI() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(window.getContentPane().getPreferredSize());
        cards = new JPanel(new CardLayout());

        //CREAMOS LOS PANELES
        //PANEL DE MODIFICAR DATOS
        panelMod = new JPanel();
        panelMod.setLayout(new BoxLayout(panelMod, BoxLayout.Y_AXIS));
        panelMod.setVisible(false);
        panelMod.setBorder(BorderFactory.createTitledBorder("Modificar datos"));
        configurarPanelMod(cards, panelMod);

        //PANEL DE VER MIS PEDIDOS
        JPanel panelPedidos = new JPanel();
        panelPedidos.setLayout(new BoxLayout(panelPedidos, BoxLayout.Y_AXIS));
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
        JPanel panelAdmin = new GUIAdmin(saFacade, window);
        panelAdmin.setVisible(false);

        // Añadir paneles al panel de cartas
        cards.add(panelIni, PANELINI);
        cards.add(panelMod, PANELMOD);
        cards.add(panelPedidos, PANELPEDIDOS);
        cards.add(panelSuscr, PANELSUSCR);
        cards.add(panelSaldo, PANELSALDO);
        cards.add(panelAdmin, PANELADMIN);


        //PARA CAMBIAR LOS PANELES CON LOS ACTION LISTENERS
        cl = (CardLayout) (cards.getLayout());


        //PANEL PARA AÑADIR LOS BOTONES
        buttonPanel = new JPanel();

        buttonPanel.setLayout(new GridLayout(1, 0));

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
        JButton ver_pedidos = new JButton("Mis pedidos");
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
            try{
                saFacade.logout();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ha habido un error al cerrar sesion: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            window.goHome();
            revalidate();
            repaint();
        }));

        // Agrega los paneles y el panel de botones al panel principal
        mainPanel.add(cards, BorderLayout.CENTER);

        mainPanel.add(buttonPanel, BorderLayout.PAGE_START);

        setViewportView(mainPanel);
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
        texto_ayuda.getCaret().deinstall(texto_ayuda);
        texto_ayuda.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        modPanel.add(texto_ayuda);

        addJLabel("Nombre", modPanel);
        _nombre = new JTextField();
        _nombre.setText(saFacade.getUsuario() != null ? saFacade.getUsuario().getNombre() : "");
        _nombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        _nombre.setMaximumSize(new Dimension(200, 20));
        _nombre.setToolTipText("Introduzca su nombre");
        modPanel.add(_nombre);

        addJLabel("Apellidos", modPanel);
        _apellidos = new JTextField();
        _apellidos.setText(saFacade.getUsuario() != null ? saFacade.getUsuario().getApellidos() : "");
        _apellidos.setAlignmentX(Component.CENTER_ALIGNMENT);
        _apellidos.setMaximumSize(new Dimension(200, 20));
        _apellidos.setToolTipText("Intruduzca sus apellidos");
        modPanel.add(_apellidos);

        addJLabel("Contraseña", modPanel);
        _contrasenya = new JPasswordField(20);
        _contrasenya.setAlignmentX(Component.CENTER_ALIGNMENT);
        _contrasenya.setMaximumSize(new Dimension(200, 20));
        _contrasenya.setToolTipText("Introduzca una contraseña");
        modPanel.add(_contrasenya);

        addJLabel("Año nacimiento", modPanel);
        _anyoNac = new JTextField();
        _anyoNac.setText(saFacade.getUsuario() != null ? saFacade.getUsuario().getAnyoNacimiento() + "" : "");
        _anyoNac.setAlignmentX(Component.CENTER_ALIGNMENT);
        _anyoNac.setMaximumSize(new Dimension(200, 20));
        _anyoNac.setToolTipText("Introduzca su año de nacimiento");
        modPanel.add(_anyoNac);

        addJLabel("Correo", modPanel);
        _correo = new JTextField();
        _correo.setText(saFacade.getUsuario() != null ? saFacade.getUsuario().getCorreo_e() : "");
        _correo.setAlignmentX(Component.CENTER_ALIGNMENT);
        _correo.setMaximumSize(new Dimension(200, 20));
        _correo.setToolTipText("Introduzca su correo electronico");
        modPanel.add(_correo);

        addJLabel("Pais", modPanel);
        _pais = new JTextField();
        _pais.setText(saFacade.getUsuario() != null ? saFacade.getUsuario().getPais() : "");
        _pais.setAlignmentX(Component.CENTER_ALIGNMENT);
        _pais.setMaximumSize(new Dimension(200, 20));
        _pais.setToolTipText("Intruduzca su pais");
        modPanel.add(_pais);

        addJLabel("Direccion", modPanel);
        _direccion = new JTextField();
        _direccion.setText(saFacade.getUsuario() != null ? saFacade.getUsuario().getDireccion() : "");
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
                JOptionPane.showMessageDialog(this, "Los datos se han modificado correctamente");
            } catch (RuntimeException re) {
                throw new RuntimeException("No se han podido actualizar los datos: ");
            } finally {
                cl.show(cards, "Panel_ini");
            }
        });
        _confirmar.setAlignmentX(Component.CENTER_ALIGNMENT);
        modPanel.add(_confirmar);

        modPanel.add(new JPanel());
    }

    private void configurarPanelPedidos(JPanel panelPedidos) {

        JPanel panelFiltrosTabla = new JPanel();
        panelFiltrosTabla.setLayout(new BorderLayout());

        JPanel filtros = new JPanel();

        filtros.setBorder(BorderFactory.createTitledBorder("Filtros"));

        filtros.setLayout(new BoxLayout(filtros, BoxLayout.X_AXIS));

        JLabel statusLabel = new JLabel("Status:");
        filtros.add(statusLabel);

        filtros.add(Box.createRigidArea(new Dimension(10, 0)));

        DefaultComboBoxModel<TOStatusPedido> statusModel = new DefaultComboBoxModel<>();
        statusModel.addElement(null);
        statusModel.addAll(List.of(TOStatusPedido.values()));
        status = new JComboBox<>(statusModel);
        filtros.add(status);

        filtros.add(Box.createRigidArea(new Dimension(10, 0)));

        JLabel fechaLabel = new JLabel("Fecha inicio:");
        filtros.add(fechaLabel);

        filtros.add(Box.createRigidArea(new Dimension(10, 0)));

        Properties i18nStrings = new Properties();
        i18nStrings.put("text.today", "Hoy");


        fechaInicio = new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel(), i18nStrings), new DateComponentFormatter());
        filtros.add((Component) fechaInicio);

        filtros.add(Box.createRigidArea(new Dimension(10, 0)));

        JLabel fechaFinLabel = new JLabel("Fecha fin:");
        filtros.add(fechaFinLabel);

        filtros.add(Box.createRigidArea(new Dimension(10, 0)));

        fechaFin = new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel(), i18nStrings), new DateComponentFormatter());
        filtros.add((Component) fechaFin);

        panelFiltrosTabla.add(filtros, BorderLayout.PAGE_START);

        status.addActionListener(e -> filterTable());

        fechaInicio.addActionListener(e -> filterTable());

        fechaFin.addActionListener(e -> filterTable());

        pedidosModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };

        pedidosModel.addColumn("ID");
        pedidosModel.addColumn("Fecha");
        pedidosModel.addColumn("Status");
        pedidosModel.addColumn("Precio");

        JTable tablaPedidos = new JTable(pedidosModel);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tablaPedidos.setDefaultRenderer(Object.class, centerRenderer);

        tablaPedidos.getColumn("ID").setCellRenderer(new ButtonRenderer());
        tablaPedidos.getColumn("ID").setCellEditor(new ButtonEditor(new JCheckBox(), v -> {
            int row = tablaPedidos.getSelectedRow();
            int idPedido = (Integer) pedidosModel.getValueAt(row, 0);
            try {
                TOPedido toPedido = saFacade.getAllPedidos().stream().filter(toPedido1 -> toPedido1.getID() == idPedido).findFirst().orElse(null);

                JButton backButton = new JButton("Atras");

                GUIPedido guiPedido = new GUIPedido(saFacade, window, toPedido, backButton);

                backButton.addActionListener(e1 -> Transitions.makeWhiteFadeTransition(guiPedido, panelFiltrosTabla, 1, (from, to) -> {
                    panelPedidos.remove(from);
                    panelPedidos.add(to);
                    revalidate();
                    repaint();
                }));

                Transitions.makeWhiteFadeTransition(panelFiltrosTabla, guiPedido, 1, (from, to) -> {
                    panelPedidos.remove(from);
                    panelPedidos.add(to);
                    revalidate();
                    repaint();
                });

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "No existe el pedido con ID " + idPedido, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }));

        JScrollPane scrollPane = new JScrollPane(tablaPedidos);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de pedidos"));
        panelFiltrosTabla.add(scrollPane, BorderLayout.CENTER);

        panelPedidos.add(panelFiltrosTabla);
    }

    private void filterTable() {
        TOStatusPedido toStatusPedido = (TOStatusPedido) status.getSelectedItem();
        Date fechaInicioDate = (Date) fechaInicio.getModel().getValue();
        Date fechaFinDate = (Date) fechaFin.getModel().getValue();
        var pedidosToShow = pedidos.stream().filter(toPedido -> fechaInicioDate == null || toPedido.getFecha().after(Date.from(fechaInicioDate.toInstant().minus(1, java.time.temporal.ChronoUnit.DAYS))))
                .filter(toPedido -> fechaFinDate == null || toPedido.getFecha().before(fechaFinDate))
                .filter(toPedido -> toStatusPedido == null || toPedido.getStatus().equals(toStatusPedido.toString().toLowerCase()))
                .toList();
        pedidosModel.setRowCount(0);
        pedidosToShow.forEach(toPedido -> pedidosModel.addRow(new Object[]{toPedido.getID(), toPedido.getFecha(), toPedido.getStatus().toUpperCase(), toPedido.getTOAArticulosEnPedido().getArticulosSet().stream().mapToDouble(TOAArticuloEnPedido::getPrecio).sum()}));
    }

    private void updateTable() {
        pedidos = saFacade.getPedidosUsuario();
        filterTable();
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
                //tUsuario.setSaldo(tUsuario.getSaldo());
                if (this.tUsuario != null) {
                    double nuevoSaldo = this.tUsuario.getSaldo();
                    saldo.setText(nuevoSaldo + "");
                }
                JOptionPane.showMessageDialog(this, "Saldo añadido con éxito!");
            } catch (RuntimeException exception) {
                JOptionPane.showMessageDialog(this, "Algo ha pasado...");
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

        DefaultComboBoxModel<Suscripciones> suscr = new DefaultComboBoxModel<>(Suscripciones.values());

        JComboBox<Suscripciones> comboBoxSusc = new JComboBox<>(suscr);
        comboBoxSusc.setMaximumSize(panelSuscr.getMaximumSize());
        comboBoxSusc.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelSuscr.add(comboBoxSusc);

        StringBuilder sb = new StringBuilder();
        for (Suscripciones s : Suscripciones.values()) {
            sb.append(s.name()).append(": ").append(s.getInfo()).append(" Precio: ").append(s.getPrecio()).append("€\n");
        }
        String texto = sb.toString();

        JTextArea info = new JTextArea("INFORMACIÓN:\n" + texto);
        info.setBackground(null);
        info.setEditable(false);
        info.setLineWrap(true);
        info.setWrapStyleWord(true);
        info.getCaret().deinstall(info);
        panelSuscr.add(info);

        JPanel panelVacio = new JPanel();

        CardLayout cl = (CardLayout) (cards.getLayout());

        JButton confirmar = new JButton("Confirmar");
        panelSuscr.add(confirmar);
        confirmar.addActionListener((e -> {
            suscripcion.setText(comboBoxSusc.getSelectedItem() + "");
            try {
                Suscripciones susc = (Suscripciones) comboBoxSusc.getSelectedItem();
                saFacade.actualizarSuscr(susc);
                if (this.saFacade.getUsuario() != null) {
                    double nuevoSaldo = this.saFacade.getUsuario().getSaldo();
                    saFacade.getUsuario().setSaldo(nuevoSaldo);
                    saldo.setText(nuevoSaldo + "");
                }

                JOptionPane.showMessageDialog(this, "Suscripcion actualizada con éxito!");
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                cl.show(cards, "Panel_ini"); //TODO Pensar si ponerlo solo si se ha actualizado correctamente
            }
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


    static class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    static class ButtonEditor extends DefaultCellEditor {

        protected JButton button;
        private Object label;
        private boolean isPushed;

        private final Consumer<Void> push;

        public ButtonEditor(JCheckBox checkBox, Consumer<Void> push) {
            super(checkBox);
            this.push = push;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value == null) ? "" : value;
            button.setText(String.valueOf(label));
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                push.accept(null);
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }

}
