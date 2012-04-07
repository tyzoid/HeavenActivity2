package tk.tyzoid.plugins.HeavenActivity.Economy;

import org.bukkit.plugin.Plugin;

public class Compatability {
	public static boolean isEssentials(Plugin p){
		if(p == null)
			return false;
		
		if(p.getClass().getPackage().getName().equals("com.earth2me.essentials"))
			return true;
		
		return false;
	}
	
	public static boolean isIConomy6(Plugin p){
		if(p == null)
			return false;
		if(p.getClass().getPackage().getName().equals("com.iCo6"))
			return true;
		
		return false;
	}
}
