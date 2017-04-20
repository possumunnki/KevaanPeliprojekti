package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by possumunnki on 17.4.2017.
 */

public class TalkScreen implements Screen {
    private MyGdxGame host;
    private SpriteBatch batch;
    private OrthographicCamera camera;


    public TalkScreen(MyGdxGame host) {
        this.host = host;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                MyGdxGame.SCREEN_WIDTH * 100f,
                MyGdxGame.SCREEN_HEIGHT * 100f);
        this.batch = host.getSpriteBatch();
    }
    @Override
    public void show() {
        Gdx.app.log("TalkScreen:", "show");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


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

        Gdx.app.log("TalkScreen", "disposed");
    }

    private void addTalk() {
        if(host.getCurrentStage() == 1) {

        }
    }
}
