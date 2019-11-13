package com.zss.mini.http.model;

import com.zss.mini.http.common.constant.RequestConstants;
import com.zss.mini.http.common.em.PageMappingEnum;
import lombok.Data;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/4 15:37
 */
@Data
public class PageModel {

    private int code;

    private String pagePath;

    public PageModel() {
        code = RequestConstants.INDEX_CODE;
        pagePath = PageMappingEnum.getPath(RequestConstants.INDEX_CODE);
    }

    public PageModel(String pagePath) {
        this.code = RequestConstants.SUCCESS_CODE;
        this.pagePath = pagePath;
    }
}
