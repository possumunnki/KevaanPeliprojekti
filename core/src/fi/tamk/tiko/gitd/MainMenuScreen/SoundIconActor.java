package fi.tamk.tiko.gitd.MainMenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by possumunnki on 7.5.2017.
 */

public class SoundIconActor extends Actor {
    private Texture onTexture;
    private Texture offTexture;
    private boolean touch = false;
    private boolean ON = true;
    private boolean OFF = false;

    /**
     * Whether sound is on or off.
     */
    private boolean sound = ON;

    private final int BGM = 1;
    private final int SOUND_EFFECT = 2;


    public SoundIconActor(float printX,
                          float printY,
                          boolean sound,
                          int type) {
        if (type == BGM) {
            onTexture = new Texture("BGM_ON.png");
            offTexture = new Texture("BGM_OFF.png");
        } else if (type == SOUND_EFFECT) {
            onTexture = new Texture("SoundEffect_ON.png");
            offTexture = new Texture("SoundEffect_OFF.png");
        }

        this.sound = sound;

        setX(printX);
        setY(printY);
        setWidth(onTexture.getWidth());
        setHeight(onTexture.getHeight());
        addListener(new SoundIconActor.SoundEffectListener());
    }

    public void draw(Batch batch, float alpha) {
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
