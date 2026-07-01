package exceptions;

public class DadosInexistentesException extends IllegalArgumentException {
    public DadosInexistentesException(String message) {
        super(message);
    }
    public DadosInexistentesException() {
        super();
    }
    
}
