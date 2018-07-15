package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Player implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private int clickScore;
	private int autoScore;
	private int scoreFactor;
	private String level;
	private HashMap<Integer, String> playerLevelDescription;
	
	// constructor
	public Player(String username) {
		this.username = username;
		playerLevelDescription = new HashMap<Integer, String>();
		playerLevelDescription.put(0, "Saiyan Novice");
		playerLevelDescription.put(200, "Saiyan Master");
		playerLevelDescription.put(300, "Super Saiyan");
		
		clickScore = 0;
		autoScore = 0;
		scoreFactor = 1;
		level = "No level";
	}
	
	// get info
	public String getUsername() {
		return username;
	}
	public int getScore() {
		return clickScore + autoScore;
	}
	public int getScoreFactor() {
		return scoreFactor;
	}
	
	// score manipulation
	// returns score after manipulation
	public int incrementScore() {
		clickScore += scoreFactor;
		ArrayList<Integer> levelList = new ArrayList<Integer>(playerLevelDescription.keySet());
		Collections.sort(levelList);
		
		int lastLevelScore = clickScore + autoScore;
		int maxScore = 0;
		for (Integer j : levelList) {
			//System.out.println("j value: "+j);
			if (j < lastLevelScore) maxScore = j;
			//System.out.println("last level score: "+maxScore);
		}
		level = playerLevelDescription.get(maxScore);
		return autoScore + clickScore;
	}
	public int decrementScore() {
		return autoScore + (clickScore--);
	}
	public int changeScoreBy(int change) {
		return autoScore + (clickScore += change * scoreFactor);
	}
	public int autoScore() {
		return clickScore + (autoScore += scoreFactor);
	}
	
	// scoreFactor upgrades
	// returns scoreFactor after changes
	public int incrementScoreFacotor() {
		return (scoreFactor++);
	}
	public int decrementScoreFacotor() {
		return (scoreFactor--);
	}
	public int changeScoreFacotorBy(int factor) {
		return (scoreFactor *= factor);
	}

	public String getLevel() {
		return level;
	}
	
}
