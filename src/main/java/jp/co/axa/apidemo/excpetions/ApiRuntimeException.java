package jp.co.axa.apidemo.excpetions;

public class ApiRuntimeException extends RuntimeException{
    public ApiRuntimeException(String message) {
        super(message);
    }
}
