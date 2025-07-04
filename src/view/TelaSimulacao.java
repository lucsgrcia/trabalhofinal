package view;

import controller.ControladorTransito;
import model.factorymethod.*;
import model.observer.ObservadorNotificacoes;
import model.strategy.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TelaSimulacao extends JFrame {
    private JTextArea painelNotificacoes;
    private List<Semaforo> semaforosAvenida = new ArrayList<>();
    private List<Semaforo> semaforosRuas = new ArrayList<>();
    private PainelRuas painelVisual;
    private ControladorTransito controlador;
    private int fluxo;
    private static final int TEMPO_AMARELO = 3;
    private Random random = new Random();
    private volatile boolean executando = true;  // flag controle da thread
    private Thread threadSimulacao;
    private ObservadorNotificacoes obsVisual;

    public TelaSimulacao(int numeroRuas) {
        super("Simulação de Avenida Inteligente");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 600);
        setLayout(new BorderLayout());

        // painel de notificações
        painelNotificacoes = new JTextArea();
        painelNotificacoes.setEditable(false);
        painelNotificacoes.setBorder(BorderFactory.createTitledBorder("Notificações"));
        JScrollPane scroll = new JScrollPane(painelNotificacoes);
        add(scroll, BorderLayout.EAST);
        painelNotificacoes.setPreferredSize(new Dimension(400, getHeight()));

        obsVisual = new ObservadorNotificacoes(painelNotificacoes);

        // Cria painel vertical para notificações + botão
        JPanel painelDireito = new JPanel();
        painelDireito.setLayout(new BorderLayout());

        // Scroll pane para notificações
        JScrollPane scrollNotificacoes = new JScrollPane(painelNotificacoes);
        painelDireito.add(scrollNotificacoes, BorderLayout.CENTER);

        // Botão parar
        JButton botaoParar = new JButton("Parar Simulação");
        painelDireito.add(botaoParar, BorderLayout.SOUTH);

        add(painelDireito, BorderLayout.EAST);

        // painel gráfico
        painelVisual = new PainelRuas(numeroRuas);
        add(painelVisual, BorderLayout.CENTER);

        // define fluxo e strategy
        atualizarFluxoETempo();

        // cria semáforos e registra observador
        SemaforoCreator criSimples = new SemaforoSimplesCreator();
        SemaforoCreator criInteligente = new SemaforoInteligenteCreator();

        for (int i = 0; i < numeroRuas; i++) {
            Semaforo sa = criInteligente.criarSemaforo();
            sa.registrar(obsVisual);
            semaforosAvenida.add(sa);

            Semaforo sr = criSimples.criarSemaforo();
            sr.registrar(obsVisual);
            semaforosRuas.add(sr);
            painelVisual.adicionarSemaforos(i, sa, sr);
        }

        // aplica tempos iniciais via Strategy
        controlador.configurarSemaforos(semaforosAvenida, fluxo);
        controlador.configurarSemaforos(semaforosRuas, fluxo);

        setLocationRelativeTo(null);
        setVisible(true);

        // ação do botão parar
        botaoParar.addActionListener(e -> {
            executando = false;
            notificar("Simulação parada pelo usuário.");
        });

        iniciarCicloCoordenado();
    }


    private void atualizarFluxoETempo() {
        fluxo = random.nextInt(81) + 20;  // entre 20 e 100
        if (fluxo > 60) {
            controlador = new ControladorTransito(new SemaforoHorarioPico());
        } else {
            controlador = new ControladorTransito(new SemaforoFluxoLeve());
        }
        notificar("Novo fluxo: " + fluxo + " veículos");
        painelVisual.setFluxoAtual(fluxo);

    }

    private void iniciarCicloCoordenado() {
        executando = true;  // garante flag true ao iniciar
        threadSimulacao = new Thread(() -> {
            try {
                while (executando) {
                    int tempoAvenida = semaforosAvenida.get(0).getTempo();
                    int tempoRua = 30;

                    // Fase 1: Avenida verde
                    contagemFase("AVENIDA", tempoAvenida, true);
                    if (!executando) break;

                    // Fase 2: Ruas verdes
                    contagemFase("RUAS", tempoRua, false);
                    if (!executando) break;

                    // Após o ciclo completo, recalcula fluxo e reconfigura semáforos
                    atualizarFluxoETempo();
                    controlador.configurarSemaforos(semaforosAvenida, fluxo);
                    controlador.configurarSemaforos(semaforosRuas, fluxo);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        threadSimulacao.start();
    }


    private void contagemFase(String fase, int tempoVerde, boolean avenidaVerde) throws InterruptedException {
        int tempoTotal = tempoVerde + TEMPO_AMARELO;

        List<Semaforo> ativos = avenidaVerde ? semaforosAvenida : semaforosRuas;
        List<Semaforo> inativos = avenidaVerde ? semaforosRuas : semaforosAvenida;

        // --- Parte 1: VERDE ---
        mudarEstadoTodos(ativos, "VERDE");
        mudarEstadoTodos(inativos, "VERMELHO");

        for (int i = 0; i < tempoVerde; i++) {
            atualizarTempos(ativos, tempoVerde - i);
            atualizarTempos(inativos, tempoTotal - i);
            painelVisual.repaint();
            Thread.sleep(1000);
        }

        // --- Parte 2: AMARELO ---
        mudarEstadoTodos(ativos, "AMARELO");

        for (int i = 0; i < TEMPO_AMARELO; i++) {
            atualizarTempos(ativos, TEMPO_AMARELO - i);
            atualizarTempos(inativos, TEMPO_AMARELO - i);
            painelVisual.repaint();
            Thread.sleep(1000);
        }

        // Finaliza: muda ativos para vermelho (deixa inativos quietos — eles vão virar verde depois)
        mudarEstadoTodos(ativos, "VERMELHO");
    }


    private void mudarEstadoTodos(List<Semaforo> semaforos, String estadoDesejado) {
        for (Semaforo s : semaforos) {
            while (!s.getEstado().contains(estadoDesejado)) {
                s.mudarEstado();
            }
        }
        painelVisual.repaint();
    }

    public void notificar(String msg) {
        SwingUtilities.invokeLater(() -> {
            painelNotificacoes.append(msg + "\n");
            painelNotificacoes.setCaretPosition(
                    painelNotificacoes.getDocument().getLength()
            );
        });
    }

    private void atualizarTempos(List<Semaforo> semaforos, int restante) {
        for (Semaforo s : semaforos) {
            painelVisual.atualizarTempoRestante(s, Math.max(restante, 0));
        }
    }
}