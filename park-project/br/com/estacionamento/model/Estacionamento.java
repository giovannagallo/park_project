package br.com.estacionamento.model;

import java.util.ArrayList;
import java.util.List;

public class Estacionamento {
    private String nome;
    private String localizacao;
    private List<Vaga> vagas;
    private double precoPorHora;
    private int capacidade;

    // Construtor
    public Estacionamento(String nome, String localizacao, double precoPorHora, int capacidade) {
        this.nome = nome;
        this.localizacao = localizacao;
        this.precoPorHora = precoPorHora;
        this.capacidade = capacidade;
        this.vagas = new ArrayList<>();
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public List<Vaga> getVagas() {
        return vagas;
    }

    public void setVagas(List<Vaga> vagas) {
        this.vagas = vagas;
    }

    public double getPrecoPorHora() {
        return precoPorHora;
    }

    public void setPrecoPorHora(double precoPorHora) {
        this.precoPorHora = precoPorHora;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    // Métodos úteis
    public void adicionarVaga(Vaga vaga) {
        if (vagas.size() < capacidade) {
            vagas.add(vaga);
        } else {
            System.out.println("Capacidade máxima atingida. Não é possível adicionar mais vagas.");
        }
    }

    public void removerVaga(Vaga vaga) {
        vagas.remove(vaga);
    }

    public List<Vaga> listarVagasDisponiveis() {
        List<Vaga> disponiveis = new ArrayList<>();
        for (Vaga v : vagas) {
            if ("livre".equalsIgnoreCase(v.getStatus())) {
                disponiveis.add(v);
            }
        }
        return disponiveis;
    }

    @Override
    public String toString() {
        return "Estacionamento{" +
                "nome='" + nome + '\'' +
                ", localizacao='" + localizacao + '\'' +
                ", precoPorHora=" + precoPorHora +
                ", capacidade=" + capacidade +
                ", totalVagas=" + vagas.size() +
                '}';
    }
}
