package br.com.estacionamento.model;

import java.time.LocalDateTime;

public class Relatorio {
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private int totalVeiculosEntrados;
    private int totalVeiculosSaiu;
    private double faturamentoTotal;

    // Construtor
    public Relatorio(LocalDateTime dataInicio, LocalDateTime dataFim, int totalVeiculosEntrados, int totalVeiculosSaiu, double faturamentoTotal) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.totalVeiculosEntrados = totalVeiculosEntrados;
        this.totalVeiculosSaiu = totalVeiculosSaiu;
        this.faturamentoTotal = faturamentoTotal;
    }

    // Getters e Setters
    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public int getTotalVeiculosEntrados() {
        return totalVeiculosEntrados;
    }

    public void setTotalVeiculosEntrados(int totalVeiculosEntrados) {
        this.totalVeiculosEntrados = totalVeiculosEntrados;
    }

    public int getTotalVeiculosSaiu() {
        return totalVeiculosSaiu;
    }

    public void setTotalVeiculosSaiu(int totalVeiculosSaiu) {
        this.totalVeiculosSaiu = totalVeiculosSaiu;
    }

    public double getFaturamentoTotal() {
        return faturamentoTotal;
    }

    public void setFaturamentoTotal(double faturamentoTotal) {
        this.faturamentoTotal = faturamentoTotal;
    }

    @Override
    public String toString() {
        return "Relatorio{" +
                "dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", totalVeiculosEntrados=" + totalVeiculosEntrados +
                ", totalVeiculosSaiu=" + totalVeiculosSaiu +
                ", faturamentoTotal=" + faturamentoTotal +
                '}';
    }
}
