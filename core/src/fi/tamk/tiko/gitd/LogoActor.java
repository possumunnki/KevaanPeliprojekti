package fi.tamk.tiko.gitd;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by possumunnki on 10.5.2017.
 */

public class LogoActor extends Actor {
    private Texture logoTexture;

    //private String logoType;
    private int logoType;
    private final int LOGO1 = 1;
    private final int LOGO2 = 2;


    public LogoActor(int logoType, float posX, float posY) {
        this.logoType = logoType;
        setPosition(posX, posY);
        setLogoTexture();

        setWidth(logoTexture.getWidth());
        setHeight(logoTexture.getHeight());

    }

    public void setLogoTexture() {
        if (logoType == LOGO1) {
            logoTexture = new Texture("logo/logo1.png");
        } else if (logoType == LOGO2) {
            logoTexture = new Texture("logo/logo2.png");
        }
    }

    public void act(float delta) {
        super.act(delta);
    }

    public void draw(Batch batch, float alpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * alpha);

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
