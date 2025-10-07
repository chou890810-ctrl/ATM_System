package exception;

/**
 * 登入失敗例外 - 用於帳號或密碼錯誤時拋出
 */
public class LoginFailedException extends RuntimeException {

    public LoginFailedException(String message) {
        super(message);
    }
}