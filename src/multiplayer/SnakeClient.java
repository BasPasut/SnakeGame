package multiplayer;

import java.io.IOException;
import java.util.ArrayList;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class SnakeClient {
	
	private Client client;
	
	public SnakeClient() throws IOException{
		client = new Client();
		client.getKryo().register(ActionData.class);
		client.getKryo().register(GameData.class);
		client.getKryo().register(BlockData.class);
		client.getKryo().register(ArrayList.class);
		
		client.start();
		client.connect(5000, "127.0.0.1", 54333);
	}
	
	class ClientListener extends Listener{
		@Override
		public void received(Connection connection, Object o) {
			super.received(connection, o);
			if(o instanceof GameData){
				GameData gameData = (GameData) o;
				System.out.println("Receive game data from Server");
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			SnakeClient clinet = new SnakeClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(60*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
