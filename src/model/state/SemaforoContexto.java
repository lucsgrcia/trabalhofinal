package model.state;

public class SemaforoContexto {
    private EstadoSemaforo estado;
    private int tempoVerde;
    private int tempoDecorrido;
    private boolean transicaoAmarelo;

    public SemaforoContexto() {
        this.estado = new EstadoVermelho();
        this.tempoVerde = 30; // Valor padrão
        this.tempoDecorrido = 0;
        this.transicaoAmarelo = false;
    }

    public void setEstado(EstadoSemaforo estado) {
        this.estado = estado;
        this.tempoDecorrido = 0;
        this.transicaoAmarelo = false;
    }

    public void proximoEstado() {
        tempoDecorrido++;

        if (estado instanceof EstadoVerde) {
            if (tempoDecorrido >= tempoVerde - 3 && !transicaoAmarelo) {
                estado.proximoEstado(this);
                transicaoAmarelo = true;
            }
        } else if (estado instanceof EstadoAmarelo) {
            if (tempoDecorrido >= 3) {
                estado.proximoEstado(this);
            }
        } else if (estado instanceof EstadoVermelho) {
            if (tempoDecorrido >= tempoVerde) {
                estado.proximoEstado(this);
            }
        }
    }

    public String getEstado() {
        return estado.getEstado();
    }

    public int getTempoVerde() {
        return tempoVerde;
    }

    public void setTempoVerde(int tempo) {
        this.tempoVerde = tempo;
    }
}