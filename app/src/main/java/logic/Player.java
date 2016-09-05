package logic;



import java.util.ArrayList;

import common.Card;
import common.NotificationType;
import common.PlayerInterface;
import common.PokerHand;
import common.Rank;
import common.Suit;
import network.NotificationHandler;

public class Player  implements PlayerInterface {
	
	

	private String name;
	private int currentBet;
	private int money;
	private boolean inGame;
	private ArrayList<Card> hand;
	private PokerHand handRank;
	private boolean allIn;
	private ArrayList<Card> bestFiveCards;
	private ArrayList<Card> cardsOfMove;
	private NotificationHandler nHandler=null;
	private boolean win;


	public Player()  {

		money=2000;
		bestFiveCards=new ArrayList<Card>();
		cardsOfMove=new ArrayList<Card>();
		hand=new ArrayList<Card>();
		hand.add(new Card(Suit.NONE, Rank.NONE));
		hand.add(new Card(Suit.NONE, Rank.NONE));
		win=false;
		reset();
	}



	public void setNhandler(NotificationHandler n){
		nHandler=n;
	}

	@Override
	public void notify(NotificationType n, int v)  {
		if(nHandler != null)
			nHandler.notified(n,v);
	}



	@Override
	public String getName()  {
		return name;
	}

	@Override
	public void setName(String val)  {
		name = val;
	}

	@Override
	public void reset() {
		currentBet=0;
		inGame=false;
		allIn=false;

	}



	@Override
	public int getCurrentBet()  {
		return currentBet;
	}



	@Override
	public void setCurrentBet(int val)  {
		currentBet = val;
	}

	@Override
	public void raiseBet(int val)  {
		int aux = money;
		money -= val-currentBet;
		
		if(money <= 0){//all in
			money=0;
			allIn=true;
			currentBet=aux;
		}else
			currentBet = val;
	}

	@Override
	public int getMoney()  {
		return money;
	}



	@Override
	public void setMoney(int val)  {
		money = val;
	}

	@Override
	public boolean getInGame()  {
		return inGame;
	}

	@Override
	public void setIngame(boolean val)  {
		inGame = val;
	}
	
	
	@Override
	public ArrayList<Card> getHand()  {

		return hand;
	}
	
	
	@Override
	public void setHand(ArrayList<Card> v)  {
		hand=v;
	}

	@Override
	public PokerHand getHandRank()  {
		return handRank;
	}

	@Override
	public void setHandRank(PokerHand val)  {
		handRank = val;
	}

	@Override
	public ArrayList<Card> getCardsOfMove() {
		return cardsOfMove;

	}

	@Override
	public void setCardsOfMove(ArrayList<Card> cards) {
		cardsOfMove=cards;
	}

	@Override
	public ArrayList<Card> getbestFiveCards() {
		return bestFiveCards;
	}

	@Override
	public void setBestFiveCards(ArrayList<Card> cards) {
		bestFiveCards=cards;
	}

	@Override
	public boolean getAllIn() {
		return allIn;
	}

	@Override
	public void setAllIn(boolean val) {
		money=0;
		allIn=val;		
	}

	@Override
	public boolean getWin() {
		return win;
	}

	@Override
	public void setWin(boolean val) {
		win=val;
	}

}
