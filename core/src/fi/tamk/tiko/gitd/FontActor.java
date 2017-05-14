package fi.tamk.tiko.gitd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by possumunnki on 7.3.2017.
 */

public class FontActor extends Actor {
    private BitmapFont blackFont;
    private BitmapFont whiteFont;
    private GlyphLayout fontLayout;
    private String fontString;
    private boolean touch = false;
    private boolean touchEnter = false;
    private float printX; // the cordinate X where the text will be drawn
    private float printY; // the cordinate Y where the text will be drawn


    public FontActor(String fontString,
                     float printX,      // x-cordinate where the text will be printed
                     float printY) {    // y-cordinate where the text will be printed
        this.fontString = fontString;
        this.printX = printX;
        this.printY = printY;

        blackFont = new BitmapFont(Gdx.files.internal("chatfont.txt"));
        whiteFont = new BitmapFont(Gdx.files.internal("chatFontWhite.txt"));
        // font.getData().setScale(0.01f);
        fontLayout = new GlyphLayout(blackFont, fontString);
        setWidth(fontLayout.width);
        setHeight(fontLayout.height);
        setBounds(printX - getWidth() / 2, printY, getWidth(), getHeight());
        addListener(new FontListener());

    }

    public void draw(Batch batch, float alpha) {

        if (touchEnter == true) {
            blackFont.draw(batch, fontString, printX - getWidth() / 2,
                    printY + getHeight());
        } else {
            whiteFont.draw(batch, fontString, printX - getWidth() / 2,
                    printY + getHeight());
        }
    }

    public void act(float delta) {
        super.act(delta);
    }

    public boolean getTouch() {
        return touch;
    }

    public void dispose() {
        blackFont.dispose();
        whiteFont.dispose();
        this.remove();
    }

    public void setTouch(boolean touch) {
        this.touch = touch;
    }

    public void setFontString(String fontString){
        this.fontString = fontString;
        fontLayout = new GlyphLayout(blackFont, fontString);
        setWidth(fontLayout.width);
        setHeight(fontLayout.height);
        setBounds(printX - getWidth() / 2, printY, getWidth(), getHeight());
    }

    class FontListener extends InputListener {
        public boolean touchDown(InputEvent event,
                                 float x,
                                 float y,
                                 int pointer,
                                 int button) {
            touch = true;
            Gdx.app.log(fontString, "touch done");

            return false;
        }

        public void touchUp(InputEvent event,
                            float x,
                            float y,
                            int pointer,
                            int button) {
            touch = true;
            Gdx.app.log(fontString, "touch done at (" + x + ", " + y + ")");
        }

        public void enter(InputEvent event,
                          float x,
                          float y,
                          int pointer,
                          Actor fromActor) {
            touchEnter = true;
        }

        public void exit(InputEvent event,
                         float x,
                         float y,
                         int pointer,
                         Actor toActor) {
            touchEnter = false;
        }

    }
}
