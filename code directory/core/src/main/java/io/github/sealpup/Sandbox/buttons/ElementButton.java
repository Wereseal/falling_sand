package io.github.sealpup.Sandbox.buttons;

import com.badlogic.gdx.graphics.Texture;
import io.github.sealpup.Sandbox.SimulationWindow;
import io.github.sealpup.Sandbox.templateButtons.ToggleableButton;

public class ElementButton extends ToggleableButton {

    SimulationWindow.elementTypes element;

    public ElementButton(int xPos, int yPos, int width, int height, String name, Texture baseTexture, Texture selectedTexture, SimulationWindow.elementTypes pElement) {
        super(xPos, yPos, width, height, name, baseTexture, selectedTexture);
        element = pElement;

    }
    @Override
    public void mouseDown(){
        SimulationWindow.changeBrush(element);
    }
}
