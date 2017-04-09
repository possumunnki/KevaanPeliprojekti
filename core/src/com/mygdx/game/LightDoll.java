package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by possumunnki on 14.3.2017.
 */


//
public class LightDoll {
    private Texture lightDollTexture;
    private Sprite lightDollSprite;
    private final float DISTANCE_X = 0.5f;
    private final float DISTANCE_Y = 0.5f;
    private final float FLOATING_SPEED = 0.002f;
    private final boolean UP = true;
    private final boolean DOWN = false;
    private boolean floatDirection = DOWN;
    private Vector2 dollLocation;

    /**
     * Whenever light doll is away from the player it is true.
     */
    private boolean throwMode = false;

    /**
     * Speed of light doll when it's shot and coming back.
     *
     */
    private final int STEPS = 15;
    private boolean leftDown = false;
    private float targetPointX;
    private float targetPointY;
    private float flingVelocityX;
    private float flingVelocityY;

    private boolean comingBack = false;
    //body for light doll
    private Body lightDollBody;


    public LightDoll(Player player, World world) {
        lightDollTexture = new Texture("lightdoll-2.png");
        lightDollSprite = new Sprite(lightDollTexture);
        // size scaled for meters
        lightDollSprite.setSize(lightDollTexture.getWidth() / 100f, lightDollTexture.getHeight() / 100f);
        dollDefPosY = player.getPlayerBody().getPosition().y +
                player.getPlayerSprite().getHeight() +
                lightDollSprite.getHeight() + DISTANCE_Y;
        lightDollSprite.setY(dollDefPosY);
        dollLocation = new Vector2(0, 0);
        lightDollBody = world.createBody(Utilities.getDefinitionOfBody());
        lightDollBody.createFixture(getFixtureDefinition());
        lightDollBody.setUserData("lightDoll");

    }

    public void draw(SpriteBatch sb) {
        lightDollSprite.draw(sb);
    }

    private float dollDefPosX; // doll's default x coordinate
    private float dollDefPosY; // doll's default y coordinate

    public FixtureDef getFixtureDefinition() {
        FixtureDef dollFixtureDef = new FixtureDef();

        // Mass per square meter (kg^m2)
        dollFixtureDef.density = 0.1f;

        // How bouncy object? Very bouncy [0,1]
        dollFixtureDef.restitution = 0.1f;

        // How slipper object? [0,1]
        dollFixtureDef.friction = 0.1f;

        // Create circle shape.
        CircleShape circleshape = new CircleShape();
        circleshape.setRadius(0.15f);

        // Add the shape to the fixture
        dollFixtureDef.shape = circleshape;
        return dollFixtureDef;
    }

    public void moveLightDoll(Player player) {
        if (throwMode) {
            shootLightDoll();
        } else {
            followPlayer(player);
        }
    }

    private float deltaX;
    private float deltaY;

    public void setDollDefPos(Player player) {
        // sets lightDoll's position
        dollDefPosX = player.getPlayerBody().getPosition().x -
                lightDollSprite.getWidth() - DISTANCE_X;
        dollDefPosY = player.getPlayerBody().getPosition().y +
                lightDollSprite.getHeight() + DISTANCE_Y;

    }

    public void followPlayer(Player player) {

        // lightDollSprite.setPosition(dollDefPosX, dollDefPosY);
        lightDollSprite.setX(dollDefPosX);
        floatDoll(dollDefPosY);

        dollLocation.x = lightDollSprite.getX() + lightDollSprite.getWidth() / 2;
        dollLocation.y = lightDollSprite.getY() + lightDollSprite.getHeight() / 2;

        // Set body position
        lightDollBody.setTransform(dollLocation, 0);
    }
    private float floatDeph = 0;

    public void floatDoll(float dollDefPosY) {

        if (floatDirection == UP) {
            floatDeph += FLOATING_SPEED;
            lightDollSprite.setY(dollDefPosY + floatDeph);
            if (lightDollSprite.getY() - dollDefPosY  > 0f) {
                floatDirection = DOWN;
            }
        }

        if(floatDirection == DOWN) {
            floatDeph -= FLOATING_SPEED;
            lightDollSprite.setY(dollDefPosY + floatDeph);
            if(lightDollSprite.getY() - dollDefPosY  < - 0.2f) {
                floatDirection = UP;
            }
        }
    }

    /**
     * Converts fling velocity values and determines the target point
     * where the light doll is supposed to go.
     *
     * @param velocityX     velocity of fling for x coordinate.
     * @param velocityY     velocity of fling for y coordinate.
     */
    public void throwLightDoll(float velocityX, float velocityY) {

        if(!throwMode) {
            Gdx.app.log("VelocityY ", "" + velocityY);
            throwMode = true;
            if(velocityX > 2500f) {
                flingVelocityX = 2500f / 500f;
            } else if(velocityX < -2500f) {
                flingVelocityX = -2500 / 500f;
            } else{
                flingVelocityX = velocityX / 500f;
            }

            if(velocityY > 1600f) {
                flingVelocityY = 1600f / -500f;
            } else if(velocityY < - 1600f) {
                flingVelocityY = - 1600f / -500f;
            } else {
                flingVelocityY = velocityY / - 500f;
            }

            if(flingVelocityX < 0 && flingVelocityY < 0) {
                leftDown = true;

            }
            targetPointX = lightDollSprite.getX() + flingVelocityX;
            targetPointY = lightDollSprite.getY() + flingVelocityY;

            Gdx.app.log("flingVelocityY ", "" + flingVelocityX);
            Gdx.app.log("flingVelocityY ", "" + flingVelocityY);
        }
    }

    /**
     * Shoots light doll from the player and comes back.
     *
     */

    public void shootLightDoll() {
        if(comingBack) { // light doll comes back
            deltaX = lightDollSprite.getX() - dollDefPosX;
            deltaY = lightDollSprite.getY() - dollDefPosY;
            lightDollSprite.setPosition(lightDollSprite.getX() - deltaX / STEPS,
                    lightDollSprite.getY() - deltaY / STEPS);
            if(leftDown) {
                if(lightDollSprite.getX()   > dollDefPosX  +- 0.01f&&
                        lightDollSprite.getY()  > dollDefPosY +- 0.01f ) {

                    comingBack = false;
                    throwMode = false;
                    leftDown = false;
                }
            } else {
                if (lightDollSprite.getX() +- 0.01f < dollDefPosX  &&
                        lightDollSprite.getY() +- 0.01f < dollDefPosY ) {
                    comingBack = false;
                    throwMode = false;
                    floatDeph = 0;
                }
            }

        } else {  // light doll moves toward the target point
            deltaX = lightDollSprite.getX() - targetPointX;
            deltaY = lightDollSprite.getY() - targetPointY;

            lightDollSprite.setPosition(lightDollSprite.getX() - deltaX / STEPS,
                    lightDollSprite.getY() - deltaY / STEPS);
            // when light doll reaches to the target point, it goes back towards player
            if(leftDown) {
              if(lightDollSprite.getX() +- 0.01f < targetPointX  &&
                      lightDollSprite.getY() +- 0.01f < targetPointY ) {
                  comingBack = true;
              }
            } else {
                if (lightDollSprite.getX() > targetPointX +- 0.01f &&
                        lightDollSprite.getY() > targetPointY +- 0.01f) {

                    comingBack = true;
                }
            }
        }
        dollLocation.x = lightDollSprite.getX() + lightDollSprite.getWidth() / 2;
        dollLocation.y = lightDollSprite.getY() + lightDollSprite.getHeight() / 2;

        // Set body position
        lightDollBody.setTransform(dollLocation, 0);
    }
    /**
     * Box2DLight-related getter
     */
    public Body getLightDollBody() {
        return lightDollBody;
    }

    public void dispose() {
        lightDollTexture.dispose();
    }

}
