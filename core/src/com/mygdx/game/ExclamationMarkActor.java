package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by possumunnki on 31.3.2017.
 */

public class ExclamationMarkActor extends Actor {
    private Texture exclamationTexture;
    private boolean touch = false;
    /**
     * coordinate
     */
    private float printX;
    private float printY;

    public ExclamationMarkActor() {
        exclamationTexture = new Texture("exclamation_mark.png");
        setWidth(exclamationTexture.getWidth());
        setHeight(exclamationTexture.getHeight());
        addListener(new exclamationListener());
    }

    public void draw(Batch batch, float alpha) {
        batch.draw(exclamationTexture,
                getX(),
                getY(),
                getWidth(),
                getHeight());

    }

    public void act(float delta) {
        super.act(delta);
    }

    public void setExclamationMarkPosition(Player player) {

        // whenever player is near the left world wall
        if (player.getPlayerBody().getPosition().x < MyGdxGame.SCREEN_WIDTH / 2) {
            printX = player.getPlayerBody().getPosition().x * 100f + player.getPlayerSprite().getWidth() * 50f;
        } else {
            printX = Gdx.graphics.getWidth() / 2 + player.getPlayerSprite().getWidth() * 50f;
        }

        // whenever player is near the ground
        if(player.getPlayerBody().getPosition().y < MyGdxGame.SCREEN_HEIGHT / 2) {
            printY = player.getPlayerBody().getPosition().y * 100f + player.getPlayerSprite().getHeight() * 50f;
        } else {
            printY = Gdx.graphics.getHeight() / 2 + player.getPlayerSprite().getHeight() * 50f;;
        }

        setPosition(printX, printY);
        setBounds(getX(), getY() ,getWidth(), getHeight());
        //Gdx.app.log("ExclamationX","" + getX());
        //Gdx.app.log("ExclamationY","" + getY());

    }

    public boolean getTouch() {
        return touch;
    }

    public void dispose() {
        exclamationTexture.dispose();
    }

    //public void setEx


    class exclamationListener extends InputListener {
        public boolean touchDown(InputEvent event,
                                 float x,
                                 float y,
                                 int pointer,
                                 int button) {
            touch = true;
            Gdx.app.log("tuch", "detected");
            return false;
        }

        public void touchUp(InputEvent event,
                            float x,
                            float y,
                            int pointer,
                            int button) {
            touch = true;
        }
    }

}
