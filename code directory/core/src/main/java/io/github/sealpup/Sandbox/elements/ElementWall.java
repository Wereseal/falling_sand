package io.github.sealpup.Sandbox.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ElementWall extends BaseElement{
    static Texture[] textures = {
        new Texture("elements/wall/wall1.jpg"),
    };
    public static void render(int x, int y, float width, float height, SpriteBatch batch){
        BaseElement.render(x, y, width, height, batch, textures[0]);
    }
    public ElementWall() {
    }

}
