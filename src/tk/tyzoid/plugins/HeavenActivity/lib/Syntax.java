package tk.tyzoid.plugins.HeavenActivity.lib;

public class Syntax {
	private final static String[][] syntax = {
			{ //activity
				"§c/activity §f-Shows your current estimated activity",
				"§c/activity list §f-Shows the top 5 players' activity",
				"§c/activity list <number> §f-Shows the top <number> players' activity"
			},
			
	};
	
	public static String getSyntax(int[] cid){
		return syntax[cid[0]][cid[1]];
	}
}
