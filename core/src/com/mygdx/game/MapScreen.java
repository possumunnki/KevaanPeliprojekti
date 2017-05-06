package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.MainMenu2.MapBorderActor;

import java.util.ArrayList;

/**
 * Created by possumunnki on 25.3.2017.
 */

public class MapScreen implements Screen {
    private MyGdxGame host;
    private float screenWidth;
    private float screenHeight;

    private SpriteBatch batch;
    private OrthographicCamera camera;

    private FontActor back;
    private PointActor stage1Point;
    private PointActor stage2Point;
    private PointActor stage3Point;
    private PointActor stage4Point;
    private PointActor stage5Point;
    private Stage pointStage;
    private Texture backGroundTexture;
    private Stage mapBorderStage;
    private MapBorderActor mapBorderActor;

    ArrayList<PointActor> pointActors;

    public MapScreen(MyGdxGame host) {
        this.host = host;
        batch = host.getSpriteBatch();
        screenWidth = host.SCREEN_WIDTH * 100f;
        screenHeight = host.SCREEN_HEIGHT * 100f;
        backGroundTexture = new Texture("mapBG.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                screenWidth,
                screenHeight);
        /*nextStage = new FontActor("Next Stage",
                                    host.SCREEN_WIDTH * 6/8 * 100f,
                                    host.SCREEN_HEIGHT * 2/8 * 100f);
        */
        back = new FontActor("Back",
                                    screenWidth  * 1/8,
                                    screenHeight * 1/8);

        pointActors = new ArrayList<PointActor>();


        mapBorderActor = new MapBorderActor();
        mapBorderStage = new Stage(new FillViewport(screenWidth, screenHeight), batch);
        mapBorderStage.addActor(mapBorderActor);

        pointStage = new Stage(new FillViewport(screenWidth, screenHeight), batch);

        setPointActors();
        fadeInActions();
        //mapScreenBG = new Background("mapScreenBG");

        //stage.addActor(mapScreenBG);
        //pointStage.addActor(nextStage);
        pointStage.addActor(back);


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
        // configStage();

        /*if (nextStage.getTouch()) {
            if(host.getCurrentStage() == 1) {
                host.setScreen(new TalkScreen(host));
            } else {
                host.setScreen(new GameScreen(host));
            }
        } else if(returnToMainMenu.getTouch()) {
            host.setScreen(new MainMenuScreen(host));
        }
        */
        if(back.getTouch()) {
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
        //mapScreenBG.dispose();
        //nextStage.dispose();
        for(int i = 0; i < pointActors.size(); i++) {
            pointActors.get(i).dispose();
        }
        Gdx.app.log("MapScreen", "disposed");
    }

    /**
     * Creates point actors as many as stages are unlocked.
     * Point actors will be added to array list to implement fade in effect in order.
     */
    private void setPointActors() {
        switch (host.getUnlockedStages()) {
            case 5:
                stage5Point = new PointActor(screenWidth  * 6.5f / 12,
                                             screenHeight * 4.5f / 24, 5);
                pointActors.add(stage5Point);
                pointStage.addActor(stage5Point);

            case 4:
                stage4Point = new PointActor(screenWidth  * 2.5f / 12,
                        screenHeight * 3.5f / 12, 4);
                pointActors.add(stage4Point);
                pointStage.addActor(stage4Point);
            case 3:
                stage3Point = new PointActor(screenWidth * 0.5f / 12 ,
                        screenHeight * 6 / 12, 3);
                pointActors.add(stage3Point);
                pointStage.addActor(stage3Point);
                Gdx.app.log("point3", "added");
            case 2:
                stage2Point = new PointActor(screenWidth  * 8f / 12,
                        screenHeight * 6f / 12, 2);
                pointActors.add(stage2Point);
                pointStage.addActor(stage2Point);
                Gdx.app.log("point2", "added");
            case 1:
                stage1Point = new PointActor(screenWidth * 4.5f / 12 ,
                        screenHeight * 8.5f / 12, 1);
                pointActors.add(stage1Point);
                pointStage.addActor(stage1Point);
                Gdx.app.log("point1", "added");
        }

    }

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
                                setPointActions(host.getUnlockedStages());
                            }
                        })));
    }

    public void fadeOutAllPointActors() {
        for(int i = 0; i < pointActors.size(); i++) {
            pointActors.get(i).addAction(Actions.fadeOut(0f));
        }
    }

    /**
     * sets fade in actions for point actors
     *
     *
     */
    public void setPointActions(final int pointNumber) {
        pointActors.get(pointNumber - 1).addAction(
                Actions.sequence(
                        Actions.fadeIn(0.5f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                int nextPointNumber = pointNumber - 1;
                                if(nextPointNumber > 0) {
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
                if(stage5Point.getTouch()) {
                    host.setCurrentStage(5);
                    removeOtherPointActors(stage5Point);
                    moveAndZoomAction(stage5Point);
                }
            case 4:
                if(stage4Point.getTouch()) {
                    host.setCurrentStage(4);
                    removeOtherPointActors(stage4Point);
                    moveAndZoomAction(stage4Point);
                }
            case 3:
                if(stage3Point.getTouch()) {
                    host.setCurrentStage(3);
                    removeOtherPointActors(stage3Point);
                    moveAndZoomAction(stage3Point);
                }
            case 2:
                if(stage2Point.getTouch()) {
                    host.setCurrentStage(2);
                    removeOtherPointActors(stage2Point);
                    moveAndZoomAction(stage2Point);
                }
            case 1:
                if(stage1Point.getTouch()) {
                    host.setCurrentStage(1);
                    removeOtherPointActors(stage1Point);
                    moveAndZoomAction(stage1Point);
                }
        }
        /*
        if(stage1Point.getTouch()) {
            host.setCurrentStage(1);
            removeOtherPointActors(stage1Point);
            moveAndZoomAction(stage1Point);
        } else if(stage2Point.getTouch()) {
            host.setCurrentStage(2);
            removeOtherPointActors(stage2Point);
            moveAndZoomAction(stage2Point);
        } else if(stage3Point.getTouch()) {
            host.setCurrentStage(3);
            removeOtherPointActors(stage3Point);
            moveAndZoomAction(stage3Point);
        }  else if(stage4Point.getTouch()) {
            host.setCurrentStage(4);
            removeOtherPointActors(stage4Point);
            moveAndZoomAction(stage4Point);
        }*/
    }

    /**
     * Removes all pointActors from the screen except one pointActor.
     *
     * @param pointActor pointActor that won't be removed
     */
    private void removeOtherPointActors(PointActor pointActor) {
        for(int i = 0; i < pointActors.size(); i++) {
            if(!pointActors.get(i).equals(pointActor)) {
                pointActors.get(i).remove();
            }
        }
    }
    // ehkä turha
    private void configStage() {
        if (host.getCurrentStage() == 1) {
            if (stage2Point.getTouch() && host.getStageAvailability(2)) {
                host.setCurrentStage(2);
                stage1Point.setTouch(false);
            } else if (stage3Point.getTouch() && host.getStageAvailability(3)) {
                host.setCurrentStage(3);
                stage1Point.setTouch(false);
            } else {
                stage1Point.setTouch(true);
                stage2Point.setTouch(false);
                stage3Point.setTouch(false);
            }

        } else if (host.getCurrentStage() == 2) {
            if (stage1Point.getTouch() && host.getStageAvailability(1)) {
                host.setCurrentStage(1);
                stage2Point.setTouch(false);
            } else if (stage3Point.getTouch() && host.getStageAvailability(3)) {
                host.setCurrentStage(3);
                stage2Point.setTouch(false);
            } else {
                stage1Point.setTouch(false);
                stage2Point.setTouch(true);
                stage3Point.setTouch(false);
            }
        } else if (host.getCurrentStage() == 3) {
            if (stage1Point.getTouch() && host.getStageAvailability(1)) {
                host.setCurrentStage(1);
                stage3Point.setTouch(false);
            } else if (stage2Point.getTouch() && host.getStageAvailability(2)) {
                host.setCurrentStage(2);
                stage3Point.setTouch(false);
            } else {
                stage1Point.setTouch(false);
                stage2Point.setTouch(false);
                stage3Point.setTouch(true);
            }
        }
    }

    /**
     * moves MapBorder texture so that the pointActors place is centralized and zooms in.
     * @param pointActor point that has been touched
     */
    public void moveAndZoomAction(PointActor pointActor) {
        pointActor.remove();
        // coordinates where map should move
        float movePointX = screenWidth / 2 - pointActor.getX() - pointActor.getWidth();
        float movePointY = screenHeight / 2 - pointActor.getY() - pointActor.getHeight();
        mapBorderActor.setOrigin(pointActor.getX() + pointActor.getWidth(), pointActor.getY() + pointActor.getHeight());

        mapBorderActor.addAction(
                Actions.sequence(
                        Actions.moveTo(movePointX,
                                       movePointY,
                                       1f), // duaration(seconds) of move
                        Actions.scaleTo( 1.5f, 1.5f, 1f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                if(host.getCurrentStage() == 1) {
                                    host.setScreen(new TalkScreen(host));
                                } else {
                                    host.setScreen(new GameScreen(host));
                                }
                            }
                        })));
    }


}
