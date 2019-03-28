package com.whaa.dingtalk.core;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TokenProcessor {
    private TokenProcessor() {
    }

    private static TokenProcessor instance = new TokenProcessor();

    public static TokenProcessor getInstance() {
        return instance;
    }

    public String generateTokeCode() {
        String value = System.currentTimeMillis() + new Random().nextInt() + "";
        long currentTime = System.currentTimeMillis();
        //获取数据指纹，指纹是唯一的
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒");
        Date date = new Date(currentTime);
        try {
            value += formatter.format(date);
            MessageDigest md = MessageDigest.getInstance("md5");
            //产生数据的指纹
            byte[] b = md.digest(value.getBytes());
            //Base64编码
            BASE64Encoder be = new BASE64Encoder();
            return be.encode(b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}