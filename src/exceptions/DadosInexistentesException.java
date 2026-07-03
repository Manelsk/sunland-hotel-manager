package exceptions;

/**
 * Exceção lançada quando dados buscados não existem no sistema.
 * 
 * Esta exceção é utilizada quando uma busca (por CPF, número de quarto,
 * ID de reserva, etc.) não encontra nenhum registro correspondente no
 * sistema, indicando que o dado procurado não existe.
 * 
 * @author Sistema Sunland Hotel Manager
 * @version 1.0
 * @since 1.0
 */
public class DadosInexistentesException extends RuntimeException {
    /**
     * Constrói uma exceção DadosInexistentesException com mensagem de erro.
     * 
     * @param message a mensagem de erro descrevendo qual dado não foi encontrado
     */
    public DadosInexistentesException(String message) {
        super(message);
    }
    
    /**
     * Constrói uma exceção DadosInexistentesException sem mensagem.
     */
    public DadosInexistentesException() {
        super();
    }
    
}
