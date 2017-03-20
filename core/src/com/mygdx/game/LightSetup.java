package com.mygdx.game;

/**
 * Created by Juz3 on 16.3.2017.
 */

/**
 * BOX2D LIGHT-RELATED
 */
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import box2dLight.ChainLight;
import box2dLight.ConeLight;
import box2dLight.DirectionalLight;
import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

public class LightSetup {

    /**
     * BOX2D LIGHT-RELATED BEGIN
     */
    private static final int RAYS_PER_BALL = 128;
    private static final int BALLSNUM = 5;

    // The distance that light will travel
    private float LIGHT_DISTANCE = 5.0f;
    private float RADIUS = 0.25f;
    private RayHandler rayHandler;
    private ArrayList<Light> lights = new ArrayList<Light>(BALLSNUM);
    private float sunDirection = -90f;
    /**
     * BOX2D LIGHT-RELATED END
     */

    public LightSetup(World world, LightDoll doll, Player player) {

        RayHandler.setGammaCorrection(true);
        RayHandler.useDiffuseLight(true);

        rayHandler = new RayHandler(world);

        // Ambient light-setting, (RED, GREEN, BLUE, ALPHA)
        rayHandler.setAmbientLight(0.01f, 0.14f, 0.24f, 0.5f);
        rayHandler.setBlurNum(3);

        initPointLights(doll, player);
    }

    public void render(OrthographicCamera cam, boolean stepped) {

        rayHandler.setCombinedMatrix(cam);
        rayHandler.update();
        rayHandler.render();
    }

    /**
     * Box2Dlights light-adding method
     */
    private void initPointLights(LightDoll lightDoll, Player player) {
        clearLights();
        for (int i = 0; i < BALLSNUM; i++) {


            PointLight DollLight = new PointLight(
                    rayHandler, RAYS_PER_BALL, null, LIGHT_DISTANCE, 0f, 0f);
            DollLight.attachToBody(lightDoll.getLightDollBody());
            DollLight.setColor(
                    0.75f,
                    0.5f,
                    0.1f,
                    0.3f);
            lights.add(DollLight);


            // Test for different type of light
            /**
            ConeLight testRoomLight = new ConeLight(
                    rayHandler, RAYS_PER_BALL, null, LIGHT_DISTANCE, 6, 1, 90, 180);
            testRoomLight.attachToBody(player.getPlayerBody());
            testRoomLight.setColor(
                    0.15f,
                    0.3f,
                    0.19f,
                    0.7f);
            lights.add(testRoomLight);
             */

        }
    }

    /**
     * Box2Dlights light-removal method
     */
    // Template for light-removal method
    private void clearLights() {
        if (lights.size() > 0) {
            for (Light light : lights) {
                light.remove();
            }
            lights.clear();
        }
    }

    public void dispose() {
        rayHandler.dispose();
    }
}
