package com.zss.login.filter;

import com.zss.login.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 比较时间戳
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/10/28 18:22
 */
@Slf4j
public class CompareKickOutFilter extends KickOutFilter {

    @Override
    public boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("CompareKickOutFilter 的 isAccessAllowed方法执行了");
        String token = request.getHeader("Authorization");
        String username = JWTUtil.getUsername(token);
        String userKey = PREFIX + username;

        RBucket<String> bucket = redissonClient.getBucket(userKey);
        String redisToken = bucket.get();

        if (token.equals(redisToken)) {
            return true;

        } else if (StringUtils.isBlank(redisToken)) {
            bucket.set(token);

        }else {
            Long redisTokenUnixTime = JWTUtil.getClaim(redisToken, "createTime").asLong();
            Long tokenUnixTime = JWTUtil.getClaim(token, "createTime").asLong();

            // token > redisToken 则覆盖
            if (tokenUnixTime.compareTo(redisTokenUnixTime) > 0) {
                bucket.set(token);

            } else {
                // 注销当前token
                userService.logout(token);
                sendJsonResponse(response, 4001, "您的账号已在其他设备登录");
                return false;

            }
        }
        return true;
    }
}
