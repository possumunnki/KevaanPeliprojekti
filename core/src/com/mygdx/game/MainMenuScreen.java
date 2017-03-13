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
        gameStage.addActor(start);



    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // batch.begin();

        // batch.end();

        gameStage.act(Gdx.graphics.getDeltaTime());
        gameStage.draw();
        Gdx.input.setInputProcessor(gameStage);

        if (start.getTouch()) {
            host.setScreen(new GameScreen(host));
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

    }

    @Override
    public void dispose() {
        batch.dispose();
        start.dispose();
        Gdx.app.log("MainMenu", "disposed");

    }

}
