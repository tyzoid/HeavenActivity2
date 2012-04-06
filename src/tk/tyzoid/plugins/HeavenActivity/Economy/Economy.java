package tk.tyzoid.plugins.HeavenActivity.Economy;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import tk.tyzoid.plugins.HeavenActivity.HeavenActivity;
import tk.tyzoid.plugins.HeavenActivity.EconomyPlugins.EssentialsEconomy;
import tk.tyzoid.plugins.HeavenActivity.EconomyPlugins.IConomy6;

public class Economy implements Listener {
	private HeavenActivity plugin;
	private String pluginname;
	boolean hooked;
	EconomyHandler econ;
	
	public Economy(HeavenActivity instance){
		
		plugin = instance;
		pluginname = plugin.pluginname;
		
		hooked = hookEconomy();
		
	}
	
	private boolean hookEconomy(){
		PluginManager pm = Bukkit.getPluginManager();
		
		Plugin[] plugins = {
				pm.getPlugin("iConomy"),
				pm.getPlugin("Essentials")
		};
		
		if(plugins[0] != null && IConomy6.isCompatableWith(plugins[0]))
			econ = new IConomy6(plugins[0]);
		else if(plugins[1] != null && EssentialsEconomy.isCompatableWith(plugins[1]))
			econ = new EssentialsEconomy(plugins[1]);
		
		if(econ != null){
			System.out.println("[" + pluginname + "] Hooked into " + econ.getName() + ".");
			econ.load();
			return true;
		}

    	System.out.println("[" + pluginname + "] No supported economy plugins found yet.");
		return false;
	}
	
	public EconomyHandler getEconomy(){
		return econ;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
		Plugin plugin = event.getPlugin();
		
		if(IConomy6.isCompatableWith(plugin))
			econ = new IConomy6(plugin);
		else if(EssentialsEconomy.isCompatableWith(plugin))
			econ = new EssentialsEconomy(plugin);
		
		econ.load();
    }
}
