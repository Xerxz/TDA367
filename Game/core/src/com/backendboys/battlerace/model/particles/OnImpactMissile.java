package com.backendboys.battlerace.model.particles;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * A Missile that explodes on impact created in ParticleHandler
 */
class OnImpactMissile extends AbstractExplosive implements IParticle {

    private Body body;
    private static final Vector2 DIRECTION = new Vector2(10, 4);
    private static final float MOVEMENT_POWER = 100;
    private static final float MISSILE_LENGTH = 10f;
    private static final float MISSILE_HEIGHT = 5f;
    private static final float LAUNCH_OFFSET_Y = 10f;
    private static final float LAUNCH_OFFSET_X = 41.5f;
    private static final int NUM_PARTICLES = 42;

    /**
     * constructor - creates the Missile
     *
     * @param world the world where it spawns
     * @param pos   spawn point of the missile
     */
    OnImpactMissile(World world, Vector2 pos, float rotation, Vector2 initialVelocity) {
        createBody(world, pos, rotation, initialVelocity);
    }

    private void createBody(World world, Vector2 pos, float rotation, Vector2 initialVelocity) {
        rotation = (float) (rotation + Math.PI / 2);
        DIRECTION.x = MathUtils.sin(rotation);
        DIRECTION.y = -MathUtils.cos(rotation);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = false;
        bodyDef.bullet = false;
        bodyDef.linearDamping = 0;
        bodyDef.gravityScale = 1f;

        if (DIRECTION.x < 0) {
            bodyDef.position.x = pos.x - LAUNCH_OFFSET_X;
        } else {
            bodyDef.position.x = pos.x + LAUNCH_OFFSET_X;
        }
        bodyDef.position.y = pos.y + LAUNCH_OFFSET_Y;

        DIRECTION.scl(MOVEMENT_POWER);
        bodyDef.linearVelocity.x = DIRECTION.x + initialVelocity.x;
        bodyDef.linearVelocity.y = DIRECTION.y + initialVelocity.y;
        body = world.createBody(bodyDef);
        body.setTransform(body.getPosition(), rotation - (MathUtils.PI / 2));

        body.getMassData().center.x = 8f;
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(MISSILE_LENGTH, MISSILE_HEIGHT);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 100;
        fixtureDef.friction = 100;
        fixtureDef.restitution = 0;
        fixtureDef.filter.groupIndex = -1; // makes particles unable to collide with one another
        body.createFixture(fixtureDef).setUserData(this);
        polygonShape.dispose();
    }

    Body getBody() {
        return body;
    }

    static int getNumParticles() {
        return NUM_PARTICLES;
    }

    /**
     * After the Missile collides we give it permission to be removed -
     * from the world and the ParticleHandler class
     */
    @Override
    public void explosiveCollided() {
        setToBeRemoved(true);
    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public float getRotation() {
        return body.getAngle();
    }
}
