package io.github.sealpup.Sandbox.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.sealpup.Sandbox.SimulationWindow;

import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Random;


public class ElementWater extends BaseElement{

    private static Random random = new Random();
    private static int seed;
    private static int x;
    private static int y;
    private static boolean interacted = false;
    private static SimulationWindow window;
    static Texture[] textures = {
        new Texture("elements/water/water1.jpg"),
    };
    public static void render(int px, int py, float width, float height, SpriteBatch batch){
        BaseElement.render(px, py, width, height, batch, textures[0]);
    }
    static SimulationWindow.elementTypes[] movableElements = {
        SimulationWindow.elementTypes.air,
        SimulationWindow.elementTypes.fire,
        SimulationWindow.elementTypes.steam,
        SimulationWindow.elementTypes.acid,
    };
    static SimulationWindow.elementTypes[] reactableElements = {
        SimulationWindow.elementTypes.fire,
        SimulationWindow.elementTypes.hotCoal,
    };


    public static void tick( SimulationWindow pWindow, int px, int py) {
        interacted = false;
        window = pWindow;
        x = px;
        y = py;
        positionCheck(x, y-1);
        if(!interacted){
            seed = random.nextInt(2);
            switch (seed) {
                //bottom left
                case 0:
                    positionCheck( x-1, y-1);
                    break;
                case 1:
                    positionCheck( x+1, y-1);
                    break;
            }

        }
        if(!interacted){
            switch (seed) {
                case 0:
                    positionCheck( x-1, y);
                    break;
                case 1:
                    positionCheck( x+1, y);
                    break;
                default:
                    break;
            }
        }

    }
    private static void positionCheck(  int targetX, int targetY) {
        if (window.inBounds(targetX, targetY)) {
            if(Arrays.asList(reactableElements).contains(window.getElement(targetX, targetY))) {
                if(window.getElement(targetX, targetY) == SimulationWindow.elementTypes.fire) {
                    fireReaction( targetX, targetY);
                    interacted = true;
                    return;
                }
                if(window.getElement(targetX, targetY) == SimulationWindow.elementTypes.hotCoal){
                    hotCoalReaction(targetX, targetY);
                    interacted = true;
                    return;
                }
            }
            if (Arrays.asList(movableElements).contains(window.getElement(targetX, targetY))) {
                window.moveElement(x, y, targetX, targetY);
                interacted = true;
                return;
            }

        }
        return;
    }
    private static void hotCoalReaction( int targetX, int targetY) {
        window.addElement(SimulationWindow.elementTypes.steam, x, y);
        window.addElement(SimulationWindow.elementTypes.coal, targetX, targetY);
    }
    private static void fireReaction( int targetX, int targetY) {
        window.removeElement(x, y);
        window.addElement(SimulationWindow.elementTypes.steam, targetX, targetY);
    }
}
