package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Juz3 on 24.3.2017.
 */

public class ObjectData {

    Texture objectTexture;
    //float radius;

    float width;
    float height;

    // public int id;


    public enum GameObjectType {
        VOODOO,
        BULLET
    }


    ObjectData.GameObjectType type;

    public ObjectData(Texture texture, float x, float y, ObjectData.GameObjectType
            gameObjectType) {
        objectTexture = texture;
        width = x;
        height = y;
        type = gameObjectType;
    }
}