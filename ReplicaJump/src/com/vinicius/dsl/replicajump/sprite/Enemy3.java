package com.vinicius.dsl.replicajump.sprite;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.vinicius.dsl.replicajump.manager.GameManager;
import com.vinicius.dsl.replicajump.manager.ResourceManager;
import com.vinicius.dsl.replicajump.manager.SceneManager;
import com.vinicius.dsl.replicajump.util.Constants;

public class Enemy3 extends Sprite {
	public Enemy3(float pX, float pY,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, ResourceManager.getInstance().ENEMY_3, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		this.setTag(0);
		this.registerEntityModifier(new MoveModifier(3f, this.getX(), this.getY(), this.getX(),480-this.getHeight()/2));
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
	                    Enemy3.this.clearUpdateHandlers();
	                    Enemy3.this.detachSelf();
	                    Enemy3.this.dispose();
	                   

	                }
	            });
		}
		if( SceneManager.getInstance().actual_scene==Constants.SCENE_MAIN_MENU && this.getTag()==0){
			  Enemy3.this.clearUpdateHandlers();
            Enemy3.this.detachSelf();
            Enemy3.this.dispose();
        }  
		super.onManagedUpdate(pSecondsElapsed);
	}
}
