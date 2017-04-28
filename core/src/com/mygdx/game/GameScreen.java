package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by possumunnki on 7.3.2017.
 */

public class GameScreen implements Screen, Input.TextInputListener, GestureDetector.GestureListener {
    private MyGdxGame host;

    private final boolean ON = true;
    private final boolean OFF = false;

    /**
     * When ever pause is on, the game will be paused so that player and enemy stops moving.
     */
    private boolean pause = OFF;

    /**
     * Debug renderer setting, set false to disable debug render
     */
    private boolean isDebugOn = ON;

    /**
     * Makes player immortal to make easy to develop the game.
     */
    private boolean immortality = OFF;

    /**
     * Map sizes.
     */
    private int tilesAmountWidth;
    private int tilesAmountHeight;

    /**
     * One tile is 32 pixels.
     */
    private final int TILE_WIDTH = 32;
    private final int TILE_HEIGHT = 32;

    /**
     * World in pixels
     * WORLD_HEIGHT_PIXELS = 42 * 32 = 1344 pixels
     * WORLD_WIDTH_PIXELS  = 42 * 32 = 1344 pixels
     */
    int worldWidthPixels;
    int worldHeightPixels;

    private float stageWidth = host.SCREEN_WIDTH * 100f;
    private float stageHeight = host.SCREEN_HEIGHT * 100f;

    private SpriteBatch batch;
    private World world;
    private OrthographicCamera camera;
    Viewport viewport;

    private float deltaTime;
    private float stateTime;
    private Player player;
    private LightDoll lightDoll;
    private Box2DDebugRenderer debugRenderer;
    private Rectangle screenRectangle;
    private Vector3 touchPos;
    private boolean goal;
    private boolean gameOver = false;



    private TiledMapRenderer tiledMapRenderer;
    private TiledMap tiledMap;

    /**
     * Stage where is all the buttons / controllers from the beginning
     */
    private Stage stage;

    /**-*
     * stage for exclamation stage.
     */
    private Stage exclamationStage;

    private Controller1 controller1;
    private ExclamationMarkActor exclamationMarkActor;
    private PauseResumeButtonActor pauseResumeButtonActor;

    private LightSetup lightSetup;

    private InputMultiplexer inputMultiplexer;

    private BodyHandler bodyHandler;

    private boolean downLeft, downRight, downMiddle;

    private Texture dialog1;



    private Cat cat;

    private Texture kekkonen;


    public GameScreen(MyGdxGame host) {

        this.host = host;
        batch = host.getSpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                host.SCREEN_WIDTH,
                host.SCREEN_HEIGHT);
        viewport = new FitViewport(host.SCREEN_WIDTH, host.SCREEN_HEIGHT, camera);

        world = new World(new Vector2(0, -9.8f), true);
        stage = new Stage(new FillViewport(stageWidth, stageHeight), batch);

        bodyHandler = new BodyHandler(world, host);
        debugRenderer = new Box2DDebugRenderer();
        player = new Player(world, host);
        lightDoll = new LightDoll(player, world);
        lightSetup = new LightSetup(world, lightDoll, player);

        setGameStage();
        setGameController();

        exclamationMarkActor = new ExclamationMarkActor();
        // creates pause/resume button and adds on the game screen
        pauseResumeButtonActor = new PauseResumeButtonActor();
        stage.addActor(pauseResumeButtonActor);
        //Create a Stage and add TouchPad


        exclamationStage = new Stage(new FillViewport(stageWidth, stageHeight), batch);
        exclamationStage.addActor(exclamationMarkActor);


        // allows to set multiple input processors
        inputMultiplexer = new InputMultiplexer();
        // adds touchpad
        setUpTouchArea();

        inputMultiplexer.addProcessor(new GestureDetector(this));
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(exclamationStage);
        // adds gesture detector

        Gdx.input.setInputProcessor(inputMultiplexer);

        dialog1 = new Texture("chatboxWithText.png");

        cat = new Cat(world, host);

        kekkonen = new Texture("ukk.png");

    }

    /**
     * Sets tile map, width and height of the game stage depending on current stage.
     * Also transforms tile maps rectangles to bodies and sets gameMode.
     *
     */
    private void setGameStage() {
        // if current game stage is 1.
        if(host.getCurrentStage() == 1) {
            tiledMap = new TmxMapLoader().load("stage1.tmx");
            // sets tiles amount on the game stage
            tilesAmountWidth = 200;
            tilesAmountHeight = 30;
            host.setGameMode(host.ADVENTURE);

        } else if(host.getCurrentStage() == 2) {
            tiledMap = new TmxMapLoader().load("stage2.tmx");
            tilesAmountWidth = 200;
            tilesAmountHeight = 30;
            host.setGameMode(host.ADVENTURE);

        } else if(host.getCurrentStage() == 3) {
            tiledMap = new TmxMapLoader().load("stage3.tmx");
            tilesAmountWidth = 400;
            tilesAmountHeight = 30;
            // turns rat race on, it changes game control
            host.setGameMode(host.RAT_RACE);

        } else if(host.getCurrentStage() == 4) {
            tiledMap = new TmxMapLoader().load("stage4.tmx");
            tilesAmountWidth = 440;
            tilesAmountHeight = 50;
            // turns rat race on, it changes game control
            host.setGameMode(host.RAT_RACE);
        }

        /**
         * Adds spike bodies to maps which contain spikes
         */
        if(host.getCurrentStage() == 2 || host.getCurrentStage() == 4) {
            Utilities.transformWallsToBodies("spike-rectangles", "spike", tiledMap, world);
        }

        Utilities.transformWallsToBodies("wall-rectangles", "wall", tiledMap, world);
        Utilities.transformWallsToBodies("goal-rectangle", "goal", tiledMap, world);

        tiledMapRenderer = new OrthoCachedTiledMapRenderer(tiledMap, 1/100f);

        worldWidthPixels = tilesAmountWidth  * TILE_WIDTH;
        worldHeightPixels = tilesAmountHeight * TILE_HEIGHT;
    }

    public void setGameController() {
        if(host.getGameMode() == host.ADVENTURE) {
            // adds game on pad in the game
            controller1 = new Controller1(host, 0, 0);
            stage.addActor(controller1.getTouchpad());
        } else if(host.getGameMode() == host.RAT_RACE) {

        }
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
        tiledMapRenderer.render();
        if(pause == OFF) {
            if(host.getGameMode() == host.ADVENTURE) {
                controller1.moveTouchPad();
                // enables player to move with game on pad
                player.movePlayer(controller1.getTouchpad().getKnobPercentX(),
                                  controller1.getTouchpad().getKnobPercentY(), host);
                // enables player to move with arrow key
                player.movePlayer(host);
            } else if(host.getGameMode() == host.RAT_RACE) {
                // enables player to move automatically
                player.moveMountedPlayer(host);
            }



            lightDoll.moveLightDoll(player);


            // If current map is containing rats and voodoo dolls
            if(host.getCurrentStage() == 1 || host.getCurrentStage() == 2) {
                bodyHandler.callEnemyWalk();
            }


            exclamationMarkActor.setExclamationMarkPosition(player);

            deltaTime = Gdx.graphics.getDeltaTime();
            stateTime += deltaTime;
            lightDoll.setDollDefPos(player);
            tiledMapRenderer.setView(camera);
            moveCamera();
            camera.update();

            world.getBodies(bodyHandler.getBodies());

            // uses debug renderer if boolean value is true
            if(isDebugOn) {
                debugRenderer.render(world,camera.combined);
            }
            // draw dialog when player touches exclamation mark
            if(host.getCurrentStage() == 1 && exclamationMarkActor.getTouch()) {
                batch.begin();
                batch.draw(dialog1, player.getPlayerSprite().getX(), 4f, dialog1.getWidth() / 100f, dialog1.getHeight() / 100f);
                batch.end();
                exclamationMarkActor.remove();
            }
        }

        // shows position of player
        if(Gdx.input.isKeyPressed(Input.Keys.P)) {
            Gdx.app.log("Player body posX", "" + player.getPlayerBody().getPosition().x);
        }


        activatePause();
        cat.moveCat(host, player, world);

        batch.begin();
        player.draw(batch, stateTime, host);
        lightDoll.draw(batch);

        bodyHandler.drawAllBodies(batch);


        cat.draw(batch, stateTime, host, player);


        /**
         * Draws Urho Kekkonen-doll to the end of stage 2
         */
        if(host.getCurrentStage() == 2) {

            batch.draw(kekkonen, 61.5f, 4.5f, 0.5f, 1f);
        }

        batch.end();

        // Render lights
        lightSetup.render(camera, stepped);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();



        if(pause == OFF) {
            if(player.getPlayerBody().getPosition().x > 5.6f &&
                    player.getPlayerBody().getPosition().x < 10.0f &&
                    host.getCurrentStage() == 1) {
                exclamationStage.act();
                exclamationStage.draw();
            }
            doPhysicsStep(deltaTime);
        }


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
                        if (body2.getUserData().equals("goal")) {

                            goal = true;
                        }
                    }

                    if(immortality == !ON) {
                        // when player touches an enemy
                        if (body1.getUserData().equals(bodyHandler.callVoodooGetter()) ||
                                body1.getUserData().equals(bodyHandler.callRatGetter())) {
                            if(body2.getUserData().equals("player")) {
                                //switch to game over screen
                                Gdx.app.log("log", "gameover");
                                gameOver = true;
                            }
                        }

                        // when players foots touches an enemy
                        if(body1.getUserData().equals("foot")) {
                            if(body2.getUserData().equals("spike")) {
                                //switch to game over screen
                                Gdx.app.log("log", "gameover");
                                gameOver = true;
                            }
                        }
                    }


                    /**
                     * Cat collision gameover-check
                     */
                    if(body1.getUserData().equals("player")) {
                        if(body2.getUserData().equals("cat")) {
                            //switch to game over screen
                            Gdx.app.log("log", "gameover");
                            gameOver = true;
                        }
                    }

                    // when player touches goal-rectangle
                    if(body1.getUserData().equals("foot")) {
                        if(body2.getUserData().equals("wall")) {
                            player.setOnTheGround();
                        }
                    }


                } else if (body2.getUserData() != null) {

                    if (body2.getUserData().equals("player")) {
                        Gdx.app.log("collision2.1", "Dump");
                        /*if(body1.getUserData().equals("ground")) {
                            //jump = false;
                            //doubleJump = false;
                            Gdx.app.log("collision2.2", "Dump");
                            //player.setOnTheGround();
                        }*/

                    }

                    if (body2.getUserData().equals("player")) {
                        Gdx.app.log("collision2.1", "Dump");
                        if(body1.getUserData().equals("goal")) {
                            Gdx.app.log("collision2.2", "Dump");
                            goal = true;

                        }

                    }
                }
            }
        });

        // If player falls down, it's game over (instead of going back to start)
        if(player.getGameOver2()) {
            gameOver = true;
        }

        if(host.getCurrentStage() == 1 || host.getCurrentStage() == 2) {
            bodyHandler.clearBodies(world, lightDoll);
        }

        if(goal) {
            host.unlocStage(host.getCurrentStage());
            host.setCurrentStage(host.getCurrentStage() + 1);
            Gdx.app.log("Current Stage", "" + host.getCurrentStage());
            doHeavyStuff();
            host.setScreen(new MapScreen(host));
        } else if(gameOver) {
            doHeavyStuff();
            host.setScreen(new GameOverScreen(host));
        }
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
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void activatePause() {
        if(pauseResumeButtonActor.getTouch() && pauseResumeButtonActor.getStatus()) {
            pause = ON;
        } else if(pauseResumeButtonActor.getTouch()) {
            pause = OFF;
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
        stage.getViewport().update(width, height, false);
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
    public void input(String text) {

    }

    @Override
    public void canceled() {

    }

    /**
     * Changes camera position depending on players position.
     * If player is next to the walls, the camera stops moving to avoid showing out side of the
     * game stage. Otherwise it centralizes the player.
     *
     */
    private void moveCamera() {
        float playerPositionX = player.getPlayerBody().getPosition().x;
        float playerPositionY = player.getPlayerBody().getPosition().y;
        float worldWidthMeter = worldWidthPixels / 100f;
        float worldHeightMerer = worldHeightPixels / 100f;

        camera.position.x = playerPositionX;
        camera.position.y = playerPositionY;

        if (playerPositionX < MyGdxGame.SCREEN_WIDTH / 2) {
            camera.position.x = MyGdxGame.SCREEN_WIDTH / 2;
        }
        if (playerPositionX > worldWidthMeter - MyGdxGame.SCREEN_WIDTH /2) {
            camera.position.x = worldWidthMeter - MyGdxGame.SCREEN_WIDTH / 2;
        }

        if(playerPositionY < MyGdxGame.SCREEN_HEIGHT / 2) {
            camera.position.y = MyGdxGame.SCREEN_HEIGHT / 2;
        }

        if (playerPositionY > worldHeightMerer - MyGdxGame.SCREEN_HEIGHT /2) {
            camera.position.y = worldHeightMerer - MyGdxGame.SCREEN_HEIGHT / 2;
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

    //turha classi atm
    public boolean isPossibleToJump() {
        getPlayerDownPoints(player.getPlayerSprite().getX(),
                            player.getPlayerSprite().getY());

        if(downLeft && downMiddle) {
            return true;
        } else if(downMiddle && downRight) {
            return true;
        } else if(downLeft && downRight) {
            return true;
        } else {
            return false;
        }
    }

    //turha classi atm.
    public void getPlayerDownPoints(float pX, float pY) {
        /*
         * pX and pY are the coordinates of the player.
         * coordinates are calculated from the bottom
         * left position of the texture:
         *
         *       XXXXXX
         *       X    X
         *       X    X
         *       PXXXXX
         *   (px, py)
         */

        float downLeftXPos  = pX;
        float downRightXPos  = player.getPlayerSprite().getWidth() + pX;
        float downMiddleXPos = player.getPlayerSprite().getWidth() / 2 + pX;

        /*
         *        X X X X X X X
         *        X           X
         *        X           X
         *        P X X P X X P
         *        ^     ^     ^
         *        |     |     |
         * downLeftXpos |    downRightXpos
         *         downMiddleXpos
         */

        /*
         *        X X X X X X X
         *        X           X
         *        X           X
         *        P X X X X X X
         *        ^
         *        |
         *     downLeft
         *
         */
        downLeft = isGround(downLeftXPos, pY);

        /*
         *        X X X X X X X
         *        X           X
         *        X           X
         *        X X X P X X X
         *              ^
         *              |
         *          downMiddle
         *
         */
        downMiddle = isGround(downMiddleXPos, pY);
        /*
         *        X X X X X X X
         *        X           X
         *        X           X
         *        X X X X X X P
         *                    ^
         *                    |
         *                  downRight
         *
         */
        downLeft = isGround(downRightXPos, pY);


    }
    // turha classi atm.
    public boolean isGround(float x, float y) {

        // Calculate player coordinates to tile coordinates
        // example, (34,34) => (1,1)
        x = x * 100f / TILE_WIDTH; // 32 = TILE_WIDTH
        y = y * 100f / TILE_HEIGHT; // 32 = TILE_HEIGHT
        int indexX = (int) x ;
        int indexY = (int) y ;

        TiledMapTileLayer wallCells = (TiledMapTileLayer) tiledMap.getLayers().get("wall-tiles");

        // Is the coordinate / cell free?
        if(wallCells.getCell(indexX, indexY) != null) {
            // There was a cell, it's ground
            return true;
        } else {
            // There was no cell, it's not ground
            return false;
        }
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
        if(host.getGameMode() == host.RAT_RACE) {
            player.jump(host);
        }
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if(host.getGameMode() == host.ADVENTURE && controller1.getIsTouched()) {
            // won't do any, if touch pad is touched
        } else {
            lightDoll.throwLightDoll(velocityX, velocityY);
        }
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

    @Override
    public void dispose() {
        if(host.getGameMode() == host.ADVENTURE) {
            controller1.dispose();
        }
        player.dispose();
        stage.dispose();

        lightDoll.dispose();
        bodyHandler.dispose();

        /**
         * BOX2D LIGHTS
         */
        lightSetup.dispose();

        dialog1.dispose();

        cat.dispose();

        world.dispose();

        if(isDebugOn) {
            debugRenderer.dispose();
        }

        kekkonen.dispose();

        Gdx.app.log("GameScreen","disposed");
    }
}
