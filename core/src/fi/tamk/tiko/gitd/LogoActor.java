package fi.tamk.tiko.gitd;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Provides Actors that has texture of logo.
 *
 * @author Akio Ide
 * @version 1.0
 * @since 2017-05-14
 */

public class LogoActor extends Actor {
    private Texture logoTexture;

    //private String logoType;
    private int logoType;
    private final int LOGO1 = 1;
    private final int LOGO2 = 2;

    /**
     * Creates logoactor.
     *
     * @param logoType type of logo
     * @param posX  X position where the actor will be printed
     * @param posY  Y position where the actor will be printed
     */
    public LogoActor(int logoType, float posX, float posY) {
        this.logoType = logoType;
        setPosition(posX, posY);
        setLogoTexture();

        setWidth(logoTexture.getWidth());
        setHeight(logoTexture.getHeight());

    }

    /**
     * Sets texture depending on type.
     */
    public void setLogoTexture() {
        if (logoType == LOGO1) {
            // this texture includes Njaa games -logo
            logoTexture = new Texture("logo/logo1.png");
        } else if (logoType == LOGO2) {
            // this texture includes tamk, tiko and vapriikko -logos.
            logoTexture = new Texture("logo/logo2.png");
        }
    }

    public void act(float delta) {
        super.act(delta);
    }

    public void draw(Batch batch, float alpha) {
        // this allows actors to use fade in/ out actions
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * alpha);

        // draws texture
        batch.draw(logoTexture,
                getX(),
                getY(),
                getWidth(),
                getHeight());

        batch.setColor(color.r, color.g, color.b, 1f);
    }

    public void dispose() {
        logoTexture.dispose();
    }
}
