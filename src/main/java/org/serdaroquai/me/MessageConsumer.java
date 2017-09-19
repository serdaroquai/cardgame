package org.serdaroquai.me;

import java.security.Principal;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired private BlockingQueue<Action> actionQueue;
	@Autowired private ApplicationController controller;
	
	// state
	@Autowired private GameState gameState;
	
	@Async
	public void start() {
		while (true) {
			
			try {
				Action action = actionQueue.take();
				logger.info("Consuming " + action);
				
				
				switch (action.getType()) {
				case "MOVE":
					gameState.update(action.getX(), action.getY());
					break;
				case "REGISTER":
					register(gameState, action.getPrincipal());
					break;
				default:
					throw new Error("Received no action");
				}
				
				// dispatch game state
				controller.dispatch(gameState);
				
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	public void register(GameState state, Principal p) {
		state.addPlayer(p);
	}
	
}
