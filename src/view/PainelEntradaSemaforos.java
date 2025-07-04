package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PainelEntradaSemaforos extends JPanel {
    private JTextField campoNumero;
    private JButton botaoConfirmar;

    public PainelEntradaSemaforos(ActionListener confirmarAcao) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // margem

        // Título e explicação com HTML centralizado
        JLabel mensagem = new JLabel(
                "<html><div style='text-align: center;'>"
                        + "<h2>Simulador de Tráfego Inteligente</h2>"
                        + "O número de cruzamentos que a avenida possuir<br>"
                        + "será equivalente ao seu número de semáforos."
                        + "</div></html>"
        );
        mensagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(mensagem);

        add(Box.createVerticalStrut(30)); // espaço abaixo da mensagem

        JLabel label = new JLabel("Número de cruzamentos da Avenida:");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label);

        add(Box.createVerticalStrut(10));

        campoNumero = new JTextField(10);
        campoNumero.setMaximumSize(new Dimension(200, 30));
        campoNumero.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(campoNumero);

        add(Box.createVerticalStrut(15));

        botaoConfirmar = new JButton("Confirmar");
        botaoConfirmar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoConfirmar.addActionListener(confirmarAcao);
        add(botaoConfirmar);
    }

    public int getNumeroSemaforos() throws NumberFormatException {
        return Integer.parseInt(campoNumero.getText());
    }
}
