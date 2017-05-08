package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

    private Body testBody1;
    private Body testBody2;
    private Body testBody3;

    private Body testBody4;
    private Body testBody5;
    private Body testBody6;

    private Body testBody7;
    private Body testBody8;
    private Body testBody9;

    private boolean leftTurn = false;

    // STAGE 1 VOODOO POSITIONS
    private final int[]stage1X = {5, 7, 13, 18, 20, 24, 29, 32, 51};
    private final int[]stage1Y = {1, 2, 8, 8, 8, 7, 7, 7, 1};

    // STAGE 2 VOODOO POSITIONS
    private final int[]stage2X = {5, 7, 13, 18, 20, 24, 29, 32, 56};
    private final int[]stage2Y = {1, 2, 8, 8, 8, 7, 7, 7, 8};

    // STAGE 5 VOODOO POSITIONS
    private final float[]stage5X = {1.5f, 14, 26, 18, 48};
    private final float[]stage5Y = {2.5f, 4, 2, 2, 2};

    // insert here
    // and here

    private Array<Body> voodooBodyArray = new Array<Body>();

    public Voodoo(World world, MyGdxGame host) {

        voodooTex = new Texture(Gdx.files.internal("voodooNew.png"));
        vdObject = new ObjectData(voodooTex, 0.2f, 0.2f, ObjectData.GameObjectType.VOODOO);

        if(host.getCurrentStage() == 1 ||
                host.getCurrentStage() == 2 ||
                host.getCurrentStage() == 5) {


            if(host.getCurrentStage() == 1) {
                // Voodoo 1-3
                testBody1 = createBody(stage1X[0], stage1Y[0], vdObject.width, vdObject.height,
                        world);
                testBody1.setUserData(vdObject);

                testBody2 = createBody(stage1X[1], stage1Y[1], vdObject.width, vdObject.height,
                        world);
                testBody2.setUserData(vdObject);

                testBody3 = createBody(stage1X[2], stage1Y[2], vdObject.width, vdObject.height,
                        world);
                testBody3.setUserData(vdObject);
                // Voodoo 4-6
                testBody4 = createBody(stage1X[3], stage1Y[3], vdObject.width, vdObject.height,
                        world);
                testBody4.setUserData(vdObject);

                testBody5 = createBody(stage1X[4], stage1Y[4], vdObject.width, vdObject.height,
                        world);
                testBody5.setUserData(vdObject);

                testBody6 = createBody(stage1X[5], stage1Y[5], vdObject.width, vdObject.height,
                        world);
                testBody6.setUserData(vdObject);
                // Voodoo 7-9
                testBody7 = createBody(stage1X[6], stage1Y[6], vdObject.width, vdObject.height,
                        world);
                testBody7.setUserData(vdObject);

                testBody8 = createBody(stage1X[7], stage1Y[7], vdObject.width, vdObject.height,
                        world);
                testBody8.setUserData(vdObject);

                testBody9 = createBody(stage1X[8], stage1Y[8], vdObject.width, vdObject.height,
                        world);
                testBody9.setUserData(vdObject);

            } else if(host.getCurrentStage() == 2) {
                // Voodoo 1-3
                testBody1 = createBody(stage2X[0], stage2Y[0], vdObject.width, vdObject.height,
                        world);
                testBody1.setUserData(vdObject);

                testBody2 = createBody(stage2X[1], stage2Y[1], vdObject.width, vdObject.height,
                        world);
                testBody2.setUserData(vdObject);

                testBody3 = createBody(stage2X[2], stage2Y[2], vdObject.width, vdObject.height,
                        world);
                testBody3.setUserData(vdObject);
                // Voodoo 4-6
                testBody4 = createBody(stage2X[3], stage2Y[3], vdObject.width, vdObject.height,
                        world);
                testBody4.setUserData(vdObject);

                testBody5 = createBody(stage2X[4], stage2Y[4], vdObject.width, vdObject.height,
                        world);
                testBody5.setUserData(vdObject);

                testBody6 = createBody(stage2X[5], stage2Y[5], vdObject.width, vdObject.height,
                        world);
                testBody6.setUserData(vdObject);
                // Voodoo 7-9
                testBody7 = createBody(stage2X[6], stage2Y[6], vdObject.width, vdObject.height,
                        world);
                testBody7.setUserData(vdObject);

                testBody8 = createBody(stage2X[7], stage2Y[7], vdObject.width, vdObject.height,
                        world);
                testBody8.setUserData(vdObject);

                testBody9 = createBody(stage2X[8], stage2Y[8], vdObject.width, vdObject.height,
                        world);
                testBody9.setUserData(vdObject);

            } else if (host.getCurrentStage() == 5) {

                    // Voodoo 1-3
                    testBody1 = createBody(stage5X[0], stage5Y[0], vdObject.width, vdObject.height,
                            world);
                    testBody1.setUserData(vdObject);

                    testBody2 = createBody(stage5X[1], stage5Y[1], vdObject.width, vdObject.height,
                            world);
                    testBody2.setUserData(vdObject);

                    testBody3 = createBody(stage5X[2], stage5Y[2], vdObject.width, vdObject.height,
                            world);
                    testBody3.setUserData(vdObject);
                    // Voodoo 4-5
                    testBody4 = createBody(stage5X[3], stage5Y[3], vdObject.width, vdObject.height,
                            world);
                    testBody4.setUserData(vdObject);

                    testBody5 = createBody(stage5X[4], stage5Y[4], vdObject.width, vdObject.height,
                            world);
                    testBody5.setUserData(vdObject);

                }
            }

            if(host.getCurrentStage() == 1 || host.getCurrentStage() == 2) {
                voodooBodyArray.add(testBody1);
                voodooBodyArray.add(testBody2);
                voodooBodyArray.add(testBody3);
                voodooBodyArray.add(testBody4);
                voodooBodyArray.add(testBody5);
                voodooBodyArray.add(testBody6);
                voodooBodyArray.add(testBody7);
                voodooBodyArray.add(testBody8);
                voodooBodyArray.add(testBody9);
            } else if(host.getCurrentStage() == 5) {
                voodooBodyArray.add(testBody1);
                voodooBodyArray.add(testBody2);
                voodooBodyArray.add(testBody3);
                voodooBodyArray.add(testBody4);
                voodooBodyArray.add(testBody5);
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
        // This position is shape's center.
        vdBodyDef.position.set(x, y);

        return vdBodyDef;
    }

    private FixtureDef getFixtureDefinition(float width, float height) {
        FixtureDef vdFixDef = new FixtureDef();

        // Mass per square meter (kg^m2)
        vdFixDef.density = 1f;

        // How bouncy object? [0,1]
        vdFixDef.restitution = 0.4f;

        // How slipper object? [0,1]
        vdFixDef.friction = 0.05f;

        // Create polygon shape
        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox(width, height);

        // Add the shape to the fixture
        vdFixDef.shape = polyShape;

        return vdFixDef;
    }


    /**
     * Moves all voodoo doll bodies according to the last body in the map, so that
     * when the last one turns, all the other bodies turn too.
     */
    public void voodooWalk(MyGdxGame host) {

        for(Body body:voodooBodyArray) {

            //Gdx.app.log("log", "vd array size " + voodooBodyArray.size);

            // STAGE 1 DOLL MOVEMENT
            if(host.getCurrentStage() == 1 && body.getLinearVelocity().y == 0 ) {

                // set initial speed
                if(!leftTurn) {
                    body.setLinearVelocity(-1, 0);
                }
                // Move to opposite direction when too far from initial position
                if(testBody9.getPosition().x < (50 - 1)) {
                    leftTurn = true;
                    body.setLinearVelocity(2, 0);
                } else if(testBody9.getPosition().x > (50 + 1)) {
                    leftTurn = false;
                }

                // STAGE 2 DOLL MOVEMENT
            } else if(host.getCurrentStage() == 2 && body.getLinearVelocity().y == 0) {
                // set initial speed
                if(!leftTurn) {
                    body.setLinearVelocity(-1, 0);
                }
                // Move to opposite direction when too far from initial position
                if(testBody9.getPosition().x < (56 - 1)) {
                    leftTurn = true;
                    body.setLinearVelocity(2, 0);
                } else if(testBody9.getPosition().x > (56 + 1)) {
                    leftTurn = false;
                }

                // STAGE 5 DOLL MOVEMENT
            } else if (host.getCurrentStage() == 5 && body.getLinearVelocity().y == 0) {
                // set initial speed
                if(!leftTurn) {

                    //Gdx.app.log("log", "stage 5 vd move");
                    body.setLinearVelocity(-0.75f, 0);
                }
                // Move to opposite direction when too far from initial position
                if(testBody4.getPosition().x < (48 - 0.5f)) {
                    body.setLinearVelocity(0, 0);
                    leftTurn = true;

                }
                /**
                } else if(testBody4.getPosition().x > (48 + 0.5f)) {
                    leftTurn = false;
                }
                 */
            }
        }
    }

    public void dispose() {
        voodooTex.dispose();
    }

    public ObjectData getVdObject() {
        return vdObject;
    }
}
