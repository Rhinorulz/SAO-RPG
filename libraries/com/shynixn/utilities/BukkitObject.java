/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 */
package libraries.com.shynixn.utilities;

import java.io.Serializable;
import libraries.com.shynixn.utilities.YamlSave;
import org.bukkit.ChatColor;

public class BukkitObject
implements Serializable {
    private static final long serialVersionUID = 1;
    @YamlSave(key="generic.name")
    private String name;
    @YamlSave(key="generic.displayname")
    private String displayName;

    public BukkitObject(String name, String displayName) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null!");
        }
        if (displayName == null) {
            throw new IllegalArgumentException("Displayname cannot be null!");
        }
        this.name = name;
        this.setDisplayName(displayName);
    }

    public BukkitObject() {
    }

    public final String getName() {
        return this.name;
    }

    public final String getDisplayName() {
        return this.displayName;
    }

    public final void setDisplayName(String displayName) {
        this.displayName = ChatColor.translateAlternateColorCodes((char)'&', (String)displayName);
    }

    public boolean equals(Object arg0) {
        if (arg0 == null) {
            return false;
        }
        if (arg0 instanceof BukkitObject) {
            return ((BukkitObject)arg0).getName().equals(this.getName());
        }
        return false;
    }
}

