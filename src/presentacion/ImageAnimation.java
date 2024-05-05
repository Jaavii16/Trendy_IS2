package presentacion;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicLong;

public class ImageAnimation extends JPanel {

    private static final double SCALE = 0.5;

    private final BufferedImage fullImage;
    private final int expectedEndTimeMs;
    private final double animationSpeed;

    private boolean stop;
    private double percentage;

    private long initTime;
    private double rotacion;
    private int fps;
    private double deltaTime;
    private Clip audio;

    public ImageAnimation(BufferedImage image, int expectedEndTimeMs, double animationSpeed) {
        this.fullImage = image;
        this.expectedEndTimeMs = expectedEndTimeMs;
        this.initTime = Long.MAX_VALUE;
        this.animationSpeed = animationSpeed;

        playSound("sounds/ascensor.wav");

        setBackground(Color.decode("0xc0c9f4"));
        setFont(new Font("Arial", Font.BOLD, 20));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        percentage += (((double) (System.currentTimeMillis() - initTime) / expectedEndTimeMs) - percentage);
        drawPercentageBar(g2d, (int) (getHeight() - (getHeight() * 0.1)), "Loading...", percentage);

        // Escribe trendy en grande encima del logo dependiendo del tamaño de la ventana
        int fontSize = (int) (getHeight() * 0.1); // 10% of window height
        g.setFont(new Font("Arial", Font.BOLD, fontSize));
        g.setColor(Color.BLACK);
        FontMetrics fm = g.getFontMetrics();
        int stringX = (getWidth() - fm.stringWidth("TRENDY")) / 2;
        int stringY = fm.getHeight() / 2 + fm.getAscent();
        g.drawString("TRENDY", stringX, stringY);

        // Pinta un circulo con poca opacidad
        g2d.setColor(new Color(0, 0, 0, 50));
        if (getWidth() > getHeight())
            g2d.fillOval((int) ((getWidth() - (getHeight() * SCALE)) / 2), (int) ((getHeight() - (getHeight() * SCALE)) / 2), (int) (getHeight() * SCALE), (int) (getHeight() * SCALE));
        else
            g2d.fillOval((int) ((getWidth() - (getWidth() * SCALE)) / 2), (int) (((getHeight()) - (getWidth() * SCALE)) / 2), (int) (getWidth() * SCALE), (int) (getWidth() * SCALE));

        // Crea una imagen escalada a partir de la imagen original
        BufferedImage image;
        if (getWidth() > getHeight()) {
            image = new BufferedImage((int) (getHeight() * SCALE), (int) (getHeight() * SCALE), BufferedImage.TYPE_INT_ARGB);
        } else {
            image = new BufferedImage((int) (getWidth() * SCALE), (int) (getWidth() * SCALE), BufferedImage.TYPE_INT_ARGB);
        }
        Graphics2D g2dImage = image.createGraphics();
        g2dImage.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2dImage.drawImage(fullImage, 0, 0, image.getWidth(), image.getHeight(), null);
        g2dImage.dispose();

        // Ubicación de dibujo (usando el centro del panel y el centro de la imagen)
        int drawLocationX = getLocation().x + getWidth() / 2 - image.getWidth() / 2;
        int drawLocationY = getLocation().y + getHeight() / 2 - image.getHeight() / 2;

        // Informacion de rotacion
        double rotation = Math.toRadians(rotacion);
        double locationX = (double) image.getWidth() / 2;
        double locationY = (double) image.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotation, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        // Dibuja la imagen rotada en las ubicaciones de dibujo
        g2d.drawImage(op.filter(image, null), drawLocationX, drawLocationY, null);
    }

    public void play() {

        this.initTime = System.currentTimeMillis();
        this.stop = false;
        this.fps = 0;

        updateFPS();

        int clockPeriodMs = 1000 / fps;
        rotacion = 0.1;

        AtomicLong lastTime = new AtomicLong(System.currentTimeMillis());

        Timer timer = new Timer(clockPeriodMs, null);
        timer.addActionListener(e -> {
            deltaTime = (System.currentTimeMillis() - lastTime.get());
            rotacion += getAngleAugment(rotacion) * animationSpeed * deltaTime;
            lastTime.set(System.currentTimeMillis());

            if (rotacion >= 359) rotacion = (rotacion + 0.1) % 360;
            if (rotacion >= 359.9 && stop) {
                timer.stop();
                FloatControl gainControl = (FloatControl) audio.getControl(FloatControl.Type.MASTER_GAIN);
                VolumeShifter volumeShifter = new VolumeShifter(gainControl);
                volumeShifter.shiftVolumeTo(0F);

            }

            updateFPS();
            timer.setDelay(1000 / fps);

            repaint();
        });
        timer.start();

        while (timer.isRunning()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                timer.stop();
                stop = true;
            }
        }
    }

    private void playSound(String s) {
        try {
            // Crea un objeto URL que apunta al archivo de audio
            URL url = this.getClass().getClassLoader().getResource(s);
            // Obtiene un AudioInputStream del archivo de audio
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            // Obtiene un Clip de línea de audio y lo abre
            audio = AudioSystem.getClip();

            audio.open(audioIn);

            FloatControl gainControl = (FloatControl) audio.getControl(FloatControl.Type.MASTER_GAIN);
            VolumeShifter volumeShifter = new VolumeShifter(gainControl);
            volumeShifter.setVolume(0.05F);

            // Empieza la cancion en el microsegundo:
            audio.setMicrosecondPosition(177_000_000);
            // Inicia la reproducción del sonido
            audio.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    private void updateFPS() {
        GraphicsDevice gd = this.getGraphicsConfiguration().getDevice();
        DisplayMode dm = gd.getDisplayMode();
        fps = dm.getRefreshRate();
        if (fps == DisplayMode.REFRESH_RATE_UNKNOWN) fps = 60;
    }

    private double getAngleAugment(double rotacion) {
        return Math.sin(Math.toRadians((rotacion) / 2));
    }

    private void drawPercentageBar(Graphics2D g, int y, String s, double percentage) {

        // Define el tamaño del progress bar
        int progressBarWidth = (int) (getWidth() * 0.8); // 80% of window width
        int progressBarHeight = (int) (getHeight() * 0.05); // 5% of window height

        // Calcula la coordenada x del progress bar
        int x = (getWidth() - progressBarWidth) / 2;

        // Limita el porcentaje a 1
        percentage = Math.max(Math.min(percentage, 1.0), 0.0);

        // Calcula la anchura de la parte llena del progress bar
        int filledWidth = (int) (percentage * progressBarWidth);

        // Pinta la parte llena del progress bar
        g.setColor(Color.decode("0x3f51b5"));
        g.fillRect(x, y, filledWidth, progressBarHeight);

        // Cambia el tamaño de la fuente para que se ajuste al tamaño de la ventana
        int fontSize = (int) (getHeight() * 0.02); // 2% of window height
        g.setFont(new Font("Arial", Font.BOLD, fontSize));

        // Pinta el texto del centro del progress bar
        g.setColor(Color.BLACK);
        FontMetrics fm = g.getFontMetrics();
        int stringX = x + (progressBarWidth - fm.stringWidth(s)) / 2;
        int stringY = y + ((progressBarHeight - fm.getHeight()) / 2) + fm.getAscent();
        g.drawString(s, stringX, stringY);

        // Pinta los bordes del progress bar
        g.setColor(Color.BLACK);
        g.drawRect(x, y, progressBarWidth, progressBarHeight);
    }

    public void stop() {
        stop = true;
    }
}