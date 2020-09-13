package com.backendboys.battlerace.view.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

abstract class AbstractMenuScreen extends AbstractScreen {

    private final SpriteBatch batch;
    private final Texture background;

    private final TextureAtlas menuTextureAtlas;
    private final Map<String, Sprite> menuSprites = new HashMap<>();

    protected AbstractMenuScreen() {
        batch = new SpriteBatch();
        background = new Texture("bg-100.jpg");

        menuTextureAtlas = new TextureAtlas("menusprites.txt");
        loadSprites();
    }

    private void loadSprites() {
        Array<TextureAtlas.AtlasRegion> regions = menuTextureAtlas.getRegions();

        for (TextureAtlas.AtlasRegion region : regions) {
            Sprite sprite = menuTextureAtlas.createSprite(region.name);

            menuSprites.put(region.name, sprite);
        }
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(background, 0, 0, getViewport().getWorldWidth(), getViewport().getWorldHeight());
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        batch.setProjectionMatrix(getCamera().combined);
    }

    @Override
    public void dispose() {
        super.dispose();
        menuTextureAtlas.dispose();
        background.dispose();
        menuSprites.clear();
    }

    protected Sprite getMenuSprite(String name) {
        return menuSprites.get(name);
    }
}
