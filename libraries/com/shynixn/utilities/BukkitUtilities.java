/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.command.ConsoleCommandSender
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package libraries.com.shynixn.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class BukkitUtilities {
    private static BukkitUtilities utilities;

    public static BukkitUtilities u() {
        if (utilities == null) {
            utilities = new BukkitUtilities();
        }
        return utilities;
    }

    private BukkitUtilities() {
    }

    public boolean compareLocation(Location location1, Location location2) {
        if (location1.getBlockX() == location2.getBlockX() && location1.getBlockY() == location2.getBlockY() && location1.getBlockZ() == location2.getBlockZ()) {
            return true;
        }
        return false;
    }

    public List<Player> getOnlinePlayers() {
        ArrayList<Player> players = new ArrayList<Player>();
        for (World world : Bukkit.getWorlds()) {
            for (Player player : world.getPlayers()) {
                players.add(player);
            }
        }
        return players;
    }

    public String[] readAllLines(File file) {
        ArrayList<String> data = new ArrayList<String>();
        try {
            String line;
            if (!file.exists()) {
                file.createNewFile();
            }
            FileReader in = new FileReader(file.getPath());
            BufferedReader br = new BufferedReader(in);
            while ((line = br.readLine()) != null) {
                data.add(line);
            }
            in.close();
        }
        catch (Exception in) {
            // empty catch block
        }
        return data.toArray(new String[data.size()]);
    }

    public boolean writeAllLines(File file, String text) {
        return this.writeAllLines(file, new String[]{text});
    }

    public boolean writeAllLines(File file, String[] text) {
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            int i = 0;
            while (i < text.length) {
                bufferedWriter.write(String.valueOf(text[i]) + "\n");
                ++i;
            }
            bufferedWriter.close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean writeAddLine(File file, String text) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.append(text);
            bufferedWriter.newLine();
            bufferedWriter.close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean writeAllLines(File file, ArrayList<String> text) {
        return this.writeAllLines(file, text.toArray(new String[text.size()]));
    }

    public boolean writeAllLines(File file, List<String> text) {
        return this.writeAllLines(file, text.toArray(new String[text.size()]));
    }

    public void sendColoredConsoleMessage(String message, ChatColor color, String prefix) {
        Bukkit.getServer().getConsoleSender().sendMessage(String.valueOf(prefix) + (Object)color + message);
    }

    public void removeAllPotionEffects(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }

    public void sendfancyValues(Player player, String[] texte, String valuename, ChatColor color) {
        String text = "";
        int i = 0;
        while (i < texte.length) {
            if (texte[i] != null) {
                text = String.valueOf(text) + texte[i];
                if (i + 1 < texte.length) {
                    text = String.valueOf(text) + ", ";
                }
            }
            ++i;
        }
        player.sendMessage((Object)color + (Object)ChatColor.BOLD + (Object)ChatColor.ITALIC + "----- " + valuename + "----- ");
        player.sendMessage((Object)color + text.toLowerCase());
    }

    public boolean saveFolderDeleting(File folder) {
        try {
            if (folder.isDirectory()) {
                if (folder.list().length != 0) {
                    String[] arrstring = folder.list();
                    int n = arrstring.length;
                    int n2 = 0;
                    while (n2 < n) {
                        String s = arrstring[n2];
                        File file = new File(folder, s);
                        if (file.isDirectory()) {
                            this.saveFolderDeleting(file);
                        } else {
                            file.delete();
                        }
                        ++n2;
                    }
                    this.saveFolderDeleting(folder);
                } else {
                    folder.delete();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public Player getPlayerbyUIDD(String uidd) {
        for (Player player : this.getOnlinePlayers()) {
            if (!uidd.equals(player.getUniqueId().toString())) continue;
            return player;
        }
        return null;
    }

    public Player getPlayerByName(String name, ArrayList<Player> players) {
        for (Player player : players) {
            if (!player.getDisplayName().equals(name)) continue;
            return player;
        }
        return null;
    }

    public boolean isInventoryFull(ItemStack[] items) {
        int i = 0;
        while (i < items.length) {
            if (items[i] == null || items[i].getType() == Material.AIR) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public Sound getSoundByName(String name) {
        Sound[] arrsound = Sound.values();
        int n = arrsound.length;
        int n2 = 0;
        while (n2 < n) {
            Sound p = arrsound[n2];
            if (p.toString().equalsIgnoreCase(name)) {
                return p;
            }
            ++n2;
        }
        return null;
    }

    public World getWorldbyName(String name) {
        if (name != null) {
            for (World w : Bukkit.getServer().getWorlds()) {
                if (!w.getName().equalsIgnoreCase(name)) continue;
                return w;
            }
        }
        return null;
    }

    public boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
        }
        catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public boolean tryParseDouble(String value) {
        try {
            Double.parseDouble(value);
        }
        catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void clearChat(Player player) {
        int i = 0;
        while (i < 20) {
            player.sendMessage("");
            ++i;
        }
    }

    public ItemStack nameItem(ItemStack item, String name, String[] lore) {
        ItemMeta im = item.getItemMeta();
        if (name != null && name != "") {
            im.setDisplayName(name);
        }
        if (lore != null) {
            im.setLore(Arrays.asList(lore));
        }
        item.setItemMeta(im);
        return item;
    }

    public ItemStack nameItemDisplay(ItemStack item, String name) {
        ItemMeta im = item.getItemMeta();
        if (name != null && name != "") {
            im.setDisplayName(name);
        }
        item.setItemMeta(im);
        return item;
    }

    public ItemStack nameItem(ItemStack item, String name, List<String> lore) {
        return this.nameItem(item, name, lore.toArray(new String[0]));
    }

    public ItemStack nameItem(ItemStack item, String name, String lore) {
        return this.nameItem(item, name, new String[]{lore});
    }

    public boolean copyFile(InputStream in, File file) {
        try {
            int len;
            FileOutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public File createFile(File file) {
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            return file;
        }
        catch (Exception var2_2) {
            return null;
        }
    }

    public String getStringColorByNumber(int i) {
        ChatColor chatcolor;
        switch (i) {
            case 0: {
                chatcolor = ChatColor.AQUA;
                break;
            }
            case 1: {
                chatcolor = ChatColor.RED;
                break;
            }
            case 2: {
                chatcolor = ChatColor.BLUE;
                break;
            }
            case 3: {
                chatcolor = ChatColor.GOLD;
                break;
            }
            case 4: {
                chatcolor = ChatColor.GREEN;
                break;
            }
            case 5: {
                chatcolor = ChatColor.DARK_PURPLE;
                break;
            }
            case 6: {
                chatcolor = ChatColor.YELLOW;
                break;
            }
            case 7: {
                chatcolor = ChatColor.GRAY;
                break;
            }
            default: {
                chatcolor = ChatColor.DARK_RED;
            }
        }
        return chatcolor.toString();
    }

    public boolean sendServerMessage(String message, List<Player> players) {
        for (Player p : players) {
            p.sendMessage(message);
        }
        return true;
    }

    public void clearCompleteInventory(Player player) {
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.getInventory().clear();
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }
        player.setLevel(0);
        player.setExp(0.0f);
    }

    public boolean isEmpty(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return true;
        }
        return false;
    }

    public boolean compareItemStacks(ItemStack itemStack, ItemStack itemStack2) {
        try {
            return this.compareItemNames(itemStack, itemStack2.getItemMeta().getDisplayName(), itemStack2.getItemMeta().getLore().toArray(new String[itemStack2.getItemMeta().getLore().size()]), itemStack2.getType(), new int[1]);
        }
        catch (Exception e) {
            return false;
        }
    }

    public Enchantment getEnchantMentByName(String name) {
        Enchantment[] arrenchantment = this.getEnchantmentValues();
        int n = arrenchantment.length;
        int n2 = 0;
        while (n2 < n) {
            Enchantment enchantment = arrenchantment[n2];
            if (enchantment.getName().equalsIgnoreCase(name)) {
                return enchantment;
            }
            ++n2;
        }
        return null;
    }

    public PotionEffectType getEffectByName(String name) {
        PotionEffectType[] arrpotionEffectType = this.getEffectValues();
        int n = arrpotionEffectType.length;
        int n2 = 0;
        while (n2 < n) {
            PotionEffectType type = arrpotionEffectType[n2];
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
            ++n2;
        }
        return null;
    }

    public String convertLocationToMessage(Location location) {
        return String.valueOf(location.getBlockX()) + "x " + location.getBlockY() + "y " + location.getBlockZ() + "z.";
    }

    public boolean compareItemNames(ItemStack item, String displayname, String lore, Material material) {
        return this.compareItemNames(item, displayname, new String[]{lore}, material, new int[1]);
    }

    /*
     * Enabled aggressive exception aggregation
     */
    public boolean compareItemNames(ItemStack item, String displayname, String[] lore, Material material, int[] lorelines) {
        try {
            if (!(item == null || material != null && item.getType() != material || item.getType() == Material.AIR || displayname != null && item.getItemMeta().getDisplayName() == null || !item.getItemMeta().getDisplayName().equals(displayname))) {
                if (lore == null) {
                    return true;
                }
                if (lore.length > 0 && item.getItemMeta().getLore() != null) {
                    int i = 0;
                    while (i < item.getItemMeta().getLore().size()) {
                        int[] arrn = lorelines;
                        int n = arrn.length;
                        int n2 = 0;
                        while (n2 < n) {
                            int s = arrn[n2];
                            if (s == i && !((String)item.getItemMeta().getLore().get(i)).equals(lore[i])) {
                                return false;
                            }
                            ++n2;
                        }
                        ++i;
                    }
                    return true;
                }
            }
            return false;
        }
        catch (Exception e) {
            return false;
        }
    }

    public String getCardinalDirection(Entity entity) {
        double rotation = (entity.getLocation().getYaw() - 90.0f) % 360.0f;
        if (rotation < 0.0) {
            rotation += 360.0;
        }
        if (0.0 <= rotation && rotation < 22.5) {
            return "N";
        }
        if (22.5 <= rotation && rotation < 67.5) {
            return "NE";
        }
        if (67.5 <= rotation && rotation < 112.5) {
            return "E";
        }
        if (112.5 <= rotation && rotation < 157.5) {
            return "SE";
        }
        if (157.5 <= rotation && rotation < 202.5) {
            return "S";
        }
        if (202.5 <= rotation && rotation < 247.5) {
            return "SW";
        }
        if (247.5 <= rotation && rotation < 292.5) {
            return "W";
        }
        if (292.5 <= rotation && rotation < 337.5) {
            return "NW";
        }
        if (337.5 <= rotation && rotation < 360.0) {
            return "N";
        }
        return null;
    }

    public boolean compareEnchantments(ItemStack item, ItemStack item2) {
        Enchantment[] keys2;
        Map enchant1 = item.getEnchantments();
        Map enchant2 = item2.getEnchantments();
        if (enchant1 == null || enchant2 == null) {
            return false;
        }
        Enchantment[] keys1 = enchant1.keySet().toArray((T[])new Enchantment[enchant1.keySet().size()]);
        if (keys1.length != (keys2 = enchant2.keySet().toArray((T[])new Enchantment[enchant2.keySet().size()])).length) {
            return false;
        }
        int i = 0;
        while (i < keys1.length) {
            int j = 0;
            while (j < keys2.length) {
                if (keys1[i].equals((Object)keys2[j]) && enchant1.get((Object)keys1[i]) != enchant2.get((Object)keys2[j])) {
                    return false;
                }
                ++j;
            }
            ++i;
        }
        return true;
    }

    public Player[] getPlayersByNames(String[] names) {
        ArrayList<Player> players = new ArrayList<Player>();
        int i = 0;
        while (i < names.length) {
            if (this.getPlayerbyName(names[i]) != null) {
                players.add(this.getPlayerbyName(names[i]));
            }
            ++i;
        }
        return players.toArray((T[])new Player[players.size()]);
    }

    public Player getPlayerbyName(String name) {
        for (Player player : this.getOnlinePlayers()) {
            if (!name.equals(player.getName())) continue;
            return player;
        }
        return null;
    }

    public boolean addSaveInventory(ItemStack[] items, ItemStack item) {
        int i = 0;
        while (i < items.length) {
            if (items[i] == null || items[i].getType() == Material.AIR) {
                items[i] = item;
                return true;
            }
            ++i;
        }
        return false;
    }

    public PotionEffectType[] getEffectValues() {
        ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>();
        types.add(PotionEffectType.ABSORPTION);
        types.add(PotionEffectType.BLINDNESS);
        types.add(PotionEffectType.CONFUSION);
        types.add(PotionEffectType.DAMAGE_RESISTANCE);
        types.add(PotionEffectType.FAST_DIGGING);
        types.add(PotionEffectType.FIRE_RESISTANCE);
        types.add(PotionEffectType.HARM);
        types.add(PotionEffectType.HEAL);
        types.add(PotionEffectType.HEALTH_BOOST);
        types.add(PotionEffectType.HUNGER);
        types.add(PotionEffectType.INCREASE_DAMAGE);
        types.add(PotionEffectType.INVISIBILITY);
        types.add(PotionEffectType.JUMP);
        types.add(PotionEffectType.NIGHT_VISION);
        types.add(PotionEffectType.POISON);
        types.add(PotionEffectType.REGENERATION);
        types.add(PotionEffectType.SATURATION);
        types.add(PotionEffectType.SLOW);
        types.add(PotionEffectType.SLOW_DIGGING);
        types.add(PotionEffectType.SPEED);
        types.add(PotionEffectType.WATER_BREATHING);
        types.add(PotionEffectType.WEAKNESS);
        types.add(PotionEffectType.WITHER);
        return types.toArray((T[])new PotionEffectType[types.size()]);
    }

    public EntityType[] getLivingEntityValues() {
        ArrayList<EntityType> types = new ArrayList<EntityType>();
        types.add(EntityType.BAT);
        types.add(EntityType.BLAZE);
        types.add(EntityType.BOAT);
        types.add(EntityType.CAVE_SPIDER);
        types.add(EntityType.CHICKEN);
        types.add(EntityType.COW);
        types.add(EntityType.CREEPER);
        types.add(EntityType.ENDER_DRAGON);
        types.add(EntityType.ENDERMAN);
        types.add(EntityType.ENDERMITE);
        types.add(EntityType.GHAST);
        types.add(EntityType.GIANT);
        types.add(EntityType.GUARDIAN);
        types.add(EntityType.HORSE);
        types.add(EntityType.IRON_GOLEM);
        types.add(EntityType.MAGMA_CUBE);
        types.add(EntityType.MINECART);
        types.add(EntityType.MINECART_CHEST);
        types.add(EntityType.MINECART_COMMAND);
        types.add(EntityType.MINECART_FURNACE);
        types.add(EntityType.MINECART_HOPPER);
        types.add(EntityType.MINECART_MOB_SPAWNER);
        types.add(EntityType.MINECART_TNT);
        types.add(EntityType.MUSHROOM_COW);
        types.add(EntityType.OCELOT);
        types.add(EntityType.PIG);
        types.add(EntityType.PIG_ZOMBIE);
        types.add(EntityType.RABBIT);
        types.add(EntityType.SHEEP);
        types.add(EntityType.SILVERFISH);
        types.add(EntityType.SKELETON);
        types.add(EntityType.SLIME);
        types.add(EntityType.SNOWMAN);
        types.add(EntityType.SPIDER);
        types.add(EntityType.SQUID);
        types.add(EntityType.VILLAGER);
        types.add(EntityType.WITCH);
        types.add(EntityType.WITHER);
        types.add(EntityType.ZOMBIE);
        return types.toArray((T[])new EntityType[types.size()]);
    }

    public String[] getEffectNames() {
        ArrayList<String> effectnames = new ArrayList<String>();
        PotionEffectType[] arrpotionEffectType = this.getEffectValues();
        int n = arrpotionEffectType.length;
        int n2 = 0;
        while (n2 < n) {
            PotionEffectType potionEffectType = arrpotionEffectType[n2];
            effectnames.add(potionEffectType.getName());
            ++n2;
        }
        return effectnames.toArray(new String[effectnames.size()]);
    }

    public Enchantment[] getEnchantmentValues() {
        ArrayList<Enchantment> enchantments = new ArrayList<Enchantment>();
        enchantments.add(Enchantment.ARROW_DAMAGE);
        enchantments.add(Enchantment.ARROW_FIRE);
        enchantments.add(Enchantment.ARROW_INFINITE);
        enchantments.add(Enchantment.ARROW_KNOCKBACK);
        enchantments.add(Enchantment.DAMAGE_ALL);
        enchantments.add(Enchantment.DAMAGE_ARTHROPODS);
        enchantments.add(Enchantment.DAMAGE_UNDEAD);
        enchantments.add(Enchantment.DIG_SPEED);
        enchantments.add(Enchantment.DURABILITY);
        enchantments.add(Enchantment.FIRE_ASPECT);
        enchantments.add(Enchantment.KNOCKBACK);
        enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
        enchantments.add(Enchantment.LOOT_BONUS_MOBS);
        enchantments.add(Enchantment.LUCK);
        enchantments.add(Enchantment.LURE);
        enchantments.add(Enchantment.OXYGEN);
        enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
        enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
        enchantments.add(Enchantment.PROTECTION_FALL);
        enchantments.add(Enchantment.PROTECTION_FIRE);
        enchantments.add(Enchantment.PROTECTION_PROJECTILE);
        enchantments.add(Enchantment.SILK_TOUCH);
        enchantments.add(Enchantment.THORNS);
        enchantments.add(Enchantment.WATER_WORKER);
        return enchantments.toArray((T[])new Enchantment[enchantments.size()]);
    }

    public String[] getEnchantmentNames() {
        ArrayList<String> enchantmentnames = new ArrayList<String>();
        Enchantment[] arrenchantment = this.getEnchantmentValues();
        int n = arrenchantment.length;
        int n2 = 0;
        while (n2 < n) {
            Enchantment enchantment = arrenchantment[n2];
            enchantmentnames.add(enchantment.getName());
            ++n2;
        }
        return enchantmentnames.toArray(new String[enchantmentnames.size()]);
    }
}

