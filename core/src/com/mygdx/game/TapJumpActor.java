package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by possumunnki on 28.4.2017.
 */

public class TapJumpActor extends Actor {
    private MyGdxGame host;
    private float stageWidth = host.SCREEN_WIDTH * 100f;
    private float stageHeight = host.SCREEN_HEIGHT * 100f;
    private boolean touch = false;

    public TapJumpActor(MyGdxGame host) {
        this.host = host;
        setBounds(stageWidth / 2, 0f, stageWidth /2, stageHeight);
        addListener(new TapJumpActor.touchListener());
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

    class touchListener extends InputListener {
        public boolean touchDown(InputEvent event,
                                 float x,
                                 float y,
                                 int pointer,
                                 int button) {
            touch = true;
            Gdx.app.log("tap jump", "detected");
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
