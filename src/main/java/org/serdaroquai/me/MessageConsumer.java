package org.serdaroquai.me;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer{

	@Autowired private BlockingQueue<Action> actionQueue;
	@Autowired private SimpMessagingTemplate template;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	
	@Async
	public void start() {
		while (true) {
			
			try {
				Action action = actionQueue.take();
				logger.info("Consuming " + action);
				
				
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
