package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;      //used to open audio file
    URL soundURL[] = new URL[30];   //used to store file path of sound files

    public Sound(){
        soundURL[0] = getClass().getResource("/sound/music.wav");
        soundURL[1] = getClass().getResource("/sound/powerup.wav");
        soundURL[2] = getClass().getResource("/sound/speak.wav");
        soundURL[3] = getClass().getResource("/sound/hitopp.wav");
        soundURL[4] = getClass().getResource("/sound/receivedamage.wav");
        soundURL[5] = getClass().getResource("/sound/burning.wav");
    }
    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);    //pass in sound to play
            clip = AudioSystem.getClip();
            clip.open(ais);
        }catch (Exception e){

        }
    }
    public void play(){
        clip.start();   //start sound
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);  //continue sound
    }
    public void stop(){
        clip.stop();    //stop sound
    }
}
