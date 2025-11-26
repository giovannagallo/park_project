package br.com.estacionamento.model;

public class Vaga {
    private String numeroVaga;
    private String tamanho;
    private String status; // "livre" ou "ocupada"
    private Carro carro;

    // Construtor
    public Vaga(String numeroVaga, String tamanho) {
        this.numeroVaga = numeroVaga;
        this.tamanho = tamanho;
        this.status = "livre"; // por padrão a vaga começa livre
        this.carro = null;
    }

    // Getters e Setters
    public String getNumeroVaga() {
        return numeroVaga;
    }

    public void setNumeroVaga(String numeroVaga) {
        this.numeroVaga = numeroVaga;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    // Métodos úteis
    public boolean estaDisponivel() {
        return "livre".equalsIgnoreCase(status);
    }

    public void ocuparVaga(Carro carro) {
        this.carro = carro;
        this.status = "ocupada";
    }

    public void liberarVaga() {
        this.carro = null;
        this.status = "livre";
    }

    @Override
    public String toString() {
        return "Vaga{" +
                "numeroVaga='" + numeroVaga + '\'' +
                ", tamanho='" + tamanho + '\'' +
                ", status='" + status + '\'' +
                ", carro=" + carro +
                '}';
    }
}
