package model.strategy;

import model.factorymethod.Semaforo;

public class SemaforoFluxoLeve implements SemaforoStrategy {
    @Override
    public void configurar(Semaforo semaforo, int fluxo) {
        semaforo.setTempo(TEMPO_PADRAO);
    }
}
