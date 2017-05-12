package fi.tamk.tiko.gitd.MapScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Created by possumunnki on 26.3.2017.
 */

public class PointActor extends Actor {
    private boolean touch = false;
    private boolean touchEnter = false;
    /**
     * Texture that appears whenever the point is not touched.
     */
    private Texture exitTexture;
    /**
     * Texture that appears whenever the point is touched.
     */
    private Texture enterTexture;

    private Texture dottedLine;
    /**
     * The number of the level that the point corresponds to.
     * It is need for drawing dotted line in right position.
     */
    private int stage;

    /**
     *
     * @param printX    x-position where the pointer will be printed
     * @param printY    y-position where the pointer will be printed
     * @param stage     the number of the level that the point corresponds to
     */
    public PointActor(float printX, float printY, int stage) {
        this.stage = stage;
        exitTexture = new Texture("cross_black.png");
        enterTexture = new Texture("cross_red.png");
        setDottedLine();
        setWidth(exitTexture.getWidth());
        setHeight(exitTexture.getHeight());
        setPosition(printX,printY);
        setBounds(printX, printY ,getWidth(), getHeight());
        addListener(new PointListener());
    }

    public void draw(Batch batch, float alpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * alpha);

        if(touchEnter == true || touch == true) {
            batch.draw(enterTexture,
                    getX(),
                    getY(),
                    getWidth(),
                    getHeight());
        } else {
            batch.draw(exitTexture,
                    getX(),
                    getY(),
                    getWidth(),
                    getHeight());
        }

        if(stage != 1) {
            batch.draw(dottedLine, 0, 0, dottedLine.getWidth(), dottedLine.getHeight());
        }


        batch.setColor(color.r, color.g, color.b, 1f);

    }

    public void act(float delta) {
        super.act(delta);
    }

    public boolean getTouch() {
        return touch;
    }

    public void setTouch(boolean touch) {
        this.touch = touch;
    }
    public void dispose() {
        if(stage != 1) {
            dottedLine.dispose();
        }
        enterTexture.dispose();
        exitTexture.dispose();
        this.remove();
    }

    /**
     * Sets dotted line texture depending on the number of the level that the point corresponds to.
     */
    private void setDottedLine() {
        if(stage == 2) {
            dottedLine = new Texture("reitti1-2.png");
        } else if(stage == 3) {
            dottedLine = new Texture("reitti2-3.png");
        } else if(stage == 4) {
            dottedLine = new Texture("reitti3-4.png");
        } else if(stage == 5) {
            dottedLine = new Texture("reitti4-5.png");
        }
    }

    class PointListener extends InputListener {
        public boolean touchDown(InputEvent event,
                                 float x,
                                 float y,
                                 int pointer,
                                 int button) {

            touch = true;
            Gdx.app.log("touch","down");
            return false;
        }

        public void touchUp(InputEvent event,
                            float x,
                            float y,
                            int pointer,
                            int button) {
            touch = true;
            Gdx.app.log("touch","up");
        }
        public void enter(InputEvent event,
                          float x,
                          float y,
                          int pointer,
                          Actor fromActor) {
            touchEnter = true;
        }
        public void exit(InputEvent event,
                         float x,
                         float y,
                         int pointer,
                         Actor toActor) {
            touchEnter = false;
        }


    }
}
