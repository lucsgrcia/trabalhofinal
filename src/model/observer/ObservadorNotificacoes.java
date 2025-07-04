package model.observer;

import javax.swing.*;

public class ObservadorNotificacoes implements Observador {

    private final JTextArea painel;

    public ObservadorNotificacoes(JTextArea painel) {
        this.painel = painel;
    }

    @Override
    public void atualizar(String mensagem) {
        SwingUtilities.invokeLater(() -> {
            painel.append(mensagem + "\n");
            painel.setCaretPosition(painel.getDocument().getLength());
        });
    }
}
