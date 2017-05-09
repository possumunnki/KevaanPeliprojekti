package com.mygdx.game.GameOverScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.FontActor;
import com.mygdx.game.GameScreen.GameScreen;
import com.mygdx.game.MainMenuScreen.MainMenuScreen;
import com.mygdx.game.MyGdxGame;

/**
 * Created by possumunnki on 28.3.2017.
 */

public class GameOverScreen implements Screen {
    private MyGdxGame host;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FontActor retry;
    private FontActor quit;

    private Stage stage;
    private Texture gameOverTexture;

    public GameOverScreen(MyGdxGame host) {
        this.host = host;
        batch = host.getSpriteBatch();

        gameOverTexture = new Texture("gameoverScreen.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                host.SCREEN_WIDTH,
                host.SCREEN_HEIGHT);
        retry = new FontActor("RETRY",
                host.SCREEN_WIDTH * 1/2 * 100f,
                host.SCREEN_HEIGHT * 2/4 * 100f);
        quit = new FontActor("QUIT",
                host.SCREEN_WIDTH  * 1/2 * 100f,
                host.SCREEN_HEIGHT * 1/4 * 100f);

        stage = new Stage(new FillViewport(host.SCREEN_WIDTH * 100f, host.SCREEN_HEIGHT * 100f), batch);

        //mapScreenBG = new Background("mapScreenBG");

        //stage.addActor(mapScreenBG);
        stage.addActor(retry);
        stage.addActor(quit);

        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        batch.begin();
        batch.draw(gameOverTexture, 0, 0,
                gameOverTexture.getWidth(),
                gameOverTexture.getHeight());

        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        if (retry.getTouch()) {
            host.setScreen(new GameScreen(host));
        } else if(quit.getTouch()) {
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
        gameOverTexture.dispose();
        retry.dispose();
        quit.dispose();
    }
}
