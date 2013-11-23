package com.vinicius.dsl.replicajump.sprite;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.vinicius.dsl.replicajump.manager.GameManager;
import com.vinicius.dsl.replicajump.manager.ResourceManager;
import com.vinicius.dsl.replicajump.manager.SceneManager;
import com.vinicius.dsl.replicajump.util.Constants;

public class CoinYellow extends Sprite {
	public CoinYellow(float pX, float pY,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, ResourceManager.getInstance().COIN_YELLOW.deepCopy(), pVertexBufferObjectManager);
		this.setTag(0);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stub
		if(!this.isDisposed()){
		if(GameManager.getInstance().player.collidesWith(this) && this.getTag()==0){
			this.setTag(1);
			ResourceManager.getInstance().SOUND_COIN.play();
			 ResourceManager.getInstance().my_engine.runOnUpdateThread(new Runnable() {

	                @Override
	                public void run() {
	                 setIgnoreUpdate(true);
	                 GameManager.getInstance().score_value+=100;
	                 GameManager.getInstance().text_score.setText("Score: "+GameManager.getInstance().score_value);
	                    CoinYellow.this.clearUpdateHandlers();
						CoinYellow.this.detachSelf();
						CoinYellow.this.dispose();
						
	                }
	            });
			
		}
		 if( SceneManager.getInstance().actual_scene==Constants.SCENE_MAIN_MENU && this.getTag()==0){
       	  CoinYellow.this.clearUpdateHandlers();
				CoinYellow.this.detachSelf();
				CoinYellow.this.dispose();
         }
		 if(this.getX()<GameManager.getInstance().my_camera.getCenterX()-500 && this.getTag()==0){
			 ResourceManager.getInstance().my_engine.runOnUpdateThread(new Runnable() {

	                @Override
	                public void run() {
	                 
	                 
	                    setIgnoreUpdate(true);
	                    CoinYellow.this.clearUpdateHandlers();
						CoinYellow.this.detachSelf();
						CoinYellow.this.dispose();
	                   
	                }
	            });
		}
		
		}
		
		
		
		super.onManagedUpdate(pSecondsElapsed);
	}
}
