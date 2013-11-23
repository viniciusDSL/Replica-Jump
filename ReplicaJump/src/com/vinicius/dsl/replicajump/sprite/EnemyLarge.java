package com.vinicius.dsl.replicajump.sprite;

import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.vinicius.dsl.replicajump.manager.GameManager;
import com.vinicius.dsl.replicajump.manager.ResourceManager;
import com.vinicius.dsl.replicajump.manager.SceneManager;
import com.vinicius.dsl.replicajump.util.Constants;

public class EnemyLarge extends Sprite{
	public EnemyLarge(float pX, float pY,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, ResourceManager.getInstance().ENEMY_LARGE, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		this.setTag(0);
		this.registerEntityModifier(new RotationModifier(7f, 0, 360));
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stubthis.registerEntityModifier(new MoveModifier(4f, this.getX(), this.getY(), this.getX()-200, this.getY()));
		if(!this.isDisposed() && this.collidesWith(GameManager.getInstance().player) && this.getTag()==0){
			this.setTag(1);
			GameManager.getInstance().player.die();
		}
		if(this.getX()<GameManager.getInstance().my_camera.getCenterX()-500){
			 ResourceManager.getInstance().my_engine.runOnUpdateThread(new Runnable() {

	                @Override
	                public void run() {
	                 setIgnoreUpdate(true);
	                    EnemyLarge.this.clearUpdateHandlers();
	                    EnemyLarge.this.detachSelf();
	                    EnemyLarge.this.dispose();
	                }
	            });
		}
		if( SceneManager.getInstance().actual_scene==Constants.SCENE_MAIN_MENU && this.getTag()==0){
			  EnemyLarge.this.clearUpdateHandlers();
          EnemyLarge.this.detachSelf();
          EnemyLarge.this.dispose();
      } 
		super.onManagedUpdate(pSecondsElapsed);
	}
}
