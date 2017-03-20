package com.mygdx.game;

import com.badlogic.gdx.Game;
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
	public static final int UP = 3;
	public static final int DOWN = 3;


	private MainMenuScreen mainMenu;

	public SpriteBatch getSpriteBatch() {
		return batch;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
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
	}
}
