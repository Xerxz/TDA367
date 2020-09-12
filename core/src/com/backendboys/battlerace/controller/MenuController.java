package com.backendboys.battlerace.controller;

import com.backendboys.battlerace.view.screens.GameScreen;
import com.backendboys.battlerace.view.screens.MainMenu;
import com.backendboys.battlerace.view.screens.OptionsMenu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MenuController {
    private final Game game;
    private final MainMenu mainMenu = new MainMenu(this);
    private final OptionsMenu optionsMenu = new OptionsMenu(this);
    private final Music music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));

    public MenuController(Game game) {
        this.game = game;
        game.setScreen(mainMenu);
        music.play();
        music.setLooping(true);
    }

    public void playPressed() {
        music.stop();
        game.setScreen(new GameScreen());
    }

    public void multiPlayerPressed() {

    }

    public void exitPressed() {
        System.exit(0);
    }

    public void optionsPressed() {
        game.setScreen(optionsMenu);
    }

    public void backToMainMenuPressed() {
        game.setScreen(mainMenu);
    }

    public void soundOnPressed() {
        if (!music.isPlaying()) {
            music.play();
            music.setLooping(true);
        }
    }

    public void soundOffPressed() {
        if (music.isPlaying()) {
            music.stop();
        }
    }
}
