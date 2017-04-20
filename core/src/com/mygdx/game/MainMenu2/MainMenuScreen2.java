package com.mygdx.game.MainMenu2;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Background;
import com.mygdx.game.FontActor;
import com.mygdx.game.MainMenuScreen;
import com.mygdx.game.MyGdxGame;

/**
 * Created by possumunnki on 17.4.2017.
 */

public class MainMenuScreen2 implements Screen {
    private MyGdxGame host;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage mainMenuStage;

    private Background menuBG;

    public MainMenuScreen2(MyGdxGame host) {
        this.host = host;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                MyGdxGame.SCREEN_WIDTH * 100f,
                MyGdxGame.SCREEN_HEIGHT * 100f);
        viewport = new FitViewport(host.SCREEN_WIDTH, host.SCREEN_HEIGHT, camera);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
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
