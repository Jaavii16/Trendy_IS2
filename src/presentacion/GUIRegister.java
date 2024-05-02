package presentacion;

import negocio.SAFacade;
import negocio.SAUsuarioImp;
import negocio.TUsuario;
import utils.ViewUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GUIRegister extends JPanel {
    private final SAFacade saFacade;
    private JButton _cancel;
    private JButton _createAccount;
    private JLabel nombre;
    private JLabel apellidos;
    private JLabel contrasenya;
    private JLabel repContrasenya;
    private JLabel correo;
    private JLabel pais;
    private JLabel anyoNac;
    private JLabel sexo;
    private JLabel direccion;
    private JTextField _nombre;
    private JTextField _apellidos;
    private JTextField _contrasenya;
    private JTextField _repContrasenya;
    private JTextField _correo;
    private JTextField _pais;
    private JTextField _anyoNac;
    private JTextField _direccion;
    private DefaultComboBoxModel<Character> sexoModelo;
    private JComboBox<Character> comboBoxSexo;
    private SAUsuarioImp saUsuario;
    private TUsuario tUsuario;

    private GUILogin _parent;

    public GUIRegister(GUILogin parent, SAFacade saFacade) {
        this.saFacade = saFacade;
        this._parent = parent;
        initGUI();
    }

    private void initGUI() {
        //setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        addJLabel("Nombre", mainPanel);

        _nombre = new JTextField();
        _nombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        _nombre.setToolTipText("Introduzca su nombre");
        mainPanel.add(_nombre);

        addJLabel("Apellidos", mainPanel);

        _apellidos = new JTextField();
        _apellidos.setAlignmentX(Component.CENTER_ALIGNMENT);
        _apellidos.setToolTipText("Intruduzca sus apellidos");
        mainPanel.add(_apellidos);


        addJLabel("Correo", mainPanel);

        _correo = new JTextField();
        _correo.setAlignmentX(Component.CENTER_ALIGNMENT);
        _correo.setToolTipText("Introduzca su correo electronico");
        mainPanel.add(_correo);

        addJLabel("Contraseña", mainPanel);

        _contrasenya = new JTextField();
        _contrasenya.setAlignmentX(Component.CENTER_ALIGNMENT);
        _contrasenya.setToolTipText("Introduzca una contraseña");
        mainPanel.add(_contrasenya);

        addJLabel("Confirmar contraseña", mainPanel);

        _repContrasenya = new JTextField();
        _repContrasenya.setAlignmentX(Component.CENTER_ALIGNMENT);
        _repContrasenya.setToolTipText("Repita la contraseña anterior");
        mainPanel.add(_repContrasenya);


        addJLabel("Pais", mainPanel);

        _pais = new JTextField();
        _pais.setAlignmentX(Component.CENTER_ALIGNMENT);
        _pais.setToolTipText("Intruduzca su pais");
        mainPanel.add(_pais);

        addJLabel("Direccion", mainPanel);

        _direccion = new JTextField();
        _direccion.setAlignmentX(Component.CENTER_ALIGNMENT);
        _direccion.setToolTipText("Introduzca su direccion");
        mainPanel.add(_direccion);

        addJLabel("Año de nacimiento", mainPanel);

        _anyoNac = new JTextField();
        _anyoNac.setAlignmentX(Component.CENTER_ALIGNMENT);
        _anyoNac.setToolTipText("Introduzca su año de nacimiento");
        mainPanel.add(_anyoNac);

        addJLabel("Sexo", mainPanel);
        sexoModelo = new DefaultComboBoxModel<>();
        setComboBox();
        comboBoxSexo = new JComboBox<>(sexoModelo);
        comboBoxSexo.setAlignmentX(Component.CENTER_ALIGNMENT);
        comboBoxSexo.setToolTipText("Seleccione su sexo");
        mainPanel.add(comboBoxSexo);

        _cancel = new JButton("Cancelar");
        _cancel.addActionListener((e) -> {
            ViewUtils.quit(this);
            _parent.setVisible(true);
        });
        _cancel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(_cancel);

        _createAccount = new JButton("Crear cuenta");
        _createAccount.addActionListener((e) -> {
            String contra, contraRep;
            contra = _contrasenya.getText();
            contraRep = _repContrasenya.getText();
            if (contra.equals(contraRep)) {
                tUsuario = crearUsuario();
                saFacade.create(tUsuario);
            } else {
                System.out.println("La contraseña no coincide");
            }
        });
        _createAccount.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(_createAccount);

        //setViewportView(mainPanel);

        add(mainPanel);

        revalidate();
        repaint();
    }

    private void setComboBox() {
        sexoModelo.addElement('M');
        sexoModelo.addElement('F');
    }

    private TUsuario crearUsuario() {
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
        sexo = (Character) comboBoxSexo.getSelectedItem();
        return new TUsuario(nombre, apellidos, correo, contrasenya, anyo, sexo, pais, dir, false);
    }

    private void addJLabel(String text, Container container) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(label);
    }
}
