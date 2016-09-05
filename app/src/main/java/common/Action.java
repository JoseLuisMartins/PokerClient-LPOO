package common;



public enum Action {
	RAISE(0),
	FOLD(1),
	ALL_IN(2),
	CALL_CHECK(3),
	NONE(4);
	
	private int value;
	
	private Action(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
}