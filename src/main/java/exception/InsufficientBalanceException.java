package exception;

/**
 * 餘額不足例外 - 用於提款金額大於帳戶餘額時
 */
public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
