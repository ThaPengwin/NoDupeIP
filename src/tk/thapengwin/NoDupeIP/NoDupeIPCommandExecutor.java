package tk.thapengwin.NoDupeIP;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NoDupeIPCommandExecutor
  implements CommandExecutor
{
  private NoDupeIP plugin;

  public NoDupeIPCommandExecutor(NoDupeIP plugin)
  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if ((cmd.getName().equalsIgnoreCase("ip")) && (args.length == 1)) {
      Player player = Bukkit.getPlayer(args[0]);
      if (player != null) {
        String ip = player.getAddress().getAddress().toString().replaceAll("/", "");
        String playern = player.getDisplayName();
        sender.sendMessage(playern + "'s IP is " + ip);
        return true;
      }
      sender.sendMessage("Player '" + args[0] + "' is not online.");
      return false;
    } else if ((cmd.getName().equalsIgnoreCase("NoDupeIP")) && (args.length == 0)) {
      sender.sendMessage("-=NoDupeIP Help=-");
      sender.sendMessage("/NoDupeIP reload  - Reloads the configuration");
      sender.sendMessage("/ip [username]    - Tells specified player's IP");
    } else if(args.length == 1){
      if (args[0].equals("reload")) {
        sender.sendMessage("Reloading config...");
        System.out.println("[NoDupeIP] Reloading config...");
        this.plugin.reloadConfig();
        System.out.println("[NoDupeIP] Config reloaded.");
        sender.sendMessage("Config reloaded.");
        return true;
      }
    } else {
        sender.sendMessage("Invalid arguments.");
        return false;
    }

    return true;
  }
}