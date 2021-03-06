package net.testusuke.open.factionschat;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class FactionData {
    //  PlayerOfFaction
    private static HashMap<Player, Faction> playerFactionMap = new HashMap<>();
    //  PlayerChatMode
    private static HashMap<Player, Boolean> playerChatMode = new HashMap<>();
    //  adminSpyChat
    public static HashMap<Player,Boolean> adminSpyChat = new HashMap<>();

    public static void registerFaction(Player player){
        MPlayer mPlayer = MPlayer.get(player);
        Faction faction = mPlayer.getFaction();
        playerFactionMap.put(player,faction);
    }

    public static Faction getFaction(Player player){
        return playerFactionMap.get(player);
    }

    public static void setFaction(Player player,Faction faction){
        playerFactionMap.put(player,faction);
    }

    public static void removeFaction(Player player){
        playerFactionMap.remove(player);
    }

    public static Boolean containsPlayer(Player player){
        return playerFactionMap.containsKey(player);
    }

    public static void clearMap(){
        playerFactionMap.clear();
    }

    //  ChatMode
    public static void setChatMode(Player player,Boolean b){
        playerChatMode.put(player,b);
    }

    public static Boolean getChatMode(Player player){
        if(!playerChatMode.containsKey(player)){
            setChatMode(player,false);
            return false;
        }
        return playerChatMode.get(player);
    }

    public static void removeChatData(Player player){
        playerChatMode.remove(player);
    }

    public static void clearChatMode(){
        playerChatMode.clear();
    }

    //  SendMessage
    public static void sendMessage(Player fromPlayer,String id,String message,String beforeMSG){
        //  MPlayer Prefix
        MPlayer mPlayer = MPlayer.get(fromPlayer);
        String rankPrefix = mPlayer.getRank().getName().toLowerCase();
        String rank = mPlayer.getRank().getPrefix();
        String symbol;
        if(rank.equalsIgnoreCase("**")){
            symbol = "§c";
        }else if(rank.equalsIgnoreCase("*")){
            symbol = "§6";
        }else if(rank.equalsIgnoreCase("+")){
            symbol = "§y";
        }else if(rank.equalsIgnoreCase("-")){
            symbol = "§b";
        }else {
            symbol = "§d";
        }
        //  loop
        for(Player player : playerFactionMap.keySet()){
            Faction faction = playerFactionMap.get(player);
            if(!faction.getId().equals(id))continue;
            if(player.hasPermission(ChatCommand.adminPermission)){
                player.sendMessage("§a[Faction]§f " + symbol + rankPrefix + " §4§l[Admin]§f" + fromPlayer.getDisplayName() + " §9>>§f " + message + " §7(" + beforeMSG + ")");
                return;
            }
            player.sendMessage("§a[Faction]§f " + symbol + rankPrefix + " §f" + fromPlayer.getDisplayName() + " §9>>§f " + message + " §7(" + beforeMSG + ")");
        }
    }

    // SendMessageToGeneralChat
    public static void sendMessageToGeneral(Player from,String msg,String beforeMSG){
        Faction faction = getFaction(from);
        //  Faction Name
        String name = faction.getName();
        //  Prefix
        String playerPrefix = from.getDisplayName();
        if(from.hasPermission(ChatCommand.adminPermission)){
            Bukkit.broadcastMessage("§6" + name + " §4§l[Admin]§f" + playerPrefix + " §9>>§f " + msg + " §7(" + beforeMSG + ")");
            return;
        }
        Bukkit.broadcastMessage("§6" + name + " §f" + playerPrefix + " §9>>§f " + msg + " §7(" + beforeMSG + ")");
    }

    private static void sendSpyChat(Player player,String msg,String beforeMSG){
        Faction faction = getFaction(player);
        //  Faction Name
        String name = faction.getName();
        //  Prefix
        String playerPrefix = player.getDisplayName();
        String message;
        if(player.hasPermission(ChatCommand.adminPermission)){
            message = "§6" + name + " §4§l[Admin]§f" + playerPrefix + " §9>>§f " + msg + " §7(" + beforeMSG + ")";

        }else {
            message = "§6" + name + " §f" + playerPrefix + " §9>>§f " + msg + " §7(" + beforeMSG + ")";
        }
        for (Player admin : Bukkit.getOnlinePlayers()){
            if(admin.hasPermission(ChatCommand.adminPermission)){
                if(!adminSpyChat.get(player))continue;
                if(!name.equals(getFaction(admin).getName())){
                    admin.sendMessage(message);
                }
            }
        }
    }
}
