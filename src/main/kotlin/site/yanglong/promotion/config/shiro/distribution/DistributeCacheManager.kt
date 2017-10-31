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


import org.apache.shiro.cache.AbstractCacheManager
import org.apache.shiro.cache.Cache
import org.apache.shiro.cache.CacheException

/**
 * 功能或描述：分布式缓存管理器
 *
 * @Author: DR.YangLong
 * @Date: 14-9-30
 * @Time: 上午9:29
 * @Email: 410357434@163.com
 * @Version: 1.0
 * @Module: 修正:            日期：
 */
class DistributeCacheManager<K,V>(private val factory:DistributeCacheFactory) : AbstractCacheManager() {


    @Throws(CacheException::class)
    override fun createCache(name: String): Cache<K, V> {
        return factory.getCache(name)
    }
}
