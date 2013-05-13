package chalmers.TDA367.B17.weapons;

import org.newdawn.slick.geom.Vector2f;

import chalmers.TDA367.B17.model.AbstractProjectile;
import chalmers.TDA367.B17.model.AbstractTank;
import chalmers.TDA367.B17.model.AbstractTurret;
import chalmers.TDA367.B17.states.ServerState;

public class ShockwaveTurret extends AbstractTurret{

	public ShockwaveTurret(int id) {
		super(id);
		turretCenter = new Vector2f(22.5f, 22.5f);
		turretLength = 42f;
		setSize(new Vector2f(45f, 65f));
		fireRate = 3000;
		projectileType = "";
	}

	@Override
	public AbstractProjectile createProjectile() {
		return new ShockwaveProjectile(ServerState.getInstance().generateID(), getTurretNozzle());
	}
	
	@Override
	public void fireWeapon(int delta, AbstractTank tank) {
		AbstractProjectile projectile = createProjectile();
		Vector2f angle = new Vector2f(getRotation() + 90);

		projectile.setDirection(angle);

//		tank.addProjectile(projectile);
	}

}
