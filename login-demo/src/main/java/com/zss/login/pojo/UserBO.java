package com.zss.login.pojo;

import lombok.Data;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/10/25 17:31
 */
@Data
public class UserBO {

    private String username;

    private String password;

    private String realName;

    private Boolean kickOut;
}
