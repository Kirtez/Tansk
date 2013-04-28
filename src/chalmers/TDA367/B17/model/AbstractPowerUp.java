package chalmers.TDA367.B17.model;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import chalmers.TDA367.B17.controller.TanskController;

public abstract class AbstractPowerUp extends Entity {

	private String name;
	protected int duration;
	protected int effectDuration;
	protected boolean effectActive;
	protected boolean activated;
	protected AbstractTank absTank;
	protected String type;

	/**
	 * Create a new AbstractPowerUp.
	 */
	public AbstractPowerUp(Vector2f position) {
		super();
		effectActive = false;
		activated = false;
		absTank = null;
		setSize(new Vector2f(50f, 15f));
		setPosition(position);
		setShape(new Rectangle(getPosition().getX()-getSize().getX()/2, getPosition().getY()-getSize().getY()/2, getSize().getX(), getSize().getY()));
	}

	/**
	 * Get the name of this power up.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of this power up.
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the duration of this power up.
	 * @return the time in milliseconds that the
	 * power up will remain on the map
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Set the duration of the power up.
	 * @param duration the time in milliseconds that the
	 * power up will remain on the map
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public void update(int delta) {
		if(duration != 0){
			duration -= delta;
			if(duration <= 0){
				active = false;
			}
		}
		
		if(effectActive){
			if(effectDuration > 0){
				effectDuration -= delta;
				updateEffect();
			}else{
				deactivate();
			}
		}else if(effectActive && absTank != null && effectDuration <= 0){
			deactivate();
		}
	}
	
	public void activate(AbstractTank absTank){
		this.absTank = absTank;
		absTank.setCurrentPowerUp(this);
		effect();
		active = false;
		effectActive = true;
	}
	
	public void deactivate(){
		if(absTank == null || !absTank.isActive())
			return;
		endEffect();
		effectActive = false;
		active = false;
		this.destroy();
		absTank.setCurrentPowerUp(null);
	}
	
	public abstract void effect();

	public abstract void endEffect();

	public abstract void updateEffect();
	
	public void didCollideWith(Entity entity){
		if(entity instanceof AbstractTank){
			activate((AbstractTank)entity);
		}
	}
	
}
