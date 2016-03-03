/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package libraries.com.shynixn.utilities;

import java.util.Collection;
import java.util.HashMap;
import libraries.com.shynixn.utilities.BukkitChatColor;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class BukkitSelector {
    private static BukkitSelector selector;
    private HashMap<Integer, Selector> selectors = new HashMap();

    public static BukkitSelector selector() {
        return selector;
    }

    public static void generate(JavaPlugin plugin) {
        if (selector == null) {
            selector = new BukkitSelector(plugin);
        }
    }

    private BukkitSelector(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents((Listener)new SelectListener(this, null), (Plugin)plugin);
    }

    public ItemStack getSelector(int id) {
        if (this.selectors.containsKey(id)) {
            Selector selector = this.selectors.get(id);
            return BukkitUtilities.u().nameItem(new ItemStack(selector.getMaterial()), selector.getName(), new String[]{selector.getLore()});
        }
        return null;
    }

    public void registerSelector(int id, Material material, String name, String lore) {
        Selector selector = new Selector(name, lore, material);
        if (this.selectors.containsKey(id)) {
            throw new IllegalArgumentException("Selector is already used!");
        }
        this.selectors.put(id, selector);
    }

    public Location getSelection(Player player, int id, SelectionType type) {
        if (this.selectors.containsKey(id)) {
            if (type == SelectionType.LEFT && this.selectors.get(id).getLeftSelection().containsKey((Object)player)) {
                return this.selectors.get(id).getLeftSelection().get((Object)player);
            }
            if (type == SelectionType.RIGHT && this.selectors.get(id).getRightSelection().containsKey((Object)player)) {
                return this.selectors.get(id).getRightSelection().get((Object)player);
            }
        }
        return null;
    }

    private class SelectListener
    implements Listener {
        final /* synthetic */ BukkitSelector this$0;

        private SelectListener(BukkitSelector bukkitSelector) {
            this.this$0 = bukkitSelector;
        }

        @EventHandler
        public void onInteract(PlayerInteractEvent e) {
            for (Selector selector : this.this$0.selectors.values()) {
                if (!BukkitUtilities.u().compareItemNames(e.getPlayer().getItemInHand(), selector.getName(), new String[]{selector.getLore()}, selector.getMaterial(), new int[1])) continue;
                if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    selector.rightSelection.put(e.getPlayer(), e.getClickedBlock().getLocation());
                    e.getPlayer().sendMessage((Object)((Object)BukkitChatColor.GOLD) + "[" + selector.getName() + (Object)((Object)BukkitChatColor.GOLD) + "] " + (Object)ChatColor.YELLOW + "Rightselection: " + e.getClickedBlock().getLocation().getBlockX() + "x " + e.getClickedBlock().getLocation().getBlockY() + "y " + e.getClickedBlock().getLocation().getBlockZ() + "z");
                    e.setCancelled(true);
                    continue;
                }
                if (e.getAction() != Action.LEFT_CLICK_BLOCK) continue;
                selector.leftSelection.put(e.getPlayer(), e.getClickedBlock().getLocation());
                e.getPlayer().sendMessage((Object)((Object)BukkitChatColor.GOLD) + "[" + selector.getName() + (Object)((Object)BukkitChatColor.GOLD) + "] " + (Object)ChatColor.YELLOW + "Leftselection: " + e.getClickedBlock().getLocation().getBlockX() + "x " + e.getClickedBlock().getLocation().getBlockY() + "y " + e.getClickedBlock().getLocation().getBlockZ() + "z");
                e.setCancelled(true);
            }
        }

        /* synthetic */ SelectListener(BukkitSelector bukkitSelector, SelectListener selectListener) {
            SelectListener selectListener2;
            selectListener2(bukkitSelector);
        }
    }

    public static enum SelectionType {
        RIGHT,
        LEFT;
        

        private SelectionType(String string2, int n2) {
        }
    }

    private class Selector {
        private Material material;
        private String name;
        private String lore;
        private HashMap<Player, Location> rightSelection;
        private HashMap<Player, Location> leftSelection;

        public Selector(String name, String lore, Material material) {
            this.rightSelection = new HashMap();
            this.leftSelection = new HashMap();
            this.name = name;
            this.lore = lore;
            this.material = material;
        }

        public Material getMaterial() {
            return this.material;
        }

        public String getName() {
            return this.name;
        }

        public String getLore() {
            return this.lore;
        }

        public HashMap<Player, Location> getRightSelection() {
            return this.rightSelection;
        }

        public HashMap<Player, Location> getLeftSelection() {
            return this.leftSelection;
        }
    }

}

