package common;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Represent's a card
 */
public class Card implements Serializable, Comparable<Card>{

	private static final long serialVersionUID = 1L;
	public Suit suit;
	public Rank rank;

	/**
	 * Create's a cart
	 * @param s suit of the card
	 * @param r Rank of the card
	 */
	public Card(Suit s,Rank r ){
		suit=s;
		rank=r;
	}
 
	/**
	 * @return rank of the card
	 */
	public Rank getRank(){
		return rank;
	}
	
	/**
	 * @return suit of the card 
	 */
	public Suit getSuit(){
		return suit;
	}

	/**
	 * @return the position of the suit on the enum Suit
	 */
	public int numberOfSuit(){
		return suit.getValue();
	}
	
	@Override
	public String toString() {
		return (rank.toString() + " " + suit.toString() );
	}
	
	/**
	 * @return the position of the rank in the enum Rank
	 */
	public int numberOfRank(){
		return rank.getValue();

	}

	@Override
	public int compareTo(Card c) {
		if (this.getRank().getValue() > c.getRank().getValue()) {
			return 1;
		}
		else if (this.getRank().getValue() < c.getRank().getValue()) {
			return -1;
		}
		return 0;    
	}

	@Override
	public boolean equals(Object obj) {

		Card c = (Card) obj;
		if (this.getRank().getValue() == c.getRank().getValue()){
			if(this.getSuit() == c.getSuit())
				return true;
		}

		return false;
	} 
	
	/**
	 *  fill an arrayList with the poitions of the card on the image
	 * @param cellwidth
	 * @param cellHeight
	 * @return the arrayList with the positions
	 */
	
    public ArrayList<Integer> postionOnImage(int cellwidth, int cellHeight){
        // SX1 - SY1 - SX2 - SY2
        ArrayList<Integer> position = new ArrayList<Integer>();
 
        int sx1,sx2,sy1,sy2;
 
        sy1 = numberOfSuit() * cellHeight;
        sy2 = sy1 + cellHeight;
 
        if(rank == Rank.ACE) {
            sx1 = 0;
            sx2 = cellwidth;
        }else if(rank == Rank.NONE && suit == Suit.NONE){
            sx1 = 0;
            sx2 = cellwidth;
            sy1= cellHeight * 4;
            sy2= cellHeight * 5;
        }else {
            sx1 = (numberOfRank() + 1) * cellwidth;
            sx2 = sx1 + cellwidth;
        }
 
        position.add(sx1);
        position.add(sy1);
        position.add(sx2);
        position.add(sy2);
 
 
        return position;
    }
 
}