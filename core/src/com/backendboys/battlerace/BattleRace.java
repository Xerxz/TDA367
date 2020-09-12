package com.backendboys.battlerace;

import com.backendboys.battlerace.controller.GameController;
import com.backendboys.battlerace.controller.MenuController;
import com.backendboys.battlerace.view.screens.GameScreen;
import com.backendboys.battlerace.view.screens.MainMenu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class BattleRace extends Game {

    @Override
    public void create() {

        new GameController();
        new MenuController(this);

        // OBS: DO NOT PUT THIS HERE, THEN THE MENU INPUT WONT WORK! ONLY CHANGE WHEN IN-GAME!!
        //Gdx.input.setInputProcessor(gameController);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {

    }

}
