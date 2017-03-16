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
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by possumunnki on 7.3.2017.
 */

public class Player {
    private World world;
    private Texture kirbieGif;
    private Animation<TextureRegion> kirbieAnimation;
    private TextureRegion kirbieCurrentFrame;
    private Sprite playerSprite;
    private Texture kirbieTexture;
    private Body kirbieBody;
    private float radius;
    private boolean onTheGround = true;
    private final float MAXSPEED = 5.0f;

    public Player(World world) {
        this.world = world;

        kirbieTexture = new Texture("kirbie1.png");
        playerSprite = new Sprite();
        playerSprite.setX(MyGdxGame.SCREEN_WIDTH / 2);
        playerSprite.setY(MyGdxGame.SCREEN_HEIGHT / 2);
        playerSprite.setSize(kirbieTexture.getWidth()/ 100,
                kirbieTexture.getHeight()/ 100);

        kirbieBody = world.createBody(Utilities.getDefinitionOfBody());
        kirbieBody.createFixture(getFixtureDefinition());

        kirbieGif = new Texture("kirbie2.png");
        kirbieAnimation = new Animation<TextureRegion>(1/6f,
                Utilities.transformToFrames(kirbieGif, 6, 1));

        radius = ((CircleShape) kirbieBody.getFixtureList().get(0).getShape()).getRadius();

    }


    public void draw(SpriteBatch sb, float stateTime) {
        kirbieCurrentFrame = kirbieAnimation.getKeyFrame(stateTime, true);
        sb.draw(kirbieCurrentFrame,
                kirbieBody.getPosition().x - radius,
                kirbieBody.getPosition().y - radius,
                radius * 2,
                radius * 2);

        //sb.draw(ballTexture, playerSprite.getX(), playerSprite.getY());
    }

    public void movePlayer() {
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            kirbieBody.applyForceToCenter(new Vector2(MAXSPEED, 0f), true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            kirbieBody.applyForceToCenter(new Vector2(- MAXSPEED, 0f), true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            jump();
        }
    }

    public void movePlayer(float knobPercentX, float knobPercentY) {



            // kirbieBody.applyForceToCenter(new Vector2(2.5f, 0f), true);
            kirbieBody.setLinearVelocity(MAXSPEED * knobPercentX, kirbieBody.getLinearVelocity().y);

            // kirbieBody.applyForceToCenter(new Vector2(-2.5f, 0f), true);



            kirbieBody.applyForceToCenter(new Vector2(0f, 20f * knobPercentY), true);



        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            jump();
        }
    }

    public void jump() {
        if (onTheGround) {
            kirbieBody.applyLinearImpulse(new Vector2(0f, 0.5f), kirbieBody.getWorldCenter(), true);
        }
    }
    public FixtureDef getFixtureDefinition() {
        FixtureDef playerFixtureDef = new FixtureDef();

        // Mass per square meter (kg^m2)
        playerFixtureDef.density = 1;

        // How bouncy object? Very bouncy [0,1]

        // ei niin bouncy :D
        playerFixtureDef.restitution = 0.3f;

        // How slipper object? [0,1]
        playerFixtureDef.friction = 0.5f;

        // Create circle shape.
        CircleShape circleshape = new CircleShape();
        circleshape.setRadius(0.5f);

        // Add the shape to the fixture
        playerFixtureDef.shape = circleshape;
        return playerFixtureDef;
    }

    public void dispose() {
        kirbieTexture.dispose();
        kirbieGif.dispose();
    }

    public World getWorld() {
        return world;
    }

    public Body getPlayerBody() {
        return kirbieBody;
    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

}
