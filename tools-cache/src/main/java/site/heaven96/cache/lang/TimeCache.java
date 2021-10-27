package site.heaven96.cache.lang;

/**
 * 时间缓存
 *
 * @author lgw3488
 * @date 2021/10/28
 */
public interface TimeCache<K, V> extends ICache {
    /**
     * 有效期 单位毫秒 默认60s 针对时间类的缓存有效
     */
    long timeout = 60000L;

    /**
     * 设置有效期
     *
     * @param timeout 有效期 单位毫秒
     */
    void setTimeout(long timeout);

    /**
     * 清除过期
     */
    void clearExpired();
}
