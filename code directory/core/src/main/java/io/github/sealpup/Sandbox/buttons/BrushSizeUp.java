package io.github.sealpup.Sandbox.buttons;

import com.badlogic.gdx.graphics.Texture;
import io.github.sealpup.Sandbox.SimulationWindow;
import io.github.sealpup.Sandbox.templateButtons.RegularButton;

public class BrushSizeUp extends RegularButton {

    private final SimulationWindow simulationWindow;
    public BrushSizeUp(int xPos, int yPos, int width, int height, String name, Texture baseTexture, Texture selectedTexture, SimulationWindow pSimulationWindow) {
        super(xPos, yPos, width, height, name, baseTexture, selectedTexture);
        simulationWindow = pSimulationWindow;
    }
    @Override
    public void mouseDown(){
        this.uiSelected();
        simulationWindow.changeBrushSize(1);
    }
}
