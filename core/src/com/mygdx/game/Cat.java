package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Juz3 on 20.4.2017.
 */

public class Cat {

    boolean speedPrintOnce1 = false;
    boolean catFinishPrint = false;

    private Texture catStillTexture;
    private Texture catRunTexture;
    private Animation<TextureRegion> catRunAnim;
    private TextureRegion catRunCurrentFrame;
    private Body catBody;

    private final float CAT_SPEED_1 = 5.0f;
    private final float CAT_SPEED_2 = 5.0f;

    private Vector2 catPosition;

    public Cat(World world, MyGdxGame host) {

        catStillTexture = new Texture("catSprite1.png");
        catRunTexture = new Texture("catRunAnim2.png");
        catRunAnim = new Animation<TextureRegion>(1/8f,
                Utilities.transformToFrames(catRunTexture, 4, 2));
        catPosition = new Vector2(24f, 1.75f);



        if(host.getGameMode() == host.RAT_RACE) {
            catBody = world.createBody(getDefinitionOfBody());
            catBody.createFixture(getFixtureDefinition());
            catBody.setUserData("cat");
            catBody.setFixedRotation(true);
        }


    }

    public static BodyDef getDefinitionOfBody() {
        // Body Definition
        BodyDef myBodyDef = new BodyDef();
        // It's a body that moves
        myBodyDef.type = BodyDef.BodyType.KinematicBody;
        // Initial position is centered up
        // This position is the CENTER of the shape!
        myBodyDef.position.set( MyGdxGame.SCREEN_WIDTH / 2,
                MyGdxGame.SCREEN_HEIGHT / 2);

        return myBodyDef;
    }

    public FixtureDef getFixtureDefinition() {
        FixtureDef catFixtureDef = new FixtureDef();

        // Mass per square meter (kg^m2)
        catFixtureDef.density = 0.1f;

        // How bouncy object? Very bouncy [0,1]
        catFixtureDef.restitution = 0.1f;

        // How slipper object? [0,1]
        catFixtureDef.friction = 0.1f;

        // Create circle shape.
        PolygonShape catShape = new PolygonShape();
        catShape.setAsBox(1.5f, 0.75f);

        // Add the shape to the fixture
        catFixtureDef.shape = catShape;
        return catFixtureDef;
    }

    public void draw(SpriteBatch sb, float stateTime, MyGdxGame host, Player player) {

        catRunCurrentFrame = catRunAnim.getKeyFrame(stateTime, true);

        if(host.getGameMode() == host.RAT_RACE && host.getCurrentStage() == 3) {
            sb.draw(catRunCurrentFrame,
                    catBody.getPosition().x - catStillTexture.getWidth() / 200,
                    catBody.getPosition().y - catStillTexture.getHeight() / 200,
                    4.5f,
                    2.8f);
        } else if(host.getGameMode() == host.RAT_RACE && host.getCurrentStage() == 4) {
            sb.draw(catRunCurrentFrame,
                    catBody.getPosition().x - catStillTexture.getWidth() / 200,
                    catBody.getPosition().y - catStillTexture.getHeight() / 200,
                    4.5f,
                    2.8f);
        }
    }

    /**
     * Moves cat automatically.
     *
     * @param host
     */
    public void moveCat(MyGdxGame host, Player player, World world)  {

        if(host.getGameMode() == host.RAT_RACE && player.getPlayerBody().getPosition().x > 10) {


            // This is a failed attempt to reset cat back to start and get it running again
            // Instead if player falls down in stage 3, it is game over.
            /**
            // RESET CAT BACK TO START IF PLAYER RESETS BACK TO START BY FALLING DOWN
            if(player.getPositionReset()) {
                catBody.setTransform(new Vector2(MyGdxGame.SCREEN_WIDTH / 2,
                        MyGdxGame.SCREEN_HEIGHT), 0);
            } else {
                catBody.setLinearVelocity(CAT_SPEED_1, 0);
            }
             */

            catBody.setLinearVelocity(CAT_SPEED_1, 0);

            //Gdx.app.log("log", "cat pos x" + catBody.getPosition().x);

            if(catBody.getPosition().x > 30 && catBody.getPosition().x < 100) {
                if(!speedPrintOnce1) {
                    Gdx.app.log("log:", "speed 2");
                }
                speedPrintOnce1 = true;

                catBody.setLinearVelocity(CAT_SPEED_2, 0);
            }
            // If cat is at the finish line, stop and remove
            if(host.getCurrentStage() == 3) {
                if(catBody.getPosition().x > 120) {
                    if(!catFinishPrint) {
                        Gdx.app.log("log:", "cat @ finish line, removal");
                    }
                    catFinishPrint = true;

                    catBody.setLinearVelocity(0, 0);
                    //world.destroyBody(catBody);
                }
            } else if(host.getCurrentStage() == 4) {


                //When at the stairs near stage 4 finish, set y-velocity for cat
                if(catBody.getPosition().x > 115) {
                    catBody.setLinearVelocity(CAT_SPEED_1, 2.7f);
                }



                if(catBody.getPosition().x > 135) {
                    if(!catFinishPrint) {
                        Gdx.app.log("log:", "cat @ finish line, removal");
                    }
                    catFinishPrint = true;

                    catBody.setLinearVelocity(0, 0);
                    //world.destroyBody(catBody);
                }
            }

        }
    }


    public void dispose() {
        catRunTexture.dispose();
        catStillTexture.dispose();
    }
}
// End of file
