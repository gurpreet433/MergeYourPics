package com.example.mergeyourpics;


import android.graphics.Color;

public class PhotoSettings {
    
    Boolean stackVertically;
    int colorChoice;
    int horizontalSpacing;
    int verticalSpacking;
    Boolean scaleImageToSmallest;


    PhotoSettings()
    {
        stackVertically = true;
        colorChoice = Color.WHITE;
        horizontalSpacing = 48;
        verticalSpacking = 39;
        scaleImageToSmallest = true;
    }

    public Boolean getStackVertically() {
        return stackVertically;
    }

    public int getColorChoice() {
        return colorChoice;
    }

    public int getHorizontalSpacing() {
        return horizontalSpacing;
    }

    public int getVerticalSpacking() {
        return verticalSpacking;
    }

    public Boolean getScaleImageToSmallest() {
        return scaleImageToSmallest;
    }

    // Setter

    public void setStackVertically(Boolean stackVercally) {
        this.stackVertically = stackVercally;
    }


    public void setColorChoice(int colorChoice) {
        this.colorChoice = colorChoice;
    }

    public void setHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
    }

    public void setVerticalSpacking(int verticalSpacking) {
        this.verticalSpacking = verticalSpacking;
    }

    public void setScaleImageToSmallest(Boolean scaleImageToSmallest) {
        scaleImageToSmallest = scaleImageToSmallest;
    }

    public void toggleStackVertically(){
        if (stackVertically == true) {
            stackVertically = false;
        }
        else if (stackVertically == false) {
            stackVertically = true;
        }
    }

    public void toggleScaleImageToSmallest(){
        if (scaleImageToSmallest == true) {
            scaleImageToSmallest = false;
        }
        else if (scaleImageToSmallest == false) {
            scaleImageToSmallest = true;
        }
    }
}
