package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

/**
 * Created by possumunnki on 7.3.2017.
 */

public class Player {
    private World world;
    private MyGdxGame host;
    private boolean positionReset = false;
    public boolean gameOver2 = false;

    // Walk animation sprite sheet.png
    private Texture walkTexture;
    private Animation<TextureRegion> mummoWalkAnim;
    private TextureRegion mummoWalkCurrentFrame;

    private Sound jumpSound1;
    private boolean isWalking = false;

    /**
     * Sprite to change players texture direction
     *
     */
    private Sprite playerSprite;
    private Texture mummoTexture;
    private Sprite ratMountSprite;

    // Rat mount combo
    private Texture ratMountTexture;
    private Body ratMountBody;


    private Texture ratRunTexture;
    private Animation<TextureRegion> ratRunAnim;
    private TextureRegion ratRunCurrentFrame;

    // If player is mounted, this is true
    private boolean isMounted = false;

    private Body playerBody;
    private Body footBody;
    private final float PLAYER_WIDTH = 0.5f;
    private final float PLAYER_HEIGHT = 1f;
    private boolean onTheGround = true;
    // Running speed
    private final float MAX_SPEED = 5.0f;
    // Mounted speed
    private final float MOUNTED_MAX_SPEED = 5.3f;
    private final float JUMP_VELOCITY = 7.0f;
    private final float MOUNTED_JUMP_VELOCITY = 12.0f;

    private BodyDef footBodyDef;
    private final boolean RIGHT = true;
    private final boolean LEFT = false;
    private boolean playerDirection = RIGHT;


    public Player(World world, MyGdxGame host) {
        this.world = world;
        this.host = host;

        jumpSound1 = Gdx.audio.newSound(Gdx.files.internal("Jump.wav"));

        mummoTexture = new Texture("mummo1.png");
        playerSprite = new Sprite(mummoTexture);
        playerSprite.setX(MyGdxGame.SCREEN_WIDTH / 2);
        playerSprite.setY(MyGdxGame.SCREEN_HEIGHT / 2);
        playerSprite.setSize(PLAYER_WIDTH,
                PLAYER_HEIGHT);

        //rat mount
        ratMountTexture = new Texture("ratMount.png");
        ratMountSprite = new Sprite(ratMountTexture);
        ratMountSprite.setX(MyGdxGame.SCREEN_WIDTH / 2);
        ratMountSprite.setY(MyGdxGame.SCREEN_HEIGHT / 2);
        ratMountSprite.setSize(ratMountTexture.getWidth() / 100f,
                ratMountTexture.getHeight() / 100f);


        // Set player start position according to current level
        if(host.getCurrentStage() == 1) {
            playerBody = world.createBody(getDefinitionOfBody(host.SCREEN_WIDTH / 2,
                    host.SCREEN_HEIGHT / 2));
        } else if(host.getCurrentStage() == 2) {
            playerBody = world.createBody(getDefinitionOfBody(host.SCREEN_WIDTH / 2 - 2,
                    host.SCREEN_HEIGHT / 2 + host.SCREEN_HEIGHT * 1.5f));
        } else if(host.getCurrentStage() == 3) {
            playerBody = world.createBody(getDefinitionOfBody(MyGdxGame.SCREEN_WIDTH / 2,
                    host.SCREEN_HEIGHT / 4 + host.SCREEN_HEIGHT * 1.5f));
        } else if(host.getCurrentStage() == 4) {
            playerBody = world.createBody(getDefinitionOfBody(MyGdxGame.SCREEN_WIDTH / 2,
                    (host.SCREEN_HEIGHT / 4 + host.SCREEN_HEIGHT * 2f) + 4));
        } else if(host.getCurrentStage() == 5) {
            playerBody = world.createBody(getDefinitionOfBody(host.SCREEN_WIDTH / 2,
                    host.SCREEN_HEIGHT / 2 + 1));
        }

        playerBody.createFixture(getPlayerFixtureDefinition());

        footBody = world.createBody(getFootDefinitionOfBody(host));
        footBody.createFixture(getFootFixtureDefinition());
        playerBody.setUserData("player");
        footBody.setUserData("foot");
        // fixes players body
        playerBody.setFixedRotation(true);
        footBody.setFixedRotation(true);

        // joints player body and foot body.
        RevoluteJointDef rDef = new RevoluteJointDef();
        rDef.bodyA = playerBody;
        rDef.bodyB = footBody;
        rDef.localAnchorB.set(0.04f, 0.41f); // I don't know how these values works, but it still works.

        world.createJoint(rDef);

        walkTexture = new Texture("mummoWalk.png");
        mummoWalkAnim = new Animation<TextureRegion>(1/5f,
                Utilities.transformToFrames(walkTexture, 5, 1));


        ratRunTexture = new Texture("ratMountAnim.png");
        ratRunAnim = new Animation<TextureRegion>(1/6f,
                Utilities.transformToFrames(ratRunTexture, 6, 1));


    }


    public void draw(SpriteBatch sb, float stateTime, MyGdxGame host) {

        mummoWalkCurrentFrame = mummoWalkAnim.getKeyFrame(stateTime, true);

        ratRunCurrentFrame = ratRunAnim.getKeyFrame(stateTime, true);

        /**
         * Mummo walk animation
         */
        if(isWalking) {

            // IF player is currently in stage 3 and rat race-part
            if(host.getGameMode() == host.RAT_RACE &&
                    host.getCurrentStage() == 3 &&
                    playerBody.getPosition().x > 10) {
                mummoWalkAnim = ratRunAnim;
                isMounted = true;
            // in stage 4, start right away in rat form
            } else if(host.getGameMode() == host.RAT_RACE &&
                    host.getCurrentStage() == 4) {
                mummoWalkAnim = ratRunAnim;
                isMounted = true;
            }


            if(!isMounted) {
                sb.draw(mummoWalkCurrentFrame,
                        playerBody.getPosition().x - playerSprite.getWidth() / 2,
                        playerBody.getPosition().y - playerSprite.getHeight() / 2,
                        0.5f,
                        1f);
            } else {

                //NOTE: THIS FRAME IS MOUNTED FRAME
                sb.draw(mummoWalkCurrentFrame,
                        playerBody.getPosition().x - ratMountSprite.getWidth() / 2,
                        playerBody.getPosition().y - ratMountSprite.getHeight() / 2,
                        2f,
                        1f);
            }

        } else {
            playerSprite.draw(sb);
        }

        if(host.getGameMode() == host.RAT_RACE && playerBody.getPosition().x > 10 && !isWalking) {
            playerSprite = ratMountSprite;
        }

    }

    /**
     * Moves player with arrow key
     *
     * @param host
     */
    public void movePlayer(MyGdxGame host) {

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            playerBody.applyForceToCenter(new Vector2(MAX_SPEED, 0f), true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            playerBody.applyForceToCenter(new Vector2(-MAX_SPEED, 0f), true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            playerBody.setLinearVelocity(0, 10f);
            Gdx.app.log("log", "p1 pos x" + playerBody.getPosition().x);

        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            Gdx.app.log("log", "player x/y: " + playerBody.getPosition());
            jump(host);
        }

        setPlayerSpritePosition();
        setFootBodyPos(host);
    }

    public void movePlayer(float knobPercentX, float knobPercentY, MyGdxGame host) {


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

        playerBody.setLinearVelocity(MAX_SPEED * knobPercentX, playerBody.getLinearVelocity().y);

        // playerBody.applyForceToCenter(new Vector2(-2.5f, 0f), true);
        if(knobPercentY > 0.5f) {
            jump(host);
        }

        // If player falls below screen
        if(playerBody.getPosition().y < -1) {
            // Clear velocity (dropping of the screen)
            playerBody.setLinearVelocity(new Vector2(0,0));
            Gdx.app.log("offscreen", "player Y-position" + playerBody.getPosition().y);

            gameOver2 = true;
        }


        setPlayerSpritePosition();
        setFootBodyPos(host);
    }

    /**
     * Moves player automatically.
     *
     * @param host
     */
    public void moveMountedPlayer(MyGdxGame host)  {
        positionReset = false;
        playerBody.setLinearVelocity(MOUNTED_MAX_SPEED, playerBody.getLinearVelocity().y);
        isWalking = true;
        setPlayerSpritePosition();
        setFootBodyPos(host);

        Gdx.app.log("log", "p1 pos x" + playerBody.getPosition().x);

        if(playerBody.getPosition().y < -1) {
            // Clear velocity (dropping of the screen)
            playerBody.setLinearVelocity(new Vector2(0,0));
            Gdx.app.log("offscreen", "player Y-position" + playerBody.getPosition().y);

            gameOver2 = true;
            /**
            playerBody.setTransform(new Vector2(MyGdxGame.SCREEN_WIDTH / 2, MyGdxGame
                    .SCREEN_HEIGHT + PLAYER_WIDTH*2), 0);

            positionReset = true;

            footBody.setTransform(new Vector2(MyGdxGame.SCREEN_WIDTH / 2, MyGdxGame
                    .SCREEN_HEIGHT + PLAYER_WIDTH*2), 0);
             */
        }
    }

    /**
     * lets player jump, when ever player touches ground
     *
     **/
    public void jump(MyGdxGame host) {
        if (onTheGround) {
            if(host.getGameMode() == host.ADVENTURE) {
                playerBody.applyLinearImpulse(new Vector2(0f, JUMP_VELOCITY),
                        playerBody.getWorldCenter(), true);
            } else if(host.getGameMode() == host.RAT_RACE) {
                playerBody.applyLinearImpulse(new Vector2(0f, MOUNTED_JUMP_VELOCITY),
                        playerBody.getWorldCenter(), true);
            }

            if(host.getSoundEffect() == true) {
                jumpSound1.play(0.3f);

            }
            onTheGround = false;
        }
    }

    /**
     * sets position of footBody so that the position will be always foot
     */
    public void setFootBodyPos(MyGdxGame host) {

        if(host.getCurrentStage() == 1) {
            footBodyDef.position.set(playerSprite.getX(),
                    playerSprite.getY());
        } else if(host.getCurrentStage() == 2) {
            footBodyDef.position.set(playerSprite.getX(),
                    playerSprite.getY() + MyGdxGame.SCREEN_HEIGHT); // position of Y must be bit lower than
        } else if(host.getCurrentStage() == 3) {
            footBodyDef.position.set(playerSprite.getX(),
                    playerSprite.getY() + MyGdxGame.SCREEN_HEIGHT);// playerBody
        } else if(host.getCurrentStage() == 4) {
            footBodyDef.position.set(playerSprite.getX(),
                    playerSprite.getY() + MyGdxGame.SCREEN_HEIGHT + 5f);// playerBody
        } else if(host.getCurrentStage() == 5) {
            footBodyDef.position.set(playerSprite.getX(),
                    playerSprite.getY());
        }
    }

    public BodyDef getDefinitionOfBody(float positionX, float positionY) {
        // Body Definition
        BodyDef myBodyDef = new BodyDef();
        // It's a body that moves
        myBodyDef.type = BodyDef.BodyType.DynamicBody;
        // Initial position is centered up
        // This position is the CENTER of the shape!
        myBodyDef.position.set( positionX, positionY);

        return myBodyDef;
    }

    public BodyDef getFootDefinitionOfBody(MyGdxGame host) {
        // Body Definition
        footBodyDef = new BodyDef();
        // It's a body that moves
        footBodyDef.type = BodyDef.BodyType.DynamicBody;
        // Initial position is centered up
        // This position is the CENTER of the shape!
        setFootBodyPos(host);

        return footBodyDef;
    }

    public FixtureDef getPlayerFixtureDefinition() {
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

    public FixtureDef getFootFixtureDefinition() {
        FixtureDef footFixtureDef = new FixtureDef();

        // Mass per square meter (kg^m2)
        footFixtureDef.density = 0f;

        // How bouncy object? Very bouncy [0,1]
        footFixtureDef.restitution = 0.0f;

        // How slipper object? [0,1]
        footFixtureDef.friction = 0.0f;

        PolygonShape footBox = new PolygonShape();
        footBox.setAsBox( PLAYER_WIDTH / 2 - 0.1f, // bit smaller width than player
                            PLAYER_HEIGHT / 10); // height could be pretty small


        // Add the shape to the fixture
        footFixtureDef.shape = footBox;

        return footFixtureDef;
    }

    public void dispose() {
        mummoTexture.dispose();
        walkTexture.dispose();
        ratMountTexture.dispose();
        ratRunTexture.dispose();
    }

    public void setPlayerSpritePosition() {
        playerSprite.setPosition(playerBody.getPosition().x - playerSprite.getWidth() / 2,
                playerBody.getPosition().y - playerSprite.getHeight() / 2);
    }

    /**
     * When players moving direction changes, flips players texture.
     *
     * @param direction     players current direction
     */
    public void changeDirection(boolean direction) {
        if(direction != playerDirection) {
            playerDirection = direction;
            playerSprite.flip(true, false);
            Utilities.flip(mummoWalkAnim);
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

    public Body getFootBody() {
        return footBody;
    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public boolean getPositionReset() {
        return positionReset;
    }

    public boolean getGameOver2() {
        return gameOver2;
    }
}
