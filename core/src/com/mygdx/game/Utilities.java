package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
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
 * Created by possumunnki on 7.3.2017.
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
        TextureRegion [] frames
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

    public static BodyDef getDefinitionOfBody() {
        // Body Definition
        BodyDef myBodyDef = new BodyDef();
        // It's a body that moves
        myBodyDef.type = BodyDef.BodyType.DynamicBody;
        // Initial position is centered up
        // This position is the CENTER of the shape!
        myBodyDef.position.set( MyGdxGame.SCREEN_WIDTH / 2,
                MyGdxGame.SCREEN_HEIGHT / 2);

        return myBodyDef;
    }

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

    public static void createStaticBody(Rectangle rect,
                                        String userData,
                                        World world) {
        BodyDef myBodyDef = new BodyDef();
        myBodyDef.type = BodyDef.BodyType.StaticBody;

        float x = rect.getX();
        float y = rect.getY();
        float width = rect.getWidth();
        float height = rect.getHeight();

        float centerX = width/2 + x;
        float centerY = height/2 + y;

        myBodyDef.position.set(centerX, centerY);

        Body wall = world.createBody(myBodyDef);

        wall.setUserData(userData);
        // Create shape
        PolygonShape groundBox = new PolygonShape();

        // Real width and height is 2 X this!
        groundBox.setAsBox(width / 2 , height / 2 );

        wall.createFixture(groundBox, 0.0f);
    }

    private static Rectangle scaleRect(Rectangle r, float scale) {
        Rectangle rectangle = new Rectangle();
        rectangle.x      = r.x * scale;
        rectangle.y      = r.y * scale;
        rectangle.width  = r.width * scale;
        rectangle.height = r.height * scale;
        return rectangle;
    }
}
