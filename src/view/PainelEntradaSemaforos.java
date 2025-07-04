package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PainelEntradaSemaforos extends JPanel {
    private JTextField campoNumero;
    private JButton botaoConfirmar;

    public PainelEntradaSemaforos(ActionListener confirmarAcao) {
        setLayout(new FlowLayout());

        add(new JLabel("Número de semáforos (inteligentes e simples):"));

        campoNumero = new JTextField(5);
        add(campoNumero);

        botaoConfirmar = new JButton("Confirmar");
        botaoConfirmar.addActionListener(confirmarAcao);
        add(botaoConfirmar);
    }

    public int getNumeroSemaforos() throws NumberFormatException {
        return Integer.parseInt(campoNumero.getText());
    }
}
