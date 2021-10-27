package site.heaven96.cache.lang;

/**
 * 缓存 接口
 *
 * @author lgw3488
 * @date 2021/10/27
 */
public interface ICache<K, V> {
    /**
     * 存放
     *
     * @param key   键
     * @param value 值
     */
    void put(K key, V value);

    /**
     * 获取
     *
     * @param key 键
     * @return {@code V}
     */
    V get(K key);

    /**
     * 更新
     *
     * @param key   键
     * @param value 值
     */
    void update(K key, V value);

    /**
     * 删除
     *
     * @param key 键
     */
    void remove(K key);

    /**
     * 更新存入
     *
     * @param key   键
     * @param value 值
     * @return boolean true-Key存在 更新成功 | false-Key不存在 已插入
     */
    boolean upPut(K key, V value);

    /**
     * 存在
     *
     * @param key 键
     * @return boolean
     */
    boolean exists(K key);

    /**
     * 缓存的元素个数
     *
     * @return int
     */
    int size();

    /**
     * 全部清除
     */
    void clearAll();

}
