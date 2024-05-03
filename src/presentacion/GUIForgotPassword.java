package presentacion;

import negocio.SAUsuarioImp;
import negocio.TUsuario;
import utils.ViewUtils;

import javax.swing.*;

public class GUIForgotPassword extends JDialog{
    private SAUsuarioImp saUsuario;
    JButton _enter, _cancel;
    JTextField _user, _contrasenya, _repContrasenya;
    public GUIForgotPassword(){
        initGUI();
    }
    public void initGUI(){
        this._user = new JTextField();
        this._contrasenya = new JPasswordField(20);
        this._repContrasenya = new JPasswordField(20);

        this._enter = new JButton("Entrar");
        this._cancel = new JButton("Cancelar");
        this._enter.addActionListener(e -> {

            if(_contrasenya.getText().equals(_repContrasenya.getText())) {
                TUsuario loginUser = crearUsuario();
                saUsuario.update(loginUser);
                ViewUtils.quit(this);
            }
            else{
                JOptionPane.showMessageDialog(null, "Usuario no existente");
            }

        });

        this._cancel.addActionListener(e -> {
            ViewUtils.quit(this);
        });
    }
    private TUsuario crearUsuario(){
        TUsuario tUsuario = new TUsuario();
        tUsuario.setCorreo_e(_user.getText());
        tUsuario.setContrasenya(_contrasenya.getText());
        return tUsuario;
    }
}