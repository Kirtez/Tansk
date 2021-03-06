package chalmers.TDA367.B17.weapons.pickups;

import org.newdawn.slick.geom.Vector2f;

import chalmers.TDA367.B17.controller.GameController;
import chalmers.TDA367.B17.model.AbstractTank;
import chalmers.TDA367.B17.model.AbstractWeaponPickup;
import chalmers.TDA367.B17.weapons.FlamethrowerTurret;

public class FlamethrowerPickup extends AbstractWeaponPickup{

	/**
	 * Create a new FlamethrowerPickup.
	 * @param id The id
	 * @param position The position
	 */
	public FlamethrowerPickup(int id, Vector2f position) {
		super(id, position);
		spriteID = "FlamethrowerIcon";
		GameController.getInstance().getWorld().addEntity(this);
	}
	
	@Override
	public void activate(AbstractTank absTank){
		super.activate(absTank);
		absTank.setTurret(new FlamethrowerTurret(GameController.getInstance().generateID(), absTank.getTurret().getPosition(), absTank.getTurret().getRotation(), absTank, absTank.getColor()));
	}
}
