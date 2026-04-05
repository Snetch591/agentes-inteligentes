/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inteligencia.artificial;

/**
 *
 * @author vinic
 */
public class AmbienteUtilidade {
    private int n = 10;
    private CelulaUtilidade[][] grid;
    private AgenteUtilidade agente;
    private int passos;
    private boolean completamenteObservavel;
    
    public AmbienteUtilidade(boolean completamenteObservavel) {
        this.completamenteObservavel = completamenteObservavel;
        this.grid = new CelulaUtilidade[n][n];
        this.passos = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = new CelulaUtilidade(1);
            }
        }
        int amarelo[][] = {{2,2},{2,3},{2,7},{3,2},{3,7},{4,2},{4,3},{4,7},{4,8},{5,3},{5,6},{5,7},{6,5}};
        for (int[] p : amarelo) {
            grid[p[0]][p[1]].setCusto(2);
        }
        int vermelho[][] = {{1,5},{2,5},{2,6},{3,4},{3,5},{3,6},{4,4},{4,5},{4,6},{5,4},{6,5}};
        for (int[] p : vermelho) {
            grid[p[0]][p[1]].setCusto(3);
        }
        int startX = 0, startY = 5;
        int goalX = 9, goalY = 5;
        System.out.println("Etapa 4: Agente Baseado em Utilidade");
        System.out.println("Modo: " + (completamenteObservavel ? "Completamente Observavel (Variacao 1)" : "Parcialmente Observavel (Variacao 2)"));
        System.out.println("Inicio : (0, 5)");
        System.out.println("Objetivo : (9, 5)\n");
        this.agente = new AgenteUtilidade(startX, startY, goalX, goalY, n, grid, completamenteObservavel);
    }
    
    public void ExecutarSimulacao() {
        while(!agente.isChegouObjetivo()) {
            passos++;
            System.out.print("Passo " + passos + ": ");
            if(!agente.mover()) {
                System.out.println("Simulacao interrompida");
                return;
            }
        }
        System.out.println("Caminho de menor custo encontrado em " + passos + " passos");
    }
    
    public void exibirGrid() {
        System.out.println("\nGrid final com custos (real):");
        System.out.print("   ");
        for (int j = 0; j < n; j++) System.out.printf("%2d ", j);
        System.out.println();
        for (int i = 0; i < n; i++) {
            System.out.printf("%2d ", i);
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 5) System.out.print(" i  ");
                else if (i == 9 && j == 5) System.out.print(" f  ");
                else if (grid[i][j].getCusto() == 3) System.out.print(" R  ");
                else if (grid[i][j].getCusto() == 2) System.out.print(" A  ");
                else System.out.print(" G  ");
            }
            System.out.println();
        }
    }
    
    public void exibirModeloAgente() {
        System.out.println("\nModelo interno do agente:");
        System.out.print("   ");
        for (int j = 0; j < n; j++) System.out.printf("%2d ", j);
        System.out.println();
        for (int i = 0; i < n; i++) {
            System.out.printf("%2d ", i);
            for (int j = 0; j < n; j++) {
                if (agente.verificarBloqueada(i, j)) {
                    System.out.print("[#] ");
                } else if (agente.verificarVisitada(i, j)) {
                    System.out.printf("[%d] ", agente.getCustoConhecido(i, j));
                } else {
                    System.out.print("[?] ");
                }
            }
            System.out.println();
        }
    }
}
