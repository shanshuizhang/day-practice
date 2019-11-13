package com.zss.mini.http.core.handle.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/4 16:26
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ResponseEnum {

    NO_PATH_MAPPING(404,"路径搜索不到");

    private Integer code;

    private String des;
}
