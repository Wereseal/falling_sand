package io.github.sealpup.Sandbox.templateButtons;

import com.badlogic.gdx.graphics.Texture;
import io.github.sealpup.Sandbox.UIelement;

public class EmptyElement extends UIelement {
    public EmptyElement() {
        super(0, 0, 0, 0, new Texture("libgdx.png"), "emptyElement");
    }

    @Override
    public void mouseDown(){}
    @Override
    public void mouseUp(){}
    @Override
    public void otherButtonClicked(){}
}
