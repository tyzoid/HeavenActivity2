package tk.tyzoid.plugins.HeavenActivity.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Properties;

import tk.tyzoid.plugins.HeavenActivity.HeavenActivity;

public class Settings {
	private Properties props = new Properties();
	
	private final HashMap<String, String> settingsHolder = new HashMap<String, String>();
	private String pluginname;
	private HeavenActivity plugin;
	
	public Settings(HeavenActivity instance){
		this.plugin = instance;
		this.pluginname = plugin.pluginname;
	}
	
	public void readSettings(){
		System.out.println("[" + pluginname + "] Reading Settings");
		try{
			String path = "plugins/HeavenActivity";
			File propertiesFile = new File(path + "/HeavenActivity.properties");
    		if(!propertiesFile.exists()){
    			(new File(path)).mkdir();
    			propertiesFile.createNewFile();
    		}
		    
			FileInputStream propertiesStream = new FileInputStream(propertiesFile);
			
			props.load(propertiesStream);
			System.out.println("[" + pluginname + "] Properties loaded.");
			propertiesStream.close();
			
			//commands
			loadProperty("command-activity", "/heavenactivity,/activity,/ha");
			
			//currency
			loadProperty("currency-factor", "100");
			loadProperty("currency-symbol", "$");
			
			//multipliers
			loadProperty("multiplier-block-destroy", "1.0");
			loadProperty("multiplier-block-place", "1.2");
			loadProperty("multiplier-chat", "0.25");
			loadProperty("multiplier-command", "0.25");
			
			//tracking options
			loadProperty("tracking-block-place", "true");
			loadProperty("tracking-block-break", "true");
			loadProperty("tracking-chat", "true");
			loadProperty("tracking-command", "true");
			
			loadProperty("tracking-time", "300");
			loadProperty("tracking-type", "logistic");
			loadProperty("tracking-quarter", "50");
			//loadProperty("", "");
			
			//verify settings
			verifySettings();
			
			//save changes
			FileOutputStream propertiesOutputStream = new FileOutputStream(propertiesFile);
			props.store(propertiesOutputStream, "");
		} catch(Exception e){
			System.out.println("[" + pluginname + "] Failed to load properties. Aborting.");
			System.out.println("[" + pluginname + "] Error: " + e.toString());
		}
	}

	public String getProperty(String property){
		return settingsHolder.get(property);
	}
	
	public void reloadData(){
		readSettings();
	}
	
	private void loadProperty(String property, String defaultValue){
		settingsHolder.put(property, lProperty(property, defaultValue));
	}
	
	private String lProperty(String property, String defaultValue){
		String currentProperty;
		currentProperty = props.getProperty(property);
		String value;
    	if(currentProperty == null){
    		System.out.println("[" + pluginname + "] Property not found: " + property + ". Resetting to: " + defaultValue);
    		props.setProperty(property, defaultValue);
    		value = defaultValue;
    	} else {
    		value = currentProperty;
    	}
    	return value;
	}
	

	private void verifySettings() {
		//commands
		if(!verifyCommands(getProperty("command-activity"))){
			setProperty("command-activity", "/heavenactivity,/activity,/ha");
		}
		
		//currency
		if(!verifyInteger(getProperty("currency-factor"))){
			setProperty("currency-factor", "100");
		}
		if(!verifyChar(getProperty("currency-symbol"))){
			setProperty("currency-symbol", "$");
		}
		
		//multipliers
		if(!verifyDouble(getProperty("multiplier-block-destroy"))){
			setProperty("multiplier-block-destroy", "1.0");
		}
		if(!verifyDouble(getProperty("multiplier-block-place"))){
			setProperty("multiplier-block-place", "1.2");
		}
		if(!verifyDouble(getProperty("multiplier-chat"))){
			setProperty("multiplier-chat", "0.25");
		}
		if(!verifyDouble(getProperty("multiplier-command"))){
			setProperty("multiplier-command", "0.25");
		}
		
		//tracking options
		if(!verifyBoolean(getProperty("tracking-block-place"))){
			setProperty("tracking-block-place", "true");
		}
		if(!verifyBoolean(getProperty("tracking-block-break"))){
			setProperty("tracking-block-break", "true");
		}
		if(!verifyBoolean(getProperty("tracking-chat"))){
			setProperty("tracking-chat", "true");
		}
		if(!verifyBoolean(getProperty("tracking-command"))){
			setProperty("tracking-command", "true");
		}

		if(!verifyInteger(getProperty("tracking-time"))){
			setProperty("tracking-time", "300");
		}
		if(!verifyTrackingType(getProperty("tracking-type"))){
			setProperty("tracking-type", "logistic");
		}
		if(!verifyInteger(getProperty("tracking-quarter"))){
			setProperty("tracking-quarter", "150");
		}
	}
	
	private boolean verifyTrackingType(String str){
		return str.equalsIgnoreCase("logistic");
	}
	
	private boolean verifyInteger(String str){
		try{
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	private boolean verifyBoolean(String str){
		return str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false");
	}
	
	private boolean verifyDouble(String str){
		try{
			Double.parseDouble(str);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	private boolean verifyChar(String str){
		return str.length()==1;
	}
	
	private boolean verifyCommand(String str){
		return str.length()>1 && str.charAt(0) == '/';
	}
	
	private boolean verifyCommands(String str){
		String[] split = str.split(",");
		boolean valid = true;
		
		for(int i = 0; i < split.length && valid; i++){
			valid = verifyCommand(split[i]);
		}
		
		return valid;
	}
	
	private void setProperty(String property, String value){
		props.setProperty(property, value);
		settingsHolder.put(property, value);
	}
}