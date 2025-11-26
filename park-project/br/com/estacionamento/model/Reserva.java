package br.com.estacionamento.model;

import java.time.LocalDateTime;

public class Reserva {
    private Cliente cliente;
    private Vaga vaga;
    private String status; // exemplo: "ativa", "finalizada", "cancelada"
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSaida;

    // Construtor
    public Reserva(Cliente cliente, Vaga vaga, String status, LocalDateTime horaEntrada, LocalDateTime horaSaida) {
        this.cliente = cliente;
        this.vaga = vaga;
        this.status = status;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
    }

    // Getters e Setters
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalDateTime getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(LocalDateTime horaSaida) {
        this.horaSaida = horaSaida;
    }

    // Método para calcular duração da reserva (em horas)
    public long calcularDuracaoHoras() {
        if (horaEntrada != null && horaSaida != null) {
            return java.time.Duration.between(horaEntrada, horaSaida).toHours();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "cliente=" + cliente +
                ", vaga=" + vaga +
                ", status='" + status + '\'' +
                ", horaEntrada=" + horaEntrada +
                ", horaSaida=" + horaSaida +
                ", duracaoHoras=" + calcularDuracaoHoras() +
                '}';
    }
}
