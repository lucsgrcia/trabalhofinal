package model.state;

public class EstadoVermelho implements EstadoSemaforo {
    @Override
    public void proximoEstado(SemaforoContexto contexto) {
        contexto.setEstado(new EstadoVerde());
    }

    @Override
    public String getEstado() {
        return "VERMELHO";
    }
}