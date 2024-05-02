package presentacion;

import negocio.AuthObserver;
import negocio.SAFacade;
import negocio.TUsuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import negocio.TUsuario;
import utils.ViewUtils;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class GUILogIn extends JDialog implements AuthObserver {

        private SAFacade saFacade;
        JButton _enter, _cancel, _register, _forgotPassword;

        private JPanel mainPanel;

        JTextField _user, _contrasenya;
        private GUIRegister guiUserRegister;

        public GUILogIn(SAFacade saFacade){
            this.saFacade = saFacade;
            this.guiUserRegister = new GUIRegister(this, saFacade);
            saFacade.registerObserver(this);
            initGUI();
        }

        public void initGUI(){

            mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBorder(new EmptyBorder(10, 10, 10 , 10));

            JLabel user = new JLabel("Correo: ");
            user.setAlignmentX(CENTER_ALIGNMENT);
            mainPanel.add(user);

            this._user = new JTextField();
            this._user.setAlignmentX(CENTER_ALIGNMENT);
            this._user.setToolTipText("Introduzca su usuario");
            mainPanel.add(this._user);

            JLabel contrasenya = new JLabel("Contraseña: ");
            contrasenya.setAlignmentX(CENTER_ALIGNMENT);
            mainPanel.add(contrasenya);

            this._contrasenya = new JTextField();
            this._contrasenya.setAlignmentX(CENTER_ALIGNMENT);
            this._contrasenya.setToolTipText("Introduzca su contraseña");
            mainPanel.add(this._contrasenya);

            this._enter = new JButton("Entrar");
            this._enter.setAlignmentX(CENTER_ALIGNMENT);
            mainPanel.add(this._enter);

            this._cancel = new JButton("Cancelar");
            this._cancel.setAlignmentX(CENTER_ALIGNMENT);
            mainPanel.add(this._cancel);

            this._register = new JButton("Registrarse");
            this._register.setAlignmentX(CENTER_ALIGNMENT);
            mainPanel.add(this._register);

            this._forgotPassword = new JButton("¿Has olvidado tu contraseña?");
            this._forgotPassword.setAlignmentX(CENTER_ALIGNMENT);
            mainPanel.add(this._forgotPassword);

            this._enter.addActionListener(e -> {
                saFacade.login(_user.getText(), _contrasenya.getText());
            });

            this._cancel.addActionListener(e -> {
                setVisible(false);
                setContentPane(mainPanel);
            });

            this._register.addActionListener(e -> {
                Transitions.makeWhiteFadeTransition(mainPanel, guiUserRegister, 1, (from, to) -> {
                    this.setContentPane((Container) to);
                });
            });

            this._forgotPassword.addActionListener(e -> {
                GUIForgotPassword guiForgotPassword = new GUIForgotPassword();
                guiForgotPassword.setVisible(true);
            });

            addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {

                }

                @Override
                public void windowClosing(WindowEvent e) {

                }

                @Override
                public void windowClosed(WindowEvent e) {
                    setContentPane(mainPanel);
                }

                @Override
                public void windowIconified(WindowEvent e) {

                }

                @Override
                public void windowDeiconified(WindowEvent e) {

                }

                @Override
                public void windowActivated(WindowEvent e) {

                }

                @Override
                public void windowDeactivated(WindowEvent e) {

                }
            });


            int size = Math.max(Math.max(mainPanel.getWidth(), guiUserRegister.getWidth()), Math.max(mainPanel.getHeight(), guiUserRegister.getHeight()));
            setSize(new Dimension(size, size));

            setContentPane(mainPanel);
        }

        private TUsuario crearUsuario(){
            TUsuario tUsuario = new TUsuario();
            tUsuario.setCorreo_e(_user.getText());
            tUsuario.setContrasenya(_contrasenya.getText());
            return tUsuario;
        }

        @Override
        public void onAuthChanged(boolean isAuth, int idUsuario){
            if(!isAuth)
                JOptionPane.showMessageDialog(this, "Usuario o contraseña no existente");
            else
                setVisible(false);
        }

        public void open(GUIWindow parent){
            setLocation(//
                    parent.getLocation().x + parent.getWidth() / 2 - getWidth() / 2, //
                    parent.getLocation().y + parent.getHeight() / 2 - getHeight() / 2);
            pack();
            setVisible(true);
        }

    }
