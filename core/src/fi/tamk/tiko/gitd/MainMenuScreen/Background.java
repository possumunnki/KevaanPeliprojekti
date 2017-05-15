package fi.tamk.tiko.gitd.MainMenuScreen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Juz3 on 19.2.2017.
 * Background selector class
 */

public class Background extends Actor {

    private Texture mainmenuBG;

    private boolean mainMenu;
    private boolean mapMenu;

    public Background(String select) {

        mainmenuBG = new Texture("mainScreenBG.png");
        //mapBG = new Texture("mapScreenBG.png");

        if (select.equalsIgnoreCase("backGround")) {
            mainMenu = true;
            mapMenu = false;
            setWidth(mainmenuBG.getWidth());
            setHeight(mainmenuBG.getHeight());
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if (mainMenu) {
            batch.draw(mainmenuBG, getX(), getY(), getWidth(), getHeight());
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void dispose() {
        if (mainMenu) {
            mainmenuBG.dispose();
        }
    }
}
