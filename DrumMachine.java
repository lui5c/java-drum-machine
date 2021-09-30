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

public class DrumMachine extends Thread{
   ExecutorService threadPool;
   Sample kickSample = null;
   Sample snareSample = null;
   Sample hatSample = null;

   volatile boolean looping = false;
   volatile boolean listening = false;

   boolean[] hatPattern;
   boolean[] snarePattern;
   boolean[] kickPattern;

   volatile long interval = -1;
   int beatsElapsed = 0;

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
      //threadPool.execute(kickSample);
      //threadPool.execute(hatSample);
      //threadPool.execute(snareSample);
      
      listening = true;
   }

   public synchronized void setConfig(HashMap<String, String> pattern){
      // parse the information in the HashMap
      System.out.println("configuring drum machine for :" + pattern);
      interval = Long.parseLong(pattern.get("interval")); // time to wait between beats
      notifyAll();

      String hatString = pattern.get("hat");
      hatPattern = new boolean[hatString.length()];
      for (int i = 0; i < hatString.length(); i++){
         hatPattern[i] = Character.compare(hatString.charAt(i), 'x') == 0;}

      String snareString = pattern.get("snare");
      snarePattern = new boolean[snareString.length()];
      for (int i = 0; i < snareString.length(); i++){
         snarePattern[i] = Character.compare(snareString.charAt(i), 'x') == 0;}

      String kickString = pattern.get("kick");
      kickPattern = new boolean[kickString.length()];
      for (int i = 0; i < kickString.length(); i++){
         kickPattern[i] = Character.compare(kickString.charAt(i), 'x') == 0;}      
   }

   @Override
   public void run(){
      try {
         while (listening){
            synchronized(this){
               if(!looping){
                  wait();
               }
            }
            while (looping && interval > 0){
               System.out.println("beat");
               if (hatPattern[beatsElapsed % hatPattern.length]){
                  threadPool.execute(hatSample);
               }
               beatsElapsed++;
               Thread.sleep(interval);
            }
         }
      } catch (InterruptedException e){
         System.out.println("problem sleeping thread");
      }
   }

   public synchronized void keepLooping(){
      looping = true;
      notifyAll();
   }

   public synchronized void stopLooping(){
      looping = false;
      notifyAll();
   }

   public synchronized void stopListening(){
      listening = false;
      notifyAll();
   }
}