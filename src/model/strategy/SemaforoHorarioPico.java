package model.strategy;

import model.factorymethod.Semaforo;

public class SemaforoHorarioPico implements SemaforoStrategy {

    @Override
    public void configurar(Semaforo semaforo, int fluxo) {
        semaforo.setTempo(TEMPO_PADRAO * 2);
    }
}
