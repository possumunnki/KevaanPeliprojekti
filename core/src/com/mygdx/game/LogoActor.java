package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by possumunnki on 10.5.2017.
 */

public class LogoActor extends Actor {
    private Texture logoTexture;

    private String logoType;
    private final String njaa = "njaa";
    private final String tamk = "tamk";
    private final String tiko = "tiko";
    private final String vapriikki = "vapriikki";

    public LogoActor(String logoType, float posX, float posY) {
        setLogoTexture();
        this.logoType = logoType;
        setPosition(posX, posY);
        setWidth(200f);
        setHeight(50f);

    }

    public void setLogoTexture() {
        if(logoType.equals(njaa)) {
            logoTexture = new Texture("logo/njaaLogo");
        } else if(logoType.equals(tamk)) {
            logoTexture = new Texture("logo/tamkLogo");
        } else if(logoType.equals(tiko)) {
            logoTexture = new Texture("logo/tikoLogo");
        } else if(logoType.equals(vapriikki)) {
            logoTexture =  new Texture("logo/vapriikkiLogo");
        }
    }

    public void act(float delta) {
        super.act(delta);
    }

    public void draw(Batch batch, float alpha) {
        batch.draw(logoTexture,
                getX(),
                getY(),
                getWidth(),
                getHeight());
    }

    public void dispose() {
        logoTexture.dispose();
    }
}
