package br.com.estacionamento;

import br.com.estacionamento.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    private static List<Vaga> vagas = new ArrayList<>();
    private static List<Reserva> reservas = new ArrayList<>();
    private static List<Pagamento> pagamentos = new ArrayList<>();

    private static final double PRECO_POR_HORA = 10.0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Criar vagas iniciais
        vagas.add(new Vaga("A1", "média"));
        vagas.add(new Vaga("A2", "pequena"));
        vagas.add(new Vaga("B1", "grande"));

        System.out.println("=== Sistema de Estacionamento ===");

        boolean continuar = true;
        while (continuar) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Fazer reserva (entrada)");
            System.out.println("2 - Finalizar reserva (saída)");
            System.out.println("3 - Listar reservas ativas");
            System.out.println("4 - Listar reservas finalizadas");
            System.out.println("5 - Listar vagas e status");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (opcao) {
                case 1:
                    fazerReserva(scanner);
                    break;
                case 2:
                    finalizarReserva(scanner);
                    break;
                case 3:
                    listarReservasPorStatus("ativa");
                    break;
                case 4:
                    listarReservasPorStatus("finalizada");
                    break;
                case 5:
                    listarVagas();
                    break;
                case 0:
                    continuar = false;
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }

        scanner.close();
    }

    private static void fazerReserva(Scanner scanner) {
        System.out.println("\n-- Fazer Reserva --");

        // Verificar vaga disponível
        Vaga vagaDisponivel = null;
        for (Vaga v : vagas) {
            if (v.estaDisponivel()) {
                vagaDisponivel = v;
                break;
            }
        }
        if (vagaDisponivel == null) {
            System.out.println("Nenhuma vaga disponível no momento.");
            return;
        }

        System.out.print("Nome do cliente: ");
        String nomeCliente = scanner.nextLine();

        System.out.print("CPF do cliente: ");
        String cpfCliente = scanner.nextLine();

        System.out.print("Telefone do cliente: ");
        String telefoneCliente = scanner.nextLine();

        System.out.print("E-mail do cliente: ");
        String emailCliente = scanner.nextLine();

        System.out.print("Placa do carro: ");
        String placaCarro = scanner.nextLine();

        System.out.print("Modelo do carro: ");
        String modeloCarro = scanner.nextLine();

        System.out.print("Cor do carro: ");
        String corCarro = scanner.nextLine();

        Carro carro = new Carro(placaCarro, modeloCarro, corCarro);
        Cliente cliente = new Cliente(nomeCliente, cpfCliente, telefoneCliente, emailCliente, carro);

        // Ocupa a vaga
        vagaDisponivel.ocuparVaga(carro);

        LocalDateTime entrada = LocalDateTime.now();
        Reserva reserva = new Reserva(cliente, vagaDisponivel, "ativa", entrada, null);
        reservas.add(reserva);

        System.out.println("Reserva criada com sucesso! Vaga " + vagaDisponivel.getNumeroVaga() + " ocupada às " + entrada);
    }

    private static void finalizarReserva(Scanner scanner) {
        System.out.println("\n-- Finalizar Reserva --");

        // Listar reservas ativas
        List<Reserva> ativas = new ArrayList<>();
        for (Reserva r : reservas) {
            if ("ativa".equalsIgnoreCase(r.getStatus())) {
                ativas.add(r);
            }
        }

        if (ativas.isEmpty()) {
            System.out.println("Não há reservas ativas.");
            return;
        }

        System.out.println("Reservas Ativas:");
        for (int i = 0; i < ativas.size(); i++) {
            Reserva r = ativas.get(i);
            System.out.printf("%d - Cliente: %s, Vaga: %s, Entrada: %s\n",
                    i + 1,
                    r.getCliente().getNome(),
                    r.getVaga().getNumeroVaga(),
                    r.getHoraEntrada());
        }

        System.out.print("Escolha o número da reserva para finalizar: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha < 1 || escolha > ativas.size()) {
            System.out.println("Número inválido.");
            return;
        }

        Reserva reserva = ativas.get(escolha - 1);

        System.out.print("Quantas horas o cliente ficou estacionado? ");
        long horas = scanner.nextLong();
        scanner.nextLine();

        LocalDateTime saida = reserva.getHoraEntrada().plusHours(horas);
        reserva.setHoraSaida(saida);
        reserva.setStatus("finalizada");

        // Liberar vaga
        Vaga vaga = reserva.getVaga();
        vaga.liberarVaga();

        // Calcular valor e criar pagamento
        double valorAPagar = horas * PRECO_POR_HORA;
        Pagamento pagamento = new Pagamento(
                "PG" + System.currentTimeMillis(),
                (float) valorAPagar,
                LocalDateTime.now(),
                "dinheiro",
                reserva.getCliente().getCarro(),
                valorAPagar);
        pagamentos.add(pagamento);

        System.out.println("Reserva finalizada.");
        System.out.println("Pagamento registrado: " + pagamento);
        System.out.println("Vaga " + vaga.getNumeroVaga() + " liberada.");
    }

    private static void listarReservasPorStatus(String status) {
        System.out.println("\n-- Reservas " + status.toUpperCase() + " --");
        boolean tem = false;
        for (Reserva r : reservas) {
            if (status.equalsIgnoreCase(r.getStatus())) {
                System.out.println(r);
                tem = true;
            }
        }
        if (!tem) {
            System.out.println("Nenhuma reserva com status " + status + ".");
        }
    }

    private static void listarVagas() {
        System.out.println("\n-- Vagas --");
        for (Vaga v : vagas) {
            System.out.println(v);
        }
    }
}
