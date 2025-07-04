package model.factorymethod;

public class SemaforoSimplesCreator extends SemaforoCreator {
    @Override
    public Semaforo criarSemaforo() {
        return new SemaforoSimples();
    }
}
