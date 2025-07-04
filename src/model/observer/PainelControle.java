package model.observer;

public class PainelControle implements Observador {
    private String nome;

    public PainelControle(String nome) {
        this.nome = nome;
    }

    @Override
    public void atualizar(String mensagem) {
        System.out.println("[" + nome + "] Notificação: " + mensagem);
    }
}
