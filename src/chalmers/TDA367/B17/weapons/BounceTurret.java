package chalmers.TDA367.B17.weapons;

import org.newdawn.slick.geom.Vector2f;

import chalmers.TDA367.B17.controller.GameController;
import chalmers.TDA367.B17.event.GameEvent;
import chalmers.TDA367.B17.event.GameEvent.EventType;
import chalmers.TDA367.B17.model.AbstractProjectile;
import chalmers.TDA367.B17.model.AbstractTank;
import chalmers.TDA367.B17.model.AbstractTurret;

public class BounceTurret extends AbstractTurret {

	private static final int DEFAULT_AMMO = 30;
	private int ammoLeft;
	/**
	 * Create a new BounceTurret.
	 * @param id The id
	 * @param startingRotation The starting rotation
	 * @param tank The tank it belongs to
	 * @param color The color
	 */
	public BounceTurret(int id, Vector2f position, double startingRotation, AbstractTank tank, String color) {
		super(id, position, startingRotation, tank, color);
		ammoLeft = DEFAULT_AMMO;
		setFireRate(500);
		setProjectileType("default");
		GameController.getInstance().getWorld().addEntity(this);
	}

	@Override
	public AbstractProjectile createProjectile() {
		return new BounceProjectile(GameController.getInstance().generateID(), getTank(), getTurretNozzle());
	}

	@Override
	public void fireWeapon(int delta, AbstractTank tank){
		if(ammoLeft>0){
			tank.addProjectile(spawnNewProjectile());
			ammoLeft--;
			GameController.getInstance().getWorld().handleEvent(new GameEvent(EventType.SOUND, this, "DEFAULTTURRET_FIRE_EVENT"));
		}else{
			tank.setTurret(new DefaultTurret(GameController.getInstance().generateID(), getPosition(), getRotation(), getTank(), getColor()));
		}
	}
}
