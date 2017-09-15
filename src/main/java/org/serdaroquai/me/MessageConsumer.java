package org.serdaroquai.me;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer{

	@Autowired private BlockingQueue<Action> actionQueue;
	@Autowired private ApplicationController controller;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired private GameState gameState;
	
	@Async
	public void start() {
		while (true) {
			
			try {
				Action action = actionQueue.take();
				logger.info("Consuming " + action);
				
				if ("MOVE".equals(action.getType())) {
					gameState.update(action.getX(), action.getY());
				}
				controller.dispatch(gameState);
				
				
//				(state, action) => state;
				//TODO redux style
				
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
}
