package fi.tamk.tiko.gitd.MainMenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import fi.tamk.tiko.gitd.MyGdxGame;

/**
 * This class implements language icon buttons.
 * The icons can detect touches and language settings can be configured through this class.
 *
 * @author Akio Ide
 * @version 1.0
 * @since 2017-05-12
 */
public class LanguageIconActor extends Actor {
    private MyGdxGame host;
    private Texture languageTexture;
    private Texture offTexture;
    /**
     * Type of icon.
     */
    private int type;
    private boolean touch = false;




    public LanguageIconActor(MyGdxGame host,
                             float printX,
                             float printY,
                             int type) {
        this.host = host;
        this.type = type;

        if (type == host.ENGLISH) {
            languageTexture = new Texture("english.png");
        } else if (type == host.FINNISH) {
            languageTexture = new Texture("finnish.png");
        }

        setX(printX);
        setY(printY);
        setWidth(languageTexture.getWidth());
        setHeight(languageTexture.getHeight());
        addListener(new LanguageIconActor.LanguageIconListener());
    }

    public void draw(Batch batch, float alpha) {
            batch.draw(languageTexture,
                    getX(),
                    getY(),
                    getWidth(),
                    getHeight());
    }

    public void act(float delta) {
        super.act(delta);

    }


    public boolean getTouch() {
        return touch;
    }


    public void dispose() {
        languageTexture.dispose();
        offTexture.dispose();
        this.remove();
    }


    public void setTouchFalse() {
        touch = false;
    }

    class LanguageIconListener extends InputListener {
        public boolean touchDown(InputEvent event,
                                 float x,
                                 float y,
                                 int pointer,
                                 int button) {
            host.locale = type;
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
            host.locale = type;
        }
    }
}

