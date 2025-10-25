package io.github.sealpup.Sandbox.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.sealpup.Sandbox.SimulationWindow;

public abstract class BaseElement {

    static Texture[] Textures = {new Texture("elements/defaultElement.jpg")};
    public static void render(int x, int y, float width, float height, SpriteBatch batch, Texture texture) {
        batch.draw(texture, 5.5f+(x*width), 50.5f+(y*height), width, height);
    }
    public static void render(int x, int y, float width, float height, SpriteBatch batch) {
        batch.draw(Textures[0], 5.5f+x, 50.5f+y, width, height);
    }
    public static void tick(int seed, SimulationWindow controller, int x, int y) {
    }
}
