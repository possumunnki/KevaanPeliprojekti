package fi.tamk.tiko.gitd.MainMenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;

import fi.tamk.tiko.gitd.FontActor;
import fi.tamk.tiko.gitd.MapScreen.MapScreen;
import fi.tamk.tiko.gitd.MyGdxGame;
import fi.tamk.tiko.gitd.TalkScreen.TalkScreen;

/**
 * Created by possumunnki on 7.3.2017.
 */

public class MainMenuScreen implements Screen {

    private MyGdxGame host;

    private final int BGM = 1;
    private final int SOUND_EFFECT = 2;

    private final boolean ON = true;
    private final boolean OFF = false;

    /**
     * Screen's size in pixel
     */
    private float screenWidth = host.SCREEN_WIDTH * 100f;
    private float screenHeight = host.SCREEN_HEIGHT * 100f;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private String newGameString;
    private String continueString;
    private Stage mainMenuStage;
    private FontActor newGame;
    private FontActor continueGame;
    private SoundIconActor soundEffectActor;
    private SoundIconActor bgmActor;
    private LanguageIconActor finnishActor;
    private LanguageIconActor englishActor;

    private Background menuBG;
    private Music mainMenuBGM;
    private Sound jumpSound1, jumpSound2, jumpSound3;

    public MainMenuScreen(MyGdxGame host) {

        this.host = host;
        batch = host.getSpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                screenWidth,
                screenHeight);
        mainMenuBGM = Gdx.audio.newMusic(Gdx.files.internal("bgm1.mp3"));
        mainMenuBGM.setVolume(0.4f);

        jumpSound1 = Gdx.audio.newSound(Gdx.files.internal("sfx/Granny_hop_001.wav"));
        jumpSound2 = Gdx.audio.newSound(Gdx.files.internal("sfx/Granny_hop_002.wav"));
        jumpSound3 = Gdx.audio.newSound(Gdx.files.internal("sfx/Granny_hop_003.wav"));

        mainMenuStage = new Stage(new FillViewport(screenWidth, screenHeight), batch);

        // sets strings depending on locale
        setStrings();
        // creates "START" text button
        newGame = new FontActor(newGameString,
                screenWidth * 5 / 8,
                screenHeight * 2 / 8);

        // creates "CONTINUE" text button
        continueGame = new FontActor(continueString,
                screenWidth * 5 / 8,
                screenHeight * 0.5f / 8);

        // creates sound effect icon
        soundEffectActor = new SoundIconActor(screenWidth * 9 / 10,
                screenHeight * 6.5f / 8,
                host.getSoundEffect(),
                SOUND_EFFECT);

        // creates music icon
        bgmActor = new SoundIconActor(screenWidth * 9 / 10,
                screenHeight * 5 / 8,
                host.getMusic(),
                BGM);

        finnishActor = new LanguageIconActor(host,
                screenWidth * 0,
                screenHeight * 4/5,
                host.FINNISH);

        englishActor = new LanguageIconActor(host,
                screenWidth * 0,
                screenHeight * 3 / 5,
                host.ENGLISH);

        // Creates main menu background
        menuBG = new Background("background");
        mainMenuStage.addActor(menuBG);
        mainMenuStage.addActor(newGame);
        mainMenuStage.addActor(continueGame);
        mainMenuStage.addActor(soundEffectActor);
        mainMenuStage.addActor(bgmActor);
        mainMenuStage.addActor(finnishActor);
        mainMenuStage.addActor(englishActor);

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

        configSounds();

        if (host.getMusic() == ON) {
            mainMenuBGM.play();
        } else if (host.getMusic() == OFF) {
            mainMenuBGM.pause();
        }

        mainMenuStage.act(Gdx.graphics.getDeltaTime());
        mainMenuStage.draw();

        if (newGame.getTouch()) {
            host.reset();
            host.setScreen(new TalkScreen(host));
        } else if (continueGame.getTouch()) {
            host.setScreen(new MapScreen(host));
        }

        setStrings();
        newGame.setFontString(newGameString);
        continueGame.setFontString(continueString);

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

        mainMenuBGM.dispose();
        jumpSound1.dispose();
        jumpSound2.dispose();
        jumpSound3.dispose();
        soundEffectActor.dispose();
        mainMenuStage.dispose();
    }

    /**
     * Sets Strings to print different text buttons depending on language.
     */
    private void setStrings() {
        if (host.locale == host.ENGLISH) {
            newGameString = "NEW GAME";
            continueString = "CONTINUE";
        } else if (host.locale == host.FINNISH) {
            newGameString = "UUSI PELI";
            continueString = "JATKA";
        }
    }


    /**
     * Sets BGM and sound effect on/ off
     */
    private void configSounds() {
        // when player touches sound effect -icon
        if (soundEffectActor.getTouch()) {
            host.setSoundEffect();
            soundEffectActor.setTouchFalse();
            if (host.getSoundEffect()) {
                int jumpRandom = (int) Math.floor(Math.random() * 3) + 1;

                switch (jumpRandom) {
                    case 1:
                        jumpSound1.play(1f);
                        break;
                    case 2:
                        jumpSound2.play(1f);
                        break;
                    case 3:
                        jumpSound3.play(1f);
                        break;
                }
            }
        }

        soundEffectActor.setSound(host.getSoundEffect());

        // when player touches music -icon
        if (bgmActor.getTouch()) {
            host.setMusic();
            bgmActor.setTouchFalse();
        }

        bgmActor.setSound(host.getMusic());
    }
}
