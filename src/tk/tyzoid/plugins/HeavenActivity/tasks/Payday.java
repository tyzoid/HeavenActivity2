package tk.tyzoid.plugins.HeavenActivity.tasks;

import org.bukkit.entity.Player;

import tk.tyzoid.plugins.HeavenActivity.HeavenActivity;
import tk.tyzoid.plugins.HeavenActivity.lib.Activity;

public class Payday implements Runnable{
	private Player player;
	private Activity activity;
	private HeavenActivity plugin;
	
	private String pluginname;
	
	public Payday(HeavenActivity instance, Activity active){
		this.player = active.getPlayer();
		this.plugin = instance;
		this.activity = active;
		
		pluginname = plugin.pluginname;
	} 
	
	public void run(){
		double base = activity.getActivity();
		char c = activity.getCurrencySymbol();
		int money = (int) Math.round(base*activity.getFactor());
		
		plugin.economy
			.getMethod()
			.getAccount(player.getName())
			.add(money);
		
		player.sendMessage(	"§b[" + pluginname + "§b] §c You account" +
							" has been credited with " + c + "" + money);
		
		activity.reset();
	}
}
