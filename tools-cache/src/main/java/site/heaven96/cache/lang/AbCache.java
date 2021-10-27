package site.heaven96.cache.lang;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 缓存 抽象类
 *
 * @author lgw3488
 * @date 2021/10/28
 */
public abstract class AbCache<K, V> implements ICache<K, V> {
    private final HashMap<Object, Long> cacheObjExpiredTime = new HashMap<>();
    private final HashMap<Object, Serializable> cacheMap = new HashMap<>();
}
