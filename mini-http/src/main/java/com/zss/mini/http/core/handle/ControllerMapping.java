package com.zss.mini.http.core.handle;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/4 15:45
 */
@Data
@AllArgsConstructor
public class ControllerMapping {
    private String url;

    private String clazz;
}
