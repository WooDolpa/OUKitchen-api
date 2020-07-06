package com.project.toy.api.exception;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/06
 */
public final class ManagedException extends RuntimeException {

    private static final long serialVersionUID = -3444772420426031907L;

    private ManagedExceptionCode exceptionCode;
    private String msg;

    public ManagedException(ManagedExceptionCode exceptionCode){
        super(exceptionCode.toString());
        this.exceptionCode = exceptionCode;
    }

    public ManagedExceptionCode getExceptionCode() { return exceptionCode;}

    public String getMsg() { return msg;}

    public void setMsg(String msg) { this.msg = msg;}
}
