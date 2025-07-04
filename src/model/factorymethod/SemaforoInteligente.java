package model.factorymethod;

import model.observer.Observador;
import model.observer.Sujeito;
import model.state.SemaforoContexto;

import java.util.ArrayList;
import java.util.List;

public class SemaforoInteligente implements Semaforo, Sujeito {
    private final SemaforoContexto contexto = new SemaforoContexto();
    private final List<Observador> observadores = new ArrayList<>();
    private String ultimoEstado = "";

    @Override
    public void mudarEstado() {
        contexto.proxEstado();
        String novoEstado = contexto.getEstado();
        if (!novoEstado.equals(ultimoEstado)) {
            ultimoEstado = novoEstado;
            notificar("Semáforo " + this.hashCode() + " mudou para " + novoEstado);
        }
    }

    @Override
    public String getEstado() {
        return contexto.getEstado();
    }

    @Override
    public void setTempo(int tempo) {
        contexto.setTempoVerde(tempo);
    }

    @Override
    public int getTempo() {
        return contexto.getTempoVerde();
    }

    @Override
    public void registrar(Observador o) {
        observadores.add(o);
    }

    @Override
    public void notificar(String mensagem) {
        for (Observador o : observadores) {
            o.atualizar(mensagem);
        }
    }
}
