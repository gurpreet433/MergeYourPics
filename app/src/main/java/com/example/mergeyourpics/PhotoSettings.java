package com.example.mergeyourpics;


import android.graphics.Color;

public class PhotoSettings {
    
    Boolean stackVertically;
    Color colorChoice;
    int horizontalSpacing;
    int verticalSpacking;
    Boolean ScaleImageToSmallest;

    public Boolean getStackVertically() {
        return stackVertically;
    }

    public Color getColorChoice() {
        return colorChoice;
    }

    public int getHorizontalSpacing() {
        return horizontalSpacing;
    }

    public int getVerticalSpacking() {
        return verticalSpacking;
    }

    public Boolean getScaleImageToSmallest() {
        return ScaleImageToSmallest;
    }

    // Setter

    public void setStackVertically(Boolean stackVercally) {
        this.stackVertically = stackVercally;
    }


    public void setColorChoice(Color colorChoice) {
        this.colorChoice = colorChoice;
    }

    public void setHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
    }

    public void setVerticalSpacking(int verticalSpacking) {
        this.verticalSpacking = verticalSpacking;
    }

    public void setScaleImageToSmallest(Boolean scaleImageToSmallest) {
        ScaleImageToSmallest = scaleImageToSmallest;
    }

    public void toggleStackVertically(){
        if (stackVertically == true)
            stackVertically = false;
        else if (stackVertically == false)
            stackVertically = true;
    }

    public void ScaleImageToSmallest(){
        if (ScaleImageToSmallest == true)
            ScaleImageToSmallest = false;
        else if (ScaleImageToSmallest == false)
            ScaleImageToSmallest = true;
    }
}
