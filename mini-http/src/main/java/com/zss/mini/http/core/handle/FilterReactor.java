package com.zss.mini.http.core.handle;

import com.zss.mini.http.common.BaseFilter;
import com.zss.mini.http.model.ControllerRequest;
import com.zss.mini.http.model.FilterModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/4 17:54
 */
public class FilterReactor {

    public static List<FilterModel> FILTER_LIST = new ArrayList<>();

    public static void preHandler(ControllerRequest controllerRequest){
        FILTER_LIST.stream().forEach(filterModel -> {
            BaseFilter baseFilter = (BaseFilter)filterModel.getFilter();
            baseFilter.beforeFilter(controllerRequest);
        });
    }

    public static void aftHandler(ControllerRequest controllerRequest){
        FILTER_LIST.stream().forEach(filterModel -> {
            BaseFilter baseFilter = (BaseFilter)filterModel.getFilter();
            baseFilter.afterFilter(controllerRequest);
        });
    }
}
