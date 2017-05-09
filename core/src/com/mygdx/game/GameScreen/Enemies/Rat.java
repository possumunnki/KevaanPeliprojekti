package com.mygdx.game.GameScreen.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameScreen.Enemies.ObjectData;
import com.mygdx.game.MyGdxGame;

/**
 * Created by Juz3 on 17.4.2017.
 */

public class Rat {

    private ObjectData ratObject;
    private Texture ratTex;

    private Body ratTemplate;
    boolean ratRight = false;

    private Body ratBody2;


    public Rat(World world, MyGdxGame host) {

        ratTex = new Texture(Gdx.files.internal("rat256.png"));

        ratObject = new ObjectData(ratTex, 1.2f, 0.5f, ObjectData.GameObjectType.RAT);


        if(host.getCurrentStage() == 1 || host.getCurrentStage() == 2) {
            // Rat bodies
            ratTemplate = createBody(12.9f, 3, ratObject.width, ratObject.height, world);
            ratTemplate.setUserData(ratObject);

            ratBody2 = createBody(30, 3, ratObject.width, ratObject.height, world);
            ratBody2.setUserData(ratObject);
        }


    }

    private Body createBody(float x, float y, float width, float height, World world) {
        Body newBody = world.createBody(getDefinitionOfBody(x, y));
        newBody.createFixture(getFixtureDefinition(width, height));
        return newBody;
    }

    private BodyDef getDefinitionOfBody(float x, float y) {
        // Voodoo doll body definition
        BodyDef vdBodyDef = new BodyDef();

        // Set dynamic
        vdBodyDef.type = BodyDef.BodyType.DynamicBody;

        // Initial position is centered up
        // This position is the CENTER of the shape!
        vdBodyDef.position.set(x, y);

        return vdBodyDef;
    }

    private FixtureDef getFixtureDefinition(float width, float height) {
        FixtureDef vdFixDef = new FixtureDef();

        // Mass per square meter (kg^m2)
        vdFixDef.density = 1;

        // How bouncy object? [0,1]
        vdFixDef.restitution = 0.4f;

        // How slipper object? [0,1]
        vdFixDef.friction = 0.01f;

        // Create polygon shape
        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox(width, height);

        // Add the shape to the fixture
        vdFixDef.shape = polyShape;

        return vdFixDef;
    }


    public void ratWalk() {


        if(ratTemplate.getLinearVelocity().y == 0) {

            if(!ratRight) {
                ratTemplate.setLinearVelocity(2f, 0);
                //Gdx.app.log("log", "rat1 x " + ratTemplate.getPosition().x);

                if(ratTemplate.getPosition().x > 20.5f) {
                    ratTemplate.setLinearVelocity(0, 0);
                    ratRight = true;
                }
            } else if(ratRight) {
                ratTemplate.setLinearVelocity(-2f, 0);
                //Gdx.app.log("log", "rat1 x " + ratTemplate.getPosition().x);

                if(ratTemplate.getPosition().x < 15.0f) {
                    ratRight = false;
                }
            }
        }

        if(ratBody2.getLinearVelocity().y == 0) {

            if(!ratRight) {
                ratBody2.setLinearVelocity(2f, 0);
                //Gdx.app.log("log", "rat2 x " + ratBody2.getPosition().x);

                if(ratBody2.getPosition().x > 40.5f) {
                    ratBody2.setLinearVelocity(0, 0);
                    ratRight = true;
                }
            } else if(ratRight) {
                ratBody2.setLinearVelocity(-2f, 0);
                //Gdx.app.log("log", "rat2 x " + ratBody2.getPosition().x);

                if(ratBody2.getPosition().x < 35.0f) {
                    ratRight = false;
                }
            }
        }
    }

    public ObjectData getRatObject() {
        return ratObject;
    }


    public Body getRatBody2() {
        return ratBody2;
    }

    public void dispose() {
        ratTex.dispose();

    }
}
