package com.mygdx.game.TalkScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.GameScreen.GameScreen;
import com.mygdx.game.MapScreen.MapScreen;
import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;

/**
 * Created by possumunnki on 17.4.2017.
 */

public class TalkScreen implements Screen {
    private MyGdxGame host;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    ArrayList<Speech> speeches;
    private float screenWidth;
    private float screenHeight;

    private final int BOTH = 0;
    private final int GRANDMA = 1;
    private final int LIGHT_DOLL = 2;

    private String text;
    private SkipActor skip;
    private int currentSpeech = 0;
    private Stage skipStage;

    public TalkScreen(MyGdxGame host) {
        this.host = host;
        screenWidth = host.SCREEN_WIDTH * 100f;
        screenHeight = host.SCREEN_HEIGHT * 100f;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                screenWidth,
                screenHeight);
        this.batch = host.getSpriteBatch();
        skipStage = new Stage(new FillViewport(screenWidth,
                                               screenHeight),
                                               batch);
        skip = new SkipActor(0, 0);

        skipStage.addActor(skip);
        Gdx.input.setInputProcessor(skipStage);
        speeches = new ArrayList<Speech>();
        addTalk();
    }
    @Override
    public void show() {
        Gdx.app.log("TalkScreen:", "show");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        // add stage to add skip button
        try {
            speeches.get(currentSpeech).draw(batch, screenWidth, screenHeight);
        } catch (Exception e) {
            // draws last speach of the array
            speeches.get(speeches.size() - 1).draw(batch, screenWidth, screenHeight);
        }
        batch.end();
        skipStage.act();
        skipStage.draw();

        // when player touches the screen, it shows next speech
        if (Gdx.input.justTouched()) {
            currentSpeech++;
        }

        if(currentSpeech == speeches.size() || skip.getTouch()) {
            if(host.getCurrentStage() == 2){
                host.unlockStage(host.getCurrentStage());
                host.setCurrentStage(host.getCurrentStage() + 1);
                host.saveUnlockedStages(host.getCurrentStage());
                host.setUnlockedStages(host.getUnlockedStages());
                host.setScreen(new MapScreen(host));
            } else {
                host.setScreen(new GameScreen(host));
            }

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
        for(int i = 0; i < speeches.size(); i++) {
            speeches.get(i).dispose();
        }
        skip.dispose();
        skipStage.dispose();
        Gdx.app.log("TalkScreen", "disposed");
    }

    private void addTalk() {
        if(host.getCurrentStage() == 1) {
            text = "Herran pieksut!\nMitä ihmettä tapahtui?\nMiksi kaikki on niin valtavaa?";
            speeches.add(new Speech(GRANDMA, text));

            text = "Huminaa muminaa";
            speeches.add(new Speech(LIGHT_DOLL, text));

            text = "Kääk! Hetkinen,etkös sinä \n ole se minkä takia minä\n tähän kartanoon murtauduin?";
            speeches.add(new Speech(GRANDMA, text));

            text = "Hums mums";
            speeches.add(new Speech(LIGHT_DOLL, text));

            text = "Sinähän leijut! Ja puhut!\nTai mumiset.. ";
            speeches.add(new Speech(GRANDMA, text));

            text = "Joko tämä tönö on kirottu\ntai unohdin sittenkin \nottaa lääkkeeni.";
            speeches.add(new Speech(GRANDMA, text));

            text = "...";
            speeches.add(new Speech(BOTH, text));

            text = "Eiköhän lähdetä\n kiireen vilkkaa pois täältä.";
            speeches.add(new Speech(GRANDMA, text));

        } else if(host.getCurrentStage() == 2) {
            text = "Mitä juuri tapahtui?\n Olo on kuin mankeloidulla";
            speeches.add(new Speech(GRANDMA, text));

            text = "Missähän me nyt mahdamme \nluurata?";
            speeches.add(new Speech(GRANDMA, text));

            text = "Kaikki näyttää pimeässä ja\n rotan kokoisena ihan \nsamalta.";
            speeches.add(new Speech(GRANDMA, text));

            text = "Hum—";
            speeches.add(new Speech(LIGHT_DOLL, text));

            text = "Hys nyt mumiseva paperipaino!\n Kuuletko tuon myhäilyn?";
            speeches.add(new Speech(GRANDMA, text));

            text = "Leijuvia auringonjumalia, \nvihaisia voodoo-nukkeja, \nepämääräistä käkätystä...";
            speeches.add(new Speech(GRANDMA, text));

            text = "Miksi minä edes tulin tänne?";
            speeches.add(new Speech(GRANDMA, text));

            text = "...";
            speeches.add(new Speech(LIGHT_DOLL, text));

            text = "Ai niin...";
            speeches.add(new Speech(GRANDMA, text));
        }
    }

    private void chopText() {

    }


}
