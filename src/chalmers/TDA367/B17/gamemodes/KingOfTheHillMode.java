package chalmers.TDA367.B17.gamemodes;

import java.util.HashMap;

import org.newdawn.slick.geom.Vector2f;

import chalmers.TDA367.B17.controller.GameController;
import chalmers.TDA367.B17.model.Player;

public class KingOfTheHillMode extends ScoreBasedGame{
	
	public static final float DEFAULT_ZONE_X = 512;
	public static final float DEFAULT_ZONE_Y = 384;
	private KingOfTheHillZone pointZone;
	private HashMap<Player, Integer> playersInZone;
	
	public KingOfTheHillMode(int scoreLimit){
		super(scoreLimit);
		playersInZone = new HashMap<Player, Integer>();
	}
	
	@Override
	public void update(int delta){
		if(!isGameOver()){
			super.update(delta);
			for(Player p: players){
				if(!p.isActive()){
					p.spawnTank();
					
				}
				if(p.getTank() != null && playerInZone(p)){
//					if the player has not previously been in the zone this round, add him/her.
					if(!playersInZone.containsKey(p)){
						playersInZone.put(p, 0);
					}else{
						Integer timeInZone = playersInZone.get(p);
						timeInZone +=delta;
//						If the player has been in the KOTH zone for a whole second, award a point.
						if(timeInZone > 1000){
							incrementPlayerScore(p);
							timeInZone = 0;
						}
						playersInZone.put(p, timeInZone);
					}
				}
			}
			for(Player p: players){
				if(p.getScore() >= getScoreLimit()){
					setGameOver(true);
					addWinningPlayer(p);
				}
			}
		}
	}
	
	public KingOfTheHillZone getZone(){
		return pointZone;
	}
	
	public void generateZone(Vector2f position){
		pointZone = new KingOfTheHillZone(GameController.getInstance().generateID(), position);
	}
	
	public boolean playerInZone(Player p){
		return pointZone.getShape().contains(p.getTank().getShape()) || pointZone.getShape().intersects(p.getTank().getShape());
	}
}
