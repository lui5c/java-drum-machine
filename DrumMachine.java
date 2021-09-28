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

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.util.HashMap;

public class DrumMachine {
   ExecutorService threadPool;
   Sample kickSample = null;
   Sample snareSample = null;
   Sample hatSample = null;


   public DrumMachine(){
      threadPool = Executors.newFixedThreadPool(3);

      String kick_path = "assets/sounds/kick.wav";
      String snare_path = "assets/sounds/snare.wav";
      String hat_path = "assets/sounds/hat.wav";
      
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
         System.out.print("Could not successfully initialize DrumMachine.");
         System.exit(1);
      }

      GUI gui = new GUI();
      threadPool.execute(kickSample);
      threadPool.execute(hatSample);
      threadPool.execute(snareSample);

      //gui extends JFrame so all JFrame methods work on it
      gui.addWindowListener(new WindowListener() {
         @Override
         public void windowClosed(WindowEvent e){
            //System.out.println("window closed");
            threadPool.shutdown();
         }
         @Override
         public void windowClosing(WindowEvent e){}
         @Override
         public void windowDeiconified(WindowEvent e){}
         @Override
         public void windowDeactivated(WindowEvent e){}
         @Override
         public void windowIconified(WindowEvent e){}
         @Override
         public void windowOpened(WindowEvent e){}
         @Override
         public void windowActivated(WindowEvent e){}
      });
   }

   public void playConfiguration(HashMap<String, int[]> pattern){
      //pattern is [[hat pattern], [snare pattern], [kick pattern]]
      System.out.println(pattern);
   }
   public static void main(String[] args){
      new DrumMachine();
   }
}