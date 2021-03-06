package com.backendboys.battlerace.view.screens;

import com.backendboys.battlerace.controller.GameController;
import com.backendboys.battlerace.controller.ServerController;
import com.backendboys.battlerace.model.GameModel;
import com.backendboys.battlerace.model.player.Player;
import com.backendboys.battlerace.model.world.GameWorld;
import com.backendboys.battlerace.view.game.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Class that handles rendering for the game
 */
class GameScreen extends AbstractScreen implements IGameScreen {

    private final boolean debugMode;
    private final GameWorld gameWorld;
    private final GameModel gameModel;
    private final GameController gameController;
    private ServerController serverController = null;

    private final OrthographicCamera camera;
    private final ExtendViewport viewport;
    private final SpriteBatch batch;

    private Box2DDebugRenderer debugRenderer;
    private final BackgroundRender backgroundRender;
    private final VehicleRender vehicleRender;
    private final OpponentRender opponentRender;
    private final PowerUpsRender powerUpsRender;
    private final MissileRender missileRender;
    private final ExplosionParticleRender explosionParticleRender;
    private final FinishLineRender finishLineRender;
    private final CurrentPowerUpRender currentPowerUpRender;
    private final PlayerPlacementRender playerPlacementRender;
    private final OpponentPlacementRender opponentPlacementRender;
    private final IdRender idRender;
    private final WinnerRender winnerRender;

    GameScreen(GameController gameController, boolean debugMode) {

        this.gameController = gameController;
        gameModel = gameController.getGameModel();
        gameWorld = gameModel.getGameWorld();

        this.debugMode = debugMode;
        if (this.debugMode) {
            debugRenderer = new Box2DDebugRenderer();
        }

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(600, 50, camera);

        backgroundRender = new BackgroundRender(camera, gameWorld.getGroundVertices());
        vehicleRender = new VehicleRender(camera);
        opponentRender = new OpponentRender(camera);
        missileRender = new MissileRender(camera);
        explosionParticleRender = new ExplosionParticleRender(camera);
        powerUpsRender = new PowerUpsRender(camera, gameModel.getPowerUps());
        finishLineRender = new FinishLineRender(camera, gameModel.getFinishLineVertices());
        currentPowerUpRender = new CurrentPowerUpRender(camera);
        playerPlacementRender = new PlayerPlacementRender(camera, gameWorld.getGroundVertices());
        opponentPlacementRender = new OpponentPlacementRender(camera, gameWorld.getGroundVertices());
        idRender = new IdRender(camera, gameController);
        winnerRender = new WinnerRender(camera, gameController);

        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        gameController.gameStepWorld();


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sendPositionPackets(gameModel.getPlayer());

        updateCameraPosition();

        renderData();
    }

    private void renderData() {
        backgroundRender.render(batch);
        vehicleRender.render(batch, gameModel.getPlayer().getVehicle());
        opponentRender.render(batch, gameModel.getOpponents());
        missileRender.render(batch, gameModel.getMissiles());
        explosionParticleRender.render(batch, gameModel.getExplosionParticles());
        powerUpsRender.render(batch);
        finishLineRender.render(batch);
        playerPlacementRender.render(batch, gameModel.getPlayer());
        opponentPlacementRender.render(batch, gameModel.getOpponents());
        idRender.render(batch);
        winnerRender.render(batch);
        try {
            currentPowerUpRender.render(batch, gameModel.getPlayer().getNextPowerUp());
        } catch (Exception e) {
            currentPowerUpRender.render(batch, null);
        }
        if (debugMode) {
            debugRenderer.render(gameWorld.getWorld(), camera.combined);
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        gameController.getGameWorld().dispose();
        backgroundRender.dispose();
        opponentRender.dispose();
        vehicleRender.dispose();
        missileRender.dispose();
        explosionParticleRender.dispose();
        powerUpsRender.dispose();
        finishLineRender.dispose();
        currentPowerUpRender.dispose();
        playerPlacementRender.dispose();
        opponentPlacementRender.dispose();
        idRender.dispose();
        if (debugMode) {
            debugRenderer.dispose();
        }
    }

    @Override
    public void setServerController(ServerController serverController) {
        this.serverController = serverController;
    }

    private void sendPositionPackets(Player player) {
        if (serverController != null) {
            if (serverController.isConnected()) {
                serverController.sendPositionPacket(player);
            }
        }
    }

    private void updateCameraPosition() {
        Vector2 playerPosition = gameModel.getPlayer().getPosition();
        camera.position.set(playerPosition.x + 200, camera.position.y, camera.position.z);
        camera.update();
    }
}
