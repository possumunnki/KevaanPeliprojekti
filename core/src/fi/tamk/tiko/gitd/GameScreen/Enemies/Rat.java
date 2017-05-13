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

    private ObjectData ratObject;
    private Texture ratTex;

    private Body ratTemplate;
    boolean ratRight = false;

    private Body ratBody2;

    /**
     * Constructs the rat enemy object.
     *
     * @param world is phys2d world object
     * @param host is extension of LibGdx Game-class.
     */
    public Rat(World world, MyGdxGame host) {

        ratTex = new Texture(Gdx.files.internal("rat256.png"));

        ratObject = new ObjectData(ratTex, 1.2f, 0.5f, ObjectData.GameObjectType.RAT);


        if (host.getCurrentStage() == 1 || host.getCurrentStage() == 2) {
            // Rat bodies
            ratTemplate = createBody(12.9f, 3, ratObject.width, ratObject.height, world);
            ratTemplate.setUserData(ratObject);

            ratBody2 = createBody(30, 3, ratObject.width, ratObject.height, world);
            ratBody2.setUserData(ratObject);
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
    public void ratWalk() {


        if (ratTemplate.getLinearVelocity().y == 0) {

            if (!ratRight) {
                ratTemplate.setLinearVelocity(2f, 0);
                //Gdx.app.log("log", "rat1 x " + ratTemplate.getPosition().x);

                if (ratTemplate.getPosition().x > 20.5f) {
                    ratTemplate.setLinearVelocity(0, 0);
                    ratRight = true;
                }
            } else if (ratRight) {
                ratTemplate.setLinearVelocity(-2f, 0);
                //Gdx.app.log("log", "rat1 x " + ratTemplate.getPosition().x);

                if (ratTemplate.getPosition().x < 15.0f) {
                    ratRight = false;
                }
            }
        }

        if (ratBody2.getLinearVelocity().y == 0) {

            if (!ratRight) {
                ratBody2.setLinearVelocity(2f, 0);
                //Gdx.app.log("log", "rat2 x " + ratBody2.getPosition().x);

                if (ratBody2.getPosition().x > 40.5f) {
                    ratBody2.setLinearVelocity(0, 0);
                    ratRight = true;
                }
            } else if (ratRight) {
                ratBody2.setLinearVelocity(-2f, 0);
                //Gdx.app.log("log", "rat2 x " + ratBody2.getPosition().x);

                if (ratBody2.getPosition().x < 35.0f) {
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

    /**
     * Disposes rat-related stuff
     */
    public void dispose() {
        ratTex.dispose();
    }
}
// end of file
