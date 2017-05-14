package fi.tamk.tiko.gitd.GameScreen.Enemies;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import fi.tamk.tiko.gitd.GameScreen.Allies.LightDoll;
import fi.tamk.tiko.gitd.GameScreen.Allies.Player;
import fi.tamk.tiko.gitd.MyGdxGame;
import fi.tamk.tiko.gitd.Utilities;


/**
 * Handles all enemy bodies in the game.
 * Created by Juz3 on 24.3.2017.
 */
public class BodyHandler {
    private MyGdxGame host;
    private Voodoo voodoo;

    // Voodoo doll walk animation
    private Texture voodooWalkTexture;
    private Animation<TextureRegion> voodooWalkAnim;
    private TextureRegion voodooWalkCurrentFrame;

    private Rat rat;

    private Array<Body> bodies = new Array<Body>();

    private final boolean ON = true;
    private Sound mobKillSound;

    private BossMob boss;

    private boolean setVictory = false;

    // Boss hit helper booleans
    private boolean bossFling1 = false;
    private boolean bossFling2 = false;
    private boolean bossFling3 = false;
    private boolean bossFling4 = false;
    private boolean bossFling5 = false;
    private boolean bossFling6 = false;


    // hit counter for boss kill, needs x hits to finish.
    private int hitCount = 0;

    /**
     * Constructs the bodyHandler object.
     * @param world is phys2d world object
     * @param host is extension of LibGdx Game-class.
     */
    public BodyHandler(World world, MyGdxGame host) {
        this.host = host;

        voodoo = new Voodoo(world, host);


        /**
        voodooWalkTexture = new Texture("voodooWalk2.png");
        voodooWalkAnim = new Animation<TextureRegion>(1/3f,
                Utilities.transformToFrames(voodooWalkTexture, 6, 1));
         */

        rat = new Rat(world, host);

        boss = new BossMob(world, host);

        mobKillSound = Gdx.audio.newSound(Gdx.files.internal("MobKill.wav"));
    }

    /**
     * Draws all enemy bodies to world
     * @param batch spriteBatch of libGdx
     */
    public void drawAllBodies(SpriteBatch batch, float stateTime, Player player) {
        // DEBUG: Print total amount of bodies in the world
        //Gdx.app.log("", "Number of bodies: " + bodies.size);

        //voodooWalkCurrentFrame = voodooWalkAnim.getKeyFrame(stateTime, true);

        // Draw all bodies
        for (Body body : bodies) {

            // Draw all bodies with  voodoo doll user data (ground is not drawn)
            if(body.getUserData().equals(voodoo.getVdObject())) {   // > voodoo getter!

                body.setFixedRotation(true);

                // Get user data, has texture, enemy type and
                // radius
                ObjectData voodooData = (ObjectData) body.getUserData();

                batch.draw(voodooData.objectTexture,
                        body.getPosition().x - voodooData.width,
                        body.getPosition().y - voodooData.width,
                        voodooData.width,                   // originX
                        voodooData.height,                   // originY
                        voodooData.width * 2,               // windowWidth
                        voodooData.height * 2,               // windowHeight
                        1.0f,                          // scaleX
                        1.0f,                          // scaleY
                        body.getTransform().getRotation() * MathUtils.radiansToDegrees,
                        0,                             // Start drawing from x = 0
                        0,                             // Start drawing from y = 0
                        voodooData.objectTexture.getWidth(),       // End drawing x
                        voodooData.objectTexture.getHeight(),      // End drawing y
                        false,                         // flipX
                        false);                        // flipY


                /**
                if(voodoo.getLeftTurn()) {

                    if(!turnOnce) {
                        Utilities.flip(voodooWalkAnim);
                        turnOnce = false;
                    }
                } else if(!voodoo.getLeftTurn()) {
                    if(turnOnce) {
                        Utilities.flip(voodooWalkAnim);
                        turnOnce = true;
                    }
                }

                batch.draw(voodooWalkCurrentFrame,
                        body.getPosition().x - voodooData.width,
                        body.getPosition().y - voodooData.width,
                        voodooData.width,                   // originX
                        voodooData.height - 0.15f,          // originY
                        0.5f,
                        0.5f,
                        0.75f,                          // scaleX
                        0.8f,                           // scaleY
                        0f);
                 */


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

    /**
     * Removes needed bodies from world
     * @param world is phys2d world object
     * @param lightDoll is lightdoll object
     */
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
         * Checks if lightdoll body is near voodoo body.
         * If it is, add corresponding voodoo body to an array which
         * contains the bodies that are going to be removed.
         */
        // Iterate all voodooDolls
        for (Body body : bodies) {
            // If it equals voodoo objectData
            if (body.getUserData().equals(voodoo.getVdObject()) ||
                    body.getUserData().equals(boss.getBossObject())) {
                ObjectData info = (ObjectData) body.getUserData();
                // If it is a voodoo doll, then mark it to be removed.
                if (info.type == ObjectData.GameObjectType.VOODOO) {

                    // Check when the light doll is near enemy body
                    if (lightDoll.getLightDollBody().getPosition().x >
                            (body.getPosition().x - 0.4f) &&
                            lightDoll.getLightDollBody().getPosition().x <
                                    (body.getPosition().x + 0.45f) &&
                            lightDoll.getLightDollBody().getPosition().y >
                                    (body.getPosition().y - 0.3f) &&
                            lightDoll.getLightDollBody().getPosition().y <
                                    (body.getPosition().y + 0.25f)) {

                        if (body.getUserData().equals(voodoo.getVdObject())) {
                            // Add the specific body to bodiesToBeDestroyed-list
                            bodiesToBeDestroyed.add(body);
                            if (host.getSoundEffect() == ON) {
                                mobKillSound.play(0.5f);
                            }
                        }
                    }

                } else if (info.type == ObjectData.GameObjectType.BOSS) {
                    // Check when the light doll is near enemy body
                    if (lightDoll.getLightDollBody().getPosition().x >
                            (body.getPosition().x) &&
                            lightDoll.getLightDollBody().getPosition().x <
                                    (body.getPosition().x + boss.getBossObject().width) &&
                            lightDoll.getLightDollBody().getPosition().y >
                                    (body.getPosition().y) &&
                            lightDoll.getLightDollBody().getPosition().y <
                                    (body.getPosition().y + boss.getBossObject().height)) {

                        // If light doll hits the boss mob
                        if (body.getUserData().equals(boss.getBossObject())) {
                            if(lightDoll.getComingBack() && !bossFling1) {
                                hitCount++;
                                bossFling1 = true;
                                Gdx.app.log("asd", "hit1, count: " + hitCount);
                                // play mob kill sfx every time the boss is hit
                                if (host.getSoundEffect() == ON) {
                                    mobKillSound.play(0.25f);
                                }
                            } else if(!lightDoll.getComingBack() && !bossFling2 &&
                                    hitCount == 1) {
                                hitCount++;
                                bossFling2 = true;
                                Gdx.app.log("asd", "hit2, count: " + hitCount);
                                // play mob kill sfx every time the boss is hit
                                if (host.getSoundEffect() == ON) {
                                    mobKillSound.play(0.25f);
                                }
                            } else if(lightDoll.getComingBack() && !bossFling3 &&
                                    hitCount == 2) {
                                hitCount++;
                                bossFling3 = true;
                                Gdx.app.log("asd", "hit3, count: " + hitCount);
                                // play mob kill sfx every time the boss is hit
                                if (host.getSoundEffect() == ON) {
                                    mobKillSound.play(0.25f);
                                }
                            } else if(!lightDoll.getComingBack() && !bossFling4 &&
                                    hitCount == 3) {
                                hitCount++;
                                bossFling4 = true;
                                Gdx.app.log("asd", "hit2, count: " + hitCount);
                                // play mob kill sfx every time the boss is hit
                                if (host.getSoundEffect() == ON) {
                                    mobKillSound.play(0.25f);
                                }
                            } else if(lightDoll.getComingBack() && !bossFling5 &&
                                    hitCount == 4) {
                                hitCount++;
                                bossFling5 = true;
                                Gdx.app.log("asd", "hit3, count: " + hitCount);
                                // play mob kill sfx every time the boss is hit
                                if (host.getSoundEffect() == ON) {
                                    mobKillSound.play(0.25f);
                                }
                            } else if(!lightDoll.getComingBack() && !bossFling6 &&
                                    hitCount == 5) {
                                hitCount++;
                                bossFling6 = true;
                                Gdx.app.log("asd", "hit3, count: " + hitCount);
                                // play mob kill sfx every time the boss is hit
                                if (host.getSoundEffect() == ON) {
                                    mobKillSound.play(0.25f);
                                }
                            }

                            // when boss is hit 6 times, destroy boss, win and set credits screen.
                            if (bossFling6) {
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
     * @param host is extension of LibGdx Game-class. Needed to check the current stage
     */
    public void callEnemyWalk(MyGdxGame host) {

        voodoo.voodooWalk(host);

        if(host.getCurrentStage() == 1 ||
                host.getCurrentStage() == 2) {
            rat.ratWalk();
        }
    }

    /**
     * Runs the boss walking method from bossMob class.
     * @param host is extension of LibGdx Game-class.
     */
    public void callBossAction(MyGdxGame host) {

        if(host.getCurrentStage() == 5) {
            boss.bossWalk();
        }
    }

    public Boolean getSetVictory() {
        return setVictory;
    }

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
        //voodooWalkTexture.dispose();
    }
}
// end of file