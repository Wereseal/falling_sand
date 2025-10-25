package io.github.sealpup.Sandbox.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.sealpup.Sandbox.SimulationWindow;

import java.util.Arrays;
import java.util.Random;

public class ElementFire extends BaseElement{
    private static int x;
    private static int y;
    private static boolean interacted = false;
    private static SimulationWindow window;
    private static Random random = new Random();
    private static int seed;
    static Texture[] textures = {

        new Texture("elements/fire/fire1.jpg"),
    };
    static SimulationWindow.elementTypes[] movableElements = {
        SimulationWindow.elementTypes.air,
        SimulationWindow.elementTypes.steam,
    };
    static SimulationWindow.elementTypes[] reactableElements = {
        SimulationWindow.elementTypes.water,
        SimulationWindow.elementTypes.coal
    };
    public static void render(int px, int py, float width, float height, SpriteBatch batch, int texture){
        BaseElement.render(px, py, width, height, batch, textures[texture]);
    }

    public static void tick(SimulationWindow pwindow, int px, int py) {
        window = pwindow;
        x = px;
        interacted = false;
        y = py;
        seed = random.nextInt(18);
        if(seed == 0){
            window.removeElement(x, y);
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
        if(!interacted){
            seed = random.nextInt(4);
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
                case 3:
                    break;
            }
        }
    }
    private static void positionCheck( int targetX, int targetY) {
        if (window.inBounds(targetX, targetY)) {
            if(Arrays.asList(reactableElements).contains(window.getElement(targetX, targetY))) {
                if(window.getElement(targetX, targetY) == SimulationWindow.elementTypes.water){
                    waterReaction(targetX, targetY);
                    interacted = true;
                }
                if(window.getElement(targetX, targetY) == SimulationWindow.elementTypes.coal){
                    coalReaction(targetX, targetY);
                    interacted = true;
                }
                return;
            }
            if (Arrays.asList(movableElements).contains(window.getElement(targetX, targetY))) {
                window.moveElement(x, y, targetX, targetY);
                interacted = true;
            }
        }
    }
    private static void coalReaction(int targetX, int targetY) {
        window.removeElement(x, y);
        window.addElement(SimulationWindow.elementTypes.hotCoal, targetX, targetY);
    }
    private static void waterReaction(int targetX, int targetY) {
        window.removeElement(x, y);
        window.addElement(SimulationWindow.elementTypes.steam, targetX, targetY);
    }
}
