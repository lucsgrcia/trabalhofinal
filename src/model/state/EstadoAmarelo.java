package model.state;

public class EstadoAmarelo implements EstadoSemaforo {
    @Override
    public void proximoEstado(SemaforoContexto contexto) {
        contexto.setEstado(new EstadoVermelho());
    }

    @Override
    public String getEstado() {
        return "AMARELO";
    }
}
