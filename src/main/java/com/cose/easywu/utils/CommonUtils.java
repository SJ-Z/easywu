package com.cose.easywu.utils;

import java.util.UUID;

public class CommonUtils {
    //得到指定数量的UUID，以数组的形式返回
    public static String uuid(int num){

        if(num <= 0)
            return null;

        String uuid = getUUID32();

        return uuid.substring(0, num);
    }

    //得到32位的uuid
    public static String getUUID32(){
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
