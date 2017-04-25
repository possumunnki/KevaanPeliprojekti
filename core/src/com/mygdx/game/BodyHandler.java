package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Juz3 on 24.3.2017.
 */

public class BodyHandler {

    private Voodoo voodoo;

    private Rat rat;

    //private Cat cat;

    private Array<Body> bodies = new Array<Body>();

    private float windowWidth;
    private float windowHeight;

    public BodyHandler(World world, MyGdxGame host) {

        windowWidth = host.SCREEN_WIDTH;
        windowHeight = host.SCREEN_HEIGHT;

        voodoo = new Voodoo(world, host);

        rat = new Rat(world, host);

        //cat = new Cat(world);

    }


    public void drawAllBodies(SpriteBatch batch) {
        // DEBUG: Print total amount of bodies in the world
        //Gdx.app.log("", "Number of bodies: " + bodies.size);

        // Draw all bodies
        for (Body body : bodies) {

            // Draw all bodies with  voodoo doll user data (ground is not drawn)
            if(body.getUserData().equals(voodoo.getVdObject())) {   // > voodoo getter!

                body.setFixedRotation(true);

                // Get user data, has texture, enemy type and
                // radius
                ObjectData voodoo = (ObjectData) body.getUserData();

                batch.draw(voodoo.objectTexture,
                        body.getPosition().x - voodoo.width,
                        body.getPosition().y - voodoo.width,
                        voodoo.width,                   // originX
                        voodoo.height,                   // originY
                        voodoo.width * 2,               // windowWidth
                        voodoo.height * 2,               // windowHeight
                        1.0f,                          // scaleX
                        1.0f,                          // scaleY
                        body.getTransform().getRotation() * MathUtils.radiansToDegrees,
                        0,                             // Start drawing from x = 0
                        0,                             // Start drawing from y = 0
                        voodoo.objectTexture.getWidth(),       // End drawing x
                        voodoo.objectTexture.getHeight(),      // End drawing y
                        false,                         // flipX
                        false);                        // flipY


            } else if (body.getUserData().equals(rat.getRatObject())) { //  >rat getter!
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
            if (body.getUserData().equals(voodoo.getVdObject())) {

                // If voodoo body is off screen
                if (body.getPosition().y < -1) {
                    // Clear velocity (dropping of the screen)
                    body.setLinearVelocity(new Vector2(0, 0));
                    Gdx.app.log("offscreen", "ball Y-pos" + body.getPosition().y);

                    bodiesToBeDestroyed.add(body);
                }
            }
        }

        /**
         * Array list for bodies which are to be destroyed
         */

        // Iterate all voodooDolls
        for (Body body : bodies) {
            // If it equals voodoo template
            if (body.getUserData().equals(voodoo.getVdObject())) {
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

    /**
     * Calls movement methods from enemy classes
     */
    public void callEnemyWalk() {
        rat.ratWalk();
        //voodoo.voodooWalk();
    }

    public ObjectData callVoodooGetter() {
        return voodoo.getVdObject();
    }

    public ObjectData callRatGetter() {
        return rat.getRatObject();
    }

    public Array<Body> getBodies() {
        return bodies;
    }

    public void dispose() {
        voodoo.dispose();
        rat.dispose();
    }
}
// end of file