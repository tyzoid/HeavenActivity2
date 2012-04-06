package tk.tyzoid.plugins.HeavenActivity.EconomyPlugins;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

import tk.tyzoid.plugins.HeavenActivity.Economy.EconomyHandler;

public class EssentialsEconomy implements EconomyHandler {
	@SuppressWarnings("unused")
	private Essentials essentials;
	private Plugin eco;
	
	public EssentialsEconomy(Plugin eco){
		this.eco = eco;
	}
	
	public static boolean isCompatableWith(Plugin p){
		if(p == null)
			return false;
		
		if(p.getClass().getPackage().getName().equals("com.earth2me.essentials"))
			return true;
		
		return false;
	}
	
	@Override
	public boolean load() {
		if(isCompatableWith(eco)){
			essentials = (Essentials) eco;
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public double addMoney(Player player, double money) {
		try {
			Economy.add(player.getName(), money);
		} catch (UserDoesNotExistException e) {
			Economy.createNPC(player.getName());
		} catch (NoLoanPermittedException e) {
			
		}
		return getBalance(player);
	}
	
	@Override
	public double getBalance(Player player) {
		double balance;
		
		try {
			balance = Economy.getMoney(player.getName());
		} catch (UserDoesNotExistException e) {
			Economy.createNPC(player.getName());
			balance = 0;
		}
		
		return balance;
	}
	
	@Override
	public String getName() {
		return "Essentials Economy";
	}
	
	@Override
	public double setBalance(Player player, double money) {
		try {
			Economy.setMoney(player.getName(), money);
		} catch (UserDoesNotExistException e) {
			Economy.createNPC(player.getName());
		} catch (NoLoanPermittedException e) {
			
		}
		return getBalance(player);
	}
	
	@Override
	public double takeMoney(Player player, double money) {
		try {
			Economy.subtract(player.getName(), money);
		} catch (UserDoesNotExistException e) {
			Economy.createNPC(player.getName());
		} catch (NoLoanPermittedException e) {
			
		}
		
		return this.getBalance(player);
	}
}
