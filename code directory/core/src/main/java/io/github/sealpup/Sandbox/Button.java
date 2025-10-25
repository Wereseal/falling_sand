package io.github.sealpup.Sandbox;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class Button extends UIelement{
    public Texture activeTexture;
    public Texture selectedTexture;
    private boolean selected = false;

    public Button(int xPos, int yPos, int width, int height, String name, Texture texture, Texture selectedTexture) {
        super(xPos, yPos, width, height, texture, name);
        this.activeTexture = texture;
        this.selectedTexture = selectedTexture;
    }
    public void uiSelected(){
        activeTexture = this.selectedTexture;
        selected = true;
    }
    public void uiUnselected(){
        activeTexture = this.texture;
        selected = false;
    }
    @Override
    public void render(SpriteBatch batch){
        batch.draw(this.activeTexture, xPos, yPos, width, height);
    }
    public boolean isSelected(){
        return selected;
    }


}
