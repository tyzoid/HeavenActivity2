package tk.tyzoid.plugins.HeavenActivity;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerListener implements Listener {
	private final HeavenActivity plugin;
	private String pluginname;
	
	PlayerListener(HeavenActivity instance){
		plugin = instance;
		pluginname = plugin.pluginname;
	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
    	String[] split = event.getMessage().split(" ");
    	
		String command = event.getMessage();
		Player player = event.getPlayer();
		
		//the /activity command
		if(split[0] == "/activity" && plugin.hasPermission(player, "activity.view.other")){
			//TODO finish command
			return;
		}
	}
}
