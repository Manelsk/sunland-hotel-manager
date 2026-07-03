package exceptions;

/**
 * Exceção lançada quando dados obrigatórios não foram informados.
 * 
 * Esta exceção é utilizada quando um objeto obrigatório ou um campo
 * requerido recebe um valor null ou vazio, indicando que informações
 * necessárias estão faltando.
 * 
 * @author Sistema Sunland Hotel Manager
 * @version 1.0
 * @since 1.0
 */
public class DadosNaoInformadosException extends RuntimeException {
    /**
     * Constrói uma exceção DadosNaoInformadosException com mensagem de erro.
     * 
     * @param message a mensagem de erro descrevendo qual dado não foi informado
     */
    public DadosNaoInformadosException(String message) {
        super(message);
    }
    
    /**
     * Constrói uma exceção DadosNaoInformadosException sem mensagem.
     */
    public DadosNaoInformadosException() {
        super();
    }
}