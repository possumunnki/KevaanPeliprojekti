package fi.tamk.tiko.gitd.GameScreen.Allies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import fi.tamk.tiko.gitd.MyGdxGame;

/**
 * Provides game on pad.
 *
 * @author Akio Ide
 * @version 1.0
 * @since 2017-05-14
 */

public class Controller1 extends Actor {

    private MyGdxGame host;

    /**
     * size of touch pad
     */
    private final float DIAMETER = (host.SCREEN_WIDTH / 4) * 100f;

    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;


    private Texture touchKnobTexture;

    /**
     * Creates game on touch pad.
     *
     * @param host      needed to get screen size
     * @param positionX x coordinate of touch pad
     * @param positionY y coordinate of touch pad
     */
    public Controller1(MyGdxGame host, float positionX, float positionY) {
        this.host = host;
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

        // creates touch pad(deadzoneRadius, style)
        // deadzoneRadius - The distance in pixels from the center of the touch pad.
        touchpad = new Touchpad(20 / 100f, touchpadStyle);

        touchpad.setBounds(positionX, positionY, DIAMETER, DIAMETER);

        setWidth(DIAMETER);
        setHeight(DIAMETER);
        touchpad.setPosition(positionX, positionY);

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

    public Touchpad getTouchpad() {
        return touchpad;
    }

    public boolean getIsTouched() {
        return touchpad.isTouched();
    }

    public void dispose() {
        touchKnobTexture.dispose();
        touchpadSkin.dispose();
        this.remove();
    }
}
