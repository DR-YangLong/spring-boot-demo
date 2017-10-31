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

/**
 * 功能或描述：缓存DAO
 *
 * @Author: DR.YangLong
 * @Date: 14-9-30
 * @Time: 上午10:06
 * @Email: 410357434@163.com
 * @Version: 1.0
 * @Module: 修正:            日期：
 */
interface DistributeCacheRepository<K, V> {

    /**
     * 获取缓存的值
     *
     * @param key key
     * @return 值
     */
    fun get(key:K):V

    /**
     * 新增或更新缓存
     *
     * @param key   key
     * @param value 值
     * @return 值
     */
    fun add(key:K,value:V):V

    /**
     * 删除缓存
     *
     * @param key key
     * @return 删除的缓存
     */
    fun delete(key:K):V

    /**
     * 清除所有缓存
     */
    fun clear()

    /**
     * 获取缓存的数量
     *
     * @return 缓存数量
     */
    fun getSize():Int

    /**
     * 获取所有缓存的KEY
     *
     * @return Keys
     */
     fun getKeys():Set<K>

    /**
     * 获取缓存中的所有值
     *
     * @return 集合
     */
     fun getValues():Collection<V>?
}
