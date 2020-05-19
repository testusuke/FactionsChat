package net.testusuke.open.factionschat;

import com.massivecraft.factions.entity.Faction;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatListener implements Listener {
    private final Main plugin;
    public ChatListener(Main main) {
        this.plugin = main;
    }

    //  PreChat
    @EventHandler
    public void onPreChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String msg = event.getMessage();
        //  Mode
        if(!plugin.mode)return;
        //  PlayerChatMode
        if(!FactionData.getChatMode(player))return;
        //  GetFaction
        Faction faction = FactionData.getFaction(player);
        String id = faction.getId();
        //  Send
        event.setCancelled(true);
        FactionData.sendMessage(player,id,msg);
    }

    //  join
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        FactionData.registerFaction(player);
        Faction faction = FactionData.getFaction(player);
        player.sendMessage(plugin.prefix + "§aあなたは§6" + faction.getName() + "§aに所属しています");
    }

    //  Quit
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        FactionData.removeFaction(player);
    }
}
