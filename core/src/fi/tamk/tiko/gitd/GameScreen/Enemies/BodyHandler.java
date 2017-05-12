package fi.tamk.tiko.gitd.GameScreen.Enemies;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import fi.tamk.tiko.gitd.GameScreen.Allies.LightDoll;

import fi.tamk.tiko.gitd.MyGdxGame;


/**
 * Created by Juz3 on 24.3.2017.
 */

public class BodyHandler {
    private MyGdxGame host;
    private Voodoo voodoo;
    private Rat rat;

    private Array<Body> bodies = new Array<Body>();

    private final boolean ON = true;
    private Sound mobKillSound;

    private BossMob boss;

    private boolean setVictory = false;

    // hit counter for boss kill, needs five hits to finish.
    private int hitCount = 0;

    public BodyHandler(World world, MyGdxGame host) {
        this.host = host;

        voodoo = new Voodoo(world, host);

        rat = new Rat(world, host);

        boss = new BossMob(world, host);

        //cat = new Cat(world);
        mobKillSound = Gdx.audio.newSound(Gdx.files.internal("MobKill.wav"));
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

            } else if(body.getUserData().equals(boss.getBossObject())) {
                ObjectData boss = (ObjectData) body.getUserData();

                batch.draw(boss.objectTexture,
                        body.getPosition().x - boss.width,
                        body.getPosition().y - boss.height,
                        boss.width,                   // originX
                        boss.height,                   // originY
                        boss.width * 2,               // windowWidth
                        boss.height * 2,               // windowHeight
                        1.0f,                          // scaleX
                        1.0f,                          // scaleY
                        body.getTransform().getRotation() * MathUtils.radiansToDegrees,
                        0,                             // Start drawing from x = 0
                        0,                             // Start drawing from y = 0
                        boss.objectTexture.getWidth(),       // End drawing x
                        boss.objectTexture.getHeight(),      // End drawing y
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

            /**
             if(body.getUserData().equals(boss.getFireballObject())) {

             // if fireball hits ground and y-speed is 0
             if(body.getLinearVelocity().y == 0f) {
             body.setLinearVelocity(0, 0);
             Gdx.app.log("log", "fireball stop");
             bodiesToBeDestroyed.add(body);
             }
             }
             */
        }

        /**
         * Array list for bodies which are to be destroyed
         */

        // Iterate all voodooDolls
        for (Body body : bodies) {
            // If it equals voodoo objectData
            if (body.getUserData().equals(voodoo.getVdObject()) ||
                    body.getUserData().equals(boss.getBossObject())) {
                ObjectData info = (ObjectData) body.getUserData();
                // If it is a voodoo doll, then mark it to be removed.
                if (info.type == ObjectData.GameObjectType.VOODOO ||
                        info.type == ObjectData.GameObjectType.BOSS) {

                    // Check when the light doll is near enemy body
                    if(lightDoll.getLightDollBody().getPosition().x >
                            (body.getPosition().x - 0.5f) &&
                            lightDoll.getLightDollBody().getPosition().x <
                                    (body.getPosition().x + 0.3f) &&
                            lightDoll.getLightDollBody().getPosition().y >
                                    (body.getPosition().y - 0.3f) &&
                            lightDoll.getLightDollBody().getPosition().y <
                                    (body.getPosition().y + 0.4f)) {

                        if (body.getUserData().equals(voodoo.getVdObject())) {
                            // Add the specific body to bodiesToBeDestroyed-list
                            bodiesToBeDestroyed.add(body);
                            if (host.getSoundEffect() == ON) {
                                mobKillSound.play(0.5f);
                            }
                        }

                        // If light doll hits the boss mob
                        if (body.getUserData().equals(boss.getBossObject())) {

                            Gdx.app.log("log", "boss is hit!");

                            // play mob kill sfx every time the boss is hit
                            if (host.getSoundEffect() == ON) {
                                mobKillSound.play(0.5f);
                            }

                            hitCount++;

                            // when boss is hit 5 times, destroy boss
                            if(hitCount > 5) {
                                bodiesToBeDestroyed.add(body);
                                setVictory = true;

                            }
                        }
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
    public void callEnemyWalk(MyGdxGame host) {
        rat.ratWalk();
        voodoo.voodooWalk(host);
    }

    public void callBossAction(MyGdxGame host, World world) {

        if(host.getCurrentStage() == 5) {
            boss.bossWalk(world);
        }
    }

    public Boolean getSetVictory() {
        return setVictory;
    }

    /**
     public void callBossFire(World world) {
     boss.fire(world);
     }
     */

    public ObjectData callVoodooGetter() {
        return voodoo.getVdObject();
    }

    public ObjectData callRatGetter() {
        return rat.getRatObject();
    }

    public ObjectData callBossGetter() {
        return boss.getBossObject();
    }

    public Array<Body> getBodies() {
        return bodies;
    }

    public void dispose() {
        voodoo.dispose();
        rat.dispose();
        boss.dispose();
        mobKillSound.dispose();
    }
}
// end of file