package com.mygdx.game.TalkScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by possumunnki on 20.4.2017.
 */

public class Speech {
    private Texture speakingTexture;

    private int speaker;
    private final int BOTH = 0;
    private final int GRANDMA = 1;
    private final int LIGHT_DOLL = 2;
    private final float SCALE = 0.5f;
    private BitmapFont font;
    private GlyphLayout bounds;
    private String text;
    public Speech(int speaker, String text) {
        this.speaker = speaker;
        font = new BitmapFont(Gdx.files.internal("chatfont.txt"));
        font.getData().setScale(SCALE);
        this.text = text;
        bounds = new GlyphLayout(font, text);

        // sets texture depending on speaker
        if(speaker == BOTH) {
            speakingTexture = new Texture("chatbox3.png");
        } else if(speaker == GRANDMA) {
            speakingTexture = new Texture("chatbox2.png");
        } else if(speaker == LIGHT_DOLL) {
            speakingTexture = new Texture("chatbox1.png");
        }


    }

    public void draw(SpriteBatch sb, float screenWidth, float screenHeight) {
        sb.draw(speakingTexture,
                -70f,
                120f,
                speakingTexture.getWidth() * 2,
                speakingTexture.getHeight() * 2);
        font.draw(sb,
                text,
                screenWidth * 5/16,
                screenHeight * 5/8);
    }

    public void dispose() {
        speakingTexture.dispose();
        font.dispose();
    }

}
