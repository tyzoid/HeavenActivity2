package tk.tyzoid.plugins.HeavenActivity.lib;

import org.bukkit.entity.Player;

import tk.tyzoid.plugins.HeavenActivity.HeavenActivity;
import tk.tyzoid.plugins.HeavenActivity.tasks.Payday;

public class Activity {
	private int messages, blockbreak, blockplace, command, trackingtime, difficulty;
	private double factor;
	private double[] multipliers = new double[4];
	private long initialtime;
	private boolean trackchat, trackcommand, trackblockplace, trackblockbreak, currdecimal;
	private char currency;
	
	Player player;
	
	HeavenActivity plugin;
	
	int taskId;
	
	public Activity(HeavenActivity instance, Player player){
		init(instance, player, 0, 0, 0, 0);
	}
	
	public Activity(HeavenActivity instance, Player player, int messages, int blockbreak, int blockplace, int command){
		init(instance, player, messages, blockbreak, blockplace, command);
	}
	
	private void init(HeavenActivity instance, Player player, int messages, int blockbreak, int blockplace, int command){
		this.messages = messages;
		this.blockbreak = blockbreak;
		this.blockplace = blockplace;
		this.command = command;
		
		initialtime = System.currentTimeMillis()/1000;
		
		plugin = instance;
		updateSettings();
		
		this.player = player;
		
		taskId = scheduleTask();
	}
	
	public synchronized void updateSettings(){
		try{
			trackingtime 	= Integer.parseInt(plugin.settings.getProperty("tracking-time"));
			difficulty 		= Integer.parseInt(plugin.settings.getProperty("tracking-quarter"));
			factor			= Double.parseDouble(plugin.settings.getProperty("currency-factor"));
			
			multipliers[0] = Double.parseDouble(plugin.settings.getProperty("multiplier-block-destroy"));
			multipliers[1] = Double.parseDouble(plugin.settings.getProperty("multiplier-block-place"));
			multipliers[2] = Double.parseDouble(plugin.settings.getProperty("multiplier-chat"));
			multipliers[3] = Double.parseDouble(plugin.settings.getProperty("multiplier-command"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		trackchat		= plugin.settings.getProperty("tracking-chat").equalsIgnoreCase("true");
		trackcommand	= plugin.settings.getProperty("tracking-command").equalsIgnoreCase("true");
		trackblockplace	= plugin.settings.getProperty("tracking-block-place").equalsIgnoreCase("true");
		trackblockbreak	= plugin.settings.getProperty("tracking-block-break").equalsIgnoreCase("true");
		
		currdecimal		= plugin.settings.getProperty("currenty-decimals").equalsIgnoreCase("true");
		
		currency		= plugin.settings.getProperty("currency-symbol").toCharArray()[0];
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
		double activity = 9/(8+64*Math.exp((-1.0/difficulty) * getBase())) - .125;
		
		return activity;
	}
	
	public synchronized double getEstimatedActivity(){
		long currenttime = System.currentTimeMillis()/1000;
		double tm = trackingtime/(currenttime - initialtime); //time multiplier
		double activity = 9/(8+64*Math.exp((-tm/difficulty) * getBase())) - .125;
		
		return activity;
	}
	
	private double getBase(){
		double base = 0;
		if(trackblockbreak)
			base+=blockbreak * multipliers[0];
		if(trackblockplace)
			base+=blockplace * multipliers[1];
		if(trackchat)
			base+=messages * multipliers[2];
		if(trackcommand)
			base+=command * multipliers[3];
		
		return base;
	}
	
	public synchronized double getFactor(){
		return factor;
	}
	
	public synchronized char getCurrencySymbol(){
		return currency;
	}
	
	public synchronized boolean getCurrencyDecimal(){
		return currdecimal;
	}
	
	public synchronized void reset(){
		this.messages = 0;
		this.blockbreak = 0;
		this.blockplace = 0;
		this.command = 0;
		
		initialtime = System.currentTimeMillis()/1000;
		
		updateSettings();
		
		taskId = scheduleTask();
	}
	
	private int scheduleTask(){
		return plugin.getServer().getScheduler().scheduleAsyncDelayedTask(
				plugin,
				new Payday(plugin, this),
				(trackingtime + System.currentTimeMillis()/1000 - initialtime)*20);
	}
	
	public Player getPlayer(){
		return player;
	}
}
