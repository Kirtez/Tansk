package chalmers.TDA367.B17.terrain;

import org.newdawn.slick.geom.Vector2f;

import chalmers.TDA367.B17.controller.GameController;
import chalmers.TDA367.B17.model.*;

public class VerticalWall extends AbstractObstacle {

	private static Vector2f VERTICAL_WALL_SIZE = new Vector2f(15, 150);
	
	/**
	 * Create a new BlackWall.
	 * @param id The id
	 * @param position The position
	 */
	public VerticalWall(int id, Vector2f position) {
		super(id, VERTICAL_WALL_SIZE, position);
		spriteID = "vertical_wall";
		renderLayer = RenderLayer.THIRD;
		GameController.getInstance().getWorld().addEntity(this);
	}
}
