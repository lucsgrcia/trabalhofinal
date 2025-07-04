package model.state;

public interface EstadoSemaforo {
    void proximoEstado(SemaforoContexto contexto);
    String getEstado();
}
