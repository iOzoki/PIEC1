import java.util.Timer;
import java.util.TimerTask;

public class Metronomo {
    private int bpm;
    private Timer timer;

    public Metronomo(int bpm) {
        this.bpm = bpm;
    }

    public void start() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        long interval = 60000 / bpm;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SoundPlayer.playSound("resources/metronome-85688.wav");
            }
        }, 0, interval);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void setBPM(int bpm) {
        this.bpm = bpm;
        start();
    }
}

