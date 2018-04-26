package main;

import lib.*;

import java.awt.Color;
import java.util.List;
import java.util.Random;

public class SnakeGame extends AbstractGame {

    protected Random random = new Random();
    protected Map map = new Map();

    protected Snake snake = new Snake(map,10, 10);
    protected Food food = new Food(Color.GREEN,15, 15);

    public SnakeGame() {
        for(Block b : snake.getBody()) {
            map.addBlock(b);
        }
        map.addBlock(food);
    }

    public int getMapSize() {
        return map.getSize();
    }

    public List<Block> getBlocks() {
        return map.getBlocks();
    }

    @Override
    protected void gameLogic() {
        snake.move();
        // Check if snake eat food
        for(Block b : snake.getBody()) {
            if(b.overlapped(food)) {
                Block newBlock = snake.expand();
                map.addBlock(newBlock);
                food.setX(random.nextInt(map.getSize()));
                food.setY(random.nextInt(map.getSize()));
                break;
            }
        }
        // Check if snake hit itself
        if(snake.hitItself()) {
            end();
        }
    }

    @Override
    protected void handleLeftKey() {
        snake.setDx(-1);
        snake.setDy(0);
    }

    @Override
    protected void handleRightKey() {
        snake.setDx(1);
        snake.setDy(0);
    }

    @Override
    protected void handleUpKey() {
        snake.setDx(0);
        snake.setDy(-1);
    }

    @Override
    protected void handleDownKey() {
        snake.setDx(0);
        snake.setDy(1);
    }

}