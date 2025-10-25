package io.github.sealpup.Sandbox;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UIelement {
    protected int xPos;
    protected int yPos;
    protected int width;
    protected int height;
    protected Texture texture;
    protected String name;


    public UIelement(int xPos, int yPos, int width, int height, Texture texture, String name) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.texture = texture;
        this.name = name;
    }
    public void render(SpriteBatch batch){
        batch.draw(texture, xPos, yPos, width, height);
    }
    public int getXLowerBound(){
        return xPos;
    }
    public int getXUpperBound(){
        return xPos + width;
    }
    public int getYLowerBound(){
        return yPos;
    }
    public int getYUpperBound(){
        return yPos + height;
    }
    public String getName(){
        return name;
    }
    public void mouseDown(){}
    public void mouseUp(){}
    public void otherButtonClicked(){}



}
