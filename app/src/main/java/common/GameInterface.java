package common;


public interface GameInterface   {

	
	public ConnectMessage join(PlayerInterface player) throws InterruptedException;

	public void leave(String name);

	public void playerAction(String name, Action act, int value);
	
	public void check();
	
}