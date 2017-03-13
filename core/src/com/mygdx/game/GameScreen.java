package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;


/**
 * Created by possumunnki on 7.3.2017.
 */

public class GameScreen implements Screen, Input.TextInputListener {

    /**
     * Map size is 70 x 60 tiles.
     */
    private final int TILES_AMOUNT_WIDTH = 70;
    private final int TILES_AMOUNT_HEIGHT = 60;

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
    Texture img;
    OrthographicCamera camera;
    private float deltaTime;
    private float stateTime;
    private Player player;
    private Box2DDebugRenderer debugRenderer;


    private TiledMapRenderer tiledMapRenderer;
    private TiledMap tiledMap;

    private Stage stage;
    Controller1 controller1;


    public GameScreen(MyGdxGame host) {
        this.host = host;
        batch = host.getSpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                host.SCREEN_WIDTH,
                host.SCREEN_HEIGHT);

        world = new World(new Vector2(0, -9.8f), true);
        img = new Texture("badlogic.jpg");
        debugRenderer = new Box2DDebugRenderer();
        player = new Player(world);


        tiledMap = new TmxMapLoader().load("map.tmx");
        tiledMapRenderer = new OrthoCachedTiledMapRenderer(tiledMap, 1/100f);
        Utilities.transformWallsToBodies("world-wall-rectangles", "world-wall", tiledMap, world);
        Utilities.transformWallsToBodies("wall-rectangles", "wall", tiledMap, world);

        controller1 = new Controller1(MyGdxGame.SCREEN_WIDTH / 10, MyGdxGame.SCREEN_HEIGHT / 5);

        //Create a Stage and add TouchPad
        stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        stage.addActor(controller1.getTouchpad());
        Gdx.input.setInputProcessor(stage);
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
        player.movePlayer(controller1.getDirection());
        player.movePlayer();
        deltaTime = Gdx.graphics.getDeltaTime();
        stateTime += deltaTime;

        tiledMapRenderer.setView(camera);
        moveCamera();
        camera.update();
        tiledMapRenderer.render();

        // debugRenderer.render(world,camera.combined);
        batch.begin();
        // doHeavyStuff();
        player.draw(batch, stateTime);
        // controller1.draw(batch);
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        doPhysicsStep(deltaTime);
    }

    private double accumulator = 0;
    private float TIME_STEP = 1 / 60f;

    private void doPhysicsStep(float deltaTime) {
        float frameTime = deltaTime;
        // If it took ages (over 4 fps, then use 4 fps)
        // Avoid of "spiral of death"
        if(deltaTime > 1 / 4f) {
            frameTime = 1 / 4f;
        }

        accumulator += frameTime;

        while (accumulator >= TIME_STEP) {
            // It's fixed time step!
            world.step(TIME_STEP, 8, 3);
            accumulator -= TIME_STEP;
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
}
