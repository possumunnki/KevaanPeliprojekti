package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Juz3 on 24.3.2017.
 */

public class BodyHandler {

    private Array<Body> bodies = new Array<Body>();
    private ObjectData vdObject;
    private Texture voodooTex;

    private ObjectData ratObject;
    private Texture ratTex;

    private Body ratTemplate;
    boolean ratRight = false;

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
    private Body testBody9;

    private Body ratBody2;

    private float windowWidth;
    private float windowHeight;

    public BodyHandler(World world, MyGdxGame host) {

        windowWidth = host.SCREEN_WIDTH;
        windowHeight = host.SCREEN_HEIGHT;

        voodooTex = new Texture(Gdx.files.internal("voodoo_alpha.png"));

        ratTex = new Texture(Gdx.files.internal("rat256.png"));

        vdObject = new ObjectData(voodooTex, 0.2f, 0.2f, ObjectData.GameObjectType.VOODOO);
        ratObject = new ObjectData(ratTex, 1.2f, 0.5f, ObjectData.GameObjectType.RAT);

        voodooBodyTemplate = createBody(1, 1, vdObject.width, vdObject.height, world);
        voodooBodyTemplate.setUserData(vdObject);

        // Voodoo 1-3
        testBody1 = createBody(7, 7, vdObject.width, vdObject.height, world);
        testBody1.setUserData(vdObject);

        testBody2 = createBody(9, 5, vdObject.width, vdObject.height, world);
        testBody2.setUserData(vdObject);

        testBody3 = createBody(12, 7, vdObject.width, vdObject.height, world);
        testBody3.setUserData(vdObject);

        // Voodoo 4-6
        testBody4 = createBody(15, 7, vdObject.width, vdObject.height, world);
        testBody4.setUserData(vdObject);

        testBody5 = createBody(17, 7, vdObject.width, vdObject.height, world);
        testBody5.setUserData(vdObject);

        testBody6 = createBody(20, 7, vdObject.width, vdObject.height, world);
        testBody6.setUserData(vdObject);

        // Voodoo 7-9
        testBody7 = createBody(24, 7, vdObject.width, vdObject.height, world);
        testBody7.setUserData(vdObject);

        testBody8 = createBody(29, 7, vdObject.width, vdObject.height, world);
        testBody8.setUserData(vdObject);

        testBody9 = createBody(32, 7, vdObject.width, vdObject.height, world);
        testBody9.setUserData(vdObject);


        // Rat bodies
        ratTemplate = createBody(12.9f, 3, ratObject.width, ratObject.height, world);
        ratTemplate.setUserData(ratObject);

        ratBody2 = createBody(30, 3, ratObject.width, ratObject.height, world);
        ratBody2.setUserData(ratObject);
    }

    private Body createBody(float x, float y, float width, float height, World world) {
        Body playerBody = world.createBody(getDefinitionOfBody(x, y));
        playerBody.createFixture(getFixtureDefinition(width, height));
        return playerBody;
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

    public void drawAllBodies(SpriteBatch batch, Player player) {
        // DEBUG: Print total amount of bodies in the world
        //Gdx.app.log("", "Number of bodies: " + bodies.size);

        // Draw all bodies
        for (Body body : bodies) {

            // Draw all bodies with  voodoo doll user data (ground is not drawn)
            if(body.getUserData().equals(voodooBodyTemplate.getUserData())) {

                // Get user data, has texture, enemy type and
                // radius
                ObjectData voodoo = (ObjectData) body.getUserData();

                batch.draw(voodoo.objectTexture,
                        body.getPosition().x - voodoo.width,
                        body.getPosition().y - voodoo.width,
                        voodoo.width,                   // originX
                        voodoo.width,                   // originY
                        voodoo.width * 2,               // windowWidth
                        voodoo.width * 2,               // windowHeight
                        1.0f,                          // scaleX
                        1.0f,                          // scaleY
                        body.getTransform().getRotation() * MathUtils.radiansToDegrees,
                        0,                             // Start drawing from x = 0
                        0,                             // Start drawing from y = 0
                        voodoo.objectTexture.getWidth(),       // End drawing x
                        voodoo.objectTexture.getHeight(),      // End drawing y
                        false,                         // flipX
                        false);                        // flipY

                /**
                if(player.getPlayerBody().getPosition().x >
                        (body.getPosition().x - 0.2f) &&
                        player.getPlayerBody().getPosition().x <
                                (body.getPosition().x + 0.2f) &&
                        player.getPlayerBody().getPosition().y >
                                (body.getPosition().y - 0.2f) &&
                        player.getPlayerBody().getPosition().y <
                                (body.getPosition().y + 0.2f)) {

                    Gdx.app.log("log", "mob collision");
                }
                */
            } else if (body.getUserData().equals(ratTemplate.getUserData())) {
                ObjectData rat = (ObjectData) body.getUserData();

                batch.draw(rat.objectTexture,
                        body.getPosition().x - rat.width,
                        body.getPosition().y - rat.height,
                        rat.width,                   // originX
                        rat.height,                   // originY
                        rat.width * 2,               // windowWidth
                        rat.height * 2,               // windowHeight
                        1.0f,                          // scaleX
                        1.0f,                          // scaleY
                        body.getTransform().getRotation() * MathUtils.radiansToDegrees,
                        0,                             // Start drawing from x = 0
                        0,                             // Start drawing from y = 0
                        rat.objectTexture.getWidth(),       // End drawing x
                        rat.objectTexture.getHeight(),      // End drawing y
                        false,                         // flipX
                        false);                        // flipY
            }
        }
    }

    public void clearBodies(World world, LightDoll lightDoll) {

        Array<Body> bodiesToBeDestroyed = new Array<Body>();

        for (Body body : bodies) {
            // If it equals voodoo template
            if (body.getUserData().equals(voodooBodyTemplate.getUserData())) {

                if (body.getPosition().y < -1) {
                    // Clear velocity (dropping of the screen)
                    body.setLinearVelocity(new Vector2(0, 0));
                    Gdx.app.log("offscreen", "ball Y-pos" + body.getPosition().y);

                    bodiesToBeDestroyed.add(body);
                }
            }
        }

        // If voodoo template is off screen
        if(voodooBodyTemplate.getPosition().y < -1) {
            // Clear velocity (dropping of the screen)
            voodooBodyTemplate.setLinearVelocity(new Vector2(0,8));
            Gdx.app.log("offscreen", "ball Y-pos" + voodooBodyTemplate.getPosition().y);

            voodooBodyTemplate.setTransform(new Vector2(windowWidth / 2, windowHeight + vdObject
                    .width*2), 0);
        }

        /**
         * Array list for bodies which are to be destroyed
         */


        // Iterate all voodooDolls
        for (Body body : bodies) {
            // If it equals voodoo template
            if (body.getUserData().equals(voodooBodyTemplate.getUserData())) {
                ObjectData info = (ObjectData) body.getUserData();
                // If it is a voodoo doll, then mark it to be removed.
                if (info.type == ObjectData.GameObjectType.VOODOO) {

                    // Check when the light doll is near enemy body
                    if(lightDoll.getLightDollBody().getPosition().x >
                            (body.getPosition().x - 0.5f) &&
                            lightDoll.getLightDollBody().getPosition().x <
                                    (body.getPosition().x + 0.3f) &&
                            lightDoll.getLightDollBody().getPosition().y >
                                    (body.getPosition().y - 0.3f) &&
                            lightDoll.getLightDollBody().getPosition().y <
                                    (body.getPosition().y + 0.4f)) {

                        // Add the specific body to bodiesToBeDestroyed-list
                        bodiesToBeDestroyed.add(body);
                    }
                }
            }
        }

        // Destroy needed bodies
        for (Body body : bodiesToBeDestroyed) {
            world.destroyBody(body);
            Gdx.app.log("log: ", "destroyed body x: " + body.getPosition().x);
            Gdx.app.log("log: ", "total body count: " + bodies.size);
        }
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

    public Array<Body> getBodies() {
        return bodies;
    }

    public Body getVoodooBodyTemplate() {
        return voodooBodyTemplate;
    }

    public void dispose() {
        voodooTex.dispose();
        ratTex.dispose();
    }
}
