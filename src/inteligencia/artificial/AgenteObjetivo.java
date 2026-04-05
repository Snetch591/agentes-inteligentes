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
public class AgenteObjetivo {
    private int x, y;
    private int goalX, goalY;
    private int n;
    private Celula[][] gridReal;
    private Celula[][] modeloInterno;
    private boolean chegouObjetivo;
    
    public AgenteObjetivo(int startX, int startY, int goalX, int goalY, int n, Celula[][] gridReal) {
        this.x = startX;
        this.y = startY;
        this.goalX = goalX;
        this.goalY = goalY;
        this.n = n;
        this.gridReal = gridReal;
        this.chegouObjetivo = false;
        this.modeloInterno = new Celula[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                modeloInterno[i][j] = new Celula(false);
            }
        }
        marcarPosicaoAtual();
    }
    
    private void atualizarModelo() {
        if (gridReal[x][y].isBloqueada()) modeloInterno[x][y].setBloqueada(true);
        verificarAdjacente(x - 1, y);
        verificarAdjacente(x + 1, y);
        verificarAdjacente(x, y - 1);
        verificarAdjacente(x, y + 1);
    }
    
    private void verificarAdjacente(int nx, int ny) {
        if (nx >= 0 && nx < n && ny >= 0 && ny < n) {
            if (gridReal[nx][ny].isBloqueada()) {
                modeloInterno[nx][ny].setBloqueada(true);
            }
        }
    }
    
    private void marcarPosicaoAtual() {
        if (!gridReal[x][y].isBloqueada()) {
            gridReal[x][y].setVisitada(true);
            modeloInterno[x][y].setVisitada(true);
        }
    }
    
    // Agente baseado em objetivos com planejamento (BFS)
    // Opera em ambiente parcialmente observável
    // Replaneja a cada passo com base no modelo interno
    public boolean mover() {
        atualizarModelo();
        if (x == goalX && y == goalY) {
            chegouObjetivo = true;
            System.out.println("Objetivo alcançado em (" + x + ", " + y + ")");
            return true;
        }
        int[] proximo = bfsProximoMovimento();
        if (proximo == null) {
            int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
            for (int[] d : dirs) {
                int nx = x + d[0];
                int ny = y + d[1];
                if (nx >= 0 && nx < n && ny >= 0 && ny < n && !modeloInterno[nx][ny].isBloqueada()) {
                    x = nx;
                    y = ny;
                    marcarPosicaoAtual();
                    System.out.println("Explorando...");
                    return true;
                }
            }
            System.out.println("Não foi possível explorar mais");
            return false;
        }
        x = proximo[0];
        y = proximo[1];
        marcarPosicaoAtual();
        if (x == goalX && y == goalY) {
            chegouObjetivo = true;
            System.out.println("Objetivo alcancado em (" + x + ", " + y + ")");
        } else {
            System.out.println("Movendo para (" + x + ", " + y + ") | Dist. Manhattan: " + manhattan());
        }
        return true;
    }
    
    private int[] bfsProximoMovimento() {
        boolean[][] visitado = new boolean[n][n];
        Queue<int[]> fila = new LinkedList<>();
        Map<String, int[]> parent = new HashMap<>();
        fila.add(new int[]{x, y});
        visitado[x][y] = true;
        parent.put(x + "," + y, null);
        boolean encontrado = false;
        while(!fila.isEmpty() && !encontrado) {
            int[] curr = fila.poll();
            int cx = curr[0], cy = curr[1];
            if (cx == goalX && cy == goalY) {
                encontrado = true;
                break;
            }
            int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
            for (int[] d : dirs) {
                int nx = cx + d[0];
                int ny = cy + d[1];
                if (nx >= 0 && nx < n && ny >= 0 && ny < n && !visitado[nx][ny] && !modeloInterno[nx][ny].isBloqueada()) {
                    visitado[nx][ny] = true;
                    fila.add(new int[]{nx, ny});
                    parent.put(nx + "," + ny, new int[]{cx, cy});
                }
            }
        }
        if (!encontrado) return null;
        int cx = goalX, cy = goalY;
        while(true) {
            int[] prev = parent.get(cx + "," + cy);
            if (prev[0] == x && prev[1] == y) {
                return new int[]{cx, cy};
            }
            cx = prev[0];
            cy = prev[1];
        }
    }
    
    private int manhattan() {
        return Math.abs(x - goalX) + Math.abs(y - goalY);
    }
    
    public boolean isChegouObjetivo() {
        return chegouObjetivo;
    }
    
    public boolean verificarObstaculoConhecido(int x, int y) {
        return modeloInterno[x][y].isBloqueada();
    }
    
    public boolean verificarVisitadoConhecido(int x, int y) {
        return modeloInterno[x][y].isVisitada();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
}
