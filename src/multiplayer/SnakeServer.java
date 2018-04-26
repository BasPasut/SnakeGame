package multiplayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import lib.Block;
import main.SnakeGame;
import main.Snake;

public class SnakeServer extends SnakeGame {

	private Server server;
	private Map<Connection, Snake> connections;

	public SnakeServer() throws IOException {
		connections = new HashMap<Connection, Snake>();
		server = new Server();
		server.getKryo().register(ActionData.class);
		server.getKryo().register(GameData.class);
		server.getKryo().register(BlockData.class);
		server.getKryo().register(ArrayList.class);
		server.addListener(new ServerListener());
		server.start();
		server.bind(54333);
		System.out.println("Server Started");
	}

	class ServerListener extends Listener {
		@Override
		public void connected(Connection connection) {
			super.connected(connection);
			Snake newSnake = new Snake(map, 10, 10);
			connections.put(connection, newSnake);
			System.out.println("Client Connected");
		}

		@Override
		public void disconnected(Connection connection) {
			super.disconnected(connection);
			connections.remove(connection);
			System.out.println("Client disconnected");
		}

		@Override
		public void received(Connection connection, Object o) {
			super.received(connection, o);
			if (o instanceof ActionData) {
				ActionData ad = (ActionData) o;
				Snake snake = connections.get(connection);
				snake.setDx(ad.dx);
				snake.setDy(ad.dy);
			}
		}
	}

	@Override
	protected void gameLogic() {
		for(Snake snake : connections.values()){
			snake.move();
		}
		sendDataToClients();
	}

	public void sendDataToClients(){
		GameData gameData = new GameData();
		for(Snake snake : connections.values()){
			for(Block block : snake.getBody()){
				BlockData data = new BlockData();
				data.x = block.getX();
				data.y = block.getY();
				data.r = block.getColor().getRed();
				data.g = block.getColor().getGreen();
				data.b = block.getColor().getBlue();
				gameData.blockData.add(data);
			}
		}
		//send out
		for(Connection c : connections.keySet()){
			c.sendTCP(gameData);
		}
		
	}
	
	public static void main(String[] args) {
		SnakeServer server = null;
		try {
			server = new SnakeServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.start();
	}

}
