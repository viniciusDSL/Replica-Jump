package com.vinicius.dsl.replicajump.manager;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;
import com.vinicius.dsl.replicajump.scene.LevelScene;
import com.vinicius.dsl.replicajump.scene.MainMenuScene;
import com.vinicius.dsl.replicajump.util.Constants;

public class SceneManager {
private static SceneManager INSTANCE;
	 
    private MainMenuScene scene_main_menu;
    private LevelScene scene_level;
    public int actual_scene = 0;
    public HUD my_hud;
    
    public SceneManager(){
    	my_hud = new HUD();
    }
    
    public Scene getScene(){
    	switch (actual_scene) {
		case Constants.SCENE_MAIN_MENU:
			
			return scene_main_menu;

		case Constants.SCENE_LEVEL:
			
			return scene_level;
			
		}
    	return null;
    }

	public synchronized static SceneManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SceneManager();
		}
		return INSTANCE;
	}
	public static void reset() {
		// TODO Auto-generated method stub
		INSTANCE = null;
	}
	
	public void changeScene(int scene_id){
		GameManager.getInstance().my_camera.setCenter(Constants.CAMARA_WIDTH/2, Constants.CAMARA_HEIGHT/2);
		GameManager.getInstance().my_camera.setChaseEntity(null);
		
		switch(scene_id){
		
		case Constants.SCENE_MAIN_MENU:
			ResourceManager.getInstance().my_engine.getCamera().setHUD(null);
		     scene_main_menu = new MainMenuScene(ResourceManager.getInstance().my_engine.getVertexBufferObjectManager());
		     ResourceManager.getInstance().my_engine.setScene(scene_main_menu.LoadScene());
			break;
		case Constants.SCENE_LEVEL:
			if(actual_scene==scene_id){
				ResourceManager.getInstance().my_engine.getCamera().setHUD(null);
				my_hud = new HUD();
				ResourceManager.getInstance().my_engine.getCamera().setHUD(my_hud);
				scene_level = new LevelScene(ResourceManager.getInstance().my_engine.getVertexBufferObjectManager());
			    ResourceManager.getInstance().my_engine.setScene(scene_level.LoadScene());
			}else{
			ResourceManager.getInstance().my_engine.getCamera().setHUD(my_hud);
			scene_level = new LevelScene(ResourceManager.getInstance().my_engine.getVertexBufferObjectManager());
		    ResourceManager.getInstance().my_engine.setScene(scene_level.LoadScene());
			}
			break;
		}
		
		if(actual_scene!=scene_id){
			switch (actual_scene) {
			case Constants.SCENE_MAIN_MENU:
				scene_main_menu.CleanScene();
				scene_main_menu.dispose();
				break;

			case Constants.SCENE_LEVEL:
				scene_level.CleanScene();
				scene_level.dispose();
				break;
			}	
		}
		
		actual_scene = scene_id;
		
		
		
	}
	
	
	
}
