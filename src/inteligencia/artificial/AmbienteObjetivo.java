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
public class AmbienteObjetivo {
    private int n;
    private Celula[][] grid;
    private AgenteObjetivo agente;
    private int passos;
    private int goalX, goalY;
    
    public AmbienteObjetivo(int n, int[][] obstaculos) {
        this.n = n;
        this.grid = new Celula[n][n];
        this.passos = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = new Celula(false);
            }
        }
        for (int[] obs : obstaculos) {
            int x = obs[0], y = obs[1];
            if (x >= 0 && x < n && y >= 0 && y < n) {
                grid[x][y].setBloqueada(true);
            }
        }
        Random rand = new Random();
        int startX, startY;
        do {
            startX = rand.nextInt(n);
            startY = rand.nextInt(n);
        } while (grid[startX][startY].isBloqueada());
        do {
            goalX = rand.nextInt(n);
            goalY = rand.nextInt(n);
        } while (grid[goalX][goalY].isBloqueada() || (goalX == startX && goalY == startY));
        System.out.println("Etapa 3: Agente baseado em objetivos:");
        System.out.println("Inicio   : (" + startX + ", " + startY + ")");
        System.out.println("Objetivo : (" + goalX + ", " + goalY + ")");
        System.out.println("Obstaculos: " + obstaculos.length + "\n");
        this.agente = new AgenteObjetivo(startX, startY, goalX, goalY, n, grid);
    }
    
    public void ExecutarSimulacao() {
        while(!agente.isChegouObjetivo()) {
            passos++;
            System.out.print("Passos " + passos + ": ");
            if (!agente.mover()) {
                System.out.println("Simulacao interrompida");
                return;
            }
            exibirModeloAgente(agente.getX(), agente.getY());
        }
        System.out.println("\nSimulacaoo concluida em " + passos + " passos");
    }
    
    public void exibirGrid() {
        System.out.println("\nEstado final do grid (real):");
        System.out.print(" ");
        for (int j = 0; j < n; j++) System.out.printf("%2d ", j);
        System.out.println();
        for (int i = 0; i < n; i++) {
            System.out.printf("%2d", i);
            for (int j = 0; j < n; j++) {
                if (grid[i][j].isBloqueada()) System.out.print("[#] ");
                else if (grid[i][j].isVisitada()) System.out.print("[X] ");
                else System.out.print("[ ] ");
            }
            System.out.println();
        }
    }
    
    public void exibirModeloAgente(int agenteX, int agenteY) {
        System.out.println("\nModelo interno do agente:");
        System.out.print("   ");
        for (int j = 0; j < n; j++) System.out.printf("%2d ", j);
        System.out.println();
        for (int i = 0; i < n; i++) {
            System.out.printf("%2d", i);
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
}
