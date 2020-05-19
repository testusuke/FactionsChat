package net.testusuke.open.factionschat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommand implements CommandExecutor {
    private final Main plugin;
    public ChatCommand(Main main) {
        this.plugin = main;

    }

    private final String permission = "factionschat.general";
    private final String adminPermission = "factionschat.admin";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player) || !sender.hasPermission(permission))return false;

        if(args.length == 0){
            sendHelp((Player) sender);
            return true;
        }
        if(args.length == 1){
            String args0 = args[0];
            Player player = (Player)sender;
            //  Help
            if(args0.equalsIgnoreCase("help")){
                sendHelp(player);
                return true;
            }
            //  faction
            if(args0.equalsIgnoreCase("f")||args0.equalsIgnoreCase("faction")){
                if(!plugin.mode){
                    sendNotUse(player);
                    return false;
                }
                FactionData.registerFaction(player);
                FactionData.setChatMode(player,true);
                player.sendMessage(plugin.prefix + "§a派閥チャットに切り替えました。");
                return true;
            }
            //  General
            if(args0.equalsIgnoreCase("g")||args0.equalsIgnoreCase("general")){
                FactionData.setChatMode(player,false);
                player.sendMessage(plugin.prefix + "§a全体チャットに切り替えました。");
                return true;
            }
            //  On
            if(args[0].equalsIgnoreCase("on")){
                if(!player.hasPermission(adminPermission))return false;
                if(plugin.mode){
                    player.sendMessage(plugin.prefix + "§cすでにONになっています");
                }
                plugin.mode = true;
                player.sendMessage(plugin.prefix + "§aONになりました");
                return true;
            }
            //  Off
            if(args[0].equalsIgnoreCase("off")){
                if(!player.hasPermission(adminPermission))return false;
                if(!plugin.mode){
                    player.sendMessage(plugin.prefix + "§cすでにOFFになっています");
                }
                plugin.mode = false;
                player.sendMessage(plugin.prefix + "§aOFFになりました");
                return true;
            }

            //  Else
            sendHelp(player);
        }
        return false;
    }

    private void sendHelp(Player player){
        player.sendMessage("§e========================================");
        player.sendMessage("§6/fc [help] <- ヘルプの表示");
        player.sendMessage("§6/fc f [faction] <- 派閥チャットに切り替えます。");
        player.sendMessage("§6/fc g [general] <- 全体チャットに切り替えます。");
        if(player.hasPermission(adminPermission)){
            player.sendMessage("§c/fc on <- プラグインを有効にします。");
            player.sendMessage("§c/fc off <- プラグインを無効にします。");
        }
        player.sendMessage("§d§lCreated by testusuke Version: " + plugin.version);
        player.sendMessage("§e========================================");
    }

    private void sendNotUse(Player player){
        player.sendMessage(plugin.prefix + "§c現在利用できません。");
    }

}
