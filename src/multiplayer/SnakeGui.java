package multiplayer;

import lib.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class SnakeGui extends JFrame implements Observer {

    private SnakeClient client;
    private GameData lastGameData;
    
    private Renderer renderer;
    private Controller controller;
    private JButton saveButton;
    private JButton loadButton;

    // TODO: You might need one variable to hold a memento

    private void loadGame() {
        // TODO: Implement Loading Game Logic
    }

    private void saveGame() {
        // TODO: Implement Saving Game Logic
    }

    public SnakeGui(SnakeClient snakeGame) {
        client = snakeGame;
        client.addObserver(this);

        controller = new Controller();

        renderer = new Renderer();
        renderer.addKeyListener(controller);

        setLayout(new BorderLayout());
        add(renderer, BorderLayout.CENTER);
        add(new JPanel() {
            {
                saveButton = new JButton("Save");
                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        saveGame();
                        renderer.requestFocus();
                    }
                });
                add(saveButton);
                loadButton = new JButton("Load");
                loadButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        loadGame();
                        renderer.requestFocus();
                    }
                });
                loadButton.setEnabled(false);
                add(loadButton);
            }
        }, BorderLayout.SOUTH);

        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        renderer.requestFocus();
    }

    @Override
    public void update(Observable o, Object arg) {
    	if(arg instanceof GameData){
    		lastGameData = (GameData) arg;
    		System.out.println("Hello");
    	}
        renderer.repaint();
    }

    class Controller extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            if(e.getKeyCode() == KeyEvent.VK_UP) {
                client.handleUpKey();
            } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                client.handleDownKey();
            } else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                client.handleLeftKey();
            } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                client.handleRightKey();
            }
        }
    }

    class Renderer extends JPanel {

        private int blockWidth = 20;
        private int mapSize;

        public Renderer() {
            mapSize = 400;
            setPreferredSize(new Dimension(mapSize, mapSize));
            setDoubleBuffered(true);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            paintGrids(g);
            paintBlocks(g);
        }

        private void paintGrids(Graphics g) {
            g.setColor(Color.gray);
            for (int i = 0; i < mapSize; i++) {
                g.drawLine(i * blockWidth, 0, i * blockWidth, getHeight());
                g.drawLine(0, i * blockWidth, getWidth(), i * blockWidth);
            }
        }

        private void paintBlocks(Graphics g) {
        	if(lastGameData == null){
        		return;
        	}
            for(BlockData b : lastGameData.blockData) {
            	g.setColor(new Color(b.r, b.g, b.b));
                g.fillRect(b.x* blockWidth, b.y * blockWidth, blockWidth, blockWidth);
            }
        }
    }
}

