package fi.tamk.tiko.gitd.GameScreen.Allies;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

/**
 * light class, which uses the "Box2Dlights"-library
 * Main use is to light up the light doll and set dark ambient "light" elsewhere
 * Created by Juz3 on 16.3.2017.
 */

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
    private RayHandler rayHandler;
    private ArrayList<Light> lights = new ArrayList<Light>(BALLSNUM);


    /**
     * Constructs the lightsetup object
     *
     * @param world phys2d world object
     * @param doll lightdoll object
     * @param player player object
     */
    public LightSetup(World world, LightDoll doll, Player player) {

        RayHandler.setGammaCorrection(true);
        RayHandler.useDiffuseLight(true);

        rayHandler = new RayHandler(world);

        // Ambient light-setting, (RED, GREEN, BLUE, ALPHA)
        rayHandler.setAmbientLight(0.06f, 0.14f, 0.20f, 0.55f);
        rayHandler.setBlurNum(3);

        initPointLights(doll);
    }

    /**
     * box2dlights-renderer method
     *
     * @param cam camera
     */
    public void render(OrthographicCamera cam) {

        rayHandler.setCombinedMatrix(cam);
        rayHandler.update();
        rayHandler.render();
    }

    /**
     * Box2Dlights light-adding method
     *
     * @param lightDoll the lightdoll object, where the light is bound to
     */
    private void initPointLights(LightDoll lightDoll) {
        clearLights();
        for (int i = 0; i < BALLSNUM; i++) {


            PointLight DollLight = new PointLight(
                    rayHandler, RAYS_PER_BALL, null, LIGHT_DISTANCE, 0f, 0f);
            DollLight.attachToBody(lightDoll.getLightDollBody());
            DollLight.setColor(
                    0.77f,
                    0.64f,
                    0.43f,
                    0.15f);
            lights.add(DollLight);

        }
    }

    /**
     * Box2Dlights light-removal method
     */
    private void clearLights() {
        if (lights.size() > 0) {
            for (Light light : lights) {
                light.remove();
            }
            lights.clear();
        }
    }

    /**
     * disposes the lights
     */
    public void dispose() {
        rayHandler.dispose();
    }
}
