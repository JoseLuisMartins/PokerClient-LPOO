package common;

import java.util.ArrayList;

public interface PlayerInterface {



	public void notify(NotificationType n, int v);


	public void raiseBet(int val);


	public void reset();

	public String getName() ;
	public void setName(String val) ;

	public int getCurrentBet() ;
	public void setCurrentBet(int val) ;

	public int getMoney() ;
	public void setMoney(int val) ;

	public boolean getInGame() ;
	public void setIngame(boolean val) ;

	public ArrayList<Card> getHand() ;
	public void setHand(ArrayList<Card> c) ;

	public PokerHand getHandRank() ;
	public void setHandRank(PokerHand val) ;

	public ArrayList<Card> getCardsOfMove();
	public void setCardsOfMove(ArrayList<Card> cards);

	public ArrayList<Card> getbestFiveCards();
	public void setBestFiveCards(ArrayList<Card> cards);

	public boolean getAllIn();
	public void setAllIn(boolean val);

	public boolean getWin();
	public void setWin(boolean val);
}