package fi.tamk.tiko.gitd.GameScreen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import fi.tamk.tiko.gitd.MyGdxGame;

/**
 * Provides jump-button on screen as actor.
 * This method can detect touches.
 *
 * @author Akio Ide
 * @version 1.0
 * @since 2017-05-14
 */
public class JumpButtonActor extends Actor {
    private MyGdxGame host;
    private float stageWidth = host.SCREEN_WIDTH * 100f;
    private float stageHeight = host.SCREEN_HEIGHT * 100f;
    private boolean touch = false;
    private Texture jumpButtonTexture;

    /**
     * Creates jump button.
     *
     * @param host needed to import screen sizes.
     */
    public JumpButtonActor(MyGdxGame host) {
        this.host = host;
        jumpButtonTexture = new Texture("jumpButton.png");

        // setBounds(X-position,Y-position, width, height)
        setBounds(stageWidth * 8 / 10,
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

    // detects touches
    class touchListener extends InputListener {
        public boolean touchDown(InputEvent event,
                                 float x,
                                 float y,
                                 int pointer,
                                 int button) {
            touch = true;
            //Gdx.app.log("tap jump", "detected");
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
