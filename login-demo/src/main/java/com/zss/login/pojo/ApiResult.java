package com.zss.login.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/10/25 17:42
 */
@Data
@ToString
@AllArgsConstructor
public class ApiResult<T> {

    private Integer code;

    private String message;

    private T data;
}
