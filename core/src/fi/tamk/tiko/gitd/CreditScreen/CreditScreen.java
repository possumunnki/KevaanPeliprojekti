package fi.tamk.tiko.gitd.CreditScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;

import fi.tamk.tiko.gitd.FontActor;
import fi.tamk.tiko.gitd.MainMenuScreen.MainMenuScreen;
import fi.tamk.tiko.gitd.MyGdxGame;

/**
 * Credit screen-class, only used in the ending of the last stage
 * Created by Juz3 on 10/05/2017.
 */

public class CreditScreen implements Screen {

    private MyGdxGame host;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    private FontActor quit;

    private Stage stage;
    private Texture creditBG;

    /**
     *
     *
     * @param host extension of libgdx game-class, needed for current stage-check
     */
    public CreditScreen(MyGdxGame host) {
        this.host = host;
        batch = host.getSpriteBatch();

        creditBG = new Texture("victoryScreen.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                host.SCREEN_WIDTH,
                host.SCREEN_HEIGHT);

        quit = new FontActor("QUIT",
                host.SCREEN_WIDTH  * 1/2 * 100f,
                host.SCREEN_HEIGHT * 10/25 * 100f);

        stage = new Stage(new FillViewport(host.SCREEN_WIDTH * 100f, host.SCREEN_HEIGHT * 100f), batch);

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
        batch.draw(creditBG, 0, 0,
                creditBG.getWidth(),
                creditBG.getHeight());

        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        if(quit.getTouch()) {
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
        creditBG.dispose();
        quit.dispose();
        stage.dispose();
    }
}
