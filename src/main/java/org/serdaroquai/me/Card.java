package org.serdaroquai.me;

public class Card {

	private String id;
	private String texture;
	private int x;
	private int y;
	
	
	public Card(String id, String texture, int x, int y) {
		this.id = id;
		this.texture = texture;
		this.x = x;
		this.y = y;
	}
	public String getId() {
		return id;
	}
	
	public String getTexture() {
		return texture;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
//	private int suit;
//	private int rank;
//	private boolean faceDown;
//	
//	public static int WIDTH = 44;
//	public static int HEIGHT = 63;
//	
//	private static String[] suits = {"h","s","d","c"};
//	private static String[] ranks = {"A","2","3","4","5","6","7","8","9","T","J","Q","K"};
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
