package com.mygdx.game;

/**
 * Created by Juz3 on 16.3.2017.
 */


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
     * Box2dLights-related setup class
     */

    // determines how many rays the point light will emit,
    // bigger value = smoother effect
    private static final int RAYS_PER_BALL = 256;
    private static final int BALLSNUM = 5;

    // The distance that light will travel
    private float LIGHT_DISTANCE = 4.5f;
    private float RADIUS = 0.25f;
    private RayHandler rayHandler;
    private ArrayList<Light> lights = new ArrayList<Light>(BALLSNUM);

    // for directional (sun)light
    private float sunDirection = -90f;


    public LightSetup(World world, LightDoll doll, Player player) {

        RayHandler.setGammaCorrection(true);
        RayHandler.useDiffuseLight(true);

        rayHandler = new RayHandler(world);

        // Ambient light-setting, (RED, GREEN, BLUE, ALPHA)
        rayHandler.setAmbientLight(0.06f, 0.22f, 0.35f, 0.55f);
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
                    0.6f,
                    0.45f,
                    0.08f,
                    0.15f);
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
