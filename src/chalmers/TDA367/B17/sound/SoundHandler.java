package chalmers.TDA367.B17.sound;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import chalmers.TDA367.B17.Tansk;
import chalmers.TDA367.B17.event.GameEvent;

public class SoundHandler {
	
	private Map<String, Sound> sounds;
	private Music menuMusic;
	private Music battleMusic;
	private float volume = 0.5f;
	
	public enum MusicType{
		MENU_MUSIC,
		BATTLE_MUSIC
	}

	/**
	 * Create a new SoundHandler.
	 */
	public SoundHandler(){
		sounds = new HashMap<String, Sound>();
		try {
			menuMusic = new Music(Tansk.SOUNDS_FOLDER + "/Tensions.wav");
			battleMusic = new Music(Tansk.SOUNDS_FOLDER + "/Battle_Music.wav");
		} catch (SlickException e) {
			System.out.println("Failed to load menu music.");
			e.printStackTrace();
		}
	}
	
	public void playMusic(MusicType music){
		if(music == MusicType.MENU_MUSIC){
			if(menuMusic != null){
				menuMusic.play();
			}
		}else if(music == MusicType.BATTLE_MUSIC){
			if(battleMusic != null){
				battleMusic.play();
			}
		}
	}
	
	public void stopMusic(MusicType music){
		if(music == MusicType.MENU_MUSIC){
			if(menuMusic != null){
				menuMusic.stop();
			}
		}
	}

	/**
	 * Play sound related to an event.
	 * @param event a GameEvent
	 */
	public void playSound(GameEvent event){
		if(event.getEventDesc().equals("MENU_MUSIC")) {
			sounds.get("Tensions").play(1, volume);
		}
		else if(event.getEventDesc().equals("TANK_DEATH_EVENT")) {
			sounds.get("Tank_Destroyed").play(1, volume);
		}else if(event.getEventDesc().equals("DEFAULTTURRET_FIRE_EVENT")){
			sounds.get("Default_Firing").play(1, volume);
		}else if(event.getEventDesc().equals("TANK_HIT_EVENT")){
			sounds.get("Tank_Hit").play(1, volume);
		}else if(event.getEventDesc().equals("FLAMETHROWER_EVENT")){
			sounds.get("Flamethrower_Firing").play(1, volume);
		}else if(event.getEventDesc().equals("SHOTGUN_FIRE_EVENT")){
			sounds.get("Shotgun_Firing").play(1, volume);
		}else if(event.getEventDesc().equals("SLOWSPEEDY_FIRE_SECONDARY_EVENT")){
			sounds.get("Slowspeedy_Firing").play(1, volume);
		}else if(event.getEventDesc().equals("SLOWSPEEDY_FIRE_EVENT")){
			sounds.get("Shockwave_Firing").play(1, volume);
		}else if(event.getEventDesc().equals("SHOCKWAVE_DETONATE_EVENT")){
			// no sound yet
		}else if(event.getEventDesc().equals("SHOCKWAVE_FIRE_EVENT")){
			// no sound yet			
		}
	}
	
	/**
	 * Loads all available sound files from a directory.
	 * @param directory The target directory
	 */
	public void loadEverySound(String directory){
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();

		for(File file : listOfFiles) {
			if(file.isFile()) {
				if(file.getName().contains(".wav")){
					try {						
	                    Sound sound = new Sound(directory + "/" + file.getName());
	                    sounds.put(file.getName().substring(0, file.getName().lastIndexOf('.')), sound);
                    } catch (SlickException e) {
                    	System.out.println("SoundHandler: Failed to load sound!");
	                    e.printStackTrace();
                    }
				}
			}
		}
        System.out.println("SoundHandler: Loaded sound.");
	}

	/**
	 * Set the volume.
	 * @param volume
	 */
	public void setVolume(float volume) {
		this.volume = volume;
	}
	
	/**
	 * Get the volume.
	 * @return volume
	 */
	public float getVolume() {
		return volume;
	}
	
	/**
	 * Return whether the sound is muted or not.
	 * @return isSoundOn
	 */
	public boolean isSoundOn(){
		return volume != 0;
	}
}
