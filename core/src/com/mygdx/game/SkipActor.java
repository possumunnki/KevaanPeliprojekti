package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Created by possumunnki on 21.4.2017.
 */

public class SkipActor extends Actor {
    private Texture skipTexture;
    private boolean touch = false;

    /**
     * coordinate where the texture will be drawn
     */
    private float printX;
    private float printY;
    private Sprite skipSprite;

    public SkipActor(float printX,
                     float printY) {
        skipTexture = new Texture("skip.png");
        skipSprite = new Sprite(skipTexture);
        this.printX = printX;
        this.printY = printY;

        setWidth(skipTexture.getWidth());
        setHeight(skipTexture.getHeight());
        addListener(new SkipActor.skipListener());
        faceInFadeOutAction();
    }

    public void draw(Batch batch, float alpha) {

        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * alpha);
        batch.draw(skipSprite, getX(), getY(), getWidth() * getScaleX(),
                getHeight() * getScaleY());
        batch.setColor(color.r, color.g, color.b, 1f);


    }

    public void act(float delta) {
        super.act(delta);

    }


    private void faceInFadeOutAction() {

        Action faceInAction = Actions.fadeIn(1.5f);
        Action fadeOutAction = Actions.fadeOut(1.5f);
        this.addAction( Actions.forever(
                Actions.sequence(Actions.fadeIn(1.5f),
                        Actions.fadeOut(1.5f)
                )
        ));

    }


    public boolean getTouch() {
        return touch;
    }

    public void dispose() {
        skipTexture.dispose();
    }




    class skipListener extends InputListener {
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

