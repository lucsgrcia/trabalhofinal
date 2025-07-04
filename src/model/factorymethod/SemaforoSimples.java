package model.factorymethod;

import model.state.SemaforoContexto;
import model.observer.Observador;
import model.observer.Sujeito;

import java.util.ArrayList;
import java.util.List;

public class SemaforoSimples implements Semaforo, Sujeito {
    private SemaforoContexto contexto;
    private final List<Observador> observadores = new ArrayList<>();
    private String ultimoEstado = "";

    public SemaforoSimples() {
        contexto = new SemaforoContexto();
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
    public void mudarEstado() {
        contexto.proxEstado();

        String novoEstado = contexto.getEstado();
        if (!novoEstado.equals(ultimoEstado)) {          // evita duplicidade
            ultimoEstado = novoEstado;
            notificar("Semáforo " + hashCode()
                    + " mudou para " + novoEstado);
        }
    }

    @Override
    public String getEstado() {
        return "Semáforo Simples : " + contexto.getEstado();
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
