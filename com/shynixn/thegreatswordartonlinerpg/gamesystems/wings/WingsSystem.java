/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.wings;

import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalSystem;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.wings.BannerWingsApi;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.wings.WingsCommandExecutor;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.wings.WingsData;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.wings.WingsDataFileManager;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.wings.WingsListener;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.wings.AincradWingsExistEvent;
import libraries.com.shynixn.utilities.BukkitFileManager;
import libraries.com.shynixn.utilities.BukkitManager;
import libraries.com.shynixn.utilities.BukkitObject;
import org.bukkit.plugin.java.JavaPlugin;

public final class WingsSystem
extends BukkitManager
implements CardinalSystem {
    private JavaPlugin plugin;

    public WingsSystem(JavaPlugin plugin) {
        super(new WingsDataFileManager(plugin), BukkitManager.SaveType.SINGEL);
        new com.shynixn.thegreatswordartonlinerpg.gamesystems.wings.WingsCommandExecutor(this, plugin);
        BannerWingsApi.start(plugin, this);
        new com.shynixn.thegreatswordartonlinerpg.gamesystems.wings.WingsListener(this, plugin);
        this.plugin = plugin;
    }

    @Override
    public WingsData getItemFromName(String name) {
        return (WingsData)super.getItemFromName(name);
    }

    public void reset() {
        BannerWingsApi.disable(this.plugin);
    }

    @Override
    public void handleCardinalEvent(AincradEvent event) throws Exception {
        if (event instanceof AincradWingsExistEvent && this.getItemFromName(((AincradWingsExistEvent)event).getWingsName()) != null) {
            ((AincradWingsExistEvent)event).setExist(true);
        }
    }
}

