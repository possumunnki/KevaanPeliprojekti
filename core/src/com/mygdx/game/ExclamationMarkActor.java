package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by possumunnki on 31.3.2017.
 */

public class ExclamationMarkActor extends Actor {
    private Texture exclamationTexture;
    private boolean touch = false;
    public ExclamationMarkActor() {
        exclamationTexture = new Texture("exclamation_mark.png");
        setWidth(exclamationTexture.getWidth());
        setHeight(exclamationTexture.getHeight());
        setBounds(1, 1 ,getWidth(), getHeight());
        addListener(new exclamationListener());
    }

    public void draw(Batch batch, float alpha) {
        if(touch == true) {
            batch.draw(exclamationTexture,
                    1,
                    1,
                    exclamationTexture.getWidth(),
                    exclamationTexture.getHeight());
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
