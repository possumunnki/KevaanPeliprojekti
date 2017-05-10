package com.mygdx.game.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.MyGdxGame;

/**
 * Created by possumunnki on 9.4.2017.
 */

public class PauseResumeButtonActor extends Actor {
    private MyGdxGame host;
    private float stageWidth = host.SCREEN_WIDTH * 100f;
    private float stageHeight = host.SCREEN_HEIGHT * 100f;
    private Texture pauseButtonTexture;
    private Texture resumeButtonTexture;
    private boolean touch;
    private final boolean PAUSE = true;
    private final boolean RUNNING = false;
    private boolean status;

    public PauseResumeButtonActor(MyGdxGame host) {
        this.host = host;
        pauseButtonTexture = new Texture("pauseButton.png");
        resumeButtonTexture = new Texture("resumeButton.png");
        // sets width of the actor
        setWidth(pauseButtonTexture.getWidth());
        // sets hight of the actor
        setHeight(pauseButtonTexture.getHeight());
        // sets position
        setPosition(stageWidth * 9/10,  stageHeight * 8/10);

        addListener(new PauseResumeButtonActor.pauseResumeListener());
    }

    public void draw(Batch batch, float alpha) {
        if(status == PAUSE) {
            batch.draw(resumeButtonTexture,
                    getX(),
                    getY(),
                    getWidth(),
                    getHeight());
        } else if(status == RUNNING) {
            batch.draw(pauseButtonTexture,
                    getX(),
                    getY(),
                    getWidth(),
                    getHeight());
        }


    }

    public void act(float delta) {
        super.act(delta);
    }


    public boolean getTouch() {
        return touch;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    public void dispose() {
        pauseButtonTexture.dispose();
        resumeButtonTexture.dispose();
        this.remove();
    }

    //public void setEx


    class pauseResumeListener extends InputListener {
        public boolean touchDown(InputEvent event,
                                 float x,
                                 float y,
                                 int pointer,
                                 int button) {
            touch = true;
            if(status == PAUSE) {
                status = RUNNING;
            } else if(status == RUNNING) {
                status = PAUSE;
            }
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
