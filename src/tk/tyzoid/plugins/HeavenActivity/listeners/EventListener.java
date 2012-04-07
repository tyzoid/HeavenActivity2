package tk.tyzoid.plugins.HeavenActivity.listeners;

import java.util.HashMap;
import java.util.Vector;

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
	
	@SuppressWarnings("unused")
	private String pluginname;
	private CommandUtils cu;
	
	public EventListener(HeavenActivity instance){
		plugin = instance;
		pluginname = plugin.pluginname;
		cu = new CommandUtils(instance);
	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		
		String command = event.getMessage();
		String[] split = command.split(" ");
		
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
					
					player.sendMessage(cu.getPluginTag() + " §cYou have an estimated " + plugin.round(eActivity*100.0, 2) + "% activity");
					event.setCancelled(true);
					return;
				}
			}
			
			if(split[1].equalsIgnoreCase("list")){
				if(!player.hasPermission("activity.view.list")){
					cu.noPermission(player);
					return;
				}
				
				if(split.length == 2){
					player.sendMessage(cu.getPluginTag() + " §fTop 5 players:");
				} else if(split.length == 3) {
					int players;
					try{
						players = Integer.parseInt(split[2]);
					} catch(Exception e){
						cu.showSyntax(player, new int[]{1,2});
						event.setCancelled(true);
						return;
					}
					
					player.sendMessage(cu.getPluginTag() + " §fTop " + players +  " players:");
				} else {
					cu.showSyntax(player, new int[]{1,2});
					event.setCancelled(true);
					return;
				}
				
				
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
	
	private String[] getTopPlayers(int players){
		HashMap<Double, Player> topPlayers = new HashMap<Double, Player>();
		
		for(Activity a : at.values()){
			topPlayers.put(a.getEstimatedActivity(), a.getPlayer());
		}
		
		
		
		return new String[]{"", ""};
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
		
		String[] s = event.getMessage().split(" ");
		if(cu.commandUsed(s, event.getPlayer(), "command-activity") && s.length == 1)
			return;
		
		at.get(event.getPlayer()).incrementcommand();
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerChat(PlayerChatEvent event){
		if(event.isCancelled()) return;
		
		at.get(event.getPlayer()).incrementcommand();
	}
	
}