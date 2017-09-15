const app = new PIXI.Application(800, 600, {backgroundColor : 0x119911});
$('#stage').append(app.view);
$('body').on('contextmenu', '#stage', (e) => false);
app.stop();
const log = console.log;


// connect to server
const sendToServer = ((endpoint, subscribeAdress, publishAddress, connectCallback, messageCallback) => {
	
	const stompClient = Stomp.over(new SockJS(endpoint));
	
	stompClient.connect({}, (frame) => {
		stompClient.subscribe(subscribeAdress, messageCallback);
		connectCallback();
	});
	
	return (message) => {
		stompClient.send(publishAddress, {}, JSON.stringify(message));
	}
	
})('/pokerNight',
	'/user/queue/private',
	'/app/message',
	() => {
		
		// load game assets
		app.loader.add("sprites.json").load(() => {
			// start the app
			app.start();
			// send the registery Message
			sendToServer({type:'REGISTER'});
		});

	},
	(msg) => {
		updateGame(msg);
	}); 


const makeCard = (id, texture, parent, x, y) => { 
	log(PIXI.utils.TextureCache[texture]);
	const card = makeGameObject(
			id, 
			texture ? PIXI.utils.TextureCache[texture] : null,
			parent,
			x,
			y);
	
	card.anchor.set(0.5);
	
	card.onDragStart = (event) => {

		if (!card.dragging) {
			card.data = event.data;
			card.dragging = true;
//			card.displayGroup = dragLayer;
			
			card.alpha = 0.5;
			card.scale.x *= 1.1;
			card.scale.y *= 1.1;
			card.dragPoint = event.data.getLocalPosition(card);
			
			card.xInitial = card.x;
			card.yInitial = card.y;
		}
	};
	
	card.onDragMove = (event) => {
		if (card.dragging) {
			const newPosition = event.data.getLocalPosition(card.parent);
			card.x = newPosition.x - card.dragPoint.x;
			card.y = newPosition.y - card.dragPoint.y;
		}
	};

	card.onDragEnd = () => {
		
		if (card.dragging) {
			card.dragging = false;
			
			var message = {
	        		'type':'MOVE',
	        		'id':card.id,
//	        		'xInitial': card.xInitial,
//	        		'yInitial': card.yInitial,
	        		'x':card.x, 
	        		'y':card.y
	        }
	        
	        // send server wtf is going on
	        sendToServer(message);
			
//			card.displayGroup = defaultLayer;
			card.x = card.xInitial;
			card.y = card.yInitial;
			card.alpha = 1;
			card.scale.x /= 1.1;
	        card.scale.y /= 1.1;
	        
	        // set the interaction data to null
	        card.data = null;
		}
	};
	
	makeDraggable(card);
	return card;
}

const makeGameObject = (id, texture = PIXI.Texture.WHITE, parent = null,x = 0, y = 0) => {
	const sprite = new PIXI.Sprite(texture);
	sprite.id = id;
	sprite.x = x;
	sprite.y = y;
	if (parent) {
		parent.addChild(sprite);		
	}
	return sprite;
}

const makeDraggable = (target) => {
	target.interactive = true;
	target.buttonMode = true;
	target
		.on('pointerdown', target.onDragStart)
		.on('pointerup', target.onDragEnd)
		.on('pointerupoutside', target.onDragEnd)
		.on('pointermove', target.onDragMove);	
}

const updateGame= (() => {
	
	// last state (by using a closure we make sure updateGame is the only method that has access to state
	const currentState = 
		{
			"cards":[]
		};
	
	const toId = (card) => card.id;
	const findMissingCards = (newCards, oldCards) => {
		return newCards.filter(card => oldCards.map(toId).indexOf(card.id) === -1);
	};
	const findExistingCards = (newCards, oldCards) => {
		return newCards.filter(card => oldCards.map(toId).indexOf(card.id) !== -1);
	}
	
	const cards = (latestCards = []) => {

		const missingCards = findMissingCards(latestCards, currentState.cards)
			.map((card) => { 
				const { id, texture, x, y } = card;
				return makeCard(id, texture, app.stage, x, y);
			});
		
		const existingCards = findExistingCards(latestCards, currentState.cards);

	};
	
	return (message) => {
				
		// map whole stomp message to body which is the newState
		const newState = JSON.parse(message.body);

		cards(newState.cards);
		
		// finally update new State
		currentState.cards = newState.cards;
		
		log(currentState);
	};
	
	
})();







