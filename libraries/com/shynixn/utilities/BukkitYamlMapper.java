/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.inventory.ItemStack
 */
package libraries.com.shynixn.utilities;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import libraries.com.shynixn.utilities.YamlSave;
import libraries.com.shynixn.utilities.YamlSaveGeneric;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public final class BukkitYamlMapper {
    private BukkitYamlMapper() {
    }

    public static void save(File file, Object object) throws Exception {
        FileConfiguration config = BukkitYamlMapper.prepareSaveFile(file);
        BukkitYamlMapper.saveObject(config, object, "");
        config.save(file);
    }

    public static Object load(File file, Class type) throws Exception {
        Object object = type.getConstructor(new Class[0]).newInstance(new Object[0]);
        FileConfiguration config = BukkitYamlMapper.prepareLoadFile(file);
        BukkitYamlMapper.loadObject(config, object, "");
        return object;
    }

    private static void loadObject(FileConfiguration config, Object object, String key) throws Exception {
        Class cls = object.getClass();
        do {
            Field[] arrfield = cls.getDeclaredFields();
            int n = arrfield.length;
            int n2 = 0;
            while (n2 < n) {
                Field field = arrfield[n2];
                if (field.isAnnotationPresent(YamlSave.class)) {
                    YamlSave save = (YamlSave)field.getDeclaredAnnotation(YamlSave.class);
                    if (!key.equals("")) {
                        BukkitYamlMapper.loadFields(config, object, String.valueOf(key) + "." + save.key(), field);
                    } else {
                        BukkitYamlMapper.loadFields(config, object, String.valueOf(key) + save.key(), field);
                    }
                } else if (field.isAnnotationPresent(YamlSaveGeneric.class)) {
                    if (!key.equals("")) {
                        BukkitYamlMapper.loadFields(config, object, String.valueOf(key) + "." + field.getName(), field);
                    } else {
                        BukkitYamlMapper.loadFields(config, object, String.valueOf(key) + field.getName(), field);
                    }
                }
                ++n2;
            }
        } while ((cls = cls.getSuperclass()) != null);
    }

    private static <T> void loadFields(FileConfiguration config, Object object, String key, Field field) throws Exception {
        block36 : {
            Class type = field.getType();
            if (BukkitYamlMapper.isGenericType(type)) {
                BukkitYamlMapper.loadData(config, key, field, object);
            } else {
                if (type == List.class || type == ArrayList.class || type == Collection.class) {
                    field.setAccessible(true);
                    field.set(object, new ArrayList());
                    ParameterizedType pt = (ParameterizedType)field.getGenericType();
                    Class type2 = (Class)pt.getActualTypeArguments()[0];
                    int i = 0;
                    do {
                        Object object2;
                        if (BukkitYamlMapper.isGenericType(type2)) {
                            object2 = BukkitYamlMapper.loadData(config, String.valueOf(key) + "." + String.valueOf(i), type2);
                            if (object2 == null) break block36;
                            ((ArrayList)field.get(object)).add(object2);
                        } else {
                            object2 = type2.getConstructor(new Class[0]).newInstance(new Object[0]);
                            try {
                                BukkitYamlMapper.loadObject(config, object2, String.valueOf(key) + "." + String.valueOf(i));
                            }
                            catch (Exception e) {
                                break block36;
                            }
                            ((ArrayList)field.get(object)).add(object2);
                        }
                        ++i;
                    } while (true);
                }
                if (field.getType().isArray()) {
                    field.setAccessible(true);
                    Class type2 = field.getType().getComponentType();
                    int i = 0;
                    do {
                        Object object2;
                        if (BukkitYamlMapper.isGenericType(type2)) {
                            object2 = BukkitYamlMapper.loadData(config, String.valueOf(key) + "." + String.valueOf(i), type2);
                            if (object2 == null) {
                                break;
                            }
                        } else {
                            object2 = field.getType().getComponentType().getConstructor(new Class[0]).newInstance(new Object[0]);
                            try {
                                BukkitYamlMapper.loadObject(config, object2, String.valueOf(key) + "." + String.valueOf(i));
                            }
                            catch (Exception e) {
                                break;
                            }
                        }
                        ++i;
                    } while (true);
                    Object[] array = (Object[])Array.newInstance(field.getType().getComponentType(), i);
                    i = 0;
                    do {
                        Object object2;
                        if (BukkitYamlMapper.isGenericType(type2)) {
                            object2 = BukkitYamlMapper.loadData(config, String.valueOf(key) + "." + String.valueOf(i), type2);
                            if (object2 == null) break;
                            array[i] = object2;
                        } else {
                            object2 = field.getType().getComponentType().getConstructor(new Class[0]).newInstance(new Object[0]);
                            try {
                                BukkitYamlMapper.loadObject(config, object2, String.valueOf(key) + "." + String.valueOf(i));
                            }
                            catch (Exception e) {
                                break;
                            }
                            array[i] = object2;
                        }
                        ++i;
                    } while (true);
                    field.set(object, array);
                } else if (field.getType().isEnum()) {
                    field.setAccessible(true);
                    field.set(object, Enum.valueOf(field.getType(), (String)BukkitYamlMapper.loadData(config, key, String.class)));
                } else if (type == HashMap.class) {
                    field.setAccessible(true);
                    ParameterizedType pt = (ParameterizedType)field.getGenericType();
                    Class keytype = (Class)pt.getActualTypeArguments()[0];
                    Class valuetype = (Class)pt.getActualTypeArguments()[1];
                    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                    int i = 0;
                    do {
                        Object objectvalue;
                        Object objectkey;
                        if (BukkitYamlMapper.isGenericType(keytype)) {
                            objectkey = BukkitYamlMapper.loadData(config, String.valueOf(key) + "." + String.valueOf(i) + ".key", keytype);
                            if (objectkey == null) {
                                break;
                            }
                        } else {
                            objectkey = keytype.getConstructor(new Class[0]).newInstance(new Object[0]);
                            try {
                                BukkitYamlMapper.loadObject(config, objectkey, String.valueOf(key) + "." + String.valueOf(i) + ".key");
                            }
                            catch (Exception e) {
                                break;
                            }
                        }
                        if (BukkitYamlMapper.isGenericType(valuetype)) {
                            objectvalue = BukkitYamlMapper.loadData(config, String.valueOf(key) + "." + String.valueOf(i) + ".value", valuetype);
                            if (objectvalue == null) {
                                break;
                            }
                        } else {
                            objectvalue = valuetype.getConstructor(new Class[0]).newInstance(new Object[0]);
                            try {
                                BukkitYamlMapper.loadObject(config, objectvalue, String.valueOf(key) + "." + String.valueOf(i) + ".value");
                            }
                            catch (Exception e) {
                                break;
                            }
                        }
                        hashMap.put(objectkey, objectvalue);
                        ++i;
                    } while (true);
                    field.set(object, hashMap);
                } else {
                    BukkitYamlMapper.loadObject(config, field.get(object), key);
                }
            }
        }
    }

    private static Object loadData(FileConfiguration config, String key, Type type) throws Exception {
        if (type == String.class) {
            return config.getString(key);
        }
        if (type == Integer.class) {
            return config.getInt(key);
        }
        if (type == Double.class) {
            return config.getDouble(key);
        }
        if (type == Float.class) {
            return config.getDouble(key);
        }
        if (type == Boolean.class) {
            return config.getBoolean(key);
        }
        if (type == ItemStack.class) {
            return config.getItemStack(key);
        }
        if (type == Double.TYPE) {
            return config.getDouble(key);
        }
        if (type == Float.TYPE) {
            return Float.valueOf((float)config.getDouble(key));
        }
        if (type == Boolean.TYPE) {
            return config.getBoolean(key);
        }
        if (type == Integer.TYPE) {
            return config.getInt(key);
        }
        return null;
    }

    private static void loadData(FileConfiguration config, String key, Field field, Object object) throws Exception {
        if (config.get(key) == null) {
            throw new NullPointerException();
        }
        Class type = field.getType();
        field.setAccessible(true);
        if (type == String.class) {
            field.set(object, config.getString(key));
        } else if (type == Integer.class) {
            field.set(object, config.getInt(key));
        } else if (type == Double.class) {
            field.set(object, config.getDouble(key));
        } else if (type == Float.class) {
            field.set(object, config.getDouble(key));
        } else if (type == Boolean.class) {
            field.set(object, config.getBoolean(key));
        } else if (type == ItemStack.class) {
            field.set(object, (Object)config.getItemStack(key));
        } else if (type == Double.TYPE) {
            field.setDouble(object, config.getDouble(key));
        } else if (type == Float.TYPE) {
            field.setFloat(object, (float)config.getDouble(key));
        } else if (type == Boolean.TYPE) {
            field.setBoolean(object, config.getBoolean(key));
        } else if (type == Integer.TYPE) {
            field.setInt(object, config.getInt(key));
        }
    }

    private static void saveObject(FileConfiguration config, Object object, String key) throws Exception {
        Class cls = object.getClass();
        do {
            Field[] arrfield = cls.getDeclaredFields();
            int n = arrfield.length;
            int n2 = 0;
            while (n2 < n) {
                Field field = arrfield[n2];
                if (field.isAnnotationPresent(YamlSave.class)) {
                    YamlSave save = (YamlSave)field.getDeclaredAnnotation(YamlSave.class);
                    if (!key.equals("")) {
                        BukkitYamlMapper.saveFields(config, object, String.valueOf(key) + "." + save.key(), field);
                    } else {
                        BukkitYamlMapper.saveFields(config, object, String.valueOf(key) + save.key(), field);
                    }
                } else if (field.isAnnotationPresent(YamlSaveGeneric.class)) {
                    if (!key.equals("")) {
                        BukkitYamlMapper.saveFields(config, object, String.valueOf(key) + "." + field.getName(), field);
                    } else {
                        BukkitYamlMapper.saveFields(config, object, String.valueOf(key) + "." + field.getName(), field);
                    }
                }
                ++n2;
            }
        } while ((cls = cls.getSuperclass()) != null);
    }

    private static void saveFields(FileConfiguration config, Object object, String key, Field field) throws Exception {
        Class type = field.getType();
        field.setAccessible(true);
        if (field.get(object) != null) {
            if (BukkitYamlMapper.isGenericType(type)) {
                BukkitYamlMapper.saveData(config, key, field.get(object));
            } else if (type == List.class || type == ArrayList.class || type == Collection.class) {
                Iterator it = ((Collection)field.get(object)).iterator();
                int i = 0;
                while (it.hasNext()) {
                    Object o = it.next();
                    if (BukkitYamlMapper.isValidType(o)) {
                        BukkitYamlMapper.saveData(config, String.valueOf(key) + "." + String.valueOf(i), o);
                    } else {
                        BukkitYamlMapper.saveObject(config, o, String.valueOf(key) + "." + String.valueOf(i));
                    }
                    ++i;
                }
            } else if (field.getType().isArray()) {
                Object[] array = (Object[])field.get(object);
                int i = 0;
                while (i < array.length) {
                    if (BukkitYamlMapper.isValidType(array[i])) {
                        BukkitYamlMapper.saveData(config, String.valueOf(key) + "." + String.valueOf(i), array[i]);
                    } else {
                        BukkitYamlMapper.saveObject(config, array[i], String.valueOf(key) + "." + String.valueOf(i));
                    }
                    ++i;
                }
            } else if (field.getType().isEnum()) {
                Enum enum1 = (Enum)field.get(object);
                BukkitYamlMapper.saveData(config, key, enum1.name().toUpperCase());
            } else if (type == HashMap.class) {
                HashMap it = (HashMap)field.get(object);
                int i = 0;
                for (Object s : it.keySet()) {
                    if (BukkitYamlMapper.isValidType(s)) {
                        BukkitYamlMapper.saveData(config, String.valueOf(key) + "." + String.valueOf(i) + ".key", s);
                    } else {
                        BukkitYamlMapper.saveObject(config, s, String.valueOf(key) + "." + String.valueOf(i) + ".key");
                    }
                    if (BukkitYamlMapper.isValidType(it.get(s))) {
                        BukkitYamlMapper.saveData(config, String.valueOf(key) + "." + String.valueOf(i) + ".value", it.get(s));
                    } else {
                        BukkitYamlMapper.saveObject(config, it.get(s), String.valueOf(key) + "." + String.valueOf(i) + ".value");
                    }
                    ++i;
                }
            } else {
                BukkitYamlMapper.saveObject(config, field.get(object), key);
            }
        }
    }

    private static boolean isGenericType(Type type) {
        if (type == String.class || type == Byte.class || type == Integer.class || type == Double.class || type == Float.class || type == ItemStack.class || type == Boolean.class) {
            return true;
        }
        if (type == Byte.TYPE || type == Double.TYPE || type == Float.TYPE || type == Integer.TYPE || type == Boolean.TYPE) {
            return true;
        }
        return false;
    }

    private static boolean isValidType(Object object) {
        if (object instanceof String || object instanceof Double || object instanceof Integer || object instanceof Double || object instanceof Float || object instanceof ItemStack || object instanceof Boolean) {
            return true;
        }
        try {
            String s = (String)object;
            return true;
        }
        catch (Exception s) {
            try {
                int s = (Integer)object;
                return true;
            }
            catch (Exception s) {
                try {
                    double s = (Double)object;
                    return true;
                }
                catch (Exception s) {
                    try {
                        float s = ((Float)object).floatValue();
                        return true;
                    }
                    catch (Exception s) {
                        try {
                            boolean s = (Boolean)object;
                            return true;
                        }
                        catch (Exception s) {
                            return false;
                        }
                    }
                }
            }
        }
    }

    private static void saveData(FileConfiguration config, String key, Object value) throws Exception {
        config.set(key, value);
    }

    private static FileConfiguration prepareLoadFile(File file) throws Exception {
        YamlConfiguration config = new YamlConfiguration();
        config.load(file);
        return config;
    }

    private static FileConfiguration prepareSaveFile(File file) throws Exception {
        YamlConfiguration config = new YamlConfiguration();
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        config.load(file);
        return config;
    }
}

