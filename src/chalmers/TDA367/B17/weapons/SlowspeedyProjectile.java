package chalmers.TDA367.B17.weapons;

import org.newdawn.slick.geom.Vector2f;

import chalmers.TDA367.B17.controller.GameController;
import chalmers.TDA367.B17.model.AbstractProjectile;
import chalmers.TDA367.B17.model.AbstractTank;

public class SlowspeedyProjectile extends AbstractProjectile {

	/**
	 * Create a new SlowspeedyProjectile.
	 * @param id The id
	 * @param tank The tank it belongs to
	 * @param position The position
	 */
	public SlowspeedyProjectile(int id, AbstractTank tank, Vector2f position) {
		super(id, tank, position, new Vector2f(1,1), 100, 0, 5, 3000);
		setSpeed(0.05f);
		setSize(new Vector2f(15,5));
		spriteID = "proj_energy";
		GameController.getInstance().getWorld().addEntity(this);
	}
	
	@Override
	public void update(int delta) {
		if(getSpeed() < 0.1){
			setSpeed(getSpeed()*1.01f);
		}else if(getSpeed() < 0.85){
			setSpeed(getSpeed()*1.5f);
		}
		super.update(delta);
	}
}
