package io.github.sealpup.Sandbox;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.awt.*;
import java.util.ArrayList;

/** {@link ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch spriteBatch;
    private FitViewport viewport;

    private ArrayList<UIelement> backgroundTextures = new ArrayList<>();

    private final int worldHeight = 100;
    private final int worldWidth = 100;
    private int margin = 5;

    private mouse playerMouse = new mouse();
    private float deltaTime;


    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        backgroundTextures.add(new UIelement("simulation window", "UIelement", (float)(margin), worldHeight-50, worldWidth-(2*margin), 50, new Texture("simulationView.png")));
        backgroundTextures.add(new button("settings button", "button", (float)(margin), 35, 10, 10, new Texture("settingsButton.png"), new Texture("settingsButtonDown.png")));
        viewport = new FitViewport(worldWidth, worldHeight);
    }

    public void uiSelection(){
        // checks if the mouse is down
        if(!playerMouse.down) {
            return;
        }
        // loops for every ui element in the background
        for (var element : backgroundTextures) {
            // checks for the mouse clicking within a buttons borders
            if ((playerMouse.x >= element.xPos && playerMouse.x <= element.xPos + element.width) && (playerMouse.y >= element.yPos && playerMouse.y <= element.yPos + element.height)) {
                if(element.type.equals("UIelement")) {
                    pressedElement(element);
                }
                else if(element.type.equals("button")) {
                    pressedButton((button)element);
                }
                else{
                    System.out.println("ERROR: Main/uiSelection/1 " + element.type);
                }
            } else {
                if(element.type.equals("UIelement")) {
                    pressedEmpty(element);
                }
                else if(element.type.equals("button")) {
                    pressedEmpty((button)element);
                }
                else{
                    System.out.println("ERROR: Main/uiSelection/2 " + element.type);
                }
            }
        }
        playerMouse.down = false;


    }
    private void pressedButton(button element) {
        if(element == playerMouse.getLastElementPressed()) {
            return;
        }
        if(playerMouse.getLastElementPressed() != null) {
            ((button)playerMouse.getLastElementPressed()).uiUnselected();
        }
        element.uiSelected();
        playerMouse.setLastPressed(element);
        System.out.println(element.name);
    }
    private void pressedElement(UIelement element) {
        // checks if the user pressed on an element that was already selected
        if (playerMouse.getLastElementPressed() == element) {
            return;
        }
        if (playerMouse.getLastElementPressed() != null) {
            if(playerMouse.getLastElementPressed().type.equals("button")) {
                ((button) playerMouse.getLastElementPressed()).uiUnselected();
            }
        }
        playerMouse.setLastPressed(element);
    }
    private void pressedEmpty(button element) {
        if (playerMouse.getLastElementPressed() == null) {
            return;
        }
        if(playerMouse.getLastElementPressed().type.equals("button")) {
            ((button) playerMouse.getLastElementPressed()).uiUnselected();
        }
        playerMouse.setLastPressed(null);
    }
    private void pressedEmpty(UIelement element) {
        if (playerMouse.getLastElementPressed() == null) {
            return;
        }
        if(element != playerMouse.getLastElementPressed()) {
            return;
        }
        playerMouse.setLastPressed(null);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    private void input(){
        if(Gdx.input.isTouched()){
            playerMouse.pressed(worldWidth, worldHeight, viewport);

        }
    }
    private void logic(){
        deltaTime = Gdx.graphics.getDeltaTime();
        uiSelection();

    }
    private void draw(){
        ScreenUtils.clear(255, 255, 255, 1f);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        for(UIelement element : backgroundTextures) {
            if(element.type.equals("UIelement")) {
                spriteBatch.draw(element.texture, element.xPos, element.yPos, element.width, element.height);
            }
            else if(element.type.equals("button")) {
                spriteBatch.draw(((button)element).getActiveTexture(), element.xPos, element.yPos, element.width, element.height);
            }
        }
        spriteBatch.end();
    }

    @Override
    public void render() {
        input();
        logic();
        draw();

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();

    }
}
