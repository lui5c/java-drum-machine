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
        
        try {
           Sample kickSample = new Sample(kick_path);
           kickSample.play();
        } catch (UnsupportedAudioFileException e){
           System.out.println("error");
           e.printStackTrace();
        } catch (IOException e){
           e.printStackTrace();
        }
     }
    }