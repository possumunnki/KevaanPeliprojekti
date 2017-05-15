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
    /**
     * Type of icon.
     */
    private int type;
    /**
     * Whether the icon is touched or not.
     */
    private boolean touch = false;


    /**
     * Creates language icon.
     *
     * @param host      To get locale settings.
     * @param printX    The X coordinate where the button will be printed.
     * @param printY    The X coordinate where the button will be printed.
     * @param type      Type of button.
     */
    public LanguageIconActor(MyGdxGame host,
                             float printX,
                             float printY,
                             int type) {
        this.host = host;
        this.type = type;

        //sets textures depending on icon type
        if (type == host.ENGLISH) {
            languageTexture = new Texture("english.png");
        } else if (type == host.FINNISH) {
            languageTexture = new Texture("finnish.png");
        }

        // sets positions where the texture will be printed
        setX(printX);
        setY(printY);
        // sets size of Actor that will be also size of texture
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
        this.remove();
    }

    // Detects touches
    class LanguageIconListener extends InputListener {
        public boolean touchDown(InputEvent event,
                                 float x,
                                 float y,
                                 int pointer,
                                 int button) {
            host.locale = type;
            touch = true;
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

