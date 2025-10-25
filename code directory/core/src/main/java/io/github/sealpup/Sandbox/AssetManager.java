package io.github.sealpup.Sandbox;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sun.source.tree.EmptyStatementTree;
import io.github.sealpup.Sandbox.buttons.*;
import io.github.sealpup.Sandbox.templateButtons.*;

public class AssetManager {
    // all assets on screen go here
    private final SimulationWindow simulationWindow;
    RegularButton[] regularButtons;
    ToggleableButton[] toggleableButtons;
    UIelement[] uiElements;
    // simple constructor, just defines the variables that need to be
    public AssetManager() {
        // gets the margin from main
        simulationWindow = new SimulationWindow(column(1), 50, 90, 50, "simulationWindow", new Texture("simulationView.png"));
        //todo: text assets go here
        regularButtons = new RegularButton[]{
            new SimulationSpeedUp(column(3), row(1), 10, 10, "SimulationUpButton", new Texture("plusButton.jpg"), new Texture("plusButtonDown.png"), simulationWindow),
            new SimulationSpeedDown(column(4), row(1), 10, 10, "SimulationDownButton", new Texture("minusButton.jpg"), new Texture("minusButtonDown.jpg"), simulationWindow),
            new BrushSizeUp(column(1), row(1), 10, 10, "brushUpButton", new Texture("plusButton.jpg"), new Texture("plusButtonDown.png"), simulationWindow),
            new BrushSizeDown(column(2), row(1), 10, 10, "brushDownButton", new Texture("minusButton.jpg"), new Texture("minusButtonDown.jpg"), simulationWindow),
        };
        toggleableButtons = new ToggleableButton[]{
            new ElementButton(column(5), row(1), 10, 10, "Empty", new Texture("waterButton.png"), new Texture("waterButton.png"), SimulationWindow.elementTypes.air),

            new ElementButton(column(1), row(2), 10, 10, "stone", new Texture("stoneButton.png"), new Texture("stoneButton.png"), SimulationWindow.elementTypes.lightStone),
            new ElementButton(column(2), row(2), 10, 10, "water", new Texture("waterButton.png"), new Texture("waterButton.png"), SimulationWindow.elementTypes.water),
            new ElementButton(column(3), row(2), 10, 10, "fire", new Texture("waterButton.png"), new Texture("waterButton.png"), SimulationWindow.elementTypes.fire),
            new ElementButton(column(4), row(2), 10, 10, "wall", new Texture("waterButton.png"), new Texture("waterButton.png"), SimulationWindow.elementTypes.wall),
            new ElementButton(column(5), row(2), 10, 10, "coal", new Texture("waterButton.png"), new Texture("waterButton.png"), SimulationWindow.elementTypes.coal),
            new ElementButton(column(6), row(2), 10, 10, "Steam", new Texture("waterButton.png"), new Texture("waterButton.png"), SimulationWindow.elementTypes.steam),

            new ElementButton(column(1), row(3), 10, 10, "acid", new Texture("waterButton.png"), new Texture("waterButton.png"), SimulationWindow.elementTypes.acid),

        };
        // all assets on screen get assigned here

        uiElements = new UIelement[regularButtons.length + toggleableButtons.length+1];
        int pos = 0;
        for (RegularButton regularButton : regularButtons) {
            uiElements[pos] = regularButton;
            pos++;
        }
        for (ToggleableButton toggleableButton : toggleableButtons) {
            uiElements[pos] = toggleableButton;
            pos++;
        }
        uiElements[pos] = simulationWindow;
    }

    // simple render loop, feels weird not having a for loop here, but I guess it's easier to read
    public void render(SpriteBatch batch) {
        simulationWindow.render(batch);

        for (RegularButton button : regularButtons) {
            button.render(batch);
        }
        for (ToggleableButton button : toggleableButtons) {
            button.render(batch);
        }
    }

    // defines what to when the mouse is no longer down
    public void mouseRelease(){
        /*
        contains a list of all regular buttons
        I believe this should be a global array as well as toggleable buttons for code readability
        */
        for (RegularButton regularButton : regularButtons) {
            regularButton.mouseUp();
        }
    }

    /*
    switches what logic to run depending on what the user pressed on
    ugly function, it's going to break at some point, and it will make me want to cry.
    */
    public void checkMouseCollision(float mouseX, float mouseY) {
        // contains all assets that have interactions when pressed
        // variable contains what the user has pressed on this tick, could cause lag?
        UIelement collision = collisionFinder(mouseX, mouseY, uiElements);
        // checks if the user pressed on the background

        if (collision == null) {
            return;
        }
        // checks if the user pressed on a toggleable and that that wasn't same as the last Toggleable pressed
        if (collision instanceof ToggleableButton) {
            if (!((ToggleableButton) collision).isSelected()) {
                for (ToggleableButton button : toggleableButtons) {
                    if (button.isSelected()) {
                        button.otherButtonClicked();
                    }
                }
                collision.mouseDown();
            }
        }
        // checks if a regular button was pressed
        else if (collision instanceof RegularButton) {
            if (!((RegularButton) collision).isSelected()) {
                collision.mouseDown();
                System.out.println(collision.getClass());
            }
        }
        // checks if the simulation window was pressed
        else if (collision instanceof SimulationWindow) {
            ((SimulationWindow) collision).mouseDown(mouseX, mouseY);
        }
        else{
            System.out.println("what just happened? AssetManager/checkMouseCollision");
        }


    }

    // checks for the mouse pressing on any assets
    private UIelement collisionFinder(float mouseX, float mouseY, UIelement[] elements){
        for(UIelement element : elements){
            if(!boundsCheck(mouseX, mouseY, element).equals("no collision")){
                return element;
            }
        }
        return null;
    }

    // checks if the collision was within the border of asset given (don't have many assets, so doesn't need optimisation)
    private String boundsCheck(float mouseX, float mouseY, UIelement element) {
        if(mouseX >= element.getXLowerBound() && mouseX <= element.getXUpperBound() && mouseY >= element.getYLowerBound() && mouseY <= element.getYUpperBound()) {
            return element.getName();
        }
        return "no collision";
    }

    public void tick() {
        simulationWindow.tick();
    }
    private int row(int rowNum){
        return 50-(rowNum)*15;
    }
    private int column(int colNum){
        return 5+(colNum-1)*15;
    }

}
