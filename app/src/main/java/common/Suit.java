package common;


public enum Suit {
	HEARTS(0),
	DIAMONDS(1),
	CLUBS(2),
	SPADES(3),
	NONE(4);
	
	private int value;
	
	private Suit(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
	
}