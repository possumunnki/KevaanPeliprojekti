package com.mygdx.game.MainMenu2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.SkipActor;

/**
 * Created by possumunnki on 25.4.2017.
 */

public class MapBorderActor extends Actor {
    private Texture mapBorderTexture;
    private Sprite mapBorderSprite;

    public MapBorderActor() {
        mapBorderTexture = new Texture("mapBorder.png");
        mapBorderSprite = new Sprite();

        setWidth(mapBorderTexture.getWidth());
        setHeight(mapBorderTexture.getHeight());
        setOrigin(getWidth()/2, getHeight()/2);
        fadeInAction();
    }

    public void draw(Batch batch, float alpha) {

        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * alpha);
        batch.draw(mapBorderTexture, getX(), getY(), getWidth() * getScaleX(),
                getHeight() * getScaleY());
        batch.setColor(color.r, color.g, color.b, 1f);


    }

    public void act(float delta) {
        super.act(delta);

    }


    private void fadeInAction() {
        this.addAction(Actions.sequence(Actions.fadeOut(0f),
                       Actions.fadeIn(3f)));

    }

    public void moveAndZoomAction(float moveToX, float moveToY) {
        this.addAction(Actions.sequence(Actions.moveTo(moveToX, moveToY),
                Actions.scaleTo( 2, 2, 1f)));
        addAction(
                Actions.sequence(
                        Actions.moveTo(moveToX, moveToY),
                        Actions.scaleTo( 2, 2, 1f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {

                                System.out.println("action complete");
                            }
                        })));
    }

    public void dispose() {
        mapBorderTexture.dispose();
    }



}
