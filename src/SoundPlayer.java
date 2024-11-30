import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;

public class SoundPlayer {
    public static void playSound(String resourcePath) {
        try {
            URL resource = SoundPlayer.class.getResource(resourcePath);
            if (resource == null) {
                throw new IllegalArgumentException("Recurso não encontrado: " + resourcePath);
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(resource);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
