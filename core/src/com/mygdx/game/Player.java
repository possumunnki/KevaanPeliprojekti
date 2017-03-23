package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by possumunnki on 7.3.2017.
 */

public class Player {
    private World world;
    private Texture kirbieGif;
    private Animation<TextureRegion> kirbieAnimation;
    private TextureRegion kirbieCurrentFrame;

    // Walk animation sprite sheet.png
    private Texture walkTexture;
    private Animation<TextureRegion> mummoWalkAnim;
    private TextureRegion mummoWalkCurrentFrame;

    private boolean isWalking = false;

    private Sprite playerSprite;
    private Texture mummoTexture;
    private Body playerBody;
    private final float PLAYER_WIDTH = 0.5f;
    private final float PLAYER_HEIGHT = 1f;
    private boolean onTheGround = true;
    private final float MAXSPEED = 5.0f;
    private final float JUMP_VELOSITY = 4.0f;
    private boolean isFixed = true;


    private final boolean RIGHT = true;
    private final boolean LEFT = false;
    private boolean playerDirection = RIGHT;

    public Player(World world) {
        this.world = world;

        mummoTexture = new Texture("mummo1.png");
        playerSprite = new Sprite(mummoTexture);
        playerSprite.setX(MyGdxGame.SCREEN_WIDTH / 2);
        playerSprite.setY(MyGdxGame.SCREEN_HEIGHT / 2);
        playerSprite.setSize(PLAYER_WIDTH,
                PLAYER_HEIGHT);

        playerBody = world.createBody(Utilities.getDefinitionOfBody());
        playerBody.createFixture(getFixtureDefinition());
        playerBody.setUserData("player");

        playerBody.setFixedRotation(isFixed);

        kirbieGif = new Texture("kirbie2.png");
        kirbieAnimation = new Animation<TextureRegion>(1/6f,
                Utilities.transformToFrames(kirbieGif, 6, 1));

        walkTexture = new Texture("mummoWalk.png");
        mummoWalkAnim = new Animation<TextureRegion>(1/5f,
                Utilities.transformToFrames(walkTexture, 5, 1));

    }


    public void draw(SpriteBatch sb, float stateTime) {
        kirbieCurrentFrame = kirbieAnimation.getKeyFrame(stateTime, true);

        mummoWalkCurrentFrame = mummoWalkAnim.getKeyFrame(stateTime, true);

        /**
         * Mummo walk animation
         */
        if(isWalking) {
            sb.draw(mummoWalkCurrentFrame,
                    playerBody.getPosition().x - playerSprite.getWidth() / 2,
                    playerBody.getPosition().y - playerSprite.getHeight() / 2,
                    0.5f,
                    1f);
        } else {
            playerSprite.draw(sb);
        }
    }

    public void movePlayer() {
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            playerBody.applyForceToCenter(new Vector2(MAXSPEED, 0f), true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            playerBody.applyForceToCenter(new Vector2(- MAXSPEED, 0f), true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            jump();
        }

        setPlayerSpritePosition();
    }

    public void movePlayer(float knobPercentX, float knobPercentY) {


            // playerBody.applyForceToCenter(new Vector2(2.5f, 0f), true);
        if(knobPercentX > 0) {
            changeDirection(RIGHT);
            isWalking = true;

        } else if(knobPercentX < 0) {
            changeDirection(LEFT);
            isWalking = true;
        } else {
            isWalking = false;
        }

        playerBody.setLinearVelocity(MAXSPEED * knobPercentX, playerBody.getLinearVelocity().y);

            // playerBody.applyForceToCenter(new Vector2(-2.5f, 0f), true);
        if(knobPercentY > 0.3f) {
            jump();
        }

        setPlayerSpritePosition();
    }

    public void jump() {
        if (onTheGround) {
            playerBody.applyLinearImpulse(new Vector2(0f, JUMP_VELOSITY), playerBody.getWorldCenter(), true);
            onTheGround = false;
        }
    }

    public FixtureDef getFixtureDefinition() {
        FixtureDef playerFixtureDef = new FixtureDef();

        // Mass per square meter (kg^m2)
        playerFixtureDef.density = 2.5f;

        // How bouncy object? Very bouncy [0,1]
        playerFixtureDef.restitution = 0.0f;

        // How slipper object? [0,1]
        playerFixtureDef.friction = 0.0f;

        PolygonShape playerBox = new PolygonShape();
        //circleshape.setRadius(0.5f);
        playerBox.setAsBox( PLAYER_WIDTH / 2, PLAYER_HEIGHT / 2);


        // Add the shape to the fixture
        playerFixtureDef.shape = playerBox;

        return playerFixtureDef;
    }

    public void dispose() {
        mummoTexture.dispose();
        kirbieGif.dispose();
        walkTexture.dispose();

    }

    public void setPlayerSpritePosition() {
        playerSprite.setPosition(playerBody.getPosition().x - playerSprite.getWidth() / 2,
                playerBody.getPosition().y - playerSprite.getHeight() / 2);
    }

    public void changeDirection(boolean direction) {
        if(direction != playerDirection) {
            playerDirection = direction;
            playerSprite.flip(true, false);
        }
    }

    public void setOnTheGround() {
        this.onTheGround = true;
    }

    public World getWorld() {
        return world;
    }

    public Body getPlayerBody() {
        return playerBody;
    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

}
