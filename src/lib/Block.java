package lib;

import java.awt.Color;

public class Block {

    protected int x;
    protected int y;
    protected Color color;

    public Block(Color color,int x, int y) {
    	this.color = color;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean overlapped(Block b) {
        return x == b.x && y == b.y;
    }
    
    public Color getColor(){
    	return color;
    }
}
