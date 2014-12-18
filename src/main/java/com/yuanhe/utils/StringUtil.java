package com.yuanhe.utils;

import java.util.Random;
import java.util.UUID;

/**
 * Created by dam on 2014/12/18.
 */
public class StringUtil {

    public static String genUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-","");
    }

}
