package com.vinicius.dsl.replicajump;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.util.Constants;

import com.vinicius.dsl.replicajump.manager.GameManager;
import com.vinicius.dsl.replicajump.manager.SceneManager;

public class CustomCamera extends BoundCamera{

	public CustomCamera(float pX, float pY, float pWidth, float pHeight) {
		super(pX, pY, pWidth, pHeight);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void updateChaseEntity() {
		// TODO Auto-generated method stub
		if (GameManager.getInstance().player != null && SceneManager.getInstance().actual_scene == com.vinicius.dsl.replicajump.util.Constants.SCENE_LEVEL) {
			final float[] centerCoordinates = GameManager.getInstance().player.getSceneCenterCoordinates();
			this.setCenter(centerCoordinates[Constants.VERTEX_INDEX_X]+200, centerCoordinates[Constants.VERTEX_INDEX_Y]);
		}
	}
	

}
