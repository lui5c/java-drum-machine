# java-drum-machine

A very simple, polyrhythm-oriented drum machine. Each sample's sequence is programmed individually and repeats at the end of the sequence. 

Sequences are programmed entirely in whole note rests or whole notes. If you want a complicated sequence with eighth or sixteenth note rests, you have to use a higher BPM and adjust the sequence accordingly. (BPM is sort of a misnomer for this type of sequencer - think of BPM as just being how many 'hits' per minute.) I have the most fun with it when I just switch my focus between each regularly-repeating pattern to them zoom out and listen to how they all play together. 

For example, a half-time classic rock pattern would be written:

hat:    x
snare:  ooxo
kick:   xooo

x is a note, o is a rest. The hat plays every beat (1-note pattern). The kick plays on the 1 and the snare plays on the 3. This is the default configuration, to test it, just press GO. To stop it, press STOP. You can't change the pattern while it is playing - you'll have to press STOP, change the pattern and BPM fields, and then press GO again. 

Some cool rhythms to try out:

Bossa Nova:
- hat:    oxxx
- snare:  oooxooxooxooxoox
- kick:   xxoo

Four against three:
- hat:    xxxo
- snare:  ooxo
- kick:   xoo

Five against four:
- hat:    xxxo
- snare:  o
- kick:   xoxxo

## How it works

Sample.java has the code for each "sample" - its job is to load the specified audio file, and to <code>play()</code> when it is told.

DrumMachine.java keeps track of each Sample and has the main loop that does the sequencing. It gets its configuration from GUI.java.

GUI.java allows the user to input configurations and start and stop the sequencer.

1. The GUI is run, which initializes a DrumMachine. The DrumMachine initializes 9 Samples, 3 for each sound. Multiple samples are prepared so that the decays of adjacent samples can overlap.

2. When the GUI's GO button is pressed, it passes its configuration to the DrumMachine, which then begins playing the correct Samples with the correct amount of time in between samples. 

3. When the GUI's STOP button is pressed, the DrumMachine stops looping, awaiting the next configuration it should play.

## How to run
<code>javac GUI.java</code>
<code>java GUI</code>