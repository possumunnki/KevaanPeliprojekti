package fi.tamk.tiko.gitd.MainMenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * This class implements sound icon buttons.
 * The icons can detect touches and sound settings can be configured using touch detect.
 *
 * @author Akio Ide
 * @version 1.0
 * @since 2017-05-12
 */

public class SoundIconActor extends Actor {
    private Texture onTexture;
    private Texture offTexture;
    private boolean touch = false;

    /**
     * Whether sound is on or off.
     */
    private boolean ON = true;
    private boolean OFF = false;
    private boolean sound = ON;

    private final int BGM = 1;
    private final int SOUND_EFFECT = 2;


    /**
     * Creates sound icon button.
     *
     * @param printX    X coordinate where the icon will be printed.
     * @param printY    Y coordinate where the icon will be printed.
     * @param sound     whether sound is on or not.
     * @param type      type of the icon
     */
    public SoundIconActor(float printX,
                          float printY,
                          boolean sound,
                          int type) {
        // sets textures depending on type
        if (type == BGM) {
            onTexture = new Texture("BGM_ON.png");
            offTexture = new Texture("BGM_OFF.png");
        } else if (type == SOUND_EFFECT) {
            onTexture = new Texture("SoundEffect_ON.png");
            offTexture = new Texture("SoundEffect_OFF.png");
        }

        this.sound = sound;
        // sets coordinates where the texture is printed
        setX(printX);
        setY(printY);
        // sets size
        setWidth(onTexture.getWidth());
        setHeight(onTexture.getHeight());
        // adds touch detector
        addListener(new SoundIconActor.SoundEffectListener());
    }

    public void draw(Batch batch, float alpha) {
        // draws different textures depending on sound status
        if (sound == ON) {
            batch.draw(onTexture,
                    getX(),
                    getY(),
                    getWidth(),
                    getHeight());
        } else if (sound == OFF) {
            batch.draw(offTexture,
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


    public void dispose() {
        onTexture.dispose();
        offTexture.dispose();
        this.remove();
    }

    public void setSound(boolean soundEffect) {
        this.sound = soundEffect;

    }

    public void setTouchFalse() {
        touch = false;
    }

    /**
     * Class that detects players touches on this actor.
     */
    class SoundEffectListener extends InputListener {
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
