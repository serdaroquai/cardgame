const app = new PIXI.Application(800, 600, {backgroundColor : 0x119911});
$('#stage').append(app.view);
$('body').on('contextmenu', '#stage', (e) => false);
app.stop();


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
	
})('/pokerNight','/user/queue/private','/app/message', () => sendToServer({type:'REGISTER'}), (msg) => updateGame(msg));


//Aliases
const TextureCache = PIXI.utils.TextureCache

var sprites = {};
var privateSprites = {};
var texture;

// load game assets
app.loader.add("sprites.json").load(() => {
	
	// specify the displayList component, create layers for z-order
	app.stage.displayList = new PIXI.DisplayList();
	const dragLayer = new PIXI.DisplayGroup(2, false);
	const defaultLayer = new PIXI.DisplayGroup(1, (sprite) => sprite.zOrder = -sprite.y);
	
	app.start();
});


function updateGame(message) {
	
	
	
	// iterate through the existing sprites to get rid of outdated ones,
	// todo open for improvement
	
	if (message.privateMessage) {
		
		var tempPrivateSprites = {};
		for (var key in privateSprites) {
			// remove if it does not exist in message
			if (message.sprites[key] == null) {
				app.stage.removeChild(privateSprites[key]);	
			} else {
				tempPrivateSprites[key] = privateSprites[key];
			}
		}
		
		privateSprites = tempPrivateSprites;

		//now create and update the existing
		
		for (var key in message.sprites) {
			var entry = message.sprites[key];

			if (privateSprites[entry.id] == null) {
				//create sprite if it does not exist
				var gameObject = new GameObject(entry.texture,entry.id);
				makeDraggable(gameObject);
				privateSprites[entry.id] = gameObject;
				privateSprites[entry.id].position.x = entry.x;
				privateSprites[entry.id].position.y = entry.y;
				app.stage.addChild(gameObject);
			} else {
				
				if (!privateSprites[entry.id].dragging) {
					privateSprites[entry.id].texture = TextureCache[entry.texture];
					//animate to new location
					app.ticker.add(animate(privateSprites[entry.id], entry));					
				}
				
			}
		}
		
	} else {
		
		var tempSprites = {};
		for (var key in sprites) {
			// remove if it does not exist in message
			if (message.sprites[key] == null) {
				app.stage.removeChild(sprites[key]);				
			} else {
				tempSprites[key] = sprites[key];
			}
		}
		
		sprites = tempSprites;
		
		for (var key in message.sprites) {
			
			var entry = message.sprites[key];
			
			if (sprites[entry.id] == null) {
				//create sprite if it does not exist
				var gameObject = new GameObject(entry.texture,entry.id);
				makeDraggable(gameObject);
				sprites[entry.id] = gameObject;
				sprites[entry.id].position.x = entry.x;
				sprites[entry.id].position.y = entry.y;
				app.stage.addChild(gameObject);
			} else {
				
				// don't update dragging units, since you will have the last say 
				if (!sprites[entry.id].dragging) {
					sprites[entry.id].texture = TextureCache[entry.texture];
					//animate to new location
					app.ticker.add(animate(sprites[entry.id], entry));
				}

			}
			
			
		}
			
	}
	
}

//using a closures to self destroy an animation
function animate(sprite, entry) {
		
	var count = 0;
    var temp = function () {

    	count++;
		var position = sprite.position;
		
		position.x += (entry.x - position.x) * 0.2;
		position.y += (entry.y - position.y) * 0.2;
		
		//either when u arrive or after one second set to latest position and end animation
		if ((Math.abs(entry.x - position.x) < 1 && Math.abs(entry.y - position.y) < 1) 
				|| count > 60) {
			position.x = entry.x;
			position.y = entry.y;
			
			// end the animation
			app.ticker.remove(temp);
		}
    };
    return temp;
};




