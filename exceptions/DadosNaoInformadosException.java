package exceptions;

public class DadosNaoInformadosException extends IllegalArgumentException {
    public DadosNaoInformadosException(String message) {
        super(message);
    }
    public DadosNaoInformadosException() {
        super();
    }
}