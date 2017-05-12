package fi.tamk.tiko.gitd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FillViewport;

import fi.tamk.tiko.gitd.MainMenuScreen.MainMenuScreen;

/**
 * Created by possumunnki on 10.5.2017.
 */

public class LogoScreen implements Screen {
    private MyGdxGame host;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    private float stageWidth;
    private float stageHeight;

    private Stage logoStage;

    private LogoActor logo1Actor;
    private LogoActor logo2Actor;


    private final int LOGO1 = 1;
    private final int LOGO2 = 2;

    public LogoScreen(MyGdxGame host) {
        this.host = host;
        this.batch = host.getSpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                stageWidth,
                stageHeight);
        stageWidth = host.SCREEN_WIDTH * 100f;
        stageHeight = host.SCREEN_HEIGHT * 100f;

        logoStage = new Stage(new FillViewport(stageWidth, stageHeight), batch);

        logo1Actor = new LogoActor(LOGO1, 0, 0);
        logo2Actor = new LogoActor(LOGO2, 0, 0);

        logoStage.addActor(logo1Actor);
        logoStage.addActor(logo2Actor);
        addFadeAction();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        logoStage.act();
        logoStage.draw();

        if (Gdx.input.justTouched()) {
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

    }

    @Override
    public void dispose() {
        logo1Actor.dispose();
        logo2Actor.dispose();
    }

    private void addFadeAction() {
        logo2Actor.addAction(Actions.fadeOut(0f));
        logo1Actor.addAction(
                Actions.sequence(
                        Actions.fadeIn(1.0f),
                        Actions.fadeOut(1.0f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                logo2Actor.addAction(
                                        Actions.sequence(
                                                Actions.fadeOut(0f),
                                                Actions.fadeIn(1.0f)
                                        ));
                            }
                        })));
    }
}
