import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

/** heavily references the following websites: 
 *  https://www.codejava.net/coding/how-to-play-back-audio-in-java-with-examples
 *  https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/AudioInputStream.html
 * 
 */

public class Sample extends Thread implements LineListener {
    Clip audioClip;
    String originalURI;
    volatile boolean playing = false;
    AudioInputStream inputStream;

    volatile boolean listening = false;

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
            audioClip.addLineListener(this);
            audioClip.open(inputStream);
        } catch (LineUnavailableException e){
            System.out.println("LineUnavailableException for audioClip");
            e.printStackTrace();
        } catch (IllegalArgumentException e){
            System.out.println("When this error happened to me, I had to find a different snare sample. Each sample had a 705kbps bitrate during testing.");
            e.printStackTrace();
        } catch (IOException e){
            System.out.println("IOException initializing Sample");
        }
        // attach stream to the clip
        System.out.println("Successfully loaded sample " + uri);
        listening = true;
    }

    /** necessary for LineListener interface
     *  allows you to talk to the Line
     */
    @Override
    public synchronized void update(LineEvent event){
        LineEvent.Type type = event.getType();
        if (type == LineEvent.Type.STOP){
            playing = false;
            notifyAll();
        } else if (type == LineEvent.Type.CLOSE){
            System.out.println("registered a close event");
        } else if (type == LineEvent.Type.START){
            playing = true;
            notifyAll();
        }
    }

    @Override
    public void run(){
        try{
            while(listening){
                synchronized(this){
                    if (!playing){
                        wait();
                    }
                }
            // while loop necessary to close clip when done
                while (playing){
                    try{
                        Thread.sleep(1);
                        //System.out.println("sleeping");
                    } catch (InterruptedException e){
                        System.out.println("Error sleeping thread");
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e){
            System.out.println("problem sleeping thread");
            e.printStackTrace();
        }
    }

    public synchronized void play(){
        if (!playing){
            audioClip.setFramePosition(0);
            audioClip.start();}
        else {
            audioClip.stop();
            audioClip.setFramePosition(0);
            audioClip.start();
        }
        notifyAll();
    }

    public synchronized void stopPlaying(){
        audioClip.stop();
        notifyAll();
    }

    public synchronized void shutdown(){
        playing = false;
        listening = false;
        audioClip.close();
        notifyAll();
    }
}
