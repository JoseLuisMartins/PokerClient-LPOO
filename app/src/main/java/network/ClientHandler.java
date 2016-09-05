package network;


import common.GameInterface;
import common.PlayerInterface;
import lipermi.handler.CallHandler;
import lipermi.net.Client;

public class ClientHandler {

	public static GameInterface initClient(PlayerInterface player, String ip, int port) {
		GameInterface game =null;
		try{
			CallHandler callHandler = new CallHandler();
			Client client = new Client(ip, port, callHandler);
			game = (GameInterface)client.getGlobal(GameInterface.class);
			
	        callHandler.exportObject(PlayerInterface.class, player);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return game;
	}
}
