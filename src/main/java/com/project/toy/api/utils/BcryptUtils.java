package com.project.toy.api.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/06
 */
public class BcryptUtils {


    public static void main(String[] args) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String  bCryptStr = bCryptPasswordEncoder.encode("1234");

        System.out.println("bCryptStr:"+ bCryptStr);
    }

}
