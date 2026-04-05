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
public class AgenteUtilidade {
    private int x, y;
    private int goalX, goalY;
    private int n;
    private CelulaUtilidade[][] gridReal;
    private CelulaUtilidade[][] modeloInterno;
    private boolean chegouObjetivo;
    private boolean completamenteObservavel;
    
    public AgenteUtilidade(int startX, int startY, int goalX, int goalY, int n, CelulaUtilidade[][] gridReal, boolean completamenteObservavel) {
        this.x = startX;
        this.y = startY;
        this.goalX = goalX;
        this.goalY = goalY;
        this.n = n;
        this.gridReal = gridReal;
        this.completamenteObservavel = completamenteObservavel;
        this.chegouObjetivo = false;
        this.modeloInterno = new CelulaUtilidade[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                modeloInterno[i][j] = new CelulaUtilidade(1);
            }
        }
        marcarPosicaoAtual();
    }
    
    private void atualizarModelo() {
        modeloInterno[x][y].setCusto(gridReal[x][y].getCusto());
        modeloInterno[x][y].setBloqueada(gridReal[x][y].isBloqueada());
        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int[] d : dirs) {
            int nx = x + d[0], ny = y + d[1];
            if (nx >= 0 && nx < n && ny >= 0 && ny < n) {
                modeloInterno[nx][ny].setCusto(gridReal[nx][ny].getCusto());
                modeloInterno[nx][ny].setBloqueada(gridReal[nx][ny].isBloqueada());
            }
        }
    }
    
    private void marcarPosicaoAtual() {
        if (!gridReal[x][y].isBloqueada()) {
            gridReal[x][y].setVisitada(true);
            modeloInterno[x][y].setVisitada(true);
            modeloInterno[x][y].setCusto(gridReal[x][y].getCusto());
        }
    }
    
    public boolean mover() {
            atualizarModelo();
        if (x == goalX && y == goalY) {
            chegouObjetivo = true;
            System.out.println("Objetivo alcancado em (" + x + ", " + y + ") com custo total minimo");
            return true;
        }
        int[] proximo = aStarProximoMovimento();
        if (proximo == null) {
            System.out.println("Nao foi possivel encontrar o caminho");
            return false;
        }
        x = proximo[0];
        y = proximo[1];
        marcarPosicaoAtual();
        if (x == goalX && y == goalY) {
            chegouObjetivo = true;
            System.out.println("Objetivo alcancado em (" + x + ", " + y + ")");
        } else {
            System.out.println("Movendo para (" + x + ", " + y + ")");
        }
        return true;
    }
    
    private int[] aStarProximoMovimento() {
        boolean[][] visitado = new boolean[n][n];
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Map<String, Node> cameFrom = new HashMap<>();
        Map<String, Integer> gScore = new HashMap<>();
        String startKey = x + "," + y;
        openSet.add(new Node(x, y, 0, manhattan(x, y)));
        gScore.put(startKey, 0);
        cameFrom.put(startKey, null);
        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            int cx = current.x, cy = current.y;
            String currKey = cx + "," + cy;
            if (cx == goalX && cy == goalY) {
                return reconstruirProximoPasso(cameFrom);
            }
            int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
            for (int[] d : dirs) {
                int nx = cx + d[0], ny = cy + d[1];
                if (nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
                CelulaUtilidade cel;
                if (completamenteObservavel) {
                    cel = gridReal[nx][ny];
                } else {
                    cel = modeloInterno[nx][ny];
                    if (!cel.isVisitada()) {
                        cel = new CelulaUtilidade(1);
                    }
                }
                if (cel.isBloqueada() || visitado[nx][ny]) continue;
                int tentativeG = gScore.getOrDefault(currKey, Integer.MAX_VALUE) + cel.getCusto();
                String neighKey = nx + "," + ny;
                if (tentativeG < gScore.getOrDefault(neighKey, Integer.MAX_VALUE)) {
                    cameFrom.put(neighKey, current);
                    gScore.put(neighKey, tentativeG);
                    int custoMinimo = 1;
                    int f = tentativeG + (manhattan(nx, ny) * custoMinimo);
                    openSet.add(new Node(nx, ny, tentativeG, f));
                    visitado[nx][ny] = true;
                }
            }
        }
        return null;
    }
    
    private int[] reconstruirProximoPasso(Map<String, Node> cameFrom) {
        int cx = goalX, cy = goalY;
        while (true) {
            String key = cx + "," + cy;
            Node prev = cameFrom.get(key);
            if (prev == null) {
                return null; // segurança
            }
            if (prev.x == x && prev.y == y) {
                return new int[]{cx, cy};
            }
            cx = prev.x;
            cy = prev.y;
        }
    }
    
    private int manhattan(int a, int b) {
        return Math.abs(a - goalX) + Math.abs(b - goalY);
    }
    
    public boolean isChegouObjetivo() {
        return chegouObjetivo;
    }
    
    public int getCustoConhecido(int i, int j) {
        return modeloInterno[i][j].getCusto();
    }
    
    public boolean verificarBloqueada(int i, int j) {
        return modeloInterno[i][j].isBloqueada();
    }
    
    public boolean verificarVisitada(int i, int j) {
        return modeloInterno[i][j].isVisitada();
    }
    
    public static class Node implements Comparable<Node> {
        int x, y, g, f;
        Node(int x, int y, int g, int f) {
            this.x = x;
            this.y = y;
            this.g = g;
            this.f = f;
        }
        public int compareTo(Node other) {
            return Integer.compare(this.f, other.f);
        }
    }
}
