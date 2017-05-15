package fi.tamk.tiko.gitd;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * This class contains several methods that is used in other classes.
 *
 * @author Akio Ide
 * @version 1.0
 * @since 2017-05-14
 */

public class Utilities {
    public static TextureRegion[] transformToFrames(Texture texture,
                                                    int frameCols,
                                                    int frameRows) {
        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / frameCols,
                texture.getHeight() / frameRows);
        TextureRegion[] frames = transformTo1D(tmp, frameCols, frameRows);
        return frames;
    }

    public static TextureRegion[] transformTo1D(TextureRegion[][] tmp, int frameCols, int frameRows) {
        TextureRegion[] frames
                = new TextureRegion[frameCols * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                frames[index] = tmp[i][j];
                index++;
            }
        }
        return frames;
    }

    /**
     * Creates body definition and sets its position.
     * @return body definition
     */
    public static BodyDef getDefinitionOfBody() {
        // Body Definition
        BodyDef myBodyDef = new BodyDef();
        // It's a body that moves
        myBodyDef.type = BodyDef.BodyType.DynamicBody;
        // Initial position is centered up
        // This position is the CENTER of the shape!
        myBodyDef.position.set(MyGdxGame.SCREEN_WIDTH / 2,
                MyGdxGame.SCREEN_HEIGHT / 2);

        return myBodyDef;
    }

    /**
     * Transforms tile map rectangles into phys2D bodies.
     * @param layer name of tilemap layer
     * @param userData name of body that identifies bodies
     * @param tiledMap tilemap
     * @param world     world of game stage
     */
    public static void transformWallsToBodies(String layer,
                                              String userData,
                                              TiledMap tiledMap,
                                              World world) {
        // Let's get the collectable rectangles layer
        MapLayer collisionObjectLayer = tiledMap.getLayers().get(layer);

        // All the rectangles of the layer
        MapObjects mapObjects = collisionObjectLayer.getObjects();

        // Cast it to RectangleObjects array
        Array<RectangleMapObject> rectangleObjects = mapObjects.getByType(RectangleMapObject.class);

        // Iterate all the rectangles
        for (RectangleMapObject rectangleObject : rectangleObjects) {
            Rectangle tmp = rectangleObject.getRectangle();

            // SCALE given rectangle down if using world dimensions!
            Rectangle rectangle = scaleRect(tmp, 1 / 100f);

            createStaticBody(rectangle, userData, world);
        }
    }

    /**
     * Creates static body by using rectangles.
     *
     * @param rect rectangle that should use
     * @param userData name of body that identifies bodies
     * @param world world of game stage
     */
    public static void createStaticBody(Rectangle rect,
                                        String userData,
                                        World world) {
        BodyDef myBodyDef = new BodyDef();
        myBodyDef.type = BodyDef.BodyType.StaticBody;

        float x = rect.getX();
        float y = rect.getY();
        float width = rect.getWidth();
        float height = rect.getHeight();

        float centerX = width / 2 + x;
        float centerY = height / 2 + y;

        myBodyDef.position.set(centerX, centerY);

        Body wall = world.createBody(myBodyDef);

        wall.setUserData(userData);
        // Create shape
        PolygonShape groundBox = new PolygonShape();

        // Real width and height is 2 X this!
        groundBox.setAsBox(width / 2, height / 2);

        wall.createFixture(groundBox, 0.0f);
    }

    /**
     * Scales rectangle size.
     *
     * @param r     rectangle
     * @param scale scale
     * @return      scaled rectangle
     */
    private static Rectangle scaleRect(Rectangle r, float scale) {
        Rectangle rectangle = new Rectangle();
        rectangle.x = r.x * scale;
        rectangle.y = r.y * scale;
        rectangle.width = r.width * scale;
        rectangle.height = r.height * scale;
        return rectangle;
    }

    /**
     * Flips animation right left.
     *
     * @param animation animimation that must flip.
     */
    public static void flip(Animation<TextureRegion> animation) {
        TextureRegion[] regions = animation.getKeyFrames();

        // flips all frames
        for (TextureRegion r : regions) {
            r.flip(true, false);
        }
    }
}
