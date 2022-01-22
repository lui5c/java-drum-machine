# java-drum-machine

A very simple, polyrhythm-oriented drum machine. Each sample's sequence is programmed individually and repeats at the end of the sequence. 

Sequences are programmed entirely in whole note rests or whole notes. If you want a complicated sequence with eighth or sixteenth note rests, you have to use a higher BPM and adjust the sequence accordingly. (BPM is sort of a misnomer for this type of sequencer - think of BPM as just being how many 'hits' per minute.)

For example, a half-time classic rock pattern would be written:

hat:    x
snare:  ooxo
kick:   xooo

x is a note, o is a rest. The hat plays every beat (1-note pattern). The kick plays on the 1 and the snare plays on the 3. 