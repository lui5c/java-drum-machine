/**
 *  Help with javax.sound.sampled.Clip provided by:
 *  https://www.codejava.net/coding/how-to-play-back-audio-in-java-with-examples
 * 
 *  Tip to use a new Thread to play concurrent sounds:
 *  https://stackoverflow.com/questions/48735326/cant-play-sound-in-java-using-clip
 */
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  

public class DrumMachine {

     
   public static void main(String[] args){
      String kick_path = "assets/sounds/kick.wav";
      //String snare_path = "assets/sounds/snare.wav";
      String hat_path = "assets/sounds/hat.wav";

      ExecutorService executor = Executors.newFixedThreadPool(3);
      
      Sample kickSample = null;
      String snareSample = "1";
      Sample hatSample = null;
      try {
         kickSample = new Sample(kick_path);
         //snareSample = new Sample(snare_path);
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
      executor.execute(kickSample);
      executor.execute(hatSample);
   }
}