package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by possumunnki on 19.3.2017.
 */

public class ScreenController implements GestureDetector.GestureListener {
    private GestureDetector gestureDetector;
    private boolean touchPadTouched;

    public ScreenController() {
        gestureDetector = new GestureDetector(this);

    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if(!touchPadTouched) {
            Gdx.app.log("VelocityX ScreenController", "" + velocityX);
            Gdx.app.log("VelocityY ScreenController", "" + velocityY);
        }

        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    public GestureDetector getGestureDetector() {
        return this.gestureDetector;
    }

    public void setTouchPadTouched(boolean touched) {
        touchPadTouched = touched;
    }
}
