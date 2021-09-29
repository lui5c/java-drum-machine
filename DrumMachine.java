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

   String hatpattern;
   String snarepattern;
   String kickpattern;
   long interval;

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
      threadPool.execute(kickSample);
      threadPool.execute(hatSample);
      threadPool.execute(snareSample);
      
      listening = true;
   }

   public void setConfig(HashMap<String, String> pattern){
      // parse the information in the HashMap
      System.out.println("configuring drum machine for :" + pattern);
      interval = Long.parseLong(pattern.get("interval")); // time to wait between beats
      String hatPattern = pattern.get("hat");
      String snarePattern = pattern.get("snare");
      String kickPattern = pattern.get("kick");
      System.out.println("hat repeats every " + hatPattern.length() + " beats, snare repeats every " + snarePattern.length() + " beats, kick repeats every " + kickPattern.length() + " beats, ns between beats is " + interval);
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
            while (looping){
               System.out.println("playing");
               Thread.sleep(1000);
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