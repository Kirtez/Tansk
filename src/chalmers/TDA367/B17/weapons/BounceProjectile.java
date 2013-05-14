package chalmers.TDA367.B17.weapons;

import org.newdawn.slick.geom.*;

import chalmers.TDA367.B17.model.AbstractProjectile;
import chalmers.TDA367.B17.model.AbstractTank;
import chalmers.TDA367.B17.model.Entity;
import chalmers.TDA367.B17.model.MapBounds;
import chalmers.TDA367.B17.terrain.BrownWall;

public class BounceProjectile extends AbstractProjectile {
	
	public BounceProjectile(int id) {
		super(id, new Vector2f(1,1), 100, -100, 5, 3000);
		setSpeed(0.25f);
		setSize(new Vector2f(5f, 10f));
		spriteID = "proj_fire";
	}
	
	@Override
	public void didCollideWith(Entity entity){
//		this is now done in World
//		if(entity instanceof AbstractProjectile || entity == getTank()){
//			return;
//		} else if (entity instanceof MapBounds){
		if(entity instanceof MapBounds){
			MapBounds tmpMapBounds = (MapBounds)entity;
			if(Math.abs(getPosition().x - tmpMapBounds.getShape().getX()) < 5 || 
					Math.abs(getPosition().x - tmpMapBounds.getShape().getWidth()) < 5){
			setDirection(new Vector2f(-getDirection().x, getDirection().y));
			}else if(true){
			setDirection(new Vector2f(getDirection().x, -getDirection().y));
			}
		}else if(entity instanceof BrownWall){
			BrownWall tmpBrownWall = (BrownWall)entity;
			if(Math.abs(getPosition().x - tmpBrownWall.getShape().getX()) < 5 || 
					Math.abs(getPosition().x - tmpBrownWall.getShape().getWidth()) < 5){
			setDirection(new Vector2f(-getDirection().x, getDirection().y));
			}else if(true){
			setDirection(new Vector2f(getDirection().x, -getDirection().y));
			}
		}else if(entity instanceof AbstractTank){
			damageTarget((AbstractTank)entity);
		}
	}
}
