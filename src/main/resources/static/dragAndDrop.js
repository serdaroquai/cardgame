
function makeDraggable(target){  
	target.interactive = true;
	target.buttonMode = true;
	target.anchor.set(0.5);
	target.on('mousedown', onDragStart).on('mouseup', onDragEnd).on('mouseupoutside', onDragEnd).on('mousemove', onDragMove).on('rightclick', onRightClick);	
}

function onRightClick(event) {

	var message = {
			'id':this.id, 
			'rightClick':true
			}
	
	// send server wtf is going on
	sendAction(JSON.stringify(message));
	
}

function onDragStart(event) {
	// store a reference to the data
	// the reason for this is because of multitouch
	// we want to track the movement of this particular touch
	if (!this.dragging) {
		this.data = event.data;
		this.dragging = true;
		this.displayGroup = dragLayer;
		
		this.alpha = 0.5;
		this.scale.x *= 1.1;
		this.scale.y *= 1.1;
		this.dragPoint = event.data.getLocalPosition(this);
		
		this.xInitial = this.x;
		this.yInitial = this.y;
	}
}

function onDragMove() {
	if (this.dragging) {
		var newPosition = this.data.getLocalPosition(this.parent);
		this.x = newPosition.x - this.dragPoint.x;
		this.y = newPosition.y - this.dragPoint.y;
	}
}

function onDragEnd() {
	
	if (this.dragging) {
		this.dragging = false;
		this.displayGroup = defaultLayer;
		
		this.alpha = 1;
		this.scale.x /= 1.1;
        this.scale.y /= 1.1;
        
        var message = {
        		'id':this.id,
        		'xInitial': this.xInitial,
        		'yInitial': this.yInitial,
        		'x':this.x, 
        		'y':this.y
        }
        
        // send server wtf is going on
        sendAction(JSON.stringify(message));
        
        // set the interaction data to null
        this.data = null;
	}
}


