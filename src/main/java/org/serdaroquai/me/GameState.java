package org.serdaroquai.me;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class GameState {

	List<Card> cards = new ArrayList<Card>();
	List<Player> players = new ArrayList<Player>();
	
	public GameState() {
		Card card = new Card(new Sprite("testId","card.png", new Double(Math.random() * 700).intValue(), new Double(Math.random() * 500).intValue()));
		cards.add(card);
	}
	
	public void update(int x, int y) {
		cards.get(0).getSprite().setX(x);
		cards.get(0).getSprite().setY(y);
	}
	
	public List<Card> getCards() {
		return cards;
	}
	
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public void addPlayer(Principal p) {
		Player player = new Player();
		player.setName(p.getName());
		
		if (players.stream().anyMatch(x -> x.equals(player))) {
			return;
		} else {
			players.add(player);
		}
	}

	public GameState fromPerspectiveOf(Player player) {
		// TODO for now skip the perspectives
		return this;
	}
	
	
}
