package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by possumunnki on 26.3.2017.
 */

public class PointActor extends Actor {
    private float printX;
    private float printY;
    private boolean touch = false;
    private Texture pointTexture;

    public PointActor(float printX, float printY) {
        this.printX = printX;
        this.printY = printY;

        pointTexture = new Texture("point.png");
        // font.getData().setScale(0.01f);

        setWidth(pointTexture.getWidth());
        setHeight(pointTexture.getHeight());
        setBounds(printX, printY ,getWidth(), getHeight());
        addListener(new PointListener());
    }
    public void draw(Batch batch, float alpha) {
        if(touch == true) {
            batch.draw(pointTexture,
                    printX,
                    printY,
                    pointTexture.getWidth(),
                    pointTexture.getHeight());
        }

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
        pointTexture.dispose();
    }

    class PointListener extends InputListener {
        public boolean touchDown(InputEvent event,
                                 float x,
                                 float y,
                                 int pointer,
                                 int button) {
            touch = true;
            Gdx.app.log("Example1", "touch done at (" + x + ", " + y + ")");

            return false;
        }

        public void touchUp(InputEvent event,
                            float x,
                            float y,
                            int pointer,
                            int button) {
            touch = true;
            Gdx.app.log("Example2", "touch done at (" + x + ", " + y + ")");
        }


    }
}
