package models;

public class DadosInvalidosException extends IllegalArgumentException {
    public DadosInvalidosException(String message) {
        super(message);
    }
    public DadosInvalidosException() {
        super();
    }
}
