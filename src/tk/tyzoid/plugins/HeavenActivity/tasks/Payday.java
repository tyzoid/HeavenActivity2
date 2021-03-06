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
		double money;
		if(!activity.getCurrencyDecimal())
			money = (int) Math.round(base*activity.getFactor());
		else
			money = plugin.round(base*activity.getFactor(), 2);
		
		plugin.economy
			.getEconomy()
			.addMoney(player, money);
		
		if(player.hasPermission("activity.notify")){
		player.sendMessage(	"§b[" + pluginname + "§b] §c You account" +
							" has been credited with " + c + "" + money + " for " +
							plugin.round(base*100.0, 2) + "% activity");
		}
		
		activity.reset();
	}
}
