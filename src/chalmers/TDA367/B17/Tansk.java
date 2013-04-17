package chalmers.TDA367.B17;

import chalmers.TDA367.B17.model.*;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

import java.awt.Point;
import java.util.*;

public class Tansk extends BasicGame implements MouseListener{
	World world;
	Input input;
	ArrayList<Player> players;
	Player playerOne;
	Image tank = null;
	Image turret = null;
	Image map = null;
	boolean newImages = true;
	Point mouseCoords;
	AbstractTurret tankTurret;
	AbstractTank playerTank;
	Shape recturret;

	public Tansk() {
		super("Tansk!");
	}
 
	@Override
	public void init(GameContainer gc) throws SlickException {
		world = new World();
		playerOne = new Player("Player One");
		players = new ArrayList<Player>();
		players.add(playerOne);
		
		turret = new Image("turret.png");
		tank = new Image("tank.png");
		map = new Image("map.png");
		input = gc.getInput();
		input.addMouseListener(this);
		mouseCoords = new Point();
		tankTurret = playerOne.getTank().getTurret();
		playerTank = playerOne.getTank();
	}
 
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {

		if(input.isKeyDown(Input.KEY_W)){
			playerOne.getTank().accelerate(delta);
		}else if(input.isKeyDown(Input.KEY_S)){
			playerOne.getTank().reverse(delta);
		}else{
			playerOne.getTank().deaccelerate(delta);
		}
	  
		if(input.isKeyDown(Input.KEY_A) && !input.isKeyDown(Input.KEY_D)){
			if(input.isKeyDown(Input.KEY_S)){
				playerOne.getTank().turnRight(delta);
			} else {
				playerOne.getTank().turnLeft(delta);
			}
		}
	
		if(input.isKeyDown(Input.KEY_D) && !input.isKeyDown(Input.KEY_A)){
			if(input.isKeyDown(Input.KEY_S)){
				playerOne.getTank().turnLeft(delta);
			} else {
				playerOne.getTank().turnRight(delta);
			}
			playerOne.getTank().turnLeft(delta);
            tank.rotate(-playerOne.getTank().getTurnSpeed() * delta/60 * (playerOne.getTank().getSpeed()*0.2f + 0.7f));
		}
		
		if(input.isKeyDown(Input.KEY_TAB)){
			if(newImages){
				tank = new Image("plane.png");
				map = new Image("map.jpg");
				playerOne.getTank().setDirection(new Vector2f(0, -1));
				newImages = false;
			}else{
				tank = new Image("tank.png");
				map = new Image("map.png");
				playerOne.getTank().setDirection(new Vector2f(0, -1));
				newImages = true;
			}
					
		}
		
		
//		tankTurret.setTurretDirection(new Vector2f((float)mouseCoords.getX() - playerOne.getTank().getPosition().x, 
//				(float)mouseCoords.getY()  - playerOne.getTank().getPosition().x));
		playerTank.setTurretPosition(new Vector2f(playerTank.getPosition().x+playerTank.getSize().x/2, playerTank.getPosition().y+playerTank.getSize().y/2-5));
		
		float xDist = (float)mouseCoords.getX() - playerTank.getPosition().x+playerTank.getSize().x/2;
		float yDist = playerTank.getTurretPosition().y+playerTank.getSize().y/2 - (float)mouseCoords.getY();
		
		double angle = Math.toDegrees(Math.atan2(yDist, xDist));
		turret.setRotation((float) angle);
		
		
//		playerOne.getTank().getTurret().setTurretDirection(playerOne.getTank().getTurret().getTurretDirection().add(angle));
//		turret.setCenterOfRotation(0, 0);
//		turret.setRotation((float) (playerOne.getTank().getTurret().getTurretDirection().getTheta()));
		
		playerOne.getTank().update(delta);
	}
	
	public void mouseMoved(int oldx, int oldy, int newx, int newy){
		mouseCoords.setLocation(newx, newy);
	}
 
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		map.draw();
		tank.draw(playerOne.getTank().getPosition().x, playerOne.getTank().getPosition().y);
		
		g.setColor(Color.black);
		g.drawString("posX:  " + playerOne.getTank().getPosition().x, 10, 30); g.drawString("rotX: " + playerOne.getTank().getDirection().x, 180, 30);
		g.drawString("posY:  " + playerOne.getTank().getPosition().y, 10, 50); g.drawString("rotY: " + playerOne.getTank().getDirection().y, 180, 50);
		g.drawString("speed: " + Double.toString(playerOne.getTank().getSpeed()), 10, 70);
		
		g.drawString("turRotX: " + tankTurret.getTurretDirection().x, 10, 90); 		g.drawString("turPosX: " + playerTank.getTurretPosition().x, 180, 90);
		g.drawString("turRotY: " + tankTurret.getTurretDirection().y, 10, 110);		g.drawString("turPosY: " + playerTank.getTurretPosition().y, 180, 110);
		
		g.drawString("mouseX: " + mouseCoords.x, 10, 130);
		g.drawString("mouseY: " + mouseCoords.y, 10, 150);
		
		turret.draw(playerOne.getTank().getPosition().x+playerOne.getTank().getSize().x/2, 
			playerOne.getTank().getPosition().y+playerOne.getTank().getSize().y/2);
		
//				playerOne.getTank().getTurret().getTurretDirection().getX(), 
//				playerOne.getTank().getTurret().getTurretDirection().getY());
		
/*		g.drawLine(playerOne.getTank().getPosition().x, playerOne.getTank().getPosition().y, 
//				mouseCoords.x, mouseCoords.y);
		g.setColor(Color.red);
		g.setLineWidth(5);
		g.drawRect(playerOne.getTank().getPosition().x+playerOne.getTank().getSize().x/2, 
				playerOne.getTank().getPosition().y+playerOne.getTank().getSize().y/2, 
				10, 50);
		/*
		g.drawLine(playerOne.getTank().getPosition().x+playerOne.getTank().getSize().x/2, 
				playerOne.getTank().getPosition().y+playerOne.getTank().getSize().y/2, 
				playerOne.getTank().getTurret().getTurretDirection().getX(), 
				playerOne.getTank().getTurret().getTurretDirection().getY());*/
	
	}
 
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Tansk());

		app.setTargetFrameRate(60);
		app.setMaximumLogicUpdateInterval(500);
		app.setMinimumLogicUpdateInterval(5);
		app.setDisplayMode(800, 600, false);
	
		app.start();
  }
}