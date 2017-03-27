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
    public static final boolean AVAILABLE = true;
    public static final boolean NOT_AVAILABLE = false;
    private int currentStage;
    private boolean[] availableStage;
	private MainMenuScreen mainMenu;

	public SpriteBatch getSpriteBatch() {
		return batch;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
        currentStage = 1;
        availableStage = new boolean[]{AVAILABLE, NOT_AVAILABLE, AVAILABLE, NOT_AVAILABLE};
		mainMenu = new MainMenuScreen(this);
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

    public void setCurrentStage(int stage) {
        currentStage = stage;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public void unlocStage(int nextStage) {
        availableStage[nextStage] = AVAILABLE;
    }

    public boolean getStageAvailability(int stageNumber) {
        return availableStage[stageNumber -1];
    }
}
