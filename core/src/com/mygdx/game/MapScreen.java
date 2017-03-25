package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;

/**
 * Created by possumunnki on 25.3.2017.
 */

public class MapScreen implements Screen {
    private MyGdxGame host;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FontActor nextStage;
    private FontActor returnToMainManu;
    private Stage stage;

    private Background mapScreenBG;

    public MapScreen(MyGdxGame host) {
        this.host = host;
        batch = host.getSpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                host.SCREEN_WIDTH,
                host.SCREEN_HEIGHT);
        nextStage = new FontActor("Next Stage",
                                    host.SCREEN_WIDTH * 6/8 * 100f,
                                    host.SCREEN_HEIGHT * 2/8 * 100f);
        returnToMainManu = new FontActor("Return to main manu",
                                    host.SCREEN_WIDTH  * 8/8 * 100f,
                                    host.SCREEN_HEIGHT * 1/8 * 100f);
        returnToMainManu.setFontScale(0.5f);

        stage = new Stage(new FillViewport(host.SCREEN_WIDTH * 100f, host.SCREEN_HEIGHT * 100f), batch);

        mapScreenBG = new Background("mapScreenBG");

        stage.addActor(mapScreenBG);
        stage.addActor(nextStage);
        stage.addActor(returnToMainManu);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        if (nextStage.getTouch()) {
            host.setScreen(new GameScreen(host));
        } else if(returnToMainManu.getTouch()) {
            host.setScreen(new MainMenuScreen(host));
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
        mapScreenBG.dispose();
        nextStage.dispose();
    }
}
