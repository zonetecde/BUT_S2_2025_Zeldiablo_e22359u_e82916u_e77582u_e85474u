package gameZeldiablo.Zeldiablo.Items;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public enum ItemsList {
    AMULETTE,
    BATON,
    BOMBE,
    EPEE,
    FOOD,
    HACHE,
    RECETTE;

    private static final Map<ItemsList,Item> cacheItem = new HashMap<>();

    public static Item factory(ItemsList i){
        try {
            return switch (i){
                case AMULETTE -> CacheManage(i,Amulette.class.getConstructor());
                case BATON -> CacheManage(i,Baton.class.getConstructor());
                case BOMBE -> CacheManage(i,Bombe.class.getConstructor());
                case EPEE -> CacheManage(i,Epee.class.getConstructor());
                case FOOD -> CacheManage(i,Food.class.getConstructor());
                case HACHE -> CacheManage(i,Hache.class.getConstructor());
                case RECETTE -> CacheManage(i,Recette.class.getConstructor());
            };
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static Item CacheManage(ItemsList i , Constructor<?> c){
        if (cacheItem.containsKey(i)){
            return cacheItem.get(i);
        }
        try {
            return (Item)c.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
