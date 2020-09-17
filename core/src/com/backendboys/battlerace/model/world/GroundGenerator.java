package com.backendboys.battlerace.model.world;

import com.backendboys.battlerace.model.GameModel;
import com.backendboys.battlerace.model.powerups.AbstractPowerUp;
import com.backendboys.battlerace.model.powerups.MissilePowerUp;
import com.backendboys.battlerace.model.powerups.NitroPowerUp;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.Random;

public class GroundGenerator {

    private final int numberVertices;
    private final double step;
    private final float friction;
    private final ArrayList<Vector2> vertices;
    private final static float SHAPE_HEIGHT = 0.5f;

    public GroundGenerator(int numberVertices, double step, int friction) {
        this.numberVertices = numberVertices;
        this.step = step;
        this.friction = friction;
        vertices = new ArrayList<>();
    }

    public void generateGround(World world) {
        generateVertices();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = friction;

        for (int i = 0; i < vertices.size(); i++) {
            PolygonShape shape = new PolygonShape();
            if (i + 1 < vertices.size()) {
                shape.setAsBox(calculateDistance(vertices.get(i), vertices.get(i + 1)), SHAPE_HEIGHT);
                fixtureDef.shape = shape;
                Body ground = world.createBody(bodyDef);
                ground.createFixture(fixtureDef);
                float rads = calculateAngle(vertices.get(i), vertices.get(i + 1));
                ground.setTransform(vertices.get(i).x, vertices.get(i).y, rads);
            }
            shape.dispose();
        }
    }

    private void generateVertices() {
        float xPos = 0, yPos = 0;
        for (int i = 0; i < numberVertices; i++) {
            yPos = (10 * (float) Math.sin(xPos * 0.1)) + 10;
            xPos += step;
            vertices.add(new Vector2(xPos, yPos + 10));
        }
    }

    private float calculateDistance(Vector2 vec, Vector2 vec2) {
        return (float) (Math.sqrt(Math.pow(((vec2.y - vec.y) / 2), 2) + Math.pow(((vec2.x - vec.x) / 2), 2)));
    }

    private float calculateAngle(Vector2 vec, Vector2 vec2) {
        double value = (double) (vec2.y - vec.y) / (double) (vec2.x - vec.x);
        double aTan = Math.atan(value);
        double degrees = Math.toDegrees(aTan);
        return (float) (Math.toRadians(degrees));
    }

    public ArrayList<Vector2> getVertices() {
        return vertices;
    }
}