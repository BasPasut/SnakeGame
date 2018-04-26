package multiplayer;

import java.io.IOException;
import java.util.ArrayList;

import com.esotericsoftware.kryonet.Client;

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
