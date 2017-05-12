package fi.tamk.tiko.gitd.MapScreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by possumunnki on 25.4.2017.
 */

public class MapBorderActor extends Actor {
    private Texture mapBorderTexture;
    private Sprite mapBorderSprite;

    public MapBorderActor() {
        mapBorderTexture = new Texture("mapBorder.png");
        mapBorderSprite = new Sprite(mapBorderTexture);

        setWidth(mapBorderTexture.getWidth());
        setHeight(mapBorderTexture.getHeight());
        setScale(1,1);
        // setOrigin(getWidth()/2, getHeight()/2);
        //fadeInAction();
    }

    public void draw(Batch batch, float alpha) {

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
