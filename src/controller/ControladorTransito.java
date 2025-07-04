package controller;

import model.factorymethod.Semaforo;
import model.strategy.SemaforoStrategy;

import java.util.List;

public class ControladorTransito {
    private SemaforoStrategy strategy;

    public ControladorTransito(SemaforoStrategy strategy) {
        this.strategy = strategy;
    }

    public void configurarSemaforos(List<Semaforo> semaforos, int fluxo) {
        for (Semaforo s : semaforos) {
            strategy.configurar(s, fluxo);
        }
    }
}
