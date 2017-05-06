package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;

import java.awt.Font;

/**
 * Created by possumunnki on 7.3.2017.
 */

public class MainMenuScreen implements Screen {

    private MyGdxGame host;
    /**
     * Screen's size in pixel
     */
    private float screenWidth = host.SCREEN_WIDTH * 100f;
    private float screenHeight = host.SCREEN_HEIGHT * 100f;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Stage mainMenuStage;
    private FontActor newGame;
    private FontActor continueGame;
    private Background menuBG;


    public MainMenuScreen(MyGdxGame host) {

        this.host = host;
        batch = host.getSpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                screenWidth,
                screenHeight);

        mainMenuStage = new Stage(new FillViewport(screenWidth, screenHeight), batch);
        // creates "START" text
        newGame = new FontActor("NEW GAME", screenWidth * 5 / 8, screenHeight * 2/8);
        continueGame = new FontActor("CONTINUE", screenWidth * 5 / 8, screenHeight * 0.5f/8);
        // Creates main menu background
        menuBG = new Background("background");
        mainMenuStage.addActor(menuBG);
        mainMenuStage.addActor(newGame);
        mainMenuStage.addActor(continueGame);

        Gdx.input.setInputProcessor(mainMenuStage);
    }

    @Override
    public void show() {
        Gdx.app.log("MainMenu:", "show");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mainMenuStage.act(Gdx.graphics.getDeltaTime());
        mainMenuStage.draw();

        if (newGame.getTouch()) {
            host.reset();
            host.setScreen(new TalkScreen(host));
        } else if(continueGame.getTouch()) {
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
        newGame.dispose();
        continueGame.dispose();
        menuBG.dispose();
        mainMenuStage.dispose();
        Gdx.app.log("MainMenu", "disposed");
    }

}
