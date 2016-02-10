package sounds;

import java.io.*;
import javax.sound.sampled.*;

public class SoundMaster{

    /**
     *
     * @param filePath
     */
    public static void playButton(){
        try {
            File yourFile = new File(".\\src\\sounds\\ButtonSound.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(yourFile));
            clip.start();
            Thread.sleep((clip.getMicrosecondLength()/1000)-5555);
        }
        catch (Exception e) {
             e.printStackTrace();
        }
    }  
    
    public static void playLaunch(){
        try {
            File yourFile = new File(".\\src\\sounds\\LaunchSound.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(yourFile));
            clip.start();
            Thread.sleep(clip.getMicrosecondLength()/1000);
        }
        catch (Exception e) {
             e.printStackTrace();
        }
    } 
}