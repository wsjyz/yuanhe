package com.yuanhe.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    public static String getMD5(String str) {

        try {

            // 生成一个MD5加密计算摘要

            MessageDigest md = MessageDigest.getInstance("MD5");

            // 计算md5函数

            md.update(str.getBytes());

            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符

            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值

            return new BigInteger(1, md.digest()).toString(16);

        } catch (Exception e) {

            throw new RuntimeException("MD5加密出现错误");

        }

    }
    public static String getMD5Str(String pwd) {
        //将字符串转换为字节数组
        byte[] toChapter = pwd.getBytes();
        //md5str用来保存字节数组转换成的十六进制数
        StringBuffer md5str = new StringBuffer();
        try{
            //得到一个实现特定摘要算法的消息摘要对象
            MessageDigest md5 = MessageDigest.getInstance( "MD5" );
            //将字节数组toChapter添加到待计算MD5值的字节序列中
            md5.update(toChapter);
            //计算字节序列的MD5值，返回16个字节的字节数组，保存到toChapterDigest
            byte[] toChapterDigest = md5.digest();

            //每个8位的二进制字节用十六进制表示的话，需要两个字符，每个十六进制字符对应四位二进制位
            //故16个字节(128bit)转换后，变为了32个字符的字符串，将它们添加到md5str中

            int digital;
            for (int i = 0; i <toChapterDigest.length; i++) {
                digital =toChapterDigest[i];
                //这里字节类型赋值给int类型，会按符号位扩展的
                //如果字节的最高位是1，扩展为int时它的高位(9-32位)都会变为1
                if(digital < 0) {
                    //将8位字节之前的高位全变为0
                    digital += 256;
                }
                if(digital < 16){
                    md5str.append("0");
                }
                //经过判断之后的操作，能保证digtal转换为十六进制字符的时候只得到两位
                md5str.append(Integer.toHexString(digital));
            }
        }catch( Exception e ) {
            e.printStackTrace();
        }
        //返回十六进制字符串
        return md5str.toString();
    }
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }
    // 返回形式只为数字
    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }
    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }
    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }
    public static void main(String[] args) {
        String apps = "2E669A6340E94F67B5DBAE53A84572DF";//yufa
        String chat_sign = apps+"|/v1/uc/user/isLoginAndReturnOpenUser|"+apps;
        String record_sign = apps+"|/v1/im/message/listPrivateMsgs|"+apps;
        String msg_sign = apps+"|/v1/im/message/savePrivateMsgs|"+apps;


        System.out.println(StringUtil.GetMD5Code(chat_sign).toUpperCase());
        System.out.println(StringUtil.GetMD5Code(record_sign).toUpperCase());
        System.out.println(StringUtil.GetMD5Code(msg_sign).toUpperCase());
    }

}
