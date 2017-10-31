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
package site.yanglong.promotion.config.shiro.distribution.spring

import org.apache.shiro.session.Session
import org.apache.shiro.session.mgt.eis.CachingSessionDAO

import java.io.Serializable

/**
 * 功能:
 * Version: 1.0
 * Author: DR.YangLong
 * Date: 2014/10/3
 * Time: 9:55
 * Email:410357434@163.com
 * Editor:
 */
class RedisCacheSessionDAO : CachingSessionDAO() {

    override fun doUpdate(session: Session) {

    }

    override fun doDelete(session: Session) {

    }

    override fun doCreate(session: Session): Serializable {
        val sessionId = generateSessionId(session)
        assignSessionId(session, sessionId)
        return sessionId
    }

    override fun doReadSession(sessionId: Serializable): Session? {
        return null
    }
}
