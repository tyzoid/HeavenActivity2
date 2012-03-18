package tk.tyzoid.plugins.HeavenActivity;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import tk.tyzoid.plugins.HeavenActivity.lib.Economy;
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
    
    public Economy economy = new Economy();
	
    public void onDisable() {
        System.out.println("[" + pluginname +"] " + pluginname + " is closing...");
    }
    
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(playerListener, this);
        
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println("[" + pluginname + "] Starting " + pluginname + " v" + pdfFile.getVersion() + "...");
        setupPermissions();
        settings.readSettings();
    }
    
    private void setupPermissions() {
        Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");
        
        if (permissionHandler == null) {
            if (permissionsPlugin != null) {
            	permissionsExists = true;
                permissionHandler = ((Permissions) permissionsPlugin).getHandler();
                System.out.println("[" + pluginname + "] Permissions found!");
            } else {
                permissionsExists = false;
                
                try{
                	@SuppressWarnings("unused")
					Permission fakePerm = new Permission("fake.perm");
                	useSuperperms = true;
                    System.out.println("[" + pluginname + "] Using built-in permissions.");
                } catch(Exception e){
                	//superperms doesn't exist
                    System.out.println("[" + pluginname + "] Update CraftBukkit, will ya?");
                }
            }
        }
    }
    
    /* Valid nodes:
     * HeavenActivity.*
     * HeavenActivity.foo
     * HeavenActivity.bar
     */
    public boolean hasPermission(Player p, String node){
    	if(!useSuperperms){
    		return permissionHandler.has(p, node);
    	} else {
    		return p.hasPermission(node);
    	}
    }
}