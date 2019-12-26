package com.zss.login.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/14 14:08
 */
public class CollectionUtil {

    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList<>();
            list1.add(1);
            list1.add(2);
            list1.add(3);
            list1.add(4);

        List<Integer> list2 = new ArrayList<>();
        list2.add(2);
        list2.add(3);
        list2.add(4);
        list2.add(5);

        List<Integer> uList = list1.stream().filter(itme -> !list2.contains(itme)).collect(Collectors.toList());
        uList.stream().forEach(System.out::println);

        List<Integer> nList = list2.stream().filter(itme -> !list1.contains(itme)).collect(Collectors.toList());
        nList.stream().forEach(System.out::println);
    }
}
