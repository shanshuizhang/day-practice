package com.zss.login.filter;

import com.zss.login.pojo.CurrentUser;
import com.zss.login.pojo.UserBO;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RDeque;
import org.redisson.api.RLock;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/10/28 18:55
 */
@Slf4j
public class QueueKickOutFilter extends KickOutFilter {

    /**
     * 踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
     */
    private boolean kickoutAfter = false;
    /**
     * 同一个帐号最大会话数 默认1
     */
    private int maxSession = 2;

    @Override
    public boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("QueueKickOutFilter 的 isAccessAllowed 方法执行了");
        String token = request.getHeader("Authorization");
        UserBO currentSession = CurrentUser.get();
        Assert.notNull(currentSession, "currentSession cannot null");
        String username = currentSession.getUsername();
        String userKey = PREFIX + "deque_" + username;
        String lockKey = PREFIX_LOCK + username;

        RLock lock = redissonClient.getLock(lockKey);

        lock.lock(2, TimeUnit.SECONDS);

        try {
            RDeque<String> deque = redissonClient.getDeque(userKey);

            // 如果队列里没有此token，且用户没有被踢出；放入队列
            if (!deque.contains(token) && currentSession.getKickOut() == false) {
                deque.push(token);
            }

            // 如果队列里的sessionId数超出最大会话数，开始踢人
            while (deque.size() > maxSession) {
                String kickoutSessionId;
                if (kickoutAfter) { // 如果踢出后者
                    kickoutSessionId = deque.removeFirst();
                } else { // 否则踢出前者
                    kickoutSessionId = deque.removeLast();
                }
                log.info("kickoutSessionId是token么？：{}",kickoutSessionId);
                try {
                    RBucket<UserBO> bucket = redissonClient.getBucket(kickoutSessionId);
                    UserBO kickoutSession = bucket.get();

                    if (kickoutSession != null) {
                        // 设置会话的kickout属性表示踢出了
                        kickoutSession.setKickOut(true);
                        bucket.set(kickoutSession);
                    }

                } catch (Exception e) {
                }

            }

            // 如果被踢出了，直接退出，重定向到踢出后的地址
            if (currentSession.getKickOut()) {
                // 会话被踢出了
                try {
                    // 注销
                    userService.logout(token);
                    sendJsonResponse(response, 4001, "您的账号已在其他设备登录");

                } catch (Exception e) {
                }

                return false;

            }

        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info(Thread.currentThread().getName() + " unlock");

            } else {
                log.info(Thread.currentThread().getName() + " already automatically release lock");
            }
        }

        return true;
    }
}
