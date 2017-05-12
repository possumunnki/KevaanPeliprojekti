package fi.tamk.tiko.gitd.GameScreen.Enemies;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Juz3 on 24.3.2017.
 */

public class ObjectData {

    Texture objectTexture;
    float width;
    float height;

    public enum GameObjectType {
        VOODOO,
        RAT,
        FIREBALL,
        BOSS
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
