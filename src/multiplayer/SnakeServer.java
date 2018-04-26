package multiplayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import main.SnakeGame;
import main.Snake;

public class SnakeServer extends SnakeGame {
	
	private Server server;
	private Map<Connection,Snake> connections;
	
	public SnakeServer() throws IOException {
		connections = new HashMap<Connection,Snake>();
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
	
	class ServerListener extends Listener{
		@Override
		public void connected(Connection connection) {
			super.connected(connection);
			Snake newSnake = new Snake(map,10,10);
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
			if(o instanceof Message){
				Message message = (Message) o;
				messages.add(message);
				for(Connection c : connections){
					c.sendTCP(message);
				}
				System.out.println("Server gets message: " + message.text);
			}
		}
	}
	
	@Override
	protected void gameLogic(){
		System.out.println("Running");
	}

	public static void main(String[] args){
		SnakeServer server = new SnakeServer();
		server.start();
	}
	
}
