package chalmers.TDA367.B17.view;

import org.newdawn.slick.Graphics;

import chalmers.TDA367.B17.controller.GameController;


public class SoundSwitch {
	
	private int x;
	private int y;
	private boolean isSoundOn;
	private float storedVolume;
	
	public SoundSwitch(int x, int y){
		this.x = x;
		this.y = y;
		isSoundOn = true;
	}
	
	public void render(Graphics g){
		if(isSoundOn){
			g.drawImage(GameController.getInstance().getImageHandler().getSprite("speaker_on"), x, y);
		}else{
			g.drawImage(GameController.getInstance().getImageHandler().getSprite("speaker_off"), x, y);
		}
	}
	
	public void soundOnOff(float f){
		if(isSoundOn){
			storedVolume = f;
			GameController.getInstance().getSoundHandler().setVolume(0);
			isSoundOn = false;
		}else{
			isSoundOn = true;
			GameController.getInstance().getSoundHandler().setVolume(storedVolume);
		}
	}
}