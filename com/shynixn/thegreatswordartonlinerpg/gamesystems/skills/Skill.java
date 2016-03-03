/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.skills;

import com.shynixn.thegreatswordartonlinerpg.resources.effects.Enchantment;
import com.shynixn.thegreatswordartonlinerpg.resources.effects.Launch;
import com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect;
import com.shynixn.thegreatswordartonlinerpg.resources.effects.PotionEffect;
import com.shynixn.thegreatswordartonlinerpg.resources.effects.Sound;
import com.shynixn.thegreatswordartonlinerpg.resources.effects.Teleport;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.EffectType;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.SkillType;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import libraries.com.shynixn.utilities.BukkitObject;
import org.bukkit.ChatColor;

public class Skill
extends BukkitObject
implements Serializable {
    private static final long serialVersionUID = 1;
    private SkillType type;
    private boolean enabled = true;
    private String[] lore = new String[]{"", "", ""};
    private int loadingtime = 5;
    private int cooldown = 5;
    private int duration = 5;
    private HashMap<Integer, Enchantment> enchantmens = new HashMap();
    private HashMap<Integer, Launch> launches = new HashMap();
    private HashMap<Integer, ParticleEffect> particles = new HashMap();
    private HashMap<Integer, PotionEffect> effects = new HashMap();
    private HashMap<Integer, Sound> sounds = new HashMap();
    private HashMap<Integer, Teleport> teleports = new HashMap();
    private HashMap<Integer, Sound> hitSounds = new HashMap();
    private HashMap<Integer, ParticleEffect> hitParticles = new HashMap();
    private HashMap<Integer, PotionEffect> hitOwnEffects = new HashMap();
    private HashMap<Integer, PotionEffect> hitEnemyEffects = new HashMap();

    public Skill(String name, String displayname, SkillType type) {
        super(name, displayname);
        this.type = type;
        this.lore[0] = (Object)ChatColor.YELLOW + "<insert>";
        this.lore[1] = (Object)ChatColor.YELLOW + "<insert>";
        this.lore[2] = (Object)ChatColor.YELLOW + "<insert>";
    }

    public Skill() {
    }

    public Skill(String name, String displayName, SkillType type, boolean enabled, String[] lore, int loadingtime, int cooldown, int duration, HashMap<Integer, Enchantment> enchantmens, HashMap<Integer, Launch> launches, HashMap<Integer, ParticleEffect> particles, HashMap<Integer, PotionEffect> effects, HashMap<Integer, Sound> sounds, HashMap<Integer, Teleport> teleports, HashMap<Integer, Sound> hitSounds, HashMap<Integer, ParticleEffect> hitParticles, HashMap<Integer, PotionEffect> hitOwnEffects, HashMap<Integer, PotionEffect> hitEnemyEffects) {
        super(name, displayName);
        this.type = type;
        this.enabled = enabled;
        this.lore = lore;
        this.loadingtime = loadingtime;
        this.cooldown = cooldown;
        this.duration = duration;
        this.enchantmens = enchantmens;
        this.launches = launches;
        this.particles = particles;
        this.effects = effects;
        this.sounds = sounds;
        this.teleports = teleports;
        this.hitSounds = hitSounds;
        this.hitParticles = hitParticles;
        this.hitOwnEffects = hitOwnEffects;
        this.hitEnemyEffects = hitEnemyEffects;
    }

    public Skill clone() {
        return new Skill(this.getName(), this.getDisplayName(), this.type, this.enabled, this.lore, this.loadingtime, this.cooldown, this.duration, this.enchantmens, this.launches, this.particles, this.effects, this.sounds, this.teleports, this.hitSounds, this.hitParticles, this.hitOwnEffects, this.hitEnemyEffects);
    }

    public void addHitEnemyEffect(int id, PotionEffect effect) {
        if (effect != null) {
            this.hitEnemyEffects.put(id, effect);
        }
    }

    public void removeHitEnemyEffect(int id) {
        if (this.hitEnemyEffects.containsKey(id)) {
            this.hitEnemyEffects.remove(id);
        }
    }

    public PotionEffect[] getHitEnemyEffects() {
        return this.hitEnemyEffects.values().toArray(new PotionEffect[0]);
    }

    public List<Integer> getHitEnemyEffectIds() {
        return Arrays.asList(this.hitEnemyEffects.keySet().toArray(new Integer[0]));
    }

    public PotionEffect getHitEnemyEffectFromId(int id) {
        if (this.hitEnemyEffects.containsKey(id)) {
            return this.hitEnemyEffects.get(id);
        }
        return null;
    }

    public void addHitOwnEffect(int id, PotionEffect effect) {
        if (effect != null) {
            this.hitOwnEffects.put(id, effect);
        }
    }

    public void removeHitOwnEffect(int id) {
        if (this.hitOwnEffects.containsKey(id)) {
            this.hitOwnEffects.remove(id);
        }
    }

    public PotionEffect[] getHitOwnEffects() {
        return this.hitOwnEffects.values().toArray(new PotionEffect[0]);
    }

    public List<Integer> getHitOwnEffectIds() {
        return Arrays.asList(this.hitOwnEffects.keySet().toArray(new Integer[0]));
    }

    public PotionEffect getHitOwnEffectFromId(int id) {
        if (this.hitOwnEffects.containsKey(id)) {
            return this.hitOwnEffects.get(id);
        }
        return null;
    }

    public void addHitParticle(int id, ParticleEffect particle) {
        if (particle != null) {
            this.hitParticles.put(id, particle);
        }
    }

    public void removeHitParticle(int id) {
        if (this.hitParticles.containsKey(id)) {
            this.hitParticles.remove(id);
        }
    }

    public ParticleEffect[] getHitParticles() {
        return this.hitParticles.values().toArray(new ParticleEffect[0]);
    }

    public List<Integer> getHitParticleIds() {
        return Arrays.asList(this.hitParticles.keySet().toArray(new Integer[0]));
    }

    public ParticleEffect getHitParticleFromId(int id) {
        if (this.hitParticles.containsKey(id)) {
            return this.hitParticles.get(id);
        }
        return null;
    }

    public void addHitSound(int id, Sound sound) {
        if (sound != null) {
            this.hitSounds.put(id, sound);
        }
    }

    public void removeHitSound(int id) {
        if (this.hitSounds.containsKey(id)) {
            this.hitSounds.remove(id);
        }
    }

    public Sound[] getHitSounds() {
        return this.hitSounds.values().toArray(new Sound[0]);
    }

    public List<Integer> getHitSoundIds() {
        return Arrays.asList(this.hitSounds.keySet().toArray(new Integer[0]));
    }

    public Sound getHitSoundFromId(int id) {
        if (this.hitSounds.containsKey(id)) {
            return this.hitSounds.get(id);
        }
        return null;
    }

    public void addTeleport(int id, Teleport teleport) {
        if (teleport != null) {
            this.teleports.put(id, teleport);
        }
    }

    public void removeTeleport(int id) {
        if (this.teleports.containsKey(id)) {
            this.teleports.remove(id);
        }
    }

    public Teleport[] getTeleports() {
        return this.teleports.values().toArray(new Teleport[0]);
    }

    public List<Integer> getTeleportIds() {
        return Arrays.asList(this.teleports.keySet().toArray(new Integer[0]));
    }

    public Teleport getTeleportFromId(int id) {
        if (this.teleports.containsKey(id)) {
            return this.teleports.get(id);
        }
        return null;
    }

    public void addSound(int id, Sound sound) {
        if (sound != null) {
            this.sounds.put(id, sound);
        }
    }

    public void removeSound(int id) {
        if (this.sounds.containsKey(id)) {
            this.sounds.remove(id);
        }
    }

    public Sound[] getSounds() {
        return this.sounds.values().toArray(new Sound[0]);
    }

    public List<Integer> getSoundIds() {
        return Arrays.asList(this.sounds.keySet().toArray(new Integer[0]));
    }

    public Sound getSoundFromId(int id) {
        if (this.sounds.containsKey(id)) {
            return this.sounds.get(id);
        }
        return null;
    }

    public void addEffect(int id, PotionEffect effect) {
        if (effect != null) {
            this.effects.put(id, effect);
        }
    }

    public void removeEffect(int id) {
        if (this.effects.containsKey(id)) {
            this.effects.remove(id);
        }
    }

    public PotionEffect[] getEffects() {
        return this.effects.values().toArray(new PotionEffect[0]);
    }

    public List<Integer> getEffectIds() {
        return Arrays.asList(this.effects.keySet().toArray(new Integer[0]));
    }

    public PotionEffect getEffectFromId(int id) {
        if (this.effects.containsKey(id)) {
            return this.effects.get(id);
        }
        return null;
    }

    public void addParticle(int id, ParticleEffect particle) {
        if (particle != null) {
            this.particles.put(id, particle);
        }
    }

    public void removeParticle(int id) {
        if (this.particles.containsKey(id)) {
            this.particles.remove(id);
        }
    }

    public ParticleEffect[] getParticles() {
        return this.particles.values().toArray(new ParticleEffect[0]);
    }

    public List<Integer> getParticleIds() {
        return Arrays.asList(this.particles.keySet().toArray(new Integer[0]));
    }

    public ParticleEffect getParticleFromId(int id) {
        if (this.particles.containsKey(id)) {
            return this.particles.get(id);
        }
        return null;
    }

    public void addLaunch(int id, Launch launch) {
        if (launch != null) {
            this.launches.put(id, launch);
        }
    }

    public void removeLaunch(int id) {
        if (this.launches.containsKey(id)) {
            this.launches.remove(id);
        }
    }

    public Launch[] getLaunches() {
        return this.launches.values().toArray(new Launch[0]);
    }

    public List<Integer> getLaunchIds() {
        return Arrays.asList(this.launches.keySet().toArray(new Integer[0]));
    }

    public Launch getLaunchFromId(int id) {
        if (this.launches.containsKey(id)) {
            return this.launches.get(id);
        }
        return null;
    }

    public void addEnchantment(int id, Enchantment enchantment) {
        if (enchantment != null) {
            this.enchantmens.put(id, enchantment);
        }
    }

    public void removeEnchantment(int id) {
        if (this.enchantmens.containsKey(id)) {
            this.enchantmens.remove(id);
        }
    }

    public Enchantment[] getEnchantments() {
        return this.enchantmens.values().toArray(new Enchantment[0]);
    }

    public List<Integer> getEnchantmentIds() {
        return Arrays.asList(this.enchantmens.keySet().toArray(new Integer[0]));
    }

    public Enchantment getEnchantmentFromId(int id) {
        if (this.enchantmens.containsKey(id)) {
            return this.enchantmens.get(id);
        }
        return null;
    }

    public void setLoadingTime(int time) {
        if (time > 0) {
            this.loadingtime = time;
        }
    }

    public void setCooldDownTime(int time) {
        if (time > 0) {
            this.cooldown = time;
        }
    }

    public void setExecutingTime(int time) {
        if (time > 0) {
            this.duration = time;
        }
    }

    public int getLoadingTime() {
        return this.loadingtime;
    }

    public int getCoolDownTime() {
        return this.cooldown;
    }

    public int getExecutingTime() {
        return this.duration;
    }

    public void setLore(int position, String lore) {
        if (position >= 0 && position < 3) {
            this.lore[position] = lore;
        }
    }

    public String getLore(int position) {
        if (position >= 0 && position < 3) {
            return this.lore[position];
        }
        return null;
    }

    public void setSkillEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public SkillType getType() {
        return this.type;
    }

    public void setType(SkillType type) {
        this.type = type;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getUnusedId(EffectType type) {
        int id = 0;
        if (type == EffectType.Enchantment) {
            while (this.getEnchantmentIds().contains(id)) {
                ++id;
            }
            return id;
        } else if (type == EffectType.HitParticle) {
            while (this.getHitParticleIds().contains(id)) {
                ++id;
            }
            return id;
        } else if (type == EffectType.HitPotionEnemy) {
            while (this.getHitEnemyEffectIds().contains(id)) {
                ++id;
            }
            return id;
        } else if (type == EffectType.HitPotionOwn) {
            while (this.getHitOwnEffectIds().contains(id)) {
                ++id;
            }
            return id;
        } else if (type == EffectType.HitSound) {
            while (this.getHitSoundIds().contains(id)) {
                ++id;
            }
            return id;
        } else if (type == EffectType.Launch) {
            while (this.getLaunchIds().contains(id)) {
                ++id;
            }
            return id;
        } else if (type == EffectType.ParticleEffect) {
            while (this.getParticleIds().contains(id)) {
                ++id;
            }
            return id;
        } else if (type == EffectType.potionEffect) {
            while (this.getEffectIds().contains(id)) {
                ++id;
            }
            return id;
        } else if (type == EffectType.Sound) {
            while (this.getSoundIds().contains(id)) {
                ++id;
            }
            return id;
        } else {
            if (type != EffectType.Teleport) return id;
            while (this.getTeleportIds().contains(id)) {
                ++id;
            }
        }
        return id;
    }
}

