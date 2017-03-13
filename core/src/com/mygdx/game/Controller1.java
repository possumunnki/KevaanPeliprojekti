package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by possumunnki on 11.3.2017.
 */

public class Controller1 extends Actor{
    private Texture padTexture;

    private int direction;
    private final float radius = 0.5f;

    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;


    private Texture touchKnobTexture;


    public Controller1(float positionX, float positionY) {

        touchKnobTexture = new Texture("touchKnob.png");

        //Create a touchpad skin
        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", new Texture("touchBackground.png"));

        //Set knob image
        touchpadSkin.add("touchKnob", new Texture("touchKnob.png"));

        //Create TouchPad Style
        touchpadStyle = new Touchpad.TouchpadStyle();

        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");

        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(10 / 100f, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(positionX, positionY, 200, 200);
        //touchpad.setSize();
        setWidth(touchKnobTexture.getWidth() / 100f);
        setHeight(touchKnobTexture.getHeight() / 100f);
        setPosition(positionX,positionY);

        //Create block sprite
        /*blockTexture = new Texture("touchKnob.png");
        blockSprite = new Sprite(blockTexture);
         blockSprite.setSize(blockTexture.getWidth()/ 100f, blockSprite.getHeight() / 100f);
        //Set position to centre of the screen
        blockSprite.setPosition(padDefPositionX,
                                padDefPositionY);
        */

    }

    public void draw(Batch batch, float alpha) {
        batch.draw(touchKnobTexture,
                touchpad.getX(),
                touchpad.getY(),
                touchpad.getWidth(),
                touchpad.getHeight());
    }

    public void act(float delta) {
        super.act(delta);
    }


    public void moveTouchPad() {

        if (touchpad.getKnobPercentX() > 0) {
            direction = MyGdxGame.RIGHT;
        }

        if(touchpad.getKnobPercentX() < 0) {
            direction = MyGdxGame.LEFT;
        }



        if (touchpad.getKnobPercentX() == 0) {
            direction = MyGdxGame.STOP;
        }

    }

    public int getDirection() {
       return direction;
    }


    public Touchpad getTouchpad() {
        return touchpad;
    }

    public void dispose() {
        touchKnobTexture.dispose();
        touchpadSkin.dispose();

    }
}
