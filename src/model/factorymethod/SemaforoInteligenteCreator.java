package model.factorymethod;

public class SemaforoInteligenteCreator extends SemaforoCreator {
    @Override
    public Semaforo criarSemaforo() {
        return new SemaforoInteligente();
    }
}
