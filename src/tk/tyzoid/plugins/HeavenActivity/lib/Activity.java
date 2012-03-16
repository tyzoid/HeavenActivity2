package tk.tyzoid.plugins.HeavenActivity.lib;

import tk.tyzoid.plugins.HeavenActivity.HeavenActivity;

public class Activity {
	private int messages, blockbreak, blockplace, command;
	private long initialtime;
	private HeavenActivity plugin;
	
	private int trackingtime;
	private int difficulty;
	
	private boolean trackchat, trackcommand, trackblockplace, trackblockbreak;
	
	
	public Activity(HeavenActivity instance){
		init(instance, 0, 0, 0, 0);
	}
	
	public Activity(HeavenActivity instance, int messages, int blockbreak, int blockplace, int command){
		init(instance, messages, blockbreak, blockplace, command);
	}
	
	private void init(HeavenActivity instance, int messages, int blockbreak, int blockplace, int command){
		this.messages = messages;
		this.blockbreak = blockbreak;
		this.blockplace = blockplace;
		this.command = command;
		
		initialtime = System.currentTimeMillis()/1000;
		
		plugin = instance;
		updateSettings();
	}
	
	public synchronized void updateSettings(){
		try{
			trackingtime 	= Integer.parseInt(plugin.settings.getProperty("tracking-time"));
			difficulty 		= Integer.parseInt(plugin.settings.getProperty("tracking-quarter"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		trackchat		= plugin.settings.getProperty("tracking-chat").equalsIgnoreCase("true");
		trackcommand	= plugin.settings.getProperty("tracking-command").equalsIgnoreCase("true");
		trackblockplace	= plugin.settings.getProperty("tracking-block-place").equalsIgnoreCase("true");
		trackblockbreak	= plugin.settings.getProperty("tracking-block-break").equalsIgnoreCase("true");
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
		double activity = 1/(1+8*Math.exp((-1/difficulty) * getBase()));
		
		return activity;
	}
	
	public synchronized double getEstimatedActivity(){
		long currenttime = System.currentTimeMillis()/1000;
		double tm = trackingtime/(currenttime - initialtime); //time multiplier
		double activity = 1/(1+8*Math.exp((-tm/difficulty) * getBase()));
		
		return activity;
	}
	
	private int getBase(){
		int base = 0;
		if(trackblockbreak)
			base+=blockbreak;
		if(trackblockplace)
			base+=blockplace;
		if(trackchat)
			base+=messages;
		if(trackcommand)
			base+=command;
		
		return base;
	}
}
