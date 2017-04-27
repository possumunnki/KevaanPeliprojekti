package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Created by possumunnki on 26.3.2017.
 */

public class PointActor extends Actor {
    private float printX;
    private float printY;
    private boolean touch = false;
    private boolean touchEnter = false;
    /**
     * Texture that appears whenever the point is not touched.
     */
    private Texture exitTexture;
    /**
     * Texture that appears whenever the point is touched.
     */
    private Texture enterTexture;

    public PointActor(float printX, float printY) {
        this.printX = printX;
        this.printY = printY;

        exitTexture = new Texture("nappulaBlack.png");
        enterTexture = new Texture("nappulaRed.png");
        // font.getData().setScale(0.01f);

        setWidth(exitTexture.getWidth());
        setHeight(exitTexture.getHeight());
        setPosition(printX,printY);
        setBounds(printX, printY ,getWidth(), getHeight());
        addListener(new PointListener());
    }
    public void draw(Batch batch, float alpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * alpha);

        if(touchEnter == true || touch == true) {
            batch.draw(enterTexture,
                    getX(),
                    getY(),
                    getWidth(),
                    getHeight());
        } else {
            batch.draw(exitTexture,
                    getX(),
                    getY(),
                    getWidth(),
                    getHeight());
        }

        batch.setColor(color.r, color.g, color.b, 1f);

    }

    public void act(float delta) {
        super.act(delta);
    }

    public boolean getTouch() {
        return touch;
    }

    public void setTouch(boolean touch) {
        this.touch = touch;
    }
    public void dispose() {
        enterTexture.dispose();
        exitTexture.dispose();
    }

    /**
     * Adds fade in action.
     * @param time time to fade in totally
     */
    public void addFadeInAction(float time) {
        this.addAction(Actions.sequence(Actions.fadeOut(0f),
                Actions.fadeIn(time)));
    }

    class PointListener extends InputListener {
        public boolean touchDown(InputEvent event,
                                 float x,
                                 float y,
                                 int pointer,
                                 int button) {

            touch = true;
            Gdx.app.log("touch","down");
            return false;
        }

        public void touchUp(InputEvent event,
                            float x,
                            float y,
                            int pointer,
                            int button) {
            touch = true;
            Gdx.app.log("touch","up");
        }
        public void enter(InputEvent event,
                          float x,
                          float y,
                          int pointer,
                          Actor fromActor) {
            touchEnter = true;
        }
        public void exit(InputEvent event,
                         float x,
                         float y,
                         int pointer,
                         Actor toActor) {
            touchEnter = false;
        }


    }
}
