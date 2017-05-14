package fi.tamk.tiko.gitd;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Locale;

import static fi.tamk.tiko.gitd.MyGdxGame.levelProgression;
import static fi.tamk.tiko.gitd.MyGdxGame.levelProgression;
import static fi.tamk.tiko.gitd.MyGdxGame.levelProgression;

public class MyGdxGame extends Game {
    SpriteBatch batch;

    private final boolean ON = true;
    private final boolean OFF = false;

    private boolean unlockAllStages = ON;
    private boolean music;
    private boolean soundEffect;
    /**
     * Screen width in meters
     */
    public static final float SCREEN_WIDTH = 8.0f;

    /**
     * Screen height in meters
     */
    public static final float SCREEN_HEIGHT = 4.8f;

    public static final int STOP = 0;
    public static final int RIGHT = 1;
    public static final int LEFT = 2;

    /**
     * Locale settings.
     */
    public static final int ENGLISH = 1;
    public static final int FINNISH = 2;
    public static int locale;

    public static final int BEGINNING = 1;
    public static final int END = 2;
    public static int levelProgression = BEGINNING;
    /**
     * stage that player is currently playing
     */
    private int currentStage;

    /**
     * list of available stages
     */
    private boolean[] availableStage;

    public static final boolean AVAILABLE = true;
    public static final boolean NOT_AVAILABLE = false;
    /**
     * amount of unlocked stage
     */
    private int unlockedStages;
    /**
     * Game modes to change game mechanics depending on current stage.
     */
    private int gameMode;
    public static final int ADVENTURE = 1;
    public static final int RAT_RACE = 2;
    private Preferences prefs;
    private LogoScreen logoScreen;

    public SpriteBatch getSpriteBatch() {
        return batch;
    }

	@Override
	public void create () {
		batch = new SpriteBatch();
        currentStage = 1; // Korjaa, kun tehdään muisti osio!
        soundEffect = ON;
        // !! LEVEL 3 SET TO AVAILABLE FOR TESTING !!
        availableStage = new boolean[]{AVAILABLE, AVAILABLE, AVAILABLE, AVAILABLE, NOT_AVAILABLE};
        prefs = Gdx.app.getPreferences("GameData");
        restoreGameData();
        setLocale();
        if (unlockAllStages) {
            setUnlockedStages(5);
        }
        logoScreen = new LogoScreen(this);
        // moves to logo screen
        setScreen(logoScreen);
    }

    @Override
    public void render() {
        super.render();

	}

    @Override
    public void dispose() {
        save();
        batch.dispose();
        Gdx.app.log("MyGdxGame", "disposed");
    }

    public void setLocale() {
        int locale = prefs.getInteger("locale", 0);
        if(locale == 0) {
            String defaultLocale = Locale.getDefault().toString();
            if(defaultLocale.equals("fi_FI")) {
                this.locale = FINNISH;
            } else {
                this.locale = ENGLISH;
            }
        } else {
            this.locale = locale;
        }

    }

    /**
     * returns amount of unlocked stages to print right number of points on map screen
     *
     * @return amount of unlocked stages
     */
    public int getUnlockedStages() {
        return unlockedStages;
    }

    /**
     * Sets current stage.
     *
     * @param stage stage number that will be set as a current stage
     */
    public void setCurrentStage(int stage) {
        currentStage = stage;
    }

    /**
     * Returns current stage
     *
     * @return current stage
     */
    public int getCurrentStage() {
        return currentStage;
    }

    /**
     * Returns current stage
     *
     * @param nextStage stage that will be unlocked
     */
    public void unlockStage(int nextStage) {
        availableStage[nextStage] = AVAILABLE;
    }

    /**
     * Checks is the stage currently able to play or not.
     *
     * @param stageNumber stage number wanted to check
     * @return whether the stage is unlocked or not
     */
    public boolean getStageAvailability(int stageNumber) {
        return availableStage[stageNumber - 1];
    }

    /**
     * Returns game mode of current stage to set up the game stage.
     *
     * @return game mode of the current stage
     */
    public int getGameMode() {
        return gameMode;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * Restores save data so that player could continue from the same point than last time.
     */
    public void restoreGameData() {
        setUnlockedStages();
        setSoundConfig();
    }

    /**
     * Checks saved data and sets amount of unlocked stages depending on it.
     */
    public void setUnlockedStages() {
        int unLockedStages = prefs.getInteger("unLockedStage", 1);
        this.unlockedStages = unLockedStages;
    }

    public void setUnlockedStages(int newUnlockedStage) {
        int unLockedStages = prefs.getInteger("unLockedStage", 1);

        if (unLockedStages >= newUnlockedStage) {
            this.unlockedStages = unLockedStages;
        } else {
            this.unlockedStages = newUnlockedStage;
        }

    }

    public void setSoundConfig() {
        boolean soundEffect = prefs.getBoolean("soundEffect", true);
        boolean music = prefs.getBoolean("music", true);

        this.soundEffect = soundEffect;
        this.music = music;
    }

    public void save() {
        saveUnlockedStages(unlockedStages);
        saveSoundSettings();
        saveLocaleSetting();
    }

    /**
     * Saves unlocked stages amount, so that player could continue the game.
     *
     * @param unlockedStages
     */
    public void saveUnlockedStages(int unlockedStages) {
        prefs.putInteger("unLockedStage", unlockedStages);
        prefs.flush();
    }

    public void saveSoundSettings() {
        prefs.putBoolean("music", music);
        prefs.putBoolean("soundEffect", soundEffect);
        prefs.flush();
    }

    public void saveLocaleSetting() {
        prefs.putInteger("locale", locale);
        prefs.flush();
    }
    /**
     * Resets game data.
     */
    public void reset() {
        unlockedStages = 1;
        currentStage = 1;
        prefs.putInteger("unLockedStage", unlockedStages);
        prefs.flush();
    }

    public boolean getMusic() {
        return music;
    }

    /**
     * on/off music.
     * When player touches bgm -icon, this method will be call.
     */
    public void setMusic() {
        if (this.music == ON) {
            music = OFF;
        } else if (this.music == OFF) {
            music = ON;
        }

    }

    public boolean getSoundEffect() {
        return soundEffect;
    }

    /**
     * on/off sound effect.
     * When player touches sound effect -icon, this method will be call.
     */
    public void setSoundEffect() {
        if (this.soundEffect == ON) {
            soundEffect = OFF;
        } else if (this.soundEffect == OFF) {
            soundEffect = ON;
        }
    }
}
