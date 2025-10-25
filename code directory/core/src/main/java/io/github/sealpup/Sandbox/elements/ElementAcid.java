package io.github.sealpup.Sandbox.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.sealpup.Sandbox.SimulationWindow;

import java.sql.SQLSyntaxErrorException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class ElementAcid extends BaseElement{
    private static Random random = new Random();
    private static int x;
    private static int y;
    private static SimulationWindow window;
    static int seed;
    static Texture[] textures = {
        new Texture("elements/stone/stone1.png"),
        new Texture("elements/stone/stone2.png"),
    };
    static SimulationWindow.elementTypes[] movableElements = {
        SimulationWindow.elementTypes.air,
        SimulationWindow.elementTypes.fire,
    };
    static SimulationWindow.elementTypes[] unreactableElements = {
        SimulationWindow.elementTypes.air,
        SimulationWindow.elementTypes.movingElement,
        SimulationWindow.elementTypes.wall,
        SimulationWindow.elementTypes.acid,
    };
    public static void render(int px, int py, float width, float height, SpriteBatch batch, int texture){
        BaseElement.render(px, py, width, height, batch, textures[texture]);
    }


    public static void tick(SimulationWindow pWindow, int px, int py) {
        window = pWindow;
        x = px;
        y = py;
        if(positionCheck(x, y-1)){
            return;
        }
        seed = random.nextInt(2);
        switch (seed){
            //bottom left
            case 0:
                positionCheck( x-1, y-1);
                break;
            case 1:
                positionCheck( x+1, y-1);
                break;
        }
        switch (seed) {
            case 0:
                positionCheck( x-1, y);
                break;
            case 1:
                positionCheck( x+1, y);
                break;
        }
    }
    private static boolean positionCheck( int targetX, int targetY) {
        if (window.inBounds(targetX, targetY)) {
            if(!Arrays.asList(unreactableElements).contains(window.getElement(targetX, targetY))) {
                acidReaction(targetX, targetY);
                return true;
            }
            if (Arrays.asList(movableElements).contains(window.getElement(targetX, targetY))) {
                window.moveElement(x, y, targetX, targetY);
                return true;
            }
        }
        return false;
    }
    private static void acidReaction(int targetX, int targetY) {
        window.removeElement(x, y);
        window.removeElement(targetX, targetY);
    }

}
