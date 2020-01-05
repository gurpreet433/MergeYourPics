package com.example.mergeyourpics;


import android.graphics.Color;

public class PhotoSettings {
    
    private Boolean stackVertically;
    private int colorChoice;
    private int horizontalSpacing;
    private int verticalSpacing;
    private Boolean scaleImageToSmallest;


    PhotoSettings()
    {
        stackVertically = true;
        colorChoice = Color.WHITE;
        horizontalSpacing = 48; //random width
        verticalSpacing = 39;  // random width
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
        return verticalSpacing;
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

    public void setVerticalSpacking(int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
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
