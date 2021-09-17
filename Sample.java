import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

/** heavily references the following websites: 
 *  https://www.codejava.net/coding/how-to-play-back-audio-in-java-with-examples
 * 
 * 
 */

public class Sample implements LineListener {
    Clip audioClip;
    String originalURI;
    boolean playing = false;
    AudioInputStream inputStream;

    public Sample (String uri) throws UnsupportedAudioFileException, IOException {
        // helper function to load each file and return a Clip object
        // if an exception is thrown, print error and return null
        System.out.println("Loading " + uri);
        File sample = new File(uri);
        originalURI = uri;
        // set up input strem
        inputStream = AudioSystem.getAudioInputStream(sample);
        // get format
        AudioFormat format = inputStream.getFormat();
        // set up DataLine
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        // setup Clip
        try {
            audioClip = (Clip) AudioSystem.getLine(info);
        } catch (LineUnavailableException e){
            e.printStackTrace();
        }
        
        
        
        // attach stream to the clip
        System.out.println("Successfully loaded sample " + uri);
        
    }

    /** necessary for LineListener interface
     *  allows you to talk to the Line
     */
    @Override
    public void update(LineEvent event){
        LineEvent.Type type = event.getType();
        System.out.println(event.toString());
        if (type == LineEvent.Type.STOP){
            playing = false;
        }
    }

    public void play(){
        System.out.println("Playing sample: " + originalURI);
        
        try {
            audioClip.addLineListener(this);
            audioClip.open(inputStream);
            playing = true;
            audioClip.start();
            // while loop necessary to close clip when done
            while (playing){
                try{
                    Thread.sleep(100);
                    //System.out.println("sleeping");
                } catch (InterruptedException e){
                    System.out.println("Error sleeping thread");
                    e.printStackTrace();
                }
            }
            audioClip.close();
        } catch (LineUnavailableException e){
            System.out.println("Line unavailable");
            e.printStackTrace();
        } catch (IOException e){
            System.out.println("IOException");
            e.printStackTrace();
        }
    }
}