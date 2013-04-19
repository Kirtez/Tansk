package chalmers.TDA367.B17.model;

import org.newdawn.slick.geom.Vector2f;

public abstract class AbstractProjectile extends MovableEntity {
	
	private double damage;
	private double duration;

	public AbstractProjectile(int id, Vector2f velocity,
			float maxSpeed, float minSpeed) {
		super(id, velocity, maxSpeed, minSpeed);
		// TODO Auto-generated constructor stub
	}

	public double getDamage() {
		return damage;
	}

	public double getDuration() {
		return duration;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

}
