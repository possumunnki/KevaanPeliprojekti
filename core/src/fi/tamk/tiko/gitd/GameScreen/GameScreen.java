package fi.tamk.tiko.gitd.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
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

import fi.tamk.tiko.gitd.CreditScreen.CreditScreen;
import fi.tamk.tiko.gitd.FontActor;
import fi.tamk.tiko.gitd.GameOverScreen.GameOverScreen;
import fi.tamk.tiko.gitd.GameScreen.Allies.Controller1;
import fi.tamk.tiko.gitd.GameScreen.Allies.LightSetup;
import fi.tamk.tiko.gitd.GameScreen.Enemies.BodyHandler;
import fi.tamk.tiko.gitd.MapScreen.MapScreen;
import fi.tamk.tiko.gitd.MyGdxGame;

import fi.tamk.tiko.gitd.GameScreen.Allies.LightDoll;
import fi.tamk.tiko.gitd.GameScreen.Allies.Player;
import fi.tamk.tiko.gitd.GameScreen.Enemies.Cat;
import fi.tamk.tiko.gitd.TalkScreen.TalkScreen;
import fi.tamk.tiko.gitd.Utilities;

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
    private boolean isDebugOn = OFF;

    /**
     * Makes player immortal to make easy to develop the game.
     */
    private boolean immortality = OFF;

    /**
     * Tile amount in the world.
     */
    private int tilesAmountWidth;
    private int tilesAmountHeight;

    /**
     * One tile is 32 pixels.
     */
    private final int TILE_WIDTH = 32;
    private final int TILE_HEIGHT = 32;

    /**
     * World size in pixels
     */
    int worldWidthPixels;
    int worldHeightPixels;

    /**
     * Size of stage in pixel
     */
    private float stageWidth = host.SCREEN_WIDTH * 100f;
    private float stageHeight = host.SCREEN_HEIGHT * 100f;

    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    Viewport viewport;

    private float deltaTime;
    private float stateTime;

    /**
     * Controllable characters and its set up.
     */
    private Player player;
    private LightDoll lightDoll;
    private LightSetup lightSetup;

    /**
     * enemies on the game
     */
    private BodyHandler bodyHandler;
    private Cat cat;
    private Texture kekkonen;

    private boolean goal;
    private boolean gameOver = false;

    private Music gameBGM;
    private Sound deathSound;
    private Sound bossCallSound;
    private boolean playBossSoundOnce = false;

    private TiledMapRenderer tiledMapRenderer;
    private TiledMap tiledMap;

    /**
     * Stage and actors of buttons / controllers.
     */
    private Stage controllerStage;
    private Controller1 controller1;
    private JumpButtonActor jumpButtonActor;

    /**
     * stage and actor for exclamation mark actor.
     */
    private Stage exclamationStage;
    private ExclamationMarkActor exclamationMarkActor;
    private boolean exclamationButton = false;

    /**
     * Stage and actors fot pause -menu.
     */
    private Stage pauseStage;
    private FontActor resume;
    private FontActor restart;
    private FontActor backToMap;

    /**
     * Stage and actor fot pause/resume -button.
     */
    private Stage pauseResumeStage;
    private PauseResumeButtonActor pauseResumeButtonActor;

    private InputMultiplexer inputMultiplexer;

    public GameScreen(MyGdxGame host) {

        this.host = host;
        batch = host.getSpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                host.SCREEN_WIDTH,
                host.SCREEN_HEIGHT);
        viewport = new FitViewport(host.SCREEN_WIDTH, host.SCREEN_HEIGHT, camera);

        if (host.getMusic() == ON) {
            gameBGM = Gdx.audio.newMusic(Gdx.files.internal("bgm2.mp3"));
            gameBGM.isLooping();
        }

        if (host.getSoundEffect() == ON) {
            deathSound = Gdx.audio.newSound(Gdx.files.internal("MummoDeath.wav"));
            bossCallSound = Gdx.audio.newSound(Gdx.files.internal("GitDbossCall01.wav"));
        }

        world = new World(new Vector2(0, -9.8f), true);

        bodyHandler = new BodyHandler(world, host);
        debugRenderer = new Box2DDebugRenderer();
        player = new Player(world, host);
        lightDoll = new LightDoll(player, world);
        lightSetup = new LightSetup(world, lightDoll, player);

        createPauseMenu();
        setGameStage();

        controllerStage = new Stage(new FillViewport(stageWidth, stageHeight), batch);
        setGameController();

        // creates exclamation button and adds on the stage
        exclamationStage = new Stage(new FillViewport(stageWidth, stageHeight), batch);
        exclamationMarkActor = new ExclamationMarkActor();
        exclamationStage.addActor(exclamationMarkActor);

        // creates pause/resume button and adds on stage
        pauseResumeStage = new Stage(new FillViewport(stageWidth, stageHeight), batch);
        pauseResumeButtonActor = new PauseResumeButtonActor(host);
        pauseResumeStage.addActor(pauseResumeButtonActor);

        // allows to set multiple input processors
        inputMultiplexer = new InputMultiplexer();

        inputMultiplexer.addProcessor(new GestureDetector(this));
        inputMultiplexer.addProcessor(controllerStage);
        inputMultiplexer.addProcessor(exclamationStage);
        inputMultiplexer.addProcessor(pauseStage);
        inputMultiplexer.addProcessor(pauseResumeStage);
        // adds gesture detector

        Gdx.input.setInputProcessor(inputMultiplexer);

        cat = new Cat(world, host);

        kekkonen = new Texture("ukk.png");

    }

    /**
     * Sets tile map, width and height of the game stage depending on current stage.
     * Also transforms tile maps rectangles to bodies and sets gameMode.
     */
    private void setGameStage() {
        // if current game stage is 1.
        if (host.getCurrentStage() == 1) {
            tiledMap = new TmxMapLoader().load("maps/stage1.tmx");
            // sets tiles amount on the game stage
            tilesAmountWidth = 200;
            tilesAmountHeight = 30;
            host.setGameMode(host.ADVENTURE);

        } else if (host.getCurrentStage() == 2) {
            tiledMap = new TmxMapLoader().load("maps/stage2.tmx");
            tilesAmountWidth = 200;
            tilesAmountHeight = 30;
            host.setGameMode(host.ADVENTURE);

        } else if (host.getCurrentStage() == 3) {
            tiledMap = new TmxMapLoader().load("maps/stage3.tmx");
            tilesAmountWidth = 400;
            tilesAmountHeight = 30;
            // turns rat race on, it changes game control
            host.setGameMode(host.RAT_RACE);

        } else if (host.getCurrentStage() == 4) {
            tiledMap = new TmxMapLoader().load("maps/stage4.tmx");
            tilesAmountWidth = 440;
            tilesAmountHeight = 50;
            // turns rat race on, it changes game control
            host.setGameMode(host.RAT_RACE);

        } else if (host.getCurrentStage() == 5) {
            tiledMap = new TmxMapLoader().load("maps/stage5.tmx");
            tilesAmountWidth = 200;
            tilesAmountHeight = 30;
            host.setGameMode(host.ADVENTURE);
        }

        /**
         * Adds spike bodies to maps which contain spikes
         */
        if (host.getCurrentStage() == 1 ||
                host.getCurrentStage() == 2 ||
                host.getCurrentStage() == 4 ||
                host.getCurrentStage() == 5) {
            Utilities.transformWallsToBodies("spike-rectangles", "spike", tiledMap, world);
        }

        Utilities.transformWallsToBodies("wall-rectangles", "wall", tiledMap, world);

        if (host.getCurrentStage() != 5) {
            Utilities.transformWallsToBodies("goal-rectangle", "goal", tiledMap, world);
        }

        tiledMapRenderer = new OrthoCachedTiledMapRenderer(tiledMap, 1 / 100f);

        worldWidthPixels = tilesAmountWidth * TILE_WIDTH;
        worldHeightPixels = tilesAmountHeight * TILE_HEIGHT;
    }

    /**
     * Creates actors and stage for pause menu
     */
    private void createPauseMenu() {
        pauseStage = new Stage(new FillViewport(stageWidth, stageHeight), batch);

        resume = new FontActor("RESUME", stageWidth * 1 / 2, stageHeight * 4 / 8);
        restart = new FontActor("RESTART", stageWidth * 1 / 2, stageHeight * 3 / 8);
        backToMap = new FontActor("BACK TO MAP", stageWidth * 1 / 2, stageHeight * 2 / 8);

        pauseStage.addActor(resume);
        pauseStage.addActor(restart);
        pauseStage.addActor(backToMap);
    }

    public void setGameController() {
        if (host.getGameMode() == host.ADVENTURE) {
            // adds game on pad in the game
            controller1 = new Controller1(host, 0, 0);
            // adds jump-button on the game
            jumpButtonActor = new JumpButtonActor(host);

            controllerStage.addActor(controller1.getTouchpad());
            controllerStage.addActor(jumpButtonActor);
        } else if (host.getGameMode() == host.RAT_RACE) {
            jumpButtonActor = new JumpButtonActor(host);
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
        if (host.getMusic() == ON) {
            gameBGM.play();
            if (pause == ON) {
                gameBGM.setVolume(0.1f);
            } else if (pause == OFF) {
                gameBGM.setVolume(0.4f);
            }
        }

        if (pause == OFF) {
            controlCharacter();

            // If current map is containing rats and voodoo dolls
            if (host.getCurrentStage() == 1
                    || host.getCurrentStage() == 2
                    || host.getCurrentStage() == 5) {
                bodyHandler.callEnemyWalk(host);
            }

            if (host.getCurrentStage() == 5) {
                bodyHandler.callBossAction(host);
            }

            exclamationMarkActor.setExclamationMarkPosition(player, worldWidthPixels, worldHeightPixels);

            deltaTime = Gdx.graphics.getDeltaTime();
            stateTime += deltaTime;
            lightDoll.setDollDefPos(player);
            tiledMapRenderer.setView(camera);
            moveCamera();
            camera.update();

            world.getBodies(bodyHandler.getBodies());

            // uses debug renderer if boolean value is true
            if (isDebugOn) {
                debugRenderer.render(world, camera.combined);
            }
            // draw dialog when player touches exclamation mark

        }

        // shows position of player
        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            Gdx.app.log("Player body posX", "" + player.getPlayerBody().getPosition().x);
            Gdx.app.log("Player body posY", "" + player.getPlayerBody().getPosition().y);
        }

        activatePause();
        cat.moveCat(host, player, world);

        batch.begin();
        player.draw(batch, stateTime, host);
        lightDoll.draw(batch);
        bodyHandler.drawAllBodies(batch, stateTime, player);

        cat.draw(batch, stateTime, host, player);

        /**
         * Draws Urho Kekkonen-doll to the end of stage 2
         */
        if (host.getCurrentStage() == 2) {

            batch.draw(kekkonen, 61.5f, 4.5f, 0.5f, 1f);
        }

        batch.end();

        // Render lights
        lightSetup.render(camera);

        pauseResumeStage.act();
        pauseResumeStage.draw();
        if (pause == ON) {
            pauseStage.act();
            pauseStage.draw();
        } else if (pause == OFF) {
            if (player.getPlayerBody().getPosition().x > 60f &&
                    player.getPlayerBody().getPosition().x < 62f &&
                    host.getCurrentStage() == 2) {
                exclamationStage.act();
                exclamationStage.draw();
                exclamationButton = true;
            } else {
                exclamationButton = false;
            }

            controllerStage.act(Gdx.graphics.getDeltaTime());
            controllerStage.draw();
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


                if (body1.getUserData() != null) {

                    // When player touches the goal area.
                    if (body1.getUserData().equals("player")) {
                        if (body2.getUserData().equals("goal")) {

                            goal = true;
                        }
                    }

                    // If the immortality hack is not used.
                    if (immortality == !ON) {
                        // when player touches an enemy
                        if (body1.getUserData().equals(bodyHandler.callVoodooGetter()) ||
                                body1.getUserData().equals(bodyHandler.callRatGetter()) ||
                                body1.getUserData().equals(bodyHandler.callBossGetter())) {
                            if (body2.getUserData().equals("player")) {
                                //switch to game over screen
                                Gdx.app.log("log", "gameover");
                                gameOver = true;
                            }
                        }

                        // when players foots touches an enemy
                        if (body1.getUserData().equals("foot")) {
                            if (body2.getUserData().equals("spike")) {
                                //switch to game over screen
                                Gdx.app.log("log", "gameover");
                                gameOver = true;
                            }
                        }
                    }


                    /**
                     * Cat collision gameover-check
                     */
                    if (body1.getUserData().equals("player")) {
                        if (body2.getUserData().equals("cat")) {
                            //switch to game over screen
                            Gdx.app.log("log", "gameover");
                            gameOver = true;
                        }
                    }

                    // when player touches the wall.
                    if (body1.getUserData().equals("foot")) {
                        if (body2.getUserData().equals("wall")) {
                            player.setOnTheGround(true);
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
                        if (body1.getUserData().equals("goal")) {
                            Gdx.app.log("collision2.2", "Dump");
                            goal = true;

                        }

                    }
                }
            }
        });

        // If player falls down, it's game over (instead of going back to start)
        if (player.getGameOver2()) {
            gameOver = true;
        }

        if (host.getCurrentStage() == 1 ||
                host.getCurrentStage() == 2 ||
                host.getCurrentStage() == 5) {
            bodyHandler.clearBodies(world, lightDoll);

        }
        if (exclamationMarkActor.getTouch()) {
            if (host.getCurrentStage() == 2 && exclamationButton) {
                exclamationMarkActor.remove();

                host.setScreen(new TalkScreen(host));
            } else {
                exclamationMarkActor.setTouch(false);
            }
        }

        // play boss shout sound effect at stage 5 start
        if (host.getCurrentStage() == 5 && host.getSoundEffect() == ON && (player.getPlayerBody()
                .getPosition().x > 10f && player.getPlayerBody().getPosition().x < 12f)) {

            if (!playBossSoundOnce) {
                bossCallSound.play(0.4f);
                playBossSoundOnce = true;
            }
        }

        detectPauseMenuButtons();

        if (goal) {
            host.unlockStage(host.getCurrentStage());
            host.setCurrentStage(host.getCurrentStage() + 1);
            host.saveUnlockedStages(host.getCurrentStage());
            host.setUnlockedStages(host.getUnlockedStages());

            Gdx.app.log("Current Stage", "" + host.getCurrentStage());
            doHeavyStuff();
            host.setScreen(new MapScreen(host));
        } else if (gameOver) {
            if (host.getSoundEffect() == ON) {
                deathSound.play(0.5f);
            }
            doHeavyStuff();
            host.setScreen(new GameOverScreen(host));
        }

        if(bodyHandler.getSetVictory()) {
            doHeavyStuff();
            host.setScreen(new CreditScreen(host));
        }
    }


    private void controlCharacter() {
        if (host.getGameMode() == host.ADVENTURE) {
            controller1.moveTouchPad();
            // enables player to move with game on pad
            player.movePlayer(controller1.getTouchpad().getKnobPercentX(),
                    controller1.getTouchpad().getKnobPercentY(), host);
            // enables player to move with arrow key
            player.movePlayer(host);
        } else if (host.getGameMode() == host.RAT_RACE) {
            // enables player to move automatically
            player.moveMountedPlayer(host);
        }

        // whenever player touches jump button, grandma jumps
        if (jumpButtonActor.getTouch()) {
            player.jump(host);
            jumpButtonActor.setTouch(false);
        }
        lightDoll.moveLightDoll(player);
    }

    /**
     * When pause is ON, buttons of menu detects touches and resumes game or moves to another
     * screen.
     */
    private void detectPauseMenuButtons() {
        if (pause == ON) {
            // when RESUME -button is touched
            if (resume.getTouch()) {
                pause = OFF;
                pauseResumeButtonActor.setStatus(pause);
                // when RESTART -button is touched
            } else if (restart.getTouch()) {
                host.setScreen(new GameScreen(host));
                // when BACK TO MAP -button is touched
            } else if (backToMap.getTouch()) {
                host.setScreen(new MapScreen(host));
            }
        } else if (pause == OFF) {
            // sets touches false because otherwise actors detects touches and sets touch true
            resume.setTouch(false);
            restart.setTouch(false);
            backToMap.setTouch(false);
        }

    }

    private double accumulator = 0;
    private float TIME_STEP = 1 / 60f;
    private boolean stepped;

    private void doPhysicsStep(float deltaTime) {
        float frameTime = deltaTime;
        // If it took ages (over 4 fps, then use 4 fps)
        // Avoid of "spiral of death"
        if (deltaTime > 1 / 4f) {
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

    /**
     * Triggers pause-mode when player touches pause icon on the screen.
     * It resumes the game if pause is already activated and player touches resume button.
     */
    private void activatePause() {
        if (pauseResumeButtonActor.getTouch() && pauseResumeButtonActor.getStatus()) {
            pause = ON;
        } else if (pauseResumeButtonActor.getTouch()) {
            pause = OFF;
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        controllerStage.getViewport().update(width, height, false);
    }

    @Override
    public void pause() {
        pause = ON;
        pauseResumeButtonActor.setStatus(pause);
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
     */
    private void moveCamera() {
        // player's position in meter
        float playerPositionX = player.getPlayerBody().getPosition().x;
        float playerPositionY = player.getPlayerBody().getPosition().y;

        // world's size in meter
        float worldWidthMeter = worldWidthPixels / 100f;
        float worldHeightMerer = worldHeightPixels / 100f;

        // when player is not near the world walls, camera centralizes the player
        camera.position.x = playerPositionX;
        camera.position.y = playerPositionY;

        // when player is near the right world wall, camera stops moving left
        if (playerPositionX < MyGdxGame.SCREEN_WIDTH / 2) {
            camera.position.x = MyGdxGame.SCREEN_WIDTH / 2;
        }
        // when player is near the left world wall camera stops moving right
        if (playerPositionX > worldWidthMeter - MyGdxGame.SCREEN_WIDTH / 2) {
            camera.position.x = worldWidthMeter - MyGdxGame.SCREEN_WIDTH / 2;
        }

        // when player is near the ground, camera stops moving down
        if (playerPositionY < MyGdxGame.SCREEN_HEIGHT / 2) {
            camera.position.y = MyGdxGame.SCREEN_HEIGHT / 2;
        }
        // when player is near the roof of the world, camera stops moving up
        if (playerPositionY > worldHeightMerer - MyGdxGame.SCREEN_HEIGHT / 2) {
            camera.position.y = worldHeightMerer - MyGdxGame.SCREEN_HEIGHT / 2;
        }

        camera.update();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if (host.getGameMode() == host.RAT_RACE) {
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
        if (pause == OFF) {
            if (host.getGameMode() == host.ADVENTURE && controller1.getIsTouched()) {
                // won't do any, if touch pad is touched
            } else {
                lightDoll.throwLightDoll(velocityX, velocityY);
            }
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
        if (host.getGameMode() == host.ADVENTURE) {
            controller1.dispose();
        }
        player.dispose();
        controllerStage.dispose();
        lightDoll.dispose();
        bodyHandler.dispose();

        /**
         * BOX2D LIGHTS
         */
        lightSetup.dispose();

        cat.dispose();
        world.dispose();

        if (isDebugOn) {
            debugRenderer.dispose();
        }

        kekkonen.dispose();
        if (host.getMusic() == ON) {
            gameBGM.dispose();
            bossCallSound.dispose();
        }
        deathSound.dispose();


        tiledMap.dispose();
        jumpButtonActor.dispose();

        exclamationMarkActor.dispose();
        exclamationStage.dispose();

        resume.dispose();
        restart.dispose();
        backToMap.dispose();
        pauseStage.dispose();

        pauseResumeButtonActor.dispose();
        pauseResumeStage.dispose();

        Gdx.app.log("GameScreen", "disposed");
    }
}
