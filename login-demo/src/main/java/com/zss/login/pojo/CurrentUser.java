package com.zss.login.pojo;


/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/10/25 17:35
 */
public class CurrentUser {

    private static final ThreadLocal<UserBO> currentUser= new ThreadLocal<>();

    public static void put(UserBO userBO){
        currentUser.set(userBO);
    }

    public static UserBO get(){
        return currentUser.get();
    }
}
