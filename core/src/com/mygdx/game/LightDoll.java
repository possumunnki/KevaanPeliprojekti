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
    private boolean throwMode = false;
    private float throwSpeed = 0.1f;

    private float targetPointX;
    private float targetPointY;
    private float flingVelocityX;
    private float flingVelocityY;
    private int throwStep = 0;

    private boolean comingBack = false;
    //body for light doll
    private Body lightDollBody;


    public LightDoll(Player player, World world) {
        lightDollTexture = new Texture("lightdoll-1.png");
        lightDollSprite = new Sprite(lightDollTexture);

        lightDollSprite.setSize(lightDollTexture.getWidth() / 1000f, lightDollTexture.getHeight() / 1000f);
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
            shootLightDoll(player);
        } else {
            followPlayer(player);
        }
    }

    private float deltaX;
    private float deltaY;
    public void shootLightDoll(Player player) {
        if(comingBack) {
            deltaX = lightDollSprite.getX() - dollDefPosX;
            deltaY = lightDollSprite.getY() - dollDefPosY;
            lightDollSprite.setPosition(lightDollSprite.getX() - deltaX / 20,
                                        lightDollSprite.getY() - deltaY / 20);
            throwStep++;
            if(lightDollSprite.getX() +- 0.01f < dollDefPosX  &&
                    lightDollSprite.getY() +- 0.01f < dollDefPosY ) {
                comingBack = false;
                throwMode = false;
                throwStep = 0;
                floatDeph = 0;
            }
        } else {
            deltaX = lightDollSprite.getX() - targetPointX;
            deltaY = lightDollSprite.getY() - targetPointY;
            lightDollSprite.setPosition(lightDollSprite.getX() - deltaX / 20,
                                        lightDollSprite.getY() - deltaY / 20);
            throwStep++;
            if(lightDollSprite.getX() > targetPointX +- 0.01f &&
               lightDollSprite.getY() > targetPointY +- 0.01f) {
                comingBack = true;
                throwStep = 0;
            }
        }
        dollLocation.x = lightDollSprite.getX() + lightDollSprite.getWidth() / 2;
        dollLocation.y = lightDollSprite.getY() + lightDollSprite.getHeight() / 2;

        // Set body position
        lightDollBody.setTransform(dollLocation, 0);
    }
    public void setDollDefPos(Player player) {
        // sets lightDoll's position
        dollDefPosX = player.getPlayerBody().getPosition().x -
                player.getPlayerSprite().getWidth() -
                lightDollSprite.getWidth() - DISTANCE_X;
        dollDefPosY = player.getPlayerBody().getPosition().y +
                player.getPlayerSprite().getHeight() +
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

    public void throwLightDoll(float velocityX, float velocityY) {

        if(throwMode == false) {
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

            targetPointX = lightDollSprite.getX() + flingVelocityX;
            targetPointY = lightDollSprite.getY() + flingVelocityY;


            Gdx.app.log("flingVelocityY ", "" + flingVelocityY);
        }
    }
    /**
     * Box2DLight-related getter
     */
    public Body getLightDollBody() {
        return lightDollBody;
    }

}
