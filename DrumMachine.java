
/**
 *  Help with javax.sound.sampled.Clip provided by:
 *  https://www.codejava.net/coding/how-to-play-back-audio-in-java-with-examples
 * 
 *  Tip to use a new Thread to play concurrent sounds:
 *  https://stackoverflow.com/questions/48735326/cant-play-sound-in-java-using-clip
 *  https://stackoverflow.com/questions/63210268/how-to-start-resume-and-stop-pause-a-thread-inside-the-action-listener-in-java
 */
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.*;
import java.awt.event.*;

import java.util.HashMap;

public class DrumMachine extends Thread {
   Sample kickSample = null;
   Sample snareSample = null;
   Sample hatSample = null;

   volatile boolean looping = false;
   volatile boolean listening = false;

   volatile boolean[] hatPattern;
   volatile boolean[] snarePattern;
   volatile boolean[] kickPattern;

   volatile long interval = -1;
   int beatsElapsed = 0;

   public Thread hatSampleThread;
   public Thread snareSampleThread;
   public Thread kickSampleThread;

   public DrumMachine() {

      String kick_path = "assets/sounds/kick.wav";
      String snare_path = "assets/sounds/snare.wav";
      String hat_path = "assets/sounds/hat.wav";

      try {
         kickSample = new Sample(kick_path);
         snareSample = new Sample(snare_path);
         hatSample = new Sample(hat_path);
      } catch (UnsupportedAudioFileException e) {
         kickSample = null;
         System.out.println("Unsupported Audio File");
         e.printStackTrace();
      } catch (IOException e) {
         kickSample = null;
         e.printStackTrace();
      }

      if (kickSample == null || snareSample == null || hatSample == null) {
         System.out.print("Could not successfully initialize DrumMachine.");
         System.exit(1);
      }

      hatSampleThread = new Thread(hatSample);
      snareSampleThread = new Thread(snareSample);
      kickSampleThread = new Thread(kickSample);

      hatSampleThread.start();
      snareSampleThread.start();
      kickSampleThread.start();

      listening = true;
   }

   public synchronized void setConfig(HashMap<String, String> pattern) {
      // parse the information in the HashMap
      System.out.println("configuring drum machine for :" + pattern);
      interval = Long.parseLong(pattern.get("interval")); // time to wait between beats

      String hatString = pattern.get("hat");
      hatPattern = new boolean[hatString.length()];
      for (int i = 0; i < hatString.length(); i++) {
         hatPattern[i] = Character.compare(hatString.charAt(i), 'x') == 0;
      }

      String snareString = pattern.get("snare");
      snarePattern = new boolean[snareString.length()];
      for (int i = 0; i < snareString.length(); i++) {
         snarePattern[i] = Character.compare(snareString.charAt(i), 'x') == 0;
      }

      String kickString = pattern.get("kick");
      kickPattern = new boolean[kickString.length()];
      for (int i = 0; i < kickString.length(); i++) {
         kickPattern[i] = Character.compare(kickString.charAt(i), 'x') == 0;
      }
      notifyAll();
   }

   @Override
   public void run() {
      try {
         while (listening) {
            synchronized (this) {
               if (!looping) {
                  wait();
               }
            }
            while (looping && interval > 0) {
               String hat = "_";
               String snare = "_";
               String kick = "_";

               if (hatPattern[beatsElapsed % hatPattern.length]) {
                  hatSample.play();
                  hat = "h";
               }
               if (snarePattern[beatsElapsed % snarePattern.length]) {
                  snareSample.play();
                  snare = "s";
               }
               if (kickPattern[beatsElapsed % kickPattern.length]) {
                  kickSample.play();
                  kick = "k";
               }
               beatsElapsed++;

               System.out.print(beatsElapsed + "\t");
               System.out.println(kick+snare+hat);
               Thread.sleep(interval);
            }
         }
      } catch (InterruptedException e) {
         System.out.println("problem sleeping thread");
      }
   }

   public synchronized void keepLooping() {
      looping = true;
      notifyAll();
   }

   public synchronized void stopLooping() {
      looping = false;
      notifyAll();
   }

   public synchronized void stopListening() {
      listening = false;
      looping = false;
      hatSample.shutdown();
      snareSample.shutdown();
      kickSample.shutdown();
      notifyAll();
   }
}