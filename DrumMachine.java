/**
 *  Help with javax.sound.sampled.Clip provided by:
 *  https://www.codejava.net/coding/how-to-play-back-audio-in-java-with-examples
 */
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

 public class DrumMachine {

    public static Clip getClipFromPath(String uri){
        // helper function to load each file and return a Clip object
        // if an exception is thrown, print error and return null
        System.out.println("Loading " + uri);
        File sample = new File(uri);
        try {
            // set up input strem
            AudioInputStream stream = AudioSystem.getAudioInputStream(sample);
            // get format
            AudioFormat format = stream.getFormat();
            // set up DataLine
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            // return the Clip
            return (Clip) AudioSystem.getLine(info);
        } catch (UnsupportedAudioFileException e){
            System.out.println("Unsupported file type - this was only tested with .wav files");
            e.printStackTrace();
            return null;
        } catch (LineUnavailableException e){
            System.out.println("Error connecting to the audio line for playback.");
            e.printStackTrace();
            return null;
        } catch (IOException e){
            System.out.println("Error playing file. IOException");
            e.printStackTrace();
            return null;
        }
    }
     
     public static void main(String[] args){
        String kick_path = "assets/sounds/kick.wav";
        String snare_path = "assets/sounds/snare.wav";
        String hat_path = "assets/sounds/hat.wav";
        
        Clip kickClip = getClipFromPath(kick_path);
        if (kickClip != null){
            System.out.println("Clip successfully loaded :)");
        } else {
            System.out.println("Clip didn't load :(");
        }
     }
    }