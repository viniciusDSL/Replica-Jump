package com.vinicius.dsl.replicajump.sprite;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.vinicius.dsl.replicajump.manager.GameManager;
import com.vinicius.dsl.replicajump.manager.ResourceManager;
import com.vinicius.dsl.replicajump.util.Constants;
import com.vinicius.dsl.replicajump.util.Preferences;

public class AndroidSprite extends AnimatedSprite{

	public Body body_android;
	public boolean is_dead = false;
	public boolean is_jumping = false;
	public boolean game_over=false;
	public AnimatedSprite explosion;
	public boolean level_clear = false;
	
	
	
	
	public AndroidSprite(float pX, float pY, 
			VertexBufferObjectManager pTiledSpriteVertexBufferObject) {
		super(pX, pY, ResourceManager.getInstance().ANDROID,
				pTiledSpriteVertexBufferObject);
		explosion = new AnimatedSprite(0,600,ResourceManager.getInstance().EXPLOSION,pTiledSpriteVertexBufferObject);
		explosion.setColor(0f,1f,0f);
		// TODO Auto-generated constructor stub
	}
	
	public void createBody(final BoundCamera camera, PhysicsWorld physicsWorld)
	{		
		body_android = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 0, 0));

		body_android.setUserData("player");
		body_android.setFixedRotation(true);
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body_android, true, false)
		{
			@Override
	        public void onUpdate(float pSecondsElapsed)
	        {
				super.onUpdate(pSecondsElapsed);
				camera.onUpdate(0.1f);
				
				if(!level_clear){
					
				
				if (!is_dead)
				{	
					body_android.setLinearVelocity(new Vector2(Constants.PLAYER_VELOCITY_X, body_android.getLinearVelocity().y)); 
				}else{
					body_android.setLinearVelocity(new Vector2(0, 0));
				}
				if(!is_dead && is_jumping){
					body_android.setLinearVelocity(new Vector2(body_android.getLinearVelocity().x, Constants.PLAYER_VELOCITY_Y));
				}
				if(AndroidSprite.this.getX()>GameManager.getInstance().my_camera.getCenterX()+500){
					level_clear = true;
				}
				
				}
	        }
		});
	}
	
	public void destroy(){
		 ResourceManager.getInstance().my_engine.runOnUpdateThread(new Runnable() {
            @Override
            public void run() {
                setIgnoreUpdate(true);
                AndroidSprite.this.clearUpdateHandlers();
                AndroidSprite.this.detachSelf();
                AndroidSprite.this.dispose();
               
            }
        });
	}
	
	public void jump(){
		is_jumping = true;
	}
	
	public void stop(){
		is_jumping = false;
	}
	
	public void run(){
		
	}
	
	public void die(){
		 ResourceManager.getInstance().my_engine.runOnUpdateThread(new Runnable() {

             @Override
             public void run() {
              setIgnoreUpdate(true);
                AndroidSprite.this.clearUpdateHandlers();
                AndroidSprite.this.detachSelf();
             }
         });
		is_dead=true;
		int best = Preferences.getInt("BEST", ResourceManager.getInstance().my_context);
		if(best<GameManager.getInstance().score_value){
			Preferences.putInt("BEST", GameManager.getInstance().score_value,ResourceManager.getInstance().my_context );
		}
		explosion.setPosition(this.getX(), this.getY());
		explosion.animate(new long[] { 100, 100, 100,100 },0,3,false,new IAnimationListener() {
			
			@Override
			public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
					int pInitialLoopCount) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
					int pRemainingLoopCount, int pInitialLoopCount) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
					int pOldFrameIndex, int pNewFrameIndex) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
				// TODO Auto-generated method stub
				
			       	game_over=true;
			       	AndroidSprite.this.explosion.stopAnimation();
			        ResourceManager.getInstance().my_engine.runOnUpdateThread(new Runnable() {

			             @Override
			             public void run() {
			              setIgnoreUpdate(true);
			                AndroidSprite.this.explosion.clearUpdateHandlers();
			                AndroidSprite.this.explosion.detachSelf();
			                AndroidSprite.this.explosion.dispose();
			             }
			         });
			}
		});
	}
	
	public void star(){
		
	}
	

}
