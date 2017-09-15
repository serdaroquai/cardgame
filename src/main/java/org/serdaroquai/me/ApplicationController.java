package org.serdaroquai.me;

import java.security.Principal;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ApplicationController {
	
	@Autowired private BlockingQueue<Action> actionQueue;
	@Autowired private MessageConsumer messageConsumer;
	@Autowired private SimpMessagingTemplate template;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostConstruct
	private void initialize() {
		
		messageConsumer.start();
		
	}
	
    @MessageMapping("/message")
    public void accept(@Payload Action action, Principal principal) throws Exception {
        
    	action.setPrincipal(principal);
    	logger.info("Registering " + action.toString());
    	actionQueue.put(action);
    	
    }
    
    public void dispatch(GameState state) {
		template.convertAndSendToUser(state.players.get(0), "/queue/private", state);					
    }
	
}
