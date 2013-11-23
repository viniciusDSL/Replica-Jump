package com.vinicius.dsl.replicajump.level;

public class LevelObject {
     
	private float posX,posY;
	private int type;
	
	public LevelObject(float px,float py,int tp){
		posX = px;
		posY = py;
		type = tp;
	}
	
	public float getX(){
		return posX;
	}
	
	public float getY(){
		return posY;
	}
	
	public int getType(){
		return type;
	}
	
}
