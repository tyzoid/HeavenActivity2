package tk.tyzoid.plugins.HeavenActivity.Economy;

import org.bukkit.entity.Player;

public interface EconomyHandler {
	
	/**
	 * @param eco
	 * @return whether it loaded or not.
	 */
	public boolean load();
	
	/**
	 * @return the name of the plugin
	 */
	public String getName();
	
	/**
	 * @param player
	 * @return the money in the account
	 */
	public double getBalance(Player player);
	
	/**
	 * @param player, money
	 * @return the new balance in the account
	 */
	public double setBalance(Player player, double money);
	
	/**
	 * @param player, money
	 * @return the money in the account after the transaction
	 */
	public double addMoney(Player player, double money);
	
	/**
	 * @param player, money
	 * @return
	 */
	public double takeMoney(Player player, double money);
}
