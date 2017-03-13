package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by possumunnki on 7.3.2017. asd
 */

public class FontActor extends Actor {
    private BitmapFont font;
    private GlyphLayout fontLayout;
    private String fontString;
    private boolean touch = false;
    private float printX; // the cordinate X where the text will be drawn
    private float printY; // the cordinate Y where the text will be drawn


    public FontActor(String fontString,
                     float printX,      // x-cordinate where the text will be printed
                     float printY) {    // y-cordinate where the text will be printed
        this.fontString = fontString;
        this.printX = printX;
        this.printY = printY;

        font = new BitmapFont(Gdx.files.internal("font.txt"));
        // font.getData().setScale(0.01f);
        fontLayout = new GlyphLayout(font, fontString);
        setWidth(fontLayout.width);
        setHeight(fontLayout.height);
        setBounds(printX - getWidth() / 2 ,printY ,getWidth(), getHeight());
        addListener(new FontListener());

    }

    public void draw(Batch batch, float alpha) {
        font.draw(batch, fontString, printX - getWidth() / 2,
                printY + getHeight());
    }

    public void act(float delta) {
        super.act(delta);
    }

    public boolean getTouch() {
        return touch;
    }

    public void dispose() {
        font.dispose();
    }

    class FontListener extends InputListener {
        public boolean touchDown(InputEvent event,
                                 float x,
                                 float y,
                                 int pointer,
                                 int button) {
            touch = true;
            Gdx.app.log("Example1", "touch done at (" + x + ", " + y + ")");

            return false;
        }

        public void touchUp(InputEvent event,
                            float x,
                            float y,
                            int pointer,
                            int button) {
            touch = true;
            Gdx.app.log("Example2", "touch done at (" + x + ", " + y + ")");
        }


    }
}
