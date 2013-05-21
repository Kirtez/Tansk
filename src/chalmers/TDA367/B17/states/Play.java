package chalmers.TDA367.B17.states;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.state.*;

import chalmers.TDA367.B17.MapLoader;
import chalmers.TDA367.B17.Tansk;
import chalmers.TDA367.B17.console.Console;
import chalmers.TDA367.B17.console.Console.OutputLevel;
import chalmers.TDA367.B17.controller.GameController;
import chalmers.TDA367.B17.model.*;
import chalmers.TDA367.B17.view.Lifebar;
import chalmers.TDA367.B17.view.SoundSwitch;
import chalmers.TDA367.B17.weaponPickups.SlowspeedyPickup;
import chalmers.TDA367.B17.weapons.*;

public class Play extends TanskState {
	
	public ArrayList<AbstractTurret> turrets;
	
	private ArrayList<Player> players;
	private Player playerOne;
	private Image map = null;
	private Input input;
	private SpriteSheet entSprite = null;
	private Lifebar lifebar;
	private SoundSwitch soundSwitch;
	
	private Player playerTwo;
	private Player playerThree;
	private Player playerFour;

	
	public Play(int state) {
	    super(state);
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		super.init(gc, sbg);
		
		map = new Image(Tansk.IMAGES_FOLDER + "/map.png");
		
		input = gc.getInput();
		input.addMouseListener(this);
	}	
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
			
		Console console = new Console(10, 533, 450, 192, OutputLevel.ALL);
		console.setBorder(false);
		controller.setConsole(console);
		
		lifebar = new Lifebar((Tansk.SCREEN_WIDTH/2)-100, 10);
		controller.newGame(Tansk.SCREEN_WIDTH, Tansk.SCREEN_HEIGHT, 10, 4, 1, 5000, 500000, 1500000, false);
		soundSwitch = new SoundSwitch(Tansk.SCREEN_WIDTH-40, 10);

		//Players
		playerOne = new Player("Player One");
		players = new ArrayList<Player>();
		players.add(playerOne);
		
		playerTwo = new Player("Player Two");
		players.add(playerTwo);
		
		playerThree = new Player("Player Three");
		players.add(playerThree);
		
		playerFour = new Player("Player Four");
		players.add(playerFour);
		
		for(Player player : players){
			GameController.getInstance().getGameConditions().addPlayer(player);
			player.setLives(GameController.getInstance().getGameConditions().getPlayerLives());
			player.setRespawnTime(GameController.getInstance().getGameConditions().getSpawnTime());
		}
		
		MapLoader.createEntities("");

		//Start a new round
		controller.getGameConditions().newRoundDelayTimer(3000);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		super.update(gc, sbg, delta);
		
		if(input.isKeyDown(Input.KEY_W)){
			if(playerOne.getTank() != null)
				playerOne.getTank().accelerate(delta);
		} else if (input.isKeyDown(Input.KEY_S)){
			if(playerOne.getTank() != null)
			playerOne.getTank().reverse(delta);
		} else {
			if(playerOne.getTank() != null)
			playerOne.getTank().friction(delta);
		}

		if(input.isKeyDown(Input.KEY_A) && !input.isKeyDown(Input.KEY_D)){
			if(input.isKeyDown(Input.KEY_S)){
				if(playerOne.getTank() != null)
				playerOne.getTank().turnRight(delta);
			} else {
				if(playerOne.getTank() != null)
				playerOne.getTank().turnLeft(delta);
			}
		}

		if(input.isKeyDown(Input.KEY_D) && !input.isKeyDown(Input.KEY_A)){
			if(input.isKeyDown(Input.KEY_S)){
				if(playerOne.getTank() != null)
				playerOne.getTank().turnLeft(delta);
			} else {
				if(playerOne.getTank() != null)
				playerOne.getTank().turnRight(delta);
			}
		}

		if(input.isMouseButtonDown(0)){
			if(playerOne.getTank() != null)
				playerOne.getTank().fireWeapon(delta);
			if(playerTwo.getTank() != null)
				playerTwo.getTank().fireWeapon(delta);
		}
		
		if(input.isKeyDown(Input.KEY_Q)){
			new SlowspeedyPickup(GameController.getInstance().generateID(), new Vector2f(800, 300));
		}
						
		for(Player player : players){
			if(player.getTank() != null){
				AbstractTurret turret = player.getTank().getTurret();
				turret.setRotation((float) Math.toDegrees(Math.atan2(turret.getPosition().x - input.getMouseX() + 0, turret.getPosition().y - input.getMouseY() + 0)* -1)+180);		
			}
		}
		
		if(input.isKeyDown(Input.KEY_UP)){
			float tmp = controller.getSoundHandler().getVolume();
			if(tmp + 0.05 < 1){
				tmp+=0.05;
			}else{
				tmp = 1;
			}
			controller.getSoundHandler().setVolume(tmp);
		}
		if(input.isKeyDown(Input.KEY_DOWN)){
			float tmp = controller.getSoundHandler().getVolume();
			if(tmp - 0.05 >= 0){
				tmp-=0.05f;
			}else if(tmp < 0.1f){
				tmp = 0;
			}
			controller.getSoundHandler().setVolume(tmp);
		}
		if(input.isKeyPressed(Input.KEY_S) && input.isKeyDown(Input.KEY_LCONTROL)){
			if(controller.getSoundHandler().isSoundOn()){
				soundSwitch.turnSoundOff(controller.getSoundHandler().getVolume());
			}else{
				soundSwitch.turnSoundOn();
			}
		}
		
		//Weapons
		if(playerOne.getTank() != null){
			AbstractTank playerOneTank = playerOne.getTank();
			AbstractTurret playerOneTurret = playerOneTank.getTurret();
		
			if(input.isKeyDown(Input.KEY_1)){
				if(playerOne.getTank() != null)
					playerOne.getTank().setTurret(new DefaultTurret(controller.generateID(), playerOneTurret.getPosition(), playerOneTurret.getRotation(), playerOneTank));
			}
			if(input.isKeyDown(Input.KEY_2)){
				if(playerOne.getTank() != null)
					playerOne.getTank().setTurret(new FlamethrowerTurret(controller.generateID(), playerOneTurret.getPosition(), playerOneTurret.getRotation(), playerOneTank));
			}
			if(input.isKeyDown(Input.KEY_3)){
				if(playerOne.getTank() != null)
					playerOne.getTank().setTurret(new ShotgunTurret(controller.generateID(), playerOneTurret.getPosition(), playerOneTurret.getRotation(), playerOneTank));
			}
			if(input.isKeyDown(Input.KEY_4)){
				if(playerOne.getTank() != null)
					playerOne.getTank().setTurret(new SlowspeedyTurret(controller.generateID(), playerOneTurret.getPosition(), playerOneTurret.getRotation(), playerOneTank));
			}
			if(input.isKeyDown(Input.KEY_5)){
				if(playerOne.getTank() != null)
					playerOne.getTank().setTurret(new ShockwaveTurret(controller.generateID(), playerOneTurret.getPosition(), playerOneTurret.getRotation(), playerOneTank));
			}
			if(input.isKeyDown(Input.KEY_6)){
				if(playerOne.getTank() != null)
					playerOne.getTank().setTurret(new BounceTurret(controller.generateID(), playerOneTurret.getPosition(), playerOneTurret.getRotation(), playerOneTank));
			}
		}
		
		if(input.isKeyDown(Input.KEY_ESCAPE)){
			gc.exit();
		}
		
		//Update for tankspawner
		controller.getWorld().getTankSpawner().update(delta);
		
		controller.getWorld().getSpawner().update(delta);
		
		//Update for getGameConditions()
		controller.getGameConditions().update(delta);
		
		updateWorld(delta);
	}
	
	public void updateWorld(int delta){
		Dimension worldSize = GameController.getInstance().getWorld().getSize();
		
		Iterator<Entry<Integer, Entity>> updateIterator = controller.getWorld().getEntities().entrySet().iterator();
		while(updateIterator.hasNext()){
			Map.Entry<Integer, Entity> entry = (Entry<Integer, Entity>) updateIterator.next();
			Entity entity = entry.getValue();
			
			entity.update(delta);
			
			if(entity instanceof MovableEntity){
				float x = entity.getPosition().getX();
				float y = entity.getPosition().getY();
				
				if((x < 0) || (x > worldSize.width) || (y < 0) || (y > worldSize.height)){
					entity.destroy();
				} else {
					controller.getWorld().checkCollisionsFor((MovableEntity)entity);
				}
			}
			if(entity instanceof AbstractSpawnPoint)
				controller.getWorld().checkCollisionsFor(entity);
		}
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {	
		map.draw();
		
		// Render the entities in three layers, bottom, middle and top
		ArrayList<Entity> firstLayerEnts = new ArrayList<Entity>();
		ArrayList<Entity> secondLayerEnts = new ArrayList<Entity>();
		ArrayList<Entity> thirdLayerEnts = new ArrayList<Entity>();
		ArrayList<Entity> fourthLayerEnts = new ArrayList<Entity>();

		Iterator<Entry<Integer, Entity>> iterator = controller.getWorld().getEntities().entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry<Integer, Entity> entry = (Entry<Integer, Entity>) iterator.next();
			Entity entity = entry.getValue();
			
			if(!entity.getSpriteID().equals("")){
				if(entity.getRenderLayer() == Entity.RenderLayer.FIRST)
					firstLayerEnts.add(entity);
				else if(entity.getRenderLayer() == Entity.RenderLayer.SECOND)
					secondLayerEnts.add(entity);
				else if(entity.getRenderLayer() == Entity.RenderLayer.THIRD)
					thirdLayerEnts.add(entity);
				else if(entity.getRenderLayer() == Entity.RenderLayer.FOURTH)
					fourthLayerEnts.add(entity);
			}
		}
		renderEntities(firstLayerEnts);
		renderEntities(secondLayerEnts);
		renderEntities(thirdLayerEnts);
		renderEntities(fourthLayerEnts);
		
		controller.getAnimationHandler().renderAnimations();
		renderGUI(container, g);
	}
	
	@Override
	public void renderGUI(GameContainer gc, Graphics g){
		super.renderGUI(gc, g);
		if(playerOne.getTank() != null){
			if(playerOne.getTank().getShield() != null && playerOne.getTank().getShield().getHealth() <= 100){
				lifebar.render(playerOne.getTank().getHealth()/playerOne.getTank().getMaxHealth(), playerOne.getTank().getShield().getHealth()/playerOne.getTank().getMaxShieldHealth(), g);
			}else{
				lifebar.render(playerOne.getTank().getHealth()/playerOne.getTank().getMaxHealth(), 0, g);
			}
		}
		soundSwitch.render(g);
		
		//Cool timer
		if(controller.getGameConditions().isDelaying()){
			if(controller.getGameConditions().getDelayTimer() > 0)
				g.drawString("Round starts in: " + 
			(controller.getGameConditions().getDelayTimer()/1000 + 1) + " seconds!", 500, 400);
		}
		
		if(controller.getGameConditions().isGameOver()){
			g.drawString("Game Over!", 500, 300);
			g.drawString("Winner: " + controller.getGameConditions().getWinningPlayer().getName(), 500, 400);
			int i = 0;
			for(Player p : controller.getGameConditions().getPlayerList()){
				i++;
				g.drawString(p.getName() + "'s score: " + p.getScore(), 500, (450+(i*25)));
			}
		}
		
		g.setColor(Color.black);
		g.drawString("Volume: " + ((int)(controller.getSoundHandler().getVolume() * 100)) + " %",  10, 50);
	}	

	private void renderEntities(ArrayList<Entity> entities){
		for(Entity entity : entities){
			entSprite = GameController.getInstance().getImageHandler().getSprite(entity.getSpriteID());
			
			if(entSprite != null){
				if(entity instanceof AbstractTank){
					entSprite = GameController.getInstance().getImageHandler().getSprite(entity.getSpriteID());
					if(entity.getRotation()!=0){
							entSprite.setRotation((float) entity.getRotation());
							// draw sprite at the coordinates of the top left corner of tank when it is not rotated
							Shape nonRotatedShape = entity.getShape().transform(Transform.createRotateTransform((float)Math.toRadians(-entity.getRotation()), entity.getPosition().x, entity.getPosition().y));
							entSprite.draw(nonRotatedShape.getMinX(), nonRotatedShape.getMinY());
					} else {
						entSprite.draw(entity.getShape().getMinX(), entity.getShape().getMinY());
					}
				} else {
					if(entity instanceof AbstractTurret){
						entSprite.setCenterOfRotation(((AbstractTurret) entity).getTurretCenter().x, ((AbstractTurret) entity).getTurretCenter().y);
					}
					entSprite.setRotation((float) entity.getRotation());
					entSprite.draw(entity.getSpritePosition().x, entity.getSpritePosition().y);						
				}
			}
		}
	}
}
