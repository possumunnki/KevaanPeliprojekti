package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Juz3 on 17.4.2017.
 */

public class Voodoo {

    private ObjectData vdObject;
    private Texture voodooTex;


    /**
     * Body template for voodoo doll enemy body
     * Draw-method compares bodies to this, if it's a match, draw bodies
     * Otherwise the userdata from ground and wall objects interferes this
     */
    private Body voodooBodyTemplate;


    private Body testBody1;
    private Body testBody2;
    private Body testBody3;

    private Body testBody4;
    private Body testBody5;
    private Body testBody6;

    private Body testBody7;
    private Body testBody8;


    public Voodoo(World world, MyGdxGame host) {


        voodooTex = new Texture(Gdx.files.internal("voodooNew.png"));

        vdObject = new ObjectData(voodooTex, 0.2f, 0.2f, ObjectData.GameObjectType.VOODOO);


        if(host.getCurrentStage() == 1 || host.getCurrentStage() == 2) {
            voodooBodyTemplate = createBody(5, 1, vdObject.width, vdObject.height, world);
            voodooBodyTemplate.setUserData(vdObject);

            // Voodoo 1-3
            testBody1 = createBody(7, 7, vdObject.width, vdObject.height, world);
            testBody1.setUserData(vdObject);

            testBody2 = createBody(11, 5, vdObject.width, vdObject.height, world);
            testBody2.setUserData(vdObject);

            testBody3 = createBody(18, 8, vdObject.width, vdObject.height, world);
            testBody3.setUserData(vdObject);

            // Voodoo 4-6
            testBody4 = createBody(20, 8, vdObject.width, vdObject.height, world);
            testBody4.setUserData(vdObject);

            testBody5 = createBody(24, 7, vdObject.width, vdObject.height, world);
            testBody5.setUserData(vdObject);

            testBody6 = createBody(29, 7, vdObject.width, vdObject.height, world);
            testBody6.setUserData(vdObject);

            // Voodoo 7-9
            testBody7 = createBody(32, 7, vdObject.width, vdObject.height, world);
            testBody7.setUserData(vdObject);

            testBody8 = createBody(35, 7, vdObject.width, vdObject.height, world);
            testBody8.setUserData(vdObject);
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


    public void dispose() {
        voodooTex.dispose();

    }

    public Body getVoodooBodyTemplate() {
        return voodooBodyTemplate;
    }

    public ObjectData getVdObject() {
        return vdObject;
    }
}