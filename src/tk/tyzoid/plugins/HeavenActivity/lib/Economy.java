package tk.tyzoid.plugins.HeavenActivity.lib;

import tk.tyzoid.plugins.HeavenActivity.register.payment.Method;
import tk.tyzoid.plugins.HeavenActivity.register.payment.Methods;

public class Economy {
	private Methods methods;
	private Method method;
	
	public Economy(){
		methods = new Methods();
		method = methods.getMethod();
	}
	
	public Method getMethod(){
		return method;
	}
}
