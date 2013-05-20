package chalmers.TDA367.B17.weaponPickups;

import org.newdawn.slick.geom.Vector2f;

import chalmers.TDA367.B17.controller.GameController;
import chalmers.TDA367.B17.model.AbstractTank;
import chalmers.TDA367.B17.weapons.ShockwaveTurret;

public class ShockwavePickup extends AbstractWeaponPickup{

	/**
	 * Create a new ShockwavePickup.
	 * @param id The id
	 * @param position The position
	 */
	public ShockwavePickup(int id, Vector2f position) {
		super(id, position);
		spriteID = "ShockwaveIcon";
	}
	
	@Override
	public void activate(AbstractTank absTank){
		super.activate(absTank);
		absTank.setTurret(new ShockwaveTurret(GameController.getInstance().generateID(), absTank));
	}
}