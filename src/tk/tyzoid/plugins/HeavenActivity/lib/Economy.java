package tk.tyzoid.plugins.HeavenActivity.lib;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import tk.tyzoid.plugins.HeavenActivity.HeavenActivity;
import tk.tyzoid.plugins.HeavenActivity.register.payment.Method;
import tk.tyzoid.plugins.HeavenActivity.register.payment.Methods;

public class Economy implements Listener {
	private Methods methods;
	private Method method;
	private HeavenActivity plugin;
	private String pluginname;
	boolean hooked;
	
	public Economy(HeavenActivity instance){
		methods = new Methods();
		method = methods.getMethod();
		
		plugin = instance;
		pluginname = plugin.pluginname;
		
		hooked = hookEconomy();
		
	}
	
	private boolean hookEconomy(){
		PluginManager pm = Bukkit.getPluginManager();
		
		Plugin[] plugins = {
				pm.getPlugin("iConomy"),
				pm.getPlugin("Essentials"),
				pm.getPlugin("BOSEcomony"),
				pm.getPlugin("MultiCurrency")
		};
		
		if(methods.hasMethod()){
			hooked = true;
			return true;
		}
		
		for(Plugin plugin : plugins){
			if (plugin != null) {
	        	System.out.println("[" + pluginname + "] " + plugin.getName()
	        			+ " is enabled");
	            if(methods.setMethod(plugin)) {
	                method = methods.getMethod();
	                System.out.println("[" + pluginname + "] Payment method found :"
	                		+ method.getName() + " v" + method.getVersion());
	                return true;
	            }
	        }
		}

    	System.out.println("[" + pluginname + "] No support plugins found yet.");
		return false;
	}
	
	public Method getMethod(){
		return method;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
		if (!methods.hasMethod() && methods.setMethod(event.getPlugin())) {
            System.out.println("[" + pluginname + "] Payment method found :"
            		+ method.getName() + " v" + method.getVersion());
            hooked = true;
        }
    }
}
