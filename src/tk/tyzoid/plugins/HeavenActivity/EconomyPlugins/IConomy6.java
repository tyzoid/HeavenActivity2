package tk.tyzoid.plugins.HeavenActivity.EconomyPlugins;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.iCo6.iConomy;
import com.iCo6.system.Accounts;

import tk.tyzoid.plugins.HeavenActivity.Economy.Compatability;
import tk.tyzoid.plugins.HeavenActivity.Economy.EconomyHandler;

public class IConomy6 implements EconomyHandler {
	@SuppressWarnings("unused")
	private iConomy economy;
	private Accounts accounts;
	private Plugin eco;
	
	public IConomy6(Plugin eco){
		this.eco = eco;
	}
	
	@Override
	public String getName(){
		return "iConomy 6";
	}
	
	@Override
	public boolean load(){
		if(Compatability.isIConomy6(eco)){
			economy = (iConomy) eco;
			accounts = new Accounts();
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public double addMoney(Player player, double money) {
		accounts.get(player.getName()).getHoldings().add(money);
		return getBalance(player);
	}
	
	@Override
	public double getBalance(Player player) {
		if (accounts.exists(player.getName())) {
			return accounts.get(player.getName()).getHoldings().getBalance();
		} else {
			return 0;
		}
	}
	
	@Override
	public double setBalance(Player player, double money) {
		accounts.get(player.getName()).getHoldings().setBalance(money);
		return getBalance(player);
	}
	
	@Override
	public double takeMoney(Player player, double money) {
		accounts.get(player.getName()).getHoldings().subtract(money);
		return getBalance(player);
	}
	
}
