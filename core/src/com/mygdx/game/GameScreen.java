package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;

/**
 * Created by possumunnki on 7.3.2017.
 */

public class GameScreen implements Screen, Input.TextInputListener, GestureDetector.GestureListener {

    /**
     * Map size is 70 x 60 tiles.
     */
    private final int TILES_AMOUNT_WIDTH = 160;
    private final int TILES_AMOUNT_HEIGHT = 64;

    /**
     * One tile is 32.
     */
    private final int TILE_WIDTH = 32;
    private final int TILE_HEIGHT = 32;

    /**
     * World in pixels
     * WORLD_HEIGHT_PIXELS = 42 * 32 = 1344 pixels
     * WORLD_WIDTH_PIXELS  = 42 * 32 = 1344 pixels
     */
    int WORLD_WIDTH_PIXELS  = TILES_AMOUNT_WIDTH  * TILE_WIDTH;
    int WORLD_HEIGHT_PIXELS = TILES_AMOUNT_HEIGHT * TILE_HEIGHT;

    private MyGdxGame host;
    private SpriteBatch batch;
    private World world;
    private OrthographicCamera camera;
    private float deltaTime;
    private float stateTime;
    private Player player;
    private LightDoll lightDoll;
    private Box2DDebugRenderer debugRenderer;
    private Rectangle screenRectangle;
    private Vector3 touchPos;
    /**
     * Debug renderer setting, set false to disable debug render
     */
    private boolean isDebugOn = true;


    private TiledMapRenderer tiledMapRenderer;
    private TiledMap tiledMap;

    private Stage stage;
    private Controller1 controller1;

    private LightSetup lightSetup;

    private InputMultiplexer inputMultiplexer;



    public GameScreen(MyGdxGame host) {

        this.host = host;
        batch = host.getSpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                host.SCREEN_WIDTH,
                host.SCREEN_HEIGHT);

        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();
        player = new Player(world);
        lightDoll = new LightDoll(player, world);
        lightSetup = new LightSetup(world, lightDoll, player);

        tiledMap = new TmxMapLoader().load("testMap_1.tmx");
        tiledMapRenderer = new OrthoCachedTiledMapRenderer(tiledMap, 1/100f);
        Utilities.transformWallsToBodies("world-wall-rectangles", "world-wall", tiledMap, world);
        //Utilities.transformWallsToBodies("wall-rectangles", "wall", tiledMap, world);
        Utilities.transformWallsToBodies("wall2-rectangles", "wall", tiledMap, world);
        Utilities.transformWallsToBodies("ground-rectangles", "ground", tiledMap, world);



        controller1 = new Controller1(MyGdxGame.SCREEN_WIDTH / 10, MyGdxGame.SCREEN_HEIGHT / 5);

        //Create a Stage and add TouchPad
        stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        stage.addActor(controller1.getTouchpad());

        // allows to set multiple inputprocessor
        inputMultiplexer = new InputMultiplexer();
        // adds touchpad
        setUpTouchArea();

        inputMultiplexer.addProcessor(new GestureDetector(this));
        inputMultiplexer.addProcessor(stage);
        // adds gesture detector

        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    public World getWorld() {
        return world;
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        controller1.moveTouchPad();
        player.movePlayer(controller1.getTouchpad().getKnobPercentX(),
                controller1.getTouchpad().getKnobPercentY());
        player.movePlayer();
        lightDoll.moveLightDoll(player);
        deltaTime = Gdx.graphics.getDeltaTime();
        stateTime += deltaTime;
        lightDoll.setDollDefPos(player);
        tiledMapRenderer.setView(camera);
        moveCamera();
        camera.update();
        tiledMapRenderer.render();

        // uses debug renderer if boolean value is true
        if(isDebugOn) {
            debugRenderer.render(world,camera.combined);
        }

        batch.begin();
        // doHeavyStuff();
        player.draw(batch, stateTime);
        lightDoll.draw(batch);
        batch.end();

        /**
         * BOX2D LIGHT-RELATED BEGIN
         */
        lightSetup.render(camera, stepped);
        /**
         * BOX2D LIGHT-RELATED END
         */

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        doPhysicsStep(deltaTime);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                /*String userDataA = (String) (contact.getFixtureA().getBody().getUserData());
                String userDataB = (String) (contact.getFixtureB().getBody().getUserData());

                if(userDataA.equals("collectable")) {
                    contact.setEnabled(false);
                    removalBodies.add(contact.getFixtureA().getBody());
                    clearCollectable();
                }
                if(userDataB.equals("collectable")) {
                    contact.setEnabled(false);
                    removalBodies.add(contact.getFixtureB().getBody());
                    clearCollectable();
                }
                */
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                Body body1 = contact.getFixtureA().getBody();
                Body body2 = contact.getFixtureB().getBody();


                if (body1.getUserData() != null ) {

                    if (body1.getUserData().equals("player")) {
                        //Gdx.app.log("collision1.1", "Dump");
                        if( body2.getUserData().equals("ground")) {
                            //Gdx.app.log("collision1.2", "Dump");
                            player.setOnTheGround();
                        }


                    }
                } else if (body2.getUserData() != null) {

                    if (body2.getUserData().equals("player")) {
                        Gdx.app.log("collision2.1", "Dump");
                        if(body1.getUserData().equals("ground")) {
                            //jump = false;
                            //doubleJump = false;
                            Gdx.app.log("collision2.2", "Dump");
                            player.setOnTheGround();
                        }

                    }
                }
            }
        });
    }

    private double accumulator = 0;
    private float TIME_STEP = 1 / 60f;
    private boolean stepped;

    private void doPhysicsStep(float deltaTime) {
        float frameTime = deltaTime;
        // If it took ages (over 4 fps, then use 4 fps)
        // Avoid of "spiral of death"
        if(deltaTime > 1 / 4f) {
            frameTime = 1 / 4f;
        }

        accumulator += frameTime;

        stepped = false;
        while (accumulator >= TIME_STEP) {
            // It's fixed time step!
            world.step(TIME_STEP, 8, 3);
            accumulator -= TIME_STEP;
            stepped = true;
        }
    }

    public void doHeavyStuff() {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    }

    @Override
    public void dispose() {
        player.dispose();
        controller1.dispose();
        batch.dispose();

        /**
         * BOX2D LIGHT-RELATED
         */
        lightSetup.dispose();
    }

    @Override
    public void input(String text) {

    }

    @Override
    public void canceled() {

    }


    private void moveCamera() {

        if (player.getPlayerBody().getPosition().x < MyGdxGame.SCREEN_WIDTH / 2) {
            camera.position.x = MyGdxGame.SCREEN_WIDTH / 2;
        } else if (player.getPlayerBody().getPosition().x > WORLD_WIDTH_PIXELS / 100f - MyGdxGame.SCREEN_WIDTH /2) {
            camera.position.x = WORLD_WIDTH_PIXELS / 100f - MyGdxGame.SCREEN_WIDTH / 2;
        } else {
            camera.position.x = player.getPlayerBody().getPosition().x;
        }



        if(player.getPlayerBody().getPosition().y < MyGdxGame.SCREEN_HEIGHT / 2) {
            camera.position.y = MyGdxGame.SCREEN_HEIGHT / 2;
        } else if (player.getPlayerBody().getPosition().y > WORLD_HEIGHT_PIXELS / 100f - MyGdxGame.SCREEN_HEIGHT /2) {
            camera.position.y = WORLD_HEIGHT_PIXELS / 100f - MyGdxGame.SCREEN_HEIGHT / 2;
        } else {
            camera.position.y = player.getPlayerBody().getPosition().y;
        }

        /*if(player.getPlayerBody().getPosition().y > WORLD_HEIGHT_PIXELS - WINDOW_HEIGHT) {
            camera.position.y = WORLD_HEIGHT_PIXELS - WINDOW_HEIGHT;
        }*/

        camera.update();
    }

    private void setUpTouchArea() {
        touchPos = new Vector3();
        screenRectangle = new Rectangle(camera.viewportWidth / 2,
                0,
                camera.viewportWidth / 2,
                camera.viewportHeight);

    }

    private boolean screenRectangleTouched(float x, float y) {
        return screenRectangle.contains(x,y);
    }

    private void screenToWorldCoordinates(float x, float y) {
        camera.unproject(touchPos.set(x,y,0));
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        screenToWorldCoordinates(touchPos.x,touchPos.y);
        Gdx.app.log("X", "" + x);
        Gdx.app.log("Y", "" + y);
        if (screenRectangleTouched(touchPos.x, touchPos.y)) {
            Gdx.app.log("touchDown", "touched");
        }

        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

        lightDoll.throwLightDoll(velocityX, velocityY);
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
