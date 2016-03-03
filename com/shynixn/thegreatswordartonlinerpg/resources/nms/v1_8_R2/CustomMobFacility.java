/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R2.EntityBat
 *  net.minecraft.server.v1_8_R2.EntityBlaze
 *  net.minecraft.server.v1_8_R2.EntityCaveSpider
 *  net.minecraft.server.v1_8_R2.EntityChicken
 *  net.minecraft.server.v1_8_R2.EntityCow
 *  net.minecraft.server.v1_8_R2.EntityCreeper
 *  net.minecraft.server.v1_8_R2.EntityEnderDragon
 *  net.minecraft.server.v1_8_R2.EntityEnderman
 *  net.minecraft.server.v1_8_R2.EntityEndermite
 *  net.minecraft.server.v1_8_R2.EntityGhast
 *  net.minecraft.server.v1_8_R2.EntityGiantZombie
 *  net.minecraft.server.v1_8_R2.EntityGuardian
 *  net.minecraft.server.v1_8_R2.EntityHorse
 *  net.minecraft.server.v1_8_R2.EntityInsentient
 *  net.minecraft.server.v1_8_R2.EntityIronGolem
 *  net.minecraft.server.v1_8_R2.EntityMagmaCube
 *  net.minecraft.server.v1_8_R2.EntityMushroomCow
 *  net.minecraft.server.v1_8_R2.EntityOcelot
 *  net.minecraft.server.v1_8_R2.EntityPig
 *  net.minecraft.server.v1_8_R2.EntityPigZombie
 *  net.minecraft.server.v1_8_R2.EntityRabbit
 *  net.minecraft.server.v1_8_R2.EntitySheep
 *  net.minecraft.server.v1_8_R2.EntitySilverfish
 *  net.minecraft.server.v1_8_R2.EntitySkeleton
 *  net.minecraft.server.v1_8_R2.EntitySlime
 *  net.minecraft.server.v1_8_R2.EntitySnowman
 *  net.minecraft.server.v1_8_R2.EntitySpider
 *  net.minecraft.server.v1_8_R2.EntitySquid
 *  net.minecraft.server.v1_8_R2.EntityTypes
 *  net.minecraft.server.v1_8_R2.EntityVillager
 *  net.minecraft.server.v1_8_R2.EntityWitch
 *  net.minecraft.server.v1_8_R2.EntityWither
 *  net.minecraft.server.v1_8_R2.EntityWolf
 *  net.minecraft.server.v1_8_R2.EntityZombie
 *  org.bukkit.World
 */
package com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2;

import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobEquipment;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.MobRegistry;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.SaoMob;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoBat;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoBlaze;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoCaveSpider;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoChicken;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoCow;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoCreeper;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoEnderDragon;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoEnderman;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoEndermite;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoGhast;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoGiant;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoGuardian;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoHorse;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoIronGolem;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoMagmaCube;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoMushroomCow;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoOcelot;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoPig;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoPigZombie;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoRabbit;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoSheep;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoSilverFish;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoSkeleton;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoSlime;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoSnowGolem;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoSpider;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoSquid;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoVillager;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoWitch;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoWither;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoWolf;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.v1_8_R2.mobs.SaoZombie;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.server.v1_8_R2.EntityBat;
import net.minecraft.server.v1_8_R2.EntityBlaze;
import net.minecraft.server.v1_8_R2.EntityCaveSpider;
import net.minecraft.server.v1_8_R2.EntityChicken;
import net.minecraft.server.v1_8_R2.EntityCow;
import net.minecraft.server.v1_8_R2.EntityCreeper;
import net.minecraft.server.v1_8_R2.EntityEnderDragon;
import net.minecraft.server.v1_8_R2.EntityEnderman;
import net.minecraft.server.v1_8_R2.EntityEndermite;
import net.minecraft.server.v1_8_R2.EntityGhast;
import net.minecraft.server.v1_8_R2.EntityGiantZombie;
import net.minecraft.server.v1_8_R2.EntityGuardian;
import net.minecraft.server.v1_8_R2.EntityHorse;
import net.minecraft.server.v1_8_R2.EntityInsentient;
import net.minecraft.server.v1_8_R2.EntityIronGolem;
import net.minecraft.server.v1_8_R2.EntityMagmaCube;
import net.minecraft.server.v1_8_R2.EntityMushroomCow;
import net.minecraft.server.v1_8_R2.EntityOcelot;
import net.minecraft.server.v1_8_R2.EntityPig;
import net.minecraft.server.v1_8_R2.EntityPigZombie;
import net.minecraft.server.v1_8_R2.EntityRabbit;
import net.minecraft.server.v1_8_R2.EntitySheep;
import net.minecraft.server.v1_8_R2.EntitySilverfish;
import net.minecraft.server.v1_8_R2.EntitySkeleton;
import net.minecraft.server.v1_8_R2.EntitySlime;
import net.minecraft.server.v1_8_R2.EntitySnowman;
import net.minecraft.server.v1_8_R2.EntitySpider;
import net.minecraft.server.v1_8_R2.EntitySquid;
import net.minecraft.server.v1_8_R2.EntityTypes;
import net.minecraft.server.v1_8_R2.EntityVillager;
import net.minecraft.server.v1_8_R2.EntityWitch;
import net.minecraft.server.v1_8_R2.EntityWither;
import net.minecraft.server.v1_8_R2.EntityWolf;
import net.minecraft.server.v1_8_R2.EntityZombie;
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

