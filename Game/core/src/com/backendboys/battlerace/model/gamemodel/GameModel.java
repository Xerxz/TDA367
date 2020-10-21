package com.backendboys.battlerace.model.gamemodel;

import com.backendboys.battlerace.model.gamemodel.opponent.OpponentPlayer;
import com.backendboys.battlerace.model.gamemodel.particles.IParticle;
import com.backendboys.battlerace.model.gamemodel.particles.WorldExplosions;
import com.backendboys.battlerace.model.gamemodel.player.Player;
import com.backendboys.battlerace.model.gamemodel.powerups.IPowerUp;
import com.backendboys.battlerace.model.gamemodel.powerups.PowerUpGenerator;
import com.backendboys.battlerace.model.gamemodel.world.GameWorld;
import com.backendboys.battlerace.model.gamemodel.world.ground.GroundStrategyFactory;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The model class, contains data and logic to the game.
 * Contains player and all other objects that exists in a game.
 */
public class GameModel {

    private static final int SPACE_BETWEEN_POWERUPS = 300;

    private final GameWorld gameWorld;
    private final Player player;
    private final List<OpponentPlayer> opponentPlayers = Collections.synchronizedList(new ArrayList<OpponentPlayer>());
    private final WorldExplosions worldExplosions;
    private List<IPowerUp> powerUps = new ArrayList<>();

    /**
     * Instantiates a GameModel, which creates a world and generates GameObjects, such as,
     * player, ground, powerups, and finishline.
     */
    public GameModel() {
        this.gameWorld = new GameWorld(GroundStrategyFactory.getSinCosStrategy(5000, 60, 5), 1);
        worldExplosions = new WorldExplosions();
        generateObjects();
        gameWorld.addListener(worldExplosions);
        Vector2 startPosition = gameWorld.getGroundVertices().get(50);
        player = new Player("You");
        player.addVehicle(gameWorld.getWorld(), startPosition.x, startPosition.y + 25);
        gameWorld.setCollisionListener(new CollisionListener(this));
    }

    private void generateObjects() {
        PowerUpGenerator powerUpGenerator = new PowerUpGenerator(gameWorld.getGroundVertices(), gameWorld.getWorld(), worldExplosions);
        powerUps = powerUpGenerator.generatePowerups(amountOfPowerUps());

        for (IPowerUp powerUp : powerUps) {
            gameWorld.addListener(powerUp);
        }


    }

    private int amountOfPowerUps() {
        int numberOfPowerUps = 0;
        int i = gameWorld.getGroundVertices().size();
        while (i > SPACE_BETWEEN_POWERUPS) {
            i -= SPACE_BETWEEN_POWERUPS;
            numberOfPowerUps++;
        }
        return numberOfPowerUps;
    }

    /**
     * Adds opponent to list of opponents
     * @param opponent
     */
    public void addOpponent(OpponentPlayer opponent) {
        opponentPlayers.add(opponent);
    }

    /**
     * Removes opponent from list of opponents
     * @param name the name of opponent removed
     */
    public void removeOpponent(String name) {
        synchronized (opponentPlayers) {
            for (OpponentPlayer p : opponentPlayers) {
                if (p.getPlayerName().equals(name)) {
                    opponentPlayers.remove(p);
                }
            }
        }
    }

    /**
     * Updates the opponent position
     * @param name of opponent
     * @param x x position
     * @param y y position
     * @param rotation rotation of opponent
     */
    public void updateOpponentPosition(String name, float x, float y, float rotation) {
        for (OpponentPlayer o : opponentPlayers) {
            if (o.getPlayerName().equalsIgnoreCase(name)) {
                o.setVectorPosition(new Vector2(x, y), rotation);
            }
        }
    }

    /**
     * Removes the PowerUp from the game and from the world.
     *
     * @param powerUp The PowerUp to remove.
     */
    public void removePowerUp(IPowerUp powerUp) {
        powerUp.remove();

        powerUps.remove(powerUp);
    }

    /**
     * Makes the Player gas.
     */
    public void gas() {
        player.gas();
    }

    /**
     * Makes the Player rotate to the left.
     */
    public void rotateLeft() {
        player.rotateLeft();
    }

    /**
     * Makes the Player brake.
     */
    public void brake() {
        player.brake();
    }

    /**
     * Makes the Player rotate to the right.
     */
    public void rotateRight() {
        player.rotateRight();
    }

    /**
     * Returns the GameWorld.
     *
     * @return The GameWorld object.
     */
    public GameWorld getGameWorld() {
        return gameWorld;
    }

    /**
     * Returns the Player.
     *
     * @return The Player object.
     */
    public Player getPlayer() {
        return player;
    }

    public Vector2 getPlayerPosition() {
        return player.getPosition();
    }

    public float getPlayerRotation() {
        return player.getRotation();
    }

    public List<OpponentPlayer> getOpponents() {
        return opponentPlayers;
    }

    public ArrayList<IParticle> getMissiles() {
        return worldExplosions.getMissiles();
    }

    public ArrayList<IParticle> getExplosionParticles() {
        return worldExplosions.getParticles();
    }

    public WorldExplosions getWorldExplosions() {
        return worldExplosions;
    }

    /**
     * Method that tells the player to use a powerup
     */
    public void usePowerUp() {
        player.usePowerUp();
    }


    /**
     * Spawns a missile in the world at a given position and rotation.
     *
     * @param x            The spawn x position.
     * @param y            The spawn y position.
     * @param rotation     The rotation.
     * @param playerXSpeed The x-axis speed of the player spawning the missile.
     * @param playerYSpeed The y-axis speed of the player spawning the missile.
     */
    public void spawnMissile(float x, float y, float rotation, float playerXSpeed, float playerYSpeed, boolean notifyListeners) {
        worldExplosions.addMissile(new Vector2(x, y), gameWorld.getWorld(), rotation, new Vector2(playerXSpeed, playerYSpeed), notifyListeners);
    }

    /**
     * Returns the list of PowerUps.
     *
     * @return The PowerUps list.
     */
    public List<IPowerUp> getPowerUps() {
        return powerUps;
    }

    public List<Vector2> getFinishLineVertices() {
        return gameWorld.getFinishLineVertices();
    }

}
