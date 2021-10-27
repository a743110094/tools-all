package site.heaven96.cache.lang;

/**
 * 哈希图时间缓存
 *
 * @author lgw3488
 * @date 2021/10/28
 */
public class HashMapTimeCache<K, V> extends AbCache<K, V> {


    /**
     * 存放
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void put(Object key, Object value) {

    }

    /**
     * 获取
     *
     * @param key 键
     * @return {@code V}
     */
    @Override
    public Object get(Object key) {
        return null;
    }

    /**
     * 更新
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void update(Object key, Object value) {

    }

    /**
     * 删除
     *
     * @param key 键
     */
    @Override
    public void remove(Object key) {

    }

    /**
     * 更新存入
     *
     * @param key   键
     * @param value 值
     * @return boolean true-Key存在 更新成功 | false-Key不存在 已插入
     */
    @Override
    public boolean upPut(Object key, Object value) {
        return false;
    }

    /**
     * 存在
     *
     * @param key 键
     * @return boolean
     */
    @Override
    public boolean exists(Object key) {
        return false;
    }

    /**
     * 缓存的元素个数
     *
     * @return int
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * 全部清除
     */
    @Override
    public void clearAll() {

    }


}
