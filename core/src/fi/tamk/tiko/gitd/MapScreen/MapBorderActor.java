package fi.tamk.tiko.gitd.MapScreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Implements borders of map. This class is used only in map screen.
 *
 * @author Akio Ide
 * @version 1.0
 * @since 2017-05-12
 */

public class MapBorderActor extends Actor {
    private Texture mapBorderTexture;
    // Sprite is needed to make it possible to use zoom actions
    private Sprite mapBorderSprite;

    public MapBorderActor() {
        mapBorderTexture = new Texture("mapBorder.png");
        mapBorderSprite = new Sprite(mapBorderTexture);

        // sets size of the actor
        setWidth(mapBorderTexture.getWidth());
        setHeight(mapBorderTexture.getHeight());
        setScale(1, 1);
    }

    public void draw(Batch batch, float alpha) {
        // allows to use fade in/ out actions
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * alpha);

        batch.draw(mapBorderSprite,
                getX(),
                getY(),
                getOriginX(),
                getOriginY(),
                getWidth() * getScaleX(),
                getHeight() * getScaleY(),
                getScaleX(),
                getScaleY(),
                getRotation());
        batch.setColor(color.r, color.g, color.b, 1f);


    }

    public void act(float delta) {
        super.act(delta);

    }

    public void dispose() {
        mapBorderTexture.dispose();
        this.remove();
    }


}
