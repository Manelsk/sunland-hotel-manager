package exceptions;

/**
 * Exceção lançada quando dados fornecidos são inválidos.
 * 
 * Esta exceção é utilizada quando os dados informados não atendem aos critérios
 * de validação esperados pela aplicação, como CPF, email, nome ou outros campos
 * que possuem regras de validação específicas.
 * 
 * @author Sistema Sunland Hotel Manager
 * @version 1.0
 * @since 1.0
 */
public class DadosInvalidosException extends RuntimeException {
    /**
     * Constrói uma exceção DadosInvalidosException com mensagem de erro.
     * 
     * @param message a mensagem de erro descrevendo o motivo da exceção
     */
    public DadosInvalidosException(String message) {
        super(message);
    }
    
    /**
     * Constrói uma exceção DadosInvalidosException sem mensagem.
     */
    public DadosInvalidosException() {
        super();
    }
}
