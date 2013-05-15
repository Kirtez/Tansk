package chalmers.TDA367.B17.weapons;

import org.newdawn.slick.geom.Vector2f;

import chalmers.TDA367.B17.controller.GameController;
import chalmers.TDA367.B17.model.AbstractProjectile;
import chalmers.TDA367.B17.model.AbstractTank;
import chalmers.TDA367.B17.model.AbstractTurret;

public class ShockwaveTurret extends AbstractTurret{

	public ShockwaveTurret(int id, AbstractTank tank) {
		super(id, tank);
		turretCenter = new Vector2f(16.875f, 16.875f);
		turretLength = 31.5f;
		fireRate = 3000;
		projectileType = "";
	}

	@Override
	public AbstractProjectile createProjectile() {	
		return new ShockwaveProjectile(GameController.getInstance().generateID(), tank, getTurretNozzle());
	}
	
	@Override
	public void fireWeapon(int delta, AbstractTank tank) {
		AbstractProjectile projectile = createProjectile();
		Vector2f angle = new Vector2f(getRotation() + 90);

		projectile.setDirection(angle);

		tank.addProjectile(projectile);
	}

}
