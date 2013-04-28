package tk.thapengwin.NoDupeIP;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class NoDupeIP extends JavaPlugin
  implements Listener
  {

	public void onEnable()
  {
    getServer().getPluginManager().registerEvents(this, this);
    getCommand("IP").setExecutor(new NoDupeIPCommandExecutor(this));
    getCommand("NoDupeIP").setExecutor(new NoDupeIPCommandExecutor(this));
    loadConfiguration();
    
    System.out.println("[NoDupeIP] Plugin loaded!");
  }

  public void onDisable(){
	  saveConfig();
	  System.out.println("[NoDupeIP] Plugin disabled!");
  }
  @EventHandler
  public void checkDupe(PlayerLoginEvent e) {
	  Player player = e.getPlayer();
	  String daship = e.getAddress().getHostAddress().replace(".", "-");
	  if (getConfig().getString("user-list." + daship) == null) {
	      getConfig().set("user-list." + daship, player.getName());
	      saveConfig();
	    }
	  if (!getConfig().getString("user-list." + daship).equals(player.getName())){
        	  if (!getConfig().getStringList("ignore-ip").contains(daship)){
        		  e.disallow(null, ChatColor.RED + "You can not change usernames! Please join as " + getConfig().getString("user-list." + daship) + "! (case sensitive!)");
        		  for (Player player1 : getServer().getOnlinePlayers()) {
        		      if (player1.hasPermission("NoDupeIP.notify"))
        		        player1.sendMessage(ChatColor.DARK_RED + player.getName() + "(" + player.getAddress().getAddress().getHostAddress().replace(".", "-") + ")" + " tried to join with a changed username!");
        		    }
        	  } else {
        		  for (Player player1 : getServer().getOnlinePlayers()) {
        		      if (player1.hasPermission("NoDupeIP.notify"))
        		        player1.sendMessage(player.getName() + "(" + player.getAddress().getAddress().getHostAddress().replace(".", "-") + ")" + " joined with a changed username, but is on the IP whitelist.");
        		    }
        	  }  
	  }
	  }
  @EventHandler
  public void ipOnJoin(PlayerJoinEvent e2) {
    Player player = e2.getPlayer();
    String ip = player.getAddress().getAddress().getHostAddress();
    String message = ChatColor.GREEN + player.getName() + ChatColor.RESET + "'s IP is " + ChatColor.GREEN + ip;
    for (Player player1 : getServer().getOnlinePlayers()) {
      if (player1.hasPermission("NoDupeIP.iponjoin")) {
        player1.sendMessage(message);
      }
    }
    System.out.println(message);
  }
  public void loadConfiguration() {
	getConfig().options().header("NoDupeIP Configuration\nPLEASE SEPARATE IPs WITH DASHES!\nignore-premium feature not implemented yet!\nDO NOT TOUCH CONFIG VERSION!");
	getConfig().addDefault("configversion", "1");
    getConfig().addDefault("ignore-premium", Boolean.valueOf(false));
    String[] ignoreipdefault = { "localhost", "youriphere" };
    getConfig().addDefault("ignore-ip", ignoreipdefault);
    getConfig().addDefault("user-list.1-1-1-1", "testuser");
    getConfig().options().copyDefaults(true);
    saveConfig();
  }
}