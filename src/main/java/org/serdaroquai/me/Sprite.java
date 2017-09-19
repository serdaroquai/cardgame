package org.serdaroquai.me;

public class Sprite {

	private String id;
	private String texture;
	private int x;
	private int y;
	
	public Sprite() {};
	
	public Sprite(String id, String texture, int x, int y) {
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
	
}
