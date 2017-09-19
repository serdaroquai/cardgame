package org.serdaroquai.me;

public class Card {

//	private static String[] suits = {"h","s","d","c"};
//	private static String[] ranks = {"A","2","3","4","5","6","7","8","9","T","J","Q","K"};
	
	private Sprite sprite;
	
	public Card() { };
	
	public Card(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
//	private int suit;
//	private int rank;
//	private boolean faceDown;
//	
//	public static int WIDTH = 44;
//	public static int HEIGHT = 63;
//	

//	
//	public Card(int suit, int rank) {
//		
//		this.suit = suit;
//		this.rank = rank;
//		faceDown = true;
//	}
//
//	
//	public boolean isFaceDown() {
//		return faceDown;
//	}
//	
//	@Override
//	public String toString() {
//		return String.format("Card[%s%s],isFaceDown:%s", ranks[rank], suits[suit], faceDown);
//	}
//
//	
//	public void flip() {
//		faceDown = !faceDown;
//	}
	
}
