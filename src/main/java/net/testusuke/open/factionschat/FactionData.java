package net.testusuke.open.factionschat;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class FactionData {
    //  PlayerOfFaction
    private static HashMap<Player, Faction> playerFactionMap = new HashMap<>();
    //  PlayerChatMode
    private static HashMap<Player, Boolean> playerChatMode = new HashMap<>();


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
        return playerChatMode.get(player);
    }

    public static void removeChatData(Player player){
        playerChatMode.remove(player);
    }

    public static void clearChatMode(){
        playerChatMode.clear();
    }

    //  SendMessage
    public static void sendMessage(Player fromPlayer,String id,String message){
        //  loop
        for(Player player : playerFactionMap.keySet()){
            Faction faction = playerFactionMap.get(player);
            if(!faction.getId().equals(id))continue;
            player.sendMessage("§a[Faction]§f" + fromPlayer.getDisplayName() + " >> " + message);
        }
    }
}
