package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by possumunnki on 10.5.2017.
 */

public class LogoScreen implements Screen {
    private MyGdxGame host;
    private SpriteBatch batch;

    private float stageWidth = host.SCREEN_WIDTH * 100f;
    private float stageHeight = host.SCREEN_HEIGHT * 100f;
    private Stage logoStage;
    private LogoActor tamkLogoActor;
    private LogoActor tikoLogoActor;
    private LogoActor vapriikkiActor;

    public LogoScreen(MyGdxGame host) {
        this.host = host;
        batch = host.getSpriteBatch();

        tamkLogoActor = new LogoActor("tamk", stageWidth * 1/4, stageHeight * 1/2);
        tikoLogoActor = new LogoActor("tiko", stageWidth * 2/4, stageHeight * 1/2);

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
