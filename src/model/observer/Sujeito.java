package model.observer;

public interface Sujeito {
    void registrar(Observador o);
    void notificar(String mensagem);
}
