package exceptions;

public class DadosInexistentesException extends RuntimeException {
    public DadosInexistentesException(String message) {
        super(message);
    }
    public DadosInexistentesException() {
        super();
    }
    
}
