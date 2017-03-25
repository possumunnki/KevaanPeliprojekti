package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Juz3 on 24.3.2017.
 */

public class ObjectData {

    public Texture objectTexture;
    public float radius;
    // public int id;


    public enum GameObjectType {
        VOODOO,
        BULLET
    }


    ObjectData.GameObjectType type;

    public ObjectData(Texture texture, float r, ObjectData.GameObjectType gameObjectType) {
        objectTexture = texture;
        radius = r;
        type = gameObjectType;
    }
}