package com.backendboys.battlerace.view.screens;


import com.backendboys.battlerace.controller.MenuController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * The MultiplayerMenu, here you can chose between create game or join game.
 */
class MultiplayerMenu extends AbstractMenuScreen implements IScreen {

    private final SpriteBatch batch;
    private Stage stage;

    MultiplayerMenu(MenuController menuController) {
        super(menuController);

        batch = new SpriteBatch();
    }

    /**
     * Shows the MultiplayerMenu and calls the method that add the buttons to the menu
     */
    @Override
    public void show() {
        super.show();

        stage = new Stage(getViewport(), batch);
        stage.addActor(createMultiplayerTable());

        Gdx.input.setInputProcessor(stage);

    }

    private Table createMultiplayerTable() {
        Table multiplayerTable = new Table();
        multiplayerTable.setFillParent(true);
        multiplayerTable.center();

        final ImageButton btnJoinGame = new ImageButton(getButtonStyleFromName("Join Game"));
        final ImageButton btnCreateGame = new ImageButton(getButtonStyleFromName("Create Game"));
        final ImageButton btnBack = new ImageButton(getButtonStyleFromName("Back"));

        btnJoinGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Start JoinGameScreen
            }
        });

        btnCreateGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Start CreateGameScreen
            }
        });

        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getMenuController().toMainMenu();
            }
        });

        return multiplayerTable;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        batch.setProjectionMatrix(super.getCamera().combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        super.dispose();
    }


}
