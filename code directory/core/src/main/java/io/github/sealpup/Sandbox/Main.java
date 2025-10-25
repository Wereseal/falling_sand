package io.github.sealpup.Sandbox;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;


/** {@link ApplicationListener} implementation shared by all platforms. */

public class Main extends ApplicationAdapter {
    //basic variables the library offers
    public SpriteBatch spriteBatch;
    private FitViewport viewport;

    //these could be local, but they are much easier to access here so....
    private final int worldHeight = 100;
    private final int worldWidth = 100;
    private final int margin = 5;
    private long timer = 0;
    // these should be the only implementation of these, if not then something has gone horribly wrong
    private final Mouse playerMouse = new Mouse();
    private AssetManager assetManager;

    /*
    this is a function provided by my graphics library that runs once at the start of runtime and never again.
    basically a constructor
    */
    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        assetManager = new AssetManager();
        viewport = new FitViewport(worldWidth, worldHeight);
    }

    // this is the main game "loop"
    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    // as its named, this is when all the inputs get registered
    private void input(){
        // basically updates mouse position and if it's down or not
        playerMouse.mouseTick();
    }

    // as the name implies, this is where all the logic lies
    private void logic(){
        // checks if mouse is down... shocker
        if(playerMouse.isDown()) {
            // checks if the player pressed something in the window and if so, what
            assetManager.checkMouseCollision(playerMouse.getX(), playerMouse.getY());
        }
        // bit hacky, this checks if this is the tick the user lifted their mouse and if so, lets the buttons know
        else if(playerMouse.initialLiftTick()){
            assetManager.mouseRelease();
        }
        assetManager.tick();
    }

    // as the name implies this handles all draws to the window
    public void draw(){
        // makes the default background colour white
        ScreenUtils.clear(255, 255, 255, 1f);
        viewport.apply();

        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        // all assets go in here
        spriteBatch.begin();
        assetManager.render(spriteBatch);
        spriteBatch.end();
    }

    /*
    returns the margin aka the border either side of the screen where I don't want any assets
     ... probably need to tidy this up
    */
    public int getMargin() {
        return margin;
    }

    // this deallocates memory once the program ends... technically not needed but eh
    @Override
    public void dispose() {
        spriteBatch.dispose();
    }

    // I have no clue what this does, its just stuff for the graphics library
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
