package Audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/*
        Sound player class is used to pay all other sound effects apart from the BGM
 */

public class SoundPlayer {

    // Mapping and stroing the soun clips prevents memeory leaks and other weird errors by keeping one copy
    // of each sound effect, and then replaying it when needed
    private  Map<String , Clip> clipMap = new HashMap<>();


    public void playSound(String soundLocation){
        //If we have already loaded the specified clip, play that version
        if(clipMap.containsKey(soundLocation)) {

            Clip thisClip = clipMap.get(soundLocation);

                thisClip.stop();

                thisClip.setMicrosecondPosition(0);
                thisClip.start();
        }
        else {
            try {

                URL path = getClass().getClassLoader().getResource(soundLocation);

                AudioInputStream audioInput = AudioSystem.getAudioInputStream(path);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clipMap.put(soundLocation, clip);
                audioInput.close();


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
