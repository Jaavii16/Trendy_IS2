package presentacion;

import negocio.SAFacade;
import negocio.UserObserver;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUILogin extends JDialog implements UserObserver {

    private SAFacade saFacade;
    JButton _enter, _cancel, _register, _forgotPassword;

    private JPanel mainPanel;

    JTextField _user, _contrasenya;
    private GUIRegister guiUserRegister;
    private GUIForgotPassword guiForgotPassword;

    public GUILogin(SAFacade saFacade) {
        this.saFacade = saFacade;
        this.guiUserRegister = new GUIRegister(this, saFacade);
        this.guiForgotPassword = new GUIForgotPassword(this, saFacade);
        initGUI();
    }

    public void initGUI() {
        File img = new File("resources/imgs/trendy_logo.png");

        try {
            BufferedImage imgBuffered = ImageIO.read(img);
            // Define los bordes a recortar
            int margin = 130;

            // Obtiene las dimensiones de la imagen original
            int originalWidth = imgBuffered.getWidth();
            int originalHeight = imgBuffered.getHeight();

            // Define las dimensiones de la imagen recortada
            int croppedWidth = originalWidth - 2 * margin;
            int croppedHeight = originalHeight - 2 * margin;

            // Crea la imagen recortada
            BufferedImage croppedImage = imgBuffered.getSubimage(margin, margin, croppedWidth, croppedHeight);

            setIconImage(croppedImage);
        } catch (IOException ignored) {
        }

        setTitle("Iniciar sesión");
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel user = new JLabel("Correo: ");
        user.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(user, gbc);

        this._user = new JTextField();
        this._user.setAlignmentX(CENTER_ALIGNMENT);
        this._user.setToolTipText("Introduzca su usuario");
        this._user.setMaximumSize(new Dimension(200, 20));
        gbc.gridy++;
        mainPanel.add(this._user, gbc);

        JLabel contrasenya = new JLabel("Contraseña: ");
        contrasenya.setAlignmentX(CENTER_ALIGNMENT);
        gbc.gridy++;
        mainPanel.add(contrasenya, gbc);

        this._contrasenya = new JPasswordField(20);
        this._contrasenya.setAlignmentX(CENTER_ALIGNMENT);
        this._contrasenya.setToolTipText("Introduzca su contraseña");
        this._contrasenya.setMaximumSize(new Dimension(200, 20));
        gbc.gridy++;
        mainPanel.add(this._contrasenya, gbc);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.fill = GridBagConstraints.HORIZONTAL;
        buttonGbc.gridx = 0;
        buttonGbc.gridy = 0;

        this._enter = new JButton("Entrar");
        this._enter.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.add(this._enter, buttonGbc);

        buttonGbc.gridy++;
        this._forgotPassword = new JButton("¿Has olvidado tu contraseña?");
        this._forgotPassword.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.add(this._forgotPassword, buttonGbc);

        buttonGbc.gridy++;
        this._register = new JButton("Registrarse");
        this._register.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.add(this._register, buttonGbc);

        gbc.gridy++;
        mainPanel.add(buttonPanel, gbc);

        gbc.gridy++;
        this._cancel = new JButton("Cancelar");
        this._cancel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(this._cancel, gbc);

        this._enter.addActionListener(e -> {
            try {
                saFacade.login(_user.getText(), _contrasenya.getText());
                JOptionPane.showMessageDialog(mainPanel, "Te has logueado con exito");
            } catch (IllegalArgumentException e1) {
                JOptionPane.showMessageDialog(mainPanel, e1.getMessage());
            }
        });

        this._cancel.addActionListener(e -> {
            setVisible(false);
            setContentPane(mainPanel);
            revalidate();
            repaint();
            pack();
            setLocationRelativeTo(getParent());
        });

        this._register.addActionListener(e -> {
            setTitle("Registro");
            this.setContentPane(guiUserRegister);
            revalidate();
            repaint();
            pack();
            setLocationRelativeTo(getParent());
        });

        this._forgotPassword.addActionListener(e -> {
            setTitle("Cambiar contraseña");
            this.setContentPane(guiForgotPassword);
            revalidate();
            repaint();
            pack();
            setLocationRelativeTo(getParent());
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

        mainPanel.add(Box.createVerticalGlue());

        setContentPane(mainPanel);

        pack();
        revalidate();
        repaint();
    }

    @Override
    public void onUserDataChanged(boolean isAuth, int idUsuario) {
        if (!isAuth)
            JOptionPane.showMessageDialog(this, "Usuario o contraseña no existente");
        else {
            setVisible(false);
            EventQueue.invokeLater(() -> saFacade.unregisterObserver(this));
        }
    }

    public void open(GUIWindow parent) {
        saFacade.registerObserver(this);
        setLocation(//
                parent.getLocation().x + parent.getWidth() / 2 - getWidth() / 2, //
                parent.getLocation().y + parent.getHeight() / 2 - getHeight() / 2);
        setVisible(true);
    }

    public void reset() {
        setTitle("Iniciar sesión");

        this._user.setText("");
        this._contrasenya.setText("");

        guiUserRegister.reset();
        guiForgotPassword.reset();

        this.setContentPane(mainPanel);
        this.revalidate();
        this.repaint();
        this.pack();
        this.setLocationRelativeTo(getParent());
    }
}