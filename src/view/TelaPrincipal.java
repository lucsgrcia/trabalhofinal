package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {
    private PainelEntradaSemaforos painelEntrada;

    public TelaPrincipal() {
        setTitle("Simulador de Trânsito Inteligente");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 150);
        setLocationRelativeTo(null);

        painelEntrada = new PainelEntradaSemaforos(new ConfirmarListener());
        add(painelEntrada);

        setVisible(true);
    }

    private class ConfirmarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int n = painelEntrada.getNumeroSemaforos();
                if (n <= 0) {
                    JOptionPane.showMessageDialog(TelaPrincipal.this, "Digite um número positivo.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                dispose(); // Fecha esta janela
                new TelaSimulacao(n); // Abre a nova tela com a simulação

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(TelaPrincipal.this, "Digite um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaPrincipal::new);
    }
}
