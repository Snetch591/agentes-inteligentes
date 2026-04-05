/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inteligencia.artificial;

/**
 *
 * @author vinic
 */
public class AmbienteSimples {
    private int n;
    private Celula[][] grid;
    private AgenteSimples agente;
    private int passos;
    
    public AmbienteSimples(int n) {
        this.n = n;
        this.grid = new Celula[n][n];
        this.passos = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = new Celula();
            }
        }
        java.util.Random rand = new java.util.Random();
        int xInicial = rand.nextInt(n);
        int yInicial = rand.nextInt(n);
        System.out.println("Posicao inicial: (" + xInicial + ", " + yInicial + ")");
        this.agente = new AgenteSimples(xInicial, yInicial, n);
    }
    
    public void executarSimulacao() {
        System.out.println("Etapa 1: Agente Reativo Simples");
        
        while(!agente.alcancouFronteiras()) {
            passos++;
            System.out.println("Passo " + passos +":" );
            agente.mover();
            exibirGrid(agente.getX(), agente.getY());
        }
        System.out.println("Concluido");
        System.out.println("Total de passos: " + passos);
    }
    
    public void exibirGrid(int agenteX, int agenteY) {
        System.out.println("\nEstado atual do grid:");
        System.out.print("   ");
        for (int j = 0; j < n; j++) System.out.printf("%2d ", j);
        System.out.println();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == agenteX && j == agenteY) {
                    System.out.print("[A] ");
                } else {
                    System.out.print("[ ] ");
                }
            }
            System.out.println();
        }
    }
}
