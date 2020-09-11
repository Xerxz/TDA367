package com.backendboys.battlerace.view.screens;

import com.backendboys.battlerace.model.IModelListener;
import com.backendboys.battlerace.model.world.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.HashMap;

public class GameScreen extends AbstractScreen implements IModelListener {

    private GameWorld gameWorld;

    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private Box2DDebugRenderer debugRenderer;

    private TextureAtlas textureAtlas;
    private SpriteBatch batch;
    private final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

    public GameScreen() {
        gameWorld = new GameWorld();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(200, 50, camera);
        batch = new SpriteBatch();
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameWorld.stepWorld();
        batch.begin();
        batch.end();
        debugRenderer.render(gameWorld.getWorld(), camera.combined);
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
        gameWorld.dispose();
        debugRenderer.dispose();
    }

    @Override
    public void update() {
    }
}
