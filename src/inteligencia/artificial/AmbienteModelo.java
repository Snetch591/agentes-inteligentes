/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inteligencia.artificial;

import java.util.Random;

/**
 *
 * @author vinic
 */
public class AmbienteModelo {
    private int n;
    private Celula[][] grid;
    private AgenteModelo agente;
    private int passos;
    private int numeroObstaculos;
    
    public AmbienteModelo(int n, int[][] obstaculos) {
        this.n = n;
        this.grid = new Celula[n][n];
        this.passos = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = new Celula(false);
            }
        }
        for (int[] obs : obstaculos) {
            int x = obs[0];
            int y = obs[1];
            if (x >= 0 && x < n && y >= 0 && y < n) {
                grid[x][y].setBloqueada(true);
                numeroObstaculos++;
            }
        }
        Random rand = new Random();
        int xInicial, yInicial;
        do {
            xInicial = rand.nextInt(n);
            yInicial = rand.nextInt(n);
        } while (grid[xInicial][yInicial].isBloqueada());
        System.out.println("\nPosicao inicial: (" + xInicial + ", " + yInicial + ")");
        System.out.println("Total de celulas: " + (n * n));
        System.out.println("Total de obstaculos: " + obstaculos.length);
        System.out.println("Celulas visitaveis: " + ((n * n) - obstaculos.length) + "\n");
        
        this.agente = new AgenteModelo(xInicial, yInicial, n, numeroObstaculos, grid);
    }
    
    public void ExecutarSimulacao() {
        System.out.println("Etapa 2: Agente Reativo Baseado em Modelo");
        while(!agente.acabou()) {
            passos++;
            System.out.print("Passo " + passos + ": ");
            agente.mover();
            exibirModeloAgente(agente.getX(), agente.getY());
        }
        System.out.println("\nConcluido");
        System.out.println("Total de passos: " + passos);
        System.out.println("Celulas visitadas: " + agente.getCelulasVisitadas() + " de " + agente.getTotalCelulas());
        System.out.println("Celulas nao visitadas (obstaculos e nao alcancadas: )" + (agente.getTotalCelulas() - agente.getCelulasVisitadas()));
    }
    
    public void exibirGrid() {
        System.out.println("\nEstado  do grid:");
        System.out.print(" ");
        for (int j = 0; j < n; j++) {
            System.out.printf("%2d ", j);
        }
        System.out.println();
        for (int i = 0; i < n; i++) {
            System.out.printf("%2d ", i);
            for (int j = 0; j < n; j++) {
                if (grid[i][j].isBloqueada()) {
                    System.out.print("[#] ");
                } else if (grid[i][j].isVisitada()) {
                    System.out.print("[X] ");
                } else {
                    System.out.print("[ ] ");
                }
            }
            System.out.println();
        }
    }
    
    public void exibirModeloAgente(int agenteX, int agenteY) {
        System.out.println("\nModelo interno do agente:");
        System.out.print("   ");
        for (int j = 0; j < n; j++) {
            System.out.printf("%2d ", j);
        }
        System.out.println();
        for (int i = 0; i < n; i++) {
            System.out.printf("%2d ", i);
            for (int j = 0; j < n; j++) {
                if (agente.verificarObstaculoConhecido(i, j)) {
                    System.out.print("[#] ");
                } else if (agente.verificarVisitadoConhecido(i, j)) {
                    System.out.print("[X] ");
                } else if (i == agenteX && j == agenteY) {
                    System.out.print("[A] ");
                } else {
                    System.out.print("[?] ");
                }
            }
            System.out.println();
        }
    }

    public AgenteModelo getAgente() {
        return agente;
    }
    
    
}


