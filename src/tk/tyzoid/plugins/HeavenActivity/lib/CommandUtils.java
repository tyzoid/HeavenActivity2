package tk.tyzoid.plugins.HeavenActivity.lib;

import org.bukkit.entity.Player;

import tk.tyzoid.plugins.HeavenActivity.HeavenActivity;

public class CommandUtils {
	private HeavenActivity plugin;
	private String pluginname;
	
	public CommandUtils(HeavenActivity instance){
		plugin = instance;
		pluginname = plugin.pluginname;
	}
	
	public boolean commandUsed(String[] split, Player player, String property, String permission){
		String[] commands = plugin.settings.getProperty(property).split(",");

		return commandUsed(split, player, commands, permission);
	}

	public boolean commandUsed(String[] split, Player player, String[] commands, String permission){
		boolean commandUsed = false;

		if(player.hasPermission(permission) || player.isOp()){
			for(int i = 0; i < commands.length && !commandUsed; i++){
				commandUsed = split[0].equalsIgnoreCase(commands[i]);
			}
		} else {
			boolean usedCommand = false;
			for(int i = 0; i < commands.length && !usedCommand; i++){
				usedCommand = split[0].equalsIgnoreCase(commands[i]);
			}
			if(usedCommand){
				player.sendMessage("§b[" + pluginname + "§b] §cYou do not have permissions to use that command.");
			}
		}

		return commandUsed;
	}

	public boolean commandUsed(String[] split, Player player, String property){
		String[] commands = plugin.settings.getProperty(property).split(",");

		return commandUsed(split, player, commands);
	}

	public boolean commandUsed(String[] split, Player player, String[] commands){
		boolean commandUsed = false;

		for(int i = 0; i < commands.length && !commandUsed; i++){
			commandUsed = split[0].equalsIgnoreCase(commands[i]);
		}
		return commandUsed;
	}
}
