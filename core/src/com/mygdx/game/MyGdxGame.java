package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game {
	SpriteBatch batch;

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
     * Game modes to change game mechanics depending on current stage.
     */
    private int gameMode;
    public static final int ADVENTURE = 1;
    public static final int RAT_RACE = 2;

	private MainMenuScreen mainMenu;

	public SpriteBatch getSpriteBatch() {
		return batch;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
        currentStage = 1; // Korjaa, kun tehdään muisti osio!
        // !! LEVEL 3 SET TO AVAILABLE FOR TESTING !!
        availableStage = new boolean[]{AVAILABLE, NOT_AVAILABLE, AVAILABLE, NOT_AVAILABLE};
		mainMenu = new MainMenuScreen(this);
		// moves to main menu
		setScreen(mainMenu);
	}

	@Override
	public void render () {
		super.render();

	}

	@Override
	public void dispose () {

        batch.dispose();
        Gdx.app.log("MyGdxGame", "disposed");
	}

    /**
     * Sets current stage.
     *
     * @param stage     stage number that will be set as a current stage
     *
     */
    public void setCurrentStage(int stage) {
        currentStage = stage;
    }

    /**
     * Returns current stage
     *
     * @return          current stage
     */
    public int getCurrentStage() {
        return currentStage;
    }

    /**
     * Returns current stage
     *
     * @param nextStage     stage that will be unlocked
     */
    public void unlocStage(int nextStage) {
        availableStage[nextStage] = AVAILABLE;
    }

    /**
     * Checks is the stage currently able to play or not.
     *
     * @param stageNumber   stage number wanted to check
     * @return              whether the stage is unlocked or not
     */
    public boolean getStageAvailability(int stageNumber) {
        return availableStage[stageNumber -1];
    }

    public int getGameMode() {
        return gameMode;
    }
    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }
}
