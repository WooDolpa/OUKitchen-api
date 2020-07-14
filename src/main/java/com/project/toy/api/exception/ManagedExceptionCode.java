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
    InvalidId                           (3),        // 사용자 아이디 파라미터 오류
    InvalidPassword                     (4),        // 사용자 비밀번호 파라미터 오류
    NoExistUser                         (5),        // 존재하지 않은 사용자
    MisMatchPassword                    (6),        // 비밀번호 불일치
    InActiveUser                        (7),        // 이용 불가능한 사용자
    AlreadyUserId                       (8),        // 이미 존재하는 아이디
    InvalidUserNo                       (9),        // 사용자 번호 파라미터 오류
    InvalidUserStatus                   (10),       // 사용자 상태 파라미터 오류
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
