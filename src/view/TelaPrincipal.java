package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {
    private PainelEntradaSemaforos painelEntrada;

    public TelaPrincipal() {
        setTitle("Simulador de Trânsito Inteligente");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 350); // tamanho confortável
        setLocationRelativeTo(null); // centraliza na tela

        // Usa BorderLayout para poder centralizar
        setLayout(new BorderLayout());

        painelEntrada = new PainelEntradaSemaforos(new ConfirmarListener());

        // Painel externo para centralização total
        JPanel painelCentralizador = new JPanel(new GridBagLayout());
        painelCentralizador.add(painelEntrada); // centraliza o PainelEntradaSemaforos
        add(painelCentralizador, BorderLayout.CENTER);

        setVisible(true);
    }

    private class ConfirmarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int n = painelEntrada.getNumeroSemaforos();
                if (n <= 0) {
                    JOptionPane.showMessageDialog(
                            TelaPrincipal.this,
                            "Digite um número positivo.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                dispose(); // Fecha esta janela
                new TelaSimulacao(n); // Abre a nova tela com a simulação

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        TelaPrincipal.this,
                        "Digite um número válido.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaPrincipal::new);
    }
}
