package model.strategy;

import model.factorymethod.Semaforo;

public interface SemaforoStrategy {
    int TEMPO_PADRAO = 30;
    void configurar(Semaforo semaforo, int fluxo);
}
