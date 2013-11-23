package com.vinicius.dsl.replicajump.scene;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.animator.AlphaMenuSceneAnimator;
import org.andengine.entity.scene.menu.animator.SlideMenuSceneAnimator;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.align.VerticalAlign;
import org.andengine.util.adt.spatial.Direction;
import org.andengine.util.modifier.ease.EaseBounceOut;

import android.view.KeyEvent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.vinicius.dsl.replicajump.level.Level1;
import com.vinicius.dsl.replicajump.level.LevelObject;
import com.vinicius.dsl.replicajump.manager.GameManager;
import com.vinicius.dsl.replicajump.manager.ResourceManager;
import com.vinicius.dsl.replicajump.manager.SceneManager;
import com.vinicius.dsl.replicajump.sprite.AndroidSprite;
import com.vinicius.dsl.replicajump.sprite.CoinGreen;
import com.vinicius.dsl.replicajump.sprite.CoinYellow;
import com.vinicius.dsl.replicajump.sprite.Enemy1;
import com.vinicius.dsl.replicajump.sprite.Enemy2;
import com.vinicius.dsl.replicajump.sprite.Enemy3;
import com.vinicius.dsl.replicajump.sprite.EnemyLarge;
import com.vinicius.dsl.replicajump.util.BasicScene;
import com.vinicius.dsl.replicajump.util.Constants;

public class LevelScene extends Scene implements BasicScene,
		IOnSceneTouchListener, IOnMenuItemClickListener {
	private PhysicsWorld my_physics_world;
	private VertexBufferObjectManager my_buffer;
	private AutoParallaxBackground autoParallaxBackground;
	private Sprite parallaxLayerBackSprite;
	private Level1 stage;
	private Sprite button_reset;
	private Sprite button_quit;
	protected static final int MENU_CONTINUE = 0;
	protected static final int MENU_RESET = MENU_CONTINUE + 1;
	protected static final int MENU_QUIT = MENU_RESET + 1;
	private MenuScene mMenuScene;
	public boolean isOver = false;
	private Sprite title_level_complete;
	private boolean level_clear = false;
	private Rectangle ground;
	private Rectangle top;

	public LevelScene(VertexBufferObjectManager buffer) {
		my_buffer = buffer;
	}

	@Override
	public Scene LoadScene() {
		createMenuScene();

		title_level_complete = new Sprite(0, 0,
				ResourceManager.getInstance().LEVEL_CLEAR, my_buffer);
		button_reset = new Sprite(0, 0,
				ResourceManager.getInstance().RESET_BUTTON, my_buffer) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub\
				if (pSceneTouchEvent.isActionDown()) {

				}
				if (pSceneTouchEvent.isActionUp()) {
					SceneManager.getInstance().changeScene(
							Constants.SCENE_LEVEL);

				}
				return true;
			}
		};
		button_quit = new Sprite(0, 0,
				ResourceManager.getInstance().QUIT_BUTTON, my_buffer) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub\
				if (pSceneTouchEvent.isActionDown()) {

				}
				if (pSceneTouchEvent.isActionUp()) {
					SceneManager.getInstance().changeScene(
							Constants.SCENE_MAIN_MENU);
				}
				return true;
			}
		};

		
		autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
		this.setBackground(autoParallaxBackground);
		parallaxLayerBackSprite = new Sprite(0, 0,
				ResourceManager.getInstance().BACKGROUND, my_buffer);
		parallaxLayerBackSprite.setOffsetCenter(0, 0);
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-10.0f,
				parallaxLayerBackSprite));
		my_physics_world = new FixedStepPhysicsWorld(60, new Vector2(0, -25),
				false);
		ground = new Rectangle(Constants.CAMARA_WIDTH * 8, 1,
				Constants.CAMARA_WIDTH * 16, 2, my_buffer);
		top = new Rectangle(Constants.CAMARA_WIDTH * 8,
				Constants.CAMARA_HEIGHT - 2, Constants.CAMARA_WIDTH * 16, 2,
				my_buffer);
		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0,
				0.01f, 0.5f);
		PhysicsFactory.createBoxBody(this.my_physics_world, ground,
				BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.my_physics_world, top,
				BodyType.StaticBody, wallFixtureDef);
		this.setOnSceneTouchListener(this);
		this.setTouchAreaBindingOnActionDownEnabled(true);
		this.attachChild(ground);
		this.attachChild(top);
		this.registerUpdateHandler(this.my_physics_world);
		GameManager.getInstance().player = new AndroidSprite(100, 70, my_buffer);
		GameManager.getInstance().player.createBody(
				GameManager.getInstance().my_camera, my_physics_world);
		GameManager.getInstance().my_camera.setChaseEntity(GameManager
				.getInstance().player);
		this.attachChild(GameManager.getInstance().player);
		this.attachChild(GameManager.getInstance().player.explosion);
		GameManager.getInstance().my_camera.setBounds(0, 0,
				Constants.CAMARA_WIDTH * 16, 480);
		GameManager.getInstance().my_camera.setBoundsEnabled(true);
		LoadLevel(1);
		this.registerUpdateHandler(new IUpdateHandler() {

			@Override
			public void reset() {
				// TODO Auto-generated method stub

			}

			LevelObject object = null;

			@Override
			public void onUpdate(float pSecondsElapsed) {
				// TODO Auto-generated method stub
				if (object == null) {
					object = stage.getNext();
				}
				if (object != null) {
					if (object.getX() <= GameManager.getInstance().my_camera
							.getCenterX() + 500) {
						addObject(object);
						object = null;
					}
				}
				if (!GameManager.getInstance().player.level_clear) {
					if (GameManager.getInstance().player.game_over && !isOver) {
						isOver = true;
						button_quit.setPosition(
								GameManager.getInstance().my_camera
										.getCenterX(), 370);
						button_reset.setPosition(
								GameManager.getInstance().my_camera
										.getCenterX(), 150);
						LevelScene.this.attachChild(button_quit);
						LevelScene.this.attachChild(button_reset);
						LevelScene.this.registerTouchArea(button_quit);
						LevelScene.this.registerTouchArea(button_reset);
					}
				} else {
					if (!level_clear) {
						level_clear = true;
						LevelScene.this.attachChild(title_level_complete);
						title_level_complete.setPosition(GameManager
								.getInstance().my_camera.getCenterX(), 370);
						button_quit.setPosition(
								GameManager.getInstance().my_camera
										.getCenterX(), 150);
						LevelScene.this.attachChild(button_quit);
						LevelScene.this.registerTouchArea(button_quit);
					}
				}

			}
		});

		GameManager.getInstance().text_score = new Text(400, 450,
				ResourceManager.getInstance().ANDROID_FONT, "Score: 0123456789", "Score: 0123456789".length(),
				my_buffer);
		GameManager.getInstance().text_score.setText("Score: 0");	
		SceneManager.getInstance().my_hud
				.attachChild(GameManager.getInstance().text_score);
		return this;
	}

	public void LoadLevel(int level_id) {
		stage = new Level1(level_id);
	}

	public void addObject(LevelObject object) {
		switch (object.getType()) {

		case 1:
			Enemy1 e1 = new Enemy1(object.getX(), object.getY(), my_buffer);
			this.attachChild(e1);
			break;
		case 2:
			Enemy2 e2 = new Enemy2(object.getX(), object.getY(), my_buffer);
			this.attachChild(e2);
			break;
		case 3:
			Enemy3 e3 = new Enemy3(object.getX(), object.getY(), my_buffer);
			this.attachChild(e3);
			break;
		case 4:
			EnemyLarge el = new EnemyLarge(object.getX(), object.getY(),
					my_buffer);
			this.attachChild(el);
			break;
		case 5:
			CoinYellow cy = new CoinYellow(object.getX(), object.getY(),
					my_buffer);
			this.attachChild(cy);
			break;
		case 6:
			CoinGreen cg = new CoinGreen(object.getX(), object.getY(),
					my_buffer);
			this.attachChild(cg);
			break;
		}

	}

	@Override
	public void CleanScene() {
		// TODO Auto-generated method stub
		ResourceManager.getInstance().my_engine
		.runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				LevelScene.this.clearTouchAreas();
				setIgnoreUpdate(true);
				GameManager.getInstance().text_score.detachSelf();
				GameManager.getInstance().score_value = 0;
				top.detachSelf();
				top.dispose();
				ground.detachSelf();
				ground.dispose();
				my_physics_world.destroyBody(GameManager.getInstance().player.body_android);
				my_physics_world.clearForces();
				my_physics_world.clearPhysicsConnectors();
				my_physics_world.reset();
				my_physics_world.dispose();
				System.gc();
			}
		});

	}

	@Override
	public void BackPress() {
		if (!level_clear && !isOver) {
			// TODO Auto-generated method stub
			if (this.hasChildScene()) {
				/* Remove the menu and reset it. */
				this.mMenuScene.back();
			} else {
				/* Attach the menu. */
				this.setChildScene(this.mMenuScene, false, true, true);
			}
		}

	}

	@Override
	public void OnPause() {
		// TODO Auto-generated method stub
		if (!this.hasChildScene()) {
			this.setChildScene(this.mMenuScene, false, true, true);
		}
	}

	@Override
	public void OnResume() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		if (pSceneTouchEvent.isActionDown() || pSceneTouchEvent.isActionMove()) {
			// JumpAndroid();
			GameManager.getInstance().player.jump();
			return true;
		}
		if (pSceneTouchEvent.isActionUp() || pSceneTouchEvent.isActionOutside()) {
			GameManager.getInstance().player.stop();
			return true;
		}

		return false;
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		// TODO Auto-generated method stub
		if (!level_clear && !isOver) {
			switch (pMenuItem.getID()) {
			case MENU_CONTINUE:
				if (!GameManager.getInstance().player.is_dead) {
					this.mMenuScene.back();
				}
				return true;
			case MENU_RESET:
				SceneManager.getInstance().changeScene(Constants.SCENE_LEVEL);
				return true;
			case MENU_QUIT:

				SceneManager.getInstance().changeScene(
						Constants.SCENE_MAIN_MENU);
				return true;
			default:
				return false;
			}
		}
		return false;

	}

	protected void createMenuScene() {
		this.mMenuScene = new MenuScene(GameManager.getInstance().my_camera,
				new AlphaMenuSceneAnimator());

		final SpriteMenuItem continueMenuItem = new SpriteMenuItem(
				MENU_CONTINUE, ResourceManager.getInstance().CONTINUE_BUTTON,
				my_buffer);
		this.mMenuScene.addMenuItem(continueMenuItem);

		final SpriteMenuItem resetMenuItem = new SpriteMenuItem(MENU_RESET,
				ResourceManager.getInstance().RESET_BUTTON, my_buffer);
		this.mMenuScene.addMenuItem(resetMenuItem);

		final SpriteMenuItem quitMenuItem = new SpriteMenuItem(MENU_QUIT,
				ResourceManager.getInstance().QUIT_BUTTON, my_buffer);
		this.mMenuScene.addMenuItem(quitMenuItem);

		final SlideMenuSceneAnimator menuSceneAnimator = new SlideMenuSceneAnimator(
				HorizontalAlign.CENTER, VerticalAlign.CENTER,
				Direction.UP_LEFT, EaseBounceOut.getInstance());
		menuSceneAnimator.setMenuItemSpacing(10);
		this.mMenuScene.setMenuSceneAnimator(menuSceneAnimator);

		this.mMenuScene.buildAnimations();

		this.mMenuScene.setBackgroundEnabled(false);

		this.mMenuScene.setOnMenuItemClickListener(this);
	}

	public void onKeyDown(int pKeyCode, KeyEvent pEvent) {
		// TODO Auto-generated method stub
		if (!level_clear && !isOver) {
			if (pKeyCode == KeyEvent.KEYCODE_MENU
					&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
				if (this.hasChildScene()) {
					/* Remove the menu and reset it. */
					this.mMenuScene.back();
				} else {
					/* Attach the menu. */
					this.setChildScene(this.mMenuScene, false, true, true);
				}
			}
		}
	}

}
