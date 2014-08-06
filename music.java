/*-----------------------------------------------------------------------------------------------------------------|
 * -------------------------------------------- MIDI File Player --------------------------------------------------|
 * ------------------------------------------ Created by Conrad Lin -----------------------------------------------|
 * ------------------------------- For all projects associated with Conrad Lin ------------------------------------|
 * -------------------------------------------- DO NOT DISTRUBUTE -------------------------------------------------|
 * ---------------------------------------------------------------------------------------------------------------*/

import java.io.*;
import javax.sound.midi.*;
   
public class music {
   private static Sequencer midiPlayer;
   
   // testing main method
   public static void main(String[] args) {
      startMidi("menuscreenmusic.mid");     // start the midi player
      try {
         Thread.sleep(5000);   // delay
      } catch (InterruptedException e) { }
      System.out.println("faster");
      midiPlayer.setTempoFactor(2.0F);   // >1 to speed up the tempo
      try {
         Thread.sleep(5000);   // delay
      } catch (InterruptedException e) { }
   
      // Do this on every move step, you could change to another song
      if (midiPlayer.isRunning()) {  // previous song finished
         // reset midi player and start a new song
         midiPlayer.stop();
         midiPlayer.close();
         startMidi("battlemusic.mid");
      }
   }
   
   public static void startMidi(String midFilename) {
      try {
         File midiFile = new File(midFilename);
         Sequence song = MidiSystem.getSequence(midiFile);
         midiPlayer = MidiSystem.getSequencer();
         midiPlayer.open();
         midiPlayer.setSequence(song);
         midiPlayer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY); // repeat 0 times (play once)
         midiPlayer.start();
         
         
      } catch (MidiUnavailableException e) {
         e.printStackTrace();
      } catch (InvalidMidiDataException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
    
   public static void stopMidi(){
     midiPlayer.stop();
     midiPlayer.close();
   }
}