/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inteligencia.artificial;

/**
 *
 * @author vinic
 */
import java.util.*;
public class AgenteModelo {
    private int x;
    private int y;
    private int n;
    private Celula[][] gridReal;
    private Celula[][] modeloInterno;
    private Random random;
    private int totalCelulas;
    private int celulasVisitadas;
    private boolean acabou;
    private int numeroObstaculos;
    
    public AgenteModelo(int x, int y, int n, int numeroObstaculos, Celula[][] gridReal) {
        this.x = x;
        this.y = y;
        this.n = n;
        this.numeroObstaculos = numeroObstaculos;
        this.gridReal = gridReal;
        this.random = new Random();
        this.totalCelulas = n * n;
        this.celulasVisitadas = 0;
        this.acabou = false;
        this.modeloInterno = new Celula[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                modeloInterno[i][j] = new Celula(false);
            }
        }
        marcarPosicaoAtual();
    }
    
    private void atualizarModelo() {
        if (gridReal[x][y].isBloqueada()) {
            modeloInterno[x][y].setBloqueada(true);
        }
        verificarCelulaAdjacente(x - 1, y);
        verificarCelulaAdjacente(x + 1, y);
        verificarCelulaAdjacente(x, y - 1);
        verificarCelulaAdjacente(x, y + 1);
    }
    
    private void verificarCelulaAdjacente(int nx, int ny) {
        if (nx >= 0 && nx < n && ny >= 0 && ny < n) {
            if (gridReal[nx][ny].isBloqueada()) {
                modeloInterno[nx][ny].setBloqueada(true);
            } else {
                modeloInterno[nx][ny].setBloqueada(false);
            }
        }
    }
    
    private void marcarPosicaoAtual() {
        if (!gridReal[x][y].isBloqueada()) {
            if (!gridReal[x][y].isVisitada()) {
                celulasVisitadas++;
            }
            gridReal[x][y].setVisitada(true);
            modeloInterno[x][y].setVisitada(true);
        }
    }
    
    private boolean podeMover(int nx, int ny) {
        if (nx < 0 || nx >= n || ny < 0 || ny >= n) {
            return false;
        }
        if (modeloInterno[nx][ny].isBloqueada()) {
            return false;
        }
        return true;
    }
    
    public int getQuantidadeMovimentos() {
        int count = 0;
        if (podeMover(x - 1, y)) count++;
        if (podeMover(x + 1, y)) count++;
        if (podeMover(x, y - 1)) count++;
        if (podeMover(x, y + 1)) count++;
        return count;
    }
    
    public void mover() {
        
        if (celulasVisitadas == (totalCelulas - numeroObstaculos)) {
            acabou = true;
            System.out.println("Todas as casas foram percorridas");
            return;
        }
        
        atualizarModelo();
        
        List<Movimento> naoVisitados = new ArrayList<>();
        
        if (podeMover(x - 1, y)) {
            if (!modeloInterno[x - 1][y].isVisitada()) {
                naoVisitados.add(new Movimento("NORTE", x - 1, y));
            }
        }

        if (podeMover(x + 1, y)) {
            if (!modeloInterno[x + 1][y].isVisitada()) {
                naoVisitados.add(new Movimento("SUL", x + 1, y));
            }
        }

        if (podeMover(x, y - 1)) {
            if (!modeloInterno[x][y - 1].isVisitada()) {
                naoVisitados.add(new Movimento("OESTE", x, y - 1));
            }
        }

        if (podeMover(x, y + 1)) {
            if (!modeloInterno[x][y + 1].isVisitada()) {
                naoVisitados.add(new Movimento("LESTE", x, y + 1));
            }
        }

        if (!naoVisitados.isEmpty()) {
            Movimento escolhido = naoVisitados.get(random.nextInt(naoVisitados.size()));
            x = escolhido.x;
            y = escolhido.y;
            marcarPosicaoAtual();
            System.out.println("Explorando " + escolhido.direcao + " | Posicao: (" + x + ", " + y + ")" + " | Visitadas: " + celulasVisitadas + "/" + totalCelulas);
            return;
        }
        
        int[] destino = buscarCelulaNaoVisitadaMaisProxima();
        
        if (destino == null) {
            acabou = true;
            return;
        }
        
        int[] proximo = proximoPassoBFS(destino);
        
        x = proximo[0];
        y = proximo[1];
        
        marcarPosicaoAtual();
        
        System.out.println("Indo para destino (" + destino[0] + "," + destino[1] + ")" + " | Atual: (" + x + ", " + y + ")");
    }

    public boolean acabou() {
        return acabou;
    }
    
    public int getTotalCelulas() {
        return totalCelulas;
    }

    public int getCelulasVisitadas() {
        return celulasVisitadas;
    }
    
    public boolean verificarObstaculoConhecido(int x, int y) {
        return modeloInterno[x][y].isBloqueada();
    }
    
    public boolean verificarVisitadoConhecido(int x, int y) {
        return modeloInterno[x][y].isVisitada();
    }
    
    private int[] buscarCelulaNaoVisitadaMaisProxima() {
        boolean[][] visitado = new boolean[n][n];
        Queue<int[]> fila = new LinkedList<>();
        fila.add(new int[]{x, y});
        visitado[x][y] = true;
        while (!fila.isEmpty()) {
            int[] atual = fila.poll();
            int cx = atual[0];
            int cy = atual[1];
            if (!modeloInterno[cx][cy].isVisitada() && !modeloInterno[cx][cy].isBloqueada()) {
                return atual;
            }
            int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
            for (int[] d : dirs) {
                int nx = cx + d[0];
                int ny = cy + d[1];
                if (nx >= 0 && nx < n && ny >= 0 && ny < n && !visitado[nx][ny] && !modeloInterno[nx][ny].isBloqueada()) {
                    visitado[nx][ny] = true;
                    fila.add(new int[]{nx, ny});
                }
            }
        }
        return null;
    }
    
    private int[] proximoPassoBFS(int[] destino) {
        Map<String, int[]> parent = new HashMap<>();
        boolean[][] visitado = new boolean[n][n];
        Queue<int[]> fila = new LinkedList<>();
        fila.add(new int[]{x,y});
        visitado[x][y] = true;
        parent.put(x + "," + y, null);
        while (!fila.isEmpty()) {
            int[] curr = fila.poll();
            int cx = curr[0], cy = curr[1];
            if (cx == destino[0] && cy == destino[1]) break;
            int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
            for (int[] d : dirs){
                int nx = cx + d[0], ny = cy + d[1];
                if (nx >= 0 && nx < n && ny >= 0 && ny < n && !visitado[nx][ny] && !modeloInterno[nx][ny].isBloqueada()) {
                    visitado[nx][ny] = true;
                    fila.add(new int[]{nx, ny});
                    parent.put(nx + "," + ny, curr);
                }
            }
        }
        int cx = destino[0], cy = destino[1];
        while (true) {
            int[] prev = parent.get(cx + "," + cy);
            if (prev[0] == x && prev[1] == y) {
                return new int[]{cx, cy};
            }
            cx = prev[0];
            cy = prev[1];
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
    
    private class Movimento {
        String direcao;
        int x;
        int y;
        Movimento(String direcao, int x, int y) {
            this.direcao = direcao;
            this.x = x;
            this.y = y;
        }
    }
}
