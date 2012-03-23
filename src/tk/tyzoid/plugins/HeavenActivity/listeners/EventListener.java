package tk.tyzoid.plugins.HeavenActivity.listeners;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tk.tyzoid.plugins.HeavenActivity.HeavenActivity;
import tk.tyzoid.plugins.HeavenActivity.lib.Activity;
import tk.tyzoid.plugins.HeavenActivity.lib.CommandUtils;

public class EventListener implements Listener {
	private final HashMap<Player, Activity> at = new HashMap<Player, Activity>(); //activity tracker
	private final HeavenActivity plugin;
	private String pluginname;
	private CommandUtils cu;

	public EventListener(HeavenActivity instance){
		plugin = instance;
		pluginname = plugin.pluginname;
		cu = new CommandUtils(instance);
	}

	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		String[] split = event.getMessage().split(" ");

		//String command = event.getMessage();
		Player player = event.getPlayer();

		//the /activity command		
		if(cu.commandUsed(split, player, "command-activity")){
			if(split.length == 1){
				if(!player.hasPermission("activity.view.own")){
					cu.noPermission(player);
					
					event.setCancelled(true);
					return;
				} else {
					double eActivity = at.get(player).getEstimatedActivity();
					
					player.sendMessage(cu.getPluginTag() + " §cYou have an estimated " + eActivity*100.0 + "% activity");
					event.setCancelled(true);
					return;
				}
			}
			
			if(split[1].equalsIgnoreCase("list")){
				cu.notImplemented(player);
				
				event.setCancelled(true);
				return;
			}
			
			if(split[1].equalsIgnoreCase("admin") && player.hasPermission("activity.admin")){
				cu.notImplemented(player);
				
				event.setCancelled(true);
				return;
			}
			
			
		}
	}
	
	@EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
    	at.remove(event.getPlayer());
    }
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
    	Player player = event.getPlayer();
    	at.put(player, new Activity(plugin, player));
    }
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event){
		if(event.isCancelled()) return;
		
		at.get(event.getPlayer()).incrementblockbreak();
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockPlace(BlockPlaceEvent event){
		if(event.isCancelled()) return;
		
		at.get(event.getPlayer()).incrementblockplace();
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerCommandPreprocessHigh(PlayerCommandPreprocessEvent event){
		if(!event.isCancelled()) return;
		
		at.get(event.getPlayer()).incrementcommand();
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerChat(PlayerChatEvent event){
		if(event.isCancelled()) return;
		
		at.get(event.getPlayer()).incrementcommand();
	}
	
}