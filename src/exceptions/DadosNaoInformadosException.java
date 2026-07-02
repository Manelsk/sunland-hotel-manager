package exceptions;

public class DadosNaoInformadosException extends RuntimeException {
    public DadosNaoInformadosException(String message) {
        super(message);
    }
    public DadosNaoInformadosException() {
        super();
    }
}