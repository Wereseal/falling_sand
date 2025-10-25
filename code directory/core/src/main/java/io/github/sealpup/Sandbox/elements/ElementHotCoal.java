package io.github.sealpup.Sandbox.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.sealpup.Sandbox.SimulationWindow;

import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class ElementHotCoal extends BaseElement{
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
        SimulationWindow.elementTypes.water,
        SimulationWindow.elementTypes.acid,
        SimulationWindow.elementTypes.steam,
        SimulationWindow.elementTypes.fire

    };
    static SimulationWindow.elementTypes[] reactableElements = {
        SimulationWindow.elementTypes.water,
        SimulationWindow.elementTypes.coal,
    };
    public static void render(int px, int py, float width, float height, SpriteBatch batch){
        BaseElement.render(px, py, width, height, batch, textures[0]);
    }


    public static void tick(SimulationWindow pWindow, int px, int py) {
        window = pWindow;
        x = px;
        y = py;
        emitFire();
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
                System.out.println("what just happened elements/Elementcoal/tick");
        }
    }
    private static void positionCheck( int targetX, int targetY) {
        if (window.inBounds(targetX, targetY)) {
            if(Arrays.asList(reactableElements).contains(window.getElement(targetX, targetY))) {
                if(window.getElement(targetX, targetY) == SimulationWindow.elementTypes.water) {
                    waterReaction(targetX, targetY);
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
    private static void waterReaction(int TargetX, int TargetY) {
        window.addElement(SimulationWindow.elementTypes.coal, TargetX, TargetY);
        window.addElement(SimulationWindow.elementTypes.steam, x, y);
    }
    private static void coalReaction(int TargetX, int TargetY){

    }
    private static void emitFire(){
        for (int cx = 0; cx < 3; cx++) {
            for(int cy = 0; cy < 3; cy++) {
                if (window.inBounds((x-1)+cx, (y-1)+cy)) {
                    if (window.getElement((x-1)+cx, (y-1)+cy) == SimulationWindow.elementTypes.air) {
                        window.addElement(SimulationWindow.elementTypes.fire, (x-1)+cx, (y-1)+cy);
                        seed = random.nextInt(200);
                        if(seed == 0){
                            window.removeElement(x, y);
                            replaceHot(x,y);
                        }
                    }
                }
            }
        }
        seed = random.nextInt(550);
        if(seed == 0){
            window.addElement(SimulationWindow.elementTypes.coal, x, y);
            if(window.getElement(x,y+1) == SimulationWindow.elementTypes.coal){
                window.addElement(SimulationWindow.elementTypes.hotCoal, x, y+1);
            }
        }

    }
    private static void replaceHot(int x, int y){
        for (int cx = 0; cx < 3; cx++) {
            for(int cy = 0; cy < 3; cy++) {
                if (window.inBounds((x-1)+cx, (y-1)+cy)) {
                    if (window.getElement((x-1)+cx, (y-1)+cy) == SimulationWindow.elementTypes.coal) {
                        window.addElement(SimulationWindow.elementTypes.hotCoal, (x-1)+cx, (y-1)+cy);
                    }
                }
            }
        }
    }

}
