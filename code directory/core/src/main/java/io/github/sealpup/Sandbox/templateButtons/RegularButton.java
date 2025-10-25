package io.github.sealpup.Sandbox.templateButtons;

import com.badlogic.gdx.graphics.Texture;
import io.github.sealpup.Sandbox.Button;

public class RegularButton extends Button {

    public RegularButton(int xPos, int yPos, int width, int height, String name, Texture baseTexture, Texture selectedTexture) {
        super(xPos, yPos, width, height, name, baseTexture, selectedTexture);
    }

    @Override
    public void mouseDown(){
        this.uiSelected();
    }
    @Override
    public void mouseUp(){
        this.uiUnselected();
    }
    @Override
    public void otherButtonClicked(){}

}

