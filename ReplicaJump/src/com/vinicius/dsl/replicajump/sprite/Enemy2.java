package com.vinicius.dsl.replicajump.sprite;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.vinicius.dsl.replicajump.manager.GameManager;
import com.vinicius.dsl.replicajump.manager.ResourceManager;
import com.vinicius.dsl.replicajump.manager.SceneManager;
import com.vinicius.dsl.replicajump.util.Constants;

public class Enemy2 extends Sprite{
	public Enemy2(float pX, float pY,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, ResourceManager.getInstance().ENEMY_2, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		this.setTag(0);
		this.registerEntityModifier(new MoveModifier(3f, this.getX(), this.getY(), GameManager.getInstance().my_camera.getCenterX()-this.getWidth()/2, this.getY()));
		
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
	                    Enemy2.this.clearUpdateHandlers();
	                    Enemy2.this.detachSelf();
	                    Enemy2.this.dispose();
	                  
	                }
	            });
		}
		if( SceneManager.getInstance().actual_scene==Constants.SCENE_MAIN_MENU && this.getTag()==0){
			  Enemy2.this.clearUpdateHandlers();
            Enemy2.this.detachSelf();
            Enemy2.this.dispose();
        }  
		
		super.onManagedUpdate(pSecondsElapsed);
	}
}
