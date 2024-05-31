package com.example.brickbuster;

public class Brick {

    private boolean isVisible;
    public int row;
    public int column;
    public int width;
    public int height;

    public Brick(int row, int column, int width, int height) {
        // Constructor to set the visibility, row, column, and size of the bricks
        isVisible = true;
        this.row = row;
        this.column = column;
        this.width = width;
        this.height = height;
    }

    public void setInvisible() {
        isVisible = false;
    }

    public boolean getVisibility() {
        return isVisible;
    }
}
