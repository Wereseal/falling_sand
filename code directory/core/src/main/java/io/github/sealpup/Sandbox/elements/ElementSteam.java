package io.github.sealpup.Sandbox.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.sealpup.Sandbox.SimulationWindow;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class ElementSteam extends BaseElement{

    private static Random random = new Random();
    private static int seed;
    private static int x;
    private static int y;
    private static SimulationWindow window;
    static Texture[] textures = {
        new Texture("elements/steam/steam1.jpg"),
    };
    static SimulationWindow.elementTypes[] movableElements = {
        SimulationWindow.elementTypes.air,
    };
    static SimulationWindow.elementTypes[] reactableElements = {
    };
    public static void render(int px, int py, float width, float height, SpriteBatch batch, int texture){
        BaseElement.render(px, py, width, height, batch, textures[texture]);
    }

    public static void tick(SimulationWindow pwindow, int px, int py) {
        x = px;
        y = py;
        window = pwindow;
        seed = random.nextInt(300);
        if(seed == 0){
            window.addElement(SimulationWindow.elementTypes.water, x, y);
            return;
        }
        seed = random.nextInt(6);
        switch (seed){
            case 0:
                positionCheck(x, y+1);
                break;
            case 1:
                positionCheck(x+1, y+1);
                break;
            case 2:
                positionCheck(x-1, y+1);
                break;
            case 3:
                positionCheck(x+1, y);
                break;
            case 4:
                positionCheck(x-1, y);
                break;
            case 5:
                return;
        }
        seed = random.nextInt(3);
        switch (seed){
            case 0:
                positionCheck(x, y-1);
                break;
            case 1:
                positionCheck(x+1, y-1);
                break;
            case 2:
                positionCheck(x-1, y-1);
                break;
        }
    }private static void positionCheck( int targetX, int targetY) {
        if (window.inBounds(targetX, targetY)) {
            if(Arrays.asList(reactableElements).contains(window.getElement(targetX, targetY))) {
                return;
            }
            if (Arrays.asList(movableElements).contains(window.getElement(targetX, targetY))) {
                window.moveElement(x, y, targetX, targetY);
            }
        }
    }
}
