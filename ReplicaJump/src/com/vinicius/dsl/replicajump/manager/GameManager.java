package com.vinicius.dsl.replicajump.manager;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.entity.text.Text;

import com.vinicius.dsl.replicajump.sprite.AndroidSprite;

public class GameManager {
	private static GameManager INSTANCE;
	public AndroidSprite player;
	public BoundCamera my_camera;
	public int score_value = 0;
	public Text text_score;
	
	public synchronized static GameManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new GameManager();
		}
		return INSTANCE;
	}
	public static void reset() {
		// TODO Auto-generated method stub
		INSTANCE = null;
	}
}
