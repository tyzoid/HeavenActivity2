package tk.tyzoid.plugins.HeavenActivity.listeners;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
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
	private final HashMap<String, Activity> at = new HashMap<String, Activity>(); //activity tracker
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
					double ea = plugin.round(at.get(player).getEstimatedActivity()*100, 2);
					String plt = cu.getPluginTag();
					
					player.sendMessage(plt + " §cYou have an estimated " + ea + "% activity");
					event.setCancelled(true);
					return;
				}
			}
			
			if(split[1].equalsIgnoreCase("list")){
				if(!player.hasPermission("activity.view.list")){
					cu.noPermission(player);
					event.setCancelled(true);
					return;
				}
				
				if(split.length == 2){
					String plt = cu.getPluginTag();
					int po = Bukkit.getOnlinePlayers().length;
					player.sendMessage(plt + " §fTop " + Math.min(5, po) + " players:");
					
					List<Activity> topPlayers = getTopPlayers(5);
					for(Activity a : topPlayers){
						String d = a.getPlayer().getDisplayName();
						double ea = plugin.round(a.getEstimatedActivity()*100, 2);
						
						player.sendMessage(plt + " §f" + d + " has " + ea + "% activity");
					}
				} else if(split.length == 3) {
					int players;
					try{
						players = Integer.parseInt(split[2]);
					} catch(Exception e){
						cu.showSyntax(player, new int[]{1,2});
						event.setCancelled(true);
						return;
					}
					
					String plt = cu.getPluginTag();
					int po = Bukkit.getOnlinePlayers().length;
					player.sendMessage(plt + " §fTop " + Math.min(players, po) + " players:");
					
					List<Activity> topPlayers = getTopPlayers(players);
					for(Activity a : topPlayers){
						String d = a.getPlayer().getDisplayName();
						double ea = plugin.round(a.getEstimatedActivity()*100, 2);
						
						player.sendMessage(plt + " §f" + d + " has " + ea + "% activity");
					}
				} else {
					cu.showSyntax(player, new int[]{1,2});
					event.setCancelled(true);
					return;
				}
				
				
				event.setCancelled(true);
				return;
			} else if(split[1].equalsIgnoreCase("admin")){
				if(!player.hasPermission("activity.admin")){
					cu.noPermission(player);
					event.setCancelled(true);
					return;
				}
				cu.notImplemented(player);
				
				event.setCancelled(true);
				return;
			} else {
				if(!player.hasPermission("activity.view.other")){
					cu.noPermission(player);
					event.setCancelled(true);
					return;
				}
				
				Player pl = Bukkit.getPlayer(split[1]);
				if((pl == null) || !pl.isOnline()){
					player.sendMessage(cu.getPluginTag() + " §fPlayer must be online.");
					event.setCancelled(true);
					return;
				}
				
				String d = pl.getDisplayName();
				String plt = cu.getPluginTag();
				
				double ea = plugin.round(at.get(pl).getEstimatedActivity()*100, 2);
				
				player.sendMessage(plt + " §f" + d + " has " + ea + "% activity");
			}
		}
	}
	
	private List<Activity> getTopPlayers(int players){
		List<Activity> topPlayers = new ArrayList<Activity>(at.values());
		
		Collections.sort(topPlayers, new Comparator<Activity>(){
			@Override
			public int compare(Activity a, Activity b) {
				double difference = b.getEstimatedActivity() - a.getEstimatedActivity();
				return (int) Math.round(100*(difference));
			}
		});
		
		List<Activity> topNPlayers = new ArrayList<Activity>();
		
		int i = 0;
		for(Activity a : topPlayers){
			if(i < players)
				topNPlayers.add(a);
			else
				return topNPlayers;
		}
		
		return topNPlayers;
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		at.remove(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		at.put(player.getName(), new Activity(plugin, player));
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event){
		if(event.isCancelled()) return;
		
		at.get(event.getPlayer().getName()).incrementblockbreak();
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockPlace(BlockPlaceEvent event){
		if(event.isCancelled()) return;
		
		at.get(event.getPlayer().getName()).incrementblockplace();
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerCommandPreprocessHigh(PlayerCommandPreprocessEvent event){
		if(!event.isCancelled()) return;
		
		String[] s = event.getMessage().split(" ");
		if(cu.commandUsed(s, event.getPlayer(), "command-activity") && s.length == 1)
			return;
		
		at.get(event.getPlayer().getName()).incrementcommand();
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerChat(PlayerChatEvent event){
		if(event.isCancelled()) return;
		
		at.get(event.getPlayer().getName()).incrementmessages();
	}
}