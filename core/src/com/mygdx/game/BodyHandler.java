package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Juz3 on 24.3.2017.
 */

public class BodyHandler {

    private Array<Body> bodies = new Array<Body>();
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

    private float windowWidth;
    private float windowHeight;

    public BodyHandler(World world, MyGdxGame host) {

        windowWidth = host.SCREEN_WIDTH;
        windowHeight = host.SCREEN_HEIGHT;

        voodooTex = new Texture(Gdx.files.internal("voodoo_alpha.png"));

        vdObject = new ObjectData(voodooTex, 0.2f, ObjectData.GameObjectType.VOODOO);

        voodooBodyTemplate = createBody(5, 5, vdObject.radius, world);
        voodooBodyTemplate.setUserData(vdObject);

        testBody1 = createBody(9, 10, vdObject.radius, world);
        testBody1.setUserData(vdObject);

        testBody2 = createBody(7, 7, vdObject.radius, world);
        testBody2.setUserData(vdObject);

        testBody3 = createBody(14, 5, vdObject.radius, world);
        testBody3.setUserData(vdObject);
    }

    private Body createBody(float x, float y, float radius, World world) {
        Body playerBody = world.createBody(getDefinitionOfPlayerBody(x, y));
        playerBody.createFixture(getFixtureDefinition(radius));
        return playerBody;
    }

    private BodyDef getDefinitionOfPlayerBody(float x, float y) {
        // Voodoo doll body definition
        BodyDef vdBodyDef = new BodyDef();

        // Set dynamic
        vdBodyDef.type = BodyDef.BodyType.DynamicBody;

        // Initial position is centered up
        // This position is the CENTER of the shape!
        vdBodyDef.position.set(x, y);

        return vdBodyDef;
    }

    private FixtureDef getFixtureDefinition(float radius) {
        FixtureDef vdFixDef = new FixtureDef();

        // Mass per square meter (kg^m2)
        vdFixDef.density = 1;

        // How bouncy object? [0,1]
        vdFixDef.restitution = 0.1f;

        // How slipper object? [0,1]
        vdFixDef.friction = 0.5f;

        // Create circle shape
        CircleShape circleshape = new CircleShape();
        circleshape.setRadius(radius);

        // Add the shape to the fixture
        vdFixDef.shape = circleshape;

        return vdFixDef;
    }

    public void drawAllBodies(SpriteBatch batch) {
        // DEBUG: Print total amount of bodies in the world
        //Gdx.app.log("", "Number of bodies: " + bodies.size);

        // Draw all bodies
        for (Body body : bodies) {

            // Draw all bodies with user data (ground is not drawn)
            if(body.getUserData().equals(voodooBodyTemplate.getUserData())) {

                // Get user data, has texture, type (bullet, or tennisball) and
                // radius
                ObjectData info = (ObjectData) body.getUserData();

                //Gdx.app.log("offscreen", "data " + body.getUserData().toString());

                //body.getUserData().toString()

                batch.draw(info.objectTexture,
                        body.getPosition().x - info.radius,
                        body.getPosition().y - info.radius,
                        info.radius,                   // originX
                        info.radius,                   // originY
                        info.radius * 2,               // windowWidth
                        info.radius * 2,               // windowHeight
                        1.0f,                          // scaleX
                        1.0f,                          // scaleY
                        body.getTransform().getRotation() * MathUtils.radiansToDegrees,
                        0,                             // Start drawing from x = 0
                        0,                             // Start drawing from y = 0
                        info.objectTexture.getWidth(),       // End drawing x
                        info.objectTexture.getHeight(),      // End drawing y
                        false,                         // flipX
                        false);                        // flipY

            }
        }
    }

    public void clearBodies(World world) {

        // If ball is off screen
        if(voodooBodyTemplate.getPosition().y < -1) {
            // Clear velocity (dropping of the screen)
            voodooBodyTemplate.setLinearVelocity(new Vector2(0,0));
            Gdx.app.log("offscreen", "ball Y-pos" + voodooBodyTemplate.getPosition().y);

            voodooBodyTemplate.setTransform(new Vector2(windowWidth / 2, windowHeight + vdObject
                    .radius*2), 0);
        }

        /**
         * Array list for bodies which are to be destroyed
         */
        Array<Body> bodiesToBeDestroyed = new Array<Body>();

        // Iterate all voodooDolls
        for (Body body : bodies) {
            // If it equals voodoo template
            if (body.getUserData().equals(voodooBodyTemplate.getUserData())) {
                ObjectData info = (ObjectData) body.getUserData();
                // If it is a voodoo doll, then mark it to be removed.
                // INSERT LIGHTDOLL SHOOT CHECK HERE
                if (info.type == ObjectData.GameObjectType.VOODOO) {
                    // INSERT LIGHTDOLL SHOOT CHECK HERE
                }
            }
        }

        // Destroy needed bodies
        for (Body body : bodiesToBeDestroyed) {
            world.destroyBody(body);
        }
    }

    public Array<Body> getBodies() {
        return bodies;
    }

    public void dispose() {
        voodooTex.dispose();
    }
}
