package statusboard;

import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audible {
    public final static String ASHORE = "sound/ashore.wav";
    public final static String AFLOAT ="sound/afloat.wav";
    
    public void play(String status) {
        Clip clip;
        try {
            InputStream input = getClass().getResourceAsStream(status);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(input);
            clip = AudioSystem.getClip();
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.open(audioIn);
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    
}
