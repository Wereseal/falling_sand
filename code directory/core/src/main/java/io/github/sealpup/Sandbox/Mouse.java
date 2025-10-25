package io.github.sealpup.Sandbox;
import com.badlogic.gdx.Gdx;

public class Mouse {
    private float x = 0;
    private float y = 0;
    private boolean down;
    private boolean wasDown;

    public void mouseTick(){
        if(Gdx.input.isTouched()){
            pressed();
            down = true;
            wasDown = true;
        }
        else{
            down = false;
        }

    }
    private void pressed(){
        int worldWidth = 100; int worldHeight = 100;
        x = Gdx.input.getX();
        y = Gdx.input.getY();
        x *= (float) worldWidth/Gdx.graphics.getWidth();
        y *= (float) worldHeight/Gdx.graphics.getHeight();
        y = worldHeight - y;
        down = true;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public boolean isDown(){
        return down;
    }
    public boolean initialLiftTick(){
        if(wasDown && !down){
            wasDown = false;
            return true;
        }
        return false;
    }



}
