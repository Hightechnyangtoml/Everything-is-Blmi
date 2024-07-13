package kr.hightechnyangtoml.everythingisblmi;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class EverythingIsBlmi extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("You are " + Reference.target + "! hehe...");
        Bukkit.getPluginManager().registerEvents(new Event(), this);
    }

    @Override
    public void onDisable() {
        System.out.println("You are not " + Reference.target + "! get out.");
    }
}
