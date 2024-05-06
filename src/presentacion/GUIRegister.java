package presentacion;

import negocio.SAFacade;
import negocio.SAUsuarioImp;
import negocio.TUsuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GUIRegister extends JPanel {
    private final SAFacade saFacade;
    private JButton _cancel;
    private JButton _createAccount;
    private JTextField _nombre;
    private JTextField _apellidos;
    private JPasswordField _contrasenya;
    private JPasswordField _repContrasenya;
    private JTextField _correo;
    private JTextField _pais;
    private JTextField _anyoNac;
    private JTextField _direccion;
    private DefaultComboBoxModel<Character> sexoModelo;
    private JComboBox<Character> comboBoxSexo;
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

        _contrasenya = new JPasswordField(20);
        _contrasenya.setAlignmentX(Component.CENTER_ALIGNMENT);
        _contrasenya.setToolTipText("Introduzca una contraseña");
        mainPanel.add(_contrasenya);

        addJLabel("Confirmar contraseña", mainPanel);

        _repContrasenya = new JPasswordField(20);
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
            reset();
            _parent.reset();
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
                try{
                    saFacade.create(tUsuario);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(mainPanel, "Ha habido un problema al crear la cuenta: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(mainPanel, "La contraseña no coincide");
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

    public void reset() {
        _nombre.setText("");
        _apellidos.setText("");
        _correo.setText("");
        _contrasenya.setText("");
        _repContrasenya.setText("");
        _pais.setText("");
        _anyoNac.setText("");
        _direccion.setText("");
    }
}
