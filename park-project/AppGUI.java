package br.com.estacionamento;

import br.com.estacionamento.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppGUI extends JFrame {

    private List<Vaga> vagas = new ArrayList<>();
    private List<Reserva> reservas = new ArrayList<>();
    private List<Pagamento> pagamentos = new ArrayList<>();

    private static final double PRECO_POR_HORA = 10.0;

    public AppGUI() {
        setTitle("Sistema de Estacionamento");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Criar vagas iniciais
        vagas.add(new Vaga("A1", "média"));
        vagas.add(new Vaga("A2", "pequena"));
        vagas.add(new Vaga("B1", "grande"));

        // Layout com botões
        setLayout(new GridLayout(5, 1, 10, 10));

        JButton btnFazerReserva = new JButton("Fazer Reserva (Entrada)");
        JButton btnFinalizarReserva = new JButton("Finalizar Reserva (Saída)");
        JButton btnListarVagas = new JButton("Listar Vagas");
        JButton btnListarReservas = new JButton("Listar Reservas");
        JButton btnSair = new JButton("Sair");

        add(btnFazerReserva);
        add(btnFinalizarReserva);
        add(btnListarVagas);
        add(btnListarReservas);
        add(btnSair);

        // Ações dos botões
        btnFazerReserva.addActionListener(e -> fazerReserva());
        btnFinalizarReserva.addActionListener(e -> finalizarReserva());
        btnListarVagas.addActionListener(e -> listarVagas());
        btnListarReservas.addActionListener(e -> listarReservas());
        btnSair.addActionListener(e -> System.exit(0));
    }

    private void fazerReserva() {
        Vaga vagaDisponivel = vagas.stream().filter(Vaga::estaDisponivel).findFirst().orElse(null);
        if (vagaDisponivel == null) {
            JOptionPane.showMessageDialog(this, "Nenhuma vaga disponível.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField nome = new JTextField();
        JTextField cpf = new JTextField();
        JTextField telefone = new JTextField();
        JTextField email = new JTextField();
        JTextField placa = new JTextField();
        JTextField modelo = new JTextField();
        JTextField cor = new JTextField();

        Object[] message = {
                "Nome:", nome,
                "CPF:", cpf,
                "Telefone:", telefone,
                "E-mail:", email,
                "Placa do carro:", placa,
                "Modelo do carro:", modelo,
                "Cor do carro:", cor
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Fazer Reserva", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // Criar objetos
            Carro carro = new Carro(placa.getText(), modelo.getText(), cor.getText());
            Cliente cliente = new Cliente(nome.getText(), cpf.getText(), telefone.getText(), email.getText(), carro);

            vagaDisponivel.ocuparVaga(carro);
            Reserva reserva = new Reserva(cliente, vagaDisponivel, "ativa", LocalDateTime.now(), null);
            reservas.add(reserva);

            JOptionPane.showMessageDialog(this, "Reserva criada! Vaga " + vagaDisponivel.getNumeroVaga() + " ocupada.");
        }
    }

    private void finalizarReserva() {
        List<Reserva> ativas = new ArrayList<>();
        for (Reserva r : reservas) {
            if ("ativa".equalsIgnoreCase(r.getStatus())) {
                ativas.add(r);
            }
        }

        if (ativas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há reservas ativas.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] opcoes = new String[ativas.size()];
        for (int i = 0; i < ativas.size(); i++) {
            Reserva r = ativas.get(i);
            opcoes[i] = String.format("%s - Vaga %s (Entrada: %s)", r.getCliente().getNome(), r.getVaga().getNumeroVaga(), r.getHoraEntrada());
        }

        String escolha = (String) JOptionPane.showInputDialog(this, "Selecione a reserva para finalizar:", "Finalizar Reserva",
                JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);

        if (escolha == null) return;

        // Buscar reserva selecionada
        Reserva reservaSelecionada = null;
        for (Reserva r : ativas) {
            String desc = String.format("%s - Vaga %s (Entrada: %s)", r.getCliente().getNome(), r.getVaga().getNumeroVaga(), r.getHoraEntrada());
            if (desc.equals(escolha)) {
                reservaSelecionada = r;
                break;
            }
        }

        if (reservaSelecionada == null) return;

        String horasStr = JOptionPane.showInputDialog(this, "Informe o número de horas estacionadas:", "Horas Estacionadas", JOptionPane.PLAIN_MESSAGE);
        if (horasStr == null) return;

        try {
            long horas = Long.parseLong(horasStr);
            LocalDateTime saida = reservaSelecionada.getHoraEntrada().plusHours(horas);
            reservaSelecionada.setHoraSaida(saida);
            reservaSelecionada.setStatus("finalizada");

            Vaga vaga = reservaSelecionada.getVaga();
            vaga.liberarVaga();

            double valorAPagar = horas * PRECO_POR_HORA;
            Pagamento pagamento = new Pagamento(
                    "PG" + System.currentTimeMillis(),
                    (float) valorAPagar,
                    LocalDateTime.now(),
                    "dinheiro",
                    reservaSelecionada.getCliente().getCarro(),
                    valorAPagar
            );
            pagamentos.add(pagamento);

            JOptionPane.showMessageDialog(this,
                    "Reserva finalizada.\nValor a pagar: R$ " + valorAPagar +
                            "\nVaga " + vaga.getNumeroVaga() + " liberada.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Número inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarVagas() {
        StringBuilder sb = new StringBuilder();
        for (Vaga v : vagas) {
            sb.append(v.toString()).append("\n");
        }
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Lista de Vagas", JOptionPane.INFORMATION_MESSAGE);
    }

    private void listarReservas() {
        StringBuilder sb = new StringBuilder();
        for (Reserva r : reservas) {
            sb.append(r.toString()).append("\n");
        }
        if (reservas.isEmpty()) {
            sb.append("Nenhuma reserva cadastrada.");
        }
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Lista de Reservas", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AppGUI().setVisible(true);
        });
    }
}
