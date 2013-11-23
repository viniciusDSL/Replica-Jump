package com.vinicius.dsl.replicajump.scene;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.opengl.GLES20;
import android.util.Log;

import com.vinicius.dsl.replicajump.manager.ResourceManager;
import com.vinicius.dsl.replicajump.util.BasicScene;
import com.vinicius.dsl.replicajump.util.Constants;
import com.vinicius.dsl.replicajump.util.Preferences;

public class MainMenuScene extends Scene implements BasicScene{

	private VertexBufferObjectManager my_buffer;
	private Sprite sprite_background;
	private Sprite sprite_title;
	private Sprite sprite_info;
	private AnimatedSprite asprite_play;
	private AnimatedSprite asprite_info;
	private Text text_best_score;
	
	public MainMenuScene(VertexBufferObjectManager vertexBufferObjectManager) {
		// TODO Auto-generated constructor stub
		my_buffer = vertexBufferObjectManager;
	}
	
	public boolean alphaInfo(){
		if(sprite_info.getAlpha()==0){
			return true;
		}else{
			sprite_info.registerEntityModifier(new AlphaModifier(1f, 1f, 0f));
			MainMenuScene.this.unregisterTouchArea(sprite_info);
			return false;
		}
	}

	@Override
	public Scene LoadScene() {
		// TODO Auto-generated method stub
		Log.d("load", "scene");
		sprite_background = new Sprite(400, 240, ResourceManager.getInstance().BACKGROUND, my_buffer);
		this.attachChild(sprite_background);
		sprite_info = new Sprite(400, 240, ResourceManager.getInstance().INFO, my_buffer){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				if(pSceneTouchEvent.isActionUp()){
					sprite_info.registerEntityModifier(new AlphaModifier(1f, 1f, 0f));
					MainMenuScene.this.unregisterTouchArea(this);
				}
				return true;
			}
		};
		
		sprite_info.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		sprite_info.setAlpha(0);
		sprite_title = new Sprite(400,300,ResourceManager.getInstance().TITLE,my_buffer);
		this.attachChild(sprite_title);
		asprite_play = new AnimatedSprite(400,80,ResourceManager.getInstance().BUTTON_PLAY,my_buffer){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				if(sprite_info.getAlpha()==0){
				if(pSceneTouchEvent.isActionUp()){
					this.setCurrentTileIndex(0);
					ResourceManager.getInstance().LoadGraphicsMainMenu(Constants.SCENE_LEVEL);
				}
				if(pSceneTouchEvent.isActionDown()){
					this.setCurrentTileIndex(1);
				}
				}
				
				return true;
			}
		};
		this.attachChild(asprite_play);
		this.registerTouchArea(asprite_play);
		asprite_info = new AnimatedSprite(700,80,ResourceManager.getInstance().BUTTON_INFO,my_buffer){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				if(sprite_info.getAlpha()==0){
				if(pSceneTouchEvent.isActionUp()){
					this.setCurrentTileIndex(0);
					
						sprite_info.registerEntityModifier(new AlphaModifier(1f, 0f, 1f));	
						MainMenuScene.this.registerTouchArea(sprite_info);
					
				}
				if(pSceneTouchEvent.isActionDown()){
					this.setCurrentTileIndex(1);
				}
				}
				return true;
			}
		};
		
		int best_score = Preferences.getInt("BEST", ResourceManager.getInstance().my_context);
		text_best_score = new Text(400,150,ResourceManager.getInstance().ANDROID_FONT,"Best Score: "+best_score,my_buffer);
		this.attachChild(text_best_score);
		
		
		this.attachChild(asprite_info);
		this.registerTouchArea(asprite_info);
		this.attachChild(sprite_info);
		this.setTouchAreaBindingOnActionDownEnabled(true);
		
		return this;
	}

	@Override
	public void CleanScene() {
		// TODO Auto-generated method stub
		sprite_background.detachSelf();
		sprite_background.dispose();
		sprite_title.detachSelf();
		sprite_title.dispose();
		sprite_info.detachSelf();
		sprite_info.dispose();
		asprite_play.detachSelf();
		asprite_play.dispose();
		asprite_info.detachSelf();
		asprite_info.dispose();
	}

	@Override
	public void BackPress() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnPause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnResume() {
		// TODO Auto-generated method stub
		
	}

}
