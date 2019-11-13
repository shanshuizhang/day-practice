package com.zss.mini.http.core.handle.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/4 15:45
 */
@AllArgsConstructor
@Data
public class BaseResponse<T> {

    /**
     * 返回的响应类型
     */
    private int code;

    private T data;

    private String msg;
}
