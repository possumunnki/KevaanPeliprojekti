package com.mygdx.game.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.GameScreen.Allies.Player;
import com.mygdx.game.MyGdxGame;

/**
 * Created by possumunnki on 31.3.2017.
 */

public class ExclamationMarkActor extends Actor {
    private Texture exclamationTexture;
    private boolean touch = false;
    /**
     * coordinate
     */
    private float printX;
    private float printY;
    private Sprite exclamationSprite;

    public ExclamationMarkActor() {
        exclamationTexture = new Texture("exclamation_mark.png");
        exclamationSprite = new Sprite(exclamationTexture);
        setSize(exclamationTexture.getWidth(), exclamationTexture.getHeight());
        addListener(new exclamationListener());
        scaleAction();
    }

    public void draw(Batch batch, float alpha) {
        batch.draw(exclamationSprite,
                getX(),
                getY(),getOriginX(),
                getOriginY(),
                getWidth(),
                getHeight(),
                getScaleX(),
                getScaleY(),
                getRotation());
    }

    public void act(float delta) {
        super.act(delta);

    }

    /**
     * Scales the
     */
    private void scaleAction() {
        Action scaleUpAction = Actions.scaleTo(2f, 2f, 0.5f);
        Action scaleDownAction = Actions.scaleTo(1f, 1f, 0.5f);
       addAction( Actions.forever(
                Actions.sequence(
                        scaleUpAction,
                        scaleDownAction
                )
        ));
    }

    /**
     * Sets Exclamation-mark's position so that it will appear right up of the player.
     * @param player In this method, used to get players position and width of the player texture.
     *
     * @param worldWidth World's width in pixel
     */
    public void setExclamationMarkPosition(Player player, float worldWidth, float worldHeight) {
        // player's position in pixel
        float playerPositionX = player.getPlayerBody().getPosition().x * 100f;
        float playerPositionY = player.getPlayerBody().getPosition().y * 100f;

        // screen size in pixel
        float screenWidth = MyGdxGame.SCREEN_WIDTH * 100f;
        float screenHeight = MyGdxGame.SCREEN_HEIGHT * 100f;

        // exclamation-mark's distance from the player in pixel
        float distanceWidth = player.getPlayerSprite().getWidth() * 50f;
        float distanceHeight = player.getPlayerSprite().getHeight() * 50f;

        // when camera is centralized on player
        printX = screenWidth / 2 + distanceWidth;
        // whenever player is near the left world wall
        if (playerPositionX < screenWidth / 2) {
            printX = playerPositionX  + distanceWidth;
        }
        // whenever player is near the right world wall
        if (playerPositionX > worldWidth - screenWidth / 2) {
            printX = screenWidth - (worldWidth - playerPositionX ) + distanceWidth;
        }

        // when camera is centralized on player
        printY = screenHeight / 2 + distanceHeight;
        // whenever player is near the ground
        if(playerPositionY < screenHeight / 2) {
            printY = playerPositionY + distanceHeight;
        }
        // whenever player is near the world roof
        if(playerPositionY > worldHeight - screenHeight / 2) {
            printY = screenHeight - (worldHeight - playerPositionY) + distanceHeight;
        }

        setPosition(printX, printY);
        setBounds(getX(), getY() ,getWidth(), getHeight());

    }

    public boolean getTouch() {
        return touch;
    }

    public void dispose() {
        exclamationTexture.dispose();
    }

    public void setTouch(boolean touch){
        this.touch = touch;
    }
    //public void setEx


    class exclamationListener extends InputListener {
        public boolean touchDown(InputEvent event,
                                 float x,
                                 float y,
                                 int pointer,
                                 int button) {
            touch = true;
            Gdx.app.log("tuch", "detected");
            return false;
        }

        public void touchUp(InputEvent event,
                            float x,
                            float y,
                            int pointer,
                            int button) {
            touch = true;
        }
    }

}
