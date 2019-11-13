package com.zss.login.service;

import com.zss.login.pojo.UserBO;
import com.zss.login.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/10/28 10:54
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public String buildUserInfo(UserBO userBO) {
        userBO.setKickOut(false);
        String token = JWTUtil.sign(userBO.getUsername(),JWTUtil.SECRET);
        Assert.notNull(token,"token cannot null");
        RBucket rBucket = redissonClient.getBucket(token);
        rBucket.set(userBO,JWTUtil.EXPIRE_TIME_MS,TimeUnit.MILLISECONDS);
        log.info("token信息：{}",token);
        return token;
    }

    @Override
    public void logout(String jwt) {
       RBucket rBucket = redissonClient.getBucket(jwt);
       rBucket.delete();
    }
}
