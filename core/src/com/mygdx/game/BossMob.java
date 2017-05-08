package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Juz3 on 7.5.2017.
 */

public class BossMob {

    private boolean shootFirst = false;

    private ObjectData bossObject;
    private Texture bossTexture;

    private ObjectData fireballObject;
    private Texture fireball;

    private Body bossBody;
    private Body fireballBody;
    private Vector2 bossLocation;

    private boolean bossRight = false;

    private float bossDensity = 150f;

    private float fireballDensity = 0.1f;

    Array<Body> fireballArray = new Array<Body>();

    private boolean shootOnce = false;


    public BossMob(World world, MyGdxGame host) {

        bossTexture = new Texture("boss1.png");
        fireball = new Texture("fireball.png");
        bossLocation = new Vector2(62f, 1.5f);

        bossObject = new ObjectData(bossTexture,
                bossTexture.getWidth() / 90f,
                bossTexture.getHeight() / 90f,
                ObjectData.GameObjectType.BOSS);

        fireballObject = new ObjectData(fireball,
                fireball.getWidth() / 100f,
                fireball.getHeight() / 100f,
                ObjectData.GameObjectType.FIREBALL);

        if(host.getCurrentStage() == 5) {

            bossBody = createBody(bossLocation.x,
                    bossLocation.y,
                    bossObject.width,
                    bossObject.height,
                    bossDensity,
                    world);

            bossBody.setUserData(bossObject);

        }
    }


    private Body createBody(float x, float y, float width, float height, float density, World
            world) {
        Body newBody = world.createBody(getDefinitionOfBody(x, y));
        newBody.createFixture(getFixtureDefinition(width, height, density));
        return newBody;
    }

    private BodyDef getDefinitionOfBody(float x, float y) {
        // Boss body definition
        BodyDef bossBodyDef = new BodyDef();

        // Set dynamic
        bossBodyDef.type = BodyDef.BodyType.DynamicBody;

        // Initial position is centered up
        // This position is shape's center.
        bossBodyDef.position.set(x, y);

        return bossBodyDef;
    }

    private FixtureDef getFixtureDefinition(float width, float height, float density) {

        FixtureDef FixDef = new FixtureDef();
        // Mass per square meter (kg^m2)
        FixDef.density = density;
        // How bouncy object? [0,1]
        FixDef.restitution = 0.1f;
        // How slipper object? [0,1]
        FixDef.friction = 0.02f;
        // Create polygon shape
        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox(width, height);
        // Add the shape to the fixture
        FixDef.shape = polyShape;

        return FixDef;
    }

    // 2nd fire method, gives fatal error
    /**
    public void fire(World world) {

        if(!shootOnce) {
            fireballBody = createBody(bossBody.getPosition().x,
                    bossBody.getPosition().y + 4f,
                    fireballObject.width / 2,
                    fireballObject.height / 2,
                    fireballDensity,
                    world);

            fireballBody.setFixedRotation(true);
            fireballBody.setUserData(fireballObject);

            fireballArray.add(fireballBody);

            Gdx.app.log("log", "array size " + fireballArray.size);

            //shootOnce = true;
        }
    }
     */

    public void bossWalk(World world) {

        //Gdx.app.log("log", "boss loc " + bossBody.getPosition().x);

        if(bossBody.getLinearVelocity().y == 0) {

            if(!bossRight) {
                bossBody.setLinearVelocity(-1.35f, 0);

                if(bossBody.getPosition().x < bossLocation.x - 5f) {
                    bossBody.setLinearVelocity(0, 0);
                    bossRight = true;

                    /**
                     * Crash-inducing fire method
                     */
                    //fire(world);
                }
            } else if(bossRight) {
                bossBody.setLinearVelocity(1.35f, 0);

                if(bossBody.getPosition().x < bossLocation.x - 0.11f) {
                    bossRight = false;
                }
            }
        }
    }

    // 1st fire method, gives fatal error
    /**
    public void shoot(World world) {

        fireballObject = new ObjectData(fireball, fireball.getWidth() / 130f,
                fireball.getHeight() / 130f, ObjectData.GameObjectType.FIREBALL);

        float fireballXpos = MathUtils.random(-2f, 2f);

        fireballBody = createBody(bossBody.getPosition().x + fireballXpos,
                bossBody.getPosition().y + 4f,
                fireballObject.width,
                fireballObject.height,
                world);
        //fireballBody.setBullet(true);

        fireballBody.setUserData(fireballObject);

        if(fireballBody.getPosition().y < 2f) {

            Gdx.app.log("log", "destroy 0y");
            world.destroyBody(fireballBody);
        }
    }
     */


    public ObjectData getBossObject() {
        return bossObject;
    }

    public ObjectData getFireballObject() {
        return fireballObject;
    }

    public void dispose() {
        bossTexture.dispose();
        fireball.dispose();
    }
}
