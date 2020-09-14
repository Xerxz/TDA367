package com.backendboys.battlerace.menu.view.screens;

import com.backendboys.battlerace.game.controller.GameController;
import com.backendboys.battlerace.game.model.IModelListener;
import com.backendboys.battlerace.game.view.BackgroundGenerator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.HashMap;

class GameScreen extends AbstractScreen implements IScreen, IModelListener {

    private final GameController gameController;

    private final BackgroundGenerator backgroundGenerator;

    private final OrthographicCamera camera;
    private final ExtendViewport viewport;
    private final Box2DDebugRenderer debugRenderer;

    private TextureAtlas textureAtlas;
    private SpriteBatch batch;
    private final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

    GameScreen(GameController gameController) {
        this.gameController = gameController;
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(200, 50, camera);
        batch = new SpriteBatch();
        debugRenderer = new Box2DDebugRenderer();
        backgroundGenerator = new BackgroundGenerator(camera, gameController.getGameWorld().getGroundVertices());
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        gameController.gameRendered();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.end();
        backgroundGenerator.renderBackground();
        debugRenderer.render(gameController.getGameWorld().getWorld(), camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void dispose() {
        textureAtlas.dispose();
        sprites.clear();
        gameController.getGameWorld().dispose();
        debugRenderer.dispose();
        backgroundGenerator.dispose();
    }

    @Override
    public void update() {
    }


}
