package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by possumunnki on 14.3.2017.
 */



public class LightDoll {
    private Texture lightDollTexture;
    private Sprite lightDollSprite;
    private final float DISTANCE_X = 0.5f;
    private final float DISTANCE_Y = 0.5f;
    private final float FLOATING_SPEED = 0.002f;
    private final boolean UP = true;
    private final boolean DOWN = false;
    private boolean floatDirection = DOWN;

    public LightDoll(Player player) {
        lightDollTexture = new Texture("badlogic.jpg");
        lightDollSprite = new Sprite(lightDollTexture);
        lightDollSprite.setSize(lightDollTexture.getWidth() / 1000f, lightDollTexture.getHeight() / 1000f);
        dollDefPosY = player.getPlayerBody().getPosition().y +
                player.getPlayerSprite().getHeight() +
                lightDollSprite.getHeight() + DISTANCE_Y;
        lightDollSprite.setY(dollDefPosY);

    }

    public void draw(SpriteBatch sb) {
        lightDollSprite.draw(sb);
    }

    private float dollDefPosX; // doll's defalt x cordination
    private float dollDefPosY; // doll's defalt y cordination

    public void followPlayer(Player player) {
        // sets lightDoll's position
        dollDefPosX = player.getPlayerBody().getPosition().x -
                player.getPlayerSprite().getWidth() -
                lightDollSprite.getWidth() - DISTANCE_X;
        dollDefPosY = player.getPlayerBody().getPosition().y +
                player.getPlayerSprite().getHeight() +
                lightDollSprite.getHeight() + DISTANCE_Y;

        // lightDollSprite.setPosition(dollDefPosX, dollDefPosY);
        lightDollSprite.setX(dollDefPosX);
        floatDoll(dollDefPosY);
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
}
