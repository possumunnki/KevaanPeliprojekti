package fi.tamk.tiko.gitd.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import fi.tamk.tiko.gitd.MyGdxGame;

/**
 * Created by possumunnki on 28.4.2017.
 */

public class JumpButtonActor extends Actor {
    private MyGdxGame host;
    private float stageWidth = host.SCREEN_WIDTH * 100f;
    private float stageHeight = host.SCREEN_HEIGHT * 100f;
    private boolean touch = false;
    private Texture jumpButtonTexture;

    public JumpButtonActor(MyGdxGame host) {
        this.host = host;
        jumpButtonTexture = new Texture("jumpButton.png");
        // setBounds(X-position,Y-position, width, height)
        setBounds(stageWidth * 8/10,
                stageHeight * 0,
                jumpButtonTexture.getWidth(),
                jumpButtonTexture.getHeight());

        addListener(new JumpButtonActor.touchListener());
    }

    public void act(float delta) {
        super.act(delta);
    }

    public void draw(Batch batch, float alpha) {
        batch.draw(jumpButtonTexture,
                getX(),
                getY(),
                getWidth(),
                getHeight());
    }

    public boolean getTouch() {
        return touch;
    }
    public void setTouch(boolean touch) {
        this.touch = touch;
    }

    public void dispose() {
        jumpButtonTexture.dispose();
        this.remove();
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
