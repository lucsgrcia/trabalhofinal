package model.factorymethod;

import model.observer.Sujeito;

public interface Semaforo extends Sujeito {
    void mudarEstado();
    String getEstado();
    void setTempo(int tempo);
    int getTempo();
}
