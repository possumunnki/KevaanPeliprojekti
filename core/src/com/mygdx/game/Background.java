package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Juz3 on 19.2.2017.
 */

public class Background extends Actor {

    private Texture bg;

    public Background() {

        bg = new Texture("menuBG.png");

        setWidth(bg.getWidth());
        setHeight(bg.getHeight());
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(bg, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void dispose() {
        bg.dispose();
    }
}
