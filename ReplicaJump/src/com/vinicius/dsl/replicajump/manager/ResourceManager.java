package com.vinicius.dsl.replicajump.manager;

import java.io.IOException;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.debug.Debug;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

import com.vinicius.dsl.replicajump.util.Constants;

public class ResourceManager {
private static ResourceManager INSTANCE;
        
    public Engine my_engine;
    public Context my_context;
    public Activity my_activity;

	private BitmapTextureAtlas map;
    
	public TextureRegion BACKGROUND;
	public TextureRegion TITLE=null;
	public TextureRegion ENEMY_1=null;
	public TextureRegion ENEMY_2;
	public TextureRegion ENEMY_3;
	public TextureRegion ENEMY_LARGE;
	public TextureRegion COIN_YELLOW;
	public TextureRegion COIN_GREEN;
	public TextureRegion INFO;
	
	public TextureRegion CONTINUE_BUTTON;
	public TextureRegion RESET_BUTTON;
	public TextureRegion QUIT_BUTTON;
	public TextureRegion LEVEL_CLEAR;
	
	
	public TiledTextureRegion BUTTON_PLAY;
	public TiledTextureRegion BUTTON_INFO;
	public TiledTextureRegion ANDROID;
	public TiledTextureRegion EXPLOSION;
	
	public Font ANDROID_FONT;
	public Sound SOUND_COIN;
	
	public TextureOptions textureOption = TextureOptions.NEAREST_PREMULTIPLYALPHA;
    
	public synchronized static ResourceManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ResourceManager();
		}
		return INSTANCE;
	}
	
	public void sendParameters(Engine mEngine,Context mContext){
		my_engine = mEngine;
		my_context = mContext;
		
	}
	
	public void loadGraphics(){
		//load the font
		FontFactory.setAssetBasePath("font/");
		ANDROID_FONT = FontFactory.createFromAsset(my_engine.getFontManager(), my_engine.getTextureManager(), 256, 256, TextureOptions.BILINEAR, my_context.getAssets(), "droid.ttf", 20, true, Color.WHITE);
		ANDROID_FONT.load();
		
		//load the textures of first screen
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("graphics/");
		map = new BitmapTextureAtlas(my_engine.getTextureManager(), 800, 480,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BACKGROUND = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(map, my_context, "background.png", 0, 0);
		map.load();
		
		map = new BitmapTextureAtlas(my_engine.getTextureManager(), 773, 268,
				textureOption);
		TITLE = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(map, my_context, "title.png", 0, 0);
		map.load();
		
		map = new BitmapTextureAtlas(my_engine.getTextureManager(), 800, 480,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		INFO = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(map, my_context, "info.png", 0, 0);
		map.load();
		
		map = new BitmapTextureAtlas(my_engine.getTextureManager(), 642, 126,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BUTTON_PLAY = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(map, my_context, "button_play.png", 0, 0,2,1);
		map.load();
		
		map = new BitmapTextureAtlas(my_engine.getTextureManager(), 207, 101,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BUTTON_INFO = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(map, my_context, "button_info.png", 0, 0,2,1);
		map.load();
		
		map = new BitmapTextureAtlas(my_engine.getTextureManager(), 95, 60,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		ANDROID = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(map, my_context, "sprite_android.png", 0, 0,2,1);
		map.load();	
	}
	
	public void loadGraphicsLevel(){
		//load the textures of level screen
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("graphics/");
		map = new BitmapTextureAtlas(my_engine.getTextureManager(), 33, 33,
				textureOption);
		ENEMY_1 = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(map, my_context, "enemy_1.png", 0, 0);
		map.load();
		
		map = new BitmapTextureAtlas(my_engine.getTextureManager(), 33, 33,
				textureOption);
		ENEMY_2 = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(map, my_context, "enemy_2.png", 0, 0);
		map.load();
		
		map = new BitmapTextureAtlas(my_engine.getTextureManager(), 33, 33,
				textureOption);
		ENEMY_3 = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(map, my_context, "enemy_3.png", 0, 0);
		map.load();
		
		map = new BitmapTextureAtlas(my_engine.getTextureManager(), 100, 15,
				textureOption);
		ENEMY_LARGE = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(map, my_context, "enemy_large.png", 0, 0);
		map.load();
		
		map = new BitmapTextureAtlas(my_engine.getTextureManager(), 33, 33,
				textureOption);
		COIN_GREEN = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(map, my_context, "green_coin.png", 0, 0);
		map.load();
		
		map = new BitmapTextureAtlas(my_engine.getTextureManager(), 33, 33,
				textureOption);
		COIN_YELLOW = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(map, my_context, "yellow_coin.png", 0, 0);
		map.load();
		
		map = new BitmapTextureAtlas(my_engine.getTextureManager(), 256, 64,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		EXPLOSION = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(map, my_context, "explosion.png", 0 , 0,4,1);
		map.load();
		
		map = new BitmapTextureAtlas(my_engine.getTextureManager(), 607, 126,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		CONTINUE_BUTTON = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(map, my_context, "continue_button.png", 0, 0);
		map.load();
		
		map = new BitmapTextureAtlas(my_engine.getTextureManager(), 607, 126,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		RESET_BUTTON = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(map, my_context, "reset_button.png", 0, 0);
		map.load();
		
		map = new BitmapTextureAtlas(my_engine.getTextureManager(), 607, 126,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		QUIT_BUTTON = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(map, my_context, "quit_button.png", 0, 0);
		map.load();
		
		map = new BitmapTextureAtlas(my_engine.getTextureManager(), 524, 62,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		LEVEL_CLEAR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(map, my_context, "level_clear.png", 0, 0);
		map.load();
		
		
		SoundFactory.setAssetBasePath("sound/");
		try {
			SOUND_COIN = SoundFactory.createSoundFromAsset(my_engine.getSoundManager(), my_context, "coin.ogg");
		} catch (final IOException e) {
			Debug.e(e);
		}
		
	}
	

	public static void reset() {
		// TODO Auto-generated method stub
		INSTANCE = null;
	}
	
	public void LoadGraphicsMainMenu(final int type){
		my_activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Load my_load = new Load();
				my_load.setType(type);
				my_load.execute();	
			}
		});
		
	}
	
	public  class Load extends AsyncTask<String, String, String> {
		int type;
		
	    public void setType(int m_type){
	    	type = m_type;
	    } 
		
	   	@Override
	   	protected void onCancelled() {
	   		// TODO Auto-generated method stub
	   
	   		super.onCancelled();
	   
	   	}
	   	
	       @Override
	       protected void onPreExecute() {
	    	   super.onPreExecute();  
	       }
	       @Override
	       protected String doInBackground(String... aurl) {
	    	   switch (type) {
			case Constants.SCENE_MAIN_MENU:
				if(TITLE==null){
				loadGraphics();
				}
				break;

			case Constants.SCENE_LEVEL:
				if(ENEMY_1==null){
				loadGraphicsLevel();
				}
				break;
			}
              
	    	 
	           return null;
	       }
	       @Override
	       protected void onPostExecute(String unused) {
	           super.onPostExecute(unused);
	           switch (type) {
				case Constants.SCENE_MAIN_MENU:
					 SceneManager.getInstance().changeScene(Constants.SCENE_MAIN_MENU);
					break;

				case Constants.SCENE_LEVEL:
					 SceneManager.getInstance().changeScene(Constants.SCENE_LEVEL);
					break;
				}
	          
	       }
}
}
