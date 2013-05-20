package chalmers.TDA367.B17.weapons;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.geom.Vector2f;

import chalmers.TDA367.B17.controller.GameController;
import chalmers.TDA367.B17.event.GameEvent;
import chalmers.TDA367.B17.model.AbstractObstacle;
import chalmers.TDA367.B17.model.AbstractProjectile;
import chalmers.TDA367.B17.model.AbstractTank;
import chalmers.TDA367.B17.model.Entity;
import chalmers.TDA367.B17.model.MapBounds;

public class ShockwaveProjectile extends AbstractProjectile {

	private boolean activated = false;
	
	protected Map<AbstractTank, Integer> tankMap = new HashMap<AbstractTank, Integer>();
	
	public ShockwaveProjectile(AbstractTank tank, Vector2f position) {
		super(tank, position, new Vector2f(1,1), 100, 0, 0, 1500);
		setSpeed(0.2f);
		setSize(new Vector2f(8,10));
		setPosition(position);
		spriteID = "shockwave_proj";
	}
	
	public void detonate(){
		GameController.getInstance().getWorld().handleEvent(new GameEvent(this, "SHOCKWAVE_DETONATE_EVENT"));
		activated = true;
		for(int i = 0; i < 40; i++){
			ShockwaveSecondaryProjectile projectile = 
					new ShockwaveSecondaryProjectile(getTank(), new Vector2f(getPosition()), this);
			projectile.setDirection(new Vector2f(getRotation() + i*9));
			
			getTank().addProjectile(projectile);
		}
		this.destroy();
	}
	
	@Override
	public void update(int delta){
		if(getDurationTimer() <= 20 && !activated){
			detonate();
		}
		super.update(delta);
	}
	
	public void didCollideWith(Entity entity){
		if(entity instanceof MapBounds || entity instanceof AbstractObstacle || entity instanceof AbstractTank){
			if(entity instanceof AbstractTank){
				if(!activated && (AbstractTank)entity != getTank()){
					detonate();
				}
			} else if(!activated){
				detonate();
			}
		}
	}
	
	public int tankDamaged(AbstractTank tank){
		if(!tankMap.containsKey(tank)){
			tankMap.put(tank, 1);
			return 1;
		}else{
			int tmp = tankMap.get(tank) + 1;
			tankMap.remove(tank);
			tankMap.put(tank, tmp);
			return tmp;
		}
	}
}
