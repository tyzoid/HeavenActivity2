package tk.tyzoid.plugins.HeavenActivity.lib;

import tk.tyzoid.plugins.HeavenActivity.HeavenActivity;

public class Activity {
	private int messages, blockbreak, blockplace, command;
	private long initialtime;
	private HeavenActivity plugin;
	 
	
	public Activity(HeavenActivity instance){
		this.messages = 0;
		this.blockbreak = 0;
		this.blockplace = 0;
		this.command = 0;
		
		initialtime = System.currentTimeMillis()/1000;
		
		plugin = instance;
	}
	
	public Activity(HeavenActivity instance, int messages, int blockbreak, int blockplace, int command){
		this.messages = messages;
		this.blockbreak = blockbreak;
		this.blockplace = blockplace;
		this.command = command;
		
		initialtime = System.currentTimeMillis()/1000;
		
		plugin = instance;
	}
	
	public synchronized int getmessages(){
		return messages;
	}
	
	public synchronized int getblockbreak(){
		return blockbreak;
	}
	
	public synchronized int getblockplace(){
		return blockplace;
	}
	
	public synchronized int getcommand(){
		return messages;
	}
	
	public synchronized void incrementmessages(){
		messages++;
	}
	
	public synchronized void incrementblockbreak(){
		blockbreak++;
	}
	
	public synchronized void incrementblockplace(){
		blockplace++;
	}
	
	public synchronized void incrementcommand(){
		command++;
	}
	
	public synchronized double getActivity(){
		double activity = 1/(1+8*Math.exp(-(messages/150+blockplace/150+blockbreak/150+command/150)));
		
		return activity;
	}
	
	public synchronized double getEstimatedActivity(){
		long currentTime = System.currentTimeMillis()/1000;
		double timeMultiplier;
		double activity = 1/(1+8*Math.exp(-(messages/150+blockplace/150+blockbreak/150+command/150)));
		
		return activity;
	}
}
