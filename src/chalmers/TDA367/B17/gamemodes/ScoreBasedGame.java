package chalmers.TDA367.B17.gamemodes;

import java.util.*;

import chalmers.TDA367.B17.model.Player;



public abstract class ScoreBasedGame extends GameConditions{
private static int DEFAULT_SCORE_LIMIT = 8;	
private int scoreLimit;

	public ScoreBasedGame(){
		this.scoreLimit = DEFAULT_SCORE_LIMIT;
	}
	
	@Override
	public void update(int delta){
		super.update(delta);
		//Check whether all players have been eliminated
		for(int i = 0; i < players.size(); i++){
			if(players.get(i).isActive() && players.get(i).isEliminated()){
				players.get(i).setActive(false);
				eliminatedPlayerCount++;
				incrementPlayerScores();
			}else if(players.get(i).isActive()){
				//Keep setting the roundWinner for later use
				roundWinner = players.get(i);
			}
		}
		
		
	}
	
	@Override
	public void endRound(){
		if(!roundEnded){
			super.endRound();
			//Winning by score
			for(Player p: players){
				if(p.getScore() >= scoreLimit){
					setGameOver(true);
				}
			}
		}
	}
	
	/**
	 * Increments every player's score.
	 */
	public void incrementPlayerScores(){
		for(Player p : getPlayerList()){
			if(p.isActive())
				p.setScore(p.getScore() + 1);
		}
	}
	
	@Override
	public List<Player> getWinningPlayers(){
		return getHighestScoringPlayers();
	}
	
	/**
	 * Return the score of a player at an index.
	 * @param index The index of the player.
	 * @return The score of the player at index.
	 */
	public int getPlayerScoreAtIndex(int index){
		return getPlayerList().get(index).getScore();
	}

	/**
	 * Set the score-limit.
	 * @param scoreLimit The new score-limit
	 */
	public void setScoreLimit(int scoreLimit) {
		this.scoreLimit = scoreLimit;
	}
	
	/**
	 * Returns the player with the highest score.
	 * return Player with the highest score
	 */
	public List<Player> getHighestScoringPlayers(){
		List<Player> player = new ArrayList<Player>();
		player.add(getPlayerList().get(0));
		for(int i = 1; i < players.size(); i++){
			if(getPlayerScoreAtIndex(i) > player.get(0).getScore()){
				player.clear();
				player.add(players.get(i));
			}else if(getPlayerScoreAtIndex(i) == player.get(0).getScore()){
				player.add(players.get(i));
			}
		}

		return player;
	}

	
	/**
	 * Return the score-limit.
	 * @return The score-limit
	 */
	public int getScoreLimit() {
		return scoreLimit;
	}
}