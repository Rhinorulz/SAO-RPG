/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.java.JavaPlugin
 */
package libraries.com.shynixn.utilities;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import libraries.com.shynixn.utilities.BukkitFileManager;
import libraries.com.shynixn.utilities.BukkitObject;
import libraries.com.shynixn.utilities.BukkitYamlMapper;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitManager {
    private BukkitFileManager fileManager;
    private SaveType saveType;
    private HashMap<String, BukkitObject> items = new HashMap();
    private JavaPlugin plugin;
    private String[] folders;
    private Class type;

    public BukkitManager(BukkitFileManager fileManager, SaveType saveType) {
        if (fileManager == null) {
            throw new IllegalArgumentException("FileManager cannot be null!");
        }
        if (saveType == null) {
            throw new IllegalArgumentException("SaveType cannot be null!");
        }
        this.saveType = saveType;
        this.fileManager = fileManager;
    }

    public BukkitManager(Class type, String[] folders, JavaPlugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null!");
        }
        if (folders == null) {
            throw new IllegalArgumentException("Folders cannot be null!");
        }
        this.saveType = SaveType.YAMLMAPPER;
        this.type = type;
        this.folders = folders;
    }

    public BukkitManager(Class type, JavaPlugin plugin) {
        this(type, new String[0], plugin);
    }

    public BukkitManager(Class type, String folder, JavaPlugin plugin) {
        this(type, new String[]{folder}, plugin);
    }

    public void addItem(BukkitObject item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null!");
        }
        if (!this.contains(item.getName())) {
            this.items.put(item.getName(), item);
            this.save(item);
        }
    }

    public void removeItem(BukkitObject item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null!");
        }
        if (this.contains(item.getName())) {
            this.items.remove(item.getName());
            if (this.saveType == SaveType.SINGEL) {
                this.fileManager.delete(item);
            } else {
                this.save(null);
            }
            this.reload();
        }
    }

    public boolean contains(String name) {
        for (String name2 : this.getItemKeys()) {
            if (!name2.equalsIgnoreCase(name)) continue;
            return true;
        }
        return false;
    }

    public List<BukkitObject> getItems() {
        return Arrays.asList(this.items.values().toArray(new BukkitObject[this.items.size()]));
    }

    public List<String> getItemKeys() {
        return Arrays.asList(this.items.keySet().toArray(new String[this.items.size()]));
    }

    public BukkitObject getItemFromName(String name) {
        for (BukkitObject object : this.getItems()) {
            if (!object.getName().equalsIgnoreCase(name)) continue;
            return object;
        }
        return null;
    }

    public void save(BukkitObject item) {
        if (this.saveType == SaveType.ALL) {
            this.fileManager.saveAll(this.getItems());
        } else if (this.saveType == SaveType.KEYS) {
            this.fileManager.saveKeys(this.getItemKeys());
        } else if (this.saveType == SaveType.YAMLMAPPER) {
            try {
                File path = this.plugin.getDataFolder();
                String[] arrstring = this.folders;
                int n = arrstring.length;
                int n2 = 0;
                while (n2 < n) {
                    String s = arrstring[n2];
                    if (!(path = new File(path, s)).exists()) {
                        path.mkdir();
                    }
                    ++n2;
                }
                path = new File(path, String.valueOf(item.getName()) + ".yml");
                BukkitYamlMapper.save(path, item);
            }
            catch (Exception e) {
                this.saveFileError(item);
            }
        } else {
            this.fileManager.save(item);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void reload() {
        this.items.clear();
        if (this.saveType == SaveType.ALL) {
            BukkitObject[] arrbukkitObject = this.fileManager.loadAll();
            int n = arrbukkitObject.length;
            int n2 = 0;
            while (n2 < n) {
                BukkitObject object = arrbukkitObject[n2];
                this.addItem(object);
                ++n2;
            }
            return;
        } else if (this.saveType == SaveType.SINGEL || this.saveType == SaveType.KEYS) {
            BukkitObject[] arrbukkitObject = this.fileManager.load();
            int n = arrbukkitObject.length;
            int n3 = 0;
            while (n3 < n) {
                BukkitObject object = arrbukkitObject[n3];
                this.addItem(object);
                ++n3;
            }
            return;
        } else {
            if (this.saveType != SaveType.YAMLMAPPER) return;
            try {
                String s;
                File path = this.plugin.getDataFolder();
                String[] arrstring = this.folders;
                int n = arrstring.length;
                int n4 = 0;
                while (n4 < n) {
                    s = arrstring[n4];
                    if (!(path = new File(path, s)).exists()) {
                        path.mkdir();
                    }
                    ++n4;
                }
                arrstring = path.list();
                n = arrstring.length;
                n4 = 0;
                while (n4 < n) {
                    s = arrstring[n4];
                    File file = new File(path, s);
                    BukkitObject object = (BukkitObject)BukkitYamlMapper.load(file, this.type);
                    this.items.put(object.getName(), object);
                    ++n4;
                }
                return;
            }
            catch (Exception e) {
                this.loadFileError();
            }
        }
    }

    public void saveFileError(BukkitObject item) {
    }

    public void loadFileError() {
    }

    public static enum SaveType {
        SINGEL,
        ALL,
        KEYS,
        YAMLMAPPER;
        

        private SaveType(String string2, int n2) {
        }
    }

}

