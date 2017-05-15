package fi.tamk.tiko.gitd.TalkScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import fi.tamk.tiko.gitd.MyGdxGame;

/**
 * Provides skip icon as an actor.
 *
 * @author Akio Ide
 * @version 1.0
 * @since 2017-05-12
 */
public class SkipActor extends Actor {
    private Texture skipTexture;
    private boolean touch = false;

    private Sprite skipSprite;

    /**
     *
     * @param printX X coordinate where the texture will be drawn
     * @param printY Y coordinate where the texture will be drawn
     * @param host needed to get localization
     */
    public SkipActor(float printX,
                     float printY,
                     MyGdxGame host) {
        // sets textures depending on language
        if(host.locale == host.ENGLISH) {
            skipTexture = new Texture("skip.png");

        } else if(host.locale == host.FINNISH) {
            skipTexture = new Texture("ohita.png");
        }
        skipSprite = new Sprite(skipTexture);
        // sets positions of texture
        setX(printX);
        setY(printY);
        // sets size of texture
        setWidth(skipTexture.getWidth());
        setHeight(skipTexture.getHeight());

        // adds actions to actor
        fadeInFadeOutAction();

        // adds listener to detect touch
        addListener(new SkipActor.skipListener());
    }

    public void draw(Batch batch, float alpha) {
        // allows to use fade in/out action
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * alpha);
        // draws actor
        batch.draw(skipSprite, getX(), getY(), getWidth() * getScaleX(),
                getHeight() * getScaleY());

        batch.setColor(color.r, color.g, color.b, 1f);


    }

    public void act(float delta) {
        super.act(delta);

    }

    /**
     * Adds fade in and out actions to actor.
     */
    private void fadeInFadeOutAction() {

        this.addAction( Actions.forever( // loops action

                Actions.sequence(Actions.fadeIn(1.5f), // fades in in 1.5 seconds
                        Actions.fadeOut(1.5f) // fades out in 1.5 seconds
                )
        ));

    }

    public boolean getTouch() {
        return touch;
    }

    public void dispose() {
        skipTexture.dispose();
        this.remove();
    }

    /**
     * Makes actor possible to detect touch.
     */
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

