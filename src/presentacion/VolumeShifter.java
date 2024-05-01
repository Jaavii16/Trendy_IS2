package presentacion;

import javax.sound.sampled.FloatControl;

public class VolumeShifter implements Runnable {

    private final FloatControl gainControl;
    private boolean stop;

    public VolumeShifter(FloatControl gainControl) {
        this.gainControl = gainControl;
    }

    public void setVolume(double value) {
        // value is between 0 and 1
        value = (value <= 0.0) ? 0.001 : ((value > 1.0) ? 1.0 : value);
        try {
            float dB = (float) (Math.log(value) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    float currDB = 0F;
    float targetDB = 0F;
    float fadePerStep = .1F;   // .1 works for applets, 1 is okay for apps
    boolean fading = false;

    public void shiftVolumeTo(double value) {
        // value is between 0 and 1
        if (value == 0F)
            stop = true;

        value = (value <= 0.0) ? 0.001 : ((value > 1.0) ? 1.0 : value);
        targetDB = (float) (Math.log(value) / Math.log(10.0) * 20.0);
        if (!fading) {
            currDB = gainControl.getValue();
            Thread t = new Thread(this);  // start a thread to fade volume
            t.start();  // calls run() below
        }
    }

    public void run() {
        fading = true;   // prevent running twice on same sound
        if (currDB > targetDB) {
            while (currDB > targetDB) {
                currDB -= fadePerStep;
                gainControl.setValue(currDB);
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                }
            }
        } else if (currDB < targetDB) {
            while (currDB < targetDB) {
                currDB += fadePerStep;
                gainControl.setValue(currDB);
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                }
            }
        }
        fading = false;
        currDB = targetDB;  // now sound is at this volume level

        if (stop) {
            gainControl.setValue(-80F);
        }
    }
}
