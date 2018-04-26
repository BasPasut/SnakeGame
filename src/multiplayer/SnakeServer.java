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
