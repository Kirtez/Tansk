package chalmers.TDA367.B17.model;

import org.newdawn.slick.geom.*;

/**
 * MovableEntity is an entity with a move method that allows it to move.
 * A min- and max-speed is used to determine how fast this object can move,
 * along with a direction.
 */
public abstract class MovableEntity extends Entity {

	protected Vector2f direction;
	private float speed;
	private float maxSpeed;
	private float minSpeed;
	private float acceleration;
	private float friction;
	private double lastDirectionTheta;
	public Vector2f lastPos;
	
	/**
	 * Create a new MovableEntity based on the following parameters
	 * @param id The id
	 * @param direction The direction
	 * @param maxSpeed The maximum speed of this object
	 * @param minSpeed The minimum speed of this object
	 */
	public MovableEntity(int id, Vector2f direction, float maxSpeed, float minSpeed) {
		super(id);
		setDirection(direction);
		this.maxSpeed = maxSpeed;
		this.minSpeed = minSpeed;
		this.acceleration = maxSpeed*0.001f;
		this.friction = maxSpeed*0.001f;
	}
	
	/**
	 * Check if the object is reversing
	 * @return True if the speed is less than 0
	 */
	public boolean isReversing(){
		return speed < 0;
	}
	
	/**
	 * Get the direction of this object
	 * @return The direction of this object
	 */
	public Vector2f getDirection(){
		return direction.copy();
	}
	
	/**
	 * Set the direction of this object.
	 * This also changes the rotation of the shape.
	 * @param newDirection The new direction
	 */
	public void setDirection(Vector2f newDirection){
		if(newDirection != null){
			this.direction = newDirection.copy();
			setShape(getShape().transform(Transform.createRotateTransform((float)Math.toRadians(newDirection.getTheta()-lastDirectionTheta), getShape().getCenterX(), getShape().getCenterY())));
			lastDirectionTheta = newDirection.getTheta();	
		}
	}

	/**
	 * Get the speed of this object
	 * @return The speed of this object
	 */
	public float getSpeed(){
		return speed;
	}
	
	/**
	 * Get the maximum speed of this object
	 * @return The maximum speed
	 */
	public float getMaxSpeed(){
		return maxSpeed;
	}
	
	/**
	 * Get the minimum speed of this object
	 * @return The minimum speed
	 */
	public float getMinSpeed(){
		return minSpeed;
	}
	
	/**
	 * Set the speed of this object will be moving at
	 * @param speed The new speed
	 */
	public void setSpeed(float speed){
		if(speed > maxSpeed){
			this.speed = maxSpeed;
		}else if(speed < minSpeed){
			this.speed = minSpeed;
		}else{
			this.speed = speed;
		}
	}
	
	/**
	 * Set the maximum speed of this object
	 * @param maxSpeed The maximum speed this object can move at
	 */
	public void setMaxSpeed(float maxSpeed){
		this.maxSpeed = maxSpeed;
	}
	
	/**
	 * Set the minimum speed of this object
	 * @param minSpeed The minimum speed this object can move at
	 */
	public void setMinSpeed(float minSpeed){
		this.minSpeed = minSpeed;
	}

	/**
	 * Get the acceleration of this object
	 * @return The acceleration of this object
	 */
	public double getAcceleration() {
		return acceleration;
	}

	/**
	 * Set the acceleration of this object
	 * @param acceleration The acceleration of this object
	 */
	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}

	/**
	 * Get the friction of this object
	 * @return The friction of this object
	 */
	public float getFriction() {
		return friction;
	}

	/**
	 * Set the friction of this object
	 * @param friction The friction of this object
	 */
	public void setFriction(float friction) {
		this.friction = friction;
	}

	/**
	 * Updates the position of the object, uses speed and direction to calculate the new position.
	 * This also translates the shape of this entity.
	 * @param delta The time that has passed since the last update
	 */
	public void move(int delta){
		Vector2f tmp = new Vector2f(direction);
		Vector2f velocity = tmp.scale(speed);
		
		velocity.x = velocity.x * delta;
		velocity.y = velocity.y * delta;
		
		lastPos = getPosition();
		
		setPosition(getPosition().add(velocity));
		setShape(getShape().transform(Transform.createTranslateTransform(velocity.x, velocity.y)));
	}

	/**
	 * Increases the speed of this object based on the acceleration.
	 * @param delta The time that has passed since the last update
	 */
	public void accelerate(int delta){
		setSpeed(speed + acceleration * delta);
	}
	
	/**
	 * Decreases the speed of this object based on the deceleration.
	 * @param delta The time that has passed since the last update
	 */
	public void friction(int delta){
		//if the speed after deceleration is greater than zero the speed is decreased
		if(speed-friction * delta > 0){
			setSpeed(speed - friction * delta);
			
		//if the speed after deceleration is less than zero the speed is increased
		}else if(speed+friction * delta < 0){
			setSpeed(speed + friction * delta); //TODO Make sure there are no possible bugs
		}else{
			setSpeed(0);
		}
	}
	
	/**
	 * Decreases the speed of this object based on the deceleration.
	 * @param delta The time that has passed since the last update
	 */
	public void reverse(int delta){
			setSpeed(speed - friction * delta);
	}

	@Override
	public void update(int delta){
		move(delta);

//		if(getPosition().getX()<0 || getPosition().getX()> 
//		GameController.getInstance().getWorld().getSize().width 
//		|| getPosition().getY()<0 || getPosition().getY()> 
//		GameController.getInstance().getWorld().getSize().height)
//			destroy();
	}
}
