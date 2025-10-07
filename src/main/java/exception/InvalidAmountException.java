package exception;

/**
 * 金額不合法例外 - 用於輸入負數、0 或非數字時
 */
public class InvalidAmountException extends RuntimeException {

    public InvalidAmountException(String message) {
        super(message);
    }
}
