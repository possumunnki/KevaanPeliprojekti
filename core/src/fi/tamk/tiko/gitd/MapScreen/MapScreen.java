package fi.tamk.tiko.gitd.MapScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FillViewport;

import fi.tamk.tiko.gitd.FontActor;
import fi.tamk.tiko.gitd.GameScreen.GameScreen;
import fi.tamk.tiko.gitd.MainMenuScreen.MainMenuScreen;
import fi.tamk.tiko.gitd.MyGdxGame;
import fi.tamk.tiko.gitd.TalkScreen.TalkScreen;

import java.util.ArrayList;


/**
 * Implements map screen. First maps border fades in and then pointers fades in.
 * Player can choose the level by touching pointers or go back to
 * main screen. If player touches pointer screen moves to talk screen.
 *
 * @author Akio Ide
 * @version 1.0
 * @since 2017-05-12
 */

public class MapScreen implements Screen {
    private MyGdxGame host;
    /**
     * Size of the screen in pixels.
     */
    private float screenWidth;
    private float screenHeight;

    private SpriteBatch batch;
    private OrthographicCamera camera;

    /**
     * Back button.
     */
    private FontActor back;

    /**
     * Stage for point actors
     */
    private Stage pointStage;

    /**
     * Pointers that player must touch when player wants to play level.
     */
    private PointActor stage1Point;
    private PointActor stage2Point;
    private PointActor stage3Point;
    private PointActor stage4Point;
    private PointActor stage5Point;
    ArrayList<PointActor> pointActors;

    /**
     * Back ground texture on this screen
     */
    private Texture backGroundTexture;

    /**
     * Stage for map borders
     */
    private Stage mapBorderStage;
    private MapBorderActor mapBorderActor;


    /**
     * Creates map screen.
     * @param host  Needed to use sprite batch and some values in this class.
     */
    public MapScreen(MyGdxGame host) {
        this.host = host;
        batch = host.getSpriteBatch();
        // sets screen size in pixels
        screenWidth = host.SCREEN_WIDTH * 100f;
        screenHeight = host.SCREEN_HEIGHT * 100f;
        backGroundTexture = new Texture("mapBG.png");

        // sets camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                screenWidth,
                screenHeight);
        createBack();

        // sets array list to make actors do fade in actions in order
        pointActors = new ArrayList<PointActor>();


        mapBorderStage = new Stage(new FillViewport(screenWidth, screenHeight), batch);
        mapBorderActor = new MapBorderActor();
        mapBorderStage.addActor(mapBorderActor);

        pointStage = new Stage(new FillViewport(screenWidth, screenHeight), batch);

        // builds point actors. The amount depends on unlocked levels
        setPointActors();
        // sets actions to actors
        fadeInActions();
        //mapScreenBG = new Background("mapScreenBG");

        // adds back -button on stage
        pointStage.addActor(back);

        // makes actors detect touches
        Gdx.input.setInputProcessor(pointStage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();
        // prints back ground
        batch.draw(backGroundTexture,
                0f,
                0f,
                screenWidth,
                screenHeight);

        batch.end();

        mapBorderStage.act();
        mapBorderStage.draw();
        pointStage.act(Gdx.graphics.getDeltaTime());
        pointStage.draw();

        // whenever player touches back button
        if (back.getTouch()) {
            host.setScreen(new MainMenuScreen(host));
        } else {
            moveToStage();
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
        this.dispose();
    }

    @Override
    public void dispose() {
        backGroundTexture.dispose();

        for (int i = 0; i < pointActors.size(); i++) {
            pointActors.get(i).dispose();
        }
        pointStage.dispose();
        mapBorderActor.dispose();
        mapBorderStage.dispose();
    }

    /**
     * Creates back-button on screen. Text changes depending on localization.
     */
    private void createBack() {
        String backString = "";
        if(host.locale == host.FINNISH) {
            backString = "TAKAISIN";
        } else if(host.locale == host.ENGLISH) {
            backString = "BACK";
        }
        back = new FontActor(backString,
                screenWidth * 1 / 8,
                screenHeight * 1 / 8);
    }
    /**
     * Creates point actors as many as stages are unlocked.
     * Point actors will be added to array list to implement fade in effect in order.
     */
    private void setPointActors() {
        switch (host.getUnlockedStages()) {
            // cases is not break to add unlocked stage and everything lower level than that
            case 5:
                stage5Point = new PointActor(screenWidth * 6.5f / 12,
                        screenHeight * 4.5f / 24, 5);
                pointActors.add(stage5Point);
                pointStage.addActor(stage5Point);

            case 4:
                stage4Point = new PointActor(screenWidth * 2.5f / 12,
                        screenHeight * 3.5f / 12, 4);
                pointActors.add(stage4Point);
                pointStage.addActor(stage4Point);
            case 3:
                stage3Point = new PointActor(screenWidth * 0.5f / 12,
                        screenHeight * 6 / 12, 3);
                pointActors.add(stage3Point);
                pointStage.addActor(stage3Point);
            case 2:
                stage2Point = new PointActor(screenWidth * 8f / 12,
                        screenHeight * 6f / 12, 2);
                pointActors.add(stage2Point);
                pointStage.addActor(stage2Point);
            case 1:
                stage1Point = new PointActor(screenWidth * 4.5f / 12,
                        screenHeight * 8.5f / 12, 1);
                pointActors.add(stage1Point);
                pointStage.addActor(stage1Point);
        }

    }

    /**
     * Adds fade in actions to map border actor.
     * After map border actor had done fade in action pointers actions will be added.
     */
    private void fadeInActions() {
        // fade outs all point actors
        fadeOutAllPointActors();
        // fades outs map borders and fades in and after this fades in point actors
        mapBorderActor.addAction(
                Actions.sequence(
                        Actions.fadeOut(0f),
                        Actions.fadeIn(1.0f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                // adds actions to point actor
                                setPointActions(host.getUnlockedStages());
                            }
                        })));
    }

    public void fadeOutAllPointActors() {
        for (int i = 0; i < pointActors.size(); i++) {
            pointActors.get(i).addAction(Actions.fadeOut(0f));
        }
    }

    /**
     * sets fade in actions for point actors
     */
    public void setPointActions(final int pointNumber) {
        pointActors.get(pointNumber - 1).addAction(
                Actions.sequence(
                        Actions.fadeIn(0.5f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                int nextPointNumber = pointNumber - 1;
                                // adds all actions to all point actors with same method
                                if (nextPointNumber > 0) {
                                    setPointActions(nextPointNumber);
                                }
                            }
                        })));
    }

    /**
     * Whenever pointActor is touched, game moves from Map screen to talk screen or game screen.
     */
    private void moveToStage() {

        switch (host.getUnlockedStages()) {
            case 5:
                if (stage5Point.getTouch()) {
                    host.setCurrentStage(5);
                    removeOtherPointActors(stage5Point);
                    moveAndZoomAction(stage5Point);
                    break;
                }
            case 4:
                if (stage4Point.getTouch()) {
                    host.setCurrentStage(4);
                    removeOtherPointActors(stage4Point);
                    moveAndZoomAction(stage4Point);
                    break;
                }
            case 3:
                if (stage3Point.getTouch()) {
                    host.setCurrentStage(3);
                    removeOtherPointActors(stage3Point);
                    moveAndZoomAction(stage3Point);
                    break;
                }
            case 2:
                if (stage2Point.getTouch()) {
                    host.setCurrentStage(2);
                    removeOtherPointActors(stage2Point);
                    moveAndZoomAction(stage2Point);
                    break;
                }
            case 1:
                if (stage1Point.getTouch()) {
                    host.setCurrentStage(1);
                    removeOtherPointActors(stage1Point);
                    moveAndZoomAction(stage1Point);
                    break;
                }
        }}

    /**
     * Removes all pointActors from the screen except one pointActor.
     *
     * @param pointActor pointActor that won't be removed
     */
    private void removeOtherPointActors(PointActor pointActor) {
        for (int i = 0; i < pointActors.size(); i++) {
            if (!pointActors.get(i).equals(pointActor)) {
                pointActors.get(i).remove();
            }
        }
    }


    /**
     * moves MapBorder texture so that the pointActors place is centralized and zooms in.
     *
     * @param pointActor point that has been touched
     */
    public void moveAndZoomAction(PointActor pointActor) {
        pointActor.remove();
        // coordinates where map should move
        float movePointX = screenWidth / 2 - pointActor.getX() - pointActor.getWidth();
        float movePointY = screenHeight / 2 - pointActor.getY() - pointActor.getHeight();
        // sets origin where the zoom effect is centralized
        mapBorderActor.setOrigin(pointActor.getX() + pointActor.getWidth(), pointActor.getY() + pointActor.getHeight());
        // adds actions
        mapBorderActor.addAction(
                // sequence allows actors do actions in order
                Actions.sequence(
                        Actions.moveTo(movePointX,
                                movePointY,
                                1f), // duaration(seconds) of move
                        Actions.scaleTo(1.5f, 1.5f, 1f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                host.setScreen(new TalkScreen(host));
                            }
                        })));
    }


}
