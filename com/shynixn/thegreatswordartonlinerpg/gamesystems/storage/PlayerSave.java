/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.storage;

import com.shynixn.thegreatswordartonlinerpg.resources.enums.InventoryType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import libraries.com.shynixn.utilities.BukkitLocation;
import libraries.com.shynixn.utilities.BukkitObject;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class PlayerSave
extends BukkitObject {
    private static final long serialVersionUID = 1;
    private ItemStack[] skillbarContents = new ItemStack[36];
    private ItemStack[][][] eqipmentContents = new ItemStack[5][4][36];
    private BukkitLocation lastLocation = null;
    private int onlineMClevel = 0;
    private float onlineMCexp = 0.0f;
    private double onlineHealth = 20.0;
    private double onlineHunger = 20.0;
    private ItemStack[] onlineContents = new ItemStack[36];
    private ItemStack[] onlineearmorContents = new ItemStack[4];
    private List<Integer> accessToFloors = new ArrayList<Integer>();
    private int floorId = -1;
    private String raceName = "";
    private List<Player> partyMembers = new ArrayList<Player>();
    private int offlinelevel;
    private float offlineexp;
    private double offlineHealth = 20.0;
    private double offlineHunger = 20.0;
    private ItemStack[] offlinecontents;
    private ItemStack[] offlinearmorContents;

    public PlayerSave(String uuid) {
        super(uuid, uuid);
    }

    public void addPartyMember(Player player) {
        if (!this.partyMembers.contains((Object)player)) {
            this.partyMembers.add(player);
        }
    }

    public void removePartyMember(Player player) {
        if (this.partyMembers.contains((Object)player)) {
            this.partyMembers.remove((Object)player);
        }
    }

    public List<Player> getPartyMembers() {
        return Arrays.asList(this.partyMembers.toArray((T[])new Player[this.partyMembers.size()]));
    }

    public void addAccess(int floorid) {
        if (!this.accessToFloors.contains(floorid)) {
            this.accessToFloors.add(floorid);
        }
    }

    public void removeAccess(int floorid) {
        if (this.accessToFloors.contains(floorid)) {
            this.accessToFloors.remove(floorid);
        }
    }

    public List<Integer> getAccessFloors() {
        return Arrays.asList(this.accessToFloors.toArray(new Integer[this.accessToFloors.size()]));
    }

    public String getFormatedFloorText() {
        String text = "";
        for (Integer i : this.accessToFloors) {
            text = String.valueOf(text) + String.valueOf(i) + ";";
        }
        return text;
    }

    public void setFormatedFloorText(String text) {
        try {
            String[] arrstring = text.split(";");
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String p = arrstring[n2];
                if (BukkitUtilities.u().tryParseInt(p)) {
                    this.accessToFloors.add(Integer.parseInt(p));
                }
                ++n2;
            }
        }
        catch (Exception p) {
            // empty catch block
        }
    }

    public BukkitLocation getLastLocation() {
        return this.lastLocation;
    }

    public void setLastLocation(BukkitLocation lastLocation) {
        this.lastLocation = lastLocation;
    }

    public ItemStack[] getSkillbarContents() {
        return this.skillbarContents;
    }

    public void setSkillbarContents(ItemStack[] skillbarContents) {
        this.skillbarContents = skillbarContents;
    }

    public int getOnlineMClevel() {
        return this.onlineMClevel;
    }

    public void setOnlineMClevel(int onlineMClevel) {
        this.onlineMClevel = onlineMClevel;
    }

    public float getOnlineMCexp() {
        return this.onlineMCexp;
    }

    public void setOnlineMCexp(float onlineMCexp) {
        this.onlineMCexp = onlineMCexp;
    }

    public ItemStack[] getOnlineContents() {
        return this.onlineContents;
    }

    public void setOnlineContents(ItemStack[] onlineContents) {
        this.onlineContents = onlineContents;
    }

    public ItemStack[] getOnlineearmorContents() {
        return this.onlineearmorContents;
    }

    public void setOnlineearmorContents(ItemStack[] onlineearmorContents) {
        this.onlineearmorContents = onlineearmorContents;
    }

    public int getOfflinelevel() {
        return this.offlinelevel;
    }

    public void setOfflinelevel(int offlinelevel) {
        this.offlinelevel = offlinelevel;
    }

    public float getOfflineexp() {
        return this.offlineexp;
    }

    public void setOfflineexp(float offlineexp) {
        this.offlineexp = offlineexp;
    }

    public ItemStack[] getOfflinecontents() {
        return this.offlinecontents;
    }

    public void setOfflinecontents(ItemStack[] offlinecontents) {
        this.offlinecontents = offlinecontents;
    }

    public ItemStack[] getOfflinearmorContents() {
        return this.offlinearmorContents;
    }

    public void setOfflinearmorContents(ItemStack[] offlinearmorContents) {
        this.offlinearmorContents = offlinearmorContents;
    }

    public void setContents(ItemStack[] contents, InventoryType inventoryType) {
        if (inventoryType == InventoryType.STANDARD) {
            this.onlineContents = contents;
        } else if (inventoryType == InventoryType.SKILLS) {
            this.skillbarContents = contents;
        } else if (InventoryType.isEquipMentArmor(inventoryType)) {
            this.eqipmentContents[0][inventoryType.getPage() - 1] = contents;
        } else if (InventoryType.isEquipMentWeapon(inventoryType)) {
            this.eqipmentContents[1][inventoryType.getPage() - 1] = contents;
        } else if (InventoryType.isEquipMentMaterial(inventoryType)) {
            this.eqipmentContents[2][inventoryType.getPage() - 1] = contents;
        } else if (InventoryType.isEquipMentFood(inventoryType)) {
            this.eqipmentContents[3][inventoryType.getPage() - 1] = contents;
        } else if (InventoryType.isEquipMentDrop(inventoryType)) {
            this.eqipmentContents[4][inventoryType.getPage() - 1] = contents;
        }
    }

    public ItemStack[] getContents(InventoryType inventoryType) {
        if (inventoryType == InventoryType.STANDARD) {
            return this.onlineContents;
        }
        if (inventoryType == InventoryType.SKILLS) {
            return this.skillbarContents;
        }
        if (inventoryType == InventoryType.MENU) {
            return null;
        }
        if (InventoryType.isEquipMentArmor(inventoryType)) {
            return this.eqipmentContents[0][inventoryType.getPage() - 1];
        }
        if (InventoryType.isEquipMentWeapon(inventoryType)) {
            return this.eqipmentContents[1][inventoryType.getPage() - 1];
        }
        if (InventoryType.isEquipMentMaterial(inventoryType)) {
            return this.eqipmentContents[2][inventoryType.getPage() - 1];
        }
        if (InventoryType.isEquipMentFood(inventoryType)) {
            return this.eqipmentContents[3][inventoryType.getPage() - 1];
        }
        if (InventoryType.isEquipMentDrop(inventoryType)) {
            return this.eqipmentContents[4][inventoryType.getPage() - 1];
        }
        return null;
    }

    public void reset() {
        this.skillbarContents = new ItemStack[36];
        this.eqipmentContents = new ItemStack[5][4][36];
        this.lastLocation = null;
        this.onlineMClevel = 0;
        this.onlineMCexp = 0.0f;
        this.onlineContents = new ItemStack[36];
        this.onlineearmorContents = new ItemStack[4];
        this.accessToFloors.clear();
    }

    public int getFloorId() {
        return this.floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    public double getOnlineHealth() {
        return this.onlineHealth;
    }

    public void setOnlineHealth(double onlineHealth) {
        this.onlineHealth = onlineHealth;
    }

    public double getOnlineHunger() {
        return this.onlineHunger;
    }

    public void setOnlineHunger(double onlineHunger) {
        this.onlineHunger = onlineHunger;
    }

    public double getOfflineHealth() {
        return this.offlineHealth;
    }

    public void setOfflineHealth(double offlineHealth) {
        this.offlineHealth = offlineHealth;
    }

    public double getOfflineHunger() {
        return this.offlineHunger;
    }

    public void setOfflineHunger(double offlineHunger) {
        this.offlineHunger = offlineHunger;
    }

    public String getRaceName() {
        return this.raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }
}

