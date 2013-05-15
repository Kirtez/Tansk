package chalmers.TDA367.B17.model;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import chalmers.TDA367.B17.controller.GameController;

public class AbstractObstacle extends Entity {

	/**
	 * Create a new AbstractObstacle.
	 * @param size The size of this object.
	 * @param position The position of this object.
	 */
	public AbstractObstacle(Vector2f size, Vector2f position) {
		setSize(size);
		setShape(new Rectangle(position.getX()-size.getX()/2, position.getY()-size.getY()/2, size.getX(), size.getY()));
		renderLayer = GameController.RenderLayer.FIRST;
	}

}
