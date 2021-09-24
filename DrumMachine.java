/**
 *  Help with javax.sound.sampled.Clip provided by:
 *  https://www.codejava.net/coding/how-to-play-back-audio-in-java-with-examples
 */
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

 public class DrumMachine {

    
     
     public static void main(String[] args){
        String kick_path = "assets/sounds/kick.wav";
        String snare_path = "assets/sounds/snare.wav";
        String hat_path = "assets/sounds/hat.wav";
        
        Sample kickSample = null;
        Sample snareSample = null;
        Sample hatSample = null;
        try {
           kickSample = new Sample(kick_path);
           snareSample = new Sample(snare_path);
           hatSample = new Sample(hat_path);
        } catch (UnsupportedAudioFileException e){
           kickSample = null;
           System.out.println("Unsupported Audio File");
           e.printStackTrace();
        } catch (IOException e){
           kickSample = null;
           e.printStackTrace();
        }

        if (kickSample == null || snareSample == null || hatSample == null){
           System.exit(1);
        }
        
        GUI gui = new GUI();

     }
    }