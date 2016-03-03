/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R3.EntityBat
 *  net.minecraft.server.v1_8_R3.EntityBlaze
 *  net.minecraft.server.v1_8_R3.EntityCaveSpider
 *  net.minecraft.server.v1_8_R3.EntityChicken
 *  net.minecraft.server.v1_8_R3.EntityCow
 *  net.minecraft.server.v1_8_R3.EntityCreeper
 *  net.minecraft.server.v1_8_R3.EntityEnderDragon
 *  net.minecraft.server.v1_8_R3.EntityEnderman
 *  net.minecraft.server.v1_8_R3.EntityEndermite
 *  net.minecraft.server.v1_8_R3.EntityGhast
 *  net.minecraft.server.v1_8_R3.EntityGiantZombie
 *  net.minecraft.server.v1_8_R3.EntityGuardian
 *  net.minecraft.server.v1_8_R3.EntityHorse
 *  net.minecraft.server.v1_8_R3.EntityInsentient
 *  net.minecraft.server.v1_8_R3.EntityIronGolem
 *  net.minecraft.server.v1_8_R3.EntityMagmaCube
 *  net.minecraft.server.v1_8_R3.EntityMushroomCow
 *  net.minecraft.server.v1_8_R3.EntityOcelot
 *  net.minecraft.server.v1_8_R3.EntityPig
 *  net.minecraft.server.v1_8_R3.EntityPigZombie
 *  net.minecraft.server.v1_8_R3.EntityRabbit
 *  net.minecraft.server.v1_8_R3.EntitySheep
 *  net.minecraft.server.v1_8_R3.EntitySilverfish
 *  net.minecraft.server.v1_8_R3.EntitySkeleton
 *  net.minecraft.server.v1_8_R3.EntitySlime
 *  net.minecraft.server.v1_8_R3.EntitySnowman
 *  net.minecraft.server.v1_8_R3.EntitySpider
 *  net.minecraft.server.v1_8_R3.EntitySquid
 *  net.minecraft.server.v1_8_R3.EntityTypes
 *  net.minecraft.server.v1_8_R3.EntityVillager
 *  net.minecraft.server.v1_8_R3.EntityWitch
 *  net.minecraft.server.v1_8_R3.EntityWither
 *  net.minecraft.server.v1_8_R3.EntityWolf
 *  net.minecraft.server.v1_8_R3.EntityZombie
 *  org.bukkit.World
 */
package com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3;

import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobEquipment;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.MobRegistry;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.SaoMob;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoBat;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoBlaze;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoCaveSpider;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoChicken;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoCow;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoCreeper;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoEnderDragon;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoEnderman;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoEndermite;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoGhast;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoGiant;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoGuardian;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoHorse;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoIronGolem;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoMagmaCube;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoMushroomCow;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoOcelot;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoPig;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoPigZombie;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoRabbit;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoSheep;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoSilverFish;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoSkeleton;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoSlime;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoSnowGolem;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoSpider;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoSquid;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoVillager;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoWitch;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoWither;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoWolf;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R3.mobs.SaoZombie;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.server.v1_8_R3.EntityBat;
import net.minecraft.server.v1_8_R3.EntityBlaze;
import net.minecraft.server.v1_8_R3.EntityCaveSpider;
import net.minecraft.server.v1_8_R3.EntityChicken;
import net.minecraft.server.v1_8_R3.EntityCow;
import net.minecraft.server.v1_8_R3.EntityCreeper;
import net.minecraft.server.v1_8_R3.EntityEnderDragon;
import net.minecraft.server.v1_8_R3.EntityEnderman;
import net.minecraft.server.v1_8_R3.EntityEndermite;
import net.minecraft.server.v1_8_R3.EntityGhast;
import net.minecraft.server.v1_8_R3.EntityGiantZombie;
import net.minecraft.server.v1_8_R3.EntityGuardian;
import net.minecraft.server.v1_8_R3.EntityHorse;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityIronGolem;
import net.minecraft.server.v1_8_R3.EntityMagmaCube;
import net.minecraft.server.v1_8_R3.EntityMushroomCow;
import net.minecraft.server.v1_8_R3.EntityOcelot;
import net.minecraft.server.v1_8_R3.EntityPig;
import net.minecraft.server.v1_8_R3.EntityPigZombie;
import net.minecraft.server.v1_8_R3.EntityRabbit;
import net.minecraft.server.v1_8_R3.EntitySheep;
import net.minecraft.server.v1_8_R3.EntitySilverfish;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.EntitySlime;
import net.minecraft.server.v1_8_R3.EntitySnowman;
import net.minecraft.server.v1_8_R3.EntitySpider;
import net.minecraft.server.v1_8_R3.EntitySquid;
import net.minecraft.server.v1_8_R3.EntityTypes;
import net.minecraft.server.v1_8_R3.EntityVillager;
import net.minecraft.server.v1_8_R3.EntityWitch;
import net.minecraft.server.v1_8_R3.EntityWither;
import net.minecraft.server.v1_8_R3.EntityWolf;
import net.minecraft.server.v1_8_R3.EntityZombie;
import org.bukkit.World;

public enum CustomMobFacility implements MobRegistry
{
    Creeper(EntityCreeper.class, "Creeper", 50),
    Skeleton(EntitySkeleton.class, "Skeleton", 51),
    Spider(EntitySpider.class, "Spider", 52),
    Giant(EntityGiantZombie.class, "Giant", 53),
    Zombie(EntityZombie.class, "Zombie", 54),
    Slime(EntitySlime.class, "Slime", 55),
    Ghast(EntityGhast.class, "Ghast", 56),
    PigZombie(EntityPigZombie.class, "PigZombie", 57),
    Enderman(EntityEnderman.class, "Enderman", 58),
    CaveSpider(EntityCaveSpider.class, "CaveSpider", 59),
    Silverfish(EntitySilverfish.class, "Silverfish", 60),
    Blaze(EntityBlaze.class, "Blaze", 61),
    MagmaCube(EntityMagmaCube.class, "LavaSlime", 62),
    EnderDragon(EntityEnderDragon.class, "EnderDragon", 63),
    Wither(EntityWither.class, "WitherBoss", 64),
    Bat(EntityBat.class, "Bat", 65),
    Witch(EntityWitch.class, "Witch", 66),
    Pig(EntityPig.class, "Pig", 90),
    Sheep(EntitySheep.class, "Sheep", 91),
    Cow(EntityCow.class, "Cow", 92),
    Chicken(EntityChicken.class, "Chicken", 93),
    Squid(EntitySquid.class, "Squid", 94),
    Wolf(EntityWolf.class, "Wolf", 95),
    MushroomCow(EntityMushroomCow.class, "MushroomCow", 96),
    Snowman(EntitySnowman.class, "SnowMan", 97),
    Ocelot(EntityOcelot.class, "Ozelot", 98),
    IronGolem(EntityIronGolem.class, "VillagerGolem", 99),
    Horse(EntityHorse.class, "EntityHorse", 100),
    Villager(EntityVillager.class, "Villager", 120),
    Guardian(EntityGuardian.class, "Guardian", 68),
    Rabbit(EntityRabbit.class, "Rabbit", 101),
    Endermite(EntityEndermite.class, "Endermite", 67);
    
    private Class<? extends EntityInsentient> class1;
    private String name;
    private int id;

    private CustomMobFacility(Class<? extends EntityInsentient> classe, String name, int id) {
        this.class1 = classe;
        this.name = name;
        this.id = id;
    }

    private void register(Class<? extends EntityInsentient> insentient) {
        new CustomEntityType(this.name, this.id, this.class1, insentient).registerEntity();
    }

    @Override
    public void unregisterAll() {
        CustomEntityType.unregisterEntities();
    }

    @Override
    public SaoMob createMob(World world, String type, MobEquipment equipment) {
        if (type.equalsIgnoreCase("Bat")) {
            return new SaoBat(world, equipment);
        }
        if (type.equalsIgnoreCase("Blaze")) {
            return new SaoBlaze(world, equipment);
        }
        if (type.equalsIgnoreCase("CaveSpider") || type.equalsIgnoreCase("Cave_Spider")) {
            return new SaoCaveSpider(world, equipment);
        }
        if (type.equalsIgnoreCase("Chicken")) {
            return new SaoChicken(world, equipment);
        }
        if (type.equalsIgnoreCase("Cow")) {
            return new SaoCow(world, equipment);
        }
        if (type.equalsIgnoreCase("Creeper")) {
            return new SaoCreeper(world, equipment);
        }
        if (type.equalsIgnoreCase("EnderDragon") || type.equalsIgnoreCase("Ender_Dragon")) {
            return new SaoEnderDragon(world, equipment);
        }
        if (type.equalsIgnoreCase("Enderman")) {
            return new SaoEnderman(world, equipment);
        }
        if (type.equalsIgnoreCase("Endermite")) {
            return new SaoEndermite(world, equipment);
        }
        if (type.equalsIgnoreCase("Ghast")) {
            return new SaoGhast(world, equipment);
        }
        if (type.equalsIgnoreCase("Giant")) {
            return new SaoGiant(world, equipment);
        }
        if (type.equalsIgnoreCase("Guardian")) {
            return new SaoGuardian(world, equipment);
        }
        if (type.equalsIgnoreCase("Horse") || type.equalsIgnoreCase("EntityHorse")) {
            return new SaoHorse(world, equipment);
        }
        if (type.equalsIgnoreCase("IronGolem") || type.equalsIgnoreCase("Iron_Golem")) {
            return new SaoIronGolem(world, equipment);
        }
        if (type.equalsIgnoreCase("MagmaCube") || type.equalsIgnoreCase("Magma_Cube")) {
            return new SaoMagmaCube(world, equipment);
        }
        if (type.equalsIgnoreCase("MushroomCow") || type.equalsIgnoreCase("Mushroom_COW")) {
            return new SaoMushroomCow(world, equipment);
        }
        if (type.equalsIgnoreCase("Ocelot")) {
            return new SaoOcelot(world, equipment);
        }
        if (type.equalsIgnoreCase("Pig")) {
            return new SaoPig(world, equipment);
        }
        if (type.equalsIgnoreCase("PigZombie") || type.equalsIgnoreCase("Pig_Zombie")) {
            return new SaoPigZombie(world, equipment);
        }
        if (type.equalsIgnoreCase("Rabbit")) {
            return new SaoRabbit(world, equipment);
        }
        if (type.equalsIgnoreCase("Sheep")) {
            return new SaoSheep(world, equipment);
        }
        if (type.equalsIgnoreCase("Silverfish")) {
            return new SaoSilverFish(world, equipment);
        }
        if (type.equalsIgnoreCase("Skeleton")) {
            return new SaoSkeleton(world, equipment);
        }
        if (type.equalsIgnoreCase("Slime")) {
            return new SaoSlime(world, equipment);
        }
        if (type.equalsIgnoreCase("Snowman")) {
            return new SaoSnowGolem(world, equipment);
        }
        if (type.equalsIgnoreCase("Spider")) {
            return new SaoSpider(world, equipment);
        }
        if (type.equalsIgnoreCase("Squid")) {
            return new SaoSquid(world, equipment);
        }
        if (type.equalsIgnoreCase("Villager")) {
            return new SaoVillager(world, equipment);
        }
        if (type.equalsIgnoreCase("Witch")) {
            return new SaoWitch(world, equipment);
        }
        if (type.equalsIgnoreCase("Wither")) {
            return new SaoWither(world, equipment);
        }
        if (type.equalsIgnoreCase("Wolf")) {
            return new SaoWolf(world, equipment);
        }
        if (type.equalsIgnoreCase("Zombie")) {
            return new SaoZombie(world, equipment);
        }
        return null;
    }

    @Override
    public void registerAll() {
        Bat.register(SaoBat.class);
        Blaze.register(SaoBlaze.class);
        CaveSpider.register(SaoCaveSpider.class);
        Chicken.register(SaoChicken.class);
        Cow.register(SaoCow.class);
        Creeper.register(SaoCreeper.class);
        EnderDragon.register(SaoEnderDragon.class);
        Enderman.register(SaoEnderman.class);
        Endermite.register(SaoEndermite.class);
        Ghast.register(SaoGhast.class);
        Giant.register(SaoGiant.class);
        Guardian.register(SaoGuardian.class);
        Horse.register(SaoHorse.class);
        IronGolem.register(SaoIronGolem.class);
        MagmaCube.register(SaoMagmaCube.class);
        MushroomCow.register(SaoMushroomCow.class);
        Ocelot.register(SaoOcelot.class);
        Pig.register(SaoPig.class);
        PigZombie.register(SaoPigZombie.class);
        Rabbit.register(SaoRabbit.class);
        Sheep.register(SaoSheep.class);
        Silverfish.register(SaoSilverFish.class);
        Skeleton.register(SaoSkeleton.class);
        Slime.register(SaoSlime.class);
        Snowman.register(SaoSnowGolem.class);
        Spider.register(SaoSpider.class);
        Squid.register(SaoSquid.class);
        Villager.register(SaoVillager.class);
        Witch.register(SaoWitch.class);
        Wither.register(SaoWither.class);
        Wolf.register(SaoWolf.class);
        Zombie.register(SaoZombie.class);
    }

    private static class CustomEntityType {
        private static List<CustomEntityType> types = new ArrayList<CustomEntityType>();
        private String name;
        private int id;
        private Class<? extends EntityInsentient> nmsClass;
        private Class<? extends EntityInsentient> customClass;

        public CustomEntityType(String name, int id, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass) {
            this.name = name;
            this.id = id;
            this.nmsClass = nmsClass;
            this.customClass = customClass;
        }

        public String getName() {
            return this.name;
        }

        public int getID() {
            return this.id;
        }

        public Class<? extends EntityInsentient> getNMSClass() {
            return this.nmsClass;
        }

        public Class<? extends EntityInsentient> getCustomClass() {
            return this.customClass;
        }

        public void registerEntity() {
            types.add(this);
            CustomEntityType.a(this.getCustomClass(), this.getName(), this.getID());
        }

        public static void unregisterEntities() {
            for (CustomEntityType entity2 : types) {
                try {
                    ((Map)CustomEntityType.getPrivateStatic(EntityTypes.class, "d")).remove(entity2.getCustomClass());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    ((Map)CustomEntityType.getPrivateStatic(EntityTypes.class, "f")).remove(entity2.getCustomClass());
                    continue;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (CustomEntityType entity2 : types) {
                try {
                    CustomEntityType.a(entity2.getNMSClass(), entity2.getName(), entity2.getID());
                    continue;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private static Object getPrivateStatic(Class clazz, String f) throws Exception {
            Field field = clazz.getDeclaredField(f);
            field.setAccessible(true);
            return field.get(null);
        }

        private static void a(Class paramClass, String paramString, int paramInt) {
            try {
                ((Map)CustomEntityType.getPrivateStatic(EntityTypes.class, "c")).put(paramString, paramClass);
                ((Map)CustomEntityType.getPrivateStatic(EntityTypes.class, "d")).put(paramClass, paramString);
                ((Map)CustomEntityType.getPrivateStatic(EntityTypes.class, "e")).put(paramInt, paramClass);
                ((Map)CustomEntityType.getPrivateStatic(EntityTypes.class, "f")).put(paramClass, paramInt);
                ((Map)CustomEntityType.getPrivateStatic(EntityTypes.class, "g")).put(paramString, paramInt);
            }
            catch (Exception var3_3) {
                // empty catch block
            }
        }
    }

}

