package com.example.will_hero;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sounds {
    private static final String SWOOSH = "sounds/swoosh_sound.wav";
    private static final String DEAD = "sounds/dead.mp3";
    private static final String COIN = "sounds/coin.wav";

    private static void play(String file) {
        try {
            Media sound = new Media(WillHeroApplication.class.getResource(file).toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setVolume(0.3);
            mediaPlayer.play();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        catch (Error e) {
            System.out.println(e.getMessage());
        }

    }

    public static void swoosh() {
        play(SWOOSH);
    }
    public static void dead(){
        play(DEAD);
    }
    public static void coin(){
        play(COIN);
    }
}
