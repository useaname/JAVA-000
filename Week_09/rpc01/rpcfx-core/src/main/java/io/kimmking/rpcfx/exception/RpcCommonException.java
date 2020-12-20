package io.kimmking.rpcfx.exception;


public class RpcCommonException extends Exception{

    private String errorCode;
    private String mssage;

    public RpcCommonException() {
        super();
    }

    public RpcCommonException(String message, String errorCode) {
        this.mssage = message;
        this.errorCode = errorCode;
    }

    public RpcCommonException(String message) {
        this.mssage = message;
    }

    public RpcCommonException(Throwable cause) {
        super(cause);
    }
}
