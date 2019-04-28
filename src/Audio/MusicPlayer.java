package Audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;


public class MusicPlayer {

    private static Clip clip;
    private boolean paused = false;
    private long clipTime;
    private AudioInputStream audioInput;
    private URL path;

    public void playMusic(String musicLocation){

        try{
            path  = getClass().getClassLoader().getResource(musicLocation);

            audioInput = AudioSystem.getAudioInputStream(path);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void stopMusic() {

        clip.stop();
    }

    public void resume(){
        if(paused) {

            clip.setMicrosecondPosition(clipTime);

            clip.start();

            paused = false;
        }

    }
    public void pause(){



        clipTime =  clip.getMicrosecondPosition();

        clip.stop();

        paused = true;


    }
}

