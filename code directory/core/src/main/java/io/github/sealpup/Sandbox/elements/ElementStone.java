package io.github.sealpup.Sandbox.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.sealpup.Sandbox.SimulationWindow;

import java.sql.SQLSyntaxErrorException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class ElementStone extends BaseElement{
    private static Random random = new Random();
    private static int x;
    private static int y;
    private static SimulationWindow window;
    private static String colour;
    static int seed;
    static Texture[] textures = {
        new Texture("elements/stone/stone1.png"),
        new Texture("elements/stone/stone2.png"),
    };
    static SimulationWindow.elementTypes[] movableElements = {
        SimulationWindow.elementTypes.air,
        SimulationWindow.elementTypes.acid,
        SimulationWindow.elementTypes.water,
        SimulationWindow.elementTypes.steam,

    };
    static SimulationWindow.elementTypes[] reactableElements = {
        SimulationWindow.elementTypes.fire,
    };
    public static void render(int px, int py, float width, float height, SpriteBatch batch, int texture){
        BaseElement.render(px, py, width, height, batch, textures[texture]);
    }


    public static void tick(SimulationWindow pWindow, String pColour, int px, int py) {
        window = pWindow;
        colour = pColour;
        x = px;
        y = py;
        positionCheck(x, y-1);
        seed = random.nextInt(2);
        switch (seed){
            //bottom left
            case 0:
                positionCheck( x-1, y-1);
                return;
            case 1:
                positionCheck( x+1, y-1);
                return;
            default:
                System.out.println("what just happened elements/ElementStone/tick");
        }
    }
    private static void positionCheck( int targetX, int targetY) {
        if (window.inBounds(targetX, targetY)) {
            if(Arrays.asList(reactableElements).contains(window.getElement(targetX, targetY))) {
                if (Objects.requireNonNull(window.getElement(targetX, targetY)) == SimulationWindow.elementTypes.fire) {
                    fireReaction(targetX, targetY);
                }
                return;
            }
            if (Arrays.asList(movableElements).contains(window.getElement(targetX, targetY))) {
                if(window.getElement(targetX, targetY) == SimulationWindow.elementTypes.water){
                    seed = random.nextInt(3);
                    if(seed == 0){
                        window.moveElement(x,y,targetX,targetY);
                    }
                }
                else{
                    window.moveElement(x, y, targetX, targetY);
                }
            }


        }
    }
    private static void fireReaction(int targetX, int targetY) {
        window.removeElement(x, y);
        switch (colour){
            case "light":
                System.out.println("in light");
                window.addElement(SimulationWindow.elementTypes.lightStone, targetX, targetY);
                break;
            case "dark":
                System.out.println("in dark");
                window.addElement(SimulationWindow.elementTypes.darkStone, targetX, targetY);
                break;
            default:
                System.out.println("what just happened elements/ElementStone/fire");
        }
    }

}
