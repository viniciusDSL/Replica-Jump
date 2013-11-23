package com.vinicius.dsl.replicajump.sprite;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.vinicius.dsl.replicajump.manager.GameManager;
import com.vinicius.dsl.replicajump.manager.ResourceManager;
import com.vinicius.dsl.replicajump.manager.SceneManager;
import com.vinicius.dsl.replicajump.util.Constants;

public class Enemy1 extends Sprite{

	public Enemy1(float pX, float pY,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, ResourceManager.getInstance().ENEMY_1, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		this.setTag(0);
	}
    
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stub
		
		if(!this.isDisposed() && this.collidesWith(GameManager.getInstance().player) && this.getTag()==0){
			this.setTag(1);
			GameManager.getInstance().player.die();
		}
		
		if(this.getX()<GameManager.getInstance().my_camera.getCenterX()-500){
			 ResourceManager.getInstance().my_engine.runOnUpdateThread(new Runnable() {

	                @Override
	                public void run() {
	                 
	                 
	                    setIgnoreUpdate(true);
	                    Enemy1.this.clearUpdateHandlers();
	                    Enemy1.this.detachSelf();
	                    Enemy1.this.dispose();
	                   
	                }
	            });
		}
		  if( SceneManager.getInstance().actual_scene==Constants.SCENE_MAIN_MENU && this.getTag()==0){
			  Enemy1.this.clearUpdateHandlers();
              Enemy1.this.detachSelf();
              Enemy1.this.dispose();
          }  
		
		
		
		super.onManagedUpdate(pSecondsElapsed);
	}
	
	
}
