package com.backendboys.battlerace.model.powerups;

import com.backendboys.battlerace.model.particles.ParticleHandler;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class that handles creation of powerups.
 */
public class PowerUpGenerator {

    private static final int BASE_HEIGHT = 20;

    private final ArrayList<Vector2> vertices;
    private final World world;

    private static final int STEP = 5;
    private final ParticleHandler particleHandler;

    /**
     * @param vertices List of vertices in world to measure spawnpoints of powerups.
     * @param world    The world, needed to spawn the powerups in the world.
     */


    public PowerUpGenerator(ArrayList<Vector2> vertices, World world, ParticleHandler particleHandler) {

        this.vertices = vertices;
        this.world = world;
        this.particleHandler = particleHandler;
    }

    /**
     * Generates the power ups and spawns them in the world.
     *
     * @param numberPowerUps The number of powerups to create.
     * @return Returns a list of all powerups in world, needed for collision checking.
     */
    public ArrayList<IPowerUp> generatePowerUps(int numberPowerUps) {

        ArrayList<IPowerUp> powerUps = new ArrayList<>();

        if (numberPowerUps <= 0) {
            return powerUps;
        }

        final int space = vertices.size() / numberPowerUps;
        int positionX = space * 2;

        Random random = new Random();

        for (int i = 0; i < numberPowerUps; i++) {

            if ((positionX / 5) > vertices.size() - 1) {
                return powerUps;
            }

            Vector2 tempVector = vertices.get(positionX / STEP);
            int positionY = (int) tempVector.y + BASE_HEIGHT + random.nextInt(BASE_HEIGHT);

            if (random.nextBoolean()) {
                NitroPowerUp nitroPowerUp = new NitroPowerUp(world, positionX, positionY);
                powerUps.add(nitroPowerUp);

            } else {
                MissilePowerUp missilePowerUp = new MissilePowerUp(world, positionX, positionY, particleHandler);
                powerUps.add(missilePowerUp);
            }
            positionX += space * STEP;
        }

        return powerUps;
    }
}
