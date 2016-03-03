/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.java.JavaPlugin
 */
package libraries.com.shynixn.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import libraries.com.shynixn.utilities.BukkitObject;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitFileManager {
    private JavaPlugin plugin;
    private String fileName;
    private String filedata = "paths.dat";

    public BukkitFileManager(JavaPlugin plugin, String fileName) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null!");
        }
        if (fileName == null) {
            throw new IllegalArgumentException("FileName cannot be null!");
        }
        this.plugin = plugin;
        this.fileName = fileName;
    }

    public boolean save(BukkitObject item) {
        boolean addList = false;
        String[] lines = BukkitUtilities.u().readAllLines(new File(this.getDataFolder(), this.filedata));
        ArrayList<String> linesList = new ArrayList<String>();
        int i = 0;
        while (i < lines.length) {
            if (lines[i].equals(String.valueOf(item.getName()) + ".dat")) {
                addList = true;
            }
            linesList.add(lines[i]);
            ++i;
        }
        if (!addList) {
            linesList.add(String.valueOf(item.getName()) + ".dat");
            BukkitUtilities.u().writeAllLines(new File(this.getDataFolder(), this.filedata), linesList);
        }
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            try {
                fos = new FileOutputStream(new File(this.getDataFolder(), String.valueOf(item.getName()) + ".dat"));
                oos = new ObjectOutputStream(fos);
                oos.writeObject(item);
            }
            catch (Exception e) {
                e.printStackTrace();
                try {
                    oos.close();
                    fos.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        finally {
            try {
                oos.close();
                fos.close();
            }
            catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public boolean saveAll(List<BukkitObject> items) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            try {
                fos = new FileOutputStream(new File(this.getDataFolder(), this.fileName));
                oos = new ObjectOutputStream(fos);
                oos.writeObject(items.toArray(new BukkitObject[items.size()]));
            }
            catch (Exception e) {
                e.printStackTrace();
                try {
                    oos.close();
                    fos.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        finally {
            try {
                oos.close();
                fos.close();
            }
            catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public boolean saveKeys(List<String> items) {
        throw new NullPointerException("You have to override this method to use it.");
    }

    public boolean delete(BukkitObject item) {
        try {
            int index = -1;
            String[] lines = BukkitUtilities.u().readAllLines(new File(this.getDataFolder(), this.filedata));
            ArrayList<String> linesList = new ArrayList<String>();
            int i = 0;
            while (i < lines.length) {
                if (lines[i].equals(String.valueOf(item.getName()) + ".dat")) {
                    index = i;
                }
                linesList.add(lines[i]);
                ++i;
            }
            if (index != -1) {
                linesList.remove(index);
                BukkitUtilities.u().writeAllLines(new File(this.getDataFolder(), this.filedata), linesList);
            }
            new File(this.getDataFolder(), String.valueOf(item.getName()) + ".dat").delete();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public BukkitObject[] load() {
        String[] lines = BukkitUtilities.u().readAllLines(new File(this.getDataFolder(), this.filedata));
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        ArrayList<BukkitObject> items = new ArrayList<BukkitObject>();
        int i = 0;
        while (i < lines.length) {
            block12 : {
                try {
                    try {
                        fis = new FileInputStream(new File(this.getDataFolder(), lines[i]));
                        ois = new ObjectInputStream(fis);
                        BukkitObject item = (BukkitObject)ois.readObject();
                        items.add(item);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        try {
                            ois.close();
                            fis.close();
                        }
                        catch (Exception var8_9) {}
                        break block12;
                    }
                }
                catch (Throwable var7_12) {
                    try {
                        ois.close();
                        fis.close();
                    }
                    catch (Exception var8_10) {
                        // empty catch block
                    }
                    throw var7_12;
                }
                try {
                    ois.close();
                    fis.close();
                }
                catch (Exception var8_11) {
                    // empty catch block
                }
            }
            ++i;
        }
        return items.toArray(new BukkitObject[items.size()]);
    }

    public BukkitObject[] loadAll() {
        List items2;
        block12 : {
            ObjectInputStream ois;
            FileInputStream fis;
            fis = null;
            ois = null;
            List items2 = new ArrayList();
            if (!new File(this.getDataFolder(), this.fileName).exists()) {
                return items2.toArray(new BukkitObject[items2.size()]);
            }
            try {
                try {
                    fis = new FileInputStream(new File(this.getDataFolder(), this.fileName));
                    ois = new ObjectInputStream(fis);
                    items2 = Arrays.asList((BukkitObject[])ois.readObject());
                }
                catch (Exception e) {
                    e.printStackTrace();
                    try {
                        ois.close();
                        fis.close();
                    }
                    catch (Exception var6_5) {}
                    break block12;
                }
            }
            catch (Throwable var5_8) {
                try {
                    ois.close();
                    fis.close();
                }
                catch (Exception var6_6) {
                    // empty catch block
                }
                throw var5_8;
            }
            try {
                ois.close();
                fis.close();
            }
            catch (Exception var6_7) {
                // empty catch block
            }
        }
        return items2.toArray(new BukkitObject[items2.size()]);
    }

    public final File getDataFolder() {
        return this.plugin.getDataFolder();
    }
}

