package io.github.sealpup.Sandbox;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sun.source.tree.AssertTree;
import io.github.sealpup.Sandbox.elements.*;

import java.util.Random;

public class SimulationWindow extends UIelement{

    // contains all elements
    private elementTypes[][] currentArr;
    private elementTypes[][] nextArr;

    // defines the size of the elements
    int elementDensity = 1;
    float elementWidth = 1/(float)elementDensity;
    float elementHeight = 1/(float)elementDensity;

    //simulation settings
    int simulationIter = 0;
    // min speed is 60
    int simulationSpeed = 1;

    Random rand = new Random();
    int seed;

    public enum elementTypes{
        lightStone,
        darkStone,
        movingElement,
        air,
        water,
        hotCoal,
        coal,
        fire,
        wall,
        acid,
        steam,
    }

    // defines brush size and element
    int brushSize = 5;
    static elementTypes brushtype = elementTypes.air;

    // Constructor
    public SimulationWindow(int xPos, int yPos, int width, int height, String name, Texture texture) {
        super(xPos, yPos, width, height, texture, name);
        // creates
        currentArr = new elementTypes[(width-1)*elementDensity][(height-1)*elementDensity];
        nextArr = new elementTypes[(width-1)*elementDensity][(height-1)*elementDensity];
        for(int i = 0; i < currentArr.length; i++){
            for(int j = 0; j < currentArr[0].length; j++){
                currentArr[i][j] = elementTypes.air;
                nextArr[i][j] = elementTypes.air;
            }
        }
    }

    public void mouseDown(float mouseX, float mouseY){
        int relativeX = (int)(((mouseX - xPos)*elementDensity) + ((float) brushSize/2));
        int relativeY = (int)(((mouseY - yPos)*elementDensity) + ((float) brushSize/2) );

        for(int x = 0; x < brushSize; x++){
            for(int y = 0 ; y < brushSize; y++){

                int xCheck = (relativeX-brushSize)+x;
                int yCheck = (relativeY-brushSize)+y;

                if(xCheck >= 0 && xCheck < currentArr.length && yCheck >= 0 && yCheck < currentArr[0].length){
                    if(brushtype != elementTypes.air) {
                        switch (currentArr[xCheck][yCheck]) {
                            case air:
                                currentArr[xCheck][yCheck] = brushtype;
                            case water:
                            case lightStone:
                            case darkStone:
                                break;
                        }
                    }
                    else{
                        currentArr[xCheck][yCheck] = brushtype;
                    }
                }
            }
        }
    }
    @Override
    public void mouseUp(){}
    @Override
    public void otherButtonClicked(){}
    @Override
    public void render(SpriteBatch batch){
        batch.draw(texture, xPos, yPos, width, height);
        for(int x = 0; x < currentArr.length; x++){
            for(int y = 0; y < currentArr[1].length; y++){
                switch(currentArr[x][y]){
                    case air:
                        break;
                    case lightStone:
                        ElementStone.render(x, y, elementWidth, elementHeight, batch, 0);
                        break;
                    case darkStone:
                        ElementStone.render(x, y, elementWidth, elementHeight, batch, 1);
                        break;
                    case water:
                        ElementWater.render(x, y, elementWidth, elementHeight, batch);
                        break;
                    case fire:
                        ElementFire.render(x, y, elementWidth, elementHeight, batch, 0);
                        break;
                    case steam:
                        ElementSteam.render(x, y, elementWidth, elementHeight, batch, 0);
                        break;
                    case wall:
                        ElementWall.render(x, y, elementWidth, elementHeight, batch);
                        break;
                    case coal:
                        ElementCoal.render(x, y, elementWidth, elementHeight, batch);
                        break;
                    case hotCoal:
                        ElementHotCoal.render(x, y, elementWidth, elementHeight, batch);
                        break;
                    case acid:
                        ElementAcid.render(x, y, elementWidth, elementHeight, batch);
                        break;
                    default:
                        BaseElement.render(x, y, elementWidth, elementHeight, batch);
                        break;

                }
            }
        }

    }

    public void addElement(elementTypes elementType, int x, int y){
        nextArr[x][y] = elementType;
        currentArr[x][y] = elementTypes.movingElement;
    }
    public void removeElement( int x, int y){
        currentArr[x][y] = elementTypes.air;
        nextArr[x][y] = elementTypes.air;
    }

    public elementTypes getElement(int x, int y){
        return currentArr[x][y];
    }

    private void elementTick(){
        seed = rand.nextInt(4);
        switch(seed) {
            case 0:
                for (int x = 0; x < currentArr.length; x++) {
                    for (int y = 0; y < currentArr[0].length; y++) {
                        switchElementTick(x, y);
                    }
                }
                break;
            case 1:
                for (int x = 0; x < currentArr.length; x++) {
                    for (int y = currentArr[0].length - 1; y >= 0; y--) {
                        switchElementTick(x, y);
                    }
                }
                break;
            case 2:
                for (int x = currentArr.length-1; x >= 0; x--) {
                    for (int y = 0; y < currentArr[0].length; y++) {
                        switchElementTick(x, y);
                    }
                }
                break;

            case 3:
                for (int x = currentArr.length-1; x >= 0; x--) {
                    for (int y = currentArr[0].length - 1; y >= 0; y--) {
                        switchElementTick(x, y);
                    }
                }
                break;
        }

    }
    private void switchElementTick(int x, int y){
        switch (currentArr[x][y]){
            case lightStone:
                ElementStone.tick(this,"light", x, y);
                break;
            case darkStone:
                ElementStone.tick(this,"dark", x, y);
                break;
            case water:
                ElementWater.tick(this, x, y);
                break;
            case fire:
                ElementFire.tick(this, x, y);
                break;
            case steam:
                ElementSteam.tick(this, x, y);
                break;
            case coal:
                ElementCoal.tick(this, x, y);
                break;
            case hotCoal:
                ElementHotCoal.tick(this, x, y);
                break;
            case acid:
                ElementAcid.tick(this, x, y);
                break;
            case movingElement:
            case air:
            default:
                break;
        }
    }
    public void tick(){
        simulationIter++;
        if(simulationIter == simulationSpeed) {
            elementTick();
            swapArrs();
            simulationIter = 0;
        }
    }

    public void changeBrushSize(int amount){
        if(brushSize+amount >= 1 && brushSize+amount <= 20) {
            brushSize = brushSize + amount;
        }
    }

    public void moveElement(int currentX, int currentY, int targetX, int targetY){
        elementTypes displaced = currentArr[targetX][targetY];
        nextArr[targetX][targetY] = currentArr[currentX][currentY];
        currentArr[targetX][targetY] = elementTypes.movingElement;
        currentArr[currentX][currentY] = displaced;
    }
    private void swapArrs(){
        for(int x = 0; x < currentArr.length; x++){
            for(int y = 0; y < currentArr[0].length; y++){
                if (currentArr[x][y] != elementTypes.air && currentArr[x][y] != elementTypes.movingElement){
                    nextArr[x][y] = currentArr[x][y];
                }
            }
        }
        for(int x = 0; x < currentArr.length; x++){
            for(int y = 0; y < currentArr[0].length; y++){
                currentArr[x][y] = nextArr[x][y];
                nextArr[x][y] = elementTypes.air;
            }
        }
    }
    public boolean inBounds(int x, int y){
        return y < currentArr[0].length && y >= 0 && x < currentArr.length && x >= 0;
    }
    public static void changeBrush(elementTypes elementType){
        brushtype = elementType;
    }
    public elementTypes outNext(int x, int y){
        return(nextArr[x][y]);
    }
    public void changeSimulationSpeed(int change){
        if(simulationSpeed-change <= 60 && simulationSpeed-change >= 1) {
            simulationSpeed -= change;
            simulationIter -= change;
            System.out.println(simulationSpeed);
        }
    }
}

