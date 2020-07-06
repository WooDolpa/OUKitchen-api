package com.project.toy.api.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/06
 */
public enum ManagedExceptionCode {

    AuthError                           (1),        // 인증 에러
    ServerError                         (2),        // 서버 에러
    InvalidId                           (3),        // id 파라미터 오류
    InvalidPassword                     (4),        // password 파라미터 오류
    ;

    private int errorCode;

    private ManagedExceptionCode(int errorCode) { this.errorCode = errorCode;}

    public int getErrorCode() { return this.errorCode; }

    public int toInt()
    {
        return errorCode;
    }

    private static final Map<Integer, ManagedExceptionCode> lookup = new HashMap<Integer, ManagedExceptionCode>();

    static
    {
        for (ManagedExceptionCode rt : ManagedExceptionCode.values())
            lookup.put(Integer.valueOf(rt.errorCode), rt);
    }

    public static ManagedExceptionCode get(int typeInt)
    {
        return lookup.get(typeInt);
    }

}
