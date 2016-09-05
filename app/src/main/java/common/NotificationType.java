package common;

public enum NotificationType {
	YOURTURN(0),
	YOUWIN(1),
	YOULOSE(2),
	WAITING(3),
	UPDATE(4);
	
	private int value;
	
	private NotificationType(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
}
