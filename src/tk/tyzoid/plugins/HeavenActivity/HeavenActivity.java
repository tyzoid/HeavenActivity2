package tk.tyzoid.plugins.HeavenActivity;

import java.math.BigDecimal;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import tk.tyzoid.plugins.HeavenActivity.Economy.Economy;
import tk.tyzoid.plugins.HeavenActivity.lib.Settings;
import tk.tyzoid.plugins.HeavenActivity.listeners.EventListener;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class HeavenActivity extends JavaPlugin {
	public String pluginname = "HeavenActivity";
	
    private final EventListener playerListener = new EventListener(this);
    public Settings settings = new Settings(this);
    public PermissionHandler permissionHandler;
    public boolean permissionsExists = false;
    public boolean useSuperperms = false;
    
    public Economy economy;
	
    public void onDisable() {
        System.out.println("[" + pluginname +"] " + pluginname + " is closing...");
    }
    
    public void onEnable() {
        economy = new Economy(this);
        
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(playerListener, this);
        pm.registerEvents(economy, this);
        
        setupPermissions();
        settings.readSettings();
    }
    
    private void setupPermissions() {
        Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");
        
        if (permissionHandler == null) {
            if (permissionsPlugin != null) {
            	permissionsExists = true;
                permissionHandler = ((Permissions) permissionsPlugin).getHandler();
                System.out.println("[" + pluginname + "] Hooked into Permissions");
            } else {
                permissionsExists = false;
                System.out.println("[" + pluginname + "] Using superperms for permissions");
            }
        }
    }
    
    public boolean hasPermission(Player p, String node){
    	if(permissionsExists){
    		return permissionHandler.has(p, node);
    	} else {
    		return p.hasPermission(node);
    	}
    }
    
    public double round(double unrounded, int precision){
        BigDecimal bd = new BigDecimal(unrounded);
        BigDecimal rounded = bd.setScale(precision, BigDecimal.ROUND_HALF_UP);
        return rounded.doubleValue();
    }
}