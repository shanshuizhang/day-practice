package com.zss.login.service;

import com.zss.login.pojo.UserBO;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/10/25 18:05
 */
public interface UserService {

    /**
     * 根据用户名和秘钥签名生成token，然后存储token-UserBO到redis中
     * @param userBO
     * @return
     */
    String buildUserInfo(UserBO userBO);

    /**
     * 注销
     * @param jwt
     */
    void logout(String jwt);
}
