package com.vinicius.dsl.replicajump;

/*
* Replica Jump 
* Android Game
* This program is free software: you can redistribute it and/or modify
*  it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
*(at your option) any later version.
*   This program is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*   You should have received a copy of the GNU General Public License
*   along with this program.  If not, see <http://www.gnu.org/licenses/>.
*   Author:Vinicius Da Silva Limachi   dasilvavinic7@gmail.com
*/


import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.modifier.IModifier;

import android.opengl.GLES20;
import android.view.KeyEvent;

import com.vinicius.dsl.replicajump.manager.GameManager;
import com.vinicius.dsl.replicajump.manager.ResourceManager;
import com.vinicius.dsl.replicajump.manager.SceneManager;
import com.vinicius.dsl.replicajump.scene.LevelScene;
import com.vinicius.dsl.replicajump.scene.MainMenuScene;
import com.vinicius.dsl.replicajump.util.Constants;

public class ReplicaJumpActivity extends BaseGameActivity {

	private Sprite sprite_splash;
	private TextureRegion texture_splash;
	private BitmapTextureAtlas map;
	private Scene scene_splash;
	
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		CustomCamera camera_game = new CustomCamera(0,0,Constants.CAMARA_WIDTH,Constants.CAMARA_HEIGHT);
		GameManager.getInstance().my_camera = camera_game;
		EngineOptions eo = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), camera_game);
		eo.getAudioOptions().setNeedsMusic(true);
		eo.getAudioOptions().setNeedsSound(true);
		return eo;
	}

	@Override
	public void onCreateResources(
			
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws IOException {
		// TODO Auto-generated method stub
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("graphics/");
		map = new BitmapTextureAtlas(mEngine.getTextureManager(), 800,480,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		texture_splash = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(map, getApplicationContext(), "splash.png", 0, 0);
		map.load();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
		
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws IOException {
		// TODO Auto-generated method stub
		scene_splash = new Scene();
		sprite_splash = new Sprite(400, 240, texture_splash, getVertexBufferObjectManager()){
			 @Override
			    protected void preDraw(final GLState pGLState, final Camera pCamera)
			    {
			        super.preDraw(pGLState, pCamera);
			        pGLState.enableDither();
			    }
		};
		sprite_splash.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		sprite_splash.setAlpha(0);
		scene_splash.attachChild(sprite_splash);
		ResourceManager.getInstance().sendParameters(getEngine(), getApplicationContext());
		ResourceManager.getInstance().my_activity = this;
		
		sprite_splash.registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(1f),
				new AlphaModifier(1f, 0f, 1f),
				new DelayModifier(1f),
				new AlphaModifier(1f, 1f, 0f, new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
						// TODO Auto-generated method stub
						ResourceManager.getInstance().LoadGraphicsMainMenu(1);
					}
				})
				));
		pOnCreateSceneCallback.onCreateSceneFinished(scene_splash);
	}

	
	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback)
			throws IOException {
		// TODO Auto-generated method stub
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		if(SceneManager.getInstance().actual_scene==Constants.SCENE_MAIN_MENU){
			if(((MainMenuScene)SceneManager.getInstance().getScene()).alphaInfo()){
				ResourceManager.reset();
				GameManager.reset();
				SceneManager.reset();
				super.onBackPressed();		
			}	
		}
		if(SceneManager.getInstance().actual_scene==Constants.SCENE_LEVEL){
			((LevelScene)SceneManager.getInstance().getScene()).BackPress();
		}
	
		
		
		
	}
	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
		if(SceneManager.getInstance().actual_scene==Constants.SCENE_LEVEL){
			((LevelScene)SceneManager.getInstance().getScene()).onKeyDown(pKeyCode, pEvent);
		}else{
			return super.onKeyDown(pKeyCode, pEvent);
		}
		return super.onKeyDown(pKeyCode, pEvent);
	}

	
	@Override
	public void onResumeGame() {
		super.onResumeGame();
        
		
	}

	@Override
	public void onPauseGame() {
		super.onPauseGame();
		if(SceneManager.getInstance().actual_scene==Constants.SCENE_LEVEL){
			((LevelScene)SceneManager.getInstance().getScene()).OnPause();
		}
		 
	}
}
