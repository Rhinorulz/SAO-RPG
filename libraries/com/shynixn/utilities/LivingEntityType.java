/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.EntityType
 */
package libraries.com.shynixn.utilities;

import org.bukkit.entity.EntityType;

public enum LivingEntityType {
    BAT(EntityType.BAT),
    BLAZE(EntityType.BLAZE),
    BOAT(EntityType.BOAT),
    CAVE_SPIDER(EntityType.CAVE_SPIDER),
    CHICKEN(EntityType.CHICKEN),
    COW(EntityType.COW),
    CREEPER(EntityType.CREEPER),
    ENDER_DRAGON(EntityType.ENDER_DRAGON),
    ENDERMAN(EntityType.ENDERMAN),
    ENDERMITE(EntityType.ENDERMITE),
    GHAST(EntityType.GHAST),
    GIANT(EntityType.GIANT),
    GUARDIAN(EntityType.GUARDIAN),
    HORSE(EntityType.HORSE),
    IRON_GOLEM(EntityType.IRON_GOLEM),
    MAGMA_CUBE(EntityType.MAGMA_CUBE),
    MINECART(EntityType.MINECART),
    MINECART_CHEST(EntityType.MINECART_CHEST),
    MINECART_COMMAND(EntityType.MINECART_COMMAND),
    MINECART_FURNACE(EntityType.MINECART_FURNACE),
    MINECART_HOPPER(EntityType.MINECART_HOPPER),
    MINECART_MOB_SPAWNER(EntityType.MINECART_MOB_SPAWNER),
    MINECART_TNT(EntityType.MINECART_TNT),
    MUSHROOM_COW(EntityType.MUSHROOM_COW),
    OCELOT(EntityType.OCELOT),
    PIG(EntityType.PIG),
    PIG_ZOMBIE(EntityType.PIG_ZOMBIE),
    RABBIT(EntityType.RABBIT),
    SHEEP(EntityType.SHEEP),
    SILVERFISH(EntityType.SILVERFISH),
    SKELETON(EntityType.SKELETON),
    SLIME(EntityType.SLIME),
    SNOWMAN(EntityType.SNOWMAN),
    SPIDER(EntityType.SPIDER),
    SQUID(EntityType.SQUID),
    VILLAGER(EntityType.VILLAGER),
    WITCH(EntityType.WITCH),
    WITHER(EntityType.WITHER),
    WOLF(EntityType.WOLF),
    ZOMBIE(EntityType.ZOMBIE);
    
    private EntityType entityType;

    private LivingEntityType(String entityType, int n2, EntityType entityType2) {
        this.entityType = entityType;
    }

    public EntityType entity() {
        return this.entityType;
    }

    public static EntityType getTypeFromName(String name) {
        LivingEntityType[] arrlivingEntityType = LivingEntityType.values();
        int n = arrlivingEntityType.length;
        int n2 = 0;
        while (n2 < n) {
            LivingEntityType entityType = arrlivingEntityType[n2];
            if (entityType.entity().name().equalsIgnoreCase(name)) {
                return entityType.entity();
            }
            ++n2;
        }
        return null;
    }
}

