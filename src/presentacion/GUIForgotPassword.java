package presentacion;

import negocio.SAFacade;
import negocio.TUsuario;

import javax.swing.*;
import java.awt.*;

public class GUIForgotPassword extends JPanel {
    private final GUILogin parent;
    private final SAFacade saFacade;
    JButton _enter, _cancel;
    JTextField _user, _contrasenya, _repContrasenya;

    public GUIForgotPassword(GUILogin guiLogin, SAFacade saFacade) {
        parent = guiLogin;
        this.saFacade = saFacade;
        initGUI();
    }

    public void initGUI() {

        setLayout(new GridBagLayout());

        JLabel correo = new JLabel("Correo");
        JLabel contrasenya = new JLabel("Nueva contraseña");
        JLabel repContrasenya = new JLabel("Repetir contraseña");

        this._user = new JTextField();
        this._contrasenya = new JPasswordField(20);
        this._repContrasenya = new JPasswordField(20);

        this._enter = new JButton("Cambiar contraseña");
        this._cancel = new JButton("Cancelar");
        this._enter.addActionListener(e -> {

            if (_contrasenya.getText().equals(_repContrasenya.getText())) {
                TUsuario loginUser = crearUsuario();
                try{
                    saFacade.update(loginUser);
                    JOptionPane.showMessageDialog(null, "Contraseña cambiada con éxito");
                    parent.reset();
                }catch(RuntimeException ex){
                    JOptionPane.showMessageDialog(this, "No se ha podido actualizar la contraseña");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
            }
        });

        this._cancel.addActionListener(e -> {
            reset();
            parent.reset();
        });

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 0;
        add(correo, c);
        c.gridx = 1;
        add(_user, c);
        c.gridx = 0;
        c.gridy = 1;
        add(contrasenya, c);
        c.gridx = 1;
        add(_contrasenya, c);
        c.gridx = 0;
        c.gridy = 2;
        add(repContrasenya, c);
        c.gridx = 1;
        add(_repContrasenya, c);
        c.gridx = 0;
        c.gridy = 3;
        add(_cancel, c);
        c.gridx = 1;
        add(_enter, c);
    }

    private TUsuario crearUsuario() {
        TUsuario tUsuario = new TUsuario();
        tUsuario.setCorreo_e(_user.getText());
        tUsuario.setContrasenya(_contrasenya.getText());
        return tUsuario;
    }

    public void reset() {
        _user.setText("");
        _contrasenya.setText("");
        _repContrasenya.setText("");
    }
}