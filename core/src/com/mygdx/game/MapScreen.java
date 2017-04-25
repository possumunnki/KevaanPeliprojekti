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
    private FontActor returnToMainMenu;
    private PointActor stage1Point;
    private PointActor stage2Point;
    private PointActor stage3Point;
    private Stage stage;
    private Texture backGroundTexture;

    //private Background mapScreenBG;

    public MapScreen(MyGdxGame host) {
        this.host = host;
        batch = host.getSpriteBatch();

        backGroundTexture = new Texture("mapSelectBackground.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                host.SCREEN_WIDTH,
                host.SCREEN_HEIGHT);
        nextStage = new FontActor("Next Stage",
                                    host.SCREEN_WIDTH * 6/8 * 100f,
                                    host.SCREEN_HEIGHT * 2/8 * 100f);
        returnToMainMenu = new FontActor("Return to main menu",
                                    host.SCREEN_WIDTH  * 8/8 * 100f,
                                    host.SCREEN_HEIGHT * 1/8 * 100f);
        returnToMainMenu.setFontScale(0.5f);

        stage1Point = new PointActor(host.SCREEN_WIDTH  * 1 / 12 * 100f,
                                     host.SCREEN_HEIGHT * 1 / 6 * 100f);
        stage2Point = new PointActor(host.SCREEN_WIDTH  * 3 / 12 * 100f,
                                     host.SCREEN_HEIGHT * 4.5f / 24 * 100f);
        stage3Point = new PointActor(host.SCREEN_WIDTH * 5 / 12 * 100f,
                                     host.SCREEN_HEIGHT * 3.5f / 12 * 100f);


        stage = new Stage(new FillViewport(host.SCREEN_WIDTH * 100f, host.SCREEN_HEIGHT * 100f), batch);

        //mapScreenBG = new Background("mapScreenBG");

        //stage.addActor(mapScreenBG);
        stage.addActor(nextStage);
        stage.addActor(returnToMainMenu);
        stage.addActor(stage1Point);
        stage.addActor(stage2Point);
        stage.addActor(stage3Point);
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
        batch.draw(backGroundTexture, 0, 0,
                backGroundTexture.getWidth(),
                backGroundTexture.getHeight());

        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        configStage();

        if (nextStage.getTouch()) {
            if(host.getCurrentStage() == 1) {
                host.setScreen(new TalkScreen(host));
            } else {
                host.setScreen(new GameScreen(host));
            }
        } else if(returnToMainMenu.getTouch()) {
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
        backGroundTexture.dispose();
        //mapScreenBG.dispose();
        nextStage.dispose();
        stage1Point.dispose();
        stage2Point.dispose();
        Gdx.app.log("MapScreen", "disposed");
    }

    private void configStage() {
        if (host.getCurrentStage() == 1) {
            if (stage2Point.getTouch() && host.getStageAvailability(2)) {
                host.setCurrentStage(2);
                stage1Point.setTouch(false);
            } else if (stage3Point.getTouch() && host.getStageAvailability(3)) {
                host.setCurrentStage(3);
                stage1Point.setTouch(false);
            } else {
                stage1Point.setTouch(true);
                stage2Point.setTouch(false);
                stage3Point.setTouch(false);
            }

        } else if (host.getCurrentStage() == 2) {
            if (stage1Point.getTouch() && host.getStageAvailability(1)) {
                host.setCurrentStage(1);
                stage2Point.setTouch(false);
            } else if (stage3Point.getTouch() && host.getStageAvailability(3)) {
                host.setCurrentStage(3);
                stage2Point.setTouch(false);
            } else {
                stage1Point.setTouch(false);
                stage2Point.setTouch(true);
                stage3Point.setTouch(false);
            }
        } else if (host.getCurrentStage() == 3) {
            if (stage1Point.getTouch() && host.getStageAvailability(1)) {
                host.setCurrentStage(1);
                stage3Point.setTouch(false);
            } else if (stage2Point.getTouch() && host.getStageAvailability(2)) {
                host.setCurrentStage(2);
                stage3Point.setTouch(false);
            } else {
                stage1Point.setTouch(false);
                stage2Point.setTouch(false);
                stage3Point.setTouch(true);
            }
        }
    }

}
