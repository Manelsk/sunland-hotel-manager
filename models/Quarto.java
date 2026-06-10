public class Quarto {
    private int numero;
    private double precoBase;
    private int capacidade;
    private String status;

    public Quarto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }
}