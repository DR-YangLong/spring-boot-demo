/*
        Copyright  DR.YangLong

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/
package site.yanglong.promotion.config.shiro.distribution

import org.apache.shiro.cache.Cache
import org.apache.shiro.cache.CacheException

/**
 * 功能或描述：分布式缓存接口
 *
 * @Author: DR.YangLong
 * @Date: 14-9-30
 * @Time: 上午9:50
 * @Email: 410357434@163.com
 * @Version: 1.0
 * @Module: 修正:            日期：
 */
class DistributeCache<K, V>(val distributeCacheRepository: DistributeCacheRepository<K, V>,var name:String="dr_redis_cache:") : Cache<K, V> {

    @Throws(CacheException::class)
    override fun get(key: K): V {
        return distributeCacheRepository.get(key)
    }

    @Throws(CacheException::class)
    override fun put(key: K, value: V): V {
        return distributeCacheRepository.add(key, value)
    }

    @Throws(CacheException::class)
    override fun remove(key: K): V {
        return distributeCacheRepository.delete(key)
    }

    @Throws(CacheException::class)
    override fun clear() {
        distributeCacheRepository.clear()
    }

    override fun size(): Int {
        return distributeCacheRepository.getSize()
    }

    override fun keys(): Set<K> {
        return distributeCacheRepository.getKeys()
    }

    override fun values(): Collection<V>? {
        return distributeCacheRepository.getValues()
    }
}
