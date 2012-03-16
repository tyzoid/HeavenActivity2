package tk.tyzoid.plugins.HeavenActivity.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import tk.tyzoid.plugins.HeavenActivity.HeavenActivity;
import tk.tyzoid.plugins.HeavenActivity.lib.CommandUtils;

public class PlayerListener implements Listener {
	private final HeavenActivity plugin;
	private String pluginname;
	private CommandUtils cu;

	PlayerListener(HeavenActivity instance){
		plugin = instance;
		pluginname = plugin.pluginname;
		cu = new CommandUtils(plugin);
	}

	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		String[] split = event.getMessage().split(" ");

		String command = event.getMessage();
		Player player = event.getPlayer();

		//the /activity command		
		if(cu.commandUsed(split, player, "command-activity")){
			if(split.length == 1){
				if(!player.hasPermission("activity.view.own")){
					player.sendMessage("§3[§6" + pluginname + "§3] §cYou don't have permission to do that.");
					event.setCancelled(true);
					return;
				}
				
				
			}
			if(split[1].equalsIgnoreCase("list")){
				System.out.println("§3[§6" + pluginname +"§3] §cCommand not implemented yet! Sorry...");
			}
		}
	}
}
