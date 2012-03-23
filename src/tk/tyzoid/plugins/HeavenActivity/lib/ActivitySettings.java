package tk.tyzoid.plugins.HeavenActivity.lib;

import tk.tyzoid.plugins.HeavenActivity.HeavenActivity;

public class ActivitySettings {
	HeavenActivity plugin;
	
	private int trackingtime, difficulty, factor;
	private char currency;
	
	public ActivitySettings(HeavenActivity instance){
		plugin = instance;
		
		reloadSettings();
	}
	
	public void reloadSettings(){
		
	}
	
	public int getTrackingTime(){
		return trackingtime;
	}
	
	public int getDifficulty(){
		return difficulty;
	}
	
	public int getFactor(){
		return factor;
	}
	
	public char getCurrencySymbol(){
		return currency;
	}
}
