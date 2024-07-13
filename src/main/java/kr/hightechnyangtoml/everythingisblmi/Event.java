package kr.hightechnyangtoml.everythingisblmi;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Event implements Listener {


    @EventHandler
    public void onPing(PaperServerListPingEvent e) {
        e.motd(Component.text("You are " + Reference.target + "!!!!"));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        PlayerProfile playerProfile = player.getPlayerProfile();

        try {
            URL url_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + Reference.target);

            InputStreamReader reader_0 = new InputStreamReader(url_0.openStream());

            String uuid = JsonParser.parseReader(reader_0).getAsJsonObject().get("id").getAsString();

            URL url_1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");

            InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());

            JsonObject properties = JsonParser.parseReader(reader_1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();

            String value = properties.get("value").getAsString();
            String signature = properties.get("signature").getAsString();

            playerProfile.setProperty(new ProfileProperty("textures", value, signature));
            player.setPlayerProfile(playerProfile);

            player.setDisplayName(Reference.target);

            player.sendMessage("You are " + Reference.target + " now!!!!");
        } catch (IllegalStateException | IOException | NullPointerException exception)
        {
            player.sendMessage("Failed to set skin to "+ Reference.target + ".");
        }

        e.joinMessage(Component.text("§e" + Reference.target + " has joined the game"));
        player.setPlayerListName(Reference.target);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        e.setDropItems(false);
        e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), createSkull(Reference.target));
    }

    private ItemStack createSkull(String p) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(p));
        head.setItemMeta(meta);
        return head;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setMessage("I'm " + Reference.target + ".");
    }
}
