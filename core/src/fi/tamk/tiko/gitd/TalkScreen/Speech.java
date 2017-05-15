package fi.tamk.tiko.gitd.TalkScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Provides Speech by using font and textures.
 *
 * @author Akio Ide
 * @version 1.0
 * @since 2017-05-12
 */

public class Speech {
    // back ground of speech
    private Texture speakingTexture;

    /**
     * Values to set right background texture.
     */
    private final int BOTH = 0;
    private final int GRANDMA = 1;
    private final int LIGHT_DOLL = 2;
    private final int KEKKONEN = 3;
    private final int GRANDMA2 = 4;

    // scale of font
    private final float SCALE = 0.5f;
    private BitmapFont font;
    // text that will be printed on screen
    private String text;

    /**
     * Creates Speech.
     *
     * @param speaker The type of speeker.
     * @param text  text that must print out on screen.
     */
    public Speech(int speaker, String text) {

        font = new BitmapFont(Gdx.files.internal("chatfont.txt"));
        font.getData().setScale(SCALE);
        this.text = text;

        // sets texture depending on speaker
        setSpeakingTexture(speaker);
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

    /**
     * Sets background of speech depending on speaker.
     * @param speaker Talker.
     */
    private void setSpeakingTexture(int speaker) {
        if(speaker == BOTH) {
            speakingTexture = new Texture("chatbox3.png");
        } else if(speaker == GRANDMA) {
            speakingTexture = new Texture("chatbox2.png");
        } else if(speaker == LIGHT_DOLL) {
            speakingTexture = new Texture("chatbox1.png");
        } else if (speaker == KEKKONEN) {
            speakingTexture = new Texture("chatbox4.png");
        } else if(speaker == GRANDMA2) {
            speakingTexture = new Texture("chatbox5.png");
        }
    }

}
