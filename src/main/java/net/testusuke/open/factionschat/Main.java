package net.testusuke.open.factionschat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public final class Main extends JavaPlugin {

    public String prefix = "§e[§aFactions§6Chat§e]§f";
    public boolean mode;

    //  Task
    private BukkitTask task = null;
    private int time = 20* 60;

    //  plugin
    public String pluginName = "CommandHelper";
    //  Version
    public String version = "1.0.1";

    @Override
    public void onEnable(){
        //  Logger
        getLogger().info("==============================");
        getLogger().info("Plugin: " + pluginName);
        getLogger().info("Ver: " + version + "  Author: testusuke");
        getLogger().info("==============================");
        //  Config
        this.saveDefaultConfig();
        loadMode();
        //  Command
        getCommand("fc").setExecutor(new ChatCommand(this));
        //  Event
        getServer().getPluginManager().registerEvents(new ChatListener(this),this);
        //  Runnable
        reloadFactionsRunnable();
        registerChatMode();
    }

    @Override
    public void onDisable() {
        task.cancel();
        task = null;
        saveMode();
    }

    private void loadMode() {
        try{
            mode = getConfig().getBoolean("mode");
        }catch (NullPointerException e){
            mode = false;
        }
    }

    private void saveMode() {
        getConfig().set("mode", mode);
        this.saveConfig();
    }

    private void reloadFactionsRunnable(){
        task = new BukkitRunnable(){
            @Override
            public void run(){
                FactionData.clearMap();
                for (Player player : Bukkit.getServer().getOnlinePlayers()){
                    FactionData.registerFaction(player);
                }
            }
        }.runTaskTimer(this,20,time);
    }

    private void registerChatMode(){
        FactionData.clearChatMode();
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            FactionData.setChatMode(player,false);
            player.sendMessage( prefix + "§a全体チャットです。");
        }
    }
}
