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
 * Implements main screen, where player can config settings of language or sounds.
 * Player can also decide whether start the game at the same point than last time or
 * begin the new game.
 *
 * @author Akio Ide
 * @version 1.0
 * @since 2017-05-12
 */

public class MainMenuScreen implements Screen {

    private MyGdxGame host;

    private final int BGM = 1;
    private final int SOUND_EFFECT = 2;

    private final boolean ON = true;
    private final boolean OFF = false;

    /**
     * Screen's size in pixel.
     */
    private float screenWidth = host.SCREEN_WIDTH * 100f;
    private float screenHeight = host.SCREEN_HEIGHT * 100f;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    /**
     * Need to make text buttons.
     */
    private String newGameString;
    private String continueString;
    private Stage mainMenuStage;
    private FontActor newGame;
    private FontActor continueGame;

    /**
     * Icons to config sounds.
     */
    private SoundIconActor soundEffectActor;
    private SoundIconActor bgmActor;

    /**
     * Icons to config languages.
     */
    private LanguageIconActor finnishActor;
    private LanguageIconActor englishActor;

    /**
     * Background texture.
     */
    private Background menuBG;

    /**
     * Back ground music of main menu.
     */
    private Music mainMenuBGM;

    /**
     * Sounds to test SFX setting.
     */
    private Sound jumpSound1, jumpSound2, jumpSound3;

    public MainMenuScreen(MyGdxGame host) {

        this.host = host;
        batch = host.getSpriteBatch();

        // sets camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                screenWidth,
                screenHeight);
        // imports background music
        mainMenuBGM = Gdx.audio.newMusic(Gdx.files.internal("bgm1.mp3"));
        mainMenuBGM.setVolume(0.4f);

        // imports some SFX
        jumpSound1 = Gdx.audio.newSound(Gdx.files.internal("sfx/Granny_hop_001.wav"));
        jumpSound2 = Gdx.audio.newSound(Gdx.files.internal("sfx/Granny_hop_002.wav"));
        jumpSound3 = Gdx.audio.newSound(Gdx.files.internal("sfx/Granny_hop_003.wav"));

        // creates to stage to make possible to actors act.
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
        // creates finnish -icon
        finnishActor = new LanguageIconActor(host,
                screenWidth * 0,
                screenHeight * 4/5,
                host.FINNISH);

        // creates english -icon
        englishActor = new LanguageIconActor(host,
                screenWidth * 0,
                screenHeight * 3 / 5,
                host.ENGLISH);

        // Creates main menu background
        menuBG = new Background("background");

        // adds actors on stage
        mainMenuStage.addActor(menuBG);
        mainMenuStage.addActor(newGame);
        mainMenuStage.addActor(continueGame);
        mainMenuStage.addActor(soundEffectActor);
        mainMenuStage.addActor(bgmActor);
        mainMenuStage.addActor(finnishActor);
        mainMenuStage.addActor(englishActor);

        // activates actors that works as buttons
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

        // if music is on, then it plays music
        if (host.getMusic() == ON) {
            mainMenuBGM.play();
        // pauses music if music is off
        } else if (host.getMusic() == OFF) {
            mainMenuBGM.pause();
        }

        mainMenuStage.act(Gdx.graphics.getDeltaTime());
        mainMenuStage.draw();

        // whenever player touches new game the game starts at the beginning
        if (newGame.getTouch()) {
            // resets unlocked stages
            host.reset();
            // moves to talk screen
            host.setScreen(new TalkScreen(host));
        } else if (continueGame.getTouch()) {
            // moves to map screen
            host.setScreen(new MapScreen(host));
        }

        // sets text buttons so that it changes texts when player changes language setting
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
                // plays one sound randomly from 3 sounds.
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
        // Sets whether sound effect is on or off. Then draw-method
        // prints different textures depending on it.
        soundEffectActor.setSound(host.getSoundEffect());

        // when player touches music -icon
        if (bgmActor.getTouch()) {
            host.setMusic();
            bgmActor.setTouchFalse();
        }

        // Sets whether music is on or off. Then draw-method
        // prints different textures depending on it.
        bgmActor.setSound(host.getMusic());
    }
}
