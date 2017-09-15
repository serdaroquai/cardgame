package org.serdaroquai.me;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class GameState {

//	List<String> players = ;
//	Stream<Card> cards = Stream.of(new Card(1,1));
	List<Card> cards = new ArrayList<Card>();
	List<String> players = new ArrayList<String>();
	
	public GameState() {
		cards.add(new Card("testId","card.png", new Double(Math.random() * 700).intValue(), new Double(Math.random() * 500).intValue()));
		players.add("player1");
	}
	
	public void update(int x, int y) {
		cards.get(0).setX(x);
		cards.get(0).setY(y);
	}
	
	public List<Card> getCards() {
		return cards;
	}
	
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	
	public List<String> getPlayers() {
		return players;
	}
	
	public void setPlayers(List<String> players) {
		this.players = players;
	}
	
	
}
