package chalmers.TDA367.B17.model;

import org.newdawn.slick.geom.Vector2f;

public abstract class Entity{
	protected int id; // The id of this object
	protected Vector2f position; // The position of the Entity
	protected Vector2f size; // The size of the entity

	/**
	 * Create an Entity based on an id
	 * @param id The value used for setting the id of this object
	 */
	public Entity(int id){
		this.id = id;
	}
	
	/**
	 * Get the position of this entity
	 * @return A vector containing the position
	 */
	public Vector2f getPosition(){
		return position;
	}
	
	/**
	 * Get the id of this entity
	 * @return The id of this object
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * Get the size of this entity
	 * @return The size of this entity
	 */
	public Vector2f getSize(){
		return size;
	}
	
	public Vector2f getCenter(){
		return new Vector2f(size.x/2, size.y/2);
	}

	/**
	 * Set the size of this entity
	 * @param size The new size of this entity
	 */
	public void setSize(Vector2f size){
		this.size = size;
	}

	/**
	 * Set the id of this entity
	 * @param id The new id for this object
	 */
	public void setId(int id){
		this.id = id;
	}
	
	/**
	 * Set the position of this entity
	 * @param position The values for the new position
	 */
	public void setPosition(Vector2f position){
		this.position = position;
	}
	
	public void update(int delta){
		
	}
}
