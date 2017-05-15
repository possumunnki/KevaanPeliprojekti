package fi.tamk.tiko.gitd.GameScreen.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import fi.tamk.tiko.gitd.MyGdxGame;

/**
 * Rat enemy class
 * Created by Juz3 on 17.4.2017.
 */

public class Rat {

    private ObjectData ratObject1;
    private ObjectData ratObject2;

    private Texture ratTex;
    private Texture ratTexFlip;

    private Body ratBody1;
    private Body ratBody2;

    private boolean ratRight1 = false;
    private boolean ratRight2 = false;

    /**
     * Constructs the rat enemy object.
     *
     * @param world is phys2d world object
     * @param host is extension of LibGdx Game-class.
     */
    public Rat(World world, MyGdxGame host) {

        ratTex = new Texture(Gdx.files.internal("rat256Flip.png"));
        ratTexFlip = new Texture(Gdx.files.internal("rat256.png"));

        ratObject1 = new ObjectData(ratTex, 1.2f, 0.5f, ObjectData.GameObjectType.RAT);
        ratObject2 = new ObjectData(ratTex, 1.2f, 0.5f, ObjectData.GameObjectType.RAT);

        if (host.getCurrentStage() == 1) {
            // Rat bodies
            ratBody1 = createBody(12.9f, 3, ratObject1.width, ratObject1.height, world);
            ratBody1.setUserData(ratObject1);

            ratBody2 = createBody(38, 1.5f, ratObject2.width, ratObject2.height, world);
            ratBody2.setUserData(ratObject2);
        } else if(host.getCurrentStage() == 2) {
            // Rat bodies
            ratBody1 = createBody(21f, 1.5f, ratObject1.width, ratObject1.height, world);
            ratBody1.setUserData(ratObject1);

            ratBody2 = createBody(48, 1.5f, ratObject2.width, ratObject2.height, world);
            ratBody2.setUserData(ratObject2);
        }
    }

    /**
     * Creates phys2d body.
     *
     * @param x x-position
     * @param y y-position
     * @param width X-size of the body
     * @param height Y-size of the body
     * @param world phys2d world object
     * @return returns this new body
     */
    private Body createBody(float x, float y, float width, float height, World world) {
        Body newBody = world.createBody(getDefinitionOfBody(x, y));
        newBody.createFixture(getFixtureDefinition(width, height));
        return newBody;
    }

    /**
     * creates phys2d related body definition.
     *
     * @param x center X-pos of body def
     * @param y center Y-pos of body def
     * @return returns this body definition
     */
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

    /**
     * Creates and returns fixture definition for a body.
     *
     * @param width the width of corresponding fixture definition
     * @param height the height of corresponding fixture definition
     * @return returns the fixdef
     */
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

    /**
     * Moves the rats in specified x positions, so that the rat turns when reached certain x-pos.
     */
    public void ratWalk(MyGdxGame host) {
        // STAGE 1 RAT MOVEMENT
        if(host.getCurrentStage() == 1) {
            if (ratBody1.getLinearVelocity().y == 0) {

                if (!ratRight1) {
                    ratBody1.setLinearVelocity(2f, 0);
                    //Gdx.app.log("log", "rat1 x " + ratBody1.getPosition().x);
                    if (ratBody1.getPosition().x > 20.5f) {
                        ratBody1.setLinearVelocity(0, 0);
                        ratRight1 = true;
                        ratObject1.objectTexture = ratTexFlip;
                    }
                } else if (ratRight1) {
                    ratBody1.setLinearVelocity(-2f, 0);
                    //Gdx.app.log("log", "rat1 x " + ratBody1.getPosition().x);

                    if (ratBody1.getPosition().x < 15.0f) {
                        ratRight1 = false;
                        ratObject1.objectTexture = ratTex;
                    }
                }
            }
            if (ratBody2.getLinearVelocity().y == 0) {

                if (!ratRight2) {
                    ratBody2.setLinearVelocity(2f, 0);
                    //Gdx.app.log("log", "rat2 x " + ratBody2.getPosition().x);
                    if (ratBody2.getPosition().x > 43.5f) {
                        ratBody2.setLinearVelocity(0, 0);
                        ratRight2 = true;
                        ratObject2.objectTexture = ratTexFlip;
                    }
                } else if (ratRight2) {
                    ratBody2.setLinearVelocity(-2f, 0);
                    //Gdx.app.log("log", "rat2 x " + ratBody2.getPosition().x);
                    if (ratBody2.getPosition().x < 35.5f) {
                        ratRight2 = false;
                        ratObject2.objectTexture = ratTex;
                    }
                }
            }
            //STAGE 2 RAT MOVEMENT
        } else if(host.getCurrentStage() == 2) {
            if (ratBody1.getLinearVelocity().y == 0) {

                if (!ratRight1) {
                    ratBody1.setLinearVelocity(2f, 0);
                    //Gdx.app.log("log", "rat1 x " + ratBody1.getPosition().x);
                    if (ratBody1.getPosition().x > 24.5f) {
                        ratBody1.setLinearVelocity(0, 0);
                        ratRight1 = true;
                        ratObject1.objectTexture = ratTexFlip;
                    }
                } else if (ratRight1) {
                    ratBody1.setLinearVelocity(-2f, 0);
                    //Gdx.app.log("log", "rat1 x " + ratBody1.getPosition().x);

                    if (ratBody1.getPosition().x < 20.0f) {
                        ratRight1 = false;
                        ratObject1.objectTexture = ratTex;
                    }
                }
            }
            if (ratBody2.getLinearVelocity().y == 0) {

                if (!ratRight2) {
                    ratBody2.setLinearVelocity(2f, 0);
                    //Gdx.app.log("log", "rat2 x " + ratBody2.getPosition().x);
                    if (ratBody2.getPosition().x > 53.5f) {
                        ratBody2.setLinearVelocity(0, 0);
                        ratRight2 = true;
                        ratObject2.objectTexture = ratTexFlip;
                    }
                } else if (ratRight2) {
                    ratBody2.setLinearVelocity(-2f, 0);
                    //Gdx.app.log("log", "rat2 x " + ratBody2.getPosition().x);
                    if (ratBody2.getPosition().x < 47.5f) {
                        ratRight2 = false;
                        ratObject2.objectTexture = ratTex;
                    }
                }
            }
        }
    }

    public ObjectData getRatObject1() {
        return ratObject1;
    }
    public ObjectData getRatObject2() {
        return ratObject2;
    }

    /**
     * Disposes rat-related stuff
     */
    public void dispose() {
        ratTex.dispose();
    }
}
// end of file
