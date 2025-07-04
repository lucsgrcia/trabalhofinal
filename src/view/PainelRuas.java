package view;

import model.factorymethod.Semaforo;

import javax.swing.*;
import java.awt.*;
import java.awt.font.GlyphVector;
import java.util.*;
import java.util.List;

public class PainelRuas extends JPanel {
    private final int alturaRua = 30;
    private final int larguraAvenida = 30;
    private int numeroRuas;
    private int fluxoAtual = 0;
    private final List<SemaforoDesenhado> semaforosGraficos = new ArrayList<>();
    private final Map<Semaforo, Integer> tempoRestante = new HashMap<>();

    public PainelRuas(int numeroRuas) {
        this.numeroRuas = numeroRuas;
        setPreferredSize(new Dimension(450, 600));
        setBackground(Color.lightGray);
    }

    public void adicionarSemaforos(int indiceRua, Semaforo avenida, Semaforo rua) {
        semaforosGraficos.add(new SemaforoDesenhado(indiceRua, true, avenida));
        semaforosGraficos.add(new SemaforoDesenhado(indiceRua, false, rua));
    }

    public void setFluxoAtual(int fluxo) {
        this.fluxoAtual = fluxo;
    }

    public void atualizarTempoRestante(Semaforo s, int t) {
        tempoRestante.put(s, t);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int H = getHeight();
        int W = getWidth();
        int s = (H - numeroRuas * alturaRua) / (numeroRuas + 1);
        int centroAvenida = W / 2;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Avenida vertical
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(centroAvenida - (larguraAvenida + 13) / 2, 0, larguraAvenida + 15, H);

        // Linhas tracejadas da avenida
        g2.setColor(Color.WHITE);
        for (int y = 0; y < H; y += 20) {
            g2.drawLine(centroAvenida, y, centroAvenida, y + 10);
        }

        int y = s;
        for (int i = 0; i < numeroRuas; i++) {
            // Rua horizontal
            g2.setColor(Color.DARK_GRAY);
            g2.fillRect(0, y, W, alturaRua);

            // Linhas tracejadas das ruas
            g2.setColor(Color.WHITE);
            for (int x = 0; x < W; x += 20) {
                g2.drawLine(x, y + alturaRua / 2, x + 10, y + alturaRua / 2);
            }

            y += alturaRua + s;
        }

        Font tempoFonte = new Font("Arial", Font.BOLD, 14);
        g2.setFont(tempoFonte);

        for (SemaforoDesenhado sd : semaforosGraficos) {
            int yFaixa = s + sd.indiceRua * (alturaRua + s);
            int x = sd.ehAvenida ? getWidth() / 2 - larguraAvenida / 3
                    : getWidth() / 2 - larguraAvenida / 2 - 25;

            String estado = sd.semaforo.getEstado();
            if (estado.contains("VERDE")) {
                g2.setColor(Color.GREEN);
            } else if (estado.contains("AMARELO")) {
                g2.setColor(Color.YELLOW);
            } else {
                g2.setColor(Color.RED);
            }

            g2.fillOval(x, yFaixa + (sd.ehAvenida ? 30 : 0) + 5, 20, 20);

            // Texto com contorno preto e preenchimento branco
            int restante = tempoRestante.getOrDefault(sd.semaforo, 0);
            String texto = restante + "s";

            int tx = x + 25;
            int ty = yFaixa + (sd.ehAvenida ? 50 : 20);
            drawTextoContornado(g2, texto, tx, ty, tempoFonte, Color.WHITE, Color.BLACK);
        }

        // Texto do fluxo
        drawTextoContornado(g2, "Fluxo atual: " + fluxoAtual + " veículos", 10, 20,
                new Font("Arial", Font.BOLD, 14), Color.WHITE, Color.BLACK);

        // "Av. Principal"
        String avPrincipal = "Av. Principal";
        int larguraTexto = g2.getFontMetrics().stringWidth(avPrincipal);
        int xTexto = (W - larguraTexto) / 2;
        drawTextoContornado(g2, avPrincipal, xTexto, H - 10,
                new Font("Arial", Font.BOLD, 16), Color.WHITE, Color.BLACK);
    }


    private static class SemaforoDesenhado {
        int indiceRua;
        boolean ehAvenida;
        Semaforo semaforo;

        SemaforoDesenhado(int indiceRua, boolean ehAvenida, Semaforo semaforo) {
            this.indiceRua = indiceRua;
            this.ehAvenida = ehAvenida;
            this.semaforo = semaforo;
        }
    }

    private void drawTextoContornado(Graphics2D g2, String texto, int x, int y, Font fonte, Color fill, Color contorno) {
        g2.setFont(fonte);
        FontMetrics fm = g2.getFontMetrics();
        GlyphVector gv = fonte.createGlyphVector(g2.getFontRenderContext(), texto);
        Shape shape = gv.getOutline(x, y);

        g2.setStroke(new BasicStroke(2f));
        g2.setColor(contorno);
        g2.draw(shape);

        g2.setColor(fill);
        g2.fill(shape);
    }

}
