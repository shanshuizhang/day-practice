package com.zss.mini.http.common.em;

import com.zss.mini.http.common.config.MyFrameConfig;
import lombok.Getter;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/4 14:54
 */
@Getter
public enum PageMappingEnum {

    INDEX_PAGE(200,MyFrameConfig.INDEX_PAGE),
    NOT_FOUND_PAGE(404, MyFrameConfig.NOT_FOUND_PAGE),
    UNKOWN_EXCEPTION_PAGE(500, MyFrameConfig.UNKNOW_EXCEPTION_PAGE);

    private int code;

    private String path;

    PageMappingEnum(int code, String path) {
        this.code = code;
        this.path = path;
    }

    public static String getPath(int code) {
        for (PageMappingEnum pageMappingEnum : PageMappingEnum.values()) {
            if (pageMappingEnum.getCode() == code) {
                return pageMappingEnum.getPath();
            }
        }
        return null;
    }
}
