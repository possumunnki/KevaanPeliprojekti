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
 * Implements screen that shows logos.
 * It shows two textures and after that the game moves to main menu screen.
 *
 * @author Akio Ide
 * @version 1.0
 * @since 2017-05-14
 */

public class LogoScreen implements Screen {
    private MyGdxGame host;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    /**
     * Whether screen is touched or not.
     */
    private boolean nextScreen = false;

    /**
     * Size of the stage in pixel.
     */
    private float stageWidth;
    private float stageHeight;

    private Stage logoStage;

    private LogoActor logo1Actor;
    private LogoActor logo2Actor;

    /**
     * Logo types that is used on LogoActor
     */
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
        // adds fade in and out actions to actors
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

        // moves to main menu screen, when player touches screen or actors has done acctions
        if (Gdx.input.justTouched() || nextScreen) {
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

    /**
     * Adds fade action to actors.
     */
    private void addFadeAction() {
        // fades out logo2 actor first so that it is not shown at the start
        logo2Actor.addAction(Actions.fadeOut(0f));
        // adds some actions to logo2 -actor.
        logo1Actor.addAction(
                // actor performs actions in turn
                Actions.sequence(
                        Actions.fadeIn(2.0f),
                        Actions.fadeOut(1.0f),
                        // makes it possible to do something after actions
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                // adds some actions to logo2-actor
                                addFadeActionAndSetNextScreen();
                            }
                        })));
    }

    /**
     * Adds some actions to logo2 -actor. After actions, the screen moves to MainMenuScreen
     * by setting nextScreen true.
     */
    private void addFadeActionAndSetNextScreen(){
        logo2Actor.addAction(
                Actions.sequence(
                        Actions.fadeIn(3f),
                        Actions.fadeOut(1f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                nextScreen = true;
                            }
                        })));
    }
}
