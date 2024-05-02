package presentacion;

import negocio.SAUsuarioImp;
import negocio.Suscripciones;
import negocio.TUsuario;
import utils.ViewUtils;
import negocio.SAFacade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class GUIPerfil extends MainGUIPanel {

    private SAFacade saFacade;
    private TUsuario tUsuario;

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

    public GUIPerfil(SAFacade facade){
        saFacade = facade;
        initGUI();
    }


    private void setComboBox(){
        sexModel.addElement('M');
        sexModel.addElement('F');
    }
    @Override
    public void update() {

    }

    @Override
    public void reset() {

    }

    class VentanaMensaje extends JFrame {
        public VentanaMensaje(String mensaje) {
            JLabel etiqueta = new JLabel(mensaje);
            getContentPane().add(etiqueta);
            setSize(200, 100);
            setVisible(true);
        }
    }

    private void initGUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        cards = new JPanel(new CardLayout());

        //CREAMOS LOS PANELES
        //PANEL DE MODIFICAR DATOS
        JPanel panelMod = new JPanel();
        panelMod.setLayout(new BoxLayout(panelMod, BoxLayout.Y_AXIS));
        panelMod.setVisible(false);
        configurarPanelMod(cards, panelMod);

        //PANEL DE VER MIS PEDIDOS
        JPanel panelPedidos = new JPanel();
        panelPedidos.setLayout(new BoxLayout(panelPedidos, BoxLayout.Y_AXIS));
        panelPedidos.setVisible(false);
        configurarPanelPedidos(panelPedidos);

        //PANEL DE ACTUALIZAR SUSCRIPCION
        JPanel panelSuscr = new JPanel();
        panelSuscr.setLayout(new BoxLayout(panelSuscr, BoxLayout.Y_AXIS));
        panelSuscr.setVisible(false);
        configurarPanelSuscr(cards, panelSuscr);

        //PANEL DE AÑADIR SALDO
        JPanel panelSaldo = new JPanel();
        panelSaldo.setLayout(new BoxLayout(panelSaldo, BoxLayout.Y_AXIS));
        panelSaldo.setVisible(false);
        configurarPanelSaldo(cards, panelSaldo);

        //PANEL QUE SE VA A MOSTRAR AL PRINCIPIO
        JPanel panelIni = new JPanel();
        panelIni.setLayout(new BoxLayout(panelIni, BoxLayout.Y_AXIS));
        JLabel nombre = new JLabel(saFacade.getUsuario());
        panelIni.add(nombre);


        // Añadir paneles al panel de cartas
        cards.add(panelIni, PANELINI);
        cards.add(panelMod, PANELMOD);
        cards.add(panelPedidos, PANELPEDIDOS);
        cards.add(panelSuscr, PANELSUSCR);
        cards.add(panelSaldo, PANELSALDO);


        //PARA CAMBIAR LOS PANELES CON LOS ACTION LISTENERS
        CardLayout cl = (CardLayout)(cards.getLayout());

        //PANEL PARA AÑADIR LOS BOTONES
        JPanel buttonPanel = new JPanel();

        //BOTON PARA VOLVER AL PANEL INICIAL
        JButton ini = new JButton("Inicio");
        buttonPanel.add(ini);
        ini.addActionListener((e -> cl.show(cards,"Panel_ini")));


        //BOTON PARA MODIFICAR DATOS
        JButton mod_datos = new JButton("Modificar datos");
        buttonPanel.add(mod_datos);
        mod_datos.addActionListener((e -> cl.show(cards, "Panel_mod")));

        //BOTON PARA VER MIS PEDIDOS
        JButton ver_pedidos = new JButton("Ver mis pedidos");
        buttonPanel.add(ver_pedidos);
        ver_pedidos.addActionListener((e -> cl.show(cards, "Panel_ped")));

        //BOTON PARA ACTUALIZAR LA SUSCRIPCION
        JButton act_suscripcion = new JButton("Actualizar suscripcion");
        buttonPanel.add(act_suscripcion);
        act_suscripcion.addActionListener((e ->cl.show(cards, "Panel_susc")));

        //BOTON PARA AÑADIR SALDO
        JButton add_saldo = new JButton("Añadir saldo");
        buttonPanel.add(add_saldo);
        add_saldo.addActionListener((e -> cl.show(cards, "Panel_saldo")));


        // Agrega los paneles y el panel de botones al panel principal
        mainPanel.add(cards, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.PAGE_START);
    }


    private void addJLabel(String text, Container container){
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(label);
    }


    private void configurarPanelMod(JPanel cards ,JPanel modPanel){
        JTextArea texto_ayuda = new JTextArea("Complete todos los campos aunque no los quiera cambiar");
        texto_ayuda.setBackground(modPanel.getBackground());
        texto_ayuda.setLineWrap(true);
        texto_ayuda.setWrapStyleWord(true);
        texto_ayuda.setEditable(false);

        addJLabel("Nombre", modPanel);
        _nombre = new JTextField();
        _nombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        _nombre.setToolTipText("Introduzca su nombre");
        modPanel.add(_nombre);

        addJLabel("Apellidos", modPanel);
        _apellidos = new JTextField();
        _apellidos.setAlignmentX(Component.CENTER_ALIGNMENT);
        _apellidos.setToolTipText("Intruduzca sus apellidos");
        modPanel.add(_apellidos);

        addJLabel("Contraseña", modPanel);
        _contrasenya = new JTextField();
        _contrasenya.setAlignmentX(Component.CENTER_ALIGNMENT);
        _contrasenya.setToolTipText("Introduzca una contraseña");
        modPanel.add(_contrasenya);

        addJLabel("Correo", modPanel);
        _correo = new JTextField();
        _correo.setAlignmentX(Component.CENTER_ALIGNMENT);
        _correo.setToolTipText("Introduzca su correo electronico");
        modPanel.add(_correo);

        addJLabel("Pais", modPanel);
        _pais = new JTextField();
        _pais.setAlignmentX(Component.CENTER_ALIGNMENT);
        _pais.setToolTipText("Intruduzca su pais");
        modPanel.add(_pais);

        addJLabel("Direccion", modPanel);
        _direccion = new JTextField();
        _direccion.setAlignmentX(Component.CENTER_ALIGNMENT);
        _direccion.setToolTipText("Introduzca su direccion");
        modPanel.add(_direccion);

        addJLabel("Sexo", modPanel);
        setComboBox();
        _sexo = new JComboBox<>(sexModel);
        _sexo.setAlignmentX(Component.CENTER_ALIGNMENT);
        _sexo.setToolTipText("Elija su sexo");
        modPanel.add(_sexo);


        CardLayout cl = (CardLayout)(cards.getLayout());
        JButton _cancel = new JButton("Cancelar");
        _cancel.addActionListener((e) -> {
            cl.show(cards, "Panel_ini");
        });
        _cancel.setAlignmentX(Component.CENTER_ALIGNMENT);
        modPanel.add(_cancel);

        JButton _confirmar = new JButton("Confirmar");
        _confirmar.addActionListener((e) -> {
            TUsuario usuario = crearUsuario();
            try{
                saFacade.update(usuario);
                VentanaMensaje ventana = new VentanaMensaje("Los datos se han modificado correctamente");
            }catch(RuntimeException re){
                throw new RuntimeException("No se han podido actualizar los datos: ");
            }finally{
                cl.show(cards, "Panel_ini");
            }
        });
        _confirmar.setAlignmentX(Component.CENTER_ALIGNMENT);
        modPanel.add(_confirmar);
    }

    private void configurarPanelPedidos( JPanel panelPedidos) {
        //TODO creo que solo tengo que hacer una instancia de la tabla de pedidos que tienen
        //que crear javi y ruben
        /*JButton atras = new JButton("Atras");
        panelPedidos.add(atras);
        atras.addActionListener((e -> {
            cl.show(cards, "Panel_ini");
        }));*/
    }

    private void configurarPanelSaldo(JPanel cards, JPanel panelSaldo) {
        JLabel cant = new JLabel("Introduzca la cantidad que desea añadir");
        cant.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelSaldo.add(cant);
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 200, 5);
        JSpinner sumarASaldo = new JSpinner(spinnerModel);
        sumarASaldo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelSaldo.add(sumarASaldo);

        CardLayout cl = (CardLayout)(cards.getLayout());

        JButton confirmar = new JButton("Confirmar");
        confirmar.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelSaldo.add(confirmar);
        confirmar.addActionListener((e -> {
            int cantidad = (int) sumarASaldo.getValue();
            saFacade.actualizarSaldo(cantidad);
            VentanaMensaje ventanaMensaje = new VentanaMensaje("Saldo añadido con éxito!");
            cl.show(cards, "Panel_ini");
        }));

        JButton atras = new JButton("Atras");
        panelSaldo.add(atras);
        atras.addActionListener((e -> {
            cl.show(cards, "Panel_ini");
        }));
    }
    private void configurarPanelSuscr(JPanel cards, JPanel panelSuscr) {
        JLabel mensaje = new JLabel("Elija la suscripcion que desea:");
        mensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelSuscr.add(mensaje);
        JComboBox<String> comboBoxSusc = new JComboBox<>();
        DefaultComboBoxModel<String> suscr = new DefaultComboBoxModel<>();
        for(Suscripciones v: Suscripciones.values()){
            suscr.addElement(v.name());
        }
        comboBoxSusc.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelSuscr.add(comboBoxSusc);

        CardLayout cl = (CardLayout)(cards.getLayout());

        JButton confirmar = new JButton("Confirmar");
        panelSuscr.add(confirmar);
        confirmar.addActionListener((e -> {
            saFacade.actualizarSuscr(comboBoxSusc.getSelectedIndex());
            VentanaMensaje ventanaMensaje = new VentanaMensaje("Suscripcion actualizada con éxito!");
            cl.show(cards, "Panel_ini");
        }));
    }



    private TUsuario crearUsuario(){
        String nombre, apellidos, correo, contrasenya, pais, dir;
        char sexo;
        int anyo;
        nombre = _nombre.getText();
        apellidos = _apellidos.getText();
        correo = _correo.getText();
        contrasenya = _contrasenya.getText();
        anyo = Integer.parseInt(_anyoNac.getText());
        pais = _pais.getText();
        dir = _direccion.getText();
        sexo = (char) _sexo.getSelectedItem();
        return new TUsuario(nombre, apellidos, correo, contrasenya, anyo, sexo, pais, dir, false);
    }


}
