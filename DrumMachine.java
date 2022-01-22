
/**
 *  Help with javax.sound.sampled.Clip provided by:
 *  https://www.codejava.net/coding/how-to-play-back-audio-in-java-with-examples
 * 
 *  Tip to use a new Thread to play concurrent sounds:
 *  https://stackoverflow.com/questions/48735326/cant-play-sound-in-java-using-clip
 *  https://stackoverflow.com/questions/63210268/how-to-start-resume-and-stop-pause-a-thread-inside-the-action-listener-in-java
 */
import java.io.IOException;

import javax.sound.sampled.*;

import java.util.HashMap;

public class DrumMachine extends Thread {
   Sample kickSample1 = null;
   Sample snareSample1 = null;
   Sample hatSample1 = null;
   Sample kickSample2 = null;
   Sample snareSample2 = null;
   Sample hatSample2 = null;
   Sample kickSample3 = null;
   Sample snareSample3 = null;
   Sample hatSample3 = null;

   Sample[] allSamples;

   volatile boolean looping = false;
   volatile boolean listening = false;

   volatile boolean[] hatPattern;
   volatile boolean[] snarePattern;
   volatile boolean[] kickPattern;

   volatile long interval = -1;
   int beatsElapsed = 0;

   public DrumMachine() {

      String kick_path = "assets/sounds/kick.wav";
      String snare_path = "assets/sounds/snare.wav";
      String hat_path = "assets/sounds/hat.wav";

      try {
         kickSample1 = new Sample(kick_path);
         snareSample1 = new Sample(snare_path);
         hatSample1 = new Sample(hat_path);
         kickSample2 = new Sample(kick_path);
         snareSample2 = new Sample(snare_path);
         hatSample2 = new Sample(hat_path);
         kickSample3 = new Sample(kick_path);
         snareSample3 = new Sample(snare_path);
         hatSample3 = new Sample(hat_path);
      } catch (UnsupportedAudioFileException e) {
         kickSample1 = null;
         System.out.println("Unsupported Audio File");
         e.printStackTrace();
      } catch (IOException e) {
         kickSample1 = null;
         e.printStackTrace();
      }

      allSamples = new Sample[9];
      allSamples[0] = kickSample1;
      allSamples[1] = kickSample2;
      allSamples[2] = kickSample3;
      allSamples[3] = snareSample1;
      allSamples[4] = snareSample2;
      allSamples[5] = snareSample3;
      allSamples[6] = hatSample1;
      allSamples[7] = hatSample2;
      allSamples[8] = hatSample3;

      int index = 0;
      while (index < allSamples.length){
         if (allSamples[index++] == null) {
            System.out.print("Could not successfully initialize DrumMachine.");
            System.exit(1);
         }
      }
      listening = true;
   }

   public synchronized void setConfig(HashMap<String, String> pattern) {
      // parse the information in the HashMap returned by GUI.getConfig()
      System.out.println("configuring drum machine for :" + pattern);
      
      // time to wait between beats
      interval = Long.parseLong(pattern.get("interval")); 

      // reinitiate the hatPattern boolean[]
      // and make the booleans correspond to the string value
      String hatString = pattern.get("hat");
      hatPattern = new boolean[hatString.length()];
      for (int i = 0; i < hatString.length(); i++) {
         hatPattern[i] = Character.compare(hatString.charAt(i), 'x') == 0;
      }
      
      // reinitiate and populate snarePattern boolean[]
      String snareString = pattern.get("snare");
      snarePattern = new boolean[snareString.length()];
      for (int i = 0; i < snareString.length(); i++) {
         snarePattern[i] = Character.compare(snareString.charAt(i), 'x') == 0;
      }

      // reinitiate and populate kickPattern boolean[]
      String kickString = pattern.get("kick");
      kickPattern = new boolean[kickString.length()];
      for (int i = 0; i < kickString.length(); i++) {
         kickPattern[i] = Character.compare(kickString.charAt(i), 'x') == 0;
      }
   }

   @Override
   public void run() {
      // this runs when DMThread.run() is called from GUI.java
      try {
         while (listening) {
            synchronized (this) {
               if (!looping) {
                  // if we aren't looping, wait until we are
                  wait();
               }
            }
            while (looping && interval > 0) {
               // play and also measure which thread is doing the playing
               // if all threads are busy, then the machine is going too fast
               // also, show a little text interface to monitor the individual
               // beats as they come out.

               String hat = "__";
               String snare = "__";
               String kick = "__";

               if (hatPattern[beatsElapsed % hatPattern.length]) {
                  if (!hatSample1.playing){
                     hatSample1.play();hat="h1";
                  } else if (!hatSample2.playing){
                     hatSample2.play();hat="h2";
                  } else  if (!hatSample3.playing){
                     hatSample3.play();hat="h3";
                  } else {
                     System.out.println("bpm overload!!!!");
                  }
               }
               if (snarePattern[beatsElapsed % snarePattern.length]) {
                  if (!snareSample1.playing){
                     snareSample1.play();snare="s1";
                  } else if (!snareSample2.playing){
                     snareSample2.play();snare="s2";
                  } else  if (!snareSample3.playing){
                     snareSample3.play();snare="s3";
                  } else {
                     System.out.println("bpm overload!!!!");
                  }
               }
               if (kickPattern[beatsElapsed % kickPattern.length]) {
                  if (!kickSample1.playing){
                     kickSample1.play();kick="k1";
                  } else if (!kickSample2.playing){
                     kickSample2.play();kick="k2";
                  } else  if (!kickSample3.playing){
                     kickSample3.play();kick="k3";
                  } else {
                     System.out.println("bpm overload!!!!");
                  }
               }

               // format and print the rhythm
               beatsElapsed++;
               System.out.print(beatsElapsed + "\t");
               System.out.println(kick+snare+hat);
               //wait until the next time to play the Samples
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
      beatsElapsed = 0;
      notifyAll();
   }

   public synchronized void stopListening() {
      listening = false;
      looping = false;
      for (int i = 0; i < allSamples.length; i++){
         allSamples[i].shutdown();}
      notifyAll();
   }
}