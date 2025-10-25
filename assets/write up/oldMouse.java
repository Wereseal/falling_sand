package io.github.sealpup.Sandbox;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class mouse {
    public float x = 0;
    public float y = 0;
    public boolean down;
    private UIelement lastElementPressed;
    private button lastButtonPressed;

    public void pressed(int worldWidth, int worldHeight, FitViewport viewport) {
        x = Gdx.input.getX();
        y = Gdx.input.getY();
        x *= (float) worldWidth/Gdx.graphics.getWidth();
        y *= (float) worldHeight/Gdx.graphics.getHeight();
        y = 100 - y;
        down = true;
    }
    public void setLastPressed(UIelement lastPressed) {
        this.lastElementPressed = lastPressed;
        this.lastButtonPressed = null;
    }
    public void setLastPressed(button lastPressed) {
        this.lastButtonPressed = lastPressed;
        this.lastElementPressed = null;
    }
    public String Buttontype(){
        if(lastElementPressed != null){
            return "element";
        }
        if(lastButtonPressed != null) {
            return "button";
        }
        System.out.println("oh shit oh fuck oh shit, mouse/Buttontype");
        return null;
    }
    public UIelement getLastElementPressed() {

        if(lastElementPressed != null){
            return  lastElementPressed;
        }
        if(lastButtonPressed != null) {
            return lastButtonPressed;
        }
        return null;
    }
}
