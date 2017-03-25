package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;

/**
 * Created by possumunnki on 7.3.2017.
 */

public class MainMenuScreen implements Screen {

    private MyGdxGame host;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Stage gameStage;
    private FontActor start;
    private Background menuBG;

    public MainMenuScreen(MyGdxGame host) {

        this.host = host;
        batch = host.getSpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                MyGdxGame.SCREEN_WIDTH * 100f,
                MyGdxGame.SCREEN_HEIGHT * 100f);

        gameStage = new Stage(new FillViewport(host.SCREEN_WIDTH * 100f, host.SCREEN_HEIGHT * 100f), batch);
        // creates "START" text
        start = new FontActor("START", host.SCREEN_WIDTH * 100f / 2, host.SCREEN_HEIGHT * 100f / 2);

        // Creates main menu background
        menuBG = new Background();
        gameStage.addActor(menuBG);
        gameStage.addActor(start);

        Gdx.input.setInputProcessor(gameStage);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameStage.act(Gdx.graphics.getDeltaTime());
        gameStage.draw();


        if (start.getTouch()) {
            host.setScreen(new MapScreen(host));
        }
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
        this.dispose();
    }

    @Override
    public void dispose() {
        start.dispose();
        menuBG.dispose();
        gameStage.dispose();
        Gdx.app.log("MainMenu", "disposed");

    }

}
