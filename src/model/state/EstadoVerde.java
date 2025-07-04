package model.state;

public class EstadoVerde implements EstadoSemaforo {
    @Override
    public void proximoEstado(SemaforoContexto contexto) {
        contexto.setEstado(new EstadoAmarelo());
    }

    @Override
    public String getEstado() {
        return "VERDE";
    }
}