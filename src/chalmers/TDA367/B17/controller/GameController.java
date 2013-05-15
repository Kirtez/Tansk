package chalmers.TDA367.B17.controller;

import java.awt.*;

import chalmers.TDA367.B17.animations.AnimationHandler;
import chalmers.TDA367.B17.model.GameConditions;
import chalmers.TDA367.B17.model.World;
import chalmers.TDA367.B17.sound.SoundHandler;

public class GameController {

	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 768;
	public static final String DATA_FOLDER = "data";
	
	private static GameController instance;
	private World world;
	private ImageHandler imgHandler;
	private SoundHandler soundHandler;
	private AnimationHandler animationHandler;
	
	private GameConditions gameConditions;

	private Point mouseCoordinates;

	private GameController() {
		mouseCoordinates = new Point();
		imgHandler = new ImageHandler();
		imgHandler.loadAllImages(DATA_FOLDER);
		soundHandler = new SoundHandler();
		soundHandler.loadEverySound(DATA_FOLDER);
		animationHandler = new AnimationHandler();
	}

	public static enum RenderLayer{
		FIRST, SECOND, THIRD, FOURTH
	}
	
	public static GameController getInstance(){
		if(instance==null)
			instance = new GameController();
		
		return instance;
	}
	
	public void newGame(int width, int height, int scoreLimit, int rounds, 
			int playerLives, int spawnTime, int roundTime, int gameTime){
		world = new World(new Dimension(width, height));
		world.init();
		gameConditions = new GameConditions();
		gameConditions.init(scoreLimit, rounds, 
				playerLives, spawnTime, roundTime, gameTime);
	}
	
	public World getWorld(){
		return world;
	}
	
	public ImageHandler getImageHandler(){
		return imgHandler;
	}
	
	public Point getMouseCoordinates(){
		return mouseCoordinates;
	}
	
	public void setMouseCoordinates(int x, int y){
		mouseCoordinates.setLocation(x, y);
	}
	
	public GameConditions getGameConditions() {
		return gameConditions;
	}

	public void setGameConditions(GameConditions gameConditions) {
		this.gameConditions = gameConditions;
	}
	
	public SoundHandler getSoundHandler(){
		return soundHandler;
	}
	
	public AnimationHandler getAnimationHandler() {
		return animationHandler;
	}
}
