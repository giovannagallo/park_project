package br.com.estacionamento.model;

import java.time.LocalDateTime;

public class Pagamento {
    private String identificadorPagamento;
    private float valor;
    private LocalDateTime data;
    private String formaPagamento; // ex: "dinheiro", "cartão", "pix"
    private Carro carro;
    private double valorAPagar;

    // Construtor
    public Pagamento(String identificadorPagamento, float valor, LocalDateTime data, String formaPagamento, Carro carro, double valorAPagar) {
        this.identificadorPagamento = identificadorPagamento;
        this.valor = valor;
        this.data = data;
        this.formaPagamento = formaPagamento;
        this.carro = carro;
        this.valorAPagar = valorAPagar;
    }

    // Getters e Setters
    public String getIdentificadorPagamento() {
        return identificadorPagamento;
    }

    public void setIdentificadorPagamento(String identificadorPagamento) {
        this.identificadorPagamento = identificadorPagamento;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public double getValorAPagar() {
        return valorAPagar;
    }

    public void setValorAPagar(double valorAPagar) {
        this.valorAPagar = valorAPagar;
    }

    // toString
    @Override
    public String toString() {
        return "Pagamento{" +
                "identificadorPagamento='" + identificadorPagamento + '\'' +
                ", valor=" + valor +
                ", data=" + data +
                ", formaPagamento='" + formaPagamento + '\'' +
                ", carro=" + carro +
                ", valorAPagar=" + valorAPagar +
                '}';
    }
}
