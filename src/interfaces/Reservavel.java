package interfaces;

/**
 * Interface que define o contrato para objetos que podem ser reservados.
 * 
 * Define operações básicas de reserva: reservar, liberar e verificar
 * disponibilidade. Implementada por classes que representam recursos
 * do hotel que podem ser reservados.
 * 
 * @author Sistema Sunland Hotel Manager
 * @version 1.0
 * @since 1.0
 */
public interface Reservavel{
    
    /**
     * Marca o recurso como reservado.
     */
    public void reservar();
    
    /**
     * Libera o recurso, marcando-o como disponível.
     */
    public void liberar();
    
    /**
     * Verifica se o recurso está disponível para reserva.
     * 
     * @return true se o recurso está disponível; false caso contrário
     */
    public boolean estaDisponivel();
}